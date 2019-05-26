package com.github.boggard.cydb.model.statement.casestatement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CaseResult {
    private String value;
    private ResultType resultType;
    private ValueType valueType;
}
