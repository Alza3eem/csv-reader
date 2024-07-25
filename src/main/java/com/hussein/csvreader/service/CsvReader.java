package com.hussein.csvreader.service;

import com.hussein.csvreader.domain.CsvProcessException;
import com.hussein.csvreader.domain.CustomData;
import com.hussein.csvreader.domain.InvalidCsvHeadersException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CsvReader {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final Map<String, Integer> EXPECTED_HEADERS;

    static {
        EXPECTED_HEADERS = new HashMap<>();
        EXPECTED_HEADERS.put("source", 0);
        EXPECTED_HEADERS.put("codeListCode", 0);
        EXPECTED_HEADERS.put("code", 0);
        EXPECTED_HEADERS.put("displayValue", 0);
        EXPECTED_HEADERS.put("longDescription", 0);
        EXPECTED_HEADERS.put("fromDate", 0);
        EXPECTED_HEADERS.put("toDate", 0);
        EXPECTED_HEADERS.put("sortingPriority", 0);
    }

    public List<CustomData> processCsvFile(MultipartFile file) {
        Map<String, Integer> csvHeaders = new HashMap<>();
        List<CustomData> customData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Skip the header line if necessary
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    String[] firstLine = line.split(",");
                    if (!areHeadersValid(firstLine)) {
                        throw new InvalidCsvHeadersException("Headers are not valid: " + line);
                    }
                    for (int i = 0; i < firstLine.length; i++) {
                        csvHeaders.put(firstLine[i], i);
                    }
                    continue;
                }
                String[] fields = line.split(",");
                CustomData dataRecord = new CustomData(fields[csvHeaders.get("source")].trim(),
                        fields[csvHeaders.get("codeListCode")].trim(),
                        fields[csvHeaders.get("code")].trim(),
                        fields[csvHeaders.get("displayValue")].trim(),
                        fields[csvHeaders.get("longDescription")].trim(),
                        fields.length < 6 || fields[csvHeaders.get("fromDate")].trim().isEmpty() ? null : LocalDate.parse(fields[csvHeaders.get("fromDate")], DATE_FORMATTER),
                        fields.length < 7 || fields[csvHeaders.get("toDate")].trim().isEmpty() ? null : LocalDate.parse(fields[csvHeaders.get("toDate")], DATE_FORMATTER),
                        fields.length < 8 || fields[csvHeaders.get("sortingPriority")].trim().isEmpty() ? null : Integer.valueOf(fields[csvHeaders.get("sortingPriority")])
                );
                customData.add(dataRecord);
            }
        } catch (IOException e) {
            log.error("Exception while processing CSV file", e);
            throw new CsvProcessException("Exception while processing CSV file", e);
        }
        return customData;
    }

    private boolean areHeadersValid(String[] headers) {
        if (headers.length != EXPECTED_HEADERS.keySet().size()) {
            return false;
        }
        for (String header : headers) {
            if (EXPECTED_HEADERS.containsKey(header)) {
                EXPECTED_HEADERS.remove(header);
            } else {
                return false;
            }
        }
        return true;
    }
}
