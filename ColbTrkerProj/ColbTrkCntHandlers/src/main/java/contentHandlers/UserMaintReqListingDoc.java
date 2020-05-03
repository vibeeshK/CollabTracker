package contentHandlers;

import java.util.ArrayList;

import colbTrk.GenericGrouperDocPojo;
import colbTrk.ItemPojo;

/**
 * json doc holder of the grouper of contributing users details
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class UserMaintReqListingDoc extends GenericGrouperDocPojo {

	ArrayList<UserMaintReqPojo> userMaintReqItemsList;

	public ArrayList<UserMaintReqPojo> getItemList() {
		return userMaintReqItemsList;
	}
	public ArrayList<UserMaintReqPojo> createItemList() {
		userMaintReqItemsList = new ArrayList<UserMaintReqPojo>();
		return userMaintReqItemsList;
	}
	public void setItem(int inCurrentLocation,ItemPojo inItemPojo){
		userMaintReqItemsList.set(inCurrentLocation, (UserMaintReqPojo) inItemPojo);
	}
	public void addItem(ItemPojo inItemPojo) {
		userMaintReqItemsList.add((UserMaintReqPojo) inItemPojo);
	}
	public void setItemList(ArrayList<?> inItemPojoList) {
		userMaintReqItemsList = (ArrayList<UserMaintReqPojo>) inItemPojoList;
	}	
}
