package org.universAAL.ucc.api.plugin;

public class PluginGridViewItem {

	private String caption="";
	private String icon;
	private Runnable toBeRunAtClick = null;
	
	public PluginGridViewItem(String caption, String icon, Runnable toBeRunAtClick){
		this.caption=caption;
		this.icon=icon;
		this.toBeRunAtClick=toBeRunAtClick;
	}
	public String getCaption(){
		return this.caption;
	}
	public String getIcon(){
		return this.icon;
	}
	public Runnable getToBeRunAtClick(){
		return this.toBeRunAtClick;
	}
}
