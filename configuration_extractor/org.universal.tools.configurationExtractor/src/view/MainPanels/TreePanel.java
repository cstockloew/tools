package view.MainPanels;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import controller.GuiControl;

@SuppressWarnings("serial")
public class TreePanel extends JPanel{
	
//	private static TreePanel treePanel=new TreePanel();
	private JTree tree;

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
