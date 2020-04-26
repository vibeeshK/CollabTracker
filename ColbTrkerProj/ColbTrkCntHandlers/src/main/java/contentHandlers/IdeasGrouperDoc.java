package contentHandlers;

import java.util.ArrayList;

import espot.GenericGrouperDocPojo;
import espot.ItemPojo;

/**
 * json doc holder for Idea grouper
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class IdeasGrouperDoc extends GenericGrouperDocPojo {

	ArrayList<IdeaPojo> ideaItemsList;

	public ArrayList<IdeaPojo> getItemList() {
		return ideaItemsList;
	}
	public ArrayList<IdeaPojo> createItemList() {
		ideaItemsList = new ArrayList<IdeaPojo>();
		return ideaItemsList;
	}
	public void setItem(int inCurrentLocation,ItemPojo inItemPojo){
		ideaItemsList.set(inCurrentLocation, (IdeaPojo) inItemPojo);
	}
	public void addItem(ItemPojo inItemPojo) {
		ideaItemsList.add((IdeaPojo) inItemPojo);
	}
	public void setItemList(ArrayList<?> inItemPojoList) {
		ideaItemsList = (ArrayList<IdeaPojo>) inItemPojoList;
	}	
}