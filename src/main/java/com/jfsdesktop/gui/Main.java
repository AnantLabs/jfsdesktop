package com.jfsdesktop.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.graphics.Image;

import com.jfsdesktop.config.Config;
import com.jfsdesktop.indice.GerenciadorIndice;
import com.jfsdesktop.indice.IndiceException;
import com.jfsdesktop.indice.IndiceUtil;
import com.jfsdesktop.texto.TipoArquivo;
import com.jfsdesktop.texto.TipoArquivoHelper;

public class Main {

	private Shell sShell = null; // @jve:decl-index=0:visual-constraint="67,10"

	private Composite compositeMain = null;

	private CTabFolder cTabFolderMain = null;

	private Composite compositeIndexDocuments = null;

	private Composite compositeSearchDocuments = null;

	private Composite compositeSearchDocumentsTop = null;

	private Composite compositeSearchDocumentsResults = null;

	private Composite compositeSearchDocumentsStatus = null;

	private Label labelIndexDir = null;

	private Text textIndexDir = null;

	private Button buttonSearchIndexDir = null;

	private Label labelQuery = null;

	private Text textQuery = null;

	private Button buttonSearch = null;

	private Table tableSearchResultsCounter = null;

	private Label lblDiretorioIndice = null;

	private Text textDiretorioIndice = null;

	private Button checkBoxRecursivo = null;

	private Text textDiretorioFonte = null;

	private Label labelDiretorioFonte = null;

	private Button checkBoxRefazerIndice = null;

	private Button buttonEscolherDiretorioIndice_Indexar = null;

	private Button buttonEscolherDiretorio_Indexar = null;

	private Text textAreaLog_Indexar = null;

	private Label labelLog_Indexar = null;

	private Composite compositeButtonIndexar = null;

	private Button buttonIndice_Indexar = null;

	private Composite compositeIndexar_Bottom = null;

	private Button buttonIndexar_Bottom_LimparLog = null;

	private Button buttonPesquisa_Remover = null;

	private Button buttonPesquisa_SubstituirEntrada = null;

	private Label labelProgressoIndexacao = null;

	protected void log(String mensagem) {
		this.textAreaLog_Indexar.insert(mensagem);
		this.textAreaLog_Indexar.insert("\n");
	}

	protected void limparLog() {
		this.textAreaLog_Indexar.setText("");
	}

	/**
	 * This method initializes compositeMain
	 * 
	 */
	private void createCompositeMain() {
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData2.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData2.grabExcessVerticalSpace = true;
		compositeMain = new Composite(sShell, SWT.NONE);
		compositeMain.setLayout(new GridLayout());
		createCTabFolderMain();
		compositeMain.setLayoutData(gridData2);
	}

	/**
	 * This method initializes cTabFolderMain
	 * 
	 */
	private void createCTabFolderMain() {
		GridData gridData3 = new GridData();
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData3.grabExcessVerticalSpace = true;
		cTabFolderMain = new CTabFolder(compositeMain, SWT.NONE);
		cTabFolderMain.setSimple(false);
		cTabFolderMain.setSelectionBackground(new Color(Display.getCurrent(),
				0, 168, 246));
		cTabFolderMain.setSelectionForeground(new Color(Display.getCurrent(),
				255, 255, 255));
		cTabFolderMain.setLayoutData(gridData3);
		createCompositeIndexDocuments();
		createCompositeSearchDocuments();
		CTabItem cTabItem = new CTabItem(cTabFolderMain, SWT.CLOSE);
		cTabItem.setText("Índice");
		cTabItem.setControl(compositeIndexDocuments);
		CTabItem cTabItem1 = new CTabItem(cTabFolderMain, SWT.CLOSE);
		cTabItem1.setText("Pesquisa");
		cTabItem1.setControl(compositeSearchDocuments);
	}

