package com.github.boggard.cydb.service;

import com.github.boggard.cydb.parser.SQLParser;
import com.github.boggard.cydb.parser.TableListener;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.LinkedList;

@RequiredArgsConstructor
@Service
public class StaticAnalyzer {

    private final KieContainer kieContainer;

    public Collection<String> analyze(MultipartFile multipartFile) {
        try {
            KieSession kieSession = kieContainer.newKieSession();
            LinkedList<String> errors = new LinkedList<>();
            kieSession.setGlobal("errors", errors);

            TableListener tableListener = new TableListener();
            SQLParser.parseSql(multipartFile, tableListener);

            tableListener.getTable().getColumns().forEach(kieSession::insert);
            kieSession.fireAllRules();
            kieSession.dispose();

            return errors;
        } catch (Exception ex) {
            throw new IllegalStateException("An error occurred", ex);
        }
    }
}
