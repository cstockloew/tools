import java.util.LinkedList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import view.Elements.BigElement;
import view.Elements.ElementG;
import view.Elements.ListG;
import view.Elements.Root;
import view.LowLevelPanels.BigListPanel;
import view.LowLevelPanels.BigRootPanel;

import view.MainPanels.MainWindow;
import view.MainPanels.TreePanel;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MainWindow mw=new MainWindow("Configuration Extractor");
//		StartWindow startWindow=new StartWindow("Configuration Extractor",mw);
//		startWindow.setVisible(true);
		
    	//-----------------------------------------------------------------------
		mw.setVisible( true );

	}

}
