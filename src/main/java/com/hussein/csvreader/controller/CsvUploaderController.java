package com.hussein.csvreader.controller;

import com.hussein.csvreader.service.CsvUploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class CsvUploaderController {

    private final CsvUploaderService csvUploaderService;

    @PostMapping("/upload")
    public ResponseEntity<HttpStatus> uploadCsv(
            @RequestParam("file") MultipartFile file) {
        csvUploaderService.processAndStoreCsvData(file);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
