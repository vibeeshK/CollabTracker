package xtdSrvrComp;

import espot.ArtifactPojo;
import espot.CommonData;
import espot.ContentHandlerInterface;

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