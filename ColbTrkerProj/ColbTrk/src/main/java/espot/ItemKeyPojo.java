package espot;

/**
 * Convenience class for holding item keys (items get grouped to the corresponding rollup classes)
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ItemKeyPojo {

	public String itemName;
	public ArtifactKeyPojo artifactKeyPojo;
	public ItemKeyPojo(ArtifactKeyPojo inArtifactKeyPojo, String inItemName) {
		artifactKeyPojo = inArtifactKeyPojo;
		itemName = inItemName;
	}
}