package org.universaal.tools.owl2javatransformation.model;

public class OntologyEntity {	

	private final String comment;
	private final String simpleName;
	private final String uri;
	
	public OntologyEntity(String simpleName, String uri, String comment) {
		this.simpleName = simpleName;
		this.uri = uri;
		this.comment = comment;		
	}

	public String getComment() {
		return comment;
	}

	public String getName() {
		return simpleName;
	}

	public String getUri() {
		return uri;
	}
	
	public static final String uriToSimpleName(String uri) {
		return uri.split("#")[1].replace('-', '_');
	}
	
	public static final String dataTypeToSimpleName(String dataType) {
		String rangeString = OntologyEntity.uriToSimpleName(dataType);
		return rangeString.substring(0, 1).toUpperCase() + rangeString.substring(1);
	}
	
	public static final String TOP_MOST_CLASS_URI = "http://ontology.universAAL.org/uAAL.owl#ManagedIndividual";

}
