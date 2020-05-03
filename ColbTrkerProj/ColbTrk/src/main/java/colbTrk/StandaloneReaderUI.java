package colbTrk;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Reader for standalone artifacts, just in case user wants to move the physical content 
 * outside the application and view
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class StandaloneReaderUI implements Runnable {

	CommonUIData commonUIData = null;
	Commons commons = null;

	Composite childCompositeOfLeftView = null;
	Group artifactDetailGroup = null;
	CCombo relevanceList;	
	Text artifactNameText;
	Button btnFileSelectButton;
	CCombo contentTypeList;
	private Shell mainShell = null;
	
	ContentHandlerSpecs contentHandlerSpecs = null;

	public StandaloneReaderUI(CommonUIData inCommonUIData) {
		System.out.println("At start of StandaloneReaderUI");

		commonUIData = inCommonUIData ;
		commons = commonUIData.getCommons();

	}

	public void run() {
		if (mainShell == null) {
			System.out.println("mainShell being set. prev value: " + mainShell);

			mainShell = new Shell(commonUIData.getColbTrkDisplay(), SWT.APPLICATION_MODAL | SWT.CLOSE
					| SWT.TITLE | SWT.BORDER | SWT.RESIZE);
			mainShell.setImage(new Image(commonUIData.getColbTrkDisplay(), commonUIData.getCommons().applicationIcon));
		} else {
			System.out.println("mainShell already set. prev value: "
					+ mainShell);
		}
		mainShell.setText("ArtifactWrapperUI");

		mainShell.setLayout(new GridLayout());
		displayContent();
	}

	public void displayContent() {

		childCompositeOfLeftView = new Composite(
				mainShell, SWT.NONE);
		childCompositeOfLeftView.setLayout(new FillLayout(SWT.VERTICAL));

		GridData gridDataLeft = new GridData(SWT.FILL, SWT.FILL, true, true);

		childCompositeOfLeftView.setLayoutData(gridDataLeft);		

		Image bg = new Image(commonUIData.getColbTrkDisplay(),
				commonUIData.getCommons().backgroundImagePathFileName);

		bg = new Image(commonUIData.getColbTrkDisplay(),
				commonUIData.getCommons().backgroundImagePathFileName);

		childCompositeOfLeftView.setBackgroundImage(bg);
		// displayContent() - base set up ends

		// displayContent() - artifactDetail Starts
		artifactDetailGroup = new Group(childCompositeOfLeftView,
				SWT.SHADOW_NONE);
		artifactDetailGroup.setText("ArtifactDetail");
		
		artifactDetailGroup.setLayout(new FillLayout(SWT.VERTICAL));

		// displayContent() - ArtifactNameGroup display starts
		Group artifactNameGroup = new Group(artifactDetailGroup, SWT.LEFT);
		artifactNameGroup.setLayout(new FillLayout());
		artifactNameGroup.setText("ArtifactName");
	    RowLayout rowLayout = new RowLayout();
	    rowLayout.pack = true;
	    rowLayout.justify = true;
	    artifactNameGroup.setLayout(rowLayout);
	    artifactNameGroup.pack();

		// displayContent() - ArtifactName display starts
		artifactNameText = new Text(artifactNameGroup, SWT.READ_ONLY);
		// displayContent() - ArtifactName display ends

		// fileSelect button starts
		btnFileSelectButton = new Button(artifactNameGroup, SWT.NONE);
		btnFileSelectButton.setText("FileSelect");
		btnFileSelectButton.setToolTipText("Click to select a file via dialog");
		btnFileSelectButton.setFocus();
		btnFileSelectButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("FileSelect selected");

				String filename = null;
				
				FileDialog dialog = new FileDialog(mainShell, SWT.OPEN);

				filename = dialog.open();
				
				if (filename == null) return;

		    	if (!commons.doesFileExist(filename)){
					MessageBox noFileMsgBox = new MessageBox(mainShell, SWT.OK);
					noFileMsgBox.setMessage("file doesn't exist : " + filename);
					int noFileMsgBoxRC = noFileMsgBox.open();
					if (noFileMsgBoxRC != SWT.YES) {
						return;
					}
		    	}
				System.out.println("File being viewed : " + filename);
				artifactNameText.setText(filename);
				artifactNameText.setToolTipText(filename);
				
				contentTypeList.setFocus();
				
				artifactNameGroup.pack();
				artifactDetailGroup.pack();
				childCompositeOfLeftView.pack();
				mainShell.pack();
			}
		});

		artifactNameGroup.pack();
		artifactDetailGroup.pack();
		// fileSelect button ends
		// displayContent() - ArtifactNameGroup display ends
		
		
		// displayContent() - ContentType display starts
		Group contentTypeGroup = new Group(artifactDetailGroup, SWT.LEFT);
		contentTypeGroup.setLayout(new FillLayout());
		contentTypeGroup.setText("ContentType");
		contentTypeList = new CCombo(contentTypeGroup,
				SWT.DROP_DOWN | SWT.READ_ONLY);

		contentTypeList
				.setItems(commonUIData.getContentTypes());
		contentTypeList.select(0);
		// displayContent() - ContentType display ends


		// view as well as edit, upload, backup on cloud, make clone
		// For non-authors, it provides ability to view, make clone
		//final Group actionsGrp = new Group(childCompositeOfLeftView, SWT.SHADOW_NONE);
		Group actionsGrp = new Group(artifactDetailGroup, SWT.SHADOW_NONE);		
		actionsGrp.setText("Actions");
		actionsGrp.setLayout(new FillLayout());

		// displayContent() - view button process starts

		System.out.println("ArtifactWrapperUI displayContent() - view button process starts");

		Button btnViewButton = new Button(actionsGrp, SWT.CENTER);
		btnViewButton.setText("View");
		btnViewButton.setToolTipText("View the artifact details");
		
		btnViewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				System.out.println("btnViewButton - widgetSelected");
				
		    	if (artifactNameText.getText().isEmpty()){

		    		MessageBox noFileMsgBox = new MessageBox(mainShell, SWT.OK);
					noFileMsgBox.setMessage("Select the filename you want to view");
					int noFileMsgBoxRC = noFileMsgBox.open();
					if (noFileMsgBoxRC != SWT.YES) {
						btnFileSelectButton.setFocus();
						return;
					}		    		
		    	}
				
		    	if (contentTypeList.getSelectionIndex() == -1){

		    		MessageBox noFileMsgBox = new MessageBox(mainShell, SWT.OK);
					noFileMsgBox.setMessage("Select the contentType associated with the file");
					int noFileMsgBoxRC = noFileMsgBox.open();
					if (noFileMsgBoxRC != SWT.YES) {
						contentTypeList.setFocus();
						return;
					}			    		
		    	}

		    	String selectedContentType = commonUIData.getContentTypes()[contentTypeList.getSelectionIndex()];
		    	contentHandlerSpecs = commonUIData.getContentHandlerSpecsMap().get(selectedContentType);
		    	
				if (!contentHandlerSpecs.hasSpecialHandler) {
					try {
						commonUIData.getCommons().openFileToView(artifactNameText.getText());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| IOException e1) {
						ErrorHandler.showErrorAndQuit(commons, "Error in ARtifactWrapperUI displayContent ", e1);
					}
				} else {
					ContentHandlerInterface contentHandlerObjectInterface = null;
					
					try {

						System.out.println("before initializing ContentHandler fullPathFileNameString = " + artifactNameText.getText());
						System.out.println("contentHandlerSpecs " + contentHandlerSpecs);
						System.out.println("contentHandlerSpecs.className " + contentHandlerSpecs.handlerClass);
						System.out.println("commons " + commonUIData.getCommons());

						contentHandlerObjectInterface = ContentHandlerManager.getInstance(commonUIData.getCommons(), commonUIData.getCatelogPersistenceManager(), selectedContentType);

						System.out.println("contentHandlerObjectInterface : " + contentHandlerObjectInterface);

						System.out.println("About to initialize for view file. contentHandlerObjectInterface = " + contentHandlerObjectInterface);
						if (contentHandlerObjectInterface
						.initializeContentHandlerForStandaloneReader(commonUIData, artifactNameText.getText(), selectedContentType)) {
							System.out.println("Initialized for standalone view");
							contentHandlerObjectInterface.viewContentsAtDesk();
						}

					} catch (IOException e2) {

						ErrorHandler.showErrorAndQuit(commons, "Error in ARtifactWrapperUI displayContent ", e2);							
					}
				}
			}
		});
		// displayContent() - view button process ends

		childCompositeOfLeftView.pack();
		mainShell.pack();
		mainShell.open();
		
		while (!mainShell.isDisposed()) {
			if (!commonUIData.getColbTrkDisplay().readAndDispatch()) {
				if (commonUIData.getArtifactDisplayOkayToContinue()) {
					commonUIData.getColbTrkDisplay().sleep();
				} else {
					break;
				}
			}
		}
		System.out.println("end of......displayContent");
		// displayContent() - final prep before display ends
	}
}