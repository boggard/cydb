package com.github.boggard.cydb.parser;

import com.github.boggard.cydb.SqlLexer;
import com.github.boggard.cydb.SqlListener;
import com.github.boggard.cydb.SqlParser;
import lombok.experimental.UtilityClass;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.core.io.InputStreamSource;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class SQLParser {

    public void parseSql(InputStreamSource inputStreamSource, SqlListener sqlListener) throws IOException {
        try (InputStream inputStream = inputStreamSource.getInputStream()) {
            ANTLRInputStream antlrInputStream = new ANTLRInputStream(inputStream);

            SqlLexer sqlLexer = new SqlLexer(antlrInputStream);
            CommonTokenStream tokenStream = new CommonTokenStream(sqlLexer);
            SqlParser sqlParser = new SqlParser(tokenStream);

            ParseTree parseTree = sqlParser.parse();

            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(sqlListener, parseTree);
        }
    }
}
