package contentHandlers;

import java.util.ArrayList;

import espot.GenericGrouperDocPojo;
import espot.ItemPojo;

/**
 * json doc holder for Time sheet grouper
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class TimeShRollupDoc extends GenericGrouperDocPojo {

	ArrayList<TimeSheetPojo> timeShItemsList;

	public ArrayList<TimeSheetPojo> getItemList() {
		return timeShItemsList;
	}
	public ArrayList<TimeSheetPojo> createItemList() {
		timeShItemsList = new ArrayList<TimeSheetPojo>();
		return timeShItemsList;
	}
	public void setItem(int inCurrentLocation,ItemPojo inItemPojo){
		timeShItemsList.set(inCurrentLocation, (TimeSheetPojo) inItemPojo);
	}
	public void addItem(ItemPojo inItemPojo) {
		timeShItemsList.add((TimeSheetPojo) inItemPojo);
	}
	public void setItemList(ArrayList<?> inItemPojoList) {
		timeShItemsList = (ArrayList<TimeSheetPojo>) inItemPojoList;
	}	
}