package org.jpmc.awm.tcoe.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
public class ExcelHelper {
    private String filePath;
    private Workbook workbook;

    public ExcelHelper(String filePath) {
        this.filePath = filePath;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to open Excel file: " + filePath, e);
        }
    }

    public String getCellData(String sheetName, int rowNum, int colNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return null;

        Row row = sheet.getRow(rowNum);
        if (row == null) return null;

        Cell cell = row.getCell(colNum);
        return getCellValueAsString(cell);
    }

    public List<Map<String, String>> getSheetDataAsList(String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return dataList;

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return dataList;

        int colCount = headerRow.getPhysicalNumberOfCells();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Map<String, String> dataMap = new HashMap<>();
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                String key = headerRow.getCell(j).getStringCellValue();
                String value = getCellValueAsString(cell);
                dataMap.put(key, value);
            }
            dataList.add(dataMap);
        }

        return dataList;
    }

    public void setCellData(String sheetName, int rowNum, int colNum, String value) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) sheet = workbook.createSheet(sheetName);

        Row row = sheet.getRow(rowNum);
        if (row == null) row = sheet.createRow(rowNum);

        Cell cell = row.getCell(colNum);
        if (cell == null) cell = row.createCell(colNum);

        cell.setCellValue(value);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to Excel file: " + filePath, e);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    public void close() {
        try {
            if (workbook != null) workbook.close();
        } catch (IOException e) {
            // Ignore
        }
    }
}
