package com.github.boggard.cydb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PrimaryKey extends StructureElement {
    private List<String> columns;
    private boolean isAutoIncrement;

    public PrimaryKey(int line) {
        super(line);
    }
}
