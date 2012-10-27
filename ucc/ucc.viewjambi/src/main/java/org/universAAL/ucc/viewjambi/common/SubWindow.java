package org.universAAL.ucc.viewjambi.common;

import org.universAAL.ucc.api.view.IMainWindow;
import org.universAAL.ucc.api.view.ISubWindow;
import org.universAAL.ucc.viewjambi.impl.Activator;
import org.universAAL.ucc.viewjambi.impl.MainWindow;
import org.universAAL.ucc.viewjambi.juic.Ui_SubWindow;

import com.trolltech.qt.QUiForm;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class SubWindow extends QWidget implements ISubWindow {

	private Ui_SubWindow ui_base = new Ui_SubWindow();
	protected IMainWindow parent = null;

	public SubWindow(QUiForm<QWidget> ui_content) {
		super(MainWindow.getInstance());
		this.parent = MainWindow.getInstance();
		
		ui_base.setupUi(this);

		String jarPath = "classpath:"
				+ Activator.getContext().getBundle().getLocation().substring(6)
				+ "#images/uaal.bmp";
		QPixmap pixmap = new QPixmap(jarPath);
		ui_base.headerLogo.setPixmap(pixmap);
		
		ui_content.setupUi(ui_base.content);
		
		this.parent.addSubWindow(this);
	}

	public void initialize() {
		this.show();
	}
	
	public QWidget getContentWidget() {
		return ui_base.content;
	}

}

