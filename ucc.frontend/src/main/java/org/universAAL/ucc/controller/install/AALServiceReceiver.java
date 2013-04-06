package org.universAAL.ucc.controller.install;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.vaadin.ui.Upload.Receiver;

public class AALServiceReceiver implements Receiver {
    private String fileName;
    private String mtype;
    private final static String dir = "tempUsrvFiles";

    public OutputStream receiveUpload(String filename, String mimeType) {
	ZipFile arch = null;
	InputStream is = null;
	OutputStream os = null;
	try {
	    arch = new ZipFile(System.getenv("systemdrive") + "/" + dir + "/"
		    + filename);
	} catch (IOException e1) {
	    e1.printStackTrace();
	}
	for (ZipEntry entry : Collections.list(arch.entries())) {
	    File file = new File(System.getenv("systemdrive") + "/" + dir,
		    entry.getName());
	    byte[] buffer = new byte[8192];
	    if (entry.isDirectory())
		file.mkdirs();
	    else {
		new File(file.getParent()).mkdirs();

		try {
		    is = arch.getInputStream(entry);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		try {
		    os = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		}

		try {
		    for (int len; (len = is.read(buffer)) != -1;) {
			os.write(buffer, 0, len);
		    }
		} catch (IOException e) {
		    e.printStackTrace();

		}

	    }
	}

	return os;

    }

}
