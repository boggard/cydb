package com.github.boggard.cydb.parser;

import com.github.boggard.cydb.SqlBaseListener;
import com.github.boggard.cydb.SqlParser;
import com.github.boggard.cydb.model.Column;
import com.github.boggard.cydb.model.Table;
import lombok.Getter;

public class TableListener extends SqlBaseListener {

    @Getter
    private Table table;
    @Getter
    private Column column;

    @Override
    public void enterCreate_table_stmt(SqlParser.Create_table_stmtContext ctx) {
        table = new Table();
    }

    @Override
    public void enterColumn_def(SqlParser.Column_defContext ctx) {
        column = new Column();
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
    public void enterCollation_name(SqlParser.Collation_nameContext ctx) {
        column.setCollationModifier(ctx.getText());
    }
}
