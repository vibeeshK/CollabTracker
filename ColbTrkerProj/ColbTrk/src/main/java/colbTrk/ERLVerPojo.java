package colbTrk;

/**
 * Convenience class for reflecting the version details of ERL
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ERLVerPojo {

	public String relevance;
	public String artifactName;
	public String versionedFileName;

	public ERLVerPojo(String inRelevance, String inArtifactName, String inVersionedFileName){
		relevance = inRelevance;
		artifactName = inArtifactName;
		versionedFileName = inVersionedFileName;		
	}
}