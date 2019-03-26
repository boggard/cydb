package com.github.boggard.cydb.model;

import lombok.Data;

import java.util.List;

@Data
public class PrimaryKey {
    private List<String> columns;
    private boolean isAutoIncrement;
}
