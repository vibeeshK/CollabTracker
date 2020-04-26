package espot;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import commonTechs.OrchestrationData;

/**
 * Called by Serverside Orchestrator to periodically execute the requests processor 
 * for the given root
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class MasterRootProcessor implements Runnable {

	private CommonData commonData;			
	private Commons commons;			
	private RootPojo rootPojo;
	OrchestrationData orchestrationData;

	private RemoteAccesser remoteAccesser;
	
	private RequestProcessor requestProcessor;

	public MasterRootProcessor(RootPojo inRootPojo, OrchestrationData inOrchestrationData) throws IOException, ParseException {
		rootPojo = inRootPojo;
		Commons commons = Commons.getInstance(Commons.BASE_CATALOG_SERVER,rootPojo.rootNick);
		
		Commons.logger.info("MasterRootProcessor constructor starting up at " + commons.getCurrentTimeStamp());
		
		orchestrationData = inOrchestrationData;
		
		System.out.println("going to create the Sardine object1 for MasterRootProcessor");
		commonData = CommonData.getInstance(commons);			

		remoteAccesser = RemoteAccessManager.getInstance(commons, rootPojo.rootNick);

		System.out.println("created sardine");

		requestProcessor = new RequestProcessor(commonData, remoteAccesser);
			
		System.out.println("at 4");
	}

	public void run() {

		while (orchestrationData.getOkayToContinue()) {
			if (rootPojo.requiresInternet && !commons.isInternetAvailable()){
				Commons.logger.warn(" Internet umavailable, hence skipping MasterRootProcess for " + rootPojo.rootNick);	
				System.out.println(" Internet umavailable, hence skipping MasterRootProcess for " + rootPojo.rootNick);
				break;
			}
			
			try {
				System.out.println("inside masterRootProcessr for = "
						+ rootPojo.rootString);

				requestProcessor.processRequestsOfOneRoot();
			} catch (IOException | ClassNotFoundException | TransformerException | ParserConfigurationException e) {
				ErrorHandler.showErrorAndQuit(commons, "Error in MasterRootProcessor run ", e);
			}

			synchronized (this) {
				System.out.println("GONNA WAIT");
				try {
					System.out.println("goint to wait " + orchestrationData.getRepeatIntervalInSeconds() + " seconds");
					wait(orchestrationData.getRepeatIntervalInSeconds() * 1000);								
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("EXITING THE CURRENT LOOP1");
				}
				System.out.println("COMING OUT OF WAIT");
			}
			System.out.println(" orchestrationData okToContinue 21 is " + orchestrationData.getOkayToContinue());	
		}
		System.out.println(" orchestrationData okToContinue 22 is " + orchestrationData.getOkayToContinue());
	}
}