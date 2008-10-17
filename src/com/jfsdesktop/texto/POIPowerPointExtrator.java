package com.jfsdesktop.texto;

import java.io.InputStream;
import java.io.StringReader;

import org.apache.poi.hslf.extractor.PowerPointExtractor;

/** <P>Extrator para arquivos MS PowerPoint. 
 *  <P>Usa a biblioteca Apache POI.
 */
public class POIPowerPointExtrator implements Extrator {

  public StringReader getText(InputStream is) throws ExtratorException {
    String bodyText = null;
    StringReader sReader = null;
    try {

      PowerPointExtractor ppe = new PowerPointExtractor(is);
      bodyText = ppe.getText();

    }
    catch (Exception e) {
      throw new ExtratorException(
          "Erro ao extrair texto de documento PowerPoint: " + e.getMessage(), e);
    }

    if ((bodyText != null) && (bodyText.trim().length() > 0)) {
      sReader = new StringReader(bodyText);
    }
    else {
      throw new ExtratorException(
          "Erro ao extrair texto de documento PowerPoint: reader null!");
    }

    return sReader;
  }

}
