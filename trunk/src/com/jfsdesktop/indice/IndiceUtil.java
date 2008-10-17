package com.jfsdesktop.indice;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;

/** <P>Esta classe encaspula para uso externo alguns m�todos 
 *  �teis da API Lucene.
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

  /** <P>Inicializa um �ndice do Lucene no diretorio indicado.
   *  <P><b>Apaga</b> o �ndice existente, se houver ! 
   *  <P><strong>NOTA:</strong>Numa aplica��o multi-threaded, � aconselh�vel
   * que este m�todo seja <i>synchronized</i>.
   * @param indexDir Onde criar (ou refazer) o �ndice. Tem de ser diret�rio existente.
   * @throws IndiceException
   */
  public static void inicializaIndice(File indexDir) throws IndiceException {
    IndexWriter indexWriter = null;
    try {
      indexWriter = new IndexWriter(indexDir, new StandardAnalyzer(), true);
    }
    catch (IOException e) {
      throw new IndiceException("IOException ao inicializar �ndice: "
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
