package org.universAAL.ucc.core.installation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PackageDownloader {

	public PackageDownloader(){
		
	}
	private static final String FILENAME_SEARCH_TAG="filename";
	
	public String download(String url){
		String fileOnHardDrive=parseFileName(url);
		
		BufferedInputStream in;
		try {
		
			in = new BufferedInputStream(new 
					URL(url).openStream());
			
		FileOutputStream fos = new FileOutputStream(fileOnHardDrive);
		BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
		byte[] data = new byte[1024];
		int x=0;
		while((x=in.read(data,0,1024))>=0)
			{
			bout.write(data,0,x);
			}
			bout.close();
			in.close();
	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileOnHardDrive;
	}
	private String parseFileName(String url){
		String result="package.uaal";
		String[] values=url.split("&");
		for(int i=0;i<values.length;i++){
			if(values[i].startsWith(FILENAME_SEARCH_TAG))
				result=values[i].substring(FILENAME_SEARCH_TAG.length()+1);
		}
		return result;
	}
}
