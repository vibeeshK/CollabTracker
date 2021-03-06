package contentHandlers;

import java.util.Date;

import colbTrk.ItemPojo;

/**
 * Data holder for time sheet capturing item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class TimeShCapturePojo extends ItemPojo{

	public int captureInterval;
	public int allocationInterval;
	public Date lastTriggeredAt;
	public String captureStartDate;
	public String captureEndDate;
	
	TimeShCapturePojo(int inItemNumber){
		super (inItemNumber);
		initializeAdditionalItemPojoFields();
        System.out.println("After super construction of ItemPojo title = " + title);
	}
	
	void initializeAdditionalItemPojoFields(){
		captureInterval = 0;
		allocationInterval = 0;
		lastTriggeredAt = new Date();		
		captureStartDate = null;
		captureEndDate = null;
		
		System.out.println("@@123 From TimeShCapturePojo initializeAdditionalItemPojoFields done");
	}
}