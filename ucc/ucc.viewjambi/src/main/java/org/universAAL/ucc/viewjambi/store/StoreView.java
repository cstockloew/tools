package org.universAAL.ucc.viewjambi.store;

import org.universAAL.ucc.viewjambi.MainWindow;
import org.universAAL.ucc.viewjambi.SubWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_StoreView;

import com.trolltech.qt.core.QSize;

public class StoreView extends SubWindow {
	
	private static Ui_StoreView install_base = new Ui_StoreView();
	static MainWindow parent;



	public StoreView(MainWindow parent) {
		super(parent, StoreView.install_base);
		this.parent = parent;
		
		this.setMinimumSize(new QSize(500,  500));
		
    	
  	}
	
	
}
