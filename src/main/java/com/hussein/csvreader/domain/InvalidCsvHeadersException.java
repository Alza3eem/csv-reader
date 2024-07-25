package com.hussein.csvreader.domain;

public class InvalidCsvHeadersException extends RuntimeException {

    public InvalidCsvHeadersException(String s) {
        super(s);
    }
}
