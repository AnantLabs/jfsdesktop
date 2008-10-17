package com.jfsdesktop.texto;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/** <P> Classe utilitária para identificar o Extrator
 *  de um determinado tipo de arquivo ou extensão.
 */
public class TipoArquivoHelper {

  private static ResourceBundle handlersPorExtensao;

  private static Map<String, TipoArquivo> tiposPorExtensaoMapa;

  static {
    handlersPorExtensao = ResourceBundle
        .getBundle("br.com.javamagazine.lucenedemo.properties.Extensoes");

    Enumeration<String> handlerNames = handlersPorExtensao.getKeys();
    tiposPorExtensaoMapa = new HashMap<String, TipoArquivo>();
    while (handlerNames.hasMoreElements()) {
      String handlerName = handlerNames.nextElement();
      String[] extensions = handlersPorExtensao.getString(handlerName).split(
          ",");
      for (String extension : extensions) {
        TipoArquivo ft = TipoArquivo.valueOf(handlerName);
        tiposPorExtensaoMapa.put(extension, ft);
      }
    }

  }

  public static TipoArquivo getTipoArquivo(String extensaoArquivo) {
    TipoArquivo fileType = null;
    extensaoArquivo = extensaoArquivo.toLowerCase();
    if (tiposPorExtensaoMapa.containsKey(extensaoArquivo)) {
      fileType = tiposPorExtensaoMapa.get(extensaoArquivo);
    }
    return fileType;
  }

  public static String[] getExtensoes() {
    return tiposPorExtensaoMapa.keySet().toArray(new String[8]);
  }

}
