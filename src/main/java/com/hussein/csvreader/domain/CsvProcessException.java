package com.hussein.csvreader.domain;

import java.io.IOException;

public class CsvProcessException extends RuntimeException {

    public CsvProcessException(String s, IOException e) {
        super(s, e);
    }
}
