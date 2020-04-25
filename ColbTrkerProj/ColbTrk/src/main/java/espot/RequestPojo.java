package espot;

/**
 * Holder of each request record while processing at server side
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class RequestPojo {

	final static String ARTIFACT = "artifact";
	final static String REVIEW = "review";
	
	public String relevance;
	public String artifactName;
	public String itemName;
	//public String erlStatus;
	public String reviewID;
	public String contentType;
	public String requestor;
	public String author;
	public String contentFileName;
	public String uploadedTimeStamp;
	public String artifactOrReview;

	public RequestPojo() {
	}

	public RequestPojo(String inRelevance,
			String inArtifactName, 
			String inItemName, 
			//String inERLStatus, 
			String inReviewID, 
			String inContenType, 
			String inRequestor, 
			String inAuthor, 
			String inContentPathFile, 
			String inUploadedTimeStamp, 
			String inArtifactOrReview
			) {
		relevance = inRelevance;
		artifactName = inArtifactName;
		itemName = inItemName;
		//erlStatus = inERLStatus;
		reviewID = inReviewID;
		contentType = inContenType;
		requestor = inRequestor;
		author = inAuthor;
		contentFileName = inContentPathFile;
		uploadedTimeStamp = inUploadedTimeStamp;
		artifactOrReview = inArtifactOrReview;
		
		System.out.println("Creating RequestPojo contenType = " + contentType);
	}
}