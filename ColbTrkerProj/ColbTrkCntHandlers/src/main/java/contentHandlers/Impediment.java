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
 * This content handler helps to log an impediment
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class Impediment extends GenericItemHandler {

	public final static String IMPPREFIX = "Impdmt";
	public final static String IMPTASKSEPARATOR = "_";
	Text impedimentDetail_Tx;

	public static String getImpedimentArtifactName(ContentHandlerSpecs inContentHandlerSpecs, String inProjID, String inTaskID, String inUser, String inTimeStamp) {
		return inProjID + inContentHandlerSpecs.rollAddSeparator 
				+  IMPPREFIX + inTaskID + IMPTASKSEPARATOR +  inUser + inTimeStamp;
	}

	public static String getTaskIdOfImpedimentID(String inImpedimentID){
		String taskIdWithImpPrefixAndPostfix = inImpedimentID;
		String taskIdWithOutImpPrefix = taskIdWithImpPrefixAndPostfix.substring(IMPPREFIX.length());
		String taskIdWithOutPosfix = StringUtils.substringBefore(taskIdWithOutImpPrefix,IMPTASKSEPARATOR);
		return taskIdWithOutPosfix;
	}	

	public static HashMap<String,ArrayList<ImpedimentItemPojo>> getImpedimentsOfTasks(ArrayList<ImpedimentItemPojo> inItemList){
		System.out.println("at start of getImpedimentsOfTasks inItemList size is " + inItemList.size());
		HashMap<String,ArrayList<ImpedimentItemPojo>> impedsOfTasks = new HashMap<String,ArrayList<ImpedimentItemPojo>>();
		for (ImpedimentItemPojo item : inItemList){
			ImpedimentItemPojo impedimentItem = (ImpedimentItemPojo) item;
			String taskID = getTaskIdOfImpedimentID(impedimentItem.impedimentID);
			
			ArrayList<ImpedimentItemPojo> impedimentsOfTask = impedsOfTasks.get(taskID);

			System.out.println("at getImpedimentsOfTasks taskID is " + taskID);
			System.out.println("at getImpedimentsOfTasks impedimentItem.impedimentID is " + impedimentItem.impedimentID);
			System.out.println("at getImpedimentsOfTasks getTaskIdOfImpedimentID(impedimentItem.impedimentID) is " + getTaskIdOfImpedimentID(impedimentItem.impedimentID));
			
			if (impedimentsOfTask == null){
				impedimentsOfTask = new ArrayList<ImpedimentItemPojo>();
				impedsOfTasks.put(taskID,impedimentsOfTask);
			}
			impedimentsOfTask.add(impedimentItem);
			System.out.println("at getImpedimentsOfTasks impedimentsOfTask size is " + impedimentsOfTask.size());
		}
		return impedsOfTasks;
	}
	
	public static ArrayList<ImpedimentItemPojo> filterOpenImpediments(ArrayList<ImpedimentItemPojo> inImpedimentsList){
		ArrayList<ImpedimentItemPojo> openImpediments = null;

		if (inImpedimentsList!=null) {
			for (ImpedimentItemPojo impedimentItem : inImpedimentsList){
				if (!impedimentItem.impedimentStatus.equalsIgnoreCase(ImpedimentItemPojo.IMPEDIMENTSTATUSVALUES_Completed)) {
					if (openImpediments == null){
						openImpediments = new ArrayList<ImpedimentItemPojo>();
					}
					openImpediments.add(impedimentItem);				
				}
			}
		}
		return openImpediments;
	}

	public static ArtifactKeyPojo getRollupArtifactKeyOfProjImpediments(ContentHandlerSpecs inContentHandlerSpecs,
			String inRootNick, String inChildRelevance, String inProjID, String inRelevanceSeparator) {
		//proj Id is passed in place of the child artifactName since its the prefix anyway.
		return inContentHandlerSpecs.getFinalArtifactKeyPojo(inRootNick, inChildRelevance, inProjID, inRelevanceSeparator);
	}

	public void setInitialItemPojoAddlFields(){
		ImpedimentItemPojo impedimentItemPojo = (ImpedimentItemPojo) primerDoc.getItem();
		impedimentItemPojo.impedimentID = contentHandlerSpecs.getChildPartOfArtifactName(impedimentItemPojo.artifactName);
		impedimentItemPojo.projectName = contentHandlerSpecs.getParentPartOfChildArtifactName(impedimentItemPojo.artifactName);
	}
	
	public void checkSetNewItemID() {
		ImpedimentItemPojo impedimentItemPojo = (ImpedimentItemPojo) primerDoc.getItem();
		if (impedimentItemPojo.itemID.equalsIgnoreCase("")) {
			impedimentItemPojo.itemID = impedimentItemPojo.author + commonData.getCommons().getCurrentTimeStamp();
		}
	}

	public Group setAddlFieldsForItemDisplay(Group itemContentGroup, Group inPrevGroup,FormData formData, ItemPojo inItemPojo){
		ImpedimentItemPojo impedimentItemPojo = (ImpedimentItemPojo) inItemPojo;
		Group lastGroup = inPrevGroup;

		Group impedimentDetailInfo = new Group(itemContentGroup, SWT.LEFT);
		impedimentDetailInfo.setText("Description");
		impedimentDetailInfo.setLayout(new FillLayout());
		if (invokedForEdit) {
			impedimentDetail_Tx = new Text(impedimentDetailInfo, SWT.WRAP | SWT.CENTER);
		} else {
			impedimentDetail_Tx = new Text(impedimentDetailInfo, SWT.WRAP | SWT.CENTER | SWT.READ_ONLY);			
		}
		impedimentDetail_Tx.setText(impedimentItemPojo.impedimentDetail!=null?impedimentItemPojo.impedimentDetail:"");
		
		formData = new FormData();
		formData.top = new FormAttachment(lastGroup);
		formData.width = PREFERED_ITEM_PANEL_WIDTH;	// this width setting is to show meaningful size for viewing
		impedimentDetailInfo.setLayoutData(formData);
		lastGroup = impedimentDetailInfo;

		return lastGroup;
	}
	
	public void getAddlFieldsOfItemPojo(ItemPojo inItemPojo){
		ImpedimentItemPojo impedimentItemPojo = (ImpedimentItemPojo) primerDoc.getItem();
		if (impedimentDetail_Tx != null) {
			impedimentItemPojo.impedimentDetail = impedimentDetail_Tx.getText();
		}		
	}
	
	public ImpedimentItemPojo getItem() {
		return (ImpedimentItemPojo) primerDoc.getItem();
	}
	
	public void testPrinter(String inPrintHead) {
		ImpedimentItemPojo impedimentItemPojo1 = (ImpedimentItemPojo) primerDoc.getItem();
		System.out.println("In testPrinter from inPrintHead " + inPrintHead);

		System.out.println("In testPrinter itemPojo title is " + primerDoc.getItem().title);
		ImpedimentItemPojo impedimentItemPojo2 = (ImpedimentItemPojo) primerDoc.getItem();

		System.out.println("In testPrinter Pojo title from primeDoc is " + impedimentItemPojo2.title);

	}

	public String validateBeforeUIEdit() {
		String validationBeforeEdit = "";
		return validationBeforeEdit;
	}

	@Override
	public Class getPrimerDocClass() {
		return ImpedimentItemDoc.class;
	}

	@Override
	public boolean validateAddlScrFields(){
		System.out.println("At the start of validateAddlScrFields ");
		return true;
	}

	@Override
	public ImpedimentItemDoc getPrimerDoc() {
		return (ImpedimentItemDoc) primerDoc;
	}

	public ImpedimentItemDoc getNewPrimerDoc() {
		return new ImpedimentItemDoc(new ImpedimentItemPojo(-1));
	}
}