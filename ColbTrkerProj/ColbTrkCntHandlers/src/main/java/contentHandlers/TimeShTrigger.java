package contentHandlers;

import java.text.ParseException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import colbTrk.ArtifactKeyPojo;
import colbTrk.ErrorHandler;
import colbTrk.GenericItemHandler;
import colbTrk.ItemPojo;

/**
 * This content handler helps to initiate time sheet captures
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class TimeShTrigger extends GenericItemHandler {

	Text applicationText;
	Text reviewerText;
	Text captureIntervalText;
	Text allocationIntervalText;
	DateTime captureStartDateDisplay;
	DateTime captureEndDateDisplay;	

	public void setInitialItemPojoAddlFields(){
		TimeShTriggerPojo timeShTriggerPojo = (TimeShTriggerPojo) primerDoc.getItem();
	}

	public void checkSetNewItemID() {
		TimeShTriggerPojo timeShTriggerPojo = (TimeShTriggerPojo) primerDoc.getItem();
		if (timeShTriggerPojo.itemID.equalsIgnoreCase("")) {
			timeShTriggerPojo.itemID = timeShTriggerPojo.author + commonData.getCommons().getCurrentTimeStamp();
		}
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo inItemPojo){

		TimeShTriggerPojo timeShTriggerPojo = (TimeShTriggerPojo) inItemPojo;
		
		Group lastGroup = inPrevGroup;

		Group reviewerInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		reviewerInfo.setText("Reviewer");
		reviewerInfo.setLayout(new FillLayout());
		if (invokedForEdit) {
			reviewerText = new Text(reviewerInfo, SWT.WRAP | SWT.CENTER);
		} else {
			reviewerText = new Text(reviewerInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);			
		}
		reviewerText.setText(timeShTriggerPojo.reviewer);
		
		formData = new FormData();
		formData.top = new FormAttachment(lastGroup);
		formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
		reviewerInfo.setLayoutData(formData);
		lastGroup = reviewerInfo;

		Group captureFreqInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		captureFreqInfo.setText("CaptureInterval");
		captureFreqInfo.setLayout(new FillLayout());
		if (invokedForEdit) {
			captureIntervalText = new Text(captureFreqInfo, SWT.WRAP | SWT.CENTER);
		} else {
			captureIntervalText = new Text(captureFreqInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);			
		}
		captureIntervalText.setText(commonData.getCommons().convertIntToString(timeShTriggerPojo.captureInterval));
		
		formData = new FormData();
		formData.top = new FormAttachment(lastGroup);
		formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
		captureFreqInfo.setLayoutData(formData);
		lastGroup = captureFreqInfo;

		Group allocationFreqInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		allocationFreqInfo.setText("AllocationInterval");
		allocationFreqInfo.setLayout(new FillLayout());
		if (invokedForEdit) {
			allocationIntervalText = new Text(allocationFreqInfo, SWT.WRAP | SWT.CENTER);
		} else {
			allocationIntervalText = new Text(allocationFreqInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);			
		}
		allocationIntervalText.setText(commonData.getCommons().convertIntToString(timeShTriggerPojo.allocationInterval));
		
		formData = new FormData();
		formData.top = new FormAttachment(lastGroup);
		formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
		allocationFreqInfo.setLayoutData(formData);
		lastGroup = allocationFreqInfo;

		Group captureStartDateInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		captureStartDateInfo.setText("Capture Start Date");
		captureStartDateInfo.setLayout(new FillLayout());
		if (invokedForEdit) {

			captureStartDateDisplay = new DateTime(captureStartDateInfo, SWT.DATE | SWT.CENTER);
		} else {
			captureStartDateDisplay = new DateTime(captureStartDateInfo, SWT.DATE | SWT.READ_ONLY | SWT.CENTER);
			captureStartDateDisplay.setEnabled(false);
		}
		if (invokedForEdit && (timeShTriggerPojo.captureStartDate == null || timeShTriggerPojo.captureStartDate.isEmpty())) {

			try {
				commons.setCurrentDateOnDisplay(captureStartDateDisplay);
			} catch (ParseException e) {
				ErrorHandler.showErrorAndQuit(commons, "Error in TimeShCapture setAddlFieldsForItemDisplay CurrDate StartDate of " 
															+ inItemPojo.artifactName, e);
			}
		} else if (timeShTriggerPojo.captureStartDate != null && !timeShTriggerPojo.captureStartDate.isEmpty()) {

			try {
				commons.setDateOnDisplay(captureStartDateDisplay, timeShTriggerPojo.captureStartDate);
			} catch (ParseException e) {
				ErrorHandler.showErrorAndQuit(commons, "Error in TimeShCapture setAddlFieldsForItemDisplay StartDate of " 
															+ inItemPojo.artifactName, e);				
			}
		}
		
		
		formData = new FormData();
		formData.top = new FormAttachment(lastGroup);
		formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
		captureStartDateInfo.setLayoutData(formData);
		lastGroup = captureStartDateInfo;

		Group captureEndDateInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		captureEndDateInfo.setText("Capture End Date");
		captureEndDateInfo.setLayout(new FillLayout());
		if (invokedForEdit) {
			captureEndDateDisplay = new DateTime(captureEndDateInfo, SWT.DATE | SWT.CENTER);
		} else {
			captureEndDateDisplay = new DateTime(captureEndDateInfo, SWT.DATE | SWT.READ_ONLY | SWT.CENTER);
			captureEndDateDisplay.setEnabled(false);
		}

		if (invokedForEdit && (timeShTriggerPojo.captureEndDate == null || timeShTriggerPojo.captureEndDate.isEmpty())) {

			try {
				commons.setCurrentDateOnDisplay(captureEndDateDisplay);
			} catch (ParseException e) {
				ErrorHandler.showErrorAndQuit(commons, "Error in TimeShCapture setAddlFieldsForItemDisplay CurrDate EndDate of " 
															+ inItemPojo.artifactName, e);
			}		
		}
		
		if (timeShTriggerPojo.captureEndDate!= null && !timeShTriggerPojo.captureEndDate.isEmpty()) {

			try {
				commons.setDateOnDisplay(captureEndDateDisplay, timeShTriggerPojo.captureEndDate);
			} catch (ParseException e) {
				ErrorHandler.showErrorAndQuit(commons, "Error in TimeShTrigger setAddlFieldsForItemDisplay EndDate of "
															+ inItemPojo.artifactName, e);
			}
		}
		
		formData = new FormData();
		formData.top = new FormAttachment(lastGroup);
		formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
		captureEndDateInfo.setLayoutData(formData);
		lastGroup = captureEndDateInfo;

		return lastGroup;
	}
	
	public void getAddlFieldsOfItemPojo(ItemPojo inItemPojo){
		TimeShTriggerPojo timeShTriggerPojo = (TimeShTriggerPojo) primerDoc.getItem();
		timeShTriggerPojo.reviewer = reviewerText.getText();

		System.out.println("title value while saving is " + timeShTriggerPojo.title);
		System.out.println("title value while saving is " + timeShTriggerPojo.title);

		timeShTriggerPojo.captureInterval = commonData.getCommons().convertStringToInt(captureIntervalText.getText());
		timeShTriggerPojo.allocationInterval = commonData.getCommons().convertStringToInt(allocationIntervalText.getText());

		try {		
			timeShTriggerPojo.captureStartDate = commons.getDateStringFromDisplayDate(captureStartDateDisplay);
			timeShTriggerPojo.captureEndDate = commons.getDateStringFromDisplayDate(captureEndDateDisplay);
			
		} catch (ParseException e) {			
			ErrorHandler.showErrorAndQuit(commons, "Error in TimeShTrigger getAddlFieldsOfItemPojo " + " " + inItemPojo.artifactName, e);
		}
	}
	
	public void testPrinter(String inPrintHead) {
		TimeShTriggerPojo timeShTriggerPojo1 = (TimeShTriggerPojo) primerDoc.getItem();
		System.out.println("In testPrinter from inPrintHead " + inPrintHead);
		System.out.println("In testPrinter itemPojo title is " + primerDoc.getItem().title);
		TimeShTriggerPojo timeShTriggerPojo2 = (TimeShTriggerPojo) primerDoc.getItem();

		System.out.println("In testPrinter Pojo title from primeDoc is " + timeShTriggerPojo2.title);
	}

	public String validateBeforeUIEdit() {
		String validationBeforeEdit = "";
		return validationBeforeEdit;
	}

	@Override
	public Class getPrimerDocClass() {
		
		return TimeShTriggerItemDoc.class;
	}

	@Override
	public TimeShTriggerItemDoc getPrimerDoc() {
		
		return (TimeShTriggerItemDoc) primerDoc;
	}	

	public TimeShTriggerItemDoc getNewPrimerDoc() {
		return new TimeShTriggerItemDoc(new TimeShTriggerPojo(-1));
	}
	
	@Override
	public boolean validateAddlScrFields(){
		System.out.println("At the start of validateAddlScrFields ");

		if (!(commonData.getCommons().checkNumeric(allocationIntervalText.getText()))) {
			MessageBox messageBox1 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
			messageBox1.setMessage("Enter only numeric value in allocationInterval. Data NOT saved!");
			int rc1 = messageBox1.open();					
			return false;
		};
		
		if (!(commonData.getCommons().checkNumeric(captureIntervalText.getText()))) {
			MessageBox messageBox1 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
			messageBox1.setMessage("Enter only numeric value in captureIntervalText. Data NOT saved!");
			int rc1 = messageBox1.open();					
			return false;
		};
		
		return true;
	}

	@Override
	public int getTriggerInterval() {
		return 0;
	}
	
	public ArtifactKeyPojo getXtdInstructedArtifactKeyPojo(String inUserName, String inContentType) {
		// this method is meant for extended processes e.g. for assigning timesheets to users
		String artifactNameCoined = inContentType + "_" + inUserName;
		ArtifactKeyPojo artifactKeyPojo = new ArtifactKeyPojo(invokedArtifactPojo.artifactKeyPojo.rootNick, invokedArtifactPojo.artifactKeyPojo.relevance, artifactNameCoined, inContentType);
		return artifactKeyPojo;
	}
}