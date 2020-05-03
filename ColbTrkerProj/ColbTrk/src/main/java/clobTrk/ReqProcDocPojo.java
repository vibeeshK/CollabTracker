package clobTrk;

import java.util.HashMap;

/**
 * Holder of request processing status doc data
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ReqProcDocPojo {

	boolean dbTobeRenewed;
	public HashMap<String,ReqTrackItem> reqTrackItems;

	public ReqProcDocPojo(){
		dbTobeRenewed = false;
		reqTrackItems = new HashMap<String,ReqTrackItem>();
	}
}