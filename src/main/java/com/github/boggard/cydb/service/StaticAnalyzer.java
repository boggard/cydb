package com.github.boggard.cydb.service;

import com.github.boggard.cydb.dto.AnalyzeResult;
import com.github.boggard.cydb.model.Table;
import com.github.boggard.cydb.parser.AlterTableStatementListener;
import com.github.boggard.cydb.parser.CaseStatementListener;
import com.github.boggard.cydb.parser.SQLParser;
import com.github.boggard.cydb.parser.TableListener;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class StaticAnalyzer {

    private final KieContainer kieContainer;

    public AnalyzeResult analyze(MultipartFile multipartFile) {
        try {
            KieSession kieSession = kieContainer.newKieSession();
            AnalyzeResult result = new AnalyzeResult();
            kieSession.setGlobal("res", result);

            TableListener tableListener = new TableListener();
            CaseStatementListener caseStatementListener = new CaseStatementListener();
            AlterTableStatementListener alterTableStatementListener = new AlterTableStatementListener();
            SQLParser.parseSql(multipartFile, tableListener, caseStatementListener, alterTableStatementListener);

            tableListener.getTables().forEach(kieSession::insert);
            tableListener.getTables().stream()
                    .map(Table::getColumns)
                    .flatMap(Collection::stream)
                    .forEach(kieSession::insert);
            caseStatementListener.getCaseStatements().forEach(kieSession::insert);
            alterTableStatementListener.getStatements().forEach(kieSession::insert);

            kieSession.fireAllRules();
            kieSession.dispose();

            return result;
        } catch (Exception ex) {
            throw new IllegalStateException("An error occurred", ex);
        }
    }
}
