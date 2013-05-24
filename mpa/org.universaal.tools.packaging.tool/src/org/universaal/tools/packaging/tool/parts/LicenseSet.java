package org.universaal.tools.packaging.tool.parts;

import java.util.ArrayList;
import java.util.List;

public class LicenseSet{

	private SLA sla;
	private List<License> licenseList;

	public LicenseSet(){
		sla = new SLA();
	}

	public SLA getSla() {
		return sla;
	}
	public void setSla(SLA sla) {
		this.sla = sla;
	}
	public List<License> getLicenseList() {
		if(licenseList == null)
			licenseList = new ArrayList<License>();
		return licenseList;
	}

	public String getXML(){

		String r = "";
		r = r.concat("<licenses>");
		for(int i = 0; i< licenseList.size(); i++)
			r = r.concat("<license>"+licenseList.get(i).getXML()+"</license>");
		r = r.concat(sla.getXML());
		r = r.concat("</licenses>");

		return r;
	}
}