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
 * This content handler helps to group impediments of a project
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ImpedimentsList extends GenericGrouper {

	public static HashMap<String,ArrayList<ImpedimentItemPojo>> getTasksOpenImpediments(ArrayList<ImpedimentItemPojo> inImpedimentsList) {
		HashMap<String,ArrayList<ImpedimentItemPojo>> tasksOpenImpediments = new HashMap<String,ArrayList<ImpedimentItemPojo>>();
		
		for (ImpedimentItemPojo impedimentItemPojo : inImpedimentsList) {
			if (!impedimentItemPojo.impedimentStatus.equalsIgnoreCase(ImpedimentItemPojo.IMPEDIMENTSTATUSVALUES_Completed)){
				String taskID = Impediment.getTaskIdOfImpedimentID(impedimentItemPojo.impedimentID);
				ArrayList<ImpedimentItemPojo> openImpedimentsList = tasksOpenImpediments.get(taskID);
				if (openImpedimentsList == null) {
					openImpedimentsList = new ArrayList<ImpedimentItemPojo>();
					tasksOpenImpediments.put(taskID, openImpedimentsList);		
				}
				openImpedimentsList.add(impedimentItemPojo);
			}
		}
		return tasksOpenImpediments;
	}

	public ItemPojo getItemPojo(int inItemCount){
		ImpedimentItemPojo impedimentPojo = new ImpedimentItemPojo(inItemCount);
		return impedimentPojo;
	}
	
	protected void setAddlColumnHeaders(){
		int addlHeadersCount = 0;
		
		System.out.println("setAddlColumnHeaders  ");
		System.out.println("addlHeadersCount=" + addlHeadersCount);

		centerBaseColHeaders = new String[] {"Title","Description","Author","Status"};
	}

	public void setDisplayItemsCenterBaseFieldsInMultiDisplay(TableEditor editor, Table table, TableItem tableItem, int inLastColLocation, ItemPojo inItemPojo){
		ImpedimentItemPojo impedimentPojo = (ImpedimentItemPojo) inItemPojo;

		editor = new TableEditor(table);
		Text requestFor_Tx = new Text(table, SWT.READ_ONLY);
		requestFor_Tx.setText(impedimentPojo.title);
		editor.grabHorizontal = true;
		editor.setEditor(requestFor_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, requestFor_Tx.getText());

		editor = new TableEditor(table);
		Text impedimentDetail_Tx = new Text(table, SWT.READ_ONLY);
		impedimentDetail_Tx.setText(impedimentPojo.impedimentDetail);
		editor.grabHorizontal = true;
		editor.setEditor(impedimentDetail_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, impedimentDetail_Tx.getText());

		editor = new TableEditor(table);
		Text author_Tx = new Text(table, SWT.READ_ONLY);
		author_Tx.setText(impedimentPojo.author);
		editor.grabHorizontal = true;
		editor.setEditor(author_Tx, tableItem, ++inLastColLocation);	
		tableItem.setText(inLastColLocation, author_Tx.getText());

		editor = new TableEditor(table);
		Text status_Tx = new Text(table, SWT.READ_ONLY);
		status_Tx.setText(impedimentPojo.status);
		editor.grabHorizontal = true;
		editor.setEditor(status_Tx, tableItem, ++inLastColLocation);
		tableItem.setText(inLastColLocation, status_Tx.getText());
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo itemPojo){

		ImpedimentItemPojo impedimentPojo = (ImpedimentItemPojo) itemPojo;

		Group impedimentDetailInfo = new Group(itemContentGroup, SWT.LEFT);
		impedimentDetailInfo.setText("ImpedimentDetail");
		impedimentDetailInfo.setLayout(new FillLayout());
		Text descriptionText = new Text(impedimentDetailInfo, SWT.WRAP | SWT.CENTER);
		descriptionText.setText(impedimentPojo.impedimentDetail);
		
		formData = new FormData();
		formData.top = new FormAttachment(inPrevGroup);
		impedimentDetailInfo.setLayoutData(formData);
		
		inPrevGroup = impedimentDetailInfo;

		Group authorInfo = new Group(itemContentGroup, SWT.LEFT);
		authorInfo.setText("Author");
		authorInfo.setLayout(new FillLayout());
		Text authorText = new Text(authorInfo, SWT.WRAP
				| SWT.READ_ONLY | SWT.CENTER);
		authorText.setText(impedimentPojo.author);
		
		formData = new FormData();
		formData.top = new FormAttachment(inPrevGroup);
		authorInfo.setLayoutData(formData);

		inPrevGroup = authorInfo;

		Group statusInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		statusInfo.setText("Status");
		statusInfo.setLayout(new FillLayout());
		Text statusText = new Text(statusInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);
		statusText.setText(impedimentPojo.status);
		
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
		return ImpedimentItemDoc.class;
	}

	@Override
	public Class getPrimerDocClass() {
		return ImpedimentsListDoc.class;
	}
	
	@Override
	public GenericGrouperDocPojo getNewPrimerDoc() {
		GenericGrouperDocPojo impedimentsListDoc = new ImpedimentsListDoc();
		return impedimentsListDoc;
	}

	public GenericItemDocPojo getBaseDoc(ItemPojo inItemPojo) {
		System.out.println("at1 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at1 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at1 getBaseDoc for itemID title" + inItemPojo.title);


		GenericItemDocPojo impedimentItemDoc = new ImpedimentItemDoc((ImpedimentItemPojo) inItemPojo);

		System.out.println("at2 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at2 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at2 getBaseDoc for itemID title" + inItemPojo.title);

		System.out.println("at3 getBaseDoc for doc " + impedimentItemDoc);
		System.out.println("at3 getBaseDoc for item " + impedimentItemDoc.getItem());
		System.out.println("at3 getBaseDoc for itemID " + impedimentItemDoc.getItem().itemID);
		System.out.println("at3 getBaseDoc for itemID relevance" + impedimentItemDoc.getItem().relevance);
		System.out.println("at3 getBaseDoc for itemID title" + impedimentItemDoc.getItem().title);
		return impedimentItemDoc;		
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
	public ImpedimentsListDoc getPrimerDoc() {
		return (ImpedimentsListDoc) primerDoc;
	}
}