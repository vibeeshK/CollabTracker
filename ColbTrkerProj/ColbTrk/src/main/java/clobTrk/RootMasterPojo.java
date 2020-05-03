package clobTrk;

/**
 * Not used / not fully implemented. This class was intended to hold the mapping of access group.
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class RootMasterPojo {

	public String rootString;
	public String accessGroup;

	public RootMasterPojo() {
	}

	public RootMasterPojo(String inRootString, String inAccessGroup) {

		setRootsPojo(inRootString, inAccessGroup);
	}

	public void setRootsPojo(String inRootString, String inAccessGroup) {

		rootString = inRootString;
		accessGroup = inAccessGroup;
	}
}