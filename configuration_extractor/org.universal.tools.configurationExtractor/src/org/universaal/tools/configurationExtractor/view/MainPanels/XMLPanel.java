package org.universaal.tools.configurationExtractor.view.MainPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.universaal.tools.configurationExtractor.controller.GuiControl;
/**
 * 
 * @author Ilja
 * This panel contains a XML editor with current configuration in poor XML format
 */
@SuppressWarnings("serial")
public class XMLPanel extends JPanel  {
	GuiControl guiControl = GuiControl.getInstance();
	private static XMLPanel xmlPanel=new XMLPanel();
	private String text;
	// Syntax highlighting text component for Java Swing
	private RSyntaxTextArea rsyntaxtextarea;
	private Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12 );
	
	/*
	 * Constructor is private because of singleton implementation  
	 */
	private XMLPanel(){
    	this.setPreferredSize(new Dimension(400, 100));
    	this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		JLabel jt =new JLabel("XML-Panel");
		jt.setBackground(Color.DARK_GRAY);
		this.add(jt);
		rsyntaxtextarea = new RSyntaxTextArea();
		rsyntaxtextarea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		RTextScrollPane sp = new RTextScrollPane(rsyntaxtextarea);  
		this.add(sp);
	}
	/**
	 * Typical singleton method
	 * @return a singleton instance of XMLPanel
	 */
	public static XMLPanel getInstance(){
		return xmlPanel;
	}
	/**
	 * Set new text
	 * @param newText new text
	 */
	public void setText(String newText){
		this.text=newText;
		rsyntaxtextarea.setText(newText);
	}
	/**
	 * Refresh a XMLPanel
	 */
	public void refresh(){
	  guiControl.refreshXmlPanel(); 		
	}
	
	/**
	 * Get current text
	 * @return current text
	 */
	public String getTextFromXMLPanel(){
		return rsyntaxtextarea.getText();
	}
	/**
	 * Checks, whether user has changed something in xml editor
	 * @return true, if xml panel has changed
	 */
	public boolean xmlPanelChanged(){
		return !rsyntaxtextarea.getText().equals((this.text));
	}
	
	/**
	 * Checks whether XMLPanel is visible
	 * @return true if XMLPanel is visible
	 */
	public boolean getVisibilityStatus(){
		return xmlPanel.isVisible();
	}
	
}
