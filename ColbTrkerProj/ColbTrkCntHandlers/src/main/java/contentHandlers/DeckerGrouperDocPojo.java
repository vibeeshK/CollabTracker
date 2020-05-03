package contentHandlers;

import java.util.ArrayList;

import colbTrk.ItemPojo;

/**
 * json doc holder for DeckerGrouper
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class DeckerGrouperDocPojo extends DeckerLiteDocPojo {

	private static String DeckerGrprAUTHORCOLHDR = "PM";
	private static String DeckerGrprKEYCOLHDR = "EngagementID";
	
	public String summaryFilePage;
	public String detailFilePage;
	public int summaryShKeyColSeqNum;	// zero based
	public ArrayList<DeckerGrouperItemPojo> deckerGrouperItemPojoList;
	
	public DeckerGrouperDocPojo(){
		super();
		summaryFilePage = "";
		detailFilePage = "";
		summaryShKeyColSeqNum = -1;	// zero based

		authorColHdrName = DeckerGrprAUTHORCOLHDR;
		keyColHdrName = DeckerGrprKEYCOLHDR;		
	}

	public ArrayList<?> getItemList() {
		return deckerGrouperItemPojoList;
	}
	
	public ArrayList<?> createItemList() {
		deckerGrouperItemPojoList = new ArrayList<DeckerGrouperItemPojo>();

		System.out.println("at DeckerGrouperDocPojo createItemList called = " + deckerGrouperItemPojoList);
		
		return deckerGrouperItemPojoList;
	}

	public void setItem(int inCurrentLocation,ItemPojo inItemPojo){
		deckerGrouperItemPojoList.set(inCurrentLocation, (DeckerGrouperItemPojo) inItemPojo);
	}

	public void addItem(ItemPojo inItemPojo) {
		deckerGrouperItemPojoList.add((DeckerGrouperItemPojo) inItemPojo);
	}

	public void setItemList(ArrayList<?> inItemPojoList) {
		deckerGrouperItemPojoList = (ArrayList<DeckerGrouperItemPojo>) inItemPojoList;
	}	
}