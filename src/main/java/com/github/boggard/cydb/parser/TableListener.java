package com.github.boggard.cydb.parser;

import com.github.boggard.cydb.SqlBaseListener;
import com.github.boggard.cydb.SqlParser;
import com.github.boggard.cydb.model.Column;
import com.github.boggard.cydb.model.Table;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class TableListener extends SqlBaseListener {

    @Getter
    private final List<Table> tables = new LinkedList<>();

    private Table table;
    private Column column;

    @Override
    public void enterCreate_table_stmt(SqlParser.Create_table_stmtContext ctx) {
        table = new Table(ctx.start.getLine());
    }

    @Override
    public void exitCreate_table_stmt(SqlParser.Create_table_stmtContext ctx) {
        tables.add(table);
    }

    @Override
    public void enterColumn_def(SqlParser.Column_defContext ctx) {
        column = new Column(ctx.start.getLine());
        column.setName(ctx.column_name().getText());
    }

    @Override
    public void exitColumn_def(SqlParser.Column_defContext ctx) {
        table.addColumn(column);
    }

    @Override
    public void enterType_name(SqlParser.Type_nameContext ctx) {
        column.setType(ctx.getText());
    }

    @Override
    public void enterColumn_constraint_not_null(SqlParser.Column_constraint_not_nullContext ctx) {
        column.setNullableModifier(ctx.getText());
    }

    @Override
    public void enterColumn_constraint_null(SqlParser.Column_constraint_nullContext ctx) {
        column.setNullableModifier(ctx.getText());
    }

    @Override
    public void enterColumn_default_value(SqlParser.Column_default_valueContext ctx) {
        column.setDefaultValue(ctx.getText());
    }

    @Override
    public void enterTable_constraint_unique(SqlParser.Table_constraint_uniqueContext ctx) {
        column.setUniqueModifier(ctx.getText());
    }

    @Override
    public void enterQualified_table_name(SqlParser.Qualified_table_nameContext ctx) {
        table.setName(ctx.getText());
    }

    @Override
    public void enterCollation_name(SqlParser.Collation_nameContext ctx) {
        column.setCollationModifier(ctx.getText());
    }
}
