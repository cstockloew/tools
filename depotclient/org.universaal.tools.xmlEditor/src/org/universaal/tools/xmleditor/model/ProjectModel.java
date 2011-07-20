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


public class ProjectModel {
	
	private IEditorInput input;
	private String pName, pDev, pDate, pUrl, pSvnUrl, pDesc;
	private ArrayList<String> pTags;
	private XmlEditor listener;
	
	public ProjectModel(String name, String dev, String date, String url,
			String svnurl, String desc, ArrayList<String> tags, XmlEditor input){
		pName = name;
		pDev = dev;
		pDate = date;
		pUrl = url;
		pSvnUrl = svnurl;
		pDesc = desc;
		pTags = tags;
		listener = input;
	}

	public IEditorInput getInput() {
		return input;
	}

	public String getpName() {
		return pName;
	}

	public String getpDev() {
		return pDev;
	}

	public String getpDate() {
		return pDate;
	}

	public String getpUrl() {
		return pUrl;
	}

	public String getpSvnUrl() {
		return pSvnUrl;
	}

	public String getpDesc() {
		return pDesc;
	}

	public ArrayList<String> getpTags() {
		return pTags;
	}

	public void setInput(IEditorInput input) {
		this.input = input;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public void setpDev(String pDev) {
		this.pDev = pDev;
	}

	public void setpDate(String pDate) {
		this.pDate = pDate;
	}

	public void setpUrl(String pUrl) {
		this.pUrl = pUrl;
	}

	public void setpSvnUrl(String pSvnUrl) {
		this.pSvnUrl = pSvnUrl;
	}

	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
	}

	public void setpTags(ArrayList<String> pTags) {
		this.pTags = pTags;
	}
	
	public String asXML(){
		String result;
		result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		result += "<project>\n"+
		"\t<name>"+pName+"</name>\n"+
		"\t<developer>"+pDev+"</developer>\n"+
		"\t<date>"+pDate+"</date>\n"+
		"\t<url>"+pUrl+"</url>\n"+
		"\t<svnurl>"+pSvnUrl+"</svnurl>\n"+
		"\t<description>"+pDesc+"</description>\n"+
		"\t<tags>\n";
		
		for(int i=0;i<pTags.size();i++){
			result+="\t\t<tag>"+pTags.get(i)+"</tag>\n";
		}
		result+="\t</tags>\n"+
	"</project>";
		return result;
	}
	
	

}
