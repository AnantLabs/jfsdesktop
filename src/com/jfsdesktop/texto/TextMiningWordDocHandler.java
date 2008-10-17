package com.jfsdesktop.texto;

import java.io.InputStream;
import java.io.StringReader;

import org.textmining.text.extraction.WordExtractor;

/** <P>Extrator para arquivos MS Word.
 *  <P>Usa a biblioteca TextMining, que é um wrapper para o Apache POI.
 */
public class TextMiningWordDocHandler implements Extrator {

  public StringReader getText(InputStream is) throws ExtratorException {
    try {
      return new StringReader(extractBodyText(is));
    }
    catch (Exception e) {
      throw new ExtratorException(
          "Exception ao extrair texto do documento MSWORD: " + e.getMessage(),
          e);
    }
  }

  protected String extractBodyText(InputStream is) throws Exception {
    return new WordExtractor().extractText(is);
  }

}
