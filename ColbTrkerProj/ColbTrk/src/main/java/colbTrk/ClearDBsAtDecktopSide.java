package colbTrk;

import java.io.IOException;
import java.text.ParseException;

/**
 * Deletes all records for a fresh start
 * CAUTION: YOU WILL LOOSE ALL DATA IF YOU INVOKE THIS CLASS.
 * 			DONT INVOKE BEFORE BACKING UP CURRENT DATA
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ClearDBsAtDecktopSide {

	CommonUIData commonUIData;
	CatelogPersistenceManager catelogPersistenceManager;

	public ClearDBsAtDecktopSide() {
	}
	public void clearClientDBs() throws IOException, ParseException{
		Commons commons = Commons.getInstance(Commons.CLIENT_MACHINE);
		CommonUIData commonUIData = CommonUIData.getUIInstance(commons);
		Commons.logger.info("ClearDBsAtDecktopSide clearClientDBs start " + commons.getCurrentTimeStamp());
		
		catelogPersistenceManager = commonUIData.getCatelogPersistenceManager();
		
		catelogPersistenceManager.neverCallMe_DeleteAllSelfAuthoredArtifacts();
		//catelogPersistenceManager.neverCallMe_DeleteERLs(); **ERLs are at server side
		catelogPersistenceManager.neverCallMe_DeleteSubscriptions();
		catelogPersistenceManager.neverCallMe_DeleteAllReviews();		
		catelogPersistenceManager.neverCallMe_DeleteAllTriggers();		

	}

	public void clearServerDBs() throws IOException, ParseException{
		Commons commons = Commons.getInstance(Commons.BASE_CATALOG_SERVER);
		CommonData commonData = CommonData.getInstance(commons);

		Commons.logger.info("ClearDBsAtDecktopSide clearServerDBs start " + commons.getCurrentTimeStamp());

		catelogPersistenceManager = commonData.getCatelogPersistenceManager();
		catelogPersistenceManager.neverCallMe_DeleteERLs();		
		RootPojo rootPojo = commonData.getCurrentRootPojo();
		
		String catalogpublishFile = rootPojo.rootString
				+ rootPojo.fileSeparator
				+ commons.getServerSideSideCatalogDbPublishFolderOfRoot(rootPojo.fileSeparator)
				+ rootPojo.fileSeparator
				+ commons.getNewCatalogDbPublishFileName(rootPojo.rootNick);
		System.out.println("catalogpublishFile = " + catalogpublishFile);		
		RemoteAccesser remoteAccesser = RemoteAccessManager.getInstance(commons, rootPojo.rootNick);
		
		remoteAccesser.uploadToRemote(catalogpublishFile, commons.getServersMasterCopyofCatalogDbLocalFileOfRoot(rootPojo.rootNick));
	}

	public static void main(String[] args) throws IOException, ParseException {
		//System.out.println("test test test");
		
		ClearDBsAtDecktopSide clearDBsAtDecktopSide = new ClearDBsAtDecktopSide();

		clearDBsAtDecktopSide.clearClientDBs();
		clearDBsAtDecktopSide.clearServerDBs();
	}
}