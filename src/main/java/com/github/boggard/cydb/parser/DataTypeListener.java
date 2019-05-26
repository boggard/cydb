package com.github.boggard.cydb.parser;

import com.github.boggard.cydb.SqlParser;
import com.github.boggard.cydb.SqlParserBaseListener;
import com.github.boggard.cydb.model.Type;
import lombok.Getter;
import org.antlr.v4.runtime.ParserRuleContext;

public class DataTypeListener extends SqlParserBaseListener {

    @Getter
    private Type type;

    @Override
    public void enterStringDataType(SqlParser.StringDataTypeContext ctx) {
        SqlParser.LengthOneDimensionContext lengthOneDimensionContext = ctx.lengthOneDimension();
        enterVaryingDataType(ctx, lengthOneDimensionContext);
    }

    @Override
    public void enterNationalStringDataType(SqlParser.NationalStringDataTypeContext ctx) {
        SqlParser.LengthOneDimensionContext lengthOneDimensionContext = ctx.lengthOneDimension();
        enterVaryingDataType(ctx, lengthOneDimensionContext);
    }

    @Override
    public void enterNationalVaryingStringDataType(SqlParser.NationalVaryingStringDataTypeContext ctx) {
        SqlParser.LengthOneDimensionContext lengthOneDimensionContext = ctx.lengthOneDimension();
        enterVaryingDataType(ctx, lengthOneDimensionContext);
    }

    @Override
    public void enterDimensionDataType(SqlParser.DimensionDataTypeContext ctx) {
        SqlParser.LengthOneDimensionContext lengthOneDimensionContext = ctx.lengthOneDimension();
        enterVaryingDataType(ctx, lengthOneDimensionContext);
    }

    @Override
    public void enterSimpleDataType(SqlParser.SimpleDataTypeContext ctx) {
        enterDataType(ctx);
    }

    @Override
    public void enterCollectionDataType(SqlParser.CollectionDataTypeContext ctx) {
        enterDataType(ctx);
    }

    @Override
    public void enterSpatialDataType(SqlParser.SpatialDataTypeContext ctx) {
        enterDataType(ctx);
    }

    @Override
    public void enterConvertedDataType(SqlParser.ConvertedDataTypeContext ctx) {
        SqlParser.LengthOneDimensionContext lengthOneDimensionContext = ctx.lengthOneDimension();
        enterVaryingDataType(ctx, lengthOneDimensionContext);
    }

    private void enterVaryingDataType(ParserRuleContext ctx, SqlParser.LengthOneDimensionContext lengthOneDimensionContext) {
        enterDataType(ctx);
        if (lengthOneDimensionContext != null) {
            int length = Integer.parseInt(lengthOneDimensionContext.decimalLiteral().getText());
            type.setLength(length);
        }
    }

    private void enterDataType(ParserRuleContext ctx) {
        type = new Type(ctx.getChild(0).getText());
    }
}
