
package colbTrk;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import commonTechs.OrchestrationData;
import commonTechs.OrchestrationUI;

/**
 * Checks access to contentRoot by checking exixtence of a give folder viz. catalogpublishfolder
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class TesterContentRootAccessCheck {

	private RemoteAccesser remoteAccesser;
	Commons commons;
	HashMap<String, RootPojo> publishedRootsMap;
	RootPojo rootPojo;
	
	TesterContentRootAccessCheck(Commons inCommons, String inRootNick){
		commons = inCommons;
		remoteAccesser = RemoteAccessManager.getInstance(commons, inRootNick);
		publishedRootsMap = PublishedRootsHandler.getPublishedRoots(commons);
		rootPojo = publishedRootsMap.get(inRootNick);
	}

	void testAccess(){
		String catalogpublishFolder = rootPojo.rootString
				+ rootPojo.fileSeparator
				+ commons.getServerSideSideCatalogDbPublishFolderOfRoot(rootPojo.fileSeparator);
		boolean checkFlag = false;
		try {
			checkFlag = remoteAccesser.exists(catalogpublishFolder);
		} catch (IOException e) {
			ErrorHandler.showErrorAndQuit(commons, "Error in TesterContentRootAccessCheck", e);
		}

		System.out.println("TesterContentRootAccessCheck completed access result : " + checkFlag);
		Commons.logger.info("TesterContentRootAccessCheck completed access result : " + checkFlag);
	}

	public static void main(String[] args) throws IOException, ParseException {
		
		System.out.println("TesterContentRootAccessCheck starting up; printing directly via sysout");
		Commons commons = Commons.getInstance(Commons.BASE_CATALOG_SERVER);
		Commons.logger.info("TesterContentRootAccessCheck starting up at " + commons.getCurrentTimeStamp());

		TesterContentRootAccessCheck accessTester = new TesterContentRootAccessCheck(commons, "DemoGShContentRoot");
		accessTester.testAccess();		
	}
}