package com.hussein.csvreader.service;

import com.hussein.csvreader.domain.CustomData;
import com.hussein.csvreader.domain.CustomDataEntity;
import com.hussein.csvreader.repository.CustomDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvUploaderService {

    private final CsvReader csvReader;
    private final CustomDataRepository customDataRepository;

    public void processAndStoreCsvData(MultipartFile file) {
        List<CustomData> customDataList = csvReader.processCsvFile(file);
        List<CustomDataEntity> customDataEntities = customDataList.stream().map(customData ->
                new CustomDataEntity(customData.source(),
                        customData.codeListCode(),
                        customData.code(),
                        customData.displayValue(),
                        customData.longDescription(),
                        customData.fromDate(),
                        customData.toDate(),
                        customData.sortingPriority()
                )).toList();
        try {
            customDataRepository.saveAll(customDataEntities);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Duplicate code value found", e);
        }
    }
}
