package com.github.boggard.cydb.parser;

import com.github.boggard.cydb.SqlParser;
import com.github.boggard.cydb.SqlParserBaseListener;
import com.github.boggard.cydb.model.Column;
import com.github.boggard.cydb.model.Table;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class TableListener extends SqlParserBaseListener {

    @Getter
    private final List<Table> tables = new LinkedList<>();

    private Table table;
    private Column column;

    @Override
    public void enterColumnCreateTable(SqlParser.ColumnCreateTableContext ctx) {
        table = new Table(ctx.start.getLine());
        table.setName(ctx.tableName().getText());
    }

    @Override
    public void exitColumnCreateTable(SqlParser.ColumnCreateTableContext ctx) {
        tables.add(table);
    }

    @Override
    public void enterColumnDeclaration(SqlParser.ColumnDeclarationContext ctx) {
        column = new Column(ctx.start.getLine());
        column.setName(ctx.uid().getText());
    }

    @Override
    public void exitColumnDeclaration(SqlParser.ColumnDeclarationContext ctx) {
        table.addColumn(column);
    }

    @Override
    public void enterColumnDefinition(SqlParser.ColumnDefinitionContext ctx) {
        column.setType(ctx.dataType().getText());
    }

    @Override
    public void enterNullColumnConstraint(SqlParser.NullColumnConstraintContext ctx) {
        column.setNullableModifier(ctx.nullNotnull().getText());
    }

    @Override
    public void enterDefaultColumnConstraint(SqlParser.DefaultColumnConstraintContext ctx) {
        column.setDefaultValue(ctx.defaultValue().getText());
    }

    @Override
    public void enterUniqueKeyColumnConstraint(SqlParser.UniqueKeyColumnConstraintContext ctx) {
        column.setUniqueModifier(ctx.getText());
    }

    @Override
    public void enterCollateColumnConstraint(SqlParser.CollateColumnConstraintContext ctx) {
        column.setCollationModifier(ctx.collationName().getText());
    }
}
