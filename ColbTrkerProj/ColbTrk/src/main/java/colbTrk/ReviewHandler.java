package colbTrk;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Review handling UI for an artifact or an item within.
 * Provides a plug in convenience across UI screens.
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ReviewHandler {

	public static final int PREFERED_REVIEW_PANEL_WIDTH = 600;
	public static final int PREFERED_REVIEW_HEIGHT = 100;
	private static final String hideReviewLIT = "HideReview";

	private Composite wrappingExtlComposite;
	private Composite extlLevel2Wrapper;
	private Composite extlLevel3Wrapper;
	
	private Composite reviewFrameOutmostScroller;
	private Composite reviewFrameOutmostWrapper;	// in grid layout
	private Composite reviewContentScroller; 		// in grid layout
	private Composite reviewContntHolder; 			// in form layout

	private CommonUIData commonUIData;

	private DownloadedReviewsHandler downloadedReviewsHandler = null;

	private UsersDisplay authorsDisplay = null;
	private UsersDisplay requestorDisplay = null;	
	private Shell outerMainShell = null;
	private ERLStatusDisplay erlStatusDisplay = null;
	private boolean reviewVisible = true;
	private Group hideReviewGrp = null;	
	private Text newCommentText = null;
	//private ContentHandlerSpecs reviewERLContentHandlerSpecs = null;
	private boolean baseItemIsRollupType = false;

	private ArtifactKeyPojo reviewArtifactKeyPojo = null;

	private String reviewOf = "";	// this is artifactName for non-rollup artifacts and
											// - itemID for rollup child.
	private String reviewERLRelevance = "";
	private String reviewERLContentType = "";

	private String reviewERLStatus = "";
	private String reviewERLRequestor = "";
	private String reviewERLAuthor = "";

	public ReviewHandler(CommonUIData inCommonUIData,
			Composite inWrappingComposite,
			Composite inLevel2Wrapper,
			Composite inLevel3Wrapper,
			ArtifactPojo inArtifactPojo, 
			Shell inMainShell) {

		initReviewHandler(inCommonUIData,
				inWrappingComposite,
				inLevel2Wrapper,
				inLevel3Wrapper,
				inArtifactPojo, 
				null,
				inMainShell);
	}

	public ReviewHandler(CommonUIData inCommonUIData,
			Composite inWrappingComposite,
			Composite inLevel2Wrapper,
			Composite inLevel3Wrapper,
			ArtifactPojo inArtifactPojo, 
			ItemPojo inItemPojo,
			Shell inMainShell){
		initReviewHandler(inCommonUIData,
				inWrappingComposite,
				inLevel2Wrapper,
				inLevel3Wrapper,
				inArtifactPojo, 
				inItemPojo,
				inMainShell);		
	}
	
	public void initReviewHandler(CommonUIData inCommonUIData,
							Composite inWrappingComposite,
							Composite inLevel2Wrapper,
							Composite inLevel3Wrapper,
							ArtifactPojo inArtifactPojo, 
							ItemPojo inItemPojo,
							Shell inMainShell)
	{
		commonUIData = inCommonUIData;
		wrappingExtlComposite = inWrappingComposite;
		extlLevel2Wrapper = inLevel2Wrapper;
		extlLevel3Wrapper = inLevel3Wrapper;

		Commons.logger.info("ReviewHandler initReviewHandler starting for "
								+ inArtifactPojo.artifactKeyPojo.artifactName);

		baseItemIsRollupType = false;
		
		if (inItemPojo!= null) {
			if (commonUIData.getContentHandlerSpecs(inItemPojo.contentType).rollupAddupType) {
				reviewArtifactKeyPojo = inArtifactPojo.artifactKeyPojo;
				reviewOf = inItemPojo.itemID;
				reviewERLRelevance = inItemPojo.relevance;
				reviewERLContentType = inItemPojo.contentType;
				reviewERLStatus = inItemPojo.status;
				reviewERLRequestor = inItemPojo.requestor;
				reviewERLAuthor = inItemPojo.author;
				if (commonUIData.getContentHandlerSpecs(inItemPojo.contentType).rollupType){
					baseItemIsRollupType = true;
				}
			} else {
			// for non rollup types use the item pojo's referred ERL for details
				reviewArtifactKeyPojo = new ArtifactKeyPojo(inArtifactPojo.artifactKeyPojo.rootNick, 
															inItemPojo.relevance,
															inItemPojo.artifactName,
															inItemPojo.contentType);
				reviewOf = inItemPojo.artifactName;
				reviewERLRelevance = inItemPojo.relevance;
				reviewERLContentType = inItemPojo.contentType;
				reviewERLStatus = inItemPojo.status;
				reviewERLRequestor = inItemPojo.requestor;
				reviewERLAuthor = inItemPojo.author;			

				ERLDownload erlDownLoad = commonUIData.getCatelogPersistenceManager().readERLDownLoad(
																				reviewArtifactKeyPojo);
				if (erlDownLoad!=null) {	
					System.out.println("At initReviewHandler erlDownLoad of non-rollup individual item is " + erlDownLoad);						
					reviewERLStatus = erlDownLoad.erlStatus;
					reviewERLRequestor = erlDownLoad.requestor;
					reviewERLAuthor = erlDownLoad.author;
				}
			}					
		} else {
			reviewArtifactKeyPojo = inArtifactPojo.artifactKeyPojo;
			reviewOf = reviewArtifactKeyPojo.artifactName;
			reviewERLRelevance = reviewArtifactKeyPojo.relevance;
			reviewERLContentType = reviewArtifactKeyPojo.contentType;
			reviewERLStatus = inArtifactPojo.erlStatus;
			reviewERLRequestor = inArtifactPojo.requestor;
			reviewERLAuthor = inArtifactPojo.author;			
		}
		
		downloadedReviewsHandler = new DownloadedReviewsHandler(inCommonUIData, reviewArtifactKeyPojo);
		//reviewERLContentHandlerSpecs = commonUIData.getContentHandlerSpecs(reviewERLContentType);

		outerMainShell = inMainShell;
		System.out.println("@ReviewHandler reviewArtifactKeyPojo = " + reviewArtifactKeyPojo);
		System.out.println("@ReviewHandler reviewOf = " + reviewOf);
		System.out.println("@ReviewHandler reviewERLRelevance = " + reviewERLRelevance);
		System.out.println("@ReviewHandler reviewERLContentType = " + reviewERLContentType);
		System.out.println("@ReviewHandler reviewERLStatus = " + reviewERLStatus);
		System.out.println("@ReviewHandler reviewERLRequestor = " + reviewERLRequestor);
		System.out.println("@ReviewHandler reviewERLAuthor = " + reviewERLAuthor);

		reviewFrameOutmostScroller = new Composite(wrappingExtlComposite,SWT.BORDER);
		GridData gridDataOuterScrl = new GridData(SWT.FILL, SWT.FILL, true, true);
		reviewFrameOutmostScroller.setLayoutData(gridDataOuterScrl);	// setting layout parameters for itself
		reviewFrameOutmostScroller.setLayout(new GridLayout(1,false));			// setting layout to be used by children
		
		reviewFrameOutmostWrapper = new Composite(reviewFrameOutmostScroller, SWT.NONE);
		GridData gridDatagridDataOuterWrpr = new GridData(SWT.FILL, SWT.FILL, true, true);
		reviewFrameOutmostWrapper.setLayoutData(gridDatagridDataOuterWrpr);
		reviewFrameOutmostWrapper.setLayout(new RowLayout());

		hideReviewGrp = new Group(reviewFrameOutmostWrapper, SWT.NONE);		
		hideReviewGrp.setLayoutData(new RowData());
		hideReviewGrp.setLayout(new FillLayout(SWT.VERTICAL));

		Button hideReviewButton = new Button(hideReviewGrp, SWT.PUSH);
		hideReviewButton.setText(hideReviewLIT);
		hideReviewButton.setToolTipText("Hide this review pane to make room in screen");
		hideReviewButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (reviewVisible) {

					System.out.println("reviewVisible");
											
					if (newCommentText.getEditable() && !newCommentText.getText().equalsIgnoreCase("")) {
						MessageBox WarningMessageBox = new MessageBox(outerMainShell,
								SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
						WarningMessageBox.setMessage("The keyed in text will be lost if hidden. Proceed?");
						if (WarningMessageBox.open() == SWT.CANCEL) {
							System.out.println("Cancel chosen. returning");
							return;
						} else {
							System.out.println("Hide confirmed. Proceeding");
						}
					} else {
						System.out.println("review not editable or empty");
					}
					((Button) event.getSource()).setText("ShowReview");
					System.out.println("Hiding Review");
					Control[] oldControls = reviewContentScroller.getChildren();
					for (Control oldControl : oldControls) {
					    oldControl.dispose();
					}
					reviewContentScroller.dispose();
					reviewVisible = false;

					hideReviewGrp.pack();
					reviewFrameOutmostWrapper.pack();
					reviewFrameOutmostScroller.pack();
					wrappingExtlComposite.pack();

					if (extlLevel2Wrapper != null) {
						extlLevel2Wrapper.pack();
						if (extlLevel3Wrapper != null){
							extlLevel3Wrapper.pack();							
						}
					}					
					//wrappingExtlComposite.layout(true);
				} else {
					((Button) event.getSource()).setText(hideReviewLIT);
					displayContent();
					reviewVisible = true;
				}
				outerMainShell.pack();
				outerMainShell.layout(true);
			}
		});
		hideReviewButton.pack();
		hideReviewGrp.pack();
		reviewFrameOutmostWrapper.pack();
		reviewFrameOutmostScroller.pack();
		wrappingExtlComposite.pack();
		if (extlLevel2Wrapper != null) {
			extlLevel2Wrapper.pack();
			if (extlLevel3Wrapper != null){
				extlLevel3Wrapper.pack();							
			}
		}
	}

	public void displayContent() {
		
		System.out.println("at 1 displayContent of reviewHandler");
		reviewContentScroller = new Composite(reviewFrameOutmostWrapper,SWT.BORDER);
		reviewContentScroller.setLayoutData(new RowData());
		reviewContentScroller.setLayout(new GridLayout(1,false));
		
		reviewContntHolder = new Composite(reviewContentScroller, SWT.NONE); 
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		reviewContntHolder.setLayoutData(gridData);
		reviewContntHolder.setLayout(new FormLayout());

		Group prevCommentGrp = new Group(reviewContntHolder, SWT.V_SCROLL | SWT.H_SCROLL);
		prevCommentGrp.setLayout(new FillLayout(SWT.VERTICAL));
		prevCommentGrp.setText("PrevComment");
		Text prevCommentText = new Text(prevCommentGrp, 
				SWT.WRAP | SWT.READ_ONLY | SWT.LEFT | SWT.V_SCROLL | SWT.H_SCROLL);
		System.out.println("11 x = " + prevCommentText.getSize().x);
		System.out.println("11 y = " + prevCommentText.getSize().y);
		prevCommentText.pack();
		System.out.println("22 x = " + prevCommentText.getSize().x);
		System.out.println("22 y = " + prevCommentText.getSize().y);

		int prevCommentHeight = 0;
		if (downloadedReviewsHandler.getArtifactAllReviewsPojo() != null &&  downloadedReviewsHandler.getArtifactAllReviewsPojo().getItemAllReviews(reviewOf) != null) {
			System.out.println("prevCommentText = " + prevCommentText);
			System.out.println("artifactAllReviewsPojo = " + downloadedReviewsHandler.getArtifactAllReviewsPojo());
			System.out.println("artifactAllReviewsPojo.itemsReviews = " + downloadedReviewsHandler.getArtifactAllReviewsPojo().itemsReviews);
			System.out.println("reviewArtifactKeyPojo = " + reviewArtifactKeyPojo);
			System.out.println("reviewOf = " + reviewOf);
			prevCommentText.setText(downloadedReviewsHandler.getArtifactAllReviewsPojo().getItemAllReviews(reviewOf));
			prevCommentHeight = PREFERED_REVIEW_HEIGHT;
		} else {
			prevCommentText.setText("-None-");
		}
		prevCommentText.pack();
		
		FormData formData = new FormData();
		if (prevCommentHeight > 0) {
			formData.height = PREFERED_REVIEW_HEIGHT;
		}
		formData.width = PREFERED_REVIEW_PANEL_WIDTH;
		formData.top = new FormAttachment(reviewContntHolder, 0, SWT.TOP);;
		prevCommentGrp.setLayoutData(formData);
		prevCommentGrp.pack();
		Group lastReviewWdgtGrp = prevCommentGrp;

		System.out.println("33 x = " + prevCommentText.getSize().x);
		System.out.println("33 y = " + prevCommentText.getSize().y);
		
		final Group newCommentGrp = new Group(reviewContntHolder, SWT.READ_ONLY | SWT.MAX);
		newCommentGrp.setLayout(new FillLayout(SWT.VERTICAL));
		newCommentGrp.setText("NewComment");

		newCommentText = new Text(newCommentGrp, SWT.WRAP | SWT.LEFT | SWT.H_SCROLL | SWT.V_SCROLL);

		ClientSideNew_ReviewPojo newReviewPojo = null;
		try {
			newReviewPojo = commonUIData.getCatelogPersistenceManager().readReview(reviewArtifactKeyPojo,reviewOf);

			System.out.println("@@2r4 artifactKeyPojo = " + newReviewPojo);
			
			if (newReviewPojo == null) {
				newCommentText.setText("");
			} else {
				System.out.println("@@2r4 artifactKeyPojo.artifactKeyPojo = " + newReviewPojo.artifactKeyPojo);
				System.out.println("new comment already exists at:" + newReviewPojo.reviewFileName);

				System.out.println("commons:" + commonUIData.getCommons());
				System.out.println("commons.defaultUIRootNick:" + commonUIData.getCommons().getCurrentRootNick());
				System.out.println("reviewPojo" + newReviewPojo);
				System.out.println("reviewPojo.artifactKeyPojo" + newReviewPojo.artifactKeyPojo);
				System.out.println("reviewPojo.artifactKeyPojo.relevance" + newReviewPojo.artifactKeyPojo.relevance);
				System.out.println("reviewPojo.localFileName:" + newReviewPojo.reviewFileName);
				
				String localReviewFullPathFileName = commonUIData.getCommons().getFullLocalPathFileNameOfNewReview(commonUIData.getCommons().getCurrentRootNick(), newReviewPojo.artifactKeyPojo.relevance, newReviewPojo.reviewFileName);

				Document reviewXMLDocument = commonUIData.getCommons().getDocumentFromXMLFile(localReviewFullPathFileName);
				newReviewPojo.buildReviewPojoFromDocument(reviewXMLDocument);		
				newCommentText.setText(newReviewPojo.description);
				newCommentText.setEditable(false);
			}
		} catch (SAXException | IOException | ParserConfigurationException e1) {
			ErrorHandler.showErrorAndQuit(commonUIData.getCommons(), "Error in ReviewHandler displayContent", e1);
		}

		formData = new FormData();
		formData.top = new FormAttachment(lastReviewWdgtGrp,0,SWT.BOTTOM);
		formData.height = PREFERED_REVIEW_HEIGHT;
		formData.width = PREFERED_REVIEW_PANEL_WIDTH;
		newCommentText.pack();
		newCommentGrp.setLayoutData(formData);

		newCommentGrp.pack();
		lastReviewWdgtGrp = newCommentGrp;

		final Group reviewActionsGrp = new Group(reviewContntHolder, SWT.READ_ONLY);
		reviewActionsGrp.setLayout(new FillLayout(SWT.VERTICAL));
		reviewActionsGrp.setText("ReviewActions");

		formData = new FormData();
		formData.top = new FormAttachment(lastReviewWdgtGrp,0,SWT.BOTTOM);
		reviewActionsGrp.setLayoutData(formData);

		System.out.println("before reassign display:");
		System.out.println("before reassign display: reviewERLRequestor is " + reviewERLRequestor);
		System.out.println("before reassign display: userName is " + commonUIData.getCommons().userName);

		if (commonUIData.getUsersHandler().doesUserHaveUpdateRightsOverMember(
												commonUIData.getCommons().userName,
												reviewERLAuthor)) {			
			if (newReviewPojo != null) {
				if (newReviewPojo.reassignedAuthor != null) {
					System.out.println("before reassign display2: reviewPojo.reassignedAuthor is " + newReviewPojo.reassignedAuthor);
					authorsDisplay = new UsersDisplay(commonUIData.getUsersHandler(),reviewActionsGrp,newReviewPojo.reassignedAuthor,false,UsersDisplay.AUTHOR_REASSIGN_TEXT);
				}
			} else {
				System.out.println("before reassign display2: reviewERLAuthor is " + reviewERLAuthor);
				authorsDisplay = new UsersDisplay(commonUIData.getUsersHandler(),reviewActionsGrp,reviewERLAuthor,true,UsersDisplay.AUTHOR_REASSIGN_TEXT);
			}

			if (newReviewPojo != null) {
				if (newReviewPojo.reassignedRequestor != null) {
					System.out.println("before reassign display2: reviewPojo.reassignedRequestor is " + newReviewPojo.reassignedRequestor);
					requestorDisplay = new UsersDisplay(commonUIData.getUsersHandler(),reviewActionsGrp,newReviewPojo.reassignedRequestor,false,UsersDisplay.REQUESTOR_REASSIGN_TEXT);
				}
			} else {
				System.out.println("before reassign display2: reviewERLRequestor is " + reviewERLRequestor);
				requestorDisplay = new UsersDisplay(commonUIData.getUsersHandler(),reviewActionsGrp,reviewERLRequestor,true,UsersDisplay.REQUESTOR_REASSIGN_TEXT);
			}
		}
		
		///////////////// NEW ERL status starts
		final Group erlStatusGroup = new Group(reviewActionsGrp, SWT.CENTER);
		erlStatusGroup.setLayout(new FillLayout());

		formData = new FormData();
		formData.top = new FormAttachment(lastReviewWdgtGrp,0,SWT.BOTTOM);
		reviewActionsGrp.setLayoutData(formData);

		ERLStatusDisplay currentERLStatusDisplay = new ERLStatusDisplay(null, erlStatusGroup, reviewERLStatus, false, ERLStatusDisplay.CURR_STATUS_TEXT);
		
		String[] validActions = null;
		
		boolean itemInDeleteAllowedState = false;
		
		System.out.println("About to check the delete allowed status for rollup child. Curr state " + reviewERLStatus);

		
		if (reviewERLStatus!= null && reviewERLStatus.equalsIgnoreCase(ArtifactPojo.ERLSTAT_INACTIVE)) {
		// Providing a clean up process for rollup artifacts that may clutter in a long period.
		// The autoarchival process only look at Artifact level status for archiving
			itemInDeleteAllowedState = true;
		}

		System.out.println("After check of delete allowed status for rollup child. rollupItemInDeleteAllowedState " + itemInDeleteAllowedState);
		System.out.println("Requestor check true for user " + commonUIData.getCommons().userName);
		System.out.println("Requestor check true for requestor " + reviewERLRequestor);
		
		if (commonUIData.getUsersHandler().doesUserHaveSuperuserRightsOverMember(
													commonUIData.getCommons().userName,
													reviewERLAuthor)) {
			System.out.println("taking path 1 ");
			if (baseItemIsRollupType && itemInDeleteAllowedState) {
				System.out.println("taking path 2 ");
				validActions = ArtifactPojo.ADMIN_VALID_ROLLUPITEM_ACTIONS;
			} else {				
				System.out.println("taking path 3 ");
				validActions = ArtifactPojo.ADMIN_VALID_ACTIONS;				
			}
		} else if (commonUIData.getCommons().userName.equalsIgnoreCase(reviewERLRequestor)){
			System.out.println("taking path 4 ");
			if (baseItemIsRollupType && itemInDeleteAllowedState) {
				System.out.println("taking path 5 ");
				validActions = ArtifactPojo.REQUESTOR_VALID_ROLLUPITEM_ACTIONS;
			} else {				
				System.out.println("taking path 6 ");
				validActions = ArtifactPojo.REQUESTOR_VALID_ACTIONS;
			}
		} else if (commonUIData.getCommons().userName.equalsIgnoreCase(reviewERLAuthor)){
			System.out.println("taking path 7 ");
			if (baseItemIsRollupType && itemInDeleteAllowedState) {
				System.out.println("taking path 8 ");
				validActions = ArtifactPojo.AUTHOR_VALID_ROLLUPITEM_ACTIONS;
			} else {
				System.out.println("taking path 9 ");
				validActions = ArtifactPojo.AUTHOR_VALID_ACTIONS;				
			}				
		}
		System.out.println("taking path 10 validActions " + validActions);

		if (commonUIData.getUsersHandler().doesUserHaveUpdateRightsOverMember(
													commonUIData.getCommons().userName,
													reviewERLAuthor)) {			
			if (newReviewPojo !=null) {
				if (newReviewPojo.newERLStatus != null) {
					erlStatusDisplay = new ERLStatusDisplay(validActions, erlStatusGroup, newReviewPojo.newERLStatus, false, ERLStatusDisplay.STATUS_ASSIGN_TEXT);
				}
			} else {
				erlStatusDisplay = new ERLStatusDisplay(validActions, erlStatusGroup, reviewERLStatus, true, ERLStatusDisplay.STATUS_ASSIGN_TEXT);
			}
		}
		///////////////// New ERL status ends

		Button submitReviewButton = new Button(reviewActionsGrp, SWT.PUSH);
		submitReviewButton.setText("SubmitReview");
		submitReviewButton.setToolTipText("Submit the review and any change to ownership and status.");
		
		if (newReviewPojo != null) {
			submitReviewButton.setEnabled(false);
			submitReviewButton.setText("Submitted already");
		} else {
			submitReviewButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
	
					try {
						if (newCommentText.getText() == null ||
								newCommentText.getText().isEmpty()){
							MessageBox emptyCommentErrorBox = new MessageBox(outerMainShell,
									SWT.ICON_WARNING | SWT.OK);
							emptyCommentErrorBox.setMessage("Please enter your comment");
							emptyCommentErrorBox.open();
							newCommentText.setFocus();
							return;
						}
						((Button) event.getSource()).setEnabled(false);
						System.out.println("Saving Remarks");
						
						ClientSideNew_ReviewPojo clientSideNew_ReviewPojo = new ClientSideNew_ReviewPojo(
								commonUIData.getCommons(), reviewArtifactKeyPojo,reviewOf,commonUIData.getCommons().userName,commonUIData.getCommons().getCurrentTimeStamp());
						if (authorsDisplay!=null && authorsDisplay.userText != null
							&& !authorsDisplay.userText.getText().equalsIgnoreCase(reviewERLAuthor)) {
							System.out.println("Inside Author display reassign sel Auth is " + authorsDisplay.userText.getText());
							clientSideNew_ReviewPojo.reassignedAuthor = authorsDisplay.userText.getText();
						} else {
							clientSideNew_ReviewPojo.reassignedAuthor = "";
						}
						if (requestorDisplay!=null && requestorDisplay.userText!=null
							&& !requestorDisplay.userText.getText().equalsIgnoreCase(reviewERLRequestor)){
							System.out.println("Inside requestor display reassign sel requestor is " + requestorDisplay.userText.getText());
							clientSideNew_ReviewPojo.reassignedRequestor = requestorDisplay.userText.getText();
						} else {
							clientSideNew_ReviewPojo.reassignedRequestor = "";
						}
						if (erlStatusDisplay!=null && erlStatusDisplay.newERLStatusCaptured != null
							&& !erlStatusDisplay.newERLStatusCaptured.equalsIgnoreCase(reviewERLStatus)) {
							System.out.println("Inside newERLStatusStaging3 is " + erlStatusDisplay.newERLStatusCaptured);
							clientSideNew_ReviewPojo.newERLStatus = erlStatusDisplay.newERLStatusCaptured;
							System.out.println("new value of newERLStatusStaging is " + clientSideNew_ReviewPojo.newERLStatus);
						} else {
							clientSideNew_ReviewPojo.newERLStatus = "";
						}
						
						System.out.println("new value of sel Auth is " + clientSideNew_ReviewPojo.reassignedAuthor);
						System.out.println("new value of sel Req is " + clientSideNew_ReviewPojo.reassignedRequestor);

						clientSideNew_ReviewPojo.captureNewComment(newCommentText.getText());
						
						String localReviewPathFileName = commonUIData.getCommons().getFullLocalPathFileNameOfNewReview(commonUIData.getCommons().getCurrentRootNick(), reviewArtifactKeyPojo.relevance, clientSideNew_ReviewPojo.reviewFileName);
	
						System.out.println("clientSideNew_ReviewPojo.newReviewDocument:" + clientSideNew_ReviewPojo.newReviewDocument);
						System.out.println("reviewPathFileName to be saved:" + localReviewPathFileName);
						commonUIData.getCommons().saveXMLFileFromDocument(clientSideNew_ReviewPojo.newReviewDocument, localReviewPathFileName);
						commonUIData.getCatelogPersistenceManager().insertReview(clientSideNew_ReviewPojo);
					} catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {

						ErrorHandler.showErrorAndQuit(commonUIData.getCommons(), "Error in ReviewHandler displayContent", e);
					}
				}
			});
		}
		reviewActionsGrp.pack();
		lastReviewWdgtGrp = reviewActionsGrp;

		System.out.println("44 x = " + prevCommentText.getSize().x);
		System.out.println("44 y = " + prevCommentText.getSize().y);
		
		reviewContntHolder.pack(); 			// in form layout
		reviewContentScroller.pack(); 		// in grid layout
		reviewFrameOutmostWrapper.pack();	// in grid layout
		reviewFrameOutmostScroller.pack();
		wrappingExtlComposite.pack();
		if (extlLevel2Wrapper != null) {
			extlLevel2Wrapper.pack();
			if (extlLevel3Wrapper != null){
				extlLevel3Wrapper.pack();							
			}
		}		
	}
}