package com.github.boggard.cydb.model;

import lombok.Data;

@Data
public class Column {
    private String name;
    private String type;
    private String nullableModifier;
    private String uniqueModifier;
    private String defaultValue;
    private String collationModifier;
}
