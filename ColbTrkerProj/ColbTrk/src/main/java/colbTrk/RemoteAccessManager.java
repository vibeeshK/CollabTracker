package colbTrk;

import java.io.IOException;
import java.util.HashMap;

import commonTechs.CustomClassLoader;

/**
 * This is a singleton class that dynamically loads remote accesser classes and provides mapping
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class RemoteAccessManager {

	private static RemoteAccessManager remoteAccessManager = null;
	private Commons commons = null;
	private HashMap<String,RemoteAccesser> transportHandlerMap = null;
	private HashMap<String,RootPojo> rootPojoMap = null;
	private RemoteAccessManager(Commons inCommons){
		transportHandlerMap = new HashMap<String,RemoteAccesser>();
		commons = inCommons;
		rootPojoMap = PublishedRootsHandler.getPublishedRoots(commons);
	}
	
	public static RemoteAccesser getInstance(Commons inCommons, String inRootText){
		if (remoteAccessManager == null) {
			remoteAccessManager = new RemoteAccessManager(inCommons);
		}
		return remoteAccessManager.getRemoteAccesser(inRootText);
	}

	private RemoteAccesser getRemoteAccesser(String inRootText) {
		RemoteAccesser remoteAccesser = null;
		if (!remoteAccessManager.transportHandlerMap.containsKey(inRootText)){
			System.out.println("getRemoteAccesser inRootText = " + inRootText);
			
			RootPojo rootPojo = rootPojoMap.get(inRootText);
			
			System.out.println("rootString = " + rootPojo.rootString);
			System.out.println("rootStructure = " + rootPojo);
			System.out.println("rootPojo remoteAccesserType " + rootPojo.remoteAccesserType);
			try {
				remoteAccesser = (RemoteAccesser) CustomClassLoader.getInstance(rootPojo.remoteAccesserType,commons.getHandlerJarNames());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e) {
				ErrorHandler.showErrorAndQuit(commons, "Error in AccessManager main getRemoteAccesser " + inRootText, e);
			}
			remoteAccessManager.transportHandlerMap.put(rootPojo.rootNick,remoteAccesser);
			remoteAccesser.intiateCommunications(rootPojo,commons);

			Commons.logger.info("RemoteAccessManager getRemoteAccesser set for inRootText " + inRootText);
		}
		remoteAccesser = remoteAccessManager.transportHandlerMap.get(inRootText);
		return remoteAccesser;
	}
}