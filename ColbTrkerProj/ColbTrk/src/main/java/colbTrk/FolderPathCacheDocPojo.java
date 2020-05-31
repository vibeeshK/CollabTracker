package colbTrk;

import java.util.HashMap;

/**
 * Holds the folder paths' IDs in a Doc Central. Folder path traversing takes
 * a lot of time to go through each hop and slows down the orchestrators. Hence
 * this caching mechanism introduced to improve speed.
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class FolderPathCacheDocPojo {

	public HashMap<String, String> foldersCacheMap;

	public void addPath(String inFolderPath, String inFolderID){
		if (foldersCacheMap == null) {
			setFoldersCacheMap();
		}
		System.out.println("At addPath any existing cache value for foldersCacheMap for " 
								+ inFolderPath + " is " + foldersCacheMap.get(inFolderPath));
		System.out.println("At addPath new value coming for " 
				+ inFolderPath + " is " + inFolderID);
		
		foldersCacheMap.put(inFolderPath, inFolderID);
	}

	public String getPath(String inFolderPath){
		
		System.out.println("At FolderPathCacheDocPojo getPath for inFolderPath " + inFolderPath); 
		System.out.println("At FolderPathCacheDocPojo getPath foldersCacheMap " + foldersCacheMap); 
		
		String folderID = null;
		if (foldersCacheMap != null) {
			folderID = foldersCacheMap.get(inFolderPath);
		} else {
			setFoldersCacheMap();
		}
		
		System.out.println("At FolderPathCacheDocPojo getPath got folderID " + folderID); 
		
		return folderID;
	}
	
	private void setFoldersCacheMap(){
		foldersCacheMap = new HashMap<String, String>();		
	}
}