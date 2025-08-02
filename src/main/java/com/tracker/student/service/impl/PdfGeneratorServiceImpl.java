package com.tracker.student.service.impl;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.tracker.student.entity.Student;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.StudentRepository;
import com.tracker.student.service.PdfGeneratorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

	private final StudentRepository studentRepository;

	@Override
	public ByteArrayOutputStream generatePdf(String studentId) throws DocumentException {
		Student student = studentRepository.findBySecureId(studentId)
				.orElseThrow(() -> new BadRequestException("Siswa tidak ditemukan"));
		Document document = new Document(PageSize.B5);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, outputStream);

		Paragraph header = new Paragraph("E-Report",
				new Font(FontFamily.HELVETICA, 18, Font.BOLDITALIC, new BaseColor(0, 0, 255)));
		Paragraph name = new Paragraph("Name: " + student.getUser().getName());
		Paragraph studentClass = new Paragraph("Class: " + student.getStudentClass().getName());

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(40);
		table.addCell("Subject");
		table.addCell("Final Result");

		for (int i = 1; i <= student.getResults().size(); i++) {
			table.addCell(student.getResults().get(i - 1).getSubject().getName());
			table.addCell(String.valueOf(student.getResults().get(i - 1).getMark()));
		}

		document.open();
		document.addTitle("E-Report - " + student.getUser().getName());
		document.add(header);
		document.add(name);
		document.add(studentClass);
		document.add(new Paragraph("\n"));
		document.add(table);
		document.close();

		return outputStream;
	}

}
