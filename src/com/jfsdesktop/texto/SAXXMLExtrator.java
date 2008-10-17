package com.jfsdesktop.texto;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

/** <P>Extrator para arquivos XML.
 *  <P>Usa a biblioteca SAX.
 */
public class SAXXMLExtrator extends DefaultHandler implements Extrator {

	static StringBuffer buffer = new StringBuffer();
	
	public Reader getText(InputStream is) throws ExtratorException {
		StringReader sReader = null;

		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = spf.newSAXParser();
			parser.parse(is, this);
			sReader = new StringReader(buffer.toString()); 			
		} catch (Exception e) {
			throw new ExtratorException("Erro extraindo texto de XML: " + e.getMessage(),e);					
		} 
		
		return sReader;
	}


	public void characters(char[] text, int start, int length) {
		buffer.append(text, start, length);
	}

}
