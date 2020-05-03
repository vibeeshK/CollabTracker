package clobTrk;

/**
 * Convenience object to hold root details in the catalogDownloadDetail file
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class DownloadedCatlogRootDtlPojo {

	public String downloadedFileName;
	public String downloadedTime;
	DownloadedCatlogRootDtlPojo(){
	}
	DownloadedCatlogRootDtlPojo(String inDownloadedFileName,String inDownloadedTime){
		downloadedFileName = inDownloadedFileName;
		downloadedTime = inDownloadedTime;
	}
}