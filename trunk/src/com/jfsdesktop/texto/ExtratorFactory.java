package com.jfsdesktop.texto;

import java.io.File;
import java.util.ResourceBundle;

/** <P>Constrói extratores de texto
 *  com base no tipo do arquivo (se informado)
 *  ou com base na extensão do nome do 
 *  arquivo.
 *  
 *  <P>O mapeamento de tipos de arquivo para
 *  extratores é definido em:
 *  br.com.javamagazine.lucenedemo.properties.Extrator
 *  
 *  <P>O mapeamento de extensões para tipos de
 *  arquivo é definido em:
 *  br.com.javamagazine.lucenedemo.properties.Extensoes
 *  
 * @see TipoArquivo
 *
 */
public class ExtratorFactory {

	private ResourceBundle extratoresPorTipoArquivo;

	private static ExtratorFactory instance = null;

	private ExtratorFactory() {
		extratoresPorTipoArquivo = ResourceBundle
				.getBundle("br.com.javamagazine.lucenedemo.properties.Extrator");
	}

	public static synchronized ExtratorFactory getInstance() {
		if (null == instance) {
			instance = new ExtratorFactory();
		}
		return instance;
	}

	/**
	 * <P>
	 * Retorna um Extrator para o tipo de arquivo indicado.
	 * <P>
	 * Veja arquivo Extrator.properties.
	 * 
	 * @param tipoArquivo
	 *            Tipo do arquivo
	 * @return Extrator para o tipo ou null
	 * @throws ExtratorException
	 * 
	 */
	public Extrator criarExtrator(TipoArquivo tipoArquivo)
			throws ExtratorException {
		Extrator extrator = null;
		String nomeClasseExtrator = extratoresPorTipoArquivo.getString(tipoArquivo.name()
				.toUpperCase());
		if (nomeClasseExtrator != null) {
			Class<?> classeExtrator;
			try {
				classeExtrator = Class.forName(nomeClasseExtrator);
				extrator = (Extrator) classeExtrator.newInstance();
			} catch (Exception e) {
				throw new ExtratorException(e.getMessage());
			}
		}

		return extrator;
	}

	/**
	 * <P>
	 * Cria um Extrator com base no nome da extensão do arquivo (.txt, .doc,
	 * etc...).
	 * <P>
	 * Veja o arquivo Extensoes.properties.
	 * 
	 * @param extensaoArquivo
	 *            Nome da extensão, sem "."
	 * @return Extrator para o tipo do arquivo ou null
	 * @throws ExtratorException
	 */
	public Extrator criarExtrator(String extensaoArquivo)
			throws ExtratorException {
		TipoArquivo tipoArquivo = TipoArquivoHelper
				.getTipoArquivo(extensaoArquivo.toLowerCase());
		return criarExtrator(tipoArquivo);
	}

	public Extrator criarExtrator(File arquivo) throws ExtratorException {
		String nome = arquivo.getName();
		String extensao = nome.substring(nome.lastIndexOf(".") + 1);
		return criarExtrator(extensao);
	}

}
