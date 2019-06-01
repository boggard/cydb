package com.github.boggard.cydb.service;

import com.github.boggard.cydb.dto.AnalyzeResult;
import com.github.boggard.cydb.dto.AnalyzeResultWithFile;
import com.github.boggard.cydb.model.Table;
import com.github.boggard.cydb.parser.AlterTableStatementListener;
import com.github.boggard.cydb.parser.CaseStatementListener;
import com.github.boggard.cydb.parser.SQLParser;
import com.github.boggard.cydb.parser.TableListener;
import com.github.boggard.cydb.util.FileInputStreamSource;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StaticAnalyzer {

    private final KieContainer kieContainer;

    public AnalyzeResultWithFile analyze(MultipartFile multipartFile) {
        try {
            File tempFile = File.createTempFile("analyze", "sql");
            multipartFile.transferTo(tempFile);

            KieSession kieSession = kieContainer.newKieSession();
            AnalyzeResult result = new AnalyzeResult();
            kieSession.setGlobal("res", result);

            TableListener tableListener = new TableListener();
            CaseStatementListener caseStatementListener = new CaseStatementListener();
            AlterTableStatementListener alterTableStatementListener = new AlterTableStatementListener();
            SQLParser.parseSql(new FileInputStreamSource(tempFile), tableListener, caseStatementListener, alterTableStatementListener);

            tableListener.getTables().forEach(kieSession::insert);
            tableListener.getTables().stream()
                    .map(Table::getColumns)
                    .flatMap(Collection::stream)
                    .forEach(kieSession::insert);
            caseStatementListener.getCaseStatements().forEach(kieSession::insert);
            alterTableStatementListener.getStatements().forEach(kieSession::insert);

            kieSession.fireAllRules();
            kieSession.dispose();

            return AnalyzeResultWithFile.fromAnalyzeResult(readFile(new FileInputStreamSource(tempFile)), result);
        } catch (Exception ex) {
            throw new IllegalStateException("An error occurred", ex);
        }
    }

    private List<String> readFile(InputStreamSource inputStreamSource) throws IOException {
        try (InputStream inputStream = inputStreamSource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}
