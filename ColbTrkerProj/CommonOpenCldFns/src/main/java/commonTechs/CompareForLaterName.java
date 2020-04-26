package commonTechs;


import java.util.Comparator;

/**
 * This class was mainly used for sorting the catalog files to get the latest.
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class CompareForLaterName implements Comparator<String> {
	public int compare(String URL1, String URL2) {
		if (URL1.compareToIgnoreCase(URL2) < 0) {
			return 1;
		} else {
			return -1;
		}
	}
}
