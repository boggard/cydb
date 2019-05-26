package com.github.boggard.cydb.parser;

import com.github.boggard.cydb.SqlParser;
import com.github.boggard.cydb.SqlParserBaseListener;
import com.github.boggard.cydb.model.Column;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnListener extends SqlParserBaseListener {

    private final Column column;

    @Override
    public void enterColumnDefinition(SqlParser.ColumnDefinitionContext ctx) {
        DataTypeListener dataTypeListener = new DataTypeListener();
        SQLParser.walkSql(ctx.dataType(), dataTypeListener);
        column.setType(dataTypeListener.getType());
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
