/*	
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
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

package org.universaal.uaalpax.model;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;

public class BundleEntry {
	private String url;
	private boolean selected;
	private boolean start;
	private int level;
	private boolean update;
	private String projectName;
	
	public static final String PROP_PROJECT = "Project";
	public static final String PROP_LEVEL = "Level";
	
	public static String urlFromArtifact(Artifact a) {
		return "mvn:" + a.getGroupId() + "/" + a.getArtifactId() + "/" + a.getVersion();
	}
	
	public static Artifact artifactFromURL(String url) {
		if (url.startsWith("mvn:")) {
			String[] s = url.substring(4).split("/");
			
			if (s.length == 3)
				return new DefaultArtifact(s[0], s[1], null, s[2]);
			else if (s.length == 2)
				return new DefaultArtifact(s[0], s[1], null, null);
			else
				return null;
		} else
			return null;
	}
	
	public BundleEntry(String url, String projectName, int startLevel, boolean update) {
		this.url = url;
		this.selected = true;
		this.start = true;
		this.level = startLevel;
		this.update = update;
		this.projectName = projectName;
	}
	
	public BundleEntry(String url, boolean selected, boolean start, int startLevel, boolean update) {
		this.url = url;
		this.selected = selected;
		this.start = start;
		this.level = startLevel;
		this.update = update;
		this.projectName = null;
	}
	
	public BundleEntry(String url, String settingsStr) {
		this.url = url;
		
		String[] settings = settingsStr.split("@");
		this.level = -1;
		
		if (settings.length == 4) {
			try {
				this.selected = Boolean.parseBoolean(settings[0]);
				this.start = Boolean.parseBoolean(settings[1]);
				try {
					this.level = Integer.parseInt(settings[2]);
				} catch (NumberFormatException e) {
					level = -1;
				}
				this.update = Boolean.parseBoolean(settings[3]);
			} catch (NumberFormatException e) {
			}
		}
		
		this.projectName = null;
	}
	
	public BundleEntry(Artifact a) {
		this.url = urlFromArtifact(a);
		this.selected = true;
		this.start = true;
		this.level = 1; // TODO
		this.update = true; // TODO
		this.projectName = this.url;
	}
	
	public Artifact toArtifact() {
		return artifactFromURL(getURL());
	}
	
	public String getOptions() {
		return String.valueOf(selected) + "@" + String.valueOf(start) + "@" + (level < 0? "default" : String.valueOf(level)) + "@" + String.valueOf(update);
	}
	
	public BundleEntry(String projectName, String url, String settingsStr) {
		this(url, settingsStr);
		this.projectName = projectName;
	}
	
	public String getProjectName() {
		if (projectName != null)
			return projectName;
		else
			return url;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getURL() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isStart() {
		return start;
	}
	
	public void setStart(boolean start) {
		this.start = start;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int startLevel) {
		this.level = startLevel;
	}
	
	public boolean isUpdate() {
		return update;
	}
	
	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	public boolean equalsURL(BundleEntry other) {
		return getURL().equals(other.getURL());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof BundleEntry))
			return false;
		
		// TODO really equals only if _all settings_ matches???
		BundleEntry pu = (BundleEntry) obj;
		return this.getLevel() == pu.getLevel() && this.getURL().equals(pu.getURL()) && this.isSelected() == pu.isSelected()
				&& this.isStart() == pu.isStart() && this.isUpdate() == pu.isUpdate();
	}
	
	@Override
	public int hashCode() {
		int hashcode = 1;
		hashcode *= 13 * getLevel();
		hashcode *= 7 * getProjectName().hashCode();
		hashcode *= 23 * getURL().hashCode();
		hashcode *= 17 * (isSelected() ? 1 : 3);
		hashcode *= 11 * (isUpdate() ? 1 : 3);
		hashcode *= 19 * (isStart() ? 1 : 3);
		return hashcode;
	}
	
	@Override
	public String toString() {
		return "ProjectURL: " + getProjectName() + " | " + isStart() + " | " + getLevel() + " | " + isUpdate();
	}
}
