package com.jfsdesktop.indice;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;

/** <P>Esta classe encaspula para uso externo alguns métodos 
 *  úteis da API Lucene.
 */
public class IndiceUtil {

  public static boolean indiceExiste(File indexDir) throws IndiceException {
    return IndexReader.indexExists(indexDir);
  }

  public static boolean isIndiceLocked(File indexDir) throws IndiceException {
    try {
      return IndexReader.isLocked(indexDir.getAbsolutePath());
    }
    catch (IOException e) {
      throw new IndiceException("IOException verificando lock: "
          + e.getMessage(), e);
    }
  }

  /** <P>Inicializa um índice do Lucene no diretorio indicado.
   *  <P><b>Apaga</b> o índice existente, se houver ! 
   *  <P><strong>NOTA:</strong>Numa aplicação multi-threaded, é aconselhável
   * que este método seja <i>synchronized</i>.
   * @param indexDir Onde criar (ou refazer) o índice. Tem de ser diretório existente.
   * @throws IndiceException
   */
  public static void inicializaIndice(File indexDir) throws IndiceException {
    IndexWriter indexWriter = null;
    try {
      indexWriter = new IndexWriter(indexDir, new StandardAnalyzer(), true);
    }
    catch (IOException e) {
      throw new IndiceException("IOException ao inicializar índice: "
          + e.getMessage(), e);
    }
    finally {
      try {
        if (null != indexWriter) {
          indexWriter.close();
        }
      }
      catch (IOException e) {
        throw new IndiceException("IOException fechando o IndexWriter: "
            + e.getMessage(), e);
      }
    }
  }
}
