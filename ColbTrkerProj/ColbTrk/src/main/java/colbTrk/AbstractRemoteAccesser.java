package colbTrk;

import java.io.IOException;
import java.io.InputStream;

/**
 * Forms the foundation for connecting to different doc centers
 * 
 * @author Vibeesh Kamalakannan
 * 
 */
public abstract class AbstractRemoteAccesser implements colbTrk.RemoteAccesser {

	public Commons commons = null;
	public RootPojo rootPojo = null;
	public String fileSeparator = null;
	public FolderPathCacheDocPojo folderPathCacheDoc = null;

	public void commonInit(RootPojo inRootPojo, Commons inCommons) throws IOException {
		System.out.println("AbstractRemoteAccesser commonInit called. inRootPojo " + inRootPojo.rootNick
								+ " inRootPojo.cachingFolderPathsRecommended is " 
								+ inRootPojo.cachingFolderPathsRecommended);
	
		rootPojo = inRootPojo;
		commons = inCommons;
		fileSeparator = rootPojo.fileSeparator;
	// if caching recommended, check if a local folderCache file available, if so, build the pojo json doc obj
		if (rootPojo.cachingFolderPathsRecommended){
			folderPathCacheDoc = ((FolderPathCacheDocPojo) commons.getJsonDocFromFile(
					commons.getLocalFoldersCacheDocsFileNameOfRoot(rootPojo.rootNick),FolderPathCacheDocPojo.class));
			System.out.println("At AbstractRemoteAccesser commonInit folderPathCacheDoc read = " + folderPathCacheDoc);
			if (folderPathCacheDoc==null){
				folderPathCacheDoc = new FolderPathCacheDocPojo(); // for first run
			}
		}
		System.out.println("At AbstractRemoteAccesser commonInit end of inRootPojo " + inRootPojo.rootNick
				+ " folderPathCacheDoc is " 
				+ folderPathCacheDoc);
	}
	
	public void endCommunications() throws IOException {
	// if caching recommended, save the save the folderCache pojo json doc obj into local file
		System.out.println("end of communications called. rootPojo.cachingFolderPathsRecommended is " 
								+ rootPojo.cachingFolderPathsRecommended);
		if (rootPojo.cachingFolderPathsRecommended){
			System.out.println("end of communications called. saving cache to "
									+ commons.getLocalFoldersCacheDocsFileNameOfRoot(rootPojo.rootNick));
			commons.putJsonDocToFile(commons.getLocalFoldersCacheDocsFileNameOfRoot(rootPojo.rootNick),
										folderPathCacheDoc);			
		}
		specificRemoteEndProcess();
	}

	public void specificRemoteEndProcess() throws IOException {
	// dummy method to be overridden by the remote processors for special process requirements viz
	// server caching document save to content doc central
		
	}
	
	public void downloadFile(Commons commons, String inRootString, String inRemoteFileName, String inLocalFileName) {
		Commons.logger.info("@@AbstractRemoteAccesser start for downloading " + inRemoteFileName + " into " + inLocalFileName);
		InputStream inputStream = getRemoteFileStream(inRemoteFileName);
		if (inputStream != null) {
			try {
				commons.storeInStream(inputStream, inLocalFileName);
				inputStream.close();
				System.out.println("@@AbstractRemoteAccesser inputStream closed" + inRemoteFileName);
			} catch (IOException e) {
				ErrorHandler.showErrorAndQuit(commons, "Error in downloadFile of AbstractRemoteAccesser " + inRemoteFileName + " into " + inLocalFileName +  commons.processMode, e);
			}
		}
		Commons.logger.info("@@AbstractRemoteAccesser download complete:::");
	}
}