package contentHandlers;

import java.util.Date;

import colbTrk.ItemPojo;

/**
 * Data holder for a project task item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ProjTaskItemPojo extends ItemPojo{

	final static String CONTENT_TYPE = "ProjTask";

	public final static String TASKSTATUSVALUES_YesToStart = "YesToStart";
	public final static String TASKSTATUSVALUES_InProgress = "InProgress";
	public final static String TASKSTATUSVALUES_Completed = "Completed";

	public String projectName;

	public String taskID;
	public String description;
	public String lead;
	public double plannedHours;
	public Date plannedStart;
	public Date plannedEnd;
	public String taskStatus;
	public String actualStart;
	public String actualEnd;
	public double burntHours;
	public double forecastOverrunHoursAtCompletion;
	public double forecastTotalHoursAtCompletion;
	public Date expectedEnd;
	public String remark;

	public ProjTaskItemPojo(int inItemNumber){
		super (inItemNumber);
        System.out.println("After super construction of ItemPojo title = " + title);		
        initializeAdditionalItemPojoFields();
	}

	public void initializeAdditionalItemPojoFields(){
		System.out.println("@@123 ProjTaskItemPojo initializeAdditionalItemPojoFields done");
		projectName="";
		taskID="";
		description="";
		lead="";
		plannedHours=0.0;
		plannedStart=null;
		plannedEnd=null;
		taskStatus="";
		actualStart=null;
		actualEnd=null;
		burntHours=0.0;
		forecastOverrunHoursAtCompletion=0.0;
		forecastTotalHoursAtCompletion=0.0;
		expectedEnd=null;
		remark="";
	}
}