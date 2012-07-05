package org.universaal.tools.configurationExtractor.view;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.universaal.tools.configurationExtractor.CommentsExtractor;
import org.universaal.tools.configurationExtractor.ConfigChecker;
import org.universaal.tools.configurationExtractor.data.ConfigItem;
import org.universaal.tools.configurationExtractor.data.GeneralUCConfig;
import org.universaal.tools.configurationExtractor.data.ItemType;
import org.universaal.tools.configurationExtractor.ontology.OntologyCreator;
import org.universaal.tools.configurationExtractor.ontology.OntologyReader;

/**
 * CEView is the ViewPart of the Configuration Extractor Plugin
 */
public class CEView extends ViewPart {

	public static final String ID = "org.universaal.tools.configurationExtractor.CEView";

	/**
	 * indicates if currently the expert mode is shown or not
	 */
	private boolean expertModeIsShown = false;
	// images for the switch mode button
	private final Image expertImage = ResourceManager.getPluginImage("org.universaal.tools.configurationExtractor", "icons/expertModeIcon.png");
	private final Image normalImage = ResourceManager.getPluginImage("org.universaal.tools.configurationExtractor", "icons/normalModeIcon.png");

	/**
	 * indicates if a project is already open
	 */
	private boolean projectIsOpen = false;
	
	private Tree tree;
	private ScrolledComposite rightSideContainer;
	private Map<Integer, ConfigItem> itemMap = new HashMap<Integer, ConfigItem>();
	private Button openWorkspaceBtn, openFilesystemBtn, switchModeBtn, exportBtn, addPanelButton, deleteButton;
	private Text expertText;
	private SashForm divider;
	
	private OntologyCreator oc = new OntologyCreator();
	
	private int itemCount;
	
	private ItemDetails itemDetails;
	
	
	/**
	 * SelectionListener for the TreeItems to open the item details when selected
	 */
	private SelectionListener selctionListener = new SelectionListener() {
		@Override public void widgetDefaultSelected(SelectionEvent e) {}
		
		@Override public void widgetSelected(SelectionEvent e) {
			ConfigItem conf = itemMap.get(((Tree) e.getSource()).getSelection()[0].getData());
			if (conf != null) {
				itemDetails.showItemDetails(conf);
				if (conf instanceof GeneralUCConfig) {
					deleteButton.setEnabled(false);
				} else {
					deleteButton.setEnabled(true);
				}
			} else {
				System.err.println("no ConfigItem found for this TreeItem ...");
			}
		}
	};
	
	/**
	 * MouseListener for the TreeItems to use a double click for expanding the panel TreeItem
	 */
	private MouseListener mouseListener = new MouseListener() {
		@Override public void mouseDown(MouseEvent e) {}
		@Override public void mouseUp(MouseEvent e) {}
		
		@Override public void mouseDoubleClick(MouseEvent e) {
			TreeItem item = ((Tree) e.getSource()).getSelection()[0];
			ConfigItem conf = itemMap.get(item.getData());
			
			if (conf != null && (conf.getItemType() == ItemType.PANEL || conf.getItemType() == ItemType.ONTOLOGY_PANEL || conf instanceof GeneralUCConfig)) {
				((Tree) e.getSource()).getSelection()[0].setExpanded(! item.getExpanded());
			}
		}
	};
	
	/**
	 * KeyListener to use the DEL key to delete a TreeItem
	 */
	private KeyListener keyListener = new KeyListener() {
		@Override public void keyReleased(KeyEvent e) {}

		@Override public void keyPressed(KeyEvent e) {
			if (e.keyCode == SWT.DEL) {
				deleteSelectedItem();
			}
		}
	};
	
	
	public CEView() {
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	/**
	 * Create contents of the CE view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		GridLayout grid = new GridLayout();
		grid.marginHeight = 0;
		grid.marginWidth = 0;
		parent.setLayout(grid); 
		
		// topComposite is located at the top and contains the Buttons
		Composite topComposite = new Composite(parent, SWT.TOP);
		RowLayout topLayout = new RowLayout();
		topLayout.wrap = true;
		topLayout.pack = false;
		topLayout.justify = false;
		topLayout.type = SWT.HORIZONTAL;
		topLayout.marginLeft = 5;
		topLayout.marginTop = 5;
		topLayout.marginRight = 5;
		topLayout.marginBottom = 1;
		topLayout.spacing = 5;
		topComposite.setLayout(topLayout);
		
		addTopButtons(topComposite);		
		
		
		// divider contains the tree at the left and the details composite at the right
	    divider = new SashForm(parent, SWT.HORIZONTAL | SWT.FILL /**/| SWT.BORDER/**/);
	    divider.SASH_WIDTH = 2;
	    divider.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
	    
