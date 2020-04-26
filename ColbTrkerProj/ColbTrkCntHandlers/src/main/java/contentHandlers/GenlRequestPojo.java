package contentHandlers;

import espot.ItemPojo;

/**
 * Data holder for an GenlRequestPojo item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class GenlRequestPojo extends ItemPojo{

	String requestDesc;	
	String requestCategory;	
	String requestStartDate;
	String requestEndDate;

	GenlRequestPojo(int inItemNumber){
		super (inItemNumber);
		initializeAdditionalItemPojoFields();
	}
	
	void initializeAdditionalItemPojoFields(){
		System.out.println("initializeAdditionalItemPojoFields at genlRequestPojoPojo class");

		requestDesc = "";
		requestCategory = "";	
		requestStartDate = "";
		requestEndDate = "";		
	}
}