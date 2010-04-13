package com.jfsdesktop.texto;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.ParserException;

/** <P>Extrator para textos HTML.
 *  <P>Usa a biblioteca HTMLParser.
 */
public class HTMLParserHTMLExtrator implements Extrator {

  public Reader getText(InputStream is) throws ExtratorException {
    StringReader sReader = null;

    try {
      Parser parser = new Parser();
      Lexer lexer = new Lexer();
      Page page = new Page(is, "utf-8");
      lexer.setPage(page);
      parser.setLexer(lexer);
      StringBean stringBean = new StringBean();
      parser.visitAllNodesWith(stringBean);
      sReader = new StringReader(stringBean.getStrings());
    }
    catch (ParserException e) {
      throw new ExtratorException("ParserException: " + e.getMessage());
    }
    catch (UnsupportedEncodingException e) {
      throw new ExtratorException("UnsupportedEncodingException: "
          + e.getMessage());
    }

    return sReader;
  }

}
