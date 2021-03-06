package colbTrk;

/**
 * Holder of request status data within the request process doc
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ReqTrackItem {

	//reqFileName would be the key for this object maintained outside
	public RequestProcesserPojo requestProcesserPojo;
	public boolean artifactMoveComplete;
	public boolean oldestContentVersionArchived;	
	public boolean erlMasterDBUpdated;
	public boolean reqRespFileUpdated;
	public boolean reqArchived;
	public boolean reqProcessOKSoFar = true;
	public String errorMessage;
	
	public ERLVersioningDocItem erlVersioningDocItem;
	//requestArchived = false; not required as the request would hvae been already archived and 
	//this record would have been removed from hashMap.
	public ReqTrackItem(RequestProcesserPojo inRequestProcesserPojo) {
		requestProcesserPojo = inRequestProcesserPojo;
		artifactMoveComplete = false;
		oldestContentVersionArchived = false;		
		erlMasterDBUpdated = false;
		reqRespFileUpdated = false;
		reqArchived = false;
		erlVersioningDocItem = null;
		reqProcessOKSoFar = true;
		errorMessage = "";
	}
}
