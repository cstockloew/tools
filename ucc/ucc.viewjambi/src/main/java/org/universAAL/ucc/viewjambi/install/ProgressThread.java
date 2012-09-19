package org.universAAL.ucc.viewjambi.install;

import com.trolltech.qt.gui.QProgressDialog;

public class ProgressThread extends Thread {
	boolean finished = false;
	
    public void run() {
    	QProgressDialog qd = new QProgressDialog("Installing the multi-part application...", null, 0, 100);
    	qd.setWindowTitle("Installing");
    	qd.setAutoClose(true);
    	int i=1;
    	qd.setValue(10);
    	while(!finished) {	
    		try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i<10) { 
				qd.setValue(i*10);
				System.out.println("progressing: " + i*10 + "%");
				i++;
			}
    	}
    	qd.setValue(100);
    }
    
}
