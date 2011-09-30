package org.universAAL.ucc.api.plugin;

public class PluginMenuSimpleEntry extends PluginMenuEntry {
	
	private String caption = "";
	private Runnable toBeRunAtClick = null;

	public PluginMenuSimpleEntry(String caption, Runnable toBeRunAtClick) {
		super(PluginMenuEntryType.SIMPLE_TEXT);
		this.caption = caption;
		this.toBeRunAtClick = toBeRunAtClick;
	}

	public String getCaption() {
		return caption;
	}

	public Runnable getToBeDoneAtClick() {
		return toBeRunAtClick;
	}	
}
