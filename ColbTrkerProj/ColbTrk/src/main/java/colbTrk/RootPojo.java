package colbTrk;

/**
 * Holder of root's details
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class RootPojo {

	public static String SysRootType="System";
	public static String RegRootType="Regular";

	public String rootNick = null; //Unique short identifier of the root
	public String remoteAccesserType = null;
	public String rootType = null;
	public String rootString = null;
	public String fileSeparator = null;
	public String rootPrefix = "";
	public boolean requiresInternet = false;
	public boolean cachingFolderPathsRecommended = false;
	
	public RootPojo() {
	}

	public RootPojo(
			String inRootNick, 
			String inRootString,
			String inRemoteAccesserType,
			String inRootType,
			String inFileSeparator,
			String inRootPrefix,
			boolean inRequiresInternet,
			boolean inCachingFolderPathsRecommended
		) {
		setRootPojo(
				inRootNick, 
				inRootString,
				inRemoteAccesserType,
				inRootType,
				inFileSeparator,
				inRootPrefix,
				inRequiresInternet,
				inCachingFolderPathsRecommended
				);
	}

	public void setRootPojo(
			String inRootNick, 
			String inRootString,
			String inRemoteAccesserType,
			String inRootType,
			String inFileSeparator,
			String inRootPrefix,
			boolean inRequiresInternet,
			boolean inCachingFolderPathsRecommended
	) {
		rootNick = inRootNick;
		rootString = inRootString;
		remoteAccesserType = inRemoteAccesserType;
		rootType =  inRootType;
		fileSeparator = inFileSeparator;
		rootPrefix = inRootPrefix;
		requiresInternet = inRequiresInternet;
		cachingFolderPathsRecommended = inCachingFolderPathsRecommended;
	}
}