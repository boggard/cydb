package com.github.boggard.cydb.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class AnalyzeResult {

    private int vulnerableCount = 0;
    private int bugsCount = 0;
    private int smellsCount = 0;
    private Map<Integer, List<LineError>> linesError = new HashMap<>();

    public void putVulnerable(int line, String ruleId, String message) {
        vulnerableCount++;
        putError(line, ruleId, message);
    }

    public void putBug(int line, String ruleId, String message) {
        bugsCount++;
        putError(line, ruleId, message);
    }

    public void putSmell(int line, String ruleId, String message) {
        smellsCount++;
        putError(line, ruleId, message);
    }

    private void putError(int line, String ruleId, String message) {
        List<LineError> errors = linesError.getOrDefault(line, new LinkedList<>());
        errors.add(new LineError(ruleId, message));
        linesError.put(line, errors);
    }
}
