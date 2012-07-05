package org.universaal.tools.configurationExtractor.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.universaal.tools.configurationExtractor.data.ConfigItem;
import org.universaal.tools.configurationExtractor.data.GeneralUCConfig;
import org.universaal.tools.configurationExtractor.data.ItemType;

/**
 * ItemDetails handles the right side panel showing details of an item
 * @author schwende
 */
public class ItemDetails {
	
	private Composite shownDetailsComposite;
	private ScrolledComposite rightSideContainer;
	private Tree tree;
	private TreeItem selectedItem;
	
	/**
	 * constructor for showing item details in the specified container
	 * @param rightSideContainer Composite to show the details in
	 */
	public ItemDetails(ScrolledComposite rightSideContainer, Tree tree) {
		this.rightSideContainer = rightSideContainer;
		this.tree = tree;
	}
	
	/**
	 * show the item details for the specified ConfigItem
	 * @param conf
	 */
	public void showItemDetails(ConfigItem conf) {
		if (shownDetailsComposite != null) {
			saveConfig();
		}
		
		if (conf instanceof GeneralUCConfig) {
			selectedItem = null;
			showGeneralDetails((GeneralUCConfig) conf);
			return;
		}
		
		selectedItem = tree.getSelection()[0];
		
		shownDetailsComposite = new Composite(rightSideContainer, SWT.FILL);
		shownDetailsComposite.setData(conf);
		rightSideContainer.setContent(shownDetailsComposite);

		GridLayout grid = new GridLayout();
		grid.marginHeight = 0;
		grid.marginWidth = 0;
		grid.numColumns = 2;
		shownDetailsComposite.setLayout(grid);

		GridData spanData = new GridData();
		spanData.horizontalSpan = 2;
		
		GridData fillData = new GridData(GridData.FILL_HORIZONTAL);
		
		Label l;		
		
		
		// add a header
		l = new Label(shownDetailsComposite, SWT.BOLD);
		l.setText("Configuration Item: " + conf.getItemType());
		l.setLayoutData(spanData);
		
		Text text;
		Combo combo;
		
		switch (conf.getItemType()) {
			case PANEL: // add panel items
				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Caption: ");
				
			    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
			    text.setText(conf.getCaption());
				text.setLayoutData(fillData);
				text.setData(ConfigItem.CAPTION);
				
				break;
				
			case VARIABLE: // add variable items
				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Name: ");
				
			    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
			    text.setEnabled(false);
			    text.setText(conf.getName());
				text.setLayoutData(fillData);
				text.setData(ConfigItem.NAME);

				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Type: ");
				
				combo = new Combo(shownDetailsComposite, SWT.READ_ONLY);
			    combo.setEnabled(false);
				combo.setItems(ConfigItem.getAllowedTypes());
				combo.select(conf.getTypeIndex());
				combo.setLayoutData(fillData);
				combo.setData(ConfigItem.TYPE);

				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Label Text: ");
				
			    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
			    text.setText(conf.getLabel());
				text.setLayoutData(fillData);
				text.setData(ConfigItem.LABEL);

				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Hover Text: ");
				
			    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
			    text.setText(conf.getHover());
				text.setLayoutData(fillData);
				text.setData(ConfigItem.HOVER);
				
				break;
				
			case ONTOLOGY_PANEL: // add uri items
				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Caption: ");
				
			    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
			    text.setText(conf.getCaption());
				text.setLayoutData(fillData);
				text.setData(ConfigItem.CAPTION);
				
				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Name: ");
				
			    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
			    text.setEnabled(false);
			    text.setText(conf.getName());
				text.setLayoutData(fillData);
				text.setData(ConfigItem.NAME);

				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Domain: ");
				
			    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
			    text.setEnabled(false);
			    text.setText(conf.getDomain());
				text.setLayoutData(fillData);
				text.setData(ConfigItem.DOMAIN);

				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Label Text: ");
				
			    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
			    text.setText(conf.getLabel());
				text.setLayoutData(fillData);
				text.setData(ConfigItem.LABEL);

				l = new Label(shownDetailsComposite, SWT.NONE);
				l.setText("Hover Text: ");
				
			    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
			    text.setText(conf.getHover());
				text.setLayoutData(fillData);
				text.setData(ConfigItem.HOVER);
				
				break;
		}
		

		rightSideContainer.setMinSize(shownDetailsComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		shownDetailsComposite.layout();
	}

	/**
	 * show the general usecase parameters
	 * @param conf general config
	 */
	private void showGeneralDetails(GeneralUCConfig conf) {
		shownDetailsComposite = new Composite(rightSideContainer, SWT.FILL);
		shownDetailsComposite.setData(conf);
		rightSideContainer.setContent(shownDetailsComposite);

		GridLayout grid = new GridLayout();
		grid.marginHeight = 0;
		grid.marginWidth = 0;
		grid.numColumns = 2;
		shownDetailsComposite.setLayout(grid);

		GridData spanData = new GridData();
		spanData.horizontalSpan = 2;
		
		GridData fillData = new GridData(GridData.FILL_HORIZONTAL);
		
		Label l;		
		
		
		// header
		l = new Label(shownDetailsComposite, SWT.BOLD);
		l.setText("General Usecase Configuration");
		l.setLayoutData(spanData);
		

		// parameter: name
		l = new Label(shownDetailsComposite, SWT.NONE);
		l.setText("Name: ");
		
	    Text text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
	    text.setText(conf.getUcName());
		text.setLayoutData(fillData);
		text.setData(GeneralUCConfig.UC_NAME);

		// parameter: version nr
		l = new Label(shownDetailsComposite, SWT.NONE);
		l.setText("Version Nr.: ");
		
		text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
		text.setText(conf.getVersionNr());
		text.setLayoutData(fillData);
		text.setData(GeneralUCConfig.VERSION_NR);

		// parameter: author
		l = new Label(shownDetailsComposite, SWT.NONE);
		l.setText("Author: ");
		
	    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
	    text.setText(conf.getAuthor());
		text.setLayoutData(fillData);
		text.setData(GeneralUCConfig.AUTHOR);

		// uid
		l = new Label(shownDetailsComposite, SWT.NONE);
		l.setText("UID: ");
		
	    text = new Text(shownDetailsComposite, SWT.SINGLE | SWT.BORDER);
	    text.setEnabled(false);
	    text.setText(conf.getUid());
		text.setLayoutData(fillData);
		text.setData(GeneralUCConfig.UID);
		
				

		rightSideContainer.setMinSize(shownDetailsComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		shownDetailsComposite.layout();
	}
	
	/**
	 * save the currently shown configuration
	 */
	public void saveConfig() {
		if (shownDetailsComposite == null) {
			return;
		}
		
		// get the config item
		ConfigItem conf;		
		if (shownDetailsComposite.getData() instanceof GeneralUCConfig) {
			conf = (GeneralUCConfig) shownDetailsComposite.getData();
		} else {
			conf = (ConfigItem) shownDetailsComposite.getData();
		}
		
		// loop through all items and save them
		Control[] childs = shownDetailsComposite.getChildren();
		for (int i = 0; i < childs.length; i++) {
			if (childs[i] instanceof Text) {
				conf.setParameter(childs[i].getData().toString(), ((Text) childs[i]).getText());
			} else if (childs[i] instanceof Combo) {
				int index = ((Combo) childs[i]).getSelectionIndex();
				if (index < 0) {
					conf.setParameter(childs[i].getData().toString(), "");
				} else {
					conf.setParameter(childs[i].getData().toString(), ((Combo) childs[i]).getItem(index));
				}
			}
		}
		
		if (selectedItem != null && ! selectedItem.isDisposed()) {
			if (conf.getItemType() == ItemType.VARIABLE) {
				selectedItem.setText("Variable: " + conf.getName());
				
			} else if (conf.getItemType() == ItemType.PANEL) {
				selectedItem.setText("Panel: " + conf.getCaption());
				
			} else if (conf.getItemType() == ItemType.ONTOLOGY_PANEL) {
				selectedItem.setText("Ontology Panel: " + conf.getCaption());
			}
		}
		
	}
	
}
