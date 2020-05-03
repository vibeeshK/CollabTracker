package xtndHandlers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import clobTrk.ArtifactKeyPojo;
import clobTrk.ArtifactPojo;
import clobTrk.ArtifactPrepper;
import clobTrk.CommonData;
import clobTrk.ContentHandlerInterface;
import clobTrk.ContentHandlerManager;
import clobTrk.ERLDownload;
import clobTrk.ErrorHandler;
import clobTrk.SelfAuthoredArtifactpojo;
import contentHandlers.DeckerLite;
import contentHandlers.DeckerLiteContentTypeInterface;
import contentHandlers.DeckerLiteDocPojo;
import contentHandlers.DeckerLiteItemPojo;
import xtdCommonTechs.ExcelHandler;
import xtdSrvrComp.ExtendedHandler;
import xtdSrvrComp.XtdCommons;
import xtdSrvrComp.XtdStdProcessRecord;

/**
 * Handler for DeckrGroupr extended processing
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class XtdDeckrLite extends DeckerLite implements ExtendedHandler {

	final static int DECKER_KEY_COLUMN = 0;
	final static String COMBINED_FILE_SUBFIX = "_allCombined.xlsx";
	XtdCommons xtdCommons = null;
	public void initializeExtendedHandlerForExtdSrvrProcess(CommonData inCommonData, 
			ArtifactPojo inArtifactPojo) {
		System.out.println("At initializeExtendedHandlerForExtdSrvrProcess");
		initializeContentHandlerForExtdSrvrProcess(inCommonData, inArtifactPojo);		
		xtdCommons = (XtdCommons) inCommonData.getCommons();
	}

	@Override
	public void processItemDetail(ArtifactPojo inChildArtifactPojo) {
		System.out.println("at 2345432 processItemDetail = " + inChildArtifactPojo.artifactKeyPojo.artifactName);
		// this method is not relevant for DeckerLite		
		System.out.println("at 2345432 processItemDetail ended");
	}

	@Override
	public void processItemSummary(ArtifactPojo inChildArtifactPojo) {
		System.out.println("at 23454231 processItemSummary inChildArtifactPojo = " + inChildArtifactPojo.artifactKeyPojo.artifactName);

		DeckerLiteDocPojo deckerLiteDocPojo = (DeckerLiteDocPojo) primerDoc;
		DeckerLiteItemPojo deckerLiteItemPojo = 
					(DeckerLiteItemPojo) deckerLiteDocPojo.getItemByChildArtifactName(inChildArtifactPojo.artifactKeyPojo.relevance,
													inChildArtifactPojo.artifactKeyPojo.artifactName,
													inChildArtifactPojo.artifactKeyPojo.contentType);

		ContentHandlerInterface contentHandlerObjectInterface = null;
		contentHandlerObjectInterface = ContentHandlerManager.getInstance(commons, catelogPersistenceManager, inChildArtifactPojo.artifactKeyPojo.contentType);
		ArtifactKeyPojo childArtifactKeyPojo = new ArtifactKeyPojo(invokedArtifactPojo.artifactKeyPojo.rootNick,
																		deckerLiteItemPojo.relevance,
																		deckerLiteItemPojo.artifactName,
																		deckerLiteItemPojo.contentType);
		
		ERLDownload childERLDownload = catelogPersistenceManager.readERLDownLoad(childArtifactKeyPojo);		
		contentHandlerObjectInterface.initNonUIContentHandlerForDownloadedArtifact(commonData, childERLDownload);
		
		DeckerLiteContentTypeInterface deckableContentTypeInterface = (DeckerLiteContentTypeInterface) contentHandlerObjectInterface;

		String srcChildFilePath = deckableContentTypeInterface.getDetailFilePath();

		if (deckerLiteDocPojo.combinedFileName == null || deckerLiteDocPojo.combinedFileName .equalsIgnoreCase("")) {
			deckerLiteDocPojo.combinedFileName = invokedArtifactPojo.artifactKeyPojo.artifactName + COMBINED_FILE_SUBFIX;
		}
		System.out.println("at 23454231 processItemSummary DeckerLiteDocPojo.OVERALLSUMMARYFILENAME = " + deckerLiteDocPojo.combinedFileName);
		String targetOverallSummaryPath = commonData.getCommons().getAbsolutePathFromDirAndFileNm(contentPathFolderName,deckerLiteDocPojo.combinedFileName);

		System.out.println("at 23454231 processItemSummary targetItemDetailsPath = " + targetOverallSummaryPath);

		ExcelHandler excelHandler;
		try {
			excelHandler = new ExcelHandler(commons,srcChildFilePath,targetOverallSummaryPath);

			try {
				if (deckerLiteDocPojo.keyBasedCombining) {
					if(deckerLiteDocPojo.considerOnlyFromAuthor) {
						excelHandler.combineShByKeysAndConstraint(deckerLiteDocPojo.keyColHdrName,deckerLiteDocPojo.authorColHdrName, inChildArtifactPojo.author);
					} else {
						excelHandler.combineShByKeys(deckerLiteDocPojo.keyColHdrName);
					}
				} else if (deckerLiteDocPojo.considerOnlyFromAuthor) {
					excelHandler.combineShOnConstraint(deckerLiteDocPojo.authorColHdrName, inChildArtifactPojo.author);
				}
				
				deckerLiteItemPojo.numberOfRecsCombined = excelHandler.recsBroughtInto;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				ErrorHandler.showErrorAndQuit(commons, "Error1 in ExtdDeckrGroupr processItemSummary " + inChildArtifactPojo.artifactKeyPojo.artifactName, e);
			}
		} catch (IOException e) {
			ErrorHandler.showErrorAndQuit(commons, "Error2 in ExtdDeckrGroupr processItemSummary " + inChildArtifactPojo.artifactKeyPojo.artifactName, e);
		}
		deckerLiteItemPojo.deckingCompletedAt = commons.getDateTS(); // as there is no separate pro
		deckerLiteDocPojo.deckerEdited = false;
		writePrimer();		
		System.out.println("at 23454231 processItemSummary ended");
	}

	@Override
	public String processXtdStdProcessRec(String xtdProcStatus) {
		System.out.println("at start ExtdDeckrGroupr processxtdStdProcessRec inArtifactKeyPojo = " + invokedArtifactPojo.artifactKeyPojo.artifactName);
		System.out.println("at ExtdDeckrGroupr This process wont be called");		
		System.out.println("at end ExtdDeckrGroupr processxtdStdProcessRec inArtifactKeyPojo = " + invokedArtifactPojo.artifactKeyPojo.artifactName);
		return xtdProcStatus;		
	}

	@Override
	public String absorbInput(Object inInput, String inInstruction) {
		String processEndingStatus = XtdStdProcessRecord.ERLRecord_CONTINUE;
		System.out.println("at 23454233 This process wont be called");		
		return processEndingStatus;
	}

	private SelfAuthoredArtifactpojo setupDraftArtifact(ArtifactKeyPojo inArtifactKeyPojo){
		ArtifactPrepper artifactPrepper = new ArtifactPrepper(inArtifactKeyPojo, commonData);
		SelfAuthoredArtifactpojo extdSelfAuthoredArtifactpojo = artifactPrepper.createDraft();
		extdSelfAuthoredArtifactpojo.draftingState = SelfAuthoredArtifactpojo.ArtifactStatusDraft;
		catelogPersistenceManager.insertArtifactUI(extdSelfAuthoredArtifactpojo);
		return extdSelfAuthoredArtifactpojo;
	}
}