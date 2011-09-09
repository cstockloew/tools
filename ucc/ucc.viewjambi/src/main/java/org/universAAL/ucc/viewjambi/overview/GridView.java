package org.universAAL.ucc.viewjambi.overview;

import java.util.ArrayList;
import java.util.List;

import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.information.InformationView;
import org.universAAL.ucc.viewjambi.install.InstallView;
import org.universAAL.ucc.viewjambi.juic.Ui_GridView;
import org.universAAL.ucc.viewjambi.layouts.OverviewGridLayout;
import org.universAAL.ucc.viewjambi.store.StoreView;

import com.trolltech.qt.core.QSize;


public class GridView extends SubWindow {
	
	private static Ui_GridView install_base = new Ui_GridView();
	private OverviewGridLayout gridLayout;
	private static List<LabeledIcon> iconWidgets;
	static OverviewView overview;


	public GridView() {
		super(GridView.install_base);
		gridLayout = install_base.currentLayout;
		
		this.setMinimumSize(new QSize(500,  200));
 
		setIconList();

		int current = 0;
    	for(LabeledIcon icon : iconWidgets){  		
    		gridLayout.addWidget(icon, 3);
    		switch(current){
    		case 0: icon.clicked.connect(this, "overview()"); break;
    		case 1: icon.clicked.connect(this, "install()"); break;
    		case 2: icon.clicked.connect(this, "uninstall()"); break;
    		case 3: icon.clicked.connect(this, "information()"); break;
    		case 4: icon.clicked.connect(this, "openStore()"); break;
    		default: icon.clicked.connect(this, "overview()"); break;
    		}
    		current++;
    	} 
  	}
	
	public void overview(){
		overview = new OverviewView();
	}
	
	public void install(){
		new InstallView();
	}
	
	public void uninstall(){
		MainWindow.getInstance().deinstallApp();
	}
	
	public void information(){
		new InformationView();
	}
	
	public void openStore(){
		new StoreView();
	}
    
	private void setIconList(){
		iconWidgets = new ArrayList<LabeledIcon>();
		iconWidgets.add(new LabeledIcon("Overview", "explorer.png"));
		iconWidgets.add(new LabeledIcon("Install App", "3dsmax.png"));
		iconWidgets.add(new LabeledIcon("Uninstall App", "aimp 4.png"));
		iconWidgets.add(new LabeledIcon("System\nInformation", "addressbook.png"));
		iconWidgets.add(new LabeledIcon("Download App", "utorrent2.png"));
		iconWidgets.add(new LabeledIcon("Foo", "rss.png"));
		iconWidgets.add(new LabeledIcon("Bar", "chat.png"));
		iconWidgets.add(new LabeledIcon("Foo2", "gmail.png"));
		/*iconWidgets.add(new LabeledIcon("Download App", "utorrent2.png"));
		iconWidgets.add(new LabeledIcon("Download App", "utorrent2.png"));
		iconWidgets.add(new LabeledIcon("Download App", "utorrent2.png"));
		iconWidgets.add(new LabeledIcon("Download App", "utorrent2.png"));
		iconWidgets.add(new LabeledIcon("Download App", "utorrent2.png"));
		*/
	}
	
}
