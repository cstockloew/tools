package org.universAAL.ucc.viewjambi.overview;

import java.util.ArrayList;
import java.util.List;

import org.universAAL.ucc.api.core.IDeinstaller;
import org.universAAL.ucc.viewjambi.Activator;
import org.universAAL.ucc.viewjambi.MainWindow;
import org.universAAL.ucc.viewjambi.SubWindow;
import org.universAAL.ucc.viewjambi.information.InformationView;
import org.universAAL.ucc.viewjambi.install.InstallView;
import org.universAAL.ucc.viewjambi.juic.Ui_GridView;
import org.universAAL.ucc.viewjambi.layouts.OverviewGridLayout;
import org.universAAL.ucc.viewjambi.store.StoreView;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLayoutItemInterface;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QMessageBox.StandardButton;
import com.trolltech.qt.webkit.QWebView;

public class GridView extends SubWindow {
	
	private static Ui_GridView install_base = new Ui_GridView();
	private OverviewGridLayout gridLayout;
	private static List<LabeledIcon> iconWidgets;
	static MainWindow parent;
	static OverviewView overview;


	public GridView(MainWindow parent) {
		super(parent, GridView.install_base);
		this.parent = parent;
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
		overview = new OverviewView(parent);
	}
	
	public void install(){
		new InstallView(parent);
	}
	
	public void uninstall(){
		if(overview instanceof OverviewView){
			if(overview.isVisible()){
				QModelIndex index = OverviewView.treeView.currentIndex();
					if(Activator.getModel().getApplicationManagment().containsApplication((String) index.data())){
						if(QMessageBox.question(this, "Deinstall Application", "Do you want to delete the application "+index.data()+"?", QMessageBox.StandardButton.Yes, QMessageBox.StandardButton.No) == StandardButton.Yes.value()){
						IDeinstaller deinstaller = Activator.getDeinstaller();
						if(deinstaller.deinstallAppication((String) index.data())){
							QMessageBox.information(this, "Deinstall Application", "Deinstalltion of the application "+index.data()+" completed!");
						}else{
							QMessageBox.critical(this, "Deinstall Application", "Could not deinstall the application!");
						}
					}
				}
			}
		}else{
			parent.deinstallApp();
		}
	}
	
	public void information(){
		new InformationView(parent);
	}
	
	public void openStore(){
		new StoreView(parent);
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
