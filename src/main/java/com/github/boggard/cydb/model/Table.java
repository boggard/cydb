package com.github.boggard.cydb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Table extends StructureElement {
    private String name;
    private List<Column> columns = new LinkedList<>();
    private List<ForeignKey> foreignKeys = new LinkedList<>();
    private PrimaryKey primaryKey;

    public Table(int line) {
        super(line);
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }
}
