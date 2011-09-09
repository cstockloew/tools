package org.universAAL.ucc.viewjambi.overview;

import java.util.Map;
import java.util.Map.Entry;

import org.universAAL.ucc.api.model.IModel;
import org.universAAL.ucc.viewjambi.common.SubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_Configure;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;

public class Overview_ConfigView extends SubWindow {
	
	private static Ui_Configure install_base = new Ui_Configure();
	private static IModel model;
	private String bundleName;
	
	protected Overview_ConfigView(String bundleName) {
		super(install_base);
		this.bundleName = bundleName;
		model = Activator.getModel();
		install_base.cancelButton.clicked.connect(this, "cancel()");
		install_base.okButton.clicked.connect(this, "save()");
		init();
	}
	
	public void init(){
		Map<String, String> configMap = model.getApplicationManagment().getConfiguration(bundleName);
		int row = 0;
		
		for(Entry<String, String> entry : configMap.entrySet()){
			QHBoxLayout hBoxLayout = new QHBoxLayout();

			QLabel attribute = new QLabel(entry.getKey());
			QLabel value = new QLabel(entry.getValue());
			
			attribute.setParent(hBoxLayout);
			hBoxLayout.insertWidget(0, attribute);
			value.setParent(hBoxLayout);
			hBoxLayout.insertWidget(1, value);
			
			install_base.verticalLayout_4.insertLayout(row, hBoxLayout);
			row++;
		}
		install_base.verticalLayout_4.activate();
	}
	
	
	protected void cancel() {
		MainWindow.getInstance().closeSubWindow(this);
	}
	
	protected void save(){
		
	}
	

}
