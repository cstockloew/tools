package org.universAAL.ucc.core.installation;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.*;

import org.universAAL.ucc.core.usrv.AalUsrv;
import org.universAAL.ucc.core.usrv.ObjectFactory;
import org.universAAL.ucc.core.usrv.VersionType;

/**
 * This class parses the .usrv.xml file against the AAL_USRV.xsd
 * @author sji
 *
 */
public class UsrvParser {
	AalUsrv usrv = null;
	private static String USRV_EXTENSION=".usrv";
	
	private JAXBContext jc;
	private Unmarshaller unmarshaller;
	
	public UsrvParser(String deployPath) {
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
			if(filelist[i].contains(USRV_EXTENSION)){
				String usrvName = deployPath+File.separator+filelist[i];
				System.out.println("[UsrvParser] the usrv file is: " + usrvName);
				// convert"\" to "//" -- this is OS dependent, solve later
				usrvName = usrvName.replace("\\", "/");
				init(usrvName);				
				return;
			}
		}
	}
	
	public void init(String usrvFile)  {
		//get the USRV object representation		
		System.out.println("[UsrvParser.init] usrvFile is " + usrvFile);
		try {
			usrv = (AalUsrv)unmarshaller.unmarshal(new File(usrvFile));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(usrv == null){
			System.out.println("Usrv file is not valid. Aborting...");
			return;
		}
		
	}

	public String getLicenseLink()  {
		String link=null;
		List<AalUsrv.Srv.Licenses> licenses = usrv.getSrv().getLicenses();
		Iterator<AalUsrv.Srv.Licenses> i = licenses.iterator();
		while (i.hasNext())  {
			AalUsrv.Srv.Licenses license = i.next();
			System.out.println("[UsrvParser] the license is: " + license.toString());
			if (license.isSetSla())  {
				link = i.next().getSla().getLink();
			}
		}
		return link;
	}
	
	public String getServiceId() {	
		return usrv.getSrv().getServiceId();
	}
	
	public String getServiceName() {
		return usrv.getSrv().getName();
	}
	
	public String getServiceDescription() {
		return usrv.getSrv().getDescription();
	}
	
	public String getServiceVersion() {
		VersionType version = usrv.getSrv().getVersion();
		return version.getMajor()+"."+version.getMinor()+"."+version.getMicro()+"_"+version.getBuild();
	}
}
