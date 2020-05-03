package xtdSrvrComp;

import java.io.IOException;
import java.text.ParseException;

import clobTrk.CommonUIData;
import clobTrk.Commons;
import clobTrk.ErrorHandler;
import clobTrk.RootPojo;

/**
 * CAUTION. EXECUTING THIS CLASS WILL CAUSE PERMANENT DATA LOSS IN EXTENDED DBs
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class NeverInvokeMe_DeleteAllXtdDbRecs {

	final static String ARG_XtdCtlgDeckerSrvrPropFileName = Commons.CONFIGFOLDERPREFIX + "extdCtlgSrvrForExtendedDecker.properties";
	final static String ARG_XtdCtlgSrvrForDeckerLitePropFileName = Commons.CONFIGFOLDERPREFIX + "extdCtlgSrvrForDeckerLite.properties";
	final static String ARG_XtdCtlgSrvrPropFileName = Commons.CONFIGFOLDERPREFIX + "extdCtlgSrvrForStdProcessor.properties";
	
	public NeverInvokeMe_DeleteAllXtdDbRecs() {
	}
	public void clearXtdSrvrDbRecs() throws IOException, ParseException {

		Commons commons = Commons.getInstance(Commons.CLIENT_MACHINE); // bare minimal commons

		Commons.logger.info("At NeverInvokeMe_DeleteAllXtdDbRecs clearXtdSrvrDbRecs start at " + commons.getCurrentTimeStamp()); 

		CommonUIData commonUIData = CommonUIData.getUIInstance(commons);

		RootPojo rootPojo = commonUIData.getCurrentRootPojo();
		
		XtdCommons xtdCommons = XtdCommons.getInstance(Commons.EXTENDED_CATALOG_SERVER, rootPojo.rootNick, ARG_XtdCtlgSrvrPropFileName);
		XtdTmShCatlogPersistenceMgr xtdCatalogPersistenceMgr = null;
		try {
			xtdCatalogPersistenceMgr = new XtdTmShCatlogPersistenceMgr(rootPojo,xtdCommons,Commons.EXTENDED_CATALOG_SERVER);
		} catch (ClassNotFoundException e) {
			ErrorHandler.showErrorAndQuit(xtdCommons, "Error NeverInvokeMe_DeleteAllXtdDbRecs clearXtdSrvrDbRecs while creating XtdTmShCatlogPersistenceMgr ", e);
		}
		xtdCatalogPersistenceMgr.neverCallMe_DeleteXtdStdProcessTbl();
		xtdCatalogPersistenceMgr.neverCallMe_DeleteTimeDetail();
		xtdCatalogPersistenceMgr.neverCallMe_DeleteAllSelfAuthoredArtifacts();
		xtdCatalogPersistenceMgr.neverCallMe_DeleteSubscriptions();		

		XtdCommons.extendedCatalogServerCommonsInstance = null; // flush cache

		xtdCommons = XtdCommons.getInstance(Commons.EXTENDED_CATALOG_SERVER, rootPojo.rootNick, ARG_XtdCtlgDeckerSrvrPropFileName);
		XtdDeckerProcCatlogPersistenceManager xtdDeckerCatalogPersistenceMgr = null;
		try {
			xtdDeckerCatalogPersistenceMgr = new XtdDeckerProcCatlogPersistenceManager(rootPojo, xtdCommons,
												Commons.EXTENDED_CATALOG_SERVER);
		} catch (ClassNotFoundException e) {
			ErrorHandler.showErrorAndQuit(xtdCommons, "Error NeverInvokeMe_DeleteAllXtdDbRecs clearXtdSrvrDbRecs while creating XtdDeckerProcCatlogPersistenceManager ", e);
		}
		xtdDeckerCatalogPersistenceMgr.neverCallMe_DeleteXtdDeckerProcessParentTbl();
		xtdDeckerCatalogPersistenceMgr.neverCallMe_DeleteXtdDeckerProcessChildTbl();
		xtdDeckerCatalogPersistenceMgr.neverCallMe_DeleteAllSelfAuthoredArtifacts();
		xtdDeckerCatalogPersistenceMgr.neverCallMe_DeleteSubscriptions();		

	
		XtdCommons.extendedCatalogServerCommonsInstance = null; // flush cache

		xtdCommons = XtdCommons.getInstance(Commons.EXTENDED_CATALOG_SERVER, rootPojo.rootNick, ARG_XtdCtlgSrvrForDeckerLitePropFileName);
		XtdDeckerProcCatlogPersistenceManager xtdDeckerLiteCatalogPersistenceMgr = null;
		try {
			xtdDeckerLiteCatalogPersistenceMgr = new XtdDeckerProcCatlogPersistenceManager(rootPojo, xtdCommons,
												Commons.EXTENDED_CATALOG_SERVER);
		} catch (ClassNotFoundException e) {
			ErrorHandler.showErrorAndQuit(xtdCommons, "Error NeverInvokeMe_DeleteAllXtdDbRecs clearXtdSrvrDbRecs while creating XtdDeckerProcCatlogPersistenceManager ", e);
		}
		xtdDeckerLiteCatalogPersistenceMgr.neverCallMe_DeleteXtdDeckerProcessParentTbl();
		xtdDeckerLiteCatalogPersistenceMgr.neverCallMe_DeleteXtdDeckerProcessChildTbl();
		xtdDeckerLiteCatalogPersistenceMgr.neverCallMe_DeleteAllSelfAuthoredArtifacts();
		xtdDeckerLiteCatalogPersistenceMgr.neverCallMe_DeleteSubscriptions();		
	}

	public static void main(String[] args) throws IOException, ParseException {
		NeverInvokeMe_DeleteAllXtdDbRecs neverInvokeMe_DeleteAll_XtdSrvr_DbRecs = new NeverInvokeMe_DeleteAllXtdDbRecs();
		neverInvokeMe_DeleteAll_XtdSrvr_DbRecs.clearXtdSrvrDbRecs();
	}
}