package com.jfsdesktop.texto;

import java.io.PrintStream;
import java.io.PrintWriter;

/** <P>Sinaliza erros no processo de extração 
 *  do conteúdo textual de um arquivo.
 */
public class ExtratorException extends Exception {

  private static final long serialVersionUID = 1L;
  private Throwable cause;

  public ExtratorException() {
    super();
  }

  public ExtratorException(String message) {
    super(message);
  }

  public ExtratorException(Throwable cause) {
    super(cause.toString());
    this.cause = cause;
  }

  /**
   * Constructs with message and exception.
   */
  public ExtratorException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Retrieves nested exception.
   */
  public Throwable getException() {
    return cause;
  }

  public void printStackTrace() {
    printStackTrace(System.err);
  }

  public void printStackTrace(PrintStream ps) {
    synchronized (ps) {
      super.printStackTrace(ps);
      if (cause != null) {
        ps.println("--- Nested Exception ---");
        cause.printStackTrace(ps);
      }
    }
  }

  public void printStackTrace(PrintWriter pw) {
    synchronized (pw) {
      super.printStackTrace(pw);
      if (cause != null) {
        pw.println("--- Nested Exception ---");
        cause.printStackTrace(pw);
      }
    }
  }
}
