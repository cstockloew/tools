/*
	Copyright 2011 SINTEF, http://www.sintef.no
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.tools.xmleditor.model;

import java.util.ArrayList;

import org.eclipse.ui.IEditorInput;
import org.universaal.tools.xmleditor.editors.XmlEditor;

/**
 * Model that is used by the XMLEditor to store information about the project.
 * @author Adrian
 *
 */
public class ProjectModel {
	
	private IEditorInput input;
	private String name, dev, date, url, svnUrl, desc, license, licenseUrl;
	private ArrayList<String> tags;
	private boolean containsSubProjects;
	
	public ProjectModel(String name, String dev, String date, String url,
			String svnurl, String desc, String license, String licenseUrl,
			boolean containSubProjects, ArrayList<String> tags, XmlEditor input){
		this.name = name;
		this.dev = dev;
		this.date = date;
		this.url = url;
		this.svnUrl = svnurl;
		this.desc = desc;
		this.tags = tags;
		this.license = license;
		this.licenseUrl = licenseUrl;
		this.containsSubProjects = containSubProjects;
	}

	public IEditorInput getInput() {
		return input;
	}

	public String getName() {
		return name;
	}

	public String getDev() {
		return dev;
	}

	public String getDate() {
		return date;
	}

	public String getUrl() {
		return url;
	}

	public String getSvnUrl() {
		return svnUrl;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getLicense(){
		return license;
	}
	
	public String getLicenseUrl(){
		return licenseUrl;
	}
	
	public boolean getContainsSubProjects(){
		return containsSubProjects;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setInput(IEditorInput input) {
		this.input = input;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public void setDev(String pDev) {
		this.dev = pDev;
	}

	public void setDate(String pDate) {
		this.date = pDate;
	}

	public void setUrl(String pUrl) {
		this.url = pUrl;
	}

	public void setSvnUrl(String pSvnUrl) {
		this.svnUrl = pSvnUrl;
	}

	public void setDesc(String pDesc) {
		this.desc = pDesc;
	}
	
	public void setLicense(String license){
		this.license = license;
	}
	
	public void setLicenseUrl(String licenseUrl){
		this.licenseUrl = licenseUrl;
	}
	
	public void setContainsSubProjects(boolean contains){
		this.containsSubProjects = contains;
	}

	public void setTags(ArrayList<String> pTags) {
		this.tags = pTags;
	}
	

}
