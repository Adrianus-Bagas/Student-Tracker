package com.tracker.student.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.tracker.student.entity.Student;
import com.tracker.student.entity.Teacher;
import com.tracker.student.exception.BadRequestException;
import com.tracker.student.repository.StudentRepository;
import com.tracker.student.repository.TeacherRepository;
import com.tracker.student.service.PdfGeneratorService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;

	@Override
	public ByteArrayOutputStream generatePdf(String studentId) throws DocumentException {
		Student student = studentRepository.findBySecureId(studentId)
				.orElseThrow(() -> new BadRequestException("Siswa tidak ditemukan"));
		Teacher teacher = teacherRepository.findByTeacherClass(student.getStudentClass())
				.orElseThrow(() -> new BadRequestException("Guru tidak ditemukan"));
		Document document = new Document(PageSize.B5);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, outputStream);

		Paragraph header = new Paragraph("E-Rapor",
				new Font(FontFamily.HELVETICA, 18, Font.BOLDITALIC, new BaseColor(0, 0, 255)));
		Paragraph name = new Paragraph("Nama: " + student.getUser().getName());
		Paragraph studentClass = new Paragraph("Kelas: " + student.getStudentClass().getName());

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(40);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.getDefaultCell().setPadding(10);
		table.addCell(new Phrase("Pelajaran",
				FontFactory.getFont(FontFactory.defaultEncoding, 12, Font.BOLD, new BaseColor(0, 0, 0))));
		table.addCell(new Phrase("Nilai Akhir",
				FontFactory.getFont(FontFactory.defaultEncoding, 12, Font.BOLD, new BaseColor(0, 0, 0))));

		for (int i = 1; i <= student.getResults().size(); i++) {
			table.addCell(student.getResults().get(i - 1).getSubject().getName());
			table.addCell(String.valueOf(student.getResults().get(i - 1).getMark()));
		}

		Paragraph teacherClass = new Paragraph("Tertanda Wali Kelas");
		teacherClass.setAlignment(Element.ALIGN_RIGHT);

		LocalDateTime localDateTime = LocalDateTime.now();
		String pattern = "dd-MM-yyyy";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		String formattedDateTime = localDateTime.format(formatter);
		Paragraph date = new Paragraph(formattedDateTime);
		date.setAlignment(Element.ALIGN_RIGHT);

		Paragraph teacherName = new Paragraph(teacher.getUser().getName());
		teacherName.setAlignment(Element.ALIGN_RIGHT);

		document.open();
		document.addTitle("E-Rapor - " + student.getUser().getName());
		document.add(header);
		document.add(name);
		document.add(studentClass);
		document.add(new Paragraph("\n"));
		document.add(table);
		for (int i = 0; i < 3; i++) {
			document.add(new Paragraph("\n"));
		}
		document.add(teacherClass);
		document.add(date);
		for (int i = 0; i < 3; i++) {
			document.add(new Paragraph("\n"));
		}
		document.add(teacherName);
		document.close();

		return outputStream;
	}

}
