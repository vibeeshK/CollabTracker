package contentHandlers;

import java.util.ArrayList;
import java.util.HashMap;

import espot.GenericGrouperDocPojo;
import espot.ItemPojo;
import espot.ScreenPatternFieldPojo;

/**
 * json doc holder for DeckerLite
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class DeckerLiteDocPojo extends GenericGrouperDocPojo {

	public static String AUTHORCOLHDRNAME = "OwnerID";
	public static String KEYCOLHDRNAME = "TaskID";
	public String combinedFileName;
	
	public HashMap<String,ScreenPatternFieldPojo> screenAddlFieldPattern;
	public HashMap<String,String> screenFieldDefaults;
	public ArrayList<String> centerColAddlHeaders;

	public String authorColHdrName;
	public String keyColHdrName;
	
	public ArrayList<DeckerLiteItemPojo> deckerLiteItemPojoList;

	public boolean noCombining;				//from Button dontCombineCheckBox;
	public boolean considerOnlyFromAuthor;	//from Button considerOnlyFromAuthorCheckBox;
	public boolean keyBasedCombining;		//from Button keyBasedCombineCheckBox;

	public boolean deckerEdited;			//if a new save is done for the decker
	
	public DeckerLiteDocPojo(){
		super();
		combinedFileName = "";
		authorColHdrName = AUTHORCOLHDRNAME;
		keyColHdrName = KEYCOLHDRNAME;
		deckerEdited = false;
	}

	public ArrayList<?> getItemList() {
		return deckerLiteItemPojoList;
	}

	public ArrayList<?> createItemList() {
		deckerLiteItemPojoList = new ArrayList<DeckerLiteItemPojo>();

		System.out.println("at DeckerLiteDocPojo createItemList called = " + deckerLiteItemPojoList);
		
		return deckerLiteItemPojoList;
	}

	public void setItem(int inCurrentLocation,ItemPojo inItemPojo){
		deckerLiteItemPojoList.set(inCurrentLocation, (DeckerLiteItemPojo) inItemPojo);
	}

	public void addItem(ItemPojo inItemPojo) {
		deckerLiteItemPojoList.add((DeckerLiteItemPojo) inItemPojo);
	}

	public void setItemList(ArrayList<?> inItemPojoList) {
		deckerLiteItemPojoList = (ArrayList<DeckerLiteItemPojo>) inItemPojoList;
	}	
}