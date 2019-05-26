package com.github.boggard.cydb.model.statement.altertable;

import com.github.boggard.cydb.model.ColumnDefinition;
import com.github.boggard.cydb.model.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddColumnStatement extends AlterTableStatement implements ColumnDefinition {
    private String name;
    private Type type;
    private String nullableModifier;
    private String uniqueModifier;
    private String defaultValue;
    private String collationModifier;

    public AddColumnStatement(int line) {
        super(line);
    }
}
