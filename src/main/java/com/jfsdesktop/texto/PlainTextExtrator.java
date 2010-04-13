package com.jfsdesktop.texto;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/** <P>Extrator para texto puro. */
public class PlainTextExtrator implements Extrator {
	public Reader getText(InputStream is) throws ExtratorException {
		return new InputStreamReader(is);
	}
}
