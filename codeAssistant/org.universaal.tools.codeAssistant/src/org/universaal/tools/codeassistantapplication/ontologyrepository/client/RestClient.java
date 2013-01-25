package org.universaal.tools.codeassistantapplication.ontologyrepository.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RestClient {

	static public boolean sendGetRequest(String endpoint,String requestParameters, String pathToSave){
		String result = null;
		if (endpoint.startsWith("http://")) {
			// Send a GET request to the servlet
			try {
				
				// Construct data
				StringBuffer data = new StringBuffer();
				// Send data
				String urlStr = endpoint;
				if (requestParameters != null && requestParameters.length() > 0) {
					urlStr += "?" + requestParameters;
				}
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();
				conn.setReadTimeout(10000);
				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				BufferedWriter out = new BufferedWriter(new FileWriter(pathToSave));
				String line;
				while ((line = rd.readLine()) != null) {
					out.write(line + "\n");
				}
				rd.close();
				out.close();			
				File fil = new File(pathToSave);
				long size = fil.length() / 1024;

				//System.out.println(size + "KB");
				// System.out.println(pathToSave);
				return true;
			} catch (Exception e) {
				//e.printStackTrace();
				return false;
			}
		}
		return false;
	}

}
