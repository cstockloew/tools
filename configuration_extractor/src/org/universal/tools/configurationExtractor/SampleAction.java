package org.universal.tools.configurationExtractor;

import java.io.*;
//import javax.xml.bind.*;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
import java.util.*;

//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.io.File;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.lang.reflect.Method;
//import java.lang.annotation.*;
import java.util.UUID;

import org.universal.tools.configurationExtractor.Annotations.*;

import javax.swing.JPanel;

import java.awt.BorderLayout;
//import java.awt.Component;
import java.awt.CardLayout;
import java.awt.Container;
//import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SampleAction() {
	}
	
	
	@Root(name="Vergessene Geräte",title="Einstellungen vergessene Geräte",info="Hier können Sie Ihre zu überwachenden Geräte konfigurieren")
	public void coolActionDelete() {
		
	}

	

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	@Lst(label="Geräte",title="Geräte auswählen",limit=-1,domain="http://www.openaal.org/SAM/Ontology/highLevelThing#Device|http://www.openaal.org/SAM/Ontology/highLevelThing#Oven")
	public void run(IAction action) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			//Do nothing
		}
		
		final HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("subdir", "no");
		
		JFrame frame = new JFrame("XML Configuration Wizard");
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alexander\\workspace\\org.universal.tools.configurationExtractor\\icons\\icon_config.gif");
		frame.setIconImage(icon);
		frame.setSize(500,330);
    
		final Container contentPane = frame.getContentPane();
		final CardLayout layout = new CardLayout();
		contentPane.setLayout(layout);
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton sourceButton = (JButton)e.getSource();
				if (sourceButton.getText().equals("Next >")) {
					layout.next(contentPane);
				}
				else if (sourceButton.getText().equals("< Back")) {
					layout.previous(contentPane);
				}
    	  

			}
		};   
		
		contentPane.add(getFirstPage(hm,listener),"",0);
		contentPane.add(getSecondPage(hm,listener),"",1);
		contentPane.add(getThirdPage(hm,listener),"",2);
		contentPane.add(getFourthPage(hm,listener),"",3);
		contentPane.add(getFifthPage(hm,listener),"",4);
      
		frame.show();
	
	}
	
	
	public static void exportXML(HashMap<String,String> hm) {
		
	    String dirToSearch = hm.get("fieldSourceDir");

	    String fileName = hm.get("fieldTargetDir");
	    
		Element root = new Element("root");
		Element panels = new Element("panels");
		Element listPanel = new Element("listpanel");
		Element panel = new Element("panel");	
		
		root.addContent(panels);
		

		try {
			root.setAttribute("id",fileName.replace(".xml", "") + ":" + System.currentTimeMillis()/1000);
			root.setAttribute("name",hm.get("rootName"));
			root.setAttribute("title",hm.get("rootTitle"));
			root.setAttribute("info",hm.get("rootInfo"));

			listPanel.setAttribute("id","listPanelDoors");
			listPanel.setAttribute("title","listPanelTitle");
		}
		catch (NullPointerException npe) {
			//Do nothing
		}		
		
		panels.addContent(listPanel);
		panels.addContent(panel);
		
		ArrayList<File> files = new ArrayList<File>();
		File[] fileList = new File[0];
		
		try {
			files = getFiles(dirToSearch,"java",hm);
			fileList=new File[files.size()];
		}
		catch (NullPointerException npe) {
			files = null;
			fileList = null;
		}
				
		if (files!=null) {
		
			String allCode ="";
			String str;
			
			for (int i=0; i<files.size(); i++) {
				
				if (files.get(i) == null || files.get(i).getAbsolutePath()=="") {
					System.out.println("Datei nicht erfolgreich!!!   " + fileList[i]);
				}
				else {
					fileList[i] = files.get(i);
					
					//Read all source code of the file and write it into a String				
					try {
						//Platform.getLocation() + "\\" + fileList[i].getName()
						BufferedReader in = new BufferedReader(new FileReader(fileList[i]));
						while ((str = in.readLine()) != null) {
							allCode += str;
						}
						in.close();
					} 
					catch (IOException ioe) {
						System.err.println(ioe);
					}
					
	
					
				}
			}
				
			//Get all Annotations, write them into an array
			String contentsReplace = SampleAction.getLst(SampleAction.class);
			String[] contents = contentsReplace.split("-\\$\\$-");
			for (int i=0; i<contents.length; i++) {
				contents[i]=contents[i].replace("$-", "").replace("-$", "");
				//System.out.println(contents[i]);
			}
	
			
			for (int i=0; i<contents.length; i++) {
				
					String[] content = contents[i].split(";");
					String elementType = content[0]; 
						
	//				if (elementType.equals("root")) {
	//					root.setAttribute("id",fileName.replace(".xml", "") + ":" + System.currentTimeMillis()/1000);
	//					for (int k=1; k<content.length; k++) {
	//						root.setAttribute(content[k].split("\"")[0],content[k].split("\"")[1]);
	//					}
	//					root.addContent(panels);
	//				}
	//				else if (elementType.equals("listpanel")) {
	//					for (int k=1; k<content.length; k++) {
	//						listPanel.setAttribute(content[k].split("\"")[0],content[k].split("\"")[1]);
	//					}
	//				}
					if (elementType.equals("list")) {
						Element list = new Element("list");
						for (int k=1; k<content.length; k++) {
							Element listChild = new Element(content[k].split("\"")[0]);
							listChild.addContent(content[k].split("\"")[1]);
							list.addContent(listChild);
						}
						listPanel.addContent(list);
					}
					else if (elementType.equals("panel_element")) {
						Element element = new Element("element");
						for (int k=1; k<content.length; k++) {
							if (content[k].split("\"")[0].equals("id")) {
								element.setAttribute("id",content[k].split("\"")[1] + ":" + UUID.randomUUID().toString());
							}
							else if (!content[k].split("\"")[1].equals("")) {
								Element panel_elementChild = new Element(content[k].split("\"")[0]);
								panel_elementChild.addContent(content[k].split("\"")[1]);
								element.addContent(panel_elementChild);
							}
						}
						panel.addContent(element);
					}
					else if (elementType.equals("list_element")) {
						Element element = new Element("element");
						for (int k=1; k<content.length; k++) {
							if (content[k].split("\"")[0].equals("id")) {
								element.setAttribute("id",content[k].split("\"")[1] + ":" + UUID.randomUUID().toString());
							}
							else if (!(content[k].split("\"")[0].equals("domain") && content[k].split("\"")[1].replace(" ", "").equals(""))) {
								Element panel_elementChild = new Element(content[k].split("\"")[0]);
								panel_elementChild.addContent(content[k].split("\"")[1]);
								element.addContent(panel_elementChild);
							}
						}
						listPanel.addContent(element);
					}
					else if (elementType.equals("panel")) {
						for (int k=1; k<content.length; k++) {
							panel.setAttribute(content[k].split("\"")[0],content[k].split("\"")[1]);
						}
					}
				/*}*/
			}
		
			
			
			Document configurationDoc = new Document(root);
			//File f = new File(Platform.getLocation().toFile().toURI());
			
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setIndent("	").setExpandEmptyElements(true));
			
			try {  
			    outputter.output(configurationDoc, System.out);
			} 
			catch (java.io.IOException ioe) {
			    ioe.printStackTrace();
			}
			
			try {
				FileWriter writer = new FileWriter(/*dirName + "\\" + */fileName);
				outputter.output(configurationDoc, writer);
				writer.close();
			}
			catch (IOException ioe) {
				System.out.print(ioe);
			}
		}
		else {
			System.out.println("No Files with java extension");
		}
	}
	
	@ElementL(id="notify_mms",type=ElementL.Type.CHECKBOX,label="Fliegen",title="Benachrichtigungstyp (MMS)",standardvalue="unchecked")
	public static String getLst(Class<?> clazz) {
		String allLst = new String();
		for (Method method : clazz.getMethods()) {
			if (method.getAnnotation(ElementL.class) != null) {
				ElementL value = method.getAnnotation(ElementL.class);
				allLst += "$-list_element;id\"" + value.id() + "\";type\"" + value.type() + "\";label\"" + value.label() + "\";title\"" + value.title() + "\";standardvalue\"" + value.standardvalue() + "\";domain\"" + value.domain() + "\"-$";
			}
			else if (method.getAnnotation(ElementP.class) != null) {
				ElementP value = method.getAnnotation(ElementP.class);
				allLst += "$-panel_element;id\"" + value.id() + "\";type\"" + value.type() + "\";label\"" + value.label() + "\";title\"" + value.title() + "\";standardvalue\"" + value.standardvalue() + "\";domain\"" + value.domain() + "\"-$";
			}
			else if (method.getAnnotation(LstPanel.class) != null) {
				LstPanel value = method.getAnnotation(LstPanel.class);
				allLst += "$-listpanel;id\"" + value.id() + "\";title\"" + value.title() + "\"-$";
			}
			else if (method.getAnnotation(Lst.class) != null) {
				Lst value = method.getAnnotation(Lst.class);
				allLst += "$-list;label\"" + value.label() + "\";title\"" + value.title() + "\";limit\"" + value.limit() + "\";domain\"" + value.domain() + "\"-$";
			}
			else if (method.getAnnotation(Root.class) != null) {
				Root value = method.getAnnotation(Root.class);
				allLst += "$-root;name\"" + value.name() + "\";title\"" + value.title() + "\";info\"" + value.info() + "\"-$";
			}

		}
		return allLst;
	}
	


	/**
	 * getFiles searches for all files in a Directory and in all SubDirectories,
	 * writing the one with the correct extension into a ArrayList
	 * @param baseDir Root Directory to start recursive search
	 * @param extension Defines the extension of the added files
	 * @return Returns all files of a selected Root Directory and all 
	 * SubDirectories, which have a specified extension
	 */
	
	
	@ElementP(id="notificationTimeout",type=ElementP.Type.TEXTBOX,label="Timeout bis zur Benachrichtigung:",title="Timeout bis zur Benachrichtigung",standardvalue="42",domain="AN")
	public static ArrayList<File> getFiles(String baseDir, String extension, HashMap<String,String> hm)
	  {
	    final ArrayList<File> files = new ArrayList<File>( 1024 );
	    final Stack<File> dirs = new Stack<File>();
	    final File startdir = new File(baseDir);
	    if ( startdir.isDirectory() && hm.get("subdir").equals("yes")) {
	    	System.out.println("Startdir: " + hm.get("subdir"));
	    	dirs.push( startdir );
	    }
	    while (dirs.size() > 0) {
	    	for (File file : dirs.pop().listFiles()) {
	    		if (file.isDirectory()  && hm.get("subdir").equals("yes")) {
	    			System.out.println("File Directory: " + hm.get("subdir"));
	    			dirs.push( file );
	    		}
	    		else if (file.canRead()) {
	    			files.add( file );
	    		}
	    	}
	    }
	    
	    for (int i=0; i<files.size(); i++) {
	    	if (!files.get(i).getName().endsWith(extension)) {
	    		files.remove(i);
	    		System.out.println("Removed: " + files.get(i).getName());
	    	}
	    }
	    return files;
	  }
	

		
	
	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	@ElementL(id="deviceSate",type=ElementL.Type.DROPDOWNLIST,label="Device Status:",title="Device Status",domain="http://www.openaal.org/SAM/Ontology/highLevelThing#Device-State")
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	@LstPanel(id="doors",title="Geräte")
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	@ElementP(id="tempControl",type=ElementP.Type.RADIOBUTTONGROUP,label="Temperaturkontrolle",title="Temperaturkontrolle",standardvalue="AUS",domain="AN|AUS")
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
	
