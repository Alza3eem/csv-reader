package com.hussein.csvreader.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CsvUploaderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUploadCSVFile() throws Exception {
        Path csvPath = Paths.get("src/test/resources/exercise.csv");
        byte[] csvBytes = Files.readAllBytes(csvPath);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "exercise.csv",
                MediaType.TEXT_PLAIN_VALUE,
                csvBytes
        );

        mockMvc.perform(multipart("/upload")
                        .file(file))
                .andExpect(status().isOk());
    }
}
