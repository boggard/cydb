package com.github.boggard.cydb.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Table {
    private String name;
    private List<Column> columns = new LinkedList<>();
    private List<ForeignKey> foreignKeys = new LinkedList<>();
    private PrimaryKey primaryKey;

    public void addColumn(Column column) {
        this.columns.add(column);
    }
}
