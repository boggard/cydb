package com.github.boggard.cydb.model.statement.casestatement;

import com.github.boggard.cydb.model.StructureElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CaseStatement extends StructureElement {

    private List<CaseResult> results = new LinkedList<>();

    public CaseStatement(int line) {
        super(line);
    }

    public void addResult(CaseResult caseResult) {
        results.add(caseResult);
    }
}
