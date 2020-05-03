package xtdSrvrComp;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import clobTrk.Commons;
import clobTrk.ErrorHandler;
import clobTrk.RootPojo;

/**
 * This processor invokes extended Decking sequence at defined intervals
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class XtdDeckerProcOrchestrator extends AbstractRtCtOrchestrator {

	final static int ProcessInterval = 1 * 5 * 1000;	// default interval as 5 Sec
	final static String ARG_XtdCtlgDeckerSrvrPropFileName = Commons.CONFIGFOLDERPREFIX + "extdCtlgSrvrForExtendedDecker.properties";

	public XtdDeckerProcOrchestrator() throws IOException, ParseException {
		super(Commons.EXTENDED_CATALOG_SERVER,ARG_XtdCtlgDeckerSrvrPropFileName);
	}
	
	public HashMap<String, XtdStdContentProcMaster> getProcessorsMap(){
		return new HashMap<String, XtdStdContentProcMaster>();
	}
	
	public boolean isThisRootToBeProcessed(String inRootNick){		
		return true; //the rootNick check done within abstract parent holds good;
	}
	
	public boolean isThisContentTypeToBeProcessed(String inContentType){
		return true; //the contentType check done within abstract parent holds good;		
	}
	
	public XtdStdContentProcMaster getProcesor(RootPojo inRootPojo, String inProcessingContentType) {
		XtdCatalogPersistenceManager xtdCatalogPersistenceMgr = null;
		System.out.println("AbstractRtCtOrchestrator getProcesor commons is " + initialCommons);

		XtdCommons xtdCommon = null;
		try {
			xtdCommon = XtdCommons.getInstance(Commons.EXTENDED_CATALOG_SERVER, inRootPojo.rootNick, ARG_XtdCtlgDeckerSrvrPropFileName);
			xtdCatalogPersistenceMgr = new XtdDeckerProcCatlogPersistenceManager(inRootPojo, xtdCommon,
												Commons.EXTENDED_CATALOG_SERVER);
		} catch (ClassNotFoundException | IOException | ParseException e) {
			ErrorHandler.showErrorAndQuit(initialCommons, "Error XtdDeckerProcOrchestrator getProcesor " + inRootPojo.rootNick + " " + inProcessingContentType, e);
		}
		System.out.println("XtdDeckerProcOrchestrator before return getProcesor commons is " + xtdCommon);
		System.out.println("XtdDeckerProcOrchestrator before return getProcesor inRootPojo.rootNick " + inRootPojo.rootNick);
		System.out.println("XtdDeckerProcOrchestrator before return getProcesor inProcessingContentType " + inProcessingContentType);		
		return new XtdDeckerProcMaster(xtdCommon, inRootPojo,inProcessingContentType, xtdCatalogPersistenceMgr);
	}
	
	public int getSleepInterval() {
		return ProcessInterval;
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		XtdDeckerProcOrchestrator colbTrkExtdCatlgSrvrOrchestrator = new XtdDeckerProcOrchestrator();
		colbTrkExtdCatlgSrvrOrchestrator.orchestration();		
	}
}