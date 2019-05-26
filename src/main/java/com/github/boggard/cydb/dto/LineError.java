package com.github.boggard.cydb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LineError {

    private String ruleId;
    private String message;
}
