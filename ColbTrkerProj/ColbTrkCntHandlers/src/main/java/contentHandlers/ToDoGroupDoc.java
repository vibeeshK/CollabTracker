package contentHandlers;

import java.util.ArrayList;

import espot.GenericGrouperDocPojo;
import espot.ItemPojo;

/**
 * json doc holder for ToDo grouper
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ToDoGroupDoc extends GenericGrouperDocPojo {

	ArrayList<ToDoPojo> toDoPojoList;

	public ArrayList<ToDoPojo> getItemList() {
		return toDoPojoList;
	}
	public ArrayList<ToDoPojo> createItemList() {
		toDoPojoList = new ArrayList<ToDoPojo>();
		return toDoPojoList;
	}
	public void setItem(int inCurrentLocation,ItemPojo inItemPojo){
		toDoPojoList.set(inCurrentLocation, (ToDoPojo) inItemPojo);
	}
	public void addItem(ItemPojo inItemPojo) {
		toDoPojoList.add((ToDoPojo) inItemPojo);
	}
	public void setItemList(ArrayList<?> inItemPojoList) {
		toDoPojoList = (ArrayList<ToDoPojo>) inItemPojoList;
	}	
}