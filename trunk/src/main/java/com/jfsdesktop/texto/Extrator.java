package com.jfsdesktop.texto;


import java.io.InputStream;
import java.io.Reader;

/** <P>Um Extrator � uma classe que
 *  tem a capacidade de extrair o 
 *  "texto puro" de um arquivo.
 *  <P>Por "texto puro" entende-se o 
 *  conte�do do arquivo sem os dados
 *  de controle e formata��o.
 */
public interface Extrator {
   
  /**<P>Extrai o texto do conte�do do 
   * arquivo e o retorna como um objeto
   * StringReader.
   * 
   * @param is InputStream com o conte�do do arquivo
   * @return Um objeto StringReader com o texto do arquivo 
   * @throws ExtratorException
   */
  Reader getText(InputStream is)
    throws ExtratorException;
  
}
