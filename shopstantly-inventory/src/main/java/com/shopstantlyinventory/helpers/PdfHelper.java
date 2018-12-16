package com.shopstantlyinventory.helpers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lib.Network.Buffers.InventoryBuffer;
import com.lib.Network.Buffers.ProductBuffer;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class PdfHelper {
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private byte[] pdfBytes;

    private static void addMetaData(Document document) {
        document.addTitle("PackingSlip");
        document.addSubject("PackingSlip");
        document.addKeywords("PackingSlip, PDF");
        document.addAuthor("HydraGroup");
        document.addCreator("HydraGroup");
    }

    private static void addContent(Document document, InventoryBuffer inventoryBuffer) throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("PackingSlip", catFont));
        addEmptyLine(preface, 2);
        document.add(preface);
        createTable(document, inventoryBuffer);
    }

    private static void createTable(Document document, InventoryBuffer inventoryBuffer) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Product"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Amount"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        for (ProductBuffer product : inventoryBuffer.productBufferList) {
            PdfPCell c = new PdfPCell(new Phrase(product.name));
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c);

            c.setPhrase(new Phrase(product.orderAmount + ""));
            table.addCell(c);

            c.setPhrase(new Phrase(product.price + " CHF"));
            table.addCell(c);
        }
        document.add(table);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public ByteArrayOutputStream createPdf(InventoryBuffer inventoryBuffer) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            addMetaData(document);
            addContent(document, inventoryBuffer);
            document.close();
            System.out.println("PackingSlip created.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos;
    }
}