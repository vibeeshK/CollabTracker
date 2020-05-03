package contentHandlers;

import colbTrk.GenericItemDocPojo;
import colbTrk.ItemPojo;

/**
 * json doc holder for Time sheet capture item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class TimeShCaptureItemDoc extends GenericItemDocPojo {

	public TimeShCaptureItemDoc(ItemPojo inItemPojo) {
		super(inItemPojo);
	}

	TimeShCapturePojo timeShCapturePojo;

	public TimeShCapturePojo getTimeShCapturePojo() {
		return timeShCapturePojo;
	}

	public void initializeItem() {
		System.out
				.println("@@123 From TimeShCaptureItemDoc item initilization");
		if (timeShCapturePojo == null) {
			timeShCapturePojo = new TimeShCapturePojo(0);
		}

		timeShCapturePojo.initializeAdditionalItemPojoFields();
	}

	public void setTimeShCapturePojo(TimeShCapturePojo timeShCapturePojo) {
		this.timeShCapturePojo = timeShCapturePojo;
	}

	@Override
	public void absorbIncomingItemPojo(ItemPojo inItemPojo) {
		timeShCapturePojo = (TimeShCapturePojo) inItemPojo;
	}

	@Override
	public ItemPojo getItem() {
		return timeShCapturePojo;
	}

	@Override
	public void setItem(ItemPojo inItemPojo) {
		timeShCapturePojo = (TimeShCapturePojo) inItemPojo;
	}
}