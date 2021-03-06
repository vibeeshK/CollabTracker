package osHandlers;
import java.io.IOException;

import colbTrk.OSHandler;
import commonTechs.CommonTechs;

/**
 * File opener on a windows machine
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class WindowsFileAccesser implements OSHandler {

	public void openFileToView(String inFileString) {
		String app_cmd = "rundll32 url.dll, FileProtocolHandler " + inFileString;
		System.out.println("command to be executed: " + app_cmd);
		try {
			System.out.println("000Trying to launch document");
		
			Runtime.getRuntime().exec(app_cmd);
		} catch (IOException e1) {
			e1.printStackTrace();
			CommonTechs.logger.error("Error in WindowsFileAccesser openFileToView " 
										+ " " + inFileString + " " + app_cmd, e1);
		}
	}
}
