package org.universaal.tools.externalserviceintegrator.ontology;

import org.universAAL.ri.wsdlToolkit.ioApi.NativeObject;

public class OntologyMapping implements Comparable<OntologyMapping>{

	private String ontologyFileName;
	private String ontologyURI;
	private NativeObject nativeObject;
	private Double score;
	
	public String getOntologyFileName() {
		return ontologyFileName;
	}
	public void setOntologyFileName(String ontologyFileName) {
		this.ontologyFileName = ontologyFileName;
	}
	public String getOntologyURI() {
		return ontologyURI;
	}
	public void setOntologyURI(String ontologyURI) {
		this.ontologyURI = ontologyURI;
	}
	public NativeObject getNativeObject() {
		return nativeObject;
	}
	public void setNativeObject(NativeObject nativeObject) {
		this.nativeObject = nativeObject;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public int compareTo(OntologyMapping o) {
		return score.compareTo(o.score);
	}
	
	
	
	
}
