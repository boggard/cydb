package com.github.boggard.cydb.parser;

import com.github.boggard.cydb.SqlParser;
import com.github.boggard.cydb.SqlParserBaseListener;
import com.github.boggard.cydb.model.Column;
import com.github.boggard.cydb.model.ForeignKey;
import com.github.boggard.cydb.model.PrimaryKey;
import com.github.boggard.cydb.model.Table;
import lombok.Getter;
import org.antlr.v4.runtime.RuleContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        SQLParser.walkSql(ctx, new ColumnListener(column));
    }

    @Override
    public void exitColumnDeclaration(SqlParser.ColumnDeclarationContext ctx) {
        table.addColumn(column);
    }

    @Override
    public void enterPrimaryKeyColumnConstraint(SqlParser.PrimaryKeyColumnConstraintContext ctx) {
        PrimaryKey primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            primaryKey = new PrimaryKey();
        }
        primaryKey.addColumn(column);
        table.setPrimaryKey(primaryKey);
    }

    @Override
    public void enterPrimaryKeyTableConstraint(SqlParser.PrimaryKeyTableConstraintContext ctx) {
        List<Column> pkColumns = ctx.indexColumnNames().indexColumnName().stream()
                .map(SqlParser.IndexColumnNameContext::uid)
                .map(RuleContext::getText)
                .map(pkCol -> table.getColumns().stream()
                        .filter(col -> col.getName().equals(pkCol))
                        .findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.setColumns(pkColumns);
        table.setPrimaryKey(primaryKey);
    }

    @Override
    public void enterForeignKeyTableConstraint(SqlParser.ForeignKeyTableConstraintContext ctx) {
        ForeignKey foreignKey = new ForeignKey(ctx.start.getLine());
        List<Column> fkColumns = ctx.indexColumnNames().indexColumnName().stream()
                .map(SqlParser.IndexColumnNameContext::uid)
                .map(RuleContext::getText)
                .map(pkCol -> table.getColumns().stream()
                        .filter(col -> col.getName().equals(pkCol))
                        .findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        foreignKey.setReferenceTable(ctx.referenceDefinition().tableName().getText());
        List<String> rfkColumns = ctx.referenceDefinition().indexColumnNames().indexColumnName().stream()
                .map(SqlParser.IndexColumnNameContext::uid)
                .map(RuleContext::getText)
                .collect(Collectors.toList());
        foreignKey.setColumns(fkColumns);
        foreignKey.setReferenceColumns(rfkColumns);
        table.addForeignKey(foreignKey);
    }
}
