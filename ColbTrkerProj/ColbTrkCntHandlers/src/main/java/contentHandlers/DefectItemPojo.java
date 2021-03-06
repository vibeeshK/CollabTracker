package contentHandlers;

import java.util.ArrayList;
import java.util.Date;

import colbTrk.ItemPojo;

/**
 * Data holder for a defect item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class DefectItemPojo extends ItemPojo {

	final static String CONTENT_TYPE = "Defect";

	public final static String DEFECTSTATUSVALUES_YesToStart = "YesToStart";
	public final static String DEFECTSTATUSVALUES_InProgress = "InProgress";
	public final static String DEFECTSTATUSVALUES_ReadyForReview = "ReadyForReview";
	public final static String DEFECTSTATUSVALUES_Completed = "Completed";
	//changed to user generic title
	//public String description;
	public String projectName;
	public String defectID;
	public String defectDetail;
	public String severity;
	public String raisedBy;
	public Date openedDate;
	public String defectStatus;
	public Date expectedEnd;
	public Date actualEnd;
	public double pctgCompleted;
	public double burntHours;
	public String remark;

	public DefectItemPojo(int inItemNumber){
		super (inItemNumber);
        System.out.println("After super construction of ItemPojo title = " + title);		
        initializeAdditionalItemPojoFields();
	}

	public void initializeAdditionalItemPojoFields(){
		System.out.println("@@123 DefectItemPojo initializeAdditionalItemPojoFields done");
		projectName="";
		defectID="";
		defectDetail="";
		severity="";
		raisedBy="";
		openedDate=null;
		defectStatus="";
		expectedEnd=null;
		actualEnd=null;
		pctgCompleted=0.0;
		burntHours=0.0;
		remark="";
	}

	public static ArrayList<DefectItemPojo> getOpenDefectsList(ArrayList<DefectItemPojo> inDefectItems){
		ArrayList<DefectItemPojo> openDefectItems = new ArrayList<DefectItemPojo>();
		for (DefectItemPojo defectItem : inDefectItems) {
			if (!defectItem.defectStatus.equalsIgnoreCase(DEFECTSTATUSVALUES_Completed)){
				openDefectItems.add(defectItem);
			}	
		}
		return openDefectItems;
	}
	
	public static String getDefectsText(ArrayList<DefectItemPojo> inDefectItems){
		String defectsText = "";
		for (DefectItemPojo defectItem : inDefectItems) {
			//changed to user generic title
			//defectsText = defectsText + "%n" + defectItem.itemID + " OpenedOn :" + defectItem.openedDate + ": " + defectItem.description;
			defectsText = defectsText + "%n" + defectItem.itemID + " OpenedOn :" + defectItem.openedDate + ": " + defectItem.title;
		}
		return defectsText;
	}	
}