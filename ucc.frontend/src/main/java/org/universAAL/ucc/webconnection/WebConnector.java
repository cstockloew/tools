package org.universAAL.ucc.webconnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.universAAL.ucc.api.IInstaller;

/**
 * Connects and registers the ucc to the uStore. 
 * @author merkle
 *
 */

public class WebConnector {
	public static final int SINGLE_INSTANCE_NETWORK_SOCKET = 9988;
	public static final String URL_SEARCH_TAG="url";
	
	private ServerSocket socket;
	private IInstaller installer;
	
	public WebConnector(IInstaller installer) {
		this.installer = installer;
		try {
			socket = new ServerSocket(SINGLE_INSTANCE_NETWORK_SOCKET, 10, 
					InetAddress.getByAddress(new byte[] {127, 0, 0, 1}));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startListening(){
		socketListenerThread.start();
	}
	
	public void stopListening(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// this is obsolete - do not suport from MW2.0
	public void onEventCatched(String post){
		String url=parseURL(post);
        if(url!=null){
        	PackageDownloader downloader=new PackageDownloader();
        	String fileOnHardDrive=downloader.download(url);
        	if(new File(fileOnHardDrive).exists()){
        		String appDir;
				try {
					appDir = installer.installApplication(fileOnHardDrive);
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
        		
/*        		Activator.getMainWindow().installApp(appDir);  */
					
    			}
    				
        	}
        	
        }
    	
	
	Thread socketListenerThread = new Thread(new Runnable() {
        public void run() {
            boolean socketClosed = false;
            
            while (!socketClosed) {
                if (socket.isClosed()) {
                    socketClosed = true;
                } else {
                    try {
                        Socket client = socket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(),Charset.forName("UTF-8")));
                        DataOutputStream out = new DataOutputStream(client.getOutputStream());
          
                        String message = in.readLine();
                        char[] cbuf = new char[2048];
      
                        in.read(cbuf);
                        message=String.valueOf(cbuf);
                        out.write(("HTTP/1.1 204 No Content").getBytes());
                        //out.writeUTF("200");
                        onEventCatched(message);
                        
                        in.close();
                        client.close();
                    } catch (IOException e) {
                        socketClosed = true;
                        e.printStackTrace();
                    }
                }
            }
        }
    });

	private String parseURL(String post){
		String url="";
		String[] lines= post.split("\n");
		String[] content=lines[lines.length-1].split("&");
		for(int i=0;i<content.length;i++){
			if(content[i].startsWith(URL_SEARCH_TAG+"=")){
				url=content[i].substring(URL_SEARCH_TAG.length()+1);
				url=URLDecoder.decode(url);
				return url;
			}
		}
		return null;
	}

}
