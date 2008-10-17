package com.jfsdesktop.texto;

import java.io.InputStream;
import java.io.StringReader;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.tidy.Tidy;

/** <P>Extrator para arquivos HTML.
 *  <P>Usa a biblioteca JTidy.
 *  
 *  <P><b>NOTA:</b> O uso de memória 
 *  é elevado para certos arquivos, devido
 *  ao processo de parsing.
 *
 */
public class JTidyHTMLExtrator implements Extrator {

  protected String getBody(Element rawDoc) {
    if (rawDoc == null) { return null; }
    String body = "";
    NodeList children = rawDoc.getElementsByTagName("body");
    if (children.getLength() > 0) {
      body = getText(children.item(0));
    }
    return body;
  }

  protected String getText(Node node) {
    NodeList children = node.getChildNodes();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < children.getLength(); i++) {
      Node child = children.item(i);
      switch (child.getNodeType()) {
        case Node.ELEMENT_NODE:
          sb.append(getText(child));
          sb.append(" ");
          break;
        case Node.TEXT_NODE:
          sb.append(((Text) child).getData());
          break;
      }
    }
    return sb.toString();
  }

  public StringReader getText(InputStream is) throws ExtratorException {
    Tidy tidy = new Tidy();
    tidy.setQuiet(true);
    tidy.setShowWarnings(false);
    org.w3c.dom.Document root = tidy.parseDOM(is, null);
    Element rawDoc = root.getDocumentElement();
    String body = getBody(rawDoc);
    if ((body != null) && (!body.equals(""))) {
      return new StringReader(body);
    }
    else {
      return null;
    }
  }
}
