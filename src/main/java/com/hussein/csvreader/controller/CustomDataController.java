package com.hussein.csvreader.controller;

import com.hussein.csvreader.domain.CustomData;
import com.hussein.csvreader.service.CsvReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomDataController {

    private final CsvReaderService csvReaderService;

    @GetMapping("/all")
    public List<CustomData> fetchAllData() {
        return csvReaderService.fetchAllData();
    }

    @GetMapping("/code/{code}")
    public CustomData fetchByCode(@PathVariable String code) {
        return csvReaderService.fetchByCode(code);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteAllData() {
        csvReaderService.deleteAllData();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
