package com.hussein.csvreader.repository;

import com.hussein.csvreader.domain.CustomDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomDataRepository extends JpaRepository<CustomDataEntity, Long> {

    CustomDataEntity findByCode(String code);
}
