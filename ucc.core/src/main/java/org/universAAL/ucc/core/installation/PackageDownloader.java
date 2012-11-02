package org.universAAL.ucc.core.installation;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class PackageDownloader {

	public PackageDownloader(){
		
	}
	private static final String FILENAME_SEARCH_TAG="filename";
	
	public String download(String url){
		String fileOnHardDrive=parseFileName(url);
		
		BufferedInputStream in;
		try {
		in = new BufferedInputStream(new URL(url).openStream());	
		FileOutputStream fos = new FileOutputStream(fileOnHardDrive);
		BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
		byte[] data = new byte[1024];
		int x=0;
		int downloadedBytes = 0;
		System.out.println("Download in progress!");
		while((x=in.read(data,0,1024))>=0)
			{
			bout.write(data,0,x);
			downloadedBytes += x;
			printProgress(downloadedBytes);
			}
			bout.close();
			in.close();
	
		} catch (MalformedURLException e) {
			System.err.println("Couldn't download uaal package. The url is malformed!");
			return null;
		} catch (IOException e) {
			System.err.println("There was an IO error downloading the uaal package!");
			return null;
		}
		System.out.println("Download finished!");
		return fileOnHardDrive;
	}
	
	private String oldDownloadedMBytes="";
	private void printProgress(int downloadedBytes){
		DecimalFormat df = new DecimalFormat("0.0");
		String downloadedMBytes=df.format((float)downloadedBytes/1048576f);
		if(!downloadedMBytes.equals(oldDownloadedMBytes))
			System.out.println(downloadedMBytes+" MB downloaded!");
		oldDownloadedMBytes=downloadedMBytes;
	}
	private String parseFileName(String url){
		String result="package.uaal";
		String[] values=url.split("&");
		for(int i=0;i<values.length;i++){
			String[] current = values[i].split("\\?");
			for(int j=0;j<current.length;j++){
				if(current[j].startsWith(FILENAME_SEARCH_TAG))
					result=current[j].substring(FILENAME_SEARCH_TAG.length()+1);	
			}		
		}
		return result;
	}
}
