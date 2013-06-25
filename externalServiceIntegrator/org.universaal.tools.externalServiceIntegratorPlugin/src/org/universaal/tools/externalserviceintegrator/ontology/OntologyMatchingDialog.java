package org.universaal.tools.externalserviceintegrator.ontology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.universAAL.ri.wsdlToolkit.ioApi.ComplexObject;
import org.universAAL.ri.wsdlToolkit.ioApi.NativeObject;
import org.universAAL.ri.wsdlToolkit.ioApi.WSOperation;

import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class OntologyMatchingDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Map<NativeObject,ArrayList<OntologyMapping>> map;
	private WSOperation selectedOperation;
	private Table table;
	private Composite composite_3;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public OntologyMatchingDialog(Shell parent, int style,Map<NativeObject,ArrayList<OntologyMapping>> map,WSOperation selectedOperation) {
		super(parent, style);
		setText("Ontology Mappings");
		this.map=map;
		this.selectedOperation=selectedOperation;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE);
		shell.setSize(803, 537);
		shell.setText(getText());
		shell.setLayout(new BorderLayout(0, 0));
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		
		final Tree tree = new Tree(composite, SWT.BORDER);
		
		
		TreeItem inputs = new TreeItem(tree, 0);
		inputs.setText("Inputs");
		inputs.setExpanded(true);
		calculateChildren(inputs,selectedOperation.getHasInput().getHasNativeOrComplexObjects());
		TreeItem outputs = new TreeItem(tree, 0);
		outputs.setText("Outputs");
		outputs.setExpanded(true);
		calculateChildren(outputs,selectedOperation.getHasOutput().getHasNativeOrComplexObjects());
		
		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new BorderLayout(0, 0));
		
		final Label lblNewLabel = new Label(composite_2, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 12, SWT.BOLD));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setLayoutData(BorderLayout.NORTH);
		
		  composite_3 = new Composite(composite_2, SWT.NONE);
		composite_3.setLayoutData(BorderLayout.CENTER);
		composite_3.setLayout(new BorderLayout(0, 0));
		
		Composite composite_4 = new Composite(composite_3, SWT.NONE);
		composite_4.setLayoutData(BorderLayout.NORTH);
		
		
		
		
		
		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event e) {
				
				TreeItem[] selection = tree.getSelection();
				
				if (selection[0] != null) {
					if (selection[0].getData() instanceof NativeObject) {
						lblNewLabel.setText("Mappings for: "+((NativeObject)selection[0].getData()).getObjectName().getLocalPart());
						//sort mappings
						sortMappings((NativeObject)selection[0].getData(),map);
						composite_3.setVisible(true);
						
					} else {
						lblNewLabel.setText("Select a leaf node in the tree to view its mapping");
						composite_3.setVisible(false);
					}
				}
			}
		});
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.NORTH);
	}
	
	
	private void sortMappings(NativeObject no, Map<NativeObject,ArrayList<OntologyMapping>> map){
		ArrayList<OntologyMapping> mappings=map.get(no);
		Collections.sort(mappings);
		
		createMappingsTable();
	}
	
	private void createMappingsTable(){
		table = new Table(composite_3, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(BorderLayout.CENTER);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn tc1 = new TableColumn(table, SWT.CENTER);
	    TableColumn tc2 = new TableColumn(table, SWT.CENTER);
	    TableColumn tc3 = new TableColumn(table, SWT.CENTER);
	    tc1.setText("Ontology URI");
	    tc2.setText("Matching score");
	    tc3.setText("Ontology file");
	    TableItem item1 = new TableItem(table, SWT.NONE);
	    item1.setText(new String[] { "Tim", "Hatton", "Kentucky" });
	    table.redraw();
	    composite_3.redraw();
	}
	
	private void calculateMappingsTree(Tree tree){
		
	}
	
	
	
	
	
	private void calculateChildren(TreeItem treeItem, Vector vec){
		for(int i=0;i<vec.size();i++){
			if(vec.get(i) instanceof NativeObject){
				NativeObject no=(NativeObject)vec.get(i);
				TreeItem item=new TreeItem(treeItem, 0);
				item.setText(no.getObjectName().getLocalPart());
				item.setData(no);
				item.setExpanded(true);
			}
			else
				if(vec.get(i) instanceof ComplexObject){
					ComplexObject co=(ComplexObject)vec.get(i);
					TreeItem item=new TreeItem(treeItem, 0);
					item.setText(co.getObjectName().getLocalPart());
					item.setExpanded(true);
					item.setData(co);
					calculateChildren(item,co.getHasComplexObjects());
					calculateChildren(item,co.getHasNativeObjects());
				}
		}		
	}
	
	
	
	
	
	
}
