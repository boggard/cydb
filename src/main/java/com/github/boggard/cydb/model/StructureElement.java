package com.github.boggard.cydb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StructureElement implements Element {

    private final int line;
}
