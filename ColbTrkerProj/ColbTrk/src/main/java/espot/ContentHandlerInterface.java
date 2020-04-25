package espot;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This interface describes the base functions each content handler shall adhere to
 *  
 * @author Vibeesh Kamalakannan
 *
 */
public interface ContentHandlerInterface {

	public String prevalidate(CommonData inCommonData,ArtifactKeyPojo inArtifactKeyPojo);
	public void initializeContentHandlerWithMinimumSetup(CommonData inCommonData);
	public void createNewStartupPrimer(String inNewPrimerFilePath, ArtifactPojo inArtifactspojo);
	public void initializeContentHandlerForDraftArtifact(CommonUIData inCommonUIData, SelfAuthoredArtifactpojo inSelfAuthoredArtifactspojo);
	public void initializeContentHandlerForDownloadedArtifact(CommonUIData inCommonUIData, ERLDownload inERLDownload);
	public void initializeContentHandlerForERLDownloadAndItemFocus(CommonUIData inCommonUIData,ERLDownload inERLDownload, ItemPojo inItemPojo);	
	public boolean initializeContentHandlerForStandaloneReader(CommonUIData inCommonUIData,String inFilePath,String inContentType);
	public void initNonUIContentHandlerForDownloadedArtifact(CommonData inCommonData, ERLDownload inERLDownload);
	public void initNonUIContentHandlerForDraftArtifact(CommonData inCommonData, SelfAuthoredArtifactpojo inSelfAuthoredArtifactspojo);	
	public ItemPojo getUptoDateERLItem(ItemPojo inItemPojo);
	public ItemPojo getERLItemByChildArtifactName(String inChildRelevance, String inChildArtifactName, String inChildContentType);
	public byte[] getBaseItemDocBytes(ItemPojo inItemPojo);
	public void initializeContentHandlerForExtdSrvrProcess(CommonData inCommonData, ArtifactPojo inArtifactPojo);
	public void editContentAtDesk() throws IOException;
	public void viewContentsAtDesk() throws IOException;
	public String validateBeforeUIEdit();
	public void processContentAtWeb(RootPojo inRootPojo, RemoteAccesser inRemoteAccesser, RequestProcesserPojo inRequestProcesserPojo);
	public ArrayList<ArtifactKeyPojo> extractAssociatedArtifactKeys();
	public int getTriggerInterval();
	public void triggeredProcess(String inTriggerAt);
	public ItemPojo getFocusedItemPojo();
	public void writePrimer();	
}