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

import colbTrk.GenericGrouper;
import colbTrk.GenericGrouperDocPojo;
import colbTrk.GenericItemDocPojo;
import colbTrk.ItemPojo;

/**
 * This content handler helps to group the timesheets of a user
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class TimeShRollup extends GenericGrouper {

	protected void setScreenTitle() {
		mainShell.setText("TimeSheetsGrouper: <viewContentsAtDesk> on " + invokedArtifactPojo.artifactKeyPojo.artifactName);
	}

	public ItemPojo getItemPojo(int inItemCount){
		TimeSheetPojo timeSheetPojo = new TimeSheetPojo(inItemCount);
		return timeSheetPojo;
	}

	protected void setAddlColumnHeaders(){

		int addlHeadersCount = 0;
		
		System.out.println("setAddlColumnHeaders  ");
		System.out.println("addlHeadersCount=" + addlHeadersCount);

		centerBaseColHeaders = new String[] {"Title","Description","Author","Application","Reviewer","Status"};
	}

	public void setDisplayItemsCenterBaseFieldsInMultiDisplay(TableEditor editor, Table table, TableItem tableItem, int inLastColLocation, ItemPojo inItemPojo){
		TimeSheetPojo timeSheetPojo = (TimeSheetPojo) inItemPojo;

		editor = new TableEditor(table);
		Text requestFor_Tx = new Text(table, SWT.READ_ONLY);
		requestFor_Tx.setText(timeSheetPojo.title);
		editor.grabHorizontal = true;
		editor.setEditor(requestFor_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, requestFor_Tx.getText());

		//changed to user generic title		
		//editor = new TableEditor(table);
		//Text description_Tx = new Text(table, SWT.READ_ONLY);
		//description_Tx.setText(timeSheetPojo.description);
		//editor.grabHorizontal = true;
		//editor.setEditor(description_Tx, tableItem, ++inLastColLocation);
		//tableItem.setText(inLastColLocation, description_Tx.getText());

		editor = new TableEditor(table);
		Text author_Tx = new Text(table, SWT.READ_ONLY);
		author_Tx.setText(timeSheetPojo.author);
		editor.grabHorizontal = true;
		editor.setEditor(author_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, author_Tx.getText());

		editor = new TableEditor(table);
		Text reviewer_Tx = new Text(table, SWT.READ_ONLY);
		reviewer_Tx.setText(timeSheetPojo.reviewer);
		editor.grabHorizontal = true;
		editor.setEditor(reviewer_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, reviewer_Tx.getText());
	
		editor = new TableEditor(table);
		Text status_Tx = new Text(table, SWT.READ_ONLY);
		status_Tx.setText(timeSheetPojo.status);
		editor.grabHorizontal = true;
		editor.setEditor(status_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, status_Tx.getText());
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo itemPojo){

		Group prevGroup = inPrevGroup;
		TimeSheetPojo timeSheetPojo = (TimeSheetPojo) itemPojo;

		Group reviewerInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		reviewerInfo.setText("Reviewer");
		reviewerInfo.setLayout(new FillLayout());
		Text reviewerText = new Text(reviewerInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);
		reviewerText.setText(timeSheetPojo.reviewer);
		
		formData = new FormData();
		formData.top = new FormAttachment(prevGroup);
		reviewerInfo.setLayoutData(formData);
		prevGroup = reviewerInfo;
	
		Group statusInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		statusInfo.setText("Status");
		statusInfo.setLayout(new FillLayout());
		Text statusText = new Text(statusInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);
		statusText.setText(timeSheetPojo.status);
		
		formData = new FormData();
		formData.top = new FormAttachment(prevGroup);
		statusInfo.setLayoutData(formData);
		prevGroup = statusInfo;
		
		Group authorInfo = new Group(itemContentGroup, SWT.LEFT);
		authorInfo.setText("Author");
		authorInfo.setLayout(new FillLayout());
		Text authorNameText = new Text(authorInfo, SWT.WRAP
				| SWT.READ_ONLY | SWT.CENTER);
		authorNameText.setText(timeSheetPojo.author);
	
		formData = new FormData();
		formData.top = new FormAttachment(prevGroup);
		authorInfo.setLayoutData(formData);
		prevGroup = authorInfo;
		
		return prevGroup;
	}
	
	public void getPrimerDocAddlFields() {
		// from persistence to screen
	}
	public void setPrimerDocAddlFields() {
		// from screen to persistence		
	}

	@Override
	public void extendedCommonInit() {
	}

	public String validateBeforeUIEdit() {
		String validationBeforeEdit = "";
		return validationBeforeEdit;
	}

	@Override
	protected Class getBasePrimerDocClass() {
		return TimeSheetItemDoc.class;
	}

	@Override
	public Class getPrimerDocClass() {
		return TimeShRollupDoc.class;
	}
	
	@Override
	public GenericGrouperDocPojo getNewPrimerDoc() {
		GenericGrouperDocPojo timeShRollupDoc = new TimeShRollupDoc();
		return timeShRollupDoc;
	}

	public GenericItemDocPojo getBaseDoc(ItemPojo inItemPojo) {
		System.out.println("at1 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at1 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at1 getBaseDoc for itemID title" + inItemPojo.title);

		GenericItemDocPojo timeSheetItemDoc = new TimeSheetItemDoc(inItemPojo);

		System.out.println("at2 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at2 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at2 getBaseDoc for itemID title" + inItemPojo.title);

		System.out.println("at3 getBaseDoc for doc " + timeSheetItemDoc);
		System.out.println("at3 getBaseDoc for item " + timeSheetItemDoc.getItem());
		System.out.println("at3 getBaseDoc for itemID " + timeSheetItemDoc.getItem().itemID);
		System.out.println("at3 getBaseDoc for itemID relevance" + timeSheetItemDoc.getItem().relevance);
		System.out.println("at3 getBaseDoc for itemID title" + timeSheetItemDoc.getItem().title);
		return timeSheetItemDoc;		
	}

	@Override
	public void additionalRibbonButtons() {
	}

	@Override
	public void setDisplayItemsCenterAddlFieldsInMultiDisplay(
			TableEditor editor, Table inTable, TableItem intableItem,
			int inLastColLocation, ItemPojo inItemPojo) {
	}

	@Override
	public String getDefaultSourceFileName(ItemPojo inItemPojo) {
		return null;
	}
	
	public TimeShRollupDoc getPrimerDoc(){
		return (TimeShRollupDoc) primerDoc;
	}
}