package com.github.boggard.cydb.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class FileInputStreamSource implements InputStreamSource {

    private final File file;

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }
}
