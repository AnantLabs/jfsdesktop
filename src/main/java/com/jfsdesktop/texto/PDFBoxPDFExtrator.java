package com.jfsdesktop.texto;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

/**
 * <P>
 * Extrator para arquivos PDF.
 * <P>
 * Usa a biblioteca PDFBox.
 */
public class PDFBoxPDFExtrator implements Extrator {

	public Reader getText(InputStream is) throws ExtratorException {

		COSDocument cosDoc = null;
		try {
			cosDoc = parseDocument(is);
		} catch (IOException e) {
			try {
				closeCOSDocument(cosDoc);
			} catch (IOException ignorada) {
			}
			throw new ExtratorException("IOException em parseDocument(): "
					+ e.getMessage(), e);
		}

		String docText = null;
		try {
			PDFTextStripper stripper = new PDFTextStripper();
			docText = stripper.getText(new PDDocument(cosDoc));
		} catch (IOException e) {
			throw new ExtratorException("IOException em getText(): "
					+ e.getMessage(), e);
		} finally {
			try {
				closeCOSDocument(cosDoc);
			} catch (IOException ignorada) {
			}
		}
		return new StringReader(docText);
	}

	private static COSDocument parseDocument(InputStream is) throws IOException {
		PDFParser parser = new PDFParser(is);
		parser.parse();
		return parser.getDocument();
	}

	private void closeCOSDocument(COSDocument cosDoc) throws IOException {
		if (cosDoc != null) {
			cosDoc.close();
		}
	}

}
