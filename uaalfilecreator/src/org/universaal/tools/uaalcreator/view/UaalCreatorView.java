package org.universaal.tools.uaalcreator.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.part.ViewPart;

/**
 * UaalCreatorView is the ViewPart of the uaal File creator Plugin
 */
public class UaalCreatorView extends ViewPart {

	public static final String ID = "org.universaal.tools.uaalcreator.UaalCreatorView";
	
//	private static final String NO_CONFIGURATION_SPECIFIED = "(none selected yet)";
	private static final int BUFFER = 2048;
	
	
	private Label labConfig;
	private List listBundles;
	
	private Composite parentComposite;
	
	
	public UaalCreatorView() {
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		this.parentComposite = parent;
		
		GridLayout grid = new GridLayout();
		grid.marginHeight = 0;
		grid.marginWidth = 0;
		parent.setLayout(grid);
		
		
		Label lab = new Label(parent, SWT.NONE);
		lab.setText("1. Please select the required bundles here:");
		
		listBundles = new List(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		
		GridData d = new GridData();
		d.widthHint = 250;
		d.heightHint = 100;
		listBundles.setLayoutData(d);
	    
	    Button addBundleBtn = new Button(parent, SWT.NONE);
	    addBundleBtn.setText("Add a required file");
	    addBundleBtn.addListener(SWT.Selection, new Listener() {
			@Override public void handleEvent(Event event) {
		        FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		        fd.setText("Add a required fle");
		        fd.setFilterExtensions(new String[] {"*.*"});
		        String filepath = fd.open();
		        
		        if (filepath != null) {
		        	listBundles.add(filepath);
		        }
			}
        });

	    
	    
		lab = new Label(parent, SWT.NONE);
		lab.setText("2. Create the *.uaal file:");
	
		

/*		lab = new Label(parent, SWT.NONE);
		lab.setText("Please select the configuration ontology here:");

		labConfig = new Label(parent, SWT.FILL);
		labConfig.setText(NO_CONFIGURATION_SPECIFIED);

	    Button addConfigBtn = new Button(parent, SWT.NONE);
	    addConfigBtn.setText("Add the configuration ontology");
	    addConfigBtn.addListener(SWT.Selection, new Listener() {
			@Override public void handleEvent(Event event) {
		        FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		        fd.setText("Set the configuration ontology");
		        fd.setFilterExtensions(new String[] {"*.owl"});
		        String filepath = fd.open();
		        
		        if (filepath != null) {
		        	labConfig.setText(filepath);
		        	parentComposite.layout();
		        }
			}
        });
*/

	    
	    
		lab = new Label(parent, SWT.NONE);
		
		

	    Button createUaalFileBtn = new Button(parent, SWT.NONE);
	    createUaalFileBtn.setText("Create file");
	    createUaalFileBtn.addListener(SWT.Selection, new Listener() {
			@Override public void handleEvent(Event event) {
				if (listBundles.getItemCount() <= 0) {
					MessageBox messageDialog = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
					messageDialog.setText("Cannot create *.uaal file");
					messageDialog.setMessage("Please specify the required files\n" +
							"before creating the *.uaal file!");
					messageDialog.open();
					return;
				}
/*				if (labConfig.getText().equals(NO_CONFIGURATION_SPECIFIED)) {
					MessageBox messageDialog = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
					messageDialog.setText("Cannot create *.uaal file");
					messageDialog.setMessage("Please specify the configuration ontology\n" +
							"before creating the *.uaal file!");
					messageDialog.open();
					return;
				}
*/				
		        FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		        fd.setText("Specify the filename of the *.uaal file");
		        fd.setFilterExtensions(new String[] {"*.uaal"});
		        String filepath = fd.open();
		        
		        if (filepath != null) {
		        	saveUaalFile(filepath);
		        }
			}
        });
				
	}
	
	/**
	 * create a *.uaal file (zip file) containing plugins and the configuration ontology
	 * 
	 * influenced by http://java.sun.com/developer/technicalArticles/Programming/compression/
	 */
	private void saveUaalFile(String filepath) {
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new 
			FileOutputStream(filepath);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			byte data[] = new byte[BUFFER];
			File f;

			for (int i=0; i < listBundles.getItemCount(); i++) {
				System.out.println("Adding: " + listBundles.getItem(i));
				FileInputStream fi = new FileInputStream(listBundles.getItem(i));
				origin = new BufferedInputStream(fi, BUFFER);
				f = new File(listBundles.getItem(i));
				ZipEntry entry = new ZipEntry(f.getName());
				out.putNextEntry(entry);
				int count;
				while((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}

/*			System.out.println("Adding: " + labConfig.getText());
			FileInputStream fi = new FileInputStream(labConfig.getText());
			origin = new BufferedInputStream(fi, BUFFER);
			f = new File(labConfig.getText());
			ZipEntry entry = new ZipEntry(f.getName());
			out.putNextEntry(entry);
			int count;
			while((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}			
			origin.close();
*/			
			out.close();
			System.out.println("file saved: " + filepath);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
