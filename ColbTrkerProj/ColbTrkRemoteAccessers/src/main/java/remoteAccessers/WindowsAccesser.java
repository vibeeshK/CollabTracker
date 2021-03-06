package remoteAccessers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import colbTrk.AbstractRemoteAccesser;
import colbTrk.Commons;
import colbTrk.ErrorHandler;
import colbTrk.RootPojo;

/**
 * This class provides the means to access files on a Windows based Doc Centers
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class WindowsAccesser extends AbstractRemoteAccesser {

	public WindowsAccesser (){
	}

	public void initiateCommunications(RootPojo inRootPojo, Commons inCommons) {
		try {
			commonInit(inRootPojo,inCommons);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ErrorHandler.showErrorAndQuit(commons, "Error in WindowsAccesser intiateCommunications."
					+ " Check connection issues and port lockups. Root: " + inRootPojo.rootNick, e);
		}
		System.out.println("initiated windows File at getTransportHandler ");
	}

	public void putInStreamIntoRemoteLocation(String inNewContentRemoteLocation, InputStream inUpdatedContentByteArray) {
		try {
		    byte[] buffer = new byte[inUpdatedContentByteArray.available()];
		    inUpdatedContentByteArray.read(buffer);

		    File targetFile = new File(inNewContentRemoteLocation);
		    commons.createFolderOfFileIfDontExist(targetFile);
		    OutputStream outStream = new FileOutputStream(targetFile);
		    outStream.write(buffer);
		    outStream.close();
		    inUpdatedContentByteArray.close();
			System.out.println("end putInStreamIntoRemoteLocation");
			System.out.println("end putInStreamIntoRemoteLocation inUpdatedContentByteArray is closed for " + inUpdatedContentByteArray);

		} catch (IOException e) {
			ErrorHandler.showErrorAndQuit(commons, "error in WindowsAccesser putInStreamIntoRemoteLocation into " + inNewContentRemoteLocation, e);
		}
	}

	public void putByteArrayDataIntoRemoteLocation(String inNewContentRemoteLocation, byte[] inByteArrayData) {
	// This method is not used anymore anywhere!!!
		try {
		    File targetFile = new File(inNewContentRemoteLocation);
		    commons.createFolderOfFileIfDontExist(targetFile);
		    OutputStream outStream = new FileOutputStream(targetFile);
		    outStream.write(inByteArrayData);			
		    outStream.close();
			System.out.println("end putputByteArrayDataIntoRemoteLocation");
		} catch (IOException e) {
			ErrorHandler.showErrorAndQuit(commons, "error in WindowsAccesser putByteArrayDataIntoRemoteLocation into " + inNewContentRemoteLocation, e);
		}
	}
	
	public void moveToRemoteLocation(String inSourceFileRemoteLocation, String inDestinationFileRemoteLocation) {
		try {
			File sourceFile = new File(inSourceFileRemoteLocation);
			File destFile = new File(inDestinationFileRemoteLocation);
		    commons.createFolderOfFileIfDontExist(destFile);
			FileUtils.moveFile(sourceFile, destFile);
		} catch (IOException e) {
			ErrorHandler.showErrorAndQuit(commons, "error in WindowsAccesser moveToRemoteLocation from "  + inSourceFileRemoteLocation + " into " + inDestinationFileRemoteLocation, e);
		}
	}
	
	public ArrayList<String> getRemoteList(String inRemoteDropBox) {
		ArrayList<String> resourcesStringList = null;
		String[] resources = (new File(inRemoteDropBox)).list();
		System.out.println("inRemoteDropBox" + inRemoteDropBox);

		resourcesStringList = new ArrayList<String>();
		for (int i = 0; i < resources.length; i++) {
			System.out.println("resources received for i = " + i + " "
					+ resources[i]);
			resourcesStringList.add(resources[i]);
		}
		return resourcesStringList;
	}
	
	public void uploadToRemote(String inDestinationFileAtRemote, String inSourceFileAtLocal) {
		try {
			FileUtils.copyFile(new File(inSourceFileAtLocal), new File(inDestinationFileAtRemote));
		} catch (IOException e) {
			ErrorHandler.showErrorAndQuit(commons, "Error in WindowsAccesser uploadToRemote into " + inDestinationFileAtRemote + " from " + inSourceFileAtLocal, e);
		}
	}
	
	public InputStream getRemoteFileStream(String inRemoteFileName) {
		InputStream remoteFileStream = null;
		try {
			remoteFileStream = new FileInputStream(new File(inRemoteFileName));
			System.out.println("At getRemoteFileStream a new remoteFileStream created with " + remoteFileStream);
			System.out.println("At getRemoteFileStream a new remoteFileStream created for " + inRemoteFileName);
		} catch (FileNotFoundException e) {
			ErrorHandler.showErrorAndQuit(commons, "Error in WindowsAccesser getRemoteFileStream of " + inRemoteFileName, e);
		}
		return remoteFileStream;
	}
	/// additional interfaces

	@Override
	public void put(String inRemoteURL, byte[] inBytes) {
	    File targetFile = new File(inRemoteURL);
	    OutputStream outStream = null;
	    try {
		    commons.createFolderOfFileIfDontExist(targetFile);
	    	outStream = new FileOutputStream(targetFile);
			outStream.write(inBytes);
		    outStream.close();
		} catch (IOException e) {
			ErrorHandler.showErrorAndQuit(commons, "error in WindowsAccesser put " + inRemoteURL, e);
		}			
	}

	@Override
	public boolean exists(String inRemoteURL) {
		boolean urlExists = false;
			urlExists = (new File(inRemoteURL)).exists();
		return urlExists;
	}	

	public File getSystemCertFile(){
		File file = new File("jssecacerts");
		if (file.isFile() == false) {
			System.out.println("via aaa");
			char SEP = File.separatorChar;
			File dir = new File(System.getProperty("java.home") + SEP + "lib"
					+ SEP + "security");
			file = new File(dir, "jssecacerts");
			if (file.isFile() == false) {
				System.out.println("via bbb");
				file = new File(dir, "cacerts");
				if (file.isFile() == false) {
					System.out.println("via ccc");
				}
			}
		}
		System.out.println("orig file is = " + file);
		return file;
	}
}