package xtdSrvrComp;

import java.sql.SQLException;

import clobTrk.CatelogPersistenceManager;
import clobTrk.ErrorHandler;
import clobTrk.RootPojo;

/**
 * Maintains connections and SQLs to the specified db files for extended processes
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public abstract class XtdCatalogPersistenceManager extends CatelogPersistenceManager {

	String extdSrvrDBAlias;
	String extdSrvrDBAliasPrefix;

	public XtdCatalogPersistenceManager(RootPojo inRootPojo, XtdCommons inCommons, int inProcessMode)
			throws ClassNotFoundException {
		super(inRootPojo, inCommons, inProcessMode);
		
		System.out.println("XtdCatelogPersistenceManager begins for inMasterOrClient = " + processMode);

		extdSrvrDBAlias = "extdSrvrDBAlias";
		extdSrvrDBAliasPrefix = extdSrvrDBAlias + ".";

		createConnectionAndStatmentForXtdServer();
	}

	protected void createConnectionAndStatmentForXtdServer() {
		connectToExtdSrvrDb();
	}

	public void connectToExtdSrvrDb(){
		try {
			System.out.println("Im here connectToExtdSrvrDb");
			System.out.println("getExtededCatalogDbFileOfRoot: " + ((XtdCommons) commons).getExtededCatalogDbFileOfRoot(rootPojo.rootNick));
			
			String queryString = "Attach database '"
						+ ((XtdCommons) commons).getExtededCatalogDbFileOfRoot(rootPojo.rootNick)
						+ "' As " + extdSrvrDBAlias;

			System.out.println(queryString);

			statement.execute(queryString);
				
		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			ErrorHandler.showErrorAndQuit(commons, "Error XtdCatalogPersistenceManager connectToExtdSrvrDb", e);
		}
		return;
	}
}