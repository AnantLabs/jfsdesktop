package com.jfsdesktop.texto;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.poi.hdf.extractor.WordDocument;

/** <P>Extrator para arquivos MS Word.
 *  <P>Usa a biblioteca Apache POI. 
 */
public class POIWordExtrator implements Extrator {

  public StringReader getText(InputStream is) throws ExtratorException {
    String bodyText = null;
    StringReader sReader = null;

    try {
      WordDocument wd = new WordDocument(is);
      StringWriter docTextWriter = new StringWriter();
      wd.writeAllText(new PrintWriter(docTextWriter));
      docTextWriter.close();
      bodyText = docTextWriter.toString();
    }
    catch (Exception e) {
      throw new ExtratorException("Erro ao extrair texto de documento Word: "
          + e.getMessage(), e);
    }

    if ((bodyText != null) && (bodyText.trim().length() > 0)) {
      sReader = new StringReader(bodyText);
    }
    else {
      throw new ExtratorException(
          "Erro ao extrair texto de documento Word: reader null!");
    }

    return sReader;
  }

}
