package contentHandlers;

import clobTrk.GenericGrouperDocPojo;
import clobTrk.ItemPojo;

/**
 * This content handler helps to deck up contents that implements decking interface
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class DeckerGrouper extends DeckerLite {

	protected void setScreenTitle() {
		mainShell.setText("DeckerGrouper: <viewContentsAtDesk>");
	}

	protected void setCenterBaseColHeaders() {
		centerBaseColHeaders = new String[] {
												RELEVANCEHDR,
												SUMMARYFILEHDR,
												DECKING_STATEHDR
											};
	}

	public Class getPrimerDocClass() {
		return DeckerGrouperDocPojo.class;
	}

	public ItemPojo createItemPojo(String inContentType, String inRelevance, String inArtifactName){
		return new DeckerGrouperItemPojo(inContentType, inRelevance, inArtifactName);
	}	
	
	public GenericGrouperDocPojo getNewPrimerDoc() {
		GenericGrouperDocPojo deckerGrouperDocPojo = new DeckerGrouperDocPojo();
		return deckerGrouperDocPojo;
	}

	public DeckerGrouperDocPojo getPrimerDoc() {		
		return (DeckerGrouperDocPojo) primerDoc;
	}
}