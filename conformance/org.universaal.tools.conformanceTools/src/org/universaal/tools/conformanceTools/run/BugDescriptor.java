package org.universaal.tools.conformanceTools.run;

import org.eclipse.jdt.core.ICompilationUnit;

public class BugDescriptor {

	private int frontEndID;
	private int markerID = -1;
	private int severity;
	private String severityDescription;
	private int line;
	private String descr;
	private String clazz;
	private String errorType;
	private ICompilationUnit cu;
	private String image;
	
	public ICompilationUnit getCu() {
		return cu;
	}
	public String getSeverityDescription() {
		return severityDescription;
	}
	public void setSeverityDescription(String severityDescription) {
		this.severityDescription = severityDescription;
	}
	public void setCu(ICompilationUnit cu) {
		this.cu = cu;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public int getMarkerID() {
		return markerID;
	}
	public void setMarkerID(int markerID) {
		this.markerID = markerID;
	}
	public int getFrontEndID() {
		return frontEndID;
	}
	public void setFrontEndID(int frontEndID) {
		this.frontEndID = frontEndID;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}		
}