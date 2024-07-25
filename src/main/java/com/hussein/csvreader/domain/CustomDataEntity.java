package com.hussein.csvreader.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class CustomDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String source;
    String codeListCode;

    @Column(unique = true)
    String code;

    String displayValue;
    String longDescription;
    LocalDate fromDate;
    LocalDate toDate;
    Integer sortingPriority;

    public CustomDataEntity(String source, String codeListCode, String code, String displayValue,
                            String longDescription, LocalDate fromDate, LocalDate toDate, Integer sortingPriority) {
        this.source = source;
        this.codeListCode = codeListCode;
        this.code = code;
        this.displayValue = displayValue;
        this.longDescription = longDescription;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.sortingPriority = sortingPriority;
    }
}
