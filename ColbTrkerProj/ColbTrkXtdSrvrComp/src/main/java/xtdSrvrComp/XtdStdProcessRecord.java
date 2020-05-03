package xtdSrvrComp;

import colbTrk.ArtifactKeyPojo;

/**
 * Db record holder for standard processing to track status
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class XtdStdProcessRecord {

	public static final String ERLRecord_NEW = "NEW";
	public static final String ERLRecord_UPDATED = "RENEWED";
	public static final String ERLRecord_PROCESSED = "PROCESSED";
	public static final String ERLRecord_CONTINUE = "CONTINUE";
	public static final String ERLRecord_ERROR = "ERROR";
	public static final String ERLRecord_DISCONTINUE = "DISCONTINUE";
	public static final String ERLRecord_SKIP = "SKIP";
	
	public String parentUpdateTimeStamp = "";
	public String parentStatus = "";
	public ArtifactKeyPojo artifactKeyPojo = null;
	
	public XtdStdProcessRecord(
			ArtifactKeyPojo inArtifactKeyPojo,
			String inParentUpdateTimeStamp,
			String inParentStatus
			) {
		artifactKeyPojo = inArtifactKeyPojo;
		parentUpdateTimeStamp = inParentUpdateTimeStamp;
		parentStatus = inParentStatus;
	}
}