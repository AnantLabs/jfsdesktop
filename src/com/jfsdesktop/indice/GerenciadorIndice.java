package com.jfsdesktop.indice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.jfsdesktop.analyzer.PortuguesAnalyzer;
import com.jfsdesktop.config.Config;
import com.jfsdesktop.logging.Logger;
import com.jfsdesktop.texto.ExtratorException;
import com.jfsdesktop.texto.ExtratorFactory;


/** <P>Classe que encapsula os métodos de acesso
 *  à API do Lucene.
 *  
 *  <P>Esta classe contém os métodos para:
 *  <LI>Adição, remoção e
 *  substituição de entradas no índice do Lucene.
 *  <LI>Pesquisa textual.
 *  <P>&nbsp;
 *  <hr>
 *  <P><b>IMPORTANTE:</b>
 *  <P>Este código tem fins meramente didáticos e 
 *  <b>não</b> deve ser usado em produção.
 *
 */
public class GerenciadorIndice {

  /** <P>Acrescenta uma entrada ao índice 
   *  do Lucene para o arquivo indicado.
   * 
   *  <P>Invoca um extrator de texto para recuperar
   *  o "texto puro".
   *  
   * @param diretorioIndice Diretório com o índice do Lucene (já deve existir).
   * @param arquivo Objeto File indicando o arquivo a indexar.  
   * @throws IndiceException Se houver problemas ao extrair o texto ou ao indexar.
   */
  @SuppressWarnings("deprecation")
  public static void adicionar(File diretorioIndice, File arquivo)
      throws IndiceException
  {

    try {
      Logger.trace("> adicionar(File,File)");
      Logger.debug("Arquivo:" + arquivo.getAbsolutePath());

      // Acesso ao índice
      FSDirectory fsDir = FSDirectory.getDirectory(diretorioIndice, false);

      // Extrái o texto do arquivo se o tipo for suportado
      Reader reader = extrairTexto(arquivo);

      // Cria um objeto Document do Lucene
      Document document = new Document();

      // Conteudo, ou seja, campo que permite pesquisar
      // pelas palavras contidas no texto do arquivo.
      // => indexed
      // => tokenized
      // => unstored  
      document
          .add(new Field(Config.getProperty("nome.campo.conteudo"), reader));

      // Nome completo do arquivo (usado para identificar a entrada no índice).
      // => indexed
      // => untokenized
      // => stored 
      document
          .add(new Field(Config.getProperty("nome.campo.nomearquivo"), arquivo
              .getAbsolutePath(), Field.Store.YES, Field.Index.UN_TOKENIZED));

      // Hash MD5 do conteúdo (para detectar atualizações).
      // => indexed
      // => untokenized
      // => stored 
      document.add(new Field(Config.getProperty("nome.campo.hash"),
          getHash(arquivo), Field.Store.YES, Field.Index.UN_TOKENIZED));

      // Obtém um IndexWriter para criar a entrada no índice,
      // adicionando o objeto document recém-criado.      
      IndexWriter idxWriter = null;
      try {
        
        // Substitua pelo StandardAnalyzer para textos em Inglês
        idxWriter = new IndexWriter(fsDir, new PortuguesAnalyzer(), false);
        
        // Por default, o Lucene admite no máximo 10.000 palavras diferentes 
        // por texto de documento. 100.000 palavras permite tratar obras literárias.
        idxWriter.setMaxFieldLength(100000);

        // Insere o document no índice (só vai gravar após o "close()")
        idxWriter.addDocument(document);
        idxWriter.optimize();
      }
      finally {
        // Persiste as alterações e "libera" o lock sobre
        // o índice em um bloco finally, ou corremos o risco
        // de ter o índice "travado" até removermos o arquivo 
        // de lock.        
        if (null != idxWriter) {
          idxWriter.close();
        }

        // O processo de extração de texto é dispendioso em memória: 
        // Solicitar garbage collection...
        System.gc();
      }
    }
    catch (Exception e) {
      Logger.error(e.getMessage());
      throw new IndiceException(e.getMessage(), e);
    }
    Logger.trace("< adicionar(File,File)");
  }

