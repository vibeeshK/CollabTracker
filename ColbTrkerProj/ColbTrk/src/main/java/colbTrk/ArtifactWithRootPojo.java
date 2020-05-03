package colbTrk;

/**
 * Simplifier class
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ArtifactWithRootPojo {

	public SelfAuthoredArtifactpojo selfAuthoredArtifactspojo;
	public RootPojo rootPojo;

	public ArtifactWithRootPojo() {
	}

	public ArtifactWithRootPojo(
			SelfAuthoredArtifactpojo inSelfAuthoredArtifactspojo,
			RootPojo inRootPojo) {

		setSelfAuthoredArtifactspojo(inSelfAuthoredArtifactspojo, inRootPojo);
	}

	public void setSelfAuthoredArtifactspojo(
			SelfAuthoredArtifactpojo inSelfAuthoredArtifactspojo,
			RootPojo inRootPojo) {
		selfAuthoredArtifactspojo = inSelfAuthoredArtifactspojo;
		rootPojo = inRootPojo;
	}
}
