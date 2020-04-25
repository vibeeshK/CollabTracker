package espot;

import java.util.HashMap;

/**
 * Convenience class that holds the different file versions of the ERL
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ERLVersionDocPojo {

	public HashMap<String,ERLVersioningDocItem> erlVersionTrackItems;	// for each Artifact/Remark Indicator + Relevance+ERLName combo string

	public ERLVersionDocPojo(){
		erlVersionTrackItems = new HashMap<String,ERLVersioningDocItem>();
	}
}