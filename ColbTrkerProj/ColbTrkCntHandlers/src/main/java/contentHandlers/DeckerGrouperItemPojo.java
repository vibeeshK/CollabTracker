package contentHandlers;

/**
 * json doc holder for a decker item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class DeckerGrouperItemPojo extends DeckerLiteItemPojo {

	public String itemSummaryFile;
	public String summaryShKeyColVal;

	DeckerGrouperItemPojo(int inItemNumber) {
		super(inItemNumber);
	}
	DeckerGrouperItemPojo(String inContentType, String inRelevance, String inArtifactName) {
		super(inContentType, inRelevance, inArtifactName);
		itemSummaryFile = "";
		summaryShKeyColVal = "";
	}
}