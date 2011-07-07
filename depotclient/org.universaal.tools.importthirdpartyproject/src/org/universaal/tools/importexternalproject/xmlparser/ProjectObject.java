package org.universaal.tools.importexternalproject.xmlparser;

import java.util.ArrayList;

public class ProjectObject implements Comparable<ProjectObject>{
	
	private String name;
	private String url;
	private String description;
	private String developer;
	private String date;
	private ArrayList<String> tags;
	private boolean nameMatch;
	
	public ProjectObject(String iName, String iUrl, String iDesc, String iDev,
			String iDate, boolean nameMatch){
		this.name = iName;
		this.url = iUrl;
		this.description = iDesc;
		this.developer = iDev;
		this.date = iDate;
		this.nameMatch = nameMatch;
		this.tags = new ArrayList<String>();
	}
	
	public String getName(){
		return name;
	}
	
	public String getUrl(){
		return url;
	}
	
	public String getDesc(){
		return description;
	}
	
	public String getDeveloper(){
		return developer;
	}
	
	public String getDate(){
		return date;
	}
	
	public void addTag(String tag){
		this.tags.add(tag);
	}
	
	public ArrayList<String> getTags(){
		return tags;
	}
	
	public void print(){
		System.out.println(name +" "+url+" "+description+" "+developer+" "+date);
	}
	
	public boolean getNameMatch(){
		return nameMatch;
	}

	@Override
	public int compareTo(ProjectObject arg0) {
		if((this.nameMatch && arg0.getNameMatch()) ||
				(!this.nameMatch && !arg0.getNameMatch())){
			return 0;
		}else if(this.nameMatch && !arg0.getNameMatch()){
			return 1;
		}else{
			return -1;
		}
	}
	
	public String getHostingSite(){
		int temp = "https://".length()+1;
		int SLASH_INDEX = url.indexOf('/', temp);
		if(SLASH_INDEX >0){
			String result = url.substring(0, SLASH_INDEX);
			return result;
		}else{
			return url;
		}
	}

}
