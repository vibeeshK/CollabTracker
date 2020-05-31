package colbTrk;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import commonTechs.TransferObject;

/**
 * Checks the syscomp refresh log and renews any outdated component.
 * Run only once till next shut down
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class SysCompRefresh {

	private static SysCompRefresh sysCompRefresh = null;

	Commons commons = null;
	RemoteAccesser platformRootRemoteAccesser = null;
	RootPojo sysRootPojo = null;
	UpdatesLogPojo updatesLogPojo = null;
	
	public static void compRefresh(Commons inCommons){
		if (sysCompRefresh == null) {
		// null checked to ensure to avoid redundant repetitions
			sysCompRefresh = new SysCompRefresh(inCommons);
		}
	}
	
	private SysCompRefresh(Commons inCommons) {
		commons = inCommons;
		System.out.println("inCommons = " + inCommons);
		System.out.println("commons = " + commons);
		
		if (commons.suppressSysCompRefresh) {
			System.out.println("SysCompRefresh skipped as  commons.suppressSysCompRefresh is set to " + commons.suppressSysCompRefresh);
			return;
		}

		HashMap<String,RootPojo> rootPojoMap = PublishedRootsHandler.getPublishedRoots(commons);

		System.out.println("commons.platformRoot is = " + commons.platformRoot);
		
		sysRootPojo = rootPojoMap.get(commons.platformRoot);

		System.out.println("sysRootPojo is = " + sysRootPojo);
		System.out.println("sysRootPojo.rootNick is = " + sysRootPojo.rootNick);
		
		platformRootRemoteAccesser = RemoteAccessManager.getInstance(commons, sysRootPojo.rootNick);
		String updateLogDocFileName = sysRootPojo.rootString + sysRootPojo.fileSeparator + commons.sysUpdateLogDoc;
		System.out.println("updateLogDocFileName = " + updateLogDocFileName);
		System.out.println("sysRootPojo.rootString = " + sysRootPojo.rootString);
		System.out.println("commons.sysUpdateLogDoc = " + commons.sysUpdateLogDoc);

		InputStream updateLogDocInputStream = platformRootRemoteAccesser.getRemoteFileStream(updateLogDocFileName);
		Document updateLogDoc = null;
		try {
			updateLogDoc = commons.getDocumentFromXMLFileStream(updateLogDocInputStream);		
			updateLogDocInputStream.close();
			updatesLogPojo = new UpdatesLogPojo(updateLogDoc, commons);
			while (updatesLogPojo.isNextTransferAvailable()) {
				TransferObject transferObject = updatesLogPojo.getNextTransfer();
				String remoteFileLocation = sysRootPojo.rootString + sysRootPojo.fileSeparator + (String) transferObject.sourceObj;
				System.out.println("sysRootPojo.rootString = " + sysRootPojo.rootString);
				System.out.println("(String) transferObject.sourceObj = " + (String) transferObject.sourceObj);
				System.out.println("remoteFileLocation = " + remoteFileLocation);
				String localFileLocation = commons.installFileFolder + commons.localFileSeparator + (String) transferObject.destinationObj;
				System.out.println("commons.localFileFolder = " + commons.installFileFolder);
				System.out.println("(String) transferObject.destinationObj = " + (String) transferObject.destinationObj);
				System.out.println("localFileLocation = " + localFileLocation);
				commons.archiveLocalFile(localFileLocation);
				platformRootRemoteAccesser.downloadFile(commons,sysRootPojo.rootString, remoteFileLocation, localFileLocation);
				Commons.logger.info(" Downloaded " + remoteFileLocation 
											+ " into " + localFileLocation
											+ " at " + commons.getCurrentTimeStamp());
			}
			if (updatesLogPojo.lastUpdateTm != null) {
				System.out.println("SysComp updates done");
				commons.setSysCompCurrLocalLogUpdateTm(commons.getTimeStamp(updatesLogPojo.lastUpdateTm));
			}
		} catch (IOException | SAXException | ParserConfigurationException e) {
			ErrorHandler.showErrorAndQuit(commons, "Error in SysCompRefresh", e);
		}		
	}
}