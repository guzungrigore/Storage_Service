package com.faf.storage.service.strategy;

import com.faf.storage.domain.StorageFile;
import com.faf.storage.repository.StorageFileRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

@Component
public class CsvFileStrategy implements FileStrategy {

    private final StorageFileRepository fileRepository;

    public CsvFileStrategy(StorageFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public byte[] generateFile() {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("ID", "Name", "Size", "MIME Type", "Path", "Created By", "Created Date")
                .build();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Writer writer = new OutputStreamWriter(outputStream);
             CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {

            List<StorageFile> files = fileRepository.findAll();

            for (StorageFile file : files) {
                csvPrinter.printRecord(
                        file.getId(),
                        file.getName(),
                        file.getSize(),
                        file.getMimeType(),
                        file.getPath(),
                        file.getCreatedBy(),
                        file.getCreatedDate()
                );
            }

            csvPrinter.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CSV file", e);
        }
    }

    @Override
    public boolean supports(String format) {
        return "csv".equalsIgnoreCase(format);
    }
}
