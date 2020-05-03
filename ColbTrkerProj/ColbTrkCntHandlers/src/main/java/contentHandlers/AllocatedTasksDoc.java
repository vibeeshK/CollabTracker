package contentHandlers;

import java.util.ArrayList;

import clobTrk.GenericGrouperDocPojo;
import clobTrk.ItemPojo;

/**
 * json doc holder for allocated tasks grouper
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class AllocatedTasksDoc extends GenericGrouperDocPojo {

	ArrayList<AllocatdTaskItemPojo> allocatedTasksList;

	public ArrayList<AllocatdTaskItemPojo> getItemList() {
		return allocatedTasksList;
	}
	public ArrayList<AllocatdTaskItemPojo> createItemList() {
		allocatedTasksList = new ArrayList<AllocatdTaskItemPojo>();
		return allocatedTasksList;
	}

	public void setItem(int inCurrentLocation,ItemPojo inItemPojo){
		allocatedTasksList.set(inCurrentLocation, (AllocatdTaskItemPojo) inItemPojo);
	}

	public void addItem(ItemPojo inItemPojo) {
		allocatedTasksList.add((AllocatdTaskItemPojo) inItemPojo);
	}
	
	public void setItemList(ArrayList<?> inItemPojoList) {
		allocatedTasksList = (ArrayList<AllocatdTaskItemPojo>) inItemPojoList;
	}		
}