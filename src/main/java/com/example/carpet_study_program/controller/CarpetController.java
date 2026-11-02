package com.example.carpet_study_program.controller;

import com.example.carpet_study_program.service.CollectionParserService;
import com.example.carpet_study_program.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/carpets")
@Slf4j
public class CarpetController {
    private final CollectionParserService collectionParserService;
    private final ExcelService excelService;

    public CarpetController(CollectionParserService collectionParserService, ExcelService excelService) {
        this.collectionParserService = collectionParserService;
        this.excelService = excelService;
    }

    @PostMapping("/parse")
    public void getProducts() throws IOException {
        log.info("Parsing started");
        collectionParserService.getElementsFromSite();
        log.info("Completed successfully!");
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToExcel() {
        byte[] excelBytes = excelService.getExcelFile();
        String fileName = "All_Collections_Ami_Kovry" + LocalDate.now() + ".xlsx";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}
