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

	public void commonInit(RootPojo inRootPojo, Commons inCommons) {
		rootPojo = inRootPojo;
		commons = inCommons;
		fileSeparator = rootPojo.fileSeparator;
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