package contentHandlers;

import clobTrk.GenericItemDocPojo;
import clobTrk.ItemPojo;

/**
 * json doc holder for Proj task item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ProjTaskItemDoc extends GenericItemDocPojo {

	ProjTaskItemPojo projTaskItemPojo;

	public ProjTaskItemDoc(ItemPojo inItemPojo) {
		super(inItemPojo);
	}
	
	public ProjTaskItemPojo getAllocatedTasksPojo() {
		return projTaskItemPojo;
	}

	public void initializeItem() {
		System.out
				.println("@@123 From AllocatedTasksItemDoc item initilization");
		if (projTaskItemPojo == null) {
			projTaskItemPojo = new ProjTaskItemPojo(0);
		}

		projTaskItemPojo.initializeAdditionalItemPojoFields();
	}

	@Override
	public void absorbIncomingItemPojo(ItemPojo inItemPojo) {
		projTaskItemPojo = (ProjTaskItemPojo) inItemPojo;
	}

	@Override
	public ItemPojo getItem() {
		return projTaskItemPojo;
	}

	@Override
	public void setItem(ItemPojo inItemPojo) {
		projTaskItemPojo = (ProjTaskItemPojo) inItemPojo;
	}
}