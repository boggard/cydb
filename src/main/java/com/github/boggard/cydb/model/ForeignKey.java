package com.github.boggard.cydb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ForeignKey extends StructureElement {
    private List<Column> columns;
    private String referenceTable;
    private List<String> referenceColumns;

    public ForeignKey(int line) {
        super(line);
    }
}
