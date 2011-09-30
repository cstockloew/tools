package org.universAAL.ucc.api.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PluginMenu {
	
	private List<PluginMenuEntry> entries = new ArrayList<PluginMenuEntry>();
	private String caption = null;

	public PluginMenu(String caption) {
		this.caption = caption;
	}
	
	public void addSeperator() {
		this.entries.add(new PluginMenuSeperator());
	}
	
	public void addEntry(String caption, Runnable toBeRunAtClick) {
		this.entries.add(new PluginMenuSimpleEntry(caption, toBeRunAtClick));
	}
	
	public Iterator<PluginMenuEntry> getEntries() {
		return this.entries.iterator();
	}

	public String getCaption() {
		return caption;
	}	
}
