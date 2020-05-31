package contentHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import colbTrk.ArtifactKeyPojo;
import colbTrk.ContentHandlerSpecs;
import colbTrk.GenericItemHandler;
import colbTrk.ItemPojo;

/**
 * This content handler helps to log a defect against a project task
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class Defect extends GenericItemHandler {

	public final static String DEFECTPREFIX = "Dfct";
	public final static String DEFECTTASKSEPARATOR = "_";
	Text defectDetail_Tx;

	public static String getDefectArtifactName(ContentHandlerSpecs inContentHandlerSpecs, String inProjID, String inTaskID, String inUser, String inTimeStamp) {
		System.out.println("At getDefectArtifactName inProjID " + inProjID);
		System.out.println("At getDefectArtifactName inContentHandlerSpecs.rollAddSeparator " + inContentHandlerSpecs.rollAddSeparator);
		System.out.println("At getDefectArtifactName inTaskID " + inTaskID);
		System.out.println("At getDefectArtifactName inUser " + inUser);
		System.out.println("At getDefectArtifactName inTimeStamp " + inTimeStamp);
		System.out.println("At getDefectArtifactName name would be " + inProjID + inContentHandlerSpecs.rollAddSeparator 
				+ DEFECTPREFIX + inTaskID + DEFECTTASKSEPARATOR +  inUser + inTimeStamp);

		return inProjID + inContentHandlerSpecs.rollAddSeparator 
				+ DEFECTPREFIX + inTaskID + DEFECTTASKSEPARATOR +  inUser + inTimeStamp;
	}
	
	public static String getTaskIdOfDefectID(String inDefectID){
		String taskIdWithDefectPrefixAndPostfix = inDefectID;
		String taskIdWithOutDefectPrefix = taskIdWithDefectPrefixAndPostfix.substring(DEFECTPREFIX.length());
		String taskIdWithOutPosfix = StringUtils.substringBefore(taskIdWithOutDefectPrefix,DEFECTTASKSEPARATOR);		
		return taskIdWithOutPosfix;
	}

	public static HashMap<String,ArrayList<DefectItemPojo>> getDefectsOfTasks(ArrayList<DefectItemPojo> inItemList){
		System.out.println("at start of getDefectsOfTasks inItemList size is " + inItemList.size());
		HashMap<String,ArrayList<DefectItemPojo>> defctsOfTasks = new HashMap<String,ArrayList<DefectItemPojo>>();
		for (DefectItemPojo item : inItemList){
			DefectItemPojo defectItem = (DefectItemPojo) item;
			String taskID = getTaskIdOfDefectID(defectItem.defectID);
			
			ArrayList<DefectItemPojo> defectsOfTask = defctsOfTasks.get(taskID);
			if (defectsOfTask == null){
				defectsOfTask = new ArrayList<DefectItemPojo>();
				defctsOfTasks.put(taskID,defectsOfTask);
			}
			defectsOfTask.add(defectItem);
		}
		return defctsOfTasks;
	}
	
	public static ArrayList<DefectItemPojo> filterOpenDefects(ArrayList<DefectItemPojo> inDefectsList){
		ArrayList<DefectItemPojo> openDefects = null;

		if (inDefectsList!=null) {
			for (DefectItemPojo defectItem : inDefectsList){
				if (!defectItem.defectStatus.equalsIgnoreCase(DefectItemPojo.DEFECTSTATUSVALUES_Completed)) {
					if (openDefects == null){
						openDefects = new ArrayList<DefectItemPojo>();
					}
					openDefects.add(defectItem);				
				}
			}
		}
		return openDefects;
	}

	public static ArtifactKeyPojo getRollupArtifactKeyOfProjDefects(ContentHandlerSpecs inContentHandlerSpecs,
			String inRootNick, String inChildRelevance, String inProjID, String inRelevanceSeparator) {
		//proj Id is passed in place of the child artifactName since its the prefix anyway.
		return inContentHandlerSpecs.getFinalArtifactKeyPojo(inRootNick, inChildRelevance, inProjID, inRelevanceSeparator);
	}

	public void setInitialItemPojoAddlFields(){
		DefectItemPojo defectItemPojo = (DefectItemPojo) primerDoc.getItem();
		defectItemPojo.defectID = contentHandlerSpecs.getChildPartOfArtifactName(defectItemPojo.artifactName);
		defectItemPojo.projectName = contentHandlerSpecs.getParentPartOfChildArtifactName(defectItemPojo.artifactName);
	}
	
	public void checkSetNewItemID() {
		DefectItemPojo defectItemPojo = (DefectItemPojo) primerDoc.getItem();
		if (defectItemPojo.itemID.equalsIgnoreCase("")) {
			defectItemPojo.itemID = defectItemPojo.author + commonData.getCommons().getCurrentTimeStamp();
		}
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo inItemPojo){
 		DefectItemPojo defectItemPojo = (DefectItemPojo) inItemPojo;
		Group lastGroup = inPrevGroup;

		Group defectDetailInfo = new Group(itemContentGroup, SWT.LEFT);
		defectDetailInfo.setText("Description");
		defectDetailInfo.setLayout(new FillLayout());
		if (invokedForEdit) {
			defectDetail_Tx = new Text(defectDetailInfo, SWT.WRAP | SWT.CENTER);
		} else {
			defectDetail_Tx = new Text(defectDetailInfo, SWT.WRAP | SWT.CENTER | SWT.READ_ONLY);			
		}
		defectDetail_Tx.setText(defectItemPojo.defectDetail!=null?defectItemPojo.defectDetail:"");
		
		formData = new FormData();
		formData.top = new FormAttachment(lastGroup);
		formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
		formData.height = PREFERED_DESC_HEIGHT;	// this height setting is to show meaningful size for viewing

		defectDetailInfo.setLayoutData(formData);
		lastGroup = defectDetailInfo;

		return lastGroup;
	}
	
	public void getAddlFieldsOfItemPojo(ItemPojo inItemPojo){
		DefectItemPojo defectItemPojo = (DefectItemPojo) primerDoc.getItem();
		if (defectDetail_Tx != null) {
			defectItemPojo.defectDetail = defectDetail_Tx.getText();
		}		
	}
	
	public DefectItemPojo getItem() {
		return (DefectItemPojo) primerDoc.getItem();
	}
	
	public void testPrinter(String inPrintHead) {
		DefectItemPojo defectItemPojo1 = (DefectItemPojo) primerDoc.getItem();
		System.out.println("In testPrinter from inPrintHead " + inPrintHead);

		System.out.println("In testPrinter itemPojo title is " + primerDoc.getItem().title);
		DefectItemPojo defectItemPojo2 = (DefectItemPojo) primerDoc.getItem();

		System.out.println("In testPrinter Pojo title from primeDoc is " + defectItemPojo2.title);

	}

	public String validateBeforeUIEdit() {
		String validationBeforeEdit = "";
		return validationBeforeEdit;
	}

	@Override
	public Class getPrimerDocClass() {
		return DefectItemDoc.class;
	}

	@Override
	public boolean validateAddlScrFields(){
		System.out.println("At the start of validateAddlScrFields ");
		return true;
	}

	@Override
	public DefectItemDoc getPrimerDoc() {
		return (DefectItemDoc) primerDoc;
	}

	public DefectItemDoc getNewPrimerDoc() {
		return new DefectItemDoc(new DefectItemPojo(-1));
	}	
}