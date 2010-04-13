package com.jfsdesktop.indice;

import com.jfsdesktop.texto.ExtratorException;

/** <P>Exception para sinalizar erros ocorridos 
 *  em opera��es de acesso ao �ndice do Lucene.
 *  
 *  @see ExtratorException
 */
public class IndiceException extends Exception {

  private static final long serialVersionUID = 1L;

  public IndiceException(String message) {
    super(message);
  }

  public IndiceException(String message, Throwable t) {
    super(message, t);
  }

}
