package contentHandlers;

import java.util.ArrayList;
import java.util.HashMap;

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
 * This content handler helps to group defect decks against a project
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class DefectsList extends GenericGrouper {

	public static HashMap<String,ArrayList<DefectItemPojo>> getTasksOpenDefects(ArrayList<DefectItemPojo> inDefectsList) {
		HashMap<String,ArrayList<DefectItemPojo>> tasksOpenDefects = new HashMap<String,ArrayList<DefectItemPojo>>();
		
		for (DefectItemPojo defectItemPojo : inDefectsList) {
			if (!defectItemPojo.defectStatus.equalsIgnoreCase(DefectItemPojo.DEFECTSTATUSVALUES_Completed)){
				String taskID = Defect.getTaskIdOfDefectID(defectItemPojo.defectID);
				ArrayList<DefectItemPojo> openDefectsList = tasksOpenDefects.get(taskID);
				if (openDefectsList == null) {
					openDefectsList = new ArrayList<DefectItemPojo>();
					tasksOpenDefects.put(taskID, openDefectsList);		
				}
				openDefectsList.add(defectItemPojo);
			}
		}
		return tasksOpenDefects;
	}

	public ItemPojo getItemPojo(int inItemCount){
		DefectItemPojo defectPojo = new DefectItemPojo(inItemCount);
		return defectPojo;
	}
	
	protected void setAddlColumnHeaders(){

		int addlHeadersCount = 0;
		
		System.out.println("setAddlColumnHeaders  ");
		System.out.println("addlHeadersCount=" + addlHeadersCount);

		centerBaseColHeaders = new String[] {"Title","Description","Author","Status"};
	}

	public void setDisplayItemsCenterBaseFieldsInMultiDisplay(TableEditor editor, Table table, TableItem tableItem, int inLastColLocation, ItemPojo inItemPojo){
		DefectItemPojo defectPojo = (DefectItemPojo) inItemPojo;

		editor = new TableEditor(table);
		Text requestFor_Tx = new Text(table, SWT.READ_ONLY);
		requestFor_Tx.setText(defectPojo.title);
		editor.grabHorizontal = true;
		editor.setEditor(requestFor_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, requestFor_Tx.getText());

		editor = new TableEditor(table);
		Text defectDetail_Tx = new Text(table, SWT.READ_ONLY);
		defectDetail_Tx.setText(defectPojo.defectDetail);
		editor.grabHorizontal = true;
		editor.setEditor(defectDetail_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, defectDetail_Tx.getText());

		editor = new TableEditor(table);
		Text author_Tx = new Text(table, SWT.READ_ONLY);
		author_Tx.setText(defectPojo.author);
		editor.grabHorizontal = true;
		editor.setEditor(author_Tx, tableItem, ++inLastColLocation);	
		tableItem.setText(inLastColLocation, author_Tx.getText());

		editor = new TableEditor(table);
		Text status_Tx = new Text(table, SWT.READ_ONLY);
		status_Tx.setText(defectPojo.status);
		editor.grabHorizontal = true;
		editor.setEditor(status_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, status_Tx.getText());
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo itemPojo){

		DefectItemPojo defectPojo = (DefectItemPojo) itemPojo;

		Group defectDetailInfo = new Group(itemContentGroup, SWT.LEFT);
		defectDetailInfo.setText("DefectDetail");
		defectDetailInfo.setLayout(new FillLayout());
		Text descriptionText = new Text(defectDetailInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);
		descriptionText.setText(defectPojo.defectDetail);
		
		formData = new FormData();
		formData.top = new FormAttachment(inPrevGroup);
		defectDetailInfo.setLayoutData(formData);
		
		inPrevGroup = defectDetailInfo;

		Group authorInfo = new Group(itemContentGroup, SWT.LEFT);
		authorInfo.setText("Author");
		authorInfo.setLayout(new FillLayout());
		Text authorText = new Text(authorInfo, SWT.WRAP
				| SWT.READ_ONLY | SWT.CENTER);
		authorText.setText(defectPojo.author);
		
		formData = new FormData();
		formData.top = new FormAttachment(inPrevGroup);
		authorInfo.setLayoutData(formData);

		inPrevGroup = authorInfo;

		Group statusInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		statusInfo.setText("Status");
		statusInfo.setLayout(new FillLayout());
		Text statusText = new Text(statusInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);
		statusText.setText(defectPojo.status);
		
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
		return DefectItemDoc.class;
	}

	@Override
	public Class getPrimerDocClass() {
		return DefectsListDoc.class;
	}
	
	@Override
	public GenericGrouperDocPojo getNewPrimerDoc() {
		GenericGrouperDocPojo defectsListDoc = new DefectsListDoc();
		return defectsListDoc;
	}

	public GenericItemDocPojo getBaseDoc(ItemPojo inItemPojo) {
		System.out.println("at1 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at1 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at1 getBaseDoc for itemID title" + inItemPojo.title);


		GenericItemDocPojo defectItemDoc = new DefectItemDoc((DefectItemPojo) inItemPojo);

		System.out.println("at2 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at2 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at2 getBaseDoc for itemID title" + inItemPojo.title);

		System.out.println("at3 getBaseDoc for doc " + defectItemDoc);
		System.out.println("at3 getBaseDoc for item " + defectItemDoc.getItem());
		System.out.println("at3 getBaseDoc for itemID " + defectItemDoc.getItem().itemID);
		System.out.println("at3 getBaseDoc for itemID relevance" + defectItemDoc.getItem().relevance);
		System.out.println("at3 getBaseDoc for itemID title" + defectItemDoc.getItem().title);
		return defectItemDoc;		
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
	public DefectsListDoc getPrimerDoc() {
		return (DefectsListDoc) primerDoc;
	}
}