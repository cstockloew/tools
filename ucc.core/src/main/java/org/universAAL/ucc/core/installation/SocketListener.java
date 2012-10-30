/*
	Copyright 2007-2014 FZI, http://www.fzi.de
	Forschungszentrum Informatik - Information Process Engineering (IPE)

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

package org.universAAL.ucc.core.installation;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.ucc.api.core.IInstaller;
import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.core.Activator;

/**
 * 
 * @author tzentek - <a href="mailto:zentek@fzi.de">Tom Zentek</a>
 * @author mfrigge
 * 
 */

public class SocketListener {

	public static final int SINGLE_INSTANCE_NETWORK_SOCKET = 9988;
	public static final String URL_SEARCH_TAG="url";
	public static final String OPEN_GUI_TAG="http://please.open.gui";
	public ServerSocket socket;
	private IInstaller installer;
	private BundleContext context;
	
	public SocketListener(IInstaller installer,BundleContext context){
		this.installer=installer;
		this.context=context;
	        // try to open network socket
	        try {
	            socket = new ServerSocket(SINGLE_INSTANCE_NETWORK_SOCKET, 10, 
	            		InetAddress.getByAddress(new byte[] {127, 0, 0, 1}));

	            System.out.println("Socket sucessfully binded to "+SINGLE_INSTANCE_NETWORK_SOCKET);
	            
	            // listen
	        } catch (UnknownHostException e) {
	        	e.printStackTrace();
	        } catch (IOException e) {
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
	
	public void onEventCatched(String post){
		String url = null;
		try{
			url = parseURL(post);	
		}catch(Exception e){
			System.err.println("An event was catched but the url couldn't be parsed!");
			e.printStackTrace();
			return;
		}
		System.out.println("Event Catched: " + url);
		
		if(url!=null){
//			if(Activator.getMainWindow()==null||!Activator.getMainWindow().isUIShowing()){
//				System.out.println("Open uCC GUI");
				Activator.getUILauncher().showUi();
//			}else
//				System.out.println("uCC GUI already opened");
			
			if(!url.equals(OPEN_GUI_TAG)){
				PackageDownloader downloader = new PackageDownloader();
				System.out.println("Start Download: " + url);
				String fileOnHardDrive=downloader.download(url);
				if(fileOnHardDrive==null){
					return;
				}
				if(new File(fileOnHardDrive).exists()){
					String appDir;
					try{
						appDir = installer.installApplication(fileOnHardDrive);
					}catch(Exception e){
						e.printStackTrace();
						return;
					}
					Activator.getMainWindow().installApp(appDir);
				}
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