	/**
	 * This method initializes compositeIndexDocuments
	 * 
	 */
    @SuppressWarnings("unused")
    private void createCompositeIndexDocuments() {
		GridData gridData16 = new GridData();
		gridData16.horizontalSpan = 6;
		gridData16.grabExcessVerticalSpace = true;
		gridData16.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData16.grabExcessHorizontalSpace = true;
		gridData16.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridData gridData12 = new GridData();
		gridData12.grabExcessHorizontalSpace = false;
		gridData12.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData12.horizontalSpan = 2;
		gridData12.horizontalIndent = 5;
		gridData12.widthHint = -1;
		GridData gridData11 = new GridData();
		gridData11.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData11.horizontalSpan = 2;
		gridData11.horizontalIndent = 5;
		gridData11.widthHint = 280;
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.numColumns = 6;
		compositeIndexDocuments = new Composite(cTabFolderMain, SWT.NONE);
		compositeIndexDocuments.setLayout(gridLayout2);
		lblDiretorioIndice = new Label(compositeIndexDocuments, SWT.NONE);
		lblDiretorioIndice.setText("Diretório do índice:");
		lblDiretorioIndice
				.setToolTipText("Diretório onde o índice será construído");
		textDiretorioIndice = new Text(compositeIndexDocuments, SWT.BORDER);
		textDiretorioIndice.setLayoutData(gridData11);
		buttonEscolherDiretorioIndice_Indexar = new Button(
				compositeIndexDocuments, SWT.NONE);
		buttonEscolherDiretorioIndice_Indexar.setText("...");
		Label filler12 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler31 = new Label(compositeIndexDocuments, SWT.NONE);
		buttonEscolherDiretorioIndice_Indexar
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						try {
							doGUI_EscolherDiretorioIndice_Indexar();
						} catch (Exception ex) {
							handleExceptions(ex);
						}
					}
				});
		labelDiretorioFonte = new Label(compositeIndexDocuments, SWT.NONE);
		labelDiretorioFonte.setText("Diretório a indexar:");
		labelDiretorioFonte
				.setToolTipText("Diretório contendo os arquivos a serem indexados");
		textDiretorioFonte = new Text(compositeIndexDocuments, SWT.BORDER);
		textDiretorioFonte.setLayoutData(gridData12);
		buttonEscolherDiretorio_Indexar = new Button(compositeIndexDocuments,
				SWT.NONE);
		buttonEscolherDiretorio_Indexar.setText("...");
		Label filler11 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler30 = new Label(compositeIndexDocuments, SWT.NONE);
		buttonEscolherDiretorio_Indexar
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						try {
							doGUI_EscolherDiretorio_Indexar();
						} catch (Exception ex) {
							handleExceptions(ex);
						}
					}
				});
		checkBoxRefazerIndice = new Button(compositeIndexDocuments, SWT.CHECK);
		checkBoxRefazerIndice.setText("Refazer o índice");
		checkBoxRefazerIndice
				.setToolTipText("Apaga o índice se já existir no diretório");
        Label filler1 = new Label(compositeIndexDocuments, SWT.NONE);
		createCompositeButtonIndexar();
		Label filler10 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler29 = new Label(compositeIndexDocuments, SWT.NONE);
		checkBoxRecursivo = new Button(compositeIndexDocuments, SWT.CHECK);
		checkBoxRecursivo.setText("Incluir sub-diretórios");
		checkBoxRecursivo
				.setToolTipText("Adiciona os arquivos nos sub-diretórios");
		Label filler = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler9 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler28 = new Label(compositeIndexDocuments, SWT.NONE);
		labelProgressoIndexacao = new Label(compositeIndexDocuments, SWT.NONE);
		labelProgressoIndexacao.setText("Progresso:");
		Label filler15 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler14 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler17 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler18 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler27 = new Label(compositeIndexDocuments, SWT.NONE);
		createCompositeIndexar_Progresso_Interromper();
		Label filler146 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler26 = new Label(compositeIndexDocuments, SWT.NONE);
		labelLog_Indexar = new Label(compositeIndexDocuments, SWT.NONE);
		labelLog_Indexar.setText("Log:");
		Label filler2 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler3 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler4 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler7 = new Label(compositeIndexDocuments, SWT.NONE);
		Label filler25 = new Label(compositeIndexDocuments, SWT.NONE);
		textAreaLog_Indexar = new Text(compositeIndexDocuments, SWT.MULTI
				| SWT.WRAP | SWT.V_SCROLL);
		textAreaLog_Indexar.setFont(new Font(Display.getDefault(),
				"Courier New", 10, SWT.NORMAL));
		textAreaLog_Indexar.setLayoutData(gridData16);
		createCompositeIndexar_Bottom();
	}

	/**
	 * This method initializes compositeSearchDocuments
	 * 
	 */
	private void createCompositeSearchDocuments() {
		compositeSearchDocuments = new Composite(cTabFolderMain, SWT.NONE);
		compositeSearchDocuments.setLayout(new GridLayout());
		createCompositeSearchDocumentsTop();
		createCompositeSearchDocumentsResults();
		createCompositeSearchDocumentsStatus();
	}

	/**
	 * This method initializes compositeSearchDocumentsTop
	 * 
	 */
	private void createCompositeSearchDocumentsTop() {
		GridData gridData22 = new GridData();
		gridData22.widthHint = -1;
		gridData22.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridData gridData9 = new GridData();
		gridData9.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData9.horizontalIndent = 5;
		gridData9.widthHint = 280;
		gridData9.horizontalSpan = 2;
		GridData gridData8 = new GridData();
		gridData8.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData8.horizontalIndent = 5;
		gridData8.widthHint = -1;
		gridData8.horizontalSpan = 2;
		GridData gridData7 = new GridData();
		gridData7.grabExcessHorizontalSpace = false;
		gridData7.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 4;
		gridLayout1.makeColumnsEqualWidth = false;
		GridData gridData4 = new GridData();
		gridData4.grabExcessHorizontalSpace = false;
		gridData4.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		compositeSearchDocumentsTop = new Composite(compositeSearchDocuments,
				SWT.NONE);
		compositeSearchDocumentsTop.setLayoutData(gridData4);
		compositeSearchDocumentsTop.setLayout(gridLayout1);
		labelIndexDir = new Label(compositeSearchDocumentsTop, SWT.NONE);
		labelIndexDir.setText("Diretório do índice:");
		textIndexDir = new Text(compositeSearchDocumentsTop, SWT.BORDER);
		textIndexDir.setLayoutData(gridData9);
		buttonSearchIndexDir = new Button(compositeSearchDocumentsTop, SWT.NONE);
		buttonSearchIndexDir.setText("...");
		buttonSearchIndexDir.setLayoutData(gridData22);
		labelQuery = new Label(compositeSearchDocumentsTop, SWT.NONE);
		labelQuery.setText("Expressão de pesquisa:");
		textQuery = new Text(compositeSearchDocumentsTop, SWT.BORDER);
		textQuery.setLayoutData(gridData8);
		buttonSearch = new Button(compositeSearchDocumentsTop, SWT.NONE);
		buttonSearch.setText("");
		// buttonSearch.setImage(new Image(Display.getCurrent(),
		//buttonSearch.setImage(new Image(Display.getCurrent(), getClass().getResourceAsStream("com/jfsdesktop/gui/lupa.jpg")));
		// "E:/DADOS/ARTIGOS/lucene/applications/LuceneDemo/src/icon_magnifier.gif"));
		buttonSearch.setLayoutData(gridData7);
		buttonSearch
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						try {
							doGUI_Pesquisar();
						} catch (Exception ex) {
							handleExceptions(ex);
						}
					}
				});

	}

	/**
	 * This method initializes compositeSearchDocumentsResults
	 * 
	 */
	private void createCompositeSearchDocumentsResults() {
		GridData gridData10 = new GridData();
		gridData10.grabExcessHorizontalSpace = true;
		gridData10.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData10.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData10.grabExcessVerticalSpace = true;
		GridData gridData5 = new GridData();
		gridData5.grabExcessHorizontalSpace = false;
		gridData5.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData5.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData5.grabExcessVerticalSpace = true;
		compositeSearchDocumentsResults = new Composite(
				compositeSearchDocuments, SWT.NONE);
		compositeSearchDocumentsResults.setLayout(new GridLayout());
		compositeSearchDocumentsResults.setLayoutData(gridData5);
		tableSearchResultsCounter = new Table(compositeSearchDocumentsResults,
				SWT.BORDER | SWT.CHECK);
		tableSearchResultsCounter.setHeaderVisible(true);
		tableSearchResultsCounter.setLayoutData(gridData10);
		tableSearchResultsCounter.setLinesVisible(true);
		TableColumn columnCounter = new TableColumn(tableSearchResultsCounter,
				SWT.NONE);
		columnCounter.setWidth(60);
		columnCounter.setText("#");
		TableColumn columnFileName = new TableColumn(tableSearchResultsCounter,
				SWT.NONE);
		columnFileName.setWidth(256);
		columnFileName.setText("Nome completo do arquivo");
		TableColumn columnDateIndexed = new TableColumn(
				tableSearchResultsCounter, SWT.NONE);
		columnDateIndexed.setWidth(200);
		columnDateIndexed.setText("Hash");
	}

	/**
	 * This method initializes compositeSearchDocumentsStatus
	 * 
	 */
	private void createCompositeSearchDocumentsStatus() {
		GridData gridData18 = new GridData();
		gridData18.grabExcessHorizontalSpace = true;
		GridData gridData17 = new GridData();
		gridData17.horizontalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		GridData gridData15 = new GridData();
		gridData15.horizontalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		GridLayout gridLayout5 = new GridLayout();
		gridLayout5.numColumns = 3;
		gridLayout5.marginWidth = 1;
		GridData gridData6 = new GridData();
		gridData6.grabExcessHorizontalSpace = true;
		gridData6.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		compositeSearchDocumentsStatus = new Composite(
				compositeSearchDocuments, SWT.NONE);
		compositeSearchDocumentsStatus.setLayoutData(gridData6);
		compositeSearchDocumentsStatus.setLayout(gridLayout5);
		Label filler5 = new Label(compositeSearchDocumentsStatus, SWT.NONE);
		filler5.setLayoutData(gridData18);
		buttonPesquisa_Remover = new Button(compositeSearchDocumentsStatus,
				SWT.NONE);
		buttonPesquisa_Remover.setText("Remover Entradas");
		buttonPesquisa_Remover.setLayoutData(gridData17);
		buttonPesquisa_Remover
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						try {
							doGUI_RemoverEntradas();
						} catch (GUIException ex) {
							handleExceptions(ex);
						}
					}
				});
		buttonPesquisa_SubstituirEntrada = new Button(
				compositeSearchDocumentsStatus, SWT.NONE);
		buttonPesquisa_SubstituirEntrada.setText("Substituir Entradas");
		buttonPesquisa_SubstituirEntrada.setLayoutData(gridData15);
		buttonPesquisa_SubstituirEntrada
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						try {
							doGUI_SubstituirEntradas();
						} catch (GUIException ex) {
							handleExceptions(ex);
						}
					}
				});
	}

	/**
	 * This method initializes compositeButtonIndexar
	 * 
	 */
	private void createCompositeButtonIndexar() {
		GridData gridData1 = new GridData();
		gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.END;
		gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.grabExcessHorizontalSpace = true;
		GridLayout gridLayout3 = new GridLayout();
		gridLayout3.marginWidth = 0;
		gridLayout3.marginHeight = 0;
		GridData gridData = new GridData();
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.verticalSpan = 2;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = false;
		compositeButtonIndexar = new Composite(compositeIndexDocuments,
				SWT.NONE);
		compositeButtonIndexar.setLayoutData(gridData);
		compositeButtonIndexar.setLayout(gridLayout3);
		buttonIndice_Indexar = new Button(compositeButtonIndexar, SWT.NONE);
		buttonIndice_Indexar.setText("Indexar diretório!");
		buttonIndice_Indexar.setLayoutData(gridData1);
		buttonIndice_Indexar
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						try {
							doGUI_Indexar();
						} catch (Exception ex) {
							handleExceptions(ex);
						}
					}
				});
	}

	/**
	 * This method initializes compositeIndexar_Bottom
	 * 
	 */
	private void createCompositeIndexar_Bottom() {
		GridData gridData14 = new GridData();
		gridData14.horizontalAlignment = org.eclipse.swt.layout.GridData.END;
		gridData14.grabExcessHorizontalSpace = true;
		GridLayout gridLayout4 = new GridLayout();
		gridLayout4.numColumns = 1;
		gridLayout4.marginWidth = 1;
		GridData gridData13 = new GridData();
		gridData13.horizontalSpan = 6;
		gridData13.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData13.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		compositeIndexar_Bottom = new Composite(compositeIndexDocuments,
				SWT.NONE);
		compositeIndexar_Bottom.setLayoutData(gridData13);
		compositeIndexar_Bottom.setLayout(gridLayout4);
		buttonIndexar_Bottom_LimparLog = new Button(compositeIndexar_Bottom,
				SWT.NONE);
		buttonIndexar_Bottom_LimparLog.setText("Limpar Log");
		buttonIndexar_Bottom_LimparLog.setLayoutData(gridData14);
		buttonIndexar_Bottom_LimparLog
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						limparLog();
					}
				});
	}

	/**
	 * This method initializes compositeIndexar_Progresso_Interromper	
	 *
	 */
    @SuppressWarnings("unused")
	private void createCompositeIndexar_Progresso_Interromper() {
		GridData gridData21 = new GridData();
		gridData21.grabExcessVerticalSpace = false;
		gridData21.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData21.grabExcessHorizontalSpace = false;
		GridData gridData19 = new GridData();
		gridData19.grabExcessHorizontalSpace = true;
		gridData19.grabExcessVerticalSpace = true;
		gridData19.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		GridLayout gridLayout6 = new GridLayout();
		gridLayout6.numColumns = 3;
		gridLayout6.marginWidth = 1;
		GridData gridData20 = new GridData();
		gridData20.horizontalSpan = 4;
		gridData20.grabExcessVerticalSpace = false;
		gridData20.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData20.grabExcessHorizontalSpace = false;
		compositeIndexar_Progresso_Interromper = new Composite(
				compositeIndexDocuments, SWT.NONE);
		compositeIndexar_Progresso_Interromper.setLayoutData(gridData20);
		compositeIndexar_Progresso_Interromper.setLayout(gridLayout6);
		progressBarIndexacao = new ProgressBar(compositeIndexar_Progresso_Interromper, SWT.NONE);
		progressBarIndexacao.setLayoutData(gridData19);
		Label filler16 = new Label(compositeIndexar_Progresso_Interromper, SWT.NONE);
		buttonIndexar_Interromper = new Button(compositeIndexar_Progresso_Interromper, SWT.NONE);
		buttonIndexar_Interromper.setText("Cancelar");
		buttonIndexar_Interromper.setEnabled(false);
		buttonIndexar_Interromper.setLayoutData(gridData21);
		buttonIndexar_Interromper
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						setThreadIndexacaoInterrompido(true);
						buttonIndexar_Interromper.setEnabled(false);
					}
				});
	}

	public static void main(String[] args) {
		/*
		 * Before this is run, be sure to set up the launch configuration
		 * (Arguments->VM Arguments) for the correct SWT library path in order
		 * to run with the SWT dlls. The dlls are located in the SWT plugin jar.
		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
		 * installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		Display display = Display.getDefault();
		Main thisClass = new Main();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		sShell = new Shell();
		sShell.setText("Lucene Demo");
		sShell.setLocation(new Point(0, 0));
		sShell.setLayout(gridLayout);
		sShell.setSize(new Point(599, 489));
		createCompositeMain();
	}

	//
	// Event-handlers
	//

	protected void doGUI_EscolherDiretorio_Indexar() {
		File diretorio = doGUI_EscolherDiretorio();
		if (null != diretorio) {
			this.textDiretorioFonte.setText(diretorio.getAbsolutePath());
		}
	}

	protected void doGUI_EscolherDiretorioIndice_Indexar() {
		File diretorioIndice = doGUI_EscolherDiretorio();
		if (null != diretorioIndice) {
			this.textDiretorioIndice.setText(diretorioIndice.getAbsolutePath());
		}
	}

	private File doGUI_EscolherDiretorio() {
		DirectoryDialog dd = new DirectoryDialog(sShell, SWT.NONE);
		dd.setText("Escolha um diretório...");
		String path = dd.open();
		if (null != path) {
			File diretorio = new File(path);
			if (diretorio.exists() && diretorio.isDirectory()) {
				return diretorio;
			}
		}
		return null;
	}

	protected void doGUI_Indexar() throws Exception {
		String nomeDiretorioIndice = textDiretorioIndice.getText();
		String nomeDiretorioFonte = textDiretorioFonte.getText();
		File indiceDiretorio = new File(nomeDiretorioIndice);

		boolean recursivo = checkBoxRecursivo.getSelection();
		boolean refazer = checkBoxRefazerIndice.getSelection();

		if (nomeDiretorioFonte.length() == 0) {
			throw new GUIException("Informe o diretório a indexar!");
		}
		File srcDir = new File(nomeDiretorioFonte);
		if (!srcDir.exists() || (!srcDir.isDirectory())) {
			throw new GUIException("Verifique o diretório: "
					+ srcDir.getAbsolutePath());
		}

		limparLog();
		log("Criando índice em: " + indiceDiretorio.getAbsolutePath());
		log("Refazer: " + refazer);
		log("Originais: " + srcDir.getAbsolutePath());
		log("Recursivo: " + recursivo);
		doIndexar(indiceDiretorio, srcDir, recursivo, refazer);
	}

	protected void doGUI_Pesquisar() throws GUIException {
		log("Pesquisar...");
		String queryExpression = this.textQuery.getText();
		String indexDirName = this.textIndexDir.getText();

		if ((queryExpression.length() == 0)) {
			throw new GUIException("Informe a expressão de pesquisa!");
		}

		File indexDir = new File(indexDirName);
		if (!indexDir.exists()) {
			throw new GUIException("Indice: diretório não existe!");
		}
		if (!indexDir.isDirectory()) {
			throw new GUIException("Indice: caminho não é diretório.");
		}
		if (!indexDir.canRead()) {
			throw new GUIException("Indice: diretório não pode ser lido.");
		}

		Hits result = doPesquisar(indexDir, queryExpression);
		doGUI_AtualizaResultadosPesquisa(result);
		log("... pesquisa concluída.");

	}

  @SuppressWarnings("unchecked")
  private void doGUI_AtualizaResultadosPesquisa(Hits result)
			throws GUIException {

		try {

			log("Resultados: " + result.length());

			tableSearchResultsCounter.removeAll();
			Iterator<Hit> hitIterator = result.iterator();
			int counter = 0;
			while (hitIterator.hasNext()) {
				Hit hit = hitIterator.next();
				TableItem tabItem = new TableItem(tableSearchResultsCounter,
						SWT.NONE);
				tabItem.setText(0, String.valueOf(counter++));
				tabItem.setText(1, hit.get(Config
						.getProperty("nome.campo.nomearquivo")));
				tabItem.setText(2, hit.get(Config
						.getProperty("nome.campo.hash")));
			}
		} catch (IOException e) {
			throw new GUIException(e.getMessage());
		}
	}

	protected void doGUI_RemoverEntradas() throws GUIException {

		// Diretório do índice está indicado ?
		if (textIndexDir.getText().length() == 0) {
			throw new GUIException("Indice o diretório com o índice!");
		}
		File diretorioIndice = new File(textIndexDir.getText());
		if (!diretorioIndice.exists() || !diretorioIndice.isDirectory()
				|| !diretorioIndice.canRead()) {
			throw new GUIException("Verifique o diretório: "
					+ diretorioIndice.getAbsolutePath());
		}

		ArrayList<Integer> linhasRemover = new ArrayList<Integer>();
		int k = 0;
		for (TableItem ti : tableSearchResultsCounter.getItems()) {
			if (ti.getChecked()) {
				String nomeArquivo = ti.getText(1);
				try {
					GerenciadorIndice.remover(diretorioIndice, nomeArquivo);
					linhasRemover.add(k);
				} catch (IndiceException e) {
					throw new GUIException("Erro ao remover: " + nomeArquivo);
				}
			}
			++k;
		}

		int[] linhasRemoverA = new int[linhasRemover.size()];
		for (int j = 0; j < linhasRemover.size(); ++j) {
			linhasRemoverA[j] = linhasRemover.get(j);
		}
		tableSearchResultsCounter.remove(linhasRemoverA);

	}

	protected void doGUI_SubstituirEntradas() throws GUIException {
		// Diretório do índice está indicado ?
		if (textIndexDir.getText().length() == 0) {
			throw new GUIException("Indice o diretório com o índice!");
		}
		File diretorioIndice = new File(textIndexDir.getText());
		if (!diretorioIndice.exists() || !diretorioIndice.isDirectory()
				|| !diretorioIndice.canRead()) {
			throw new GUIException("Verifique o diretório: "
					+ diretorioIndice.getAbsolutePath());
		}

		int k = 0;
		for (TableItem ti : tableSearchResultsCounter.getItems()) {
			if (ti.getChecked()) {
				String nomeArquivo = ti.getText(1);
				String hashArquivoNoIndice = ti.getText(2);
				try {
					File arquivoAtual = new File(nomeArquivo);
					if (!arquivoAtual.exists()) {
						throw new GUIException("Não localizei o arquivo: "
								+ arquivoAtual.getAbsolutePath());
					}
					String hashArquivoAtual = GerenciadorIndice
							.getHash(arquivoAtual);
					if (hashArquivoNoIndice.equals(hashArquivoAtual)) {
						MessageBox msgBox = new MessageBox(sShell, SWT.OK);
						msgBox.setMessage("O arquivo " + arquivoAtual.getName()
								+ " não foi modificado. Ignorando.");
						msgBox.open();
					} else {
						GerenciadorIndice.substituir(diretorioIndice,
								arquivoAtual);
						ti.setText(2, hashArquivoAtual);
					}

				} catch (IndiceException e) {
					throw new GUIException("Erro ao substituir: " + nomeArquivo);
				}
			}
			++k;
		}

	}

	/**
	 * <P>
	 * Trata exceptions lançadas em métodos chamados por event-handlers
	 */
	private void handleExceptions(Exception e) {
		MessageBox msgBox = new MessageBox(this.sShell, SWT.OK);
		msgBox.setMessage(e.getMessage());
		msgBox.setText("Erro!");
		msgBox.open();
	}

	private void doIndexar(File diretorioIndice, File diretorioParaIndexar,
			boolean recursivo, boolean refazer) throws Exception {
		ThreadIndexador threadIndexador = new ThreadIndexador(refazer,
				recursivo, diretorioIndice, diretorioParaIndexar);
		Thread thread = new Thread(threadIndexador);
		thread.start();
	}

	private Hits doPesquisar(File diretorioIndice, String expressaoDePesquisa)
			throws GUIException {
		Hits hits = null;

		try {
			hits = GerenciadorIndice.pesquisar(diretorioIndice,
					expressaoDePesquisa);
		} catch (IndiceException e) {
			throw new GUIException("Erro ao pesquisar: " + e.getMessage());
		}
		return hits;
	}

	/**
	 * <P>
	 * 
	 */
	class ThreadIndexador implements Runnable {

		private boolean refazer;

		private boolean recursivo;

		private File diretorioIndice;

		private File diretorioParaIndexar;

		private CallbackLog callbackLog;

		private CallbackProgressBar callbackProgressBar;

		ThreadIndexador(boolean refazer, boolean recursivo,
				File diretorioIndice, File diretorioParaIndexar) {
			super();
			this.refazer = refazer;
			this.recursivo = recursivo;
			this.diretorioIndice = diretorioIndice;
			this.diretorioParaIndexar = diretorioParaIndexar;
			this.callbackLog = new CallbackLog();
			this.callbackProgressBar = new CallbackProgressBar();
		}

		private void log(String message) {
			callbackLog.setMessage(message);
			Display.getDefault().syncExec(callbackLog);
		}

		private void setBar(int selection) {
			callbackProgressBar.adjust(selection, 0);
			Display.getDefault().syncExec(callbackProgressBar);
		}

		private void resetBar() {
			callbackProgressBar.adjust(0, 0);
			Display.getDefault().syncExec(callbackProgressBar);
		}

		private void setBarMax(int maximum) {
			callbackProgressBar.adjust(0, maximum);
			Display.getDefault().syncExec(callbackProgressBar);
		}
		
		private void disableCancelButton() {		
			Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {							
							buttonIndexar_Interromper.setEnabled(false);
						}		
					});				
		}
		
		private void enableCancelButton() {
			Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							buttonIndexar_Interromper.setEnabled(true);
						}						
					});
		}

		@SuppressWarnings("unchecked")
		public void run() {

			try {
				HashMap<File, String> falhas = new HashMap<File, String>();

				// Se flag 'refazer o índice' setado, constrói um índice zerado
				if (refazer) {
					IndiceUtil.inicializaIndice(diretorioIndice);
				}

				// Usa commons.io para obter a lista de arquivos 
				// de extensões conhecidas
				Collection<File> listaDeArquivos = (Collection<File>) FileUtils
						.listFiles(diretorioParaIndexar, TipoArquivoHelper.getExtensoes(), recursivo);

				int numArquivos = listaDeArquivos.size();
				log("Arquivos: " + numArquivos);
				setBarMax(numArquivos);
				resetBar();
				int counter = 0;

				// Só faz sentido habilitar o botão de cancelar
				// no loop em que é feita a checagem...
				enableCancelButton();
				
				// Itera a lista de arquivos a indexar
				for (File arquivo : listaDeArquivos) {
					setBar(++counter);

					// Teste de sanidade: existe ? é arquivo ? pode ser lido ?
					if (!arquivo.exists() || !arquivo.isFile()
							|| !arquivo.canRead()) {
						continue;
					}

					log("Processando: " + arquivo.getName());

					// Obtem o tipo do arquivo pela extensão em seu nome
					TipoArquivo tipoArquivo = TipoArquivoHelper
							.getTipoArquivo(arquivo.getName().substring(
									arquivo.getName().lastIndexOf(".") + 1));

					if (null == tipoArquivo) {
						log("\tIgnorado.");
						continue;
					}

					// Solicita a indexação do arquivo.
					// As falhas são registradas no mapa 'falhas'

					try {
						long inicio = Calendar.getInstance().getTimeInMillis();
						log("\tIndexando...");
						GerenciadorIndice.adicionar(diretorioIndice, arquivo);
						long termino = Calendar.getInstance().getTimeInMillis();
						log("\tConcluído em (ms): " + (termino - inicio));
					} catch (IndiceException e) {
						falhas.put(arquivo, e.getMessage());
					}

					if (isThreadIndexacaoInterrompido()) {
						log("INTERROMPIDO PELO USUÁRIO!");												
						break;
					}

				} // itera sobre a lista de arquivos

				//
				// Mostra a lista de falhas na indexação
				//

				for (File falha : falhas.keySet()) {
					log("FALHA: " + falha.getAbsolutePath());
					log("EXCEP: " + falhas.get(falha));
				}

				
				
			} catch (Exception e) {
				// handleExceptions(e);
				System.err.println(e.getMessage());
			} finally {
				disableCancelButton();
			}
		}

	} // class ThreadIndexador

	class CallbackLog implements Runnable {
		private String message;

		void setMessage(String message) {
			this.message = message;
		}

		public void run() {
			log(message);
		}
	}

	class CallbackProgressBar implements Runnable {
		private int selection;

		private int maximum;

		void adjust(int selection, int maximum) {
			this.selection = selection;
			this.maximum = maximum;
		}

		public void run() {
			if (maximum > 0) {
				progressBarIndexacao.setMaximum(maximum);
			}
			progressBarIndexacao.setSelection(selection);
		}

	}

	private boolean threadIndexacaoInterrompido = false;

	private Composite compositeIndexar_Progresso_Interromper = null;

	private ProgressBar progressBarIndexacao = null;

	private Button buttonIndexar_Interromper = null;

	public synchronized boolean isThreadIndexacaoInterrompido() {
		boolean interrompido = this.threadIndexacaoInterrompido;
		if (this.threadIndexacaoInterrompido) {
			this.threadIndexacaoInterrompido = false;
		}
		return interrompido; 
	}

	public synchronized void setThreadIndexacaoInterrompido(boolean threadIndexacaoInterrompido) {
		this.threadIndexacaoInterrompido = threadIndexacaoInterrompido;
	}

} // class Main

/**
 * <P>
 * Exception usada para sinalizar erros no tratamento de eventos na interface
 * gráfica de usuário (GUI)
 */
class GUIException extends Exception {

  private static final long serialVersionUID = 1L;

  public GUIException(String message) {
		super(message);
	}
}
