package colbTrk;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;

/**
 * This class helps absorbing the catalog download details into the application object
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class CatalogDownloadDtlsHandler {

	private final int mapRefreshGap = 5; // time seconds to refresh hashMap
	private Commons commons;
	private static CatalogDownloadDtlsHandler catalogDownloadsHandler = null;
	private CatlogDownlodedDtlDocPojo catlogDownlodedDtlDocPojo = null;
	private Calendar mapRefreshedTime = null;

	public static synchronized CatalogDownloadDtlsHandler getInstance(Commons inCommons){
		if (catalogDownloadsHandler == null) {
			catalogDownloadsHandler = new CatalogDownloadDtlsHandler(inCommons);
			System.out.println("commons1 : " + catalogDownloadsHandler.commons);
		} else {
			catalogDownloadsHandler.refreshCatalogDownloadDetailsMap();
		}
		System.out.println("commons2 : " + catalogDownloadsHandler.commons);

		return catalogDownloadsHandler;
	}

	private CatalogDownloadDtlsHandler(Commons inCommon){
		commons = inCommon;
		System.out.println("commons : " + commons);
		refreshCatalogDownloadDetailsMap();
	}
	
	public synchronized void refreshCatalogDownloadDetailsMap(){
		Commons.logger.info("CatalogDownloadDtlsHandler refreshCatalogDownloadDetailsMap start at " + commons.getCurrentTimeStamp());
		System.out.println("starting refreshCatalogDownloadDetailsMap mapRefreshedTime is " + mapRefreshedTime);
		System.out.println("starting refreshCatalogDownloadDetailsMap mapRefreshGap is " + mapRefreshGap);
		
		if (mapRefreshedTime != null && !commons.hasTimeSecElapsed(mapRefreshedTime.getTime(),mapRefreshGap)) {
			System.out.println("At refreshCatalogDownloadDetailsMap time too short to refresh for all already downloaded roots");
			return; // no need to refresh as its too short after previous refresh
		}
		try {
			System.out.println("commons : " + commons);
			System.out.println("commons.downloadedCatalogDetailsFile : " + commons.downloadedCatalogDetailsFile);
			catlogDownlodedDtlDocPojo = (CatlogDownlodedDtlDocPojo) commons.getJsonDocFromFile(commons.downloadedCatalogDetailsFile,CatlogDownlodedDtlDocPojo.class);
			if (catlogDownlodedDtlDocPojo == null) {
				catlogDownlodedDtlDocPojo = new CatlogDownlodedDtlDocPojo();
			}			
			if (catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap == null) {
				catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap = new HashMap<String,DownloadedCatlogRootDtlPojo>();					
			}
		} catch (IOException e) {
			ErrorHandler.showErrorAndQuit(commons, "Error in CatalogDownloadDtlsHandler getCatalogDownloadsDetailsDoc", e);
		}
		mapRefreshedTime = commons.getCalendarTS();
	}

	public synchronized String getCatalogDownLoadedFileName(String inRootNick){
	// This method reads for downloadedFileName and aborts if unavailable present
	// This method shall be called if there is subsequent process expects a downloaded file
		System.out.println("starting getCatalogDownLoadedFileName for inRootNick " + inRootNick);
		Commons.logger.info("CatalogDownloadDtlsHandler getCatalogDownLoadedFileName for inRootNick " + inRootNick);

		String catalogDownLoadedFileName = getCatalogDownLoadedFileNameIfAvailable(inRootNick);
		if (catalogDownLoadedFileName == null) {
			System.out.println("At end of getCatalogDownLoadedFileName inRootNick " + inRootNick + " is not downloaded yet");
			ErrorHandler.showErrorAndQuit(commons, "At end of getCatalogDownLoadedFileName inRootNick " + inRootNick + " is not downloaded yet");
		}
		return catalogDownLoadedFileName;
	}
	
	public synchronized String getCatalogDownLoadedFileNameIfAvailable(String inRootNick){
	// This method reads the downloadedFileName and returns null if there is none		
		System.out.println("starting getCatalogDownLoadedFileNameIfAvailable for inRootNick " + inRootNick);
		Commons.logger.info("CatalogDownloadDtlsHandler getCatalogDownLoadedFileNameIfAvailable. rootNick : " + inRootNick);
		refreshCatalogDownloadDetailsMap();
		String catalogDownLoadedFileName = null;
		if (catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap.containsKey(inRootNick)){
			catalogDownLoadedFileName = commons.getFullLocalPathFileNameOfDownloadedDbFile(inRootNick,
					catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap.get(inRootNick).downloadedFileName);
		}
		return catalogDownLoadedFileName;
	}

	public synchronized boolean isFreshDownLoadAllowed(String inRootNick){
		refreshCatalogDownloadDetailsMap();
		if (!catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap.containsKey(inRootNick)){
			System.out.println("Continue for new Catalog download as there was no prev download");
			return true;
		}
		try {
			System.out.println("prevCatalogDownloadedTme          : " + commons.getDateFromString(catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap.get(inRootNick).downloadedTime));
			System.out.println("commons.catalogDownloadTimeGapSec : " + commons.catalogDownloadTimeGapSec);

			if (commons.hasTimeSecElapsed(commons.getDateFromString(catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap.get(inRootNick).downloadedTime),commons.catalogDownloadTimeGapSec)) {
				System.out.println("Continue for new Catalog download");
				return true;
			}
		} catch (ParseException e) {
			ErrorHandler.showErrorAndQuit(commons, "Error in CatalogDownloadDtlsHandler isFreshDownLoadAllowed " + inRootNick, e);
		}
		System.out.println("Gap is too short for a new Catalog download");
		return false;		// Gap is too short for a new download
	}
		
	public synchronized void updateCatalogDownloadDetail(String inRootNick, String inDownloadedFileName, String inDownloadedTime) {
		System.out.println("at updateCatalogDownloadDetail for inRootNick " + inRootNick);
		System.out.println("at updateCatalogDownloadDetail for inDownloadedFileName " + inDownloadedFileName);
		Commons.logger.info("CatalogDownloadDtlsHandler updateCatalogDownloadDetail for inRootNick " + inRootNick + " at " + commons.getCurrentTimeStamp());

		try {
			DownloadedCatlogRootDtlPojo downloadedCatlogRootDtlPojo;
			
			if (catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap.containsKey(inRootNick)){
				downloadedCatlogRootDtlPojo = 
						catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap.get(inRootNick);
				downloadedCatlogRootDtlPojo.downloadedFileName = inDownloadedFileName;
				downloadedCatlogRootDtlPojo.downloadedTime = inDownloadedTime;
			} else {
				downloadedCatlogRootDtlPojo = new DownloadedCatlogRootDtlPojo(
														inDownloadedFileName,inDownloadedTime);
			}
			
			catlogDownlodedDtlDocPojo.catalogDownlodedRootPojoMap.put(inRootNick,downloadedCatlogRootDtlPojo);

			commons.putJsonDocToFile(commons.downloadedCatalogDetailsFile,catlogDownlodedDtlDocPojo);

			System.out.println("Updated commons.downloadedCatalogDetailsFile : " + commons.downloadedCatalogDetailsFile);
			System.out.println("Updated time : " + commons.getCurrentTimeStamp());

		} catch (IOException e) {
			ErrorHandler.showErrorAndQuit(commons, "Error in CatalogDownloadDtlsHandler updateCatalogDownloadDetail " + inRootNick + " " + inDownloadedFileName + " " + inDownloadedTime, e);
		}
	}
}