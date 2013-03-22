package org.universaal.tools.packaging.tool.parts;

public class OtherChannel{

	private String channelName, channelDetails;

	public OtherChannel(){
		channelName = Application.defaultString;
		channelDetails = Application.defaultString;
	}
	
	public OtherChannel(String channelName, String channelDetails){
		this.channelName = channelName;
		this.channelDetails = channelDetails;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelDetails() {
		return channelDetails;
	}

	public void setChannelDetails(String channelDetails) {
		this.channelDetails = channelDetails;
	}

	public String getXML(){
		return "<channelName>"+channelName+"</channelName>"+"<channelDetails>"+channelDetails+"</channelDetails>";
	}
}