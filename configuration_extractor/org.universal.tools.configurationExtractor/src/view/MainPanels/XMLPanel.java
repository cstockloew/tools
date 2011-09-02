package view.MainPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.IconGroup;
import org.fife.ui.rtextarea.RTextScrollPane;

import controller.GuiControl;

import view.LowLevelPanels.BigRootPanel;
import model.xml.XMLCreator;


@SuppressWarnings("serial")
public class XMLPanel extends JPanel  {
	GuiControl guiControl = GuiControl.getInstance();
	private static XMLPanel xmlPanel=new XMLPanel();
	private String text;
//	private JTextArea text= new JTextArea();
	// Syntax highlighting text component for Java Swing
	RSyntaxTextArea rsyntaxtextarea;
	 	
	private Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12 );
	
	private XMLPanel(){
    	this.setPreferredSize(new Dimension(400, 100));
    	this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		JLabel jt =new JLabel("XML-Panel");
		jt.setBackground(Color.DARK_GRAY);
		this.add(jt);
		rsyntaxtextarea = new RSyntaxTextArea();
		rsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
//		rsyntaxtextarea.setEditable(false);
		RTextScrollPane sp = new RTextScrollPane(rsyntaxtextarea);  
		this.add(sp);
	}
	public static XMLPanel getInstance(){
		return xmlPanel;
	}
	public void setText(String newText){
		this.text=newText;
		rsyntaxtextarea.setText(newText);
	}
	
	public void refresh(){
	  guiControl.refreshXmlPanel(); 		
	}
	
	public String getTextFromXMLPanel(){
		return rsyntaxtextarea.getText();
	}
	
	public boolean xmlPanelChanged(){
		return !rsyntaxtextarea.getText().equals((this.text));
	}
	
	public boolean getVisibilityStatus(){
		return xmlPanel.isVisible();
	}
	
}
