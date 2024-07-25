package com.hussein.csvreader.domain;

import java.time.LocalDate;

public record CustomData(

        String source,
        String codeListCode,
        String code,
        String displayValue,
        String longDescription,
        LocalDate fromDate,
        LocalDate toDate,
        Integer sortingPriority
) {
}
