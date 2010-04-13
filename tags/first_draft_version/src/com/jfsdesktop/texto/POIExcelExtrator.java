package com.jfsdesktop.texto;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/** <P>Extrator para documentos do MS Excel.
 *  <P>Usa a biblioteca Apache POI.
 */
public class POIExcelExtrator implements Extrator {


  /** <P>Percorre todos as "sheets" de um "workbook".
   *  <P>Para cada "sheet", percorre todas as linhas
   *  e, para cada linha, todas as c�lulas.
   *  <P>O texto extra�do de cada uma das c�lulas
   *  � retornado como um String.
   *  <P><b>Importante:</b> O POI at� a vers�o 3.0
   *  tem um <i>bug</i> no rowiterator que exclui
   *  a primeira coluna. 
   */
  @SuppressWarnings("unchecked")
public String readContents(HSSFWorkbook workbook) {
    StringBuilder builder = new StringBuilder();
    for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++)
    {
      HSSFSheet sheet = workbook.getSheetAt(numSheets);
      Iterator rows = sheet.rowIterator();
      while (rows.hasNext()) {
        HSSFRow row = (HSSFRow) rows.next();

        int first = row.getFirstCellNum();
        int last = row.getLastCellNum();

        for (int col = first; col <= last; ++col) {
          HSSFCell cell = (HSSFCell) row.getCell((short) col);
          if (null != cell) {
            processCell(cell, builder);
          }
        } // for each cell            	

      } // for each row

    } // for each sheet
    return builder.toString();
  }

  /** <P>Analisa o conte�da da c�lula e, 
   *  se for textual, ent�o o inclui no 
   *  StringBuilder passado como par�metro.
   *  <P>Se for num�rica, transforma em texto
   *  para indexa��o.
   *  <P>C�lulas com f�rmulas s�o testadas como
   *  texto e como n�mero.
   */
  @SuppressWarnings("deprecation")
  private void processCell(HSSFCell cell, StringBuilder builder)
  {
    if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {

      builder.append(cell.getStringCellValue());
      builder.append(" ");

    }
    else if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {

      String cellValue = Double.toString(cell.getNumericCellValue());
      if (cellValue.matches(".*\\.0")) {
        cellValue = cellValue.substring(0, cellValue.lastIndexOf("."));
      }
      else {
        cellValue = cellValue.replace(".", ",");
      }

      builder.append(cellValue);
      builder.append(" ");
    }
  }

  public StringReader getText(InputStream is) throws ExtratorException {
    String bodyText = null;

    try {

      POIFSFileSystem poiFS = new POIFSFileSystem(is);

      // Cria um objeto Workbook (a planilha com v�rios "sheets")
      HSSFWorkbook workBook = new HSSFWorkbook(poiFS);

      // Extrai o texto
      bodyText = readContents(workBook);

    }
    catch (IOException e) {
      throw new ExtratorException("IOException: " + e.getMessage(), e);
    }
    return new StringReader(bodyText);
  }
}