	    GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
	    divider.setLayoutData(gridData);
	    
		tree = new Tree(divider, SWT.V_SCROLL | SWT.H_SCROLL| SWT.FILL);
		tree.addSelectionListener(selctionListener);
		tree.addMouseListener(mouseListener);
		tree.addKeyListener(keyListener);
		

		DragnDropHelper helper = new DragnDropHelper(this);
		helper.enableDragnDrop(tree);
		
		
		rightSideContainer = new ScrolledComposite(divider, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FILL);
		rightSideContainer.setExpandHorizontal(true);
		rightSideContainer.setExpandVertical(true);
		
		Composite comp = new Composite(rightSideContainer, SWT.FILL);
		rightSideContainer.setContent(comp);
		
		itemDetails = new ItemDetails(rightSideContainer, tree);
		
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		fillLayout.type = SWT.HORIZONTAL;
		
		comp.setLayout(fillLayout);
		
		Label l = new Label(comp, SWT.NONE);
		l.setText("Please use the buttons above to import a project!");
		
		
		expertText = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData g = new GridData(GridData.FILL_BOTH);
		g.exclude = true;
		expertText.setLayoutData(g);
		expertText.setVisible(false);
		

		rightSideContainer.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	/**
	 * add the buttons to the topComposite and configure their Listeners
	 * @param topComposite
	 */
	private void addTopButtons(Composite topComposite) {
		openWorkspaceBtn = new Button(topComposite, SWT.TOP);
        openWorkspaceBtn.setToolTipText("Import project from workspace");
        openWorkspaceBtn.setImage(ResourceManager.getPluginImage("org.universaal.tools.configurationExtractor", "icons/openFromWorkspaceIcon.png"));
        openWorkspaceBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (projectIsOpen) {
					MessageDialog messageDialog = new MessageDialog(null, "Confirmation", null,
					        "Do you really want to open a project?\n" +
					        "All unsaved changes will be lost!", MessageDialog.CONFIRM,
					        new String[] { "Yes", "No" }, 0);
					if (messageDialog.open() != 0) {
					    return;
					}
				}
				
				ListDialog dlg = new ListDialog(null);
				dlg.setTitle("Select a project");
				dlg.setMessage("Please select a project from the workspace listed below:");
				dlg.setContentProvider(new ArrayContentProvider());
				dlg.setInput(getWorkspaceProjects());
				dlg.setLabelProvider(new LabelProvider() {
					@Override public String getText(Object element) {
						return ((IProject) element).getName();
					}
				});
				
				if (dlg.open() == Dialog.OK) {
					IProject project = (IProject) dlg.getResult()[0];
					openProject(project.getLocation().toString());					
					
				} else {
					System.out.println("user cancelled project selection");
				}
			}
        });
        
		
		openFilesystemBtn = new Button(topComposite, SWT.TOP);
        openFilesystemBtn.setToolTipText("Import project from filesystem");
		openFilesystemBtn.setImage(ResourceManager.getPluginImage("org.universaal.tools.configurationExtractor", "icons/openFromDirectoryIcon.png"));
		openFilesystemBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (projectIsOpen) {
					MessageDialog messageDialog = new MessageDialog(null, "Confirmation", null,
					        "Do you really want to open a project?\n" +
					        "All unsaved changes will be lost!", MessageDialog.CONFIRM,
					        new String[] { "Yes", "No" }, 0);
					if (messageDialog.open() != 0) {
					    return;
					}
				}
				
				DirectoryDialog directoryDialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
				directoryDialog.setFilterPath("/");
				directoryDialog.setMessage("Please select a project directory:");
				directoryDialog.setText("Select a directory");
				String selectedDirectory = directoryDialog.open();
				if (selectedDirectory != null) {
					openProject(selectedDirectory);
					
				} else {
					System.out.println("no project was selected");
				}
			}
        });
        
		
		switchModeBtn = new Button(topComposite, SWT.TOP);
        switchModeBtn.setToolTipText("Switch expert / normal mode");
		switchModeBtn.setImage(expertImage);
		switchModeBtn.setEnabled(false);
		switchModeBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				itemDetails.saveConfig();

				if (! ConfigChecker.checkIfOk(itemMap, tree.getItems())) {
					return;
				}
				
				GridData gridZero = new GridData(0, 0);
				gridZero.exclude = true;
				GridData gridDefault = new GridData(GridData.FILL_BOTH);
				gridDefault.exclude = false;
				
				if (expertModeIsShown) { // show the normal tree mode
					LinkedList<ConfigItem> config = null;
					try {
						config = OntologyReader.getConfig(expertText.getText());
					} catch (Exception e) {
						e.printStackTrace();
		        		ConfigChecker.showError("Parsing the configuration ontology", 
		        				"The ontology could not be parsed:\n" + e.getMessage());
						return;
					}
					if (config != null) {
						openProject(config);						
					} else {
						MessageDialog messageDialog = new MessageDialog(Display.getCurrent().getActiveShell()
								, "Error", null, 
								"An error occured while parsing the OWL text\n" +
								"What do you want to do?", MessageDialog.ERROR,
								new String[] { "correct my changes", "discard my changes" }, 1);
						if (messageDialog.open() == 0) {
							return;
						}
					}
					
					((Button) event.widget).setImage(expertImage);
					expertText.setLayoutData(gridZero);
					divider.setLayoutData(gridDefault);
					
				} else { // show the expert mode
					((Button) event.widget).setImage(normalImage);
					divider.setLayoutData(gridZero);
					expertText.setLayoutData(gridDefault);
					try {
						expertText.setText(oc.getOntology(itemMap, tree.getItems()).toString());
					} catch (Exception e) {
						e.printStackTrace();
		        		ConfigChecker.showError("Creating the configuration ontology", 
		        				"The ontology could not be created:\n" + e.getMessage());
		        		
						((Button) event.widget).setImage(expertImage);
						expertText.setLayoutData(gridZero);
						divider.setLayoutData(gridDefault);
					}
				}
				
				expertModeIsShown = ! expertModeIsShown;
				
				expertText.setVisible(expertModeIsShown);
				divider.setVisible(! expertModeIsShown);

				expertText.getParent().layout();
			}
        });
        
		
		exportBtn = new Button(topComposite, SWT.TOP);
        exportBtn.setToolTipText("Export configuration");
        exportBtn.setEnabled(false);
		exportBtn.setImage(ResourceManager.getPluginImage("org.universaal.tools.configurationExtractor", "icons/exportIcon.png"));
		exportBtn.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				itemDetails.saveConfig();

				if (! ConfigChecker.checkIfOk(itemMap, tree.getItems())) {
					return;
				}
				
		        FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		        fd.setText("Export to an owl file");
		        fd.setFilterExtensions(new String[] {"*.owl"});
		        String filepath = fd.open();
		        
		        if (filepath != null) {
		        	try {
		        		oc.saveOntology(itemMap, tree.getItems(), filepath);
		        	} catch (Exception e) {
		        		e.printStackTrace();
		        		ConfigChecker.showError("Exporting the configuration", 
		        				"The configuration could not be exported:\n" + e.getMessage());
		        	}
		        }

				GeneralUCConfig ucconf = (GeneralUCConfig) itemMap.get(tree.getItem(0).getData());
				try {
					CommentsExtractor.addGeneralConfig(ucconf);
				} catch (Exception e) {
	        		ConfigChecker.showError("Exporting the configuration", 
	        				"Could not write the general configuration back into the code:\n" + e.getMessage());
				}
				
			}
        });
		
		Label emptySpace = new Label(topComposite, SWT.NONE);		

		addPanelButton = new Button(topComposite, SWT.BOTTOM);
		addPanelButton.setEnabled(false);
		addPanelButton.setToolTipText("Add a new panel");
		addPanelButton.setImage(ResourceManager.getPluginImage("org.universaal.tools.configurationExtractor", "icons/addPanelIcon.png"));
		addPanelButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				ConfigItem configItem = new ConfigItem(ItemType.PANEL);
				configItem.setParameter(ConfigItem.CAPTION, "CAPTION");
				
				TreeItem treeItem = new TreeItem(tree, SWT.NONE);
				treeItem.setText("Panel");
				treeItem.setData(itemCount);
				itemMap.put(itemCount++, configItem);
			}
        });	

		deleteButton = new Button(topComposite, SWT.BOTTOM);
		deleteButton.setEnabled(false);
		deleteButton.setToolTipText("delete an item");
		deleteButton.setImage(ResourceManager.getPluginImage("org.universaal.tools.configurationExtractor", "icons/DeleteIcon1.png"));
		deleteButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				deleteSelectedItem();
			}
        });
	}
	
	/**
	 * this method is called to delete an item
	 */
	private void deleteSelectedItem() {
		TreeItem item = tree.getSelection()[0];
		ConfigItem conf = itemMap.get(item.getData());
		
		if (conf instanceof GeneralUCConfig) {
			return;
		}
		
		if ((conf.getItemType() == ItemType.PANEL || conf.getItemType() == ItemType.ONTOLOGY_PANEL) && item.getItemCount() > 0) {
			MessageDialog messageDialog = new MessageDialog(Display.getCurrent().getActiveShell()
					, "Delete a panel", null, 
					"The panel you want to delete contains items.\n" +
							"What do you want to do?", MessageDialog.CONFIRM,
							new String[] { "Delete all", "Delete only the panel", "Cancel" }, 0);
			switch (messageDialog.open()) {
			case 0:
				break;
			case 1: // "move" the items of the panel into the GeneralUCConfig panel
				TreeItem newItem;
				for (TreeItem moveItem : item.getItems()) {
					newItem = new TreeItem(tree.getItem(0), SWT.NONE);
					newItem.setText(moveItem.getText());
					newItem.setData(moveItem.getData());
				}
				break;
			case 2:
				return;
			}
			
		} else {
			MessageDialog messageDialog = new MessageDialog(Display.getCurrent().getActiveShell()
					, "Delete an item", null, 
					"Do you really want to delete this item?", MessageDialog.CONFIRM,
					new String[] { "Yes", "No" }, 0);
			if (messageDialog.open() == 1) {
				return;
			}
		}				
		
		itemMap.remove(item.getData());
		item.dispose();
		
		tree.setSelection(tree.getItem(0));
	}
	
	/**
	 * @return Projects in the Workspace (but can be located elsewhere)
	 */
	private IProject[] getWorkspaceProjects() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
				
		return projects;
	}

	/**
	 * open a project located at the specified directory
	 * @param projectDir
	 */
	private void openProject(String projectDir) {
		// get the configuration for this project
		CommentsExtractor ce = new CommentsExtractor();
		LinkedList<ConfigItem> config = null;
		try {
			config = ce.getConfigFromProject(projectDir);
		} catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "An error occured when parsing the configuration comments.\n" +
        			"Probably there is a wrong syntax in your config comments.", "Error found!", JOptionPane.ERROR_MESSAGE);
        	return;
		}
		
		// cancel if an error was found
		if (config == null) {
			return;
		}
		
		// open the project
		openProject(config);
	}

	/**
	 * open a project using the given configuration items
	 * @param config
	 */
	private void openProject(LinkedList<ConfigItem> config) {
		projectIsOpen = true;
		exportBtn.setEnabled(true);
		switchModeBtn.setEnabled(true);
		addPanelButton.setEnabled(true);
		deleteButton.setEnabled(false);
		
		tree.removeAll();
		
		TreeItem item = null, subItem = null;
		
		itemCount = 0;

		// add the general configuration item
		item = new TreeItem(tree, SWT.NONE);
		item.setText("General Configuration");
		item.setData(itemCount);
		
		for (ConfigItem configItem : config) {
			if (configItem instanceof GeneralUCConfig) {
				ConfigItem general = configItem;
				config.remove(configItem);
				config.add(0, general);
				break;
			}
		}
		
		if (! (config.get(0) instanceof GeneralUCConfig)) { // item does not exist yet
			itemMap.put(itemCount++, new GeneralUCConfig());
			System.err.println("no general uc config found!!");
		} else { // item already exists
			itemMap.put(itemCount++, config.get(0));
		}
		
		// loop through all config items
		for (ConfigItem configItem : config) {
			if (configItem instanceof GeneralUCConfig) {
				continue;
			}
			
			if (configItem.getItemType() == ItemType.PANEL) {
				item = new TreeItem(tree, SWT.NONE);
				item.setText("Panel: " + configItem.getCaption());
				item.setData(itemCount);
				itemMap.put(itemCount++, configItem);
				
			} else if (configItem.getItemType() == ItemType.ONTOLOGY_PANEL) {
				item = new TreeItem(tree, SWT.NONE);
				item.setText("Ontology Panel: " + configItem.getCaption());
				item.setData(itemCount);
				itemMap.put(itemCount++, configItem);
				
			} else {
				if (item == null) {
					subItem = new TreeItem(tree, SWT.NONE);
				} else {
					subItem = new TreeItem(item, SWT.NONE);
				}
				
				subItem.setText("Variable: " + configItem.getName());
				subItem.setData(itemCount);
				
				itemMap.put(itemCount++, configItem);
			}
		}
		
		// clear the right side	composite
		Composite comp = new Composite(rightSideContainer, SWT.FILL);
		rightSideContainer.setContent(comp);
		
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		fillLayout.type = SWT.HORIZONTAL;
		
		comp.setLayout(fillLayout);
		
		Label l = new Label(comp, SWT.NONE);
		l.setText("Please select an item from the tree!");

		rightSideContainer.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		comp.layout();
	}

	
	/**
	 * @param data
	 * @return ConfigItem that has the specified id
	 */
	public ConfigItem getConfigItem(int id) {
		return itemMap.get(id);
	}
	
}
