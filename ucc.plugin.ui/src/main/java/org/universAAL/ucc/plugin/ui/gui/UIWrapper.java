package org.universAAL.ucc.plugin.ui.gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.universAAL.ucc.api.plugin.IPluginBase;
import org.universAAL.ucc.plugin.ui.gui.juic.Ui_universAALUI;
import org.universAAL.ucc.viewjambi.common.SubWindow;

import com.trolltech.research.qtjambiawtbridge.QComponentHost;

public class UIWrapper extends SubWindow {

	private static Ui_universAALUI information_base = new Ui_universAALUI();
	
	private IPluginBase uCCPluginBase = null;

	public UIWrapper() {
		super(UIWrapper.information_base);
		
		//JFrame frame = new JFrame();
		//frame.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(100, 100);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		JButton button = new JButton("Test");
		mainPanel.add(button);
		//frame.add(mainPanel);
		
		QComponentHost host = new QComponentHost(mainPanel);
		UIWrapper.information_base.gridLayout_2.addWidget(host,0,0);
	}

	protected void closeMe() {
		this.uCCPluginBase.getMainView().hideSubWindow(this);
	}
}
