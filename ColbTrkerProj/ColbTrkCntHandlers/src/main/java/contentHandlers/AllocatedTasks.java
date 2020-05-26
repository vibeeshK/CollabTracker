package contentHandlers;
import java.util.ArrayList;

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
 * This content handler helps to group allocated Tasks against a project for a team member
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class AllocatedTasks extends GenericGrouper {

	protected void setScreenTitle() {
		mainShell.setText("AllocatedTasksList: <viewContentsAtDesk> on " + invokedArtifactPojo.artifactKeyPojo.artifactName);
	}

	public ItemPojo getItemPojo(int inItemCount){
		AllocatdTaskItemPojo allocatdTaskPojo = new AllocatdTaskItemPojo(inItemCount);
		return allocatdTaskPojo;
	}
	
	protected void setAddlColumnHeaders(){

		int addlHeadersCount = 0;
		
		System.out.println("setAddlColumnHeaders  ");
		System.out.println("addlHeadersCount=" + addlHeadersCount);

		addlLeftColumnHeaders = new String[]{};
		centerBaseColHeaders = new String[] {"Title","Description","Author","Status"};		
		centerAddlColHeaders = new String[]{};
	}

	public void setDisplayItemsCenterBaseFieldsInMultiDisplay(TableEditor editor, Table table, TableItem tableItem, int inLastColLocation, ItemPojo inItemPojo){
		AllocatdTaskItemPojo allocatdTaskPojo = (AllocatdTaskItemPojo) inItemPojo;

		editor = new TableEditor(table);
		Text requestFor_Tx = new Text(table, SWT.READ_ONLY);
		requestFor_Tx.setText(allocatdTaskPojo.title);
		editor.grabHorizontal = true;
		editor.setEditor(requestFor_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, requestFor_Tx.getText());
	
		//changed to user generic title
		//editor = new TableEditor(table);
		//Text description_Tx = new Text(table, SWT.READ_ONLY);
		//description_Tx.setText(allocatdTaskPojo.description);
		//editor.grabHorizontal = true;
		//editor.setEditor(description_Tx, tableItem, ++inLastColLocation);
		//tableItem.setText(inLastColLocation, description_Tx.getText());

		editor = new TableEditor(table);
		Text author_Tx = new Text(table, SWT.READ_ONLY);
		author_Tx.setText(allocatdTaskPojo.author);
		editor.grabHorizontal = true;
		editor.setEditor(author_Tx, tableItem, ++inLastColLocation);	
		tableItem.setText(inLastColLocation, author_Tx.getText());

		editor = new TableEditor(table);
		Text status_Tx = new Text(table, SWT.READ_ONLY);
		status_Tx.setText(allocatdTaskPojo.status);
		editor.grabHorizontal = true;
		editor.setEditor(status_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, status_Tx.getText());
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo itemPojo){

		AllocatdTaskItemPojo allocatdTaskPojo = (AllocatdTaskItemPojo) itemPojo;

		//changed to user generic title
		//Group descriptionInfo = new Group(itemContentGroup, SWT.LEFT);
		//descriptionInfo.setText("Application");
		//descriptionInfo.setLayout(new FillLayout());
		//Text descriptionText = new Text(descriptionInfo, SWT.WRAP | SWT.CENTER | SWT.READ_ONLY);
		//descriptionText.setText(allocatdTaskPojo.description);
		//
		//formData = new FormData();
		//formData.top = new FormAttachment(inPrevGroup);
		//descriptionInfo.setLayoutData(formData);
		//
		//inPrevGroup = descriptionInfo;

		Group authorInfo = new Group(itemContentGroup, SWT.LEFT);
		authorInfo.setText("Author");
		authorInfo.setLayout(new FillLayout());
		Text authorText = new Text(authorInfo, SWT.WRAP
				| SWT.READ_ONLY | SWT.CENTER);
		authorText.setText(allocatdTaskPojo.author);
		
		formData = new FormData();
		formData.top = new FormAttachment(inPrevGroup);
		authorInfo.setLayoutData(formData);

		inPrevGroup = authorInfo;

		Group statusInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		statusInfo.setText("Status");
		statusInfo.setLayout(new FillLayout());
		Text statusText = new Text(statusInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);
		statusText.setText(allocatdTaskPojo.status);
		
		formData = new FormData();
		formData.top = new FormAttachment(inPrevGroup);
		statusInfo.setLayoutData(formData);
		
		inPrevGroup = statusInfo;

		return inPrevGroup;
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
		return AllocatdTaskItemDoc.class;
	}

	@Override
	public Class getPrimerDocClass() {
		return AllocatedTasksDoc.class;
	}
	
	@Override
	public GenericGrouperDocPojo getNewPrimerDoc() {
		GenericGrouperDocPojo allocatedTasksListDoc = new AllocatedTasksDoc();
		return allocatedTasksListDoc;
	}

	public GenericItemDocPojo getBaseDoc(ItemPojo inItemPojo) {
		System.out.println("at1 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at1 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at1 getBaseDoc for itemID title" + inItemPojo.title);


		GenericItemDocPojo allocatdTaskItemDoc = new AllocatdTaskItemDoc((AllocatdTaskItemPojo) inItemPojo);

		System.out.println("at2 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at2 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at2 getBaseDoc for itemID title" + inItemPojo.title);

		System.out.println("at3 getBaseDoc for doc " + allocatdTaskItemDoc);
		System.out.println("at3 getBaseDoc for item " + allocatdTaskItemDoc.getItem());
		System.out.println("at3 getBaseDoc for itemID " + allocatdTaskItemDoc.getItem().itemID);
		System.out.println("at3 getBaseDoc for itemID relevance" + allocatdTaskItemDoc.getItem().relevance);
		System.out.println("at3 getBaseDoc for itemID title" + allocatdTaskItemDoc.getItem().title);
		return allocatdTaskItemDoc;		
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

	@Override
	public AllocatedTasksDoc getPrimerDoc() {
		return (AllocatedTasksDoc) primerDoc;
	}

	public ArrayList<AllocatdTaskItemPojo> getAllocatedTasks() {
		AllocatedTasksDoc allocatedTasksListDoc = (AllocatedTasksDoc) primerDoc;
		return allocatedTasksListDoc.getItemList();
	}
}