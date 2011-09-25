package org.universaal.tools.configurationExtractor.view.MainPanels;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import org.universaal.tools.configurationExtractor.controller.GuiControl;
/**
 * 
 * @author Ilja
 * This class provides a tree view of current configuration implemented as singleton.
 */
@SuppressWarnings("serial")
public class TreePanel extends JPanel{
	private JTree tree;

	/**
	 * Constructor
	 */
	public TreePanel(){

    	this.setPreferredSize(new Dimension(300, 100));
    	this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );

		JLabel jt =new JLabel("Tree-Panel");
		jt.setBackground(Color.DARK_GRAY);
		this.add(jt);
		
		tree=GuiControl.getInstance().getTree();

		JScrollPane jp= new JScrollPane(tree);
		this.add(jp);
		
	}

}
