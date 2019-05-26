package com.github.boggard.cydb.parser;

import com.github.boggard.cydb.SqlParser;
import com.github.boggard.cydb.SqlParserBaseListener;
import com.github.boggard.cydb.model.statement.casestatement.CaseResult;
import com.github.boggard.cydb.model.statement.casestatement.CaseStatement;
import com.github.boggard.cydb.model.statement.casestatement.ResultType;
import com.github.boggard.cydb.model.statement.casestatement.ValueType;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class CaseStatementListener extends SqlParserBaseListener {

    @Getter
    private List<CaseStatement> caseStatements = new LinkedList<>();

    private CaseStatement caseStatement;

    @Override
    public void enterCaseFunctionCall(SqlParser.CaseFunctionCallContext ctx) {
        caseStatement = new CaseStatement(ctx.start.getLine());
        ctx.caseFuncAlternative().forEach(this::enterCaseFunctionAlternative);
        enterFunctionArgContext(ctx.elseArg);
    }

    private void enterCaseFunctionAlternative(SqlParser.CaseFuncAlternativeContext ac) {
        SqlParser.FunctionArgContext result = ac.consequent;
        enterFunctionArgContext(result);
    }

    private void enterFunctionArgContext(SqlParser.FunctionArgContext result) {
        SqlParser.ConstantContext constant = result.constant();
        SqlParser.FullColumnNameContext columnName = result.fullColumnName();
        SqlParser.FunctionCallContext functionCall = result.functionCall();
        if (constant != null) {
            if (constant.stringLiteral() != null) {
                caseStatement.addResult(new CaseResult(constant.stringLiteral().getText(),
                        ResultType.CONSTANT, ValueType.STRING));
            } else if (constant.decimalLiteral() != null) {
                caseStatement.addResult(new CaseResult(constant.decimalLiteral().getText(),
                        ResultType.CONSTANT, ValueType.DECIMAL));
            } else if (constant.booleanLiteral() != null) {
                caseStatement.addResult(new CaseResult(constant.booleanLiteral().getText(),
                        ResultType.CONSTANT, ValueType.BOOLEAN));
            }
        } else if(columnName != null) {
            caseStatement.addResult(new CaseResult(columnName.uid().getText(),
                    ResultType.COLUMN, null));
        } else if(functionCall != null) {
            caseStatement.addResult(new CaseResult(functionCall.getText(),
                    ResultType.FUNCTION_CALL, null));
        }
    }

    @Override
    public void exitCaseFunctionCall(SqlParser.CaseFunctionCallContext ctx) {
        caseStatements.add(caseStatement);
    }
}
