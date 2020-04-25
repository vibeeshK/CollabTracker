package contentHandlers;

/**
 * Interface requirements which all Deckable content types shall adhere to
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public interface DeckableContentTypeInterface extends DeckerLiteContentTypeInterface {

	public String getDetailSheetName();
	public String getSummarySheetName();
	public String getSummaryShKeyColumnHdr();
	public String getSummaryShKeyColumnVal();
	public int getSummaryShKeyColSeqNum();
}