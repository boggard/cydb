package com.github.boggard.cydb.dto;

import lombok.Value;

import java.util.List;

@Value
public class FileLine {

    private int number;
    private String text;
    private List<LineError> errors;
}
