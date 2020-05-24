package colbTrk;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import commonTechs.DisplayKeeper;

/**
 * User's desktop side UI. Provides option for 
 * 	1) Reading Standalone artifacts previously generated
 *  2) View Catalog of artifacts available at doc center
 *  3) initiate Client Orchestrator to sync up with doc center
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ColbTrkClientUI {

	Display mainDisplay;
	Shell mainShell;
	Button btnCatalogUI;
	Button btnStandaloneReaderUI;
	Button btnClientOrchestrator;

	Thread catalogDisplayThread;
	Thread espotClientOrchestratorThread;
	
	ColbTrkClientOrchestrator espotClientOrchestrator;
	ArrayList<StandaloneReaderUI> standaloneRdrUIs;
	CatalogDisplay catalogDisplay;
	
	Commons commons;

    final static int MIN_SHELL_WIDTH = 600;
    final static int MIN_SHELL_HEIGHT = 200;	
	
	public ColbTrkClientUI() {
		System.out.println("test test test");

		try {
			commons = Commons.getInstance(Commons.CLIENT_MACHINE);
		} catch (IOException | ParseException e) {
			Commons.logger.error("CollabTrackClientUI error creating commons");
		}

		mainDisplay = DisplayKeeper.getDisplay();
				
		mainShell = new Shell(mainDisplay,SWT.APPLICATION_MODAL
				|SWT.CLOSE|SWT.TITLE|SWT.BORDER|SWT.RESIZE|SWT.MAX|SWT.MIN);
		mainShell.setImage(new Image(mainDisplay, commons.applicationIcon));
		
		mainShell.setLayout(new GridLayout(1, false));
		mainShell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		mainShell.setText("CollabTracker of " + System.getProperty("user.name"));		
		mainShell.setToolTipText("CollabTracker of " + System.getProperty("user.name"));

		mainShell.setMinimumSize(
	            MIN_SHELL_WIDTH,
	            MIN_SHELL_HEIGHT);		
	}
	
	public void displayCollabTrackClient() {

		btnCatalogUI = new Button(mainShell, SWT.NONE);
		btnCatalogUI.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Catalog Display UI selected");
				btnCatalogUI();
			}
		});
		btnCatalogUI.setText("Catalog UI");
		btnCatalogUI.setToolTipText("Bring Up Catalog UI");
		btnCatalogUI.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		btnClientOrchestrator = new Button(mainShell, SWT.NONE);
		btnClientOrchestrator.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("btnClientOrchestrator being invoked");
				btnClientOrchestratorProcess();
			}
		});
		btnClientOrchestrator.setText("Client Orchestrator");
		btnClientOrchestrator.setToolTipText("Start Client Orchestrator");
		btnClientOrchestrator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		btnStandaloneReaderUI = new Button(mainShell, SWT.NONE);
		btnStandaloneReaderUI.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Standalone Reader UI selected");
				btnStandaloneReaderUI();
			}
		});
		btnStandaloneReaderUI.setText("Standalone Reader UI");
		btnStandaloneReaderUI.setToolTipText("Bring Up Standalone Reader UI");
		btnStandaloneReaderUI.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		mainShell.pack();
		mainShell.open();

		
		mainShell.addListener(SWT.Close, new Listener() {
		      public void handleEvent(Event event) {
		    	  
		  		MessageBox closeConfirmMsgBox = new MessageBox(mainShell,
						SWT.ICON_WARNING | SWT.YES | SWT.NO);
				closeConfirmMsgBox.setMessage("HAVE YOU ENSURED ALL ARTIFACTs ARE SAVED?");
				int rc = closeConfirmMsgBox.open();
				if (rc != SWT.YES) {
					event.doit = false;
				}
		      }
		    });

		btnClientOrchestratorProcess();	// auto triggering client orchestrator to ensure content sync up
		//comments this auto opens and leaving upto user to choose
		//btnCatalogUI();

		shellDisposeHolder();
	}

	private void shellDisposeHolder() {
		while (!mainShell.isDisposed()) {
			if (mainDisplay.readAndDispatch()) {

				mainDisplay.sleep();
			}
		}
		System.out.println("here disposing....");

		if (standaloneRdrUIs != null) {
			for (StandaloneReaderUI standaloneReaderUI : standaloneRdrUIs) {
				standaloneReaderUI.commonUIData.setArtifactDisplayOkayToContinue(false);
			}
		}
		if (catalogDisplay != null) {
			catalogDisplay.commonUIData.setArtifactDisplayOkayToContinue(false);
		}
		if (espotClientOrchestrator != null) {
			espotClientOrchestrator.orchestrationData.setOkayToContinue(false);
		}

		if (!mainShell.isDisposed()) {
			mainShell.dispose();
		}
	}
	
	public void btnCatalogUI() {
		//if (catalogDisplayThread != null && catalogDisplayThread.isAlive()){
		if (catalogDisplayThread != null && catalogDisplayThread.getState() != Thread.State.TERMINATED){
			MessageBox messageBox1 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
			messageBox1.setMessage("CatalogDisplay already open");
			messageBox1.open();
			return;
		//} else if (espotClientOrchestratorThread == null || !espotClientOrchestratorThread.isAlive()) {
		} else if (espotClientOrchestratorThread == null || espotClientOrchestratorThread.getState() == Thread.State.TERMINATED) {
			MessageBox messageBox2 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
			messageBox2.setMessage("Ensure ClientOrchestrator is started to sync up with Doc Center");
			messageBox2.open();
		}

		CatalogDownloadDtlsHandler catalogDownloadDtlsHandler = CatalogDownloadDtlsHandler.getInstance(commons);
		String catalogDownLoadedFileName = catalogDownloadDtlsHandler.getCatalogDownLoadedFileNameIfAvailable(commons.getCurrentRootNick());
		
		if (catalogDownLoadedFileName == null) {
			MessageBox messageBox1 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
			messageBox1.setMessage("Catalogs not downLoaded yet. Please try few min after initiating Client Orchestrator.");
			messageBox1.open();
			return;			
		}		

		Commons.logger.info(" Catalog Display Started at " + commons.getCurrentTimeStamp());

		CommonUIData commonUIData = (CommonUIData) CommonUIData.getUIInstance(commons);
		catalogDisplay = new CatalogDisplay(commonUIData);
		catalogDisplayThread = new Thread(catalogDisplay);
		catalogDisplayThread.start();
	}

	public void btnStandaloneReaderUI(){

		Commons.logger.info("StandaloneReaderUI Started at " + commons.getCurrentTimeStamp());

		CommonUIData commonUIData = (CommonUIData) CommonUIData.getUIInstance(commons);

		StandaloneReaderUI standaloneReaderUI = new StandaloneReaderUI(commonUIData);
		Thread standaloneReaderThread = new Thread(standaloneReaderUI);
		standaloneReaderThread.start();

		if (standaloneRdrUIs == null) {
			standaloneRdrUIs = new ArrayList<StandaloneReaderUI>();
		}
		standaloneRdrUIs.add(standaloneReaderUI);
	}
	
	public void btnClientOrchestratorProcess() {
		//if (espotClientOrchestratorThread != null && espotClientOrchestratorThread.isAlive()){		
		if (espotClientOrchestratorThread != null && espotClientOrchestratorThread.getState() != Thread.State.TERMINATED) {
			MessageBox messageBox1 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
			messageBox1.setMessage("ClientOrchestrator already active");
			messageBox1.open();
			return;
		}
		
		Commons.logger.info(" ClientOrchestrator Started at " + commons.getCurrentTimeStamp());

		espotClientOrchestrator = new ColbTrkClientOrchestrator(commons);
		espotClientOrchestratorThread = new Thread(espotClientOrchestrator);
		espotClientOrchestratorThread.start();
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		
		ColbTrkClientUI collabTrackClientUI = new ColbTrkClientUI();
		collabTrackClientUI.displayCollabTrackClient();
	}
}