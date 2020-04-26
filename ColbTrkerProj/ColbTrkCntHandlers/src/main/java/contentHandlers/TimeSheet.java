package contentHandlers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import espot.GenericItemHandler;
import espot.ItemPojo;

/**
 * This content handler helps to hold Timesheet entry made at one time
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class TimeSheet extends GenericItemHandler {

	Text applicationText;
	Text reviewerText;
	Text descriptionText;

	public void setInitialItemPojoAddlFields(){
		TimeSheetPojo timeSheetPojo = (TimeSheetPojo) primerDoc.getItem();
	}

	public void checkSetNewItemID() {
		TimeSheetPojo timeSheetPojo = (TimeSheetPojo) primerDoc.getItem();
		if (timeSheetPojo.itemID.equalsIgnoreCase("")) {
			timeSheetPojo.itemID = timeSheetPojo.author + commonData.getCommons().getCurrentTimeStamp();
		}
	}

	public void setDisplayItemsAddlFieldsInMultiDisplay(TableEditor editor, Table table, TableItem tableItem, Object itemPojo){
		
		System.out.println("SURPRISE SURPRISE. NEVER THOUGHT THIS LINE WILL BE EXECUTED");
		System.out.println("SURPRISE SURPRISE. NEVER THOUGHT THIS LINE WILL BE EXECUTED");

		TimeSheetPojo timeSheetPojo = (TimeSheetPojo) itemPojo;
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo itemPojo){

		TimeSheetPojo timeSheetPojo = (TimeSheetPojo) itemPojo;

		Group descriptionInfo = new Group(itemContentGroup, SWT.LEFT);
		descriptionInfo.setText("Description");
		descriptionInfo.setLayout(new FillLayout());
		descriptionText = new Text(descriptionInfo, SWT.WRAP | SWT.CENTER);
		descriptionText.setText(timeSheetPojo.description);
		
		formData = new FormData();
		formData.top = new FormAttachment(inPrevGroup);
		formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
		descriptionInfo.setLayoutData(formData);
		inPrevGroup = descriptionInfo;

		Group reviewerInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		reviewerInfo.setText("Reviewer");
		reviewerInfo.setLayout(new FillLayout());
		reviewerText = new Text(reviewerInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);
		reviewerText.setText(timeSheetPojo.reviewer);
		
		formData = new FormData();
		formData.top = new FormAttachment(inPrevGroup);
		formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
		reviewerInfo.setLayoutData(formData);
		inPrevGroup = reviewerInfo;

		return inPrevGroup;
	}
	
	public void getAddlFieldsOfItemPojo(ItemPojo inItemPojo){
		TimeSheetPojo timeSheetPojo = (TimeSheetPojo) inItemPojo;
		timeSheetPojo.reviewer = reviewerText.getText();
		timeSheetPojo.description = descriptionText.getText();
	}

	public String validateBeforeUIEdit() {
		String validationBeforeEdit = "";
		return validationBeforeEdit;
	}

	@Override
	public Class getPrimerDocClass() {
		return TimeSheetItemDoc.class;
	}

	@Override
	public void testPrinter(String inPrintHead) {
	}
	@Override
	public boolean validateAddlScrFields() {
		return true;
	}
	@Override
	public TimeSheetItemDoc getPrimerDoc() {
		return (TimeSheetItemDoc) primerDoc;
	}

	public TimeSheetItemDoc getNewPrimerDoc() {
		return new TimeSheetItemDoc(new TimeSheetPojo(-1));
	}
}