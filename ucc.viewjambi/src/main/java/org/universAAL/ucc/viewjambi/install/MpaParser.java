package org.universAAL.ucc.viewjambi.install;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.*;

import org.universAAL.middleware.connectors.deploy.model.AalMpa;
import org.universAAL.middleware.connectors.deploy.model.AalMpa.ApplicationPart;
import org.universAAL.middleware.connectors.deploy.model.ObjectFactory;

public class MpaParser {
	AalMpa mpa = null;
	
	private JAXBContext jc;
	private Unmarshaller unmarshaller;
	
	public MpaParser(String deployPath) {
		try {	
			jc = JAXBContext.newInstance(ObjectFactory.class);
			unmarshaller = jc.createUnmarshaller();
			//marshaller = jc.createMarshaller();
		} catch (JAXBException e) {
			System.out.println(e);
		}

		
		File appDir=new File(deployPath);
    	String[] filelist=appDir.list();
		for(int i=0;i<filelist.length;i++){
			if(filelist[i].endsWith(".mpa")){
				String mpaName = deployPath+File.separator+filelist[i];
				System.out.println("[MpaParser] the mpa file is: " + mpaName);
				// convert"\" to "//" -- this is OS dependent, solve later
				mpaName = mpaName.replace("\\", "/");
				init(mpaName);				
				return;
			}
		}
	}
	
	public void init(String multiPartApplication)  {
		//get the MPA object representation		
		System.out.println("[MpaParser.init] multiPartApplication is " + multiPartApplication);
		try {
			mpa = (AalMpa)unmarshaller.unmarshal(new File(multiPartApplication));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(mpa == null){
			System.out.println("MPA file is not valid. Aborting...");
			return;
		}
		
	}

	public String getAppName()  {
		return mpa.getApp().getName();
	}
	
	public ApplicationPart getApplicationPart() {
		return mpa.getApplicationPart();
	}
}
