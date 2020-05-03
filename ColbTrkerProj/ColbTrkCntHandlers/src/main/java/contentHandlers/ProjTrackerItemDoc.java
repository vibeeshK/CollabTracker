package contentHandlers;

import colbTrk.GenericItemDocPojo;
import colbTrk.ItemPojo;

/**
 * json doc holder for the Project Tracker
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ProjTrackerItemDoc extends GenericItemDocPojo {

	public ProjTrackerItemDoc(ItemPojo inItemPojo) {
		super(inItemPojo);
	}

	ProjTrackerPojo projTrackerPojo;

	public ProjTrackerPojo getProjTrackerPojo() {
		return projTrackerPojo;
	}

	public void initializeItem() {
		System.out.println("@@123 From ProjTrackerDoc item initilization");
		if (projTrackerPojo == null) {
			projTrackerPojo = new ProjTrackerPojo(0);
		}
		projTrackerPojo.initializeAdditionalItemPojoFields();
	}

	public void setProjTrackerPojo(ProjTrackerPojo projTrackerPojo) {
		this.projTrackerPojo= projTrackerPojo;
	}

	@Override
	public void absorbIncomingItemPojo(ItemPojo inItemPojo) {
		projTrackerPojo = (ProjTrackerPojo) inItemPojo;
	}

	@Override
	public ItemPojo getItem() {
		return projTrackerPojo;
	}

	@Override
	public void setItem(ItemPojo inItemPojo) {
		projTrackerPojo = (ProjTrackerPojo) inItemPojo;
	}
}