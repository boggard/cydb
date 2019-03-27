package com.github.boggard.cydb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ForeignKey extends StructureElement {
    private String column;
    private String referenceTable;
    private String referenceColumn;

    public ForeignKey(int line) {
        super(line);
    }
}
