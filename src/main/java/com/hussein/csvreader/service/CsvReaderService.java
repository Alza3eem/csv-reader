package com.hussein.csvreader.service;

import com.hussein.csvreader.domain.CustomData;
import com.hussein.csvreader.domain.CustomDataEntity;
import com.hussein.csvreader.repository.CustomDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvReaderService {

    private final CustomDataRepository customDataRepository;

    public List<CustomData> fetchAllData() {
        List<CustomDataEntity> allData = customDataRepository.findAll();
        return allData.stream().map(entity -> new CustomData(
                entity.getSource(),
                entity.getCodeListCode(),
                entity.getCode(),
                entity.getDisplayValue(),
                entity.getLongDescription(),
                entity.getFromDate(),
                entity.getToDate(),
                entity.getSortingPriority()
        )).toList();
    }

    public CustomData fetchByCode(String code) {
        CustomDataEntity entity = customDataRepository.findByCode(code);
        return new CustomData(
                entity.getSource(),
                entity.getCodeListCode(),
                entity.getCode(),
                entity.getDisplayValue(),
                entity.getLongDescription(),
                entity.getFromDate(),
                entity.getToDate(),
                entity.getSortingPriority());
    }
}
