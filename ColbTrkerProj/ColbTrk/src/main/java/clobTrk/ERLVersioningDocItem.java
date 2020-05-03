package clobTrk;

import java.util.ArrayList;

/**
 * Convenience class for holding a specific ERL's version details within the versioning file
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ERLVersioningDocItem {

	final static String OLD_PREFIX = "old_";
	final static String VER_NODE_SEPARATOR = "_";

	public static String getERLVersioningItemKey(String inRelevance, String inArtifactName, String inArtifactRRemarkIndicator) {
		return inRelevance + VER_NODE_SEPARATOR + inArtifactName + VER_NODE_SEPARATOR + inArtifactRRemarkIndicator;
	}


	public ArrayList<String> erlVerList;

	public ERLVersioningDocItem (){
		erlVerList = new ArrayList<String>();		
	}
	
	public void stackUp(String inBaseFileName){

		if (erlVerList == null) {
			erlVerList = new ArrayList<String>();
		}
		if (!erlVerList.contains(inBaseFileName)) {
			// for grouper items the old file is just updated hence no new version will be created			
			erlVerList.add(inBaseFileName);		
		}
	}

	public String getOldestVerFileDetail(int inVersionsKeepLimit){
		String oldestVerFileDetail = null;
		if (erlVerList != null && erlVerList.size() > inVersionsKeepLimit) {
			oldestVerFileDetail  = erlVerList.get(0);
		}
		return oldestVerFileDetail;
	}
	
	public void removeOldestVerFileDetail(){
		if (erlVerList != null && erlVerList.size() > 0) {
			erlVerList.remove(0);
		}
	}
}