public static JPanel getFirstPage(HashMap<String, String> hm, ActionListener listener) {
		
		JPanel page = new JPanel();
		page.setLayout(new BorderLayout(50,0));
      
		page.add(BorderLayout.WEST,getMenu(0));
		
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill=GridBagConstraints.HORIZONTAL;

		content.add(new JLabel("<html><br><font size=4>Welcome at Configuration-Extractor V0.9</font><br><br>This Wizard helps you to extract Annotations from your sourcecode at runtime and saves them as a XML configuration-file.<br><br>Please follow the steps of this Wizard to generate the XML file.</html>"),gbc);
		
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		content.add(new JPanel(),gbc);
		
		page.add(BorderLayout.CENTER,content);
      	page.add(BorderLayout.SOUTH,getButtons(hm,listener,false,true,false));
      
      	return page;
  	
	}
  
	public static JPanel getSecondPage(final HashMap<String, String> hm, ActionListener listener) {
		
		JPanel page = new JPanel();
		page.setLayout(new BorderLayout(50,0));
		
		page.add(BorderLayout.WEST,getMenu(1));
		
		JPanel formular = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor=GridBagConstraints.NORTHWEST;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=2;
		gbc.gridwidth=4;
		formular.add(new JLabel("<html><br>Please select the directory, which contains your sourcecode.<br>Choose if the Wizard either search trough subdirs or not.<br>Select the destination (including filename) of the XML-file, too.<br><br></html>"),gbc);
		
		gbc.gridwidth=1;
		gbc.gridy++;
		gbc.gridx=0;
      
		formular.add(new JLabel("Source Directory"),gbc);
      
		gbc.gridy++;
		gbc.gridx=0;
		gbc.gridwidth=2;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		final JTextField fieldSourceDir = new JTextField();
		fieldSourceDir.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				hm.put("fieldSourceDir", fieldSourceDir.getText());			
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				hm.put("fieldSourceDir", fieldSourceDir.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				hm.put("fieldSourceDir", fieldSourceDir.getText());		
			}
		});
      
		formular.add(fieldSourceDir,gbc);
		gbc.fill=GridBagConstraints.NONE;
		gbc.gridwidth=1;
		gbc.gridx+=3;
		JButton sourceButton = new JButton("Choose...");
		sourceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(""));
				chooser.setDialogTitle("Ordner öffnen");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    chooser.showOpenDialog(chooser);
			    try {
			    	fieldSourceDir.setText(chooser.getSelectedFile().getAbsolutePath());
			    }
			    catch (NullPointerException npe) {
			    	//Do nothing
			    }
			}		
		});
		formular.add(sourceButton,gbc);
		gbc.gridx=0;
		gbc.gridy++;
		gbc.gridwidth=1;
		JCheckBox subdir = new JCheckBox("Search in Subdirectories");
		subdir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				JCheckBox box = (JCheckBox)evt.getSource();
				if (box.isSelected()) {
					hm.put("subdir", "yes");
				}
				else {
					hm.put("subdir", "no");
				}
			}
		});
		formular.add(subdir,gbc);
		
		gbc.gridwidth=1;
		gbc.gridy++;
		gbc.gridx=0;
		formular.add(new JLabel("<html><br></html>"),gbc);
		gbc.gridy++;
	
		formular.add(new JLabel("Destination File"),gbc);
		gbc.gridy++;
		gbc.gridx=0;
		gbc.gridwidth=2;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		final JTextField fieldTargetDir = new JTextField();
		fieldTargetDir.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				hm.put("fieldTargetDir", fieldTargetDir.getText());			
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				hm.put("fieldTargetDir", fieldTargetDir.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				hm.put("fieldTargetDir", fieldTargetDir.getText());		
			}
	      });
		formular.add(fieldTargetDir,gbc);
		gbc.fill=GridBagConstraints.NONE;
		gbc.gridwidth=1;
		gbc.gridx+=3;
		JButton targetButton = new JButton("Choose...");
		targetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser saver = new JFileChooser();
			    saver.setCurrentDirectory(new java.io.File("C:\\Users\\Alexander\\Desktop"));
			    saver.setDialogTitle("XML Konfigurationsdatei speichern unter");
			    saver.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    saver.setFileFilter(new FileNameExtensionFilter("XML", new String[] {"xml"}));
			    saver.showOpenDialog(saver);
			    try {
			    	fieldTargetDir.setText(saver.getCurrentDirectory().getAbsolutePath() + "\\" +  saver.getSelectedFile().getName());
			    }
			    catch (NullPointerException npe) {
			    	//Do nothing
			    }
			}
		});
		formular.add(targetButton,gbc);
		
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		formular.add(new JPanel(),gbc);
		
		page.add(BorderLayout.CENTER,formular);
		page.add(BorderLayout.SOUTH,getButtons(hm,listener,true,true,false));
		return page;
  	
	}
  
	public static JPanel getThirdPage(final HashMap<String, String> hm, ActionListener listener) {
	  
		JPanel page = new JPanel();
		page.setLayout(new BorderLayout(50,0));
		
		page.add(BorderLayout.WEST,getMenu(2));
		
		JPanel formular = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.ipadx=5;
		gbc.fill=GridBagConstraints.HORIZONTAL;
            
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=3;
		formular.add(new JLabel("<html><br>Please specify the attributes of root-element.<br><br>&lt;root id=\"filename\" name=\"\" title=\"\" info=\"\"&gt;<br>&lt;/root&gt;<br><br><br></html>"),gbc);
		
		gbc.insets=new Insets(0,0,0,10);
		gbc.gridx=0;
		gbc.gridy++;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(new JLabel("Name: "));
		final JTextField rootName = new JTextField();
		rootName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				hm.put("rootName", rootName.getText());			
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				hm.put("rootName", rootName.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				hm.put("rootName", rootName.getText());		
			}
	      });
      	box.add(rootName);
      	formular.add(box,gbc);
      
      	gbc.gridx=0;
      	gbc.gridy++;
      	gbc.fill=GridBagConstraints.HORIZONTAL;
      	box = new JPanel();
      	box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
      	box.add(new JLabel("Title:   "));
      	final JTextField rootTitle = new JTextField();
      	rootTitle.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				hm.put("rootTitle", rootTitle.getText());			
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				hm.put("rootTitle", rootTitle.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				hm.put("rootTitle", rootTitle.getText());		
			}
	      });
      	box.add(rootTitle);
      	formular.add(box,gbc);

      	gbc.gridx=0;
      	gbc.gridy++;
      	gbc.fill=GridBagConstraints.HORIZONTAL;
      	box = new JPanel();
      	box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
      	box.add(new JLabel("Info:   "));
      	final JTextField rootInfo = new JTextField();
      	rootInfo.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				hm.put("rootInfo", rootInfo.getText());			
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				hm.put("rootInfo", rootInfo.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				hm.put("rootInfo", rootInfo.getText());		
			}
	      });
      	box.add(rootInfo);
      	formular.add(box,gbc);
      	
      	gbc.weightx=1.0;
      	gbc.weighty=1.0;
      	formular.add(new JPanel(),gbc);
      	
      	page.add(BorderLayout.CENTER,formular);
		page.add(BorderLayout.SOUTH,getButtons(hm,listener,true,true,false));
      
      	return page;
      
	}
  
	public static JPanel getFourthPage(final HashMap<String, String> hm, ActionListener listener) {
	  
		JPanel page = new JPanel();
		page.setLayout(new BorderLayout(50,0));
		
		page.add(BorderLayout.WEST,getMenu(3));
		
		JPanel formular = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor=GridBagConstraints.NORTHWEST;
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.ipadx=5;
		
		gbc.gridwidth=4;
		formular.add(new JLabel("<html><br>Please specify the attributes of  listpanel-element.<br><br>&lt;listpanel doors=\"\" title=\"\"&gt;<br>&lt;/listpanel&gt;<br><br><br></html>"),gbc);

		gbc.insets=new Insets(0,0,0,10);
		gbc.gridx=0;
		gbc.gridy++;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(new JLabel("Doors: "));
		final JTextField listPanelDoors = new JTextField();
		listPanelDoors.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				hm.put("listPanelDoors", listPanelDoors.getText());			
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				hm.put("listPanelDoors", listPanelDoors.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				hm.put("listPanelDoors", listPanelDoors.getText());		
			}
	      });
		box.add(listPanelDoors);
		formular.add(box,gbc);
      
		gbc.gridx=0;
		gbc.gridy++;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(new JLabel("Title:   "));
		final JTextField listPanelTitle = new JTextField();
		listPanelTitle.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				hm.put("listPanelTitle", listPanelTitle.getText());			
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				hm.put("listPanelTitle", listPanelTitle.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				hm.put("listPanelTitle", listPanelTitle.getText());		
			}
	      });
		box.add(listPanelTitle);
		formular.add(box,gbc);
      
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		formular.add(new JPanel(),gbc);
		
		page.add(BorderLayout.CENTER,formular);
		page.add(BorderLayout.SOUTH,getButtons(hm,listener,true,true,false));
      
		return page;

	  
	}
  
	public static JPanel getFifthPage(HashMap<String, String> hm, ActionListener listener) {
	  
		JPanel page = new JPanel();
		page.setLayout(new BorderLayout(50,0));
		
		page.add(BorderLayout.WEST,getMenu(4));
		
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor=GridBagConstraints.NORTHWEST;
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=0;
		
     	content.add(new JLabel("<html><br>The configuartion file will be created, as soon as you click on the Finish Button...</font></html>"),gbc);

     	gbc.weightx=1.0;
     	gbc.weighty=1.0;
     	content.add(new JPanel(),gbc);
           	
     	page.add(BorderLayout.CENTER,content);
		page.add(BorderLayout.SOUTH,getButtons(hm,listener,false,false,true));
      
     	return page;
	  
	}
  
