package com.github.boggard.cydb.dto;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class AnalyzeResultWithFile {
    private List<FileLine> lines = new LinkedList<>();

    public static AnalyzeResultWithFile fromAnalyzeResult(List<String> fileLines, AnalyzeResult analyzeResult) {
        AnalyzeResultWithFile analyzeResultWithFile = new AnalyzeResultWithFile();
        for (int i = 0; i < fileLines.size(); i++) {
            analyzeResultWithFile.lines.add(new FileLine(i, fileLines.get(i), analyzeResult.getLinesError().get(i + 1)));
        }
        return analyzeResultWithFile;
    }
}
