package xtdSrvrComp;

import clobTrk.ArtifactPojo;
import clobTrk.CommonData;
import clobTrk.ContentHandlerInterface;

/**
 * Interface mandate for extended processes
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public interface ExtendedHandler extends ContentHandlerInterface {

	public void initializeExtendedHandlerForExtdSrvrProcess(CommonData inCommonData, 
			ArtifactPojo inXtdArtifactpojo);
	public void processItemDetail(ArtifactPojo inChildArtifactPojo);
	public void processItemSummary(ArtifactPojo inChildArtifactPojo);
	public String processXtdStdProcessRec(String xtdProcStatus);
	public String absorbInput(Object inData,String inInstruction);
}