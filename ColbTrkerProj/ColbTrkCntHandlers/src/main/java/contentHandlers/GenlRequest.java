package contentHandlers;

import java.text.ParseException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import colbTrk.ErrorHandler;
import colbTrk.GenericItemHandler;
import colbTrk.ItemPojo;

/**
 * This content handler helps to let team members to log general requests
 * which will be collated into the grouping content type by the server.
 * 
 * The leaders can view the requests, categorize on status and process on rolled up view
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class GenlRequest extends GenericItemHandler {

	public static final int PREFERED_DESC_WIDTH = 600;
	public static final int PREFERED_DESC_HEIGHT = 100;

	Text requestForText;
	Text requestDescText;
	Text requestorShortNameText;
	Text requestCategoryText;	
	DateTime requestStartDateDisplay;
	DateTime requestEndDateDisplay;

	public ItemPojo getItemPojo(int itemCount){
		ItemPojo genlRequestPojo = new GenlRequestPojo(itemCount);
		return genlRequestPojo;
	}

	@Override
	public void setInitialItemPojoAddlFields() {
		GenlRequestPojo genlRequestPojo = (GenlRequestPojo) primerDoc.getItem();
	}

	public void checkSetNewItemID() {
		GenlRequestPojo genlRequestPojo = (GenlRequestPojo) primerDoc.getItem();
		if (genlRequestPojo.itemID.equalsIgnoreCase("")) {
			genlRequestPojo.itemID = genlRequestPojo.author + commonData.getCommons().getCurrentTimeStamp();
		}
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo itemPojo){

		GenlRequestPojo genlRequestPojo = (GenlRequestPojo) itemPojo;

		{
			Group requestDescInfo = new Group(itemContentGroup, SWT.LEFT);
			requestDescInfo.setText("Description");
			requestDescInfo.setLayout(new FillLayout());
			if (invokedForEdit) {
				requestDescText = new Text(requestDescInfo, SWT.WRAP | SWT.CENTER);
			} else {
				requestDescText = new Text(requestDescInfo, SWT.WRAP | SWT.CENTER | SWT.READ_ONLY);			
			}
			requestDescText.setText(genlRequestPojo.requestDesc);
			
			formData = new FormData();
			formData.top = new FormAttachment(inPrevGroup);
			formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
			
			if (genlRequestPojo.requestDesc == null || genlRequestPojo.requestDesc.equalsIgnoreCase("")) {
				formData.height = PREFERED_DESC_HEIGHT;
				formData.width = PREFERED_DESC_WIDTH;
			}
	
			requestDescInfo.setLayoutData(formData);
			inPrevGroup = requestDescInfo;
		}

		{
			Group requestCategoryInfo = new Group(itemContentGroup, SWT.LEFT);
			requestCategoryInfo.setText("RequestCategory");
			requestCategoryInfo.setLayout(new FillLayout());
			if (invokedForEdit) {
				requestCategoryText = new Text(requestCategoryInfo, SWT.WRAP | SWT.CENTER);
			} else {
				requestCategoryText = new Text(requestCategoryInfo, SWT.WRAP | SWT.CENTER | SWT.READ_ONLY);			
			}
			requestCategoryText.setText(genlRequestPojo.requestCategory);
			
			formData = new FormData();
			formData.top = new FormAttachment(inPrevGroup);
			formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
			requestCategoryInfo.setLayoutData(formData);
			inPrevGroup = requestCategoryInfo;
		}

		{
			Group requestStartDateInfo = new Group(itemContentGroup, SWT.LEFT);
			requestStartDateInfo.setText("RequestStartDate");
			requestStartDateInfo.setLayout(new FillLayout());
			if (invokedForEdit) {
				requestStartDateDisplay = new DateTime(requestStartDateInfo, SWT.DATE | SWT.CENTER);
			} else {
				requestStartDateDisplay = new DateTime(requestStartDateInfo, SWT.DATE | SWT.CENTER | SWT.READ_ONLY);			
				requestStartDateDisplay.setEnabled(false);
			}
			if (invokedForEdit && (genlRequestPojo.requestStartDate == null || genlRequestPojo.requestStartDate.isEmpty())) {
				try {
					commons.setCurrentDateOnDisplay(requestStartDateDisplay);
				} catch (ParseException e) {
					ErrorHandler.showErrorAndQuit(commons, "Error in GenlRequest setAddlFieldsForItemDisplay CurrDate StartDate of " 
																+ genlRequestPojo.artifactName, e);
				}
			} else if (genlRequestPojo.requestStartDate != null && !genlRequestPojo.requestStartDate.isEmpty()) {
				try {
					System.out.println("commons is " + commons);
					System.out.println("genlRequestPojo is " + genlRequestPojo);
					System.out.println("genlRequestPojo.requestStartDate is " + genlRequestPojo.requestStartDate);
					System.out.println("requestStartDateDisplay is " + requestStartDateDisplay);
					commons.setDateOnDisplay(requestStartDateDisplay, genlRequestPojo.requestStartDate);
				} catch (ParseException e) {
					ErrorHandler.showErrorAndQuit(commons, "Error in GenlRequest setAddlFieldsForItemDisplay StartDate of " 
																+ genlRequestPojo.artifactName, e);
				}
			}

			formData = new FormData();
			formData.top = new FormAttachment(inPrevGroup);
			formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
			requestStartDateInfo.setLayoutData(formData);
			inPrevGroup = requestStartDateInfo;
		}

		{
			Group requestEndDateInfo = new Group(itemContentGroup, SWT.LEFT);
			requestEndDateInfo.setText("RequestStartDate");
			requestEndDateInfo.setLayout(new FillLayout());
			if (invokedForEdit) {
				requestEndDateDisplay = new DateTime(requestEndDateInfo, SWT.DATE | SWT.CENTER);
			} else {
				requestEndDateDisplay = new DateTime(requestEndDateInfo, SWT.DATE | SWT.CENTER | SWT.READ_ONLY);
				requestEndDateDisplay.setEnabled(false);
			}
			if (invokedForEdit && (genlRequestPojo.requestEndDate == null || genlRequestPojo.requestEndDate.isEmpty())) {
				try {
					commons.setCurrentDateOnDisplay(requestEndDateDisplay);
				} catch (ParseException e) {
					ErrorHandler.showErrorAndQuit(commons, "Error in GenlRequest setAddlFieldsForItemDisplay CurrDate EndDate of " 
																+ genlRequestPojo.artifactName, e);
				}
			} else if (genlRequestPojo.requestEndDate != null && !genlRequestPojo.requestEndDate.isEmpty()) {
				try {
					commons.setDateOnDisplay(requestEndDateDisplay, genlRequestPojo.requestEndDate);
				} catch (ParseException e) {
					ErrorHandler.showErrorAndQuit(commons, "Error in GenlRequest setAddlFieldsForItemDisplay EndDate of "
																+ genlRequestPojo.artifactName, e);
				}
			}

			formData = new FormData();
			formData.top = new FormAttachment(inPrevGroup);
			formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
			requestEndDateInfo.setLayoutData(formData);
			inPrevGroup = requestEndDateInfo;
		}

		return inPrevGroup;
	}
	
	public void getAddlFieldsOfItemPojo(ItemPojo inItemPojo){
		GenlRequestPojo genlRequestPojo = (GenlRequestPojo) inItemPojo;
		genlRequestPojo.requestDesc = requestDescText.getText();
		genlRequestPojo.requestCategory = requestCategoryText.getText();
		try {
			genlRequestPojo.requestStartDate = commons.getDateStringFromDisplayDate(requestStartDateDisplay);
			genlRequestPojo.requestEndDate = commons.getDateStringFromDisplayDate(requestEndDateDisplay);
			
		} catch (ParseException e) {			
			ErrorHandler.showErrorAndQuit(commons, "Error in GenlRequest getAddlFieldsOfItemPojo "
														+ inItemPojo.artifactName, e);
		}
	}
	
	public String validateBeforeUIEdit() {
		String validationBeforeEdit = "";
		return validationBeforeEdit;
	}

	@Override
	public Class getPrimerDocClass() {
		return GenlRequestItemDoc.class;
	}

	@Override
	public void testPrinter(String inPrintHead) {
	}
	
	@Override
	public boolean validateAddlScrFields() {
		return true;
	}
	
	@Override
	public GenlRequestItemDoc getPrimerDoc() {
		return (GenlRequestItemDoc) primerDoc;
	}

	public GenlRequestItemDoc getNewPrimerDoc() {
		return new GenlRequestItemDoc(new GenlRequestPojo(-1));
	}
}