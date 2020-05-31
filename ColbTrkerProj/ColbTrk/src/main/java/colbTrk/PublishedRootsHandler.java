package colbTrk;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Holder of content from the published roots xml
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class PublishedRootsHandler {

	Commons commons = null;
	Document pubishedRootsDoc = null;
	HashMap<String, RootPojo> publishedRootsMap = null;
	int rootsTotal = 0;
	private static PublishedRootsHandler publishedRootsHandler = null;

	public static synchronized HashMap<String, RootPojo> getPublishedRoots(Commons inCommons) {
		System.out.println("@ getPublishedRoots ");

		if (publishedRootsHandler == null) {
			publishedRootsHandler = new PublishedRootsHandler(inCommons);
		}
		return publishedRootsHandler.publishedRootsMap;		
	}

	private PublishedRootsHandler(Commons inCommons) {
		commons = inCommons;
		try {
			System.out.println("@ PublishedRootsHandler commons.pulishedRootsFile = " + commons.publishedRootsFileName);
			pubishedRootsDoc = commons.getDocumentFromXMLFile(commons.publishedRootsFileName);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			ErrorHandler.showErrorAndQuit(commons, "Error in PublishedRootsHandler PublishedRootsHandler ", e);
		}

		rootsTotal = pubishedRootsDoc.getElementsByTagName("Root").getLength();
		System.out.println("@ PublishedRootsHandler rootsTotal = " + rootsTotal);

		publishedRootsMap = new HashMap<String,RootPojo>();

		for (int rootCount=0;rootCount < rootsTotal; rootCount++) {
			RootPojo rootPojo = new RootPojo();			

			/* The below are attributes in published roots xml
			RootNick="SysController"
			RootType="System"
			RootString="D:\Kannan\Java\ESPoT\Controller" 
			RemoteAccesserType="remoteAccessers.WindowsAccesser"
			FileSeparator
			RequiresInternet="Yes"
			 */

			//rootPojo.rootNick = ((Element) pubishedRootsDoc.getElementsByTagName("Root").item(rootCount)).getAttribute("RootNick");
			//rootPojo.rootType = ((Element) pubishedRootsDoc.getElementsByTagName("Root").item(rootCount)).getAttribute("RootType");
			//rootPojo.rootString = ((Element) pubishedRootsDoc.getElementsByTagName("Root").item(rootCount)).getAttribute("RootString");
			//rootPojo.remoteAccesserType = ((Element) pubishedRootsDoc.getElementsByTagName("Root").item(rootCount)).getAttribute("RemoteAccesserType");
			//rootPojo.fileSeparator = ((Element) pubishedRootsDoc.getElementsByTagName("Root").item(rootCount)).getAttribute("FileSeparator");
			//String requiresInternetAsTx = ((Element) pubishedRootsDoc.getElementsByTagName("Root").item(rootCount)).getAttribute("RequiresInternet");

			
			Element rootElement = ((Element) pubishedRootsDoc.getElementsByTagName("Root").item(rootCount));
			
			rootPojo.rootNick = rootElement.getAttribute("RootNick");
			rootPojo.rootType = rootElement.getAttribute("RootType");
			rootPojo.rootString = rootElement.getAttribute("RootString");
			rootPojo.remoteAccesserType = rootElement.getAttribute("RemoteAccesserType");
			rootPojo.fileSeparator = rootElement.getAttribute("FileSeparator");

			String requiresInternetAsTx = rootElement.getAttribute("RequiresInternet");
			if (requiresInternetAsTx.equalsIgnoreCase("Yes")) {
				rootPojo.requiresInternet = true;
			}

			String cachingFolderPathsRecommendedAsTx = rootElement.getAttribute("CachingFolderPathsRecommended");
			if (cachingFolderPathsRecommendedAsTx.equalsIgnoreCase("Yes")) {
				rootPojo.cachingFolderPathsRecommended = true;
			}
			
			System.out.println("@ PublishedRootsHandler Loop rootCount = " + rootCount);
			System.out.println("@ PublishedRootsHandler rootPojo.rootNick = " + rootPojo.rootNick);
			System.out.println("@ PublishedRootsHandler rootPojo.rootString = " + rootPojo.rootString);
			System.out.println("@ PublishedRootsHandler rootPojo.cachingFolderPathsRecommended = " + rootPojo.cachingFolderPathsRecommended);
			
			if (commons.isWebURI(rootPojo.rootString)){
				rootPojo.rootPrefix = commons.getHostName(rootPojo.rootString);
			} else {
				rootPojo.rootPrefix = rootPojo.rootString;
			}
			System.out.println("@ PublishedRootsHandler rootPojo.rootPrefix = " + rootPojo.rootPrefix);
			
			publishedRootsMap.put(rootPojo.rootNick,rootPojo);
			System.out.println("@ Added rootPojo.rootNick = " + rootPojo.rootNick);
			System.out.println("@ Added rootPojo.rootString = " + rootPojo.rootString);
			System.out.println("@ Added rootPojo.fileSeparator = " + rootPojo.fileSeparator);
			System.out.println("@ Added rootPojo.rootPrefix = " + rootPojo.rootPrefix);
			Commons.logger.info("PublishedRootsHandler rootCount " + rootCount + "; rootPojo.rootPrefix is " + rootPojo.rootPrefix);			
			Commons.logger.info("PublishedRootsHandler rootPojo.rootString is " + rootPojo.rootString);	
		}
	}
}