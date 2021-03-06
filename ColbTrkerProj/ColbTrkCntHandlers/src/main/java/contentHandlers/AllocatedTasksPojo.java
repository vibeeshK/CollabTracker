package contentHandlers;

import java.util.ArrayList;

import colbTrk.ItemPojo;

/**
 * Grouping Doc data holder for allocated tasks on a project for a team member
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class AllocatedTasksPojo extends ItemPojo{

	public ArrayList<AllocatedOneTaskPojo> allocations = null;
	
	public AllocatedTasksPojo(int inItemNumber){
		super (inItemNumber);
        System.out.println("After super construction of ItemPojo title = " + title);		
        initializeAdditionalItemPojoFields();
	}
	
	public void initializeAdditionalItemPojoFields(){
		allocations = new ArrayList<AllocatedOneTaskPojo>();
		System.out.println("@@123 AllocatedTasksPojo initializeAdditionalItemPojoFields done");
	}

	public void absorbData(AllocatedOneTaskPojo inAllocatedOneTaskPojo) {
		System.out.println("XtdAllocatedTasks absorbing absorbData for = " + inAllocatedOneTaskPojo.taskID);
		allocations.add(inAllocatedOneTaskPojo);
	}
}