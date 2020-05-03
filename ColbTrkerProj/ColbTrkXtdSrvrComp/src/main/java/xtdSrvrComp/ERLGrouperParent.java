package xtdSrvrComp;

import java.util.ArrayList;

import clobTrk.ArtifactKeyPojo;

/**
 * Db record holder for Decking grouper Parent to track status
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ERLGrouperParent {

	public static final String PARENT_NEW = "NEW";
	public static final String PARENT_UPDATED = "RENEWED";
	public static final String PARENT_PROCESSED = "PROCESSED";
	
	public int childTotal = 0;
	public String parentUpdateTimeStamp = "";
	public String parentStatus = "";
	public ArrayList<ExtendedChildPojo> extendedChildList = null;
	public ArtifactKeyPojo artifactKeyPojo = null;
	
	public ERLGrouperParent(
			ArtifactKeyPojo inArtifactKeyPojo,
			int inChildTotal,
			String inParentUpdateTimeStamp,
			String inParentStatus
			) {
		artifactKeyPojo = inArtifactKeyPojo;
		parentUpdateTimeStamp = inParentUpdateTimeStamp;
		parentStatus = inParentStatus;
		childTotal = inChildTotal;
	}
	
	public void addChild(ExtendedChildPojo inExtendedChildPojo) {
		if (extendedChildList == null) {
			extendedChildList = new ArrayList<ExtendedChildPojo>();
		}
		extendedChildList.add(inExtendedChildPojo);		
	}
}