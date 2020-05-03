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

import clobTrk.GenericGrouper;
import clobTrk.GenericGrouperDocPojo;
import clobTrk.GenericItemDocPojo;
import clobTrk.ItemPojo;

/**
 * This content handler helps to group ideas within a relevance
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class IdeasGrouper extends GenericGrouper {

	protected void setScreenTitle() {
		mainShell.setText("IdeasGrouper: <viewContentsAtDesk> on " + invokedArtifactPojo.artifactKeyPojo.artifactName);
	}

	public ItemPojo getItemPojo(int inItemCount){
		IdeaPojo ideaPojo = new IdeaPojo(inItemCount);
		return ideaPojo;
	}
	
	protected void setAddlColumnHeaders(){

		int addlHeadersCount = 0;
		
		System.out.println("setAddlColumnHeaders  ");
		System.out.println("addlHeadersCount=" + addlHeadersCount);

		centerBaseColHeaders = new String[] {"Title","Description","Author","Application","Reviewer","Status"};
	}

	public void setDisplayItemsCenterBaseFieldsInMultiDisplay(TableEditor editor, Table inTable, TableItem inTableItem, int inLastColLocation, ItemPojo inItemPojo){
		IdeaPojo ideaPojo = (IdeaPojo) inItemPojo;

		editor = new TableEditor(inTable);
		Text requestFor_Tx = new Text(inTable, SWT.READ_ONLY);
		requestFor_Tx.setText(ideaPojo.title);
		editor.grabHorizontal = true;
		editor.setEditor(requestFor_Tx, inTableItem, ++inLastColLocation);
		inTableItem.setText(inLastColLocation, requestFor_Tx.getText());		
		
		editor = new TableEditor(inTable);
		Text description_Tx = new Text(inTable, SWT.READ_ONLY);
		description_Tx.setText(ideaPojo.description);
		editor.grabHorizontal = true;
		editor.setEditor(description_Tx, inTableItem, ++inLastColLocation);
		inTableItem.setText(inLastColLocation, description_Tx.getText());

		editor = new TableEditor(inTable);
		Text author_Tx = new Text(inTable, SWT.READ_ONLY);
		author_Tx.setText(ideaPojo.author);
		editor.grabHorizontal = true;
		editor.setEditor(author_Tx, inTableItem, ++inLastColLocation);
		inTableItem.setText(inLastColLocation, author_Tx.getText());
	
		editor = new TableEditor(inTable);
		Text application_Tx = new Text(inTable, SWT.READ_ONLY);
		application_Tx.setText(ideaPojo.application);
		editor.grabHorizontal = true;
		editor.setEditor(application_Tx, inTableItem, ++inLastColLocation);
		inTableItem.setText(inLastColLocation, application_Tx.getText());
	
		editor = new TableEditor(inTable);
		Text reviewer_Tx = new Text(inTable, SWT.READ_ONLY);
		reviewer_Tx.setText(ideaPojo.reviewer);
		editor.grabHorizontal = true;
		editor.setEditor(reviewer_Tx, inTableItem, ++inLastColLocation);
		inTableItem.setText(inLastColLocation, reviewer_Tx.getText());
	
		editor = new TableEditor(inTable);
		Text status_Tx = new Text(inTable, SWT.READ_ONLY);
		status_Tx.setText(ideaPojo.status);
		editor.grabHorizontal = true;
		editor.setEditor(status_Tx, inTableItem, ++inLastColLocation);
		inTableItem.setText(inLastColLocation, status_Tx.getText());
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo itemPojo){

		IdeaPojo ideaPojo = (IdeaPojo) itemPojo;
		Group applicationInfo = new Group(itemContentGroup, SWT.LEFT);
		applicationInfo.setText("Application");
		applicationInfo.setLayout(new FillLayout());
		Text applicationText = new Text(applicationInfo, SWT.WRAP | SWT.CENTER);
		applicationText.setText(ideaPojo.application);
		
		formData = new FormData();
		formData.top = new FormAttachment(inPrevGroup);
		applicationInfo.setLayoutData(formData);
	
		Group reviewerInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		reviewerInfo.setText("Reviewer");
		reviewerInfo.setLayout(new FillLayout());
		Text reviewerText = new Text(reviewerInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);
		reviewerText.setText(ideaPojo.reviewer);
		
		formData = new FormData();
		formData.top = new FormAttachment(applicationInfo);
		reviewerInfo.setLayoutData(formData);
	
		Group statusInfo = new Group(itemContentGroup, SWT.LEFT
				| SWT.WRAP | SWT.READ_ONLY);
		statusInfo.setText("Status");
		statusInfo.setLayout(new FillLayout());
		Text statusText = new Text(statusInfo, SWT.WRAP | SWT.READ_ONLY | SWT.CENTER);
		statusText.setText(ideaPojo.status);
		
		formData = new FormData();
		formData.top = new FormAttachment(reviewerInfo);
		statusInfo.setLayoutData(formData);
		
		Group authorInfo = new Group(itemContentGroup, SWT.LEFT);
		authorInfo.setText("Author");
		authorInfo.setLayout(new FillLayout());
		Text authorNameText = new Text(authorInfo, SWT.WRAP
				| SWT.READ_ONLY | SWT.CENTER);
		authorNameText.setText(ideaPojo.author);
	
		formData = new FormData();
		formData.top = new FormAttachment(statusInfo);
		authorInfo.setLayoutData(formData);
		
		return authorInfo;
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
		return IdeaItemDoc.class;
	}

	@Override
	public Class getPrimerDocClass() {
		return IdeasGrouperDoc.class;
	}
	
	@Override
	public GenericGrouperDocPojo getNewPrimerDoc() {
		GenericGrouperDocPojo ideasGrouperDoc = new IdeasGrouperDoc();
		return ideasGrouperDoc;
	}

	public GenericItemDocPojo getBaseDoc(ItemPojo inItemPojo) {
		System.out.println("at1 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at1 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at1 getBaseDoc for itemID title" + inItemPojo.title);


		GenericItemDocPojo ideaItemDoc = new IdeaItemDoc(inItemPojo);

		System.out.println("at2 getBaseDoc for itemID " + inItemPojo.itemID);
		System.out.println("at2 getBaseDoc for itemID relevance" + inItemPojo.relevance);
		System.out.println("at2 getBaseDoc for itemID title" + inItemPojo.title);

		System.out.println("at3 getBaseDoc for doc " + ideaItemDoc);
		System.out.println("at3 getBaseDoc for item " + ideaItemDoc.getItem());
		System.out.println("at3 getBaseDoc for itemID " + ideaItemDoc.getItem().itemID);
		System.out.println("at3 getBaseDoc for itemID relevance" + ideaItemDoc.getItem().relevance);
		System.out.println("at3 getBaseDoc for itemID title" + ideaItemDoc.getItem().title);
		return ideaItemDoc;		
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
	public IdeasGrouperDoc getPrimerDoc() {
		return (IdeasGrouperDoc) primerDoc;
	}
}