package com.github.boggard.cydb.dto;

import lombok.Value;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Value
public class AnalyzeResult {

    private Map<Integer, List<LineError>> linesError = new HashMap<>();

    public void putError(int line, String ruleId, String message) {
        List<LineError> errors = linesError.getOrDefault(line, new LinkedList<>());
        errors.add(new LineError(ruleId, message));
        linesError.put(line, errors);
    }
}
