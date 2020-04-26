package contentHandlers;

import java.util.Date;
import java.util.HashMap;
import espot.ItemPojo;

/**
 * json doc holder for a deckerLite item
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class DeckerLiteItemPojo extends ItemPojo {

	public Date deckingCompletedAt;
	public HashMap<String,String> addlFieldValues;
	public String itemDetailFileInRelevancePath;
	public int numberOfRecsCombined;

	DeckerLiteItemPojo(int inItemNumber) {
		super(inItemNumber);
	}
	DeckerLiteItemPojo(String inContentType, String inRelevance, String inArtifactName) {
		super(-1);
		contentType = inContentType;
		relevance = inRelevance;
		artifactName = inArtifactName;
		deckingCompletedAt = null;
		numberOfRecsCombined = 0;
	}
	void absorbScreenFieldValues(HashMap<String,String> inScreenFieldValues){
		addlFieldValues = new HashMap<String,String>();
		for (String screenHdr : inScreenFieldValues.keySet()){
			addlFieldValues.put(screenHdr,inScreenFieldValues.get(screenHdr));
		}
	}
}