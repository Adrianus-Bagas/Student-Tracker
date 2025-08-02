package com.tracker.student.service;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.DocumentException;

public interface PdfGeneratorService {

	public ByteArrayOutputStream generatePdf(String studentId) throws DocumentException;

}
