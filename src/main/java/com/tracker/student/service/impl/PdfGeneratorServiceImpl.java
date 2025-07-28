package com.tracker.student.service.impl;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.tracker.student.service.PdfGeneratorService;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

	@Override
	public ByteArrayOutputStream generatePdf() throws DocumentException {
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, outputStream);

		document.open();
		document.addTitle("E-Report");
		document.add(new Paragraph("Hello, this is a generated PDF from Spring Boot!"));
		// Add more content like tables, images, etc.
		document.close();

		return outputStream;
	}

}