public static JPanel getMenu(int index) {
		
		JLabel entryZero;
		JLabel entryOne;
		JLabel entryTwo;
		JLabel entryThree;
		JLabel entryFour;
		
		JPanel menu = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (index == 0) {
			entryZero = new JLabel("<html>&nbsp;&nbsp;<b>1. Welcome</b></html>");
		}
		else {
			entryZero = new JLabel("<html>&nbsp;&nbsp;1. Welcome</html>");
		}
		
		if (index == 1) {
			entryOne = new JLabel("<html>&nbsp;&nbsp;<b>2. Source- and Target-Dir</b></html>");
		}
		else {
			entryOne = new JLabel("<html>&nbsp;&nbsp;2. Source- and Target-Dir</html>");
		}
		
		if (index == 2) {
			entryTwo = new JLabel("<html>&nbsp;&nbsp;<b>3. Define Root-Element</b></html>");
		}
		else {
			entryTwo = new JLabel("<html>&nbsp;&nbsp;3. Define Root-Element</html>");
		}
		
		if (index == 3) {
			entryThree = new JLabel("<html>&nbsp;&nbsp;<b>4. Define ListPanel</b></html>");
		}
		else {
			entryThree = new JLabel("<html>&nbsp;&nbsp;4. Define ListPanel</html>");
		}
		
		if (index == 4) {
			entryFour = new JLabel("<html>&nbsp;&nbsp;<b>5. Create Configuration-File</b></html>");
		}
		else {
			entryFour = new JLabel("<html>&nbsp;&nbsp;5. Create Configuration-File</html>");
		}

		gbc.gridx=0;
		gbc.gridy=0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		menu.add(new JLabel(" "),gbc);
		gbc.gridy++;
		menu.add(entryZero,gbc);
		gbc.gridy++;
		menu.add(entryOne,gbc);
		gbc.gridy++;
		menu.add(entryTwo,gbc);
		gbc.gridy++;
		menu.add(entryThree,gbc);
		gbc.gridy++;
		menu.add(entryFour,gbc);
		gbc.weighty=1.0;
		menu.add(new JLabel(" "),gbc);
		
		return menu;
	}
  
	public static JPanel getButtons(final HashMap hm,ActionListener listener, boolean bBack, boolean bFurther, boolean bFinish) {
	 
		
		JButton back = new JButton("< Back");
		back.setEnabled(bBack);
		back.addActionListener(listener);
		JButton further = new JButton("Next >");
		further.setEnabled(bFurther);
		further.addActionListener(listener);
		JButton finish = new JButton("Finish");
		finish.setEnabled(bFinish);
		finish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				exportXML(hm);
				System.exit(0);
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.setEnabled(!bFinish);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});
      		
		JPanel box = new JPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		
		box.add(new JLabel("<html><div align=center>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html>"));
		box.add(back);
		box.add(new JLabel("  "));
		box.add(further);
		box.add(new JLabel("  "));
		box.add(finish);
		box.add(new JLabel("  "));
		box.add(cancel);
		box.add(new JLabel("<html></div><br><br><br><br></html>"));
	  
		return box;
	
	}
	
}