package com.github.boggard.cydb.model.statement.altertable;

import com.github.boggard.cydb.model.StructureElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AlterTableStatement extends StructureElement {

    private String tableName;

    public AlterTableStatement(int line) {
        super(line);
    }
}
