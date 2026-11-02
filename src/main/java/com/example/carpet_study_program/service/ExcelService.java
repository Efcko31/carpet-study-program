package com.example.carpet_study_program.service;


import com.example.carpet_study_program.entity.CarpetProduct;
import com.example.carpet_study_program.repository.CarpetRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ExcelService {
    private final CarpetRepository carpetRepository;

    public ExcelService(CarpetRepository carpetRepository) {
        this.carpetRepository = carpetRepository;
    }

    public byte[] getExcelFile() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        LocalDate today = LocalDate.now();
        XSSFSheet sheet = workbook.createSheet("Ami Kovry " + today);
        String[] columnNames = new String[]{"Бренд", "Коллекция", "Страна бренда", "Страна производства", "Материал",
                "Состав ворса", "Состав основы", "Тип производства", "Плотность ворса", "Высота ворса или толщина",
                "Форма ковра", "Особенности", "Вес на метр вкадратный"};

        List<CarpetProduct> bookData = carpetRepository.findAll();

        log.info("Скачал данные с БД. Всего {} ковров", bookData.size());

        int rowNum = 0;

        int colNamesNum = 0;
        Row rowNames = sheet.createRow(rowNum++);
        for (String n : columnNames) {
            Cell cellNames = rowNames.createCell(colNamesNum++);
            cellNames.setCellValue(n);
        }

        for (CarpetProduct dto : bookData) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            String[] values = toArrayStringFromDto(dto);
            if (values != null) {
                for (String s : values) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(s != null ? s : "");
                }
            }
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            log.info("Успешно сохранено!");
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String[] toArrayStringFromDto(CarpetProduct entity) {
        return new String[]{
                entity.getBrand(),
                entity.getCollection(),
                entity.getCountryOfBrand(),
                entity.getCountryOfManufacture(),
                entity.getMaterial(),
                entity.getPileComposition(),
                entity.getCompositionBase(), entity.getTypeProduction(), entity.getPileDensity(),
                entity.getPileHeightOrThickness(), entity.getCarpetShape(), entity.getFeatures(),
                entity.getWeightPerSquareMeter()};
    }
}
