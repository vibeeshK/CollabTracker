package contentHandlers;

import colbTrk.ItemPojo;

/**
 * Data holder for a toDo item content
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ToDoPojo extends ItemPojo{

	String cloneFromArtifactName;
	String cloneFromRelevance;
	String cloneFromContentType;
	String attachment;

	ToDoPojo(int inItemNumber){
		super (inItemNumber);
		initializeAdditionalItemPojoFields();
	}
	
	void initializeAdditionalItemPojoFields(){
		cloneFromArtifactName="";
		cloneFromRelevance="";
		cloneFromContentType="";
		attachment="";
	}

	public String getCloneFromArtifactName() {
		return cloneFromArtifactName;
	}

	public void setCloneFromArtifactName(String cloneFromArtifactName) {
		this.cloneFromArtifactName = cloneFromArtifactName;
	}

	public String getCloneFromRelevance() {
		return cloneFromRelevance;
	}

	public void setCloneFromRelevance(String cloneFromRelevance) {
		this.cloneFromRelevance = cloneFromRelevance;
	}

	public String getCloneFromContentType() {
		return cloneFromContentType;
	}

	public void setCloneFromContentType(String cloneFromContentType) {
		this.cloneFromContentType = cloneFromContentType;
	}
}