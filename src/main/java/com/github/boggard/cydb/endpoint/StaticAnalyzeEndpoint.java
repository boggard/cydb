package com.github.boggard.cydb.endpoint;

import com.github.boggard.cydb.service.StaticAnalyzer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/analyze")
public class StaticAnalyzeEndpoint {

    private final StaticAnalyzer staticAnalyzer;

    @PostMapping
    public Collection<String> analyze(@RequestPart("file") MultipartFile file) {
        return staticAnalyzer.analyze(file);
    }
}
