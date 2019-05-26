package com.github.boggard.cydb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Column extends StructureElement {
    private String name;
    private Type type;
    private String nullableModifier;
    private String uniqueModifier;
    private String defaultValue;
    private String collationModifier;

    public Column(int line) {
        super(line);
    }
}
