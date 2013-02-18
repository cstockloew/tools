package org.universaal.tools.packaging.tool.parts;


public class Application {

	public static final String defaultURL = "http://www.webpage.com";
	public static final String defaultString = "asdf";
	public static final String defaultFile = "c:\file.txt";

	private App application;
	private ApplicationCapabilities capabilities;
	private ApplicationRequirements requirements;
	private ApplicationManagement management;
	private Part part;

	public Application(){
		this.application = new App();
		this.capabilities = new ApplicationCapabilities();
		this.requirements = new ApplicationRequirements();
		this.management = new ApplicationManagement();
		this.part = new Part("defaultId");
	}

	public App getApplication() {
		return application;
	}
	public void setApplication(App app) {
		this.application = app;
	}
	public ApplicationCapabilities getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(ApplicationCapabilities capabilities) {
		this.capabilities = capabilities;
	}
	public ApplicationRequirements getRequirements() {
		return requirements;
	}
	public void setRequirements(ApplicationRequirements requirements) {
		this.requirements = requirements;
	}
	public ApplicationManagement getManagement() {
		return management;
	}
	public void setManagement(ApplicationManagement management) {
		this.management = management;
	}
	public Part getPart() {
		return part;
	}
	public void setPart(Part part) {
		this.part = part;
	}
}