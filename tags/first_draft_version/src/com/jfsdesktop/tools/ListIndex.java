package com.jfsdesktop.tools;

import java.io.File;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

/** <P>Lista as entradas no índice na saída padrão.*/
public class ListIndex {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		File indexDir = new File(args[0]);
		FSDirectory fsDir = FSDirectory.getDirectory(indexDir);
		IndexReader iReader = IndexReader.open(fsDir);
		int numDocs = iReader.numDocs();
		for (int k = 0; k < numDocs; ++k) {
			Document document = iReader.document(k);
			List<Field> fields = (List<Field>) document.getFields();
			for (Field f : fields) {
				System.out.println("DOCUMENT " + k);
				System.out.println("\tNAME  : " + f.name());
				System.out.println("\tVALUE : " + f.stringValue());				
			}

		}

	}

}
