package colbTrk;

/**
 * Holds the key specifications of a content type
 *  
 * @author Vibeesh Kamalakannan
 *
 */
public class ContentTypePojo {

	public String contentType = null;
	public String template = null;
	public String extension = null;
	public boolean hasSpecialHandler = false;
	public String class_Name = null;
	public int rollupLevel = 0;
	public String artifactName = null;
	public String rollupContentType = null;
	
	public ContentTypePojo() {
	}

	public ContentTypePojo(String inContentType, String inTemplate, String inExtension) {
		System.out.println("ContentTypePojo starts");
		
		setContentTypePojo(inContentType, inTemplate, inExtension);
	}
	
	public void setContentTypePojo(String inContentType, String inTemplate, String inExtension) {
		
		System.out.println("setContentTypePojo starts inContentType=" + inContentType + " Template= " + inTemplate + " inExtension=" + inExtension);
		
		contentType = inContentType;
		template = inTemplate;
		extension = inExtension;
		
		System.out.println("5Oct_setContentTypePojo2");
		
	}
}
