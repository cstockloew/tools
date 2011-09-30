package org.universAAL.ucc.api.plugin;

public class PluginMenuEntry {
	
	private PluginMenuEntryType type = null;
	
	public PluginMenuEntry(PluginMenuEntryType type) {
		this.type = type;
	}

	public PluginMenuEntryType getType() {
		return type;
	}
}
