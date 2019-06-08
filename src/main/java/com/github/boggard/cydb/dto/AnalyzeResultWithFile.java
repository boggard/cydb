package com.github.boggard.cydb.dto;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class AnalyzeResultWithFile {
    private int vulnerableCount = 0;
    private int bugsCount = 0;
    private int smellsCount = 0;
    private List<FileLine> lines = new LinkedList<>();

    public static AnalyzeResultWithFile fromAnalyzeResult(List<String> fileLines, AnalyzeResult analyzeResult) {
        AnalyzeResultWithFile analyzeResultWithFile = new AnalyzeResultWithFile();
        analyzeResultWithFile.setVulnerableCount(analyzeResult.getVulnerableCount());
        analyzeResultWithFile.setBugsCount(analyzeResult.getBugsCount());
        analyzeResultWithFile.setSmellsCount(analyzeResult.getSmellsCount());
        for (int i = 0; i < fileLines.size(); i++) {
            analyzeResultWithFile.lines.add(new FileLine(i, fileLines.get(i), analyzeResult.getLinesError().get(i + 1)));
        }
        return analyzeResultWithFile;
    }
}
