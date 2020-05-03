package contentHandlers;

import clobTrk.GenericItemDocPojo;
import clobTrk.ItemPojo;

/**
 * json doc holder for the GenlRequestPojo item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class GenlRequestItemDoc extends GenericItemDocPojo  {

	GenlRequestPojo genlRequestPojo;

	public GenlRequestItemDoc(ItemPojo inItemPojo){
		super(inItemPojo);
	}
	
	public GenlRequestPojo getGenlRequestPojo() {
		return genlRequestPojo;
	}

	@Override
	public void absorbIncomingItemPojo(ItemPojo inItemPojo) {
		genlRequestPojo = (GenlRequestPojo) inItemPojo;
	}
	
	@Override
	public ItemPojo getItem() {
		return genlRequestPojo;
	}
	
	@Override
	public void initializeItem() {
		System.out
		.println("@@123 From GenlRequestItemDoc initilization");
		if (genlRequestPojo == null) {
			genlRequestPojo = new GenlRequestPojo(0);
		}		
		genlRequestPojo.initializeAdditionalItemPojoFields();
	}

	@Override
	public void setItem(ItemPojo inItemPojo) {
		genlRequestPojo = (GenlRequestPojo) inItemPojo;
		System.out.println("at setItem for inItemPojo " + inItemPojo );
		System.out.println("at setItem for itemID " + genlRequestPojo.itemID);
		System.out.println("at setItem for itemID relevance" + genlRequestPojo.relevance);
		System.out.println("at setItem for itemID title" + genlRequestPojo.title);
	}
}