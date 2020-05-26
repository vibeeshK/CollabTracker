package contentHandlers;

import colbTrk.ItemPojo;

/**
 * Data holder for a timesheet item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class TimeSheetPojo extends ItemPojo{

	public static int ALLOCATION_TYPE_PREALLOCATED = 10;
	public static int ALLOCATION_TYPE_PREVUSED = 20;
	public static int ALLOCATION_TYPE_MANUALADD = 30;

	public String taskID;
	public String teamID;
	//changed to user generic title		
	//public String description;
	public String capturedAt;
	public int hoursLogged;
	public String attachments;
	public String reviewer;
	public int timeAllocationType;

	TimeSheetPojo(int inItemNumber){
		super (inItemNumber);
		initializeAdditionalItemPojoFields();
	}
	
	void initializeAdditionalItemPojoFields(){
		taskID = "";
		teamID = "";
		//changed to user generic title		
		//description = "";
		hoursLogged = 0; // keep the default as 1 hour for each time someone clicks on timeSheet popup
		capturedAt = null;
		attachments = "";
		reviewer = "";
		timeAllocationType = 0;
	}
}