package configuration_extractor.actions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
//import org.eclipse.jface.dialogs.MessageDialog;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.SWT;


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

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		
		//File Dialog: "Datei speichern unter"
		FileDialog fd = new FileDialog(window.getShell(),SWT.SAVE);
		fd.setText("XML-Konfiguration speichern unter");
		String[] filterText = {"*.xml"};
		fd.setFilterExtensions(filterText);
		fd.setFilterPath("C:/");
		String selected = fd.open();
		System.out.println(selected);
		
		String dirName = fd.getFilterPath();
		String fileName = fd.getFileName();
				
		
		/*
		 * String fileName = fd.getFileName();
		 * $-
		 * root;
		 * name:Vergessene Geräte;
		 * title:Einstellungen vergessene Geräte;
		 * info:Hier können sie ihre zu überwachenden Geräte konfigurieren
		 * -$
		 * 
		 * System.out.println(selected);
		 * 
		 * $-
		 * listpanel;
		 * id:doors;
		 * title:Geräte
		 * -$
		 * 
		 * @param: testParam Describes something, doesn't matter what
		 * 
		 * $-
		 * list;
		 * label:Geräte;
		 * title:Geräte auswählen;
		 * limit:-1;
		 * domain:www.domain.tld
		 * -$
		 * 
		 * fsdsdfsdf @ Sinnloser Kommentar
		 * 
		 * 
		 * $-
		 * element;
		 * id:label1;
		 * type:LABEL;
		 * label:Geräte Konfiguration;
		 * title:Geräte Konfiguration;
		 * standardValue:123"
		 * -$
		 * 
		 * 
		 * $-
		 * element;
		 * id:label1;
		 * type:LABEL;
		 * label:Geräte Konfiguration;
		 * title:Geräte Konfiguration;
		 * standardValue:
		 * -$
		 * 
		 * $-panel;title:Einstellungen;-$
		 * 
		 * $-panel_element;
		 * id:notificationTimeout;
		 * type:TEXTBOX;
		 * label:Timeout bis zur Benachrichtigung
		 * title:Timeout bis zur Benachrichtigung;
		 * standardvalue:42
		 * -$
		 */
		
		
		Element root = new Element("root");
		Element panels = new Element("panels");
		Element listPanel = new Element("listpanel");
		Element panel = new Element("panel");
		
		panels.addContent(listPanel);
		panels.addContent(panel);
		
		
		String allCode = "/* " +
		 "* $- "+
		 "* root;" +
		 "* name:Vergessene Geräte; "+
		 "* title:Einstellungen vergessene Geräte; "+
		 "* info:Hier können sie ihre zu überwachenden Geräte konfigurieren "+
		 "* -$"+
		 "* "+
		 "* callMeAFunction(String test) is a function that causes a commentary"+
		 "* $-"+
		 "* listpanel;"+
		 "* id:doors;"+
		 "* title:Geräte"+
		 "* -$"+
		 "* "+
		 "@param: Ojidoifoifsd jks fjofs dij hsfo jsofd" +
		 "* $-"+
		 "* list;"+
		 "* label:Geräte;"+
		 "* title:Geräte auswählen;"+
		 "* limit:-1;"+
		 "* domain:www.domain.tld"+
		 "* -$"+ 
		 "* "+
		 "* fsdsdfsdf @"+ 
		 "* "+
		 "* $-"+
		 "* element;"+
		 "* id:label1;"+
		 "* type:LABEL;"+
		 "* label:Geräte Konfiguration;"+
		 "* title:Geräte Konfiguration;"+
		 "* standardValue:123"+
		 "* -$"+
		 "* "+
		 "* "+
		 "* $-"+
		 "* element;"+
		 "* id:label1;"+
		 "* type:LABEL;"+
		 "* label:Geräte Konfiguration;"+
		 "* title:Geräte Konfiguration;"+
		 "* standardValue:"+
		 "* -$"+
		 "$-panel;title:Einstellungen;-$"+
		 "$-panel_element;"+
		 "id:notificationTimeout;"+
		 "type:TEXTBOX;"+
		 "label:Timeout bis zur Benachrichtigung;"+
		 "title:Timeout bis zur Benachrichtigung;"+
		 "standardvalue:42-$"+
		 "*/";
		
		
		String contentsReplace = allCode.replaceAll("([\\/][\\*]){1}([\\*][\\/]){1}", "").replaceAll("[ ]?[\\*]{1}[ ]?", "").replaceAll("([\\-][\\$]){1}[a-zA-Z 0-9\\@\\:\\-\\(\\)]+([\\$][\\-]){1}", "\\-\\$\\$\\-").replaceAll("([\\/][\\$][\\-]){1}", "").replaceAll("([\\-][\\$][\\/]){1}", "");
		
		String[] contents = contentsReplace.split("\\-\\$\\$\\-");
		
		
		for (int k=0; k<contents.length; k++) {
				
				if (!contents[k].equals(null) && !contents[k].equals("")) {
				
					String[] content = contents[k].split(";");
						
					String elementType = content[0];
						
					if (elementType.equals("root")) {
						for (int i=1; i<content.length; i++) {
							if (i == 1) 
								root.setAttribute("id",fileName.replace(".xml", ""));
							root.setAttribute(content[i].split(":")[0],content[i].split(":")[1]);
						}
						root.addContent(panels);
					}
					else if (elementType.equals("listpanel")) {
						for (int i=1; i<content.length; i++) {
							listPanel.setAttribute(content[i].split(":")[0],content[i].split(":")[1]);
						}
						
					}
					else if (elementType.equals("list")) {
						Element list = new Element("list");
						for (int i=1; i<content.length; i++) {
							Element listChild = new Element(content[i].split(":")[0]);
							listChild.addContent(content[i].split(":")[1]);
							list.addContent(listChild);
						}
						listPanel.addContent(list);
					}
					else if (elementType.equals("panel_element")) {
						Element panel_element = new Element("element");
						for (int i=1; i<content.length; i++) {
							if (content[i].split(":")[0].equals("id")) {
								panel_element.setAttribute("id",content[i].split(":")[1]);
							}
							else {
								Element panel_elementChild = new Element(content[i].split(":")[0]);
								panel_elementChild.addContent(content[i].split(":")[1]);
								panel_element.addContent(panel_elementChild);
							}
						}
						panel.addContent(panel_element);
						
					}
					
					else if (elementType.equals("panel")) {
						for (int i=1; i<content.length; i++) {
							panel.setAttribute(content[i].split(":")[0],content[i].split(":")[1]);
						}
						
					}
					
				}
		}
		

		
		Document configurationDoc = new Document(root);
		//File f = new File(Platform.getLocation().toFile().toURI());


		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setIndent("	").setExpandEmptyElements(true));
		
		try {
		    
		    outputter.output(configurationDoc, System.out);
		} 
		catch (java.io.IOException e) {
		    e.printStackTrace();
		}
		
		try {
			FileWriter writer = new FileWriter(dirName + "\\" + fileName);
			//writer.
			outputter.output(configurationDoc, writer);
			writer.close();
		}
		catch (IOException i) {
			System.out.print(i);
		}
		
	}
	

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}