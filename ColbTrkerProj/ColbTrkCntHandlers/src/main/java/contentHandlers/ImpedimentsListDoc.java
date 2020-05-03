package contentHandlers;

import java.util.ArrayList;

import clobTrk.GenericGrouperDocPojo;
import clobTrk.ItemPojo;

/**
 * json doc holder for Impediment grouper
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ImpedimentsListDoc extends GenericGrouperDocPojo {

	ArrayList<ImpedimentItemPojo> impedimentList;

	public ArrayList<ImpedimentItemPojo> getItemList() {
		return impedimentList;
	}
	public ArrayList<ImpedimentItemPojo> createItemList() {
		impedimentList = new ArrayList<ImpedimentItemPojo>();
		return impedimentList;
	}

	public void setItem(int inCurrentLocation,ItemPojo inItemPojo){
		impedimentList.set(inCurrentLocation, (ImpedimentItemPojo) inItemPojo);
	}

	public void addItem(ItemPojo inItemPojo) {
		impedimentList.add((ImpedimentItemPojo) inItemPojo);
	}
	
	public void setItemList(ArrayList<?> inItemPojoList) {
		impedimentList = (ArrayList<ImpedimentItemPojo>) inItemPojoList;
	}		
}