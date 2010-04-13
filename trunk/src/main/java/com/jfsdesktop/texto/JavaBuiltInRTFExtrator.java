package com.jfsdesktop.texto;

import java.io.InputStream;
import java.io.StringReader;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

/** <P>Extrator para arquivos RTF.
 *  <P>Usa o suporte a RTF do próprio JDK.
 */
public class JavaBuiltInRTFExtrator implements Extrator {

  public StringReader getText(InputStream iStream) throws ExtratorException {

    StringReader sReader = null;

    DefaultStyledDocument styledDoc = new DefaultStyledDocument();

    try {
      new RTFEditorKit().read(iStream, styledDoc, 0);
      if (styledDoc.getLength() > 0) {
        sReader = new StringReader(styledDoc.getText(0, styledDoc.getLength()));
      }
    }
    catch (Exception e) {
      throw new ExtratorException("Erro na extração de texto em RTF: " + e.getMessage(), e);
    }

    return sReader;
  }

}