  /** <P>Remove uma ou mais entradas do índice do Lucene.
   *  <P>As entradas a remover são identificadas pelo
   *  valor do campo "nome.campo.nomearquivo", definido
   *  no arquivo de configuração.
   *  
   * @see br.com.javamagazine.lucenedemo.properties.Config
   *
   * @param diretorioIndice Diretório com o índice do Lucene (deve existir).
   * @param nomeArquivo Identificador da entrada a ser removida (caminho completo).
   *  
   * @return True se a remoção for bem-sucedida. 
   * @throws IndiceException
   */
  @SuppressWarnings("deprecation")
  public static boolean remover(File diretorioIndice, String nomeArquivo)
      throws IndiceException
  {
    Logger.trace("> remover(File,String)");
    Logger.trace("Removendo: " + nomeArquivo);
    boolean ok = false;

    try {
      Directory fsDir = FSDirectory.getDirectory(diretorioIndice, false);
      IndexSearcher idxSearcher = new IndexSearcher(fsDir);

      // Cria um objeto Term para representar o valor do campo "nome do arquivo"
      Term term = new Term(Config.getProperty("nome.campo.nomearquivo"),
          nomeArquivo);

      // Cria uma Query para buscar por documentos que tenham esse
      // valor para o campo "nome do arquivo".
      Query termQuery = new TermQuery(term);
      
      // Pesquisa...
      Hits hits = idxSearcher.search(termQuery);
      int hitCount = hits.length();
      idxSearcher.close();

      // Verifica o número de resultados da pesquisa, ou seja,
      // quantos documentos têm o nome indicado      
      if (hitCount == 0) {
        Logger.debug("Documento não localizado: " + nomeArquivo);
      }
      else if (hitCount > 1) {
        // Como estamos usando o nome completo do 
        // arquivo como identificador, não deve
        // haver mais de uma entrada a remover.
        // Sinalizar como um erro !
        throw new IndiceException("Mais de um documento localizado para: "
            + nomeArquivo);
      }
      else {
        // Exatamente uma entrada localizada: remover !
        IndexReader idxReader = null;
        try {
          // Marca o document como removido:
          // somente será persistido ao fechar o IndexReader !
          idxReader = IndexReader.open(fsDir);
          idxReader.deleteDocuments(term);
          ok = true;
          Logger.debug("Entrada removida: " + nomeArquivo);
        }
        finally {
          // Persiste a remoção num bloco finally:
          // não deixar o índice sob lock !
          if (null != idxReader) {
            idxReader.close();
          }
        }
      }

      Logger.trace("< remover(File,String)");
      return ok;
    }
    catch (Exception e) {
      Logger.error(e.getMessage());
      throw new IndiceException(e.getMessage(), e);
    }
  }

  /** <P>Realiza uma pesquisa no índice usando a expressão de 
   *  busca digitada por um usuário.
   *  
   * @param diretorioIndice Diretório com o índice do Lucene.
   * @param expressaoDeBusca Expressão de busca válida.
   * @return Uma coleção org.apache.lucene.search.Hits
   * @throws IndiceException
   */
  @SuppressWarnings("deprecation")
  public static Hits pesquisar(File diretorioIndice, String expressaoDeBusca)
      throws IndiceException
  {
    Logger.trace("> pesquisar(File,String)");
    try {
      Logger.debug("Pesquisando por: " + expressaoDeBusca);
      Directory fsDir = FSDirectory.getDirectory(diretorioIndice, false);
      IndexSearcher is = new IndexSearcher(fsDir);
      Query query = new QueryParser(Config.getProperty("nome.campo.conteudo"),
          new PortuguesAnalyzer()).parse(expressaoDeBusca);
      Hits hits = is.search(query);
      Logger.debug("Encontrados: " + hits.length());
      Logger.trace("< pesquisar(File,String)");
      return hits;
    }
    catch (Exception e) {
      Logger.error(e.getMessage());
      throw new IndiceException(e.getMessage(), e);
    }
  }

  /**<P>Substitui uma entrada existente no índice por
   * outra, ou seja, refaz a indexação de um arquivo
   * que já tinha uma entrada no índice do Lucene.
   * <P>Isto é feito fazendo uma remoção seguida de uma
   * inserção.
   * <P><b>Importante:</b>Este procedimento não é transacional.
   * Para suporte a transações, consulte o framework Compass.
   * 
   * @param diretorioIndice Diretório com o índice do Lucene.
   * @param arquivo Arquivo cuja entrada deve ser substituída no índice.
   * @return True se a substituição teve sucesso.
   * @throws IndiceException
   */
  public static boolean substituir(File diretorioIndice, File arquivo)
      throws IndiceException
  {
    Logger.trace("> substituir(File,File)");
    boolean ok = false;
    if (remover(diretorioIndice, arquivo.getAbsolutePath())) {
      adicionar(diretorioIndice, arquivo);
      ok = true;
      Logger.trace("Entrada substituida:" + arquivo.getAbsolutePath());
    }
    Logger.trace("< substituir(File,File)");
    return ok;
  }

  /** <P>Utilitário para computar o hash MD5 de um arquivo. */
  public static String getHash(File arquivo) throws IndiceException {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      FileInputStream fis = new FileInputStream(arquivo);
      byte[] buffer = new byte[1024];
      while (-1 != fis.read(buffer)) {
        md.update(buffer);
      }
      fis.close();
      return new String(Hex.encodeHex(md.digest())).toUpperCase();
    }
    catch (Exception e) {
      throw new IndiceException(e.getMessage(), e);
    }
  }

  /** <P>Recupera o "texto puro" contido em 
   *  um determinado arquivo.
   *  
   * @see com.jfsdesktop.texto.TipoArquivo
   * @see com.jfsdesktop.texto.ExtratorFactory
   * @see com.jfsdesktop.texto.Extrator
   *
   * @param arquivo Objeto File apontando o arquivo de onde extrair o texto.
   * @return Um objeto Reader com acesso ao texto extraído. 
   * @throws ExtratorException Se houver erros na extração do texto.
   * 
   */
  private static Reader extrairTexto(File arquivo) throws ExtratorException {

    Reader reader = null;

    try {
      FileInputStream fis = new FileInputStream(arquivo);
      reader = ExtratorFactory.getInstance().criarExtrator(arquivo)
          .getText(fis);
    }
    catch (FileNotFoundException e) {
      throw new ExtratorException("Arquivo não localizado: "
          + arquivo.getAbsolutePath());
    }

    if (null == reader) { throw new ExtratorException(
        "Erro na extração de texto: Reader null!"); }

    return reader;

  }

}
