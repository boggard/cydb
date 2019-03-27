package com.github.boggard.cydb.dto;

import lombok.Value;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Value
public class AnalyzeResult {

    private Map<Integer, List<String>> linesError = new HashMap<>();

    public void putError(int line, String error) {
        List<String> errors = linesError.getOrDefault(line, new LinkedList<>());
        errors.add(error);
        linesError.put(line, errors);
    }
}
