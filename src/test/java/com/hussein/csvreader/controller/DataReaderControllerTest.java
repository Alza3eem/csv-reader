package com.hussein.csvreader.controller;

import com.hussein.csvreader.repository.CustomDataRepository;
import com.hussein.csvreader.service.CsvUploaderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class DataReaderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CsvUploaderService csvUploaderService;
    @Autowired
    private CustomDataRepository customDataRepository;

    @BeforeEach
    public void setup() throws Exception {
        Path csvPath = Paths.get("src/test/resources/exercise.csv");
        byte[] csvBytes = Files.readAllBytes(csvPath);
        MockMultipartFile csvFile = new MockMultipartFile(
                "file",
                "exercise.csv",
                MediaType.TEXT_PLAIN_VALUE,
                csvBytes
        );
        csvUploaderService.processAndStoreCsvData(csvFile);
    }

    @AfterEach
    public void cleanDB() throws Exception {
        customDataRepository.deleteAll();
    }

    @Test
    void testFetchAllData() throws Exception {
        mockMvc.perform(get("/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(18)));
    }

    @Test
    void testFetchByCode() throws Exception {
        mockMvc.perform(get("/code/271636001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source", is("ZIB")))
                .andExpect(jsonPath("$.codeListCode", is("ZIB001")))
                .andExpect(jsonPath("$.code", is("271636001")))
                .andExpect(jsonPath("$.displayValue", is("Polsslag regelmatig")));
    }
}
