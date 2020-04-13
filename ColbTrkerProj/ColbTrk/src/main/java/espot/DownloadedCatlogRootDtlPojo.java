package espot;

public class DownloadedCatlogRootDtlPojo {
	/*
	 * Convenience object to hold root details in the catalogDownloadDetail file
	 */
	public String downloadedFileName;
	public String downloadedTime;
	DownloadedCatlogRootDtlPojo(){
	}
	DownloadedCatlogRootDtlPojo(String inDownloadedFileName,String inDownloadedTime){
		downloadedFileName = inDownloadedFileName;
		downloadedTime = inDownloadedTime;
	}
}