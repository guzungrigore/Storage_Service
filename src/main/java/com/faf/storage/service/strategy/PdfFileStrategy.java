package com.faf.storage.service.strategy;

import com.faf.storage.domain.StorageFile;
import com.faf.storage.repository.StorageFileRepository;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class PdfFileStrategy implements FileStrategy {

    private final StorageFileRepository fileRepository;

    public PdfFileStrategy(StorageFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public byte[] generateFile() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4.rotate());

            float[] columnWidths = {1, 3, 2, 2, 4, 3, 3};
            Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
            table.setAutoLayout();

            table.addHeaderCell("ID");
            table.addHeaderCell("Name");
            table.addHeaderCell("Size");
            table.addHeaderCell("MIME Type");
            table.addHeaderCell("Path");
            table.addHeaderCell("Created By");
            table.addHeaderCell("Created Date");

            List<StorageFile> files = fileRepository.findAll();

            for (StorageFile file : files) {
                table.addCell(file.getId().toString());
                table.addCell(file.getName());
                table.addCell(file.getSize().toString());
                table.addCell(file.getMimeType());
                table.addCell(file.getPath());
                table.addCell(file.getCreatedBy());
                table.addCell(file.getCreatedDate().toString());
            }

            document.add(table);

            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF file", e);
        }
    }

    @Override
    public boolean supports(String format) {
        return "pdf".equalsIgnoreCase(format);
    }
}
