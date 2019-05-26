package com.github.boggard.cydb.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Type {

    private final String type;
    private Integer length;
}
