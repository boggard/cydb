package com.github.boggard.cydb.model;

import lombok.Data;

@Data
public class ForeignKey {
    private String column;
    private String referenceTable;
    private String referenceColumn;
}
