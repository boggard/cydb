package com.github.boggard.cydb.parser;

import com.github.boggard.cydb.SqlParser;
import com.github.boggard.cydb.SqlParserBaseListener;
import com.github.boggard.cydb.model.Column;
import com.github.boggard.cydb.model.statement.altertable.AddColumnStatement;
import com.github.boggard.cydb.model.statement.altertable.AlterTableStatement;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class AlterTableStatementListener extends SqlParserBaseListener {

    @Getter
    private List<AlterTableStatement> statements = new LinkedList<>();
    private AlterTableStatement alterTableStatement;
    private Column column;

    @Override
    public void enterAlterByAddColumn(SqlParser.AlterByAddColumnContext ctx) {
        column = new Column(ctx.start.getLine());
        column.setName(ctx.uid(0).getText());
        SQLParser.walkSql(ctx, new ColumnListener(column));
    }

    @Override
    public void exitAlterByAddColumn(SqlParser.AlterByAddColumnContext ctx) {
        AddColumnStatement addColumnStatement = new AddColumnStatement(ctx.start.getLine());
        addColumnStatement.setName(column.getName());
        addColumnStatement.setType(column.getType());
        addColumnStatement.setUniqueModifier(column.getUniqueModifier());
        addColumnStatement.setDefaultValue(column.getDefaultValue());
        addColumnStatement.setCollationModifier(column.getCollationModifier());
        addColumnStatement.setNullableModifier(column.getNullableModifier());
        alterTableStatement = addColumnStatement;
    }

    @Override
    public void exitAlterTable(SqlParser.AlterTableContext ctx) {
        alterTableStatement.setTableName(ctx.tableName().getText());
        statements.add(alterTableStatement);
    }
}
