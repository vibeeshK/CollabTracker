package colbTrk;

/**
 * Interface mandates for the primer doc consumer.
 * Primer doc is the meta data holder of the artifact packaged in a zip file
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public interface PrimerDocInterface {

	void absorbIncomingItemPojo (ItemPojo inItemPojo);
}