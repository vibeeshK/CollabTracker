package contentHandlers;

import clobTrk.GenericItemDocPojo;
import clobTrk.ItemPojo;

/**
 * json doc holder for the maintenance of contributing user detail item document
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class UserMaintReqDoc extends GenericItemDocPojo  {

	UserMaintReqPojo userMaintReqPojo;

	public UserMaintReqDoc(ItemPojo inItemPojo){
		super(inItemPojo);
	}
	
	public UserMaintReqPojo getUserItemPojo() {
		return userMaintReqPojo;
	}

	@Override
	public void absorbIncomingItemPojo(ItemPojo inItemPojo) {
		userMaintReqPojo = (UserMaintReqPojo) inItemPojo;
	}
	
	@Override
	public ItemPojo getItem() {
		return userMaintReqPojo;
	}
	
	@Override
	public void initializeItem() {
		System.out
		.println("@@123 From UserItemDoc initilization");
		if (userMaintReqPojo == null) {
			userMaintReqPojo = new UserMaintReqPojo(0);
		}		
		userMaintReqPojo.initializeAdditionalItemPojoFields();
	}

	@Override
	public void setItem(ItemPojo inItemPojo) {
		userMaintReqPojo = (UserMaintReqPojo) inItemPojo;
		System.out.println("at setItem for inItemPojo " + inItemPojo );
		System.out.println("at setItem for itemID " + userMaintReqPojo.itemID);
		System.out.println("at setItem for itemID relevance" + userMaintReqPojo.relevance);
		System.out.println("at setItem for itemID title" + userMaintReqPojo.title);
	}
}