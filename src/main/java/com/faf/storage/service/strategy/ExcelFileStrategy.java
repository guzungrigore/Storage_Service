package com.faf.storage.service.strategy;

import com.faf.storage.domain.StorageFile;
import com.faf.storage.repository.StorageFileRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelFileStrategy implements FileStrategy {

    private final StorageFileRepository fileRepository;

    public ExcelFileStrategy(StorageFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public byte[] generateFile() {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Storage Files");
            List<StorageFile> files = fileRepository.findAll();

            Row headerRow = sheet.createRow(0);
            createCell(headerRow, 0, "ID");
            createCell(headerRow, 1, "Name");
            createCell(headerRow, 2, "Size");
            createCell(headerRow, 3, "MIME Type");
            createCell(headerRow, 4, "Path");
            createCell(headerRow, 5, "Created By");
            createCell(headerRow, 6, "Created Date");
            createCell(headerRow, 7, "User ID");

            int rowNum = 1;
            for (StorageFile file : files) {
                Row row = sheet.createRow(rowNum++);
                createCell(row, 0, file.getId().toString());
                createCell(row, 1, file.getName());
                createCell(row, 2, file.getSize().toString());
                createCell(row, 3, file.getMimeType());
                createCell(row, 4, file.getPath());
                createCell(row, 5, file.getCreatedBy());
                createCell(row, 6, file.getCreatedDate().toString());
                createCell(row, 7, file.getUser().getId().toString());
            }

            // Auto-size columns
            for (int i = 0; i < 8; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(bos);
            return bos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel report", e);
        }
    }

    private void createCell(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
    }

    @Override
    public boolean supports(String format) {
        return "excel".equalsIgnoreCase(format);
    }
}
