package com.github.boggard.cydb.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class PrimaryKey {
    private List<Column> columns = new LinkedList<>();
    private boolean isAutoIncrement;

    public void addColumn(Column column) {
        columns.add(column);
    }
}
