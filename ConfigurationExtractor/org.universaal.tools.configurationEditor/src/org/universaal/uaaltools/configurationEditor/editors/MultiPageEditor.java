/*
	Copyright 2011 FZI, http://www.fzi.de

	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	  http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.uaaltools.configurationEditor.editors;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.*;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.ide.IDE;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.universaal.uaaltools.configurationEditor.utils.WidgetListener;
import org.universaal.uaaltools.configurationEditor.utils.WidgetMapping;

public class MultiPageEditor extends MultiPageEditorPart implements
		IResourceChangeListener {

	/** The text editor used in text editor. */
	private TextEditor editor;

	private ExpandBar mainBar;

	private Document doc;
	
	private  WidgetListener widgetListener;
	
	
	 protected boolean dirty = false;

	/**
	 * Creates a multi-page editor example.
	 */
	public MultiPageEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		
		widgetListener = new WidgetListener(this);

	}
	

	// --------------------------------------------------------------------------------------------------------------------------

	void createConfigPage() {

		Composite composite = new Composite(getContainer(), SWT.NONE);
		// GridLayout layout = new GridLayout();
		FillLayout layout = new FillLayout();
		// layout.type = SWT.VERTICAL;
		// layout.numColumns = 2;

		// GridData gridData = new GridData();
		// gridData.horizontalAlignment = GridData.FILL;
		// gridData.verticalAlignment = GridData.FILL;
		// composite.setLayoutData(gridData);

		composite.setLayout(layout);
		
		
		

		mainBar = new ExpandBar(composite, SWT.V_SCROLL);

		// mainBar.setLayoutData(gridData);
		// mainBar.setLayout(new FillLayout());

		// ExpandBar catBar = new ExpandBar(composite, SWT.H_SCROLL);
		// catBar.setLayoutData(gridData);
		// mainBar.setLayout(new FillLayout());

		createMainBar(
				editor.getDocumentProvider()
						.getDocument(editor.getEditorInput()).get(), mainBar);

		// Composite mainComp = new Composite(mainBar, SWT.NONE);
		// GridLayout mainLayout = new GridLayout (2, false);
		// mainComp.setLayout(mainLayout);
		//
		//
		//
		//
		//
		// Label l1 = new Label(mainComp, SWT.NONE);
		// l1.setText("Label text");
		//
		// Text t1 = new Text(mainComp, SWT.SINGLE);
		// t1.setEditable(true);
		// t1.setTextLimit(30);
		// t1.setText("Test lololo");
		//
		//
		// ExpandItem itemMain = new ExpandItem (mainBar, SWT.NONE, 0);
		// itemMain.setText("Main configuration parameters");
		// itemMain.setHeight(mainComp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		// itemMain.setControl(mainComp);
		//
		// itemMain.setExpanded(true);
		//
		//
		
		int index = addPage(composite);
		setPageText(index, "Configuration Editor");
		
	}

	private void createMainBar(String xml, ExpandBar mainBar /*
															 * , ExpandBar
															 * catBar
															 */) {

		Composite mainComp = new Composite(mainBar, SWT.NONE);
		GridLayout mainLayout = new GridLayout(2, false);
		mainComp.setLayout(mainLayout);

		try {
			this.doc = new SAXBuilder().build(new StringReader(xml));

			// Element xmlRoot = doc.getRootElement();
			Element config = doc.getRootElement(); // xmlRoot.getChild("universaal:configuration")

			Label l1;
			Text t1;

			ExpandItem itemMain = new ExpandItem(mainBar, SWT.NONE, 0);
			itemMain.setText(config.getName() + ": Main parameters");
			// itemMain.setHeight(mainComp.computeSize(SWT.DEFAULT,
			// SWT.DEFAULT).y);

			// height and control
			// int mainItemHeight = itemMain.getHeaderHeight();

			itemMain.setControl(mainComp);

			List<Attribute> ats = config.getAttributes();

			for (Attribute at : ats) {

				l1 = new Label(mainComp, SWT.NONE);
				l1.setText(at.getName() + ":");

				t1 = new Text(mainComp, SWT.SINGLE | SWT.BORDER);
				t1.setEditable(true);
				t1.setText(at.getValue());

				// ------------- <Listener> --------------

				WidgetMapping.put(t1, at);
				t1.addModifyListener(widgetListener);
				
				// ------------- </Listener> --------------

				GridData gd = new GridData();
				gd.horizontalAlignment = GridData.FILL;
				gd.grabExcessHorizontalSpace = true;
				t1.setLayoutData(gd);

			}

			itemMain.setHeight(mainComp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

			itemMain.setExpanded(true);

			// Categories

			List<Element> cats = config.getChildren();
			int i = 1;

			for (Element cat : cats) {
				ExpandItem itemCat = new ExpandItem(/* catBar */mainBar,
						SWT.NONE, i++);
				itemCat.setText(cat.getName() + ": "
						+ cat.getAttributeValue("label"));
				createCategoryBar(cat, itemCat /* catBar */);
			}

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createCategoryBar(Element cat, ExpandItem itemCat) {

		Composite catComp = new Composite(mainBar, SWT.NONE);
		GridLayout catLayout = new GridLayout(2, false);
		catComp.setLayout(catLayout);
		// itemMain.setHeight(mainComp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

		Label l1;
		Text t1;

		List<Attribute> cAts = cat.getAttributes();

		for (Attribute at : cAts) {
			l1 = new Label(catComp, SWT.NONE);
			l1.setText(at.getName() + ":");

			t1 = new Text(catComp, SWT.SINGLE | SWT.BORDER);
			t1.setEditable(true);
			t1.setText(at.getValue());

			// ------------- <Listener> --------------

			WidgetMapping.put(t1, at);
			t1.addModifyListener(widgetListener);

			// ------------- </Listener> --------------

			GridData gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			gd.grabExcessHorizontalSpace = true;
			t1.setLayoutData(gd);

		}

		// item tabs
		TabFolder tabFolder = new TabFolder(catComp, SWT.NONE);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;

		tabFolder.setLayoutData(gridData);

		List<Element> items = cat.getChildren();

		for (Element el : items) {

			createCategoryTab(el, tabFolder);
		}

		tabFolder.pack();

		// TODO
		itemCat.setControl(catComp);
		itemCat.setHeight(catComp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	}

	private void createCategoryTab(Element el, TabFolder tf) {

		Composite tiComp = new Composite(tf, SWT.NONE);
		GridLayout tiLayout = new GridLayout(2, false);
		tiComp.setLayout(tiLayout);

		TabItem ti = new TabItem(tf, SWT.NONE);
		// set name
		ti.setText(el.getAttributeValue("id"));
		ti.setControl(tiComp);

		// ti's attributs
		List<Attribute> tiAtts = el.getAttributes();
		Label l1;
		Text t1;

		l1 = new Label(tiComp, SWT.NONE);
		l1.setText("ConfigItem type: ");

		l1 = new Label(tiComp, SWT.NONE);
		l1.setText(el.getName());

		for (Attribute att : tiAtts) {
			l1 = new Label(tiComp, SWT.NONE);
			l1.setText(att.getName() + ":");

			t1 = new Text(tiComp, SWT.SINGLE | SWT.BORDER);
			t1.setEditable(true);
			t1.setText(att.getValue());

			// ------------- <Listener> --------------

			WidgetMapping.put(t1, att);
			t1.addModifyListener(widgetListener);

			// ------------- </Listener> --------------

			GridData gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			gd.grabExcessHorizontalSpace = true;
			t1.setLayoutData(gd);
		}

		// TI's children
		List<Element> tiEls = el.getChildren();

		for (Element tiEl : tiEls) {

			// mapConfigItem
			if (tiEl.getName().equals("options")) {
				l1 = new Label(tiComp, SWT.NONE);
				l1.setText(tiEl.getName() + ":");

				Combo combo = new Combo(tiComp, SWT.NONE | SWT.READ_ONLY);

				List<Element> opt = tiEl.getChildren();
				for (Element e : opt) {
					combo.add(e.getValue());
				}

				GridData gd = new GridData();
				gd.horizontalAlignment = GridData.FILL;
				gd.grabExcessHorizontalSpace = true;
				combo.setLayoutData(gd);

				// Validators
			} else if (tiEl.getName().equals("validators")) {

				List<Element> opt = tiEl.getChildren();
				for (Element e : opt) {

					Group validatorGroup = new Group(tiComp,
							SWT.SHADOW_ETCHED_IN);

					GridData gd = new GridData();
					gd.horizontalAlignment = GridData.FILL;
					gd.horizontalSpan = 2;
					validatorGroup.setLayoutData(gd);

					validatorGroup.setLayout(new GridLayout(2, false));

					validatorGroup.setText("Validator");

					l1 = new Label(validatorGroup, SWT.NONE);
					l1.setText("class:");
					l1.pack();

					t1 = new Text(validatorGroup, SWT.SINGLE | SWT.BORDER);
					t1.setEditable(true);
					t1.setText(e.getAttributeValue("class"));
					t1.pack();

					// ------------- <Listener> --------------

					WidgetMapping.put(t1, e.getAttribute("class"));
					t1.addModifyListener(widgetListener);

					// ------------- </Listener> --------------

					gd = new GridData();
					gd.horizontalAlignment = GridData.FILL;
					gd.grabExcessHorizontalSpace = true;
					t1.setLayoutData(gd);

					// Validator attributs
					List<Element> vAtts = e.getChildren();

					for (Element vAt : vAtts) {
						l1 = new Label(validatorGroup, SWT.NONE);
						l1.setText(vAt.getName() + ":");
						// l1.pack();

						t1 = new Text(validatorGroup, SWT.SINGLE | SWT.BORDER);
						t1.setEditable(true);
						t1.setText(vAt.getValue());

						// ------------- <Listener> --------------

						WidgetMapping.put(t1, vAt);
						t1.addModifyListener(widgetListener);

						// ------------- </Listener> --------------

						// t1.pack();

						gd = new GridData();
						gd.horizontalAlignment = GridData.FILL;
						gd.grabExcessHorizontalSpace = true;
						t1.setLayoutData(gd);
					}
				}

			} else {

				l1 = new Label(tiComp, SWT.NONE);
				l1.setText(tiEl.getName() + ":");

				t1 = new Text(tiComp, SWT.SINGLE | SWT.BORDER);
				t1.setEditable(true);
				t1.setText(tiEl.getValue());

				// ------------- <Listener> --------------

				WidgetMapping.put(t1, tiEl);
				t1.addModifyListener(widgetListener);

				// ------------- </Listener> --------------

				GridData gd = new GridData();
				gd.horizontalAlignment = GridData.FILL;
				gd.grabExcessHorizontalSpace = true;
				t1.setLayoutData(gd);
			}
		}
	}

	/**
	 * Creates page 0 of the multi-page editor, which contains a text editor.
	 */
	void createEditorPage() {
		try {
			editor = new TextEditor();
			int index = addPage(editor, getEditorInput());
			setPageText(index, editor.getTitle());
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating nested text editor", null, e.getStatus());
		}
	}
	
	
	

	// void createPage1() {
	//
	// Composite composite = new Composite(getContainer(), SWT.NONE);
	// GridLayout layout = new GridLayout();
	// composite.setLayout(layout);
	// layout.numColumns = 2;
	//
	// Button fontButton = new Button(composite, SWT.NONE);
	// GridData gd = new GridData(GridData.BEGINNING);
	// gd.horizontalSpan = 2;
	// fontButton.setLayoutData(gd);
	// fontButton.setText("Change Font...");
	//
	// fontButton.addSelectionListener(new SelectionAdapter() {
	// public void widgetSelected(SelectionEvent event) {
	// setFont();
	// }
	// });
	//
	// int index = addPage(composite);
	// setPageText(index, "Properties");
	// }

	// void createPage2() {
	// Composite composite = new Composite(getContainer(), SWT.NONE);
	// FillLayout layout = new FillLayout();
	// composite.setLayout(layout);
	// text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
	// text.setEditable(false);
	//
	// int index = addPage(composite);
	// setPageText(index, "Preview");
	// }

	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createEditorPage();
		createConfigPage();

		setActivePage(1);

	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {

		// xml -> config
		if (getActivePage() == 0) {
			getEditor(0).doSave(monitor);
			removePage(1);
			createConfigPage();

			// config -> xml
		} else if (getActivePage() == 1) {
			XMLOutputter outp = new XMLOutputter();

			// outp.setFormat(Format.getCompactFormat());
			// outp.setFormat(Format.getRawFormat());
			outp.setFormat(Format.getPrettyFormat());
			// outp.getFormat().setTextMode(Format.TextMode.PRESERVE);

			StringWriter sw = new StringWriter();
			try {
				outp.output(doc.getContent(), sw);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuffer sb = sw.getBuffer();
			String editorText = sb.toString();

			editor.getDocumentProvider().getDocument(editor.getEditorInput())
					.set(editorText);
			getEditor(0).doSave(monitor);
			removePage(1);
			createConfigPage();
			setActivePage(1);
		}
		setDirty(false);
	}

	/**
	 * Saves the multi-page editor's document as another file. Also updates the
	 * text for page 0's tab, and updates this multi-page editor's input to
	 * correspond to the nested editor's.
	 */
	public void doSaveAs() {
		
		doSave(null);
//		IEditorPart editor = getEditor(0);
//		editor.doSaveAs();
//		setPageText(0, editor.getTitle());
//		setInput(editor.getEditorInput());
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException(
					"Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	// /**
	// * Calculates the contents of page 2 when the it is activated.
	// */
	// protected void pageChange(int newPageIndex) {
	// super.pageChange(newPageIndex);
	// if (newPageIndex == 2) {
	// sortWords();
	// }
	// }

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow()
							.getPages();
					for (int i = 0; i < pages.length; i++) {
						if (((FileEditorInput) editor.getEditorInput())
								.getFile().getProject()
								.equals(event.getResource())) {
							IEditorPart editorPart = pages[i].findEditor(editor
									.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	// /**
	// * Sets the font related data to be applied to the text in page 2.
	// */
	// void setFont() {
	// FontDialog fontDialog = new FontDialog(getSite().getShell());
	// fontDialog.setFontList(text.getFont().getFontData());
	// FontData fontData = fontDialog.open();
	// if (fontData != null) {
	// if (font != null)
	// font.dispose();
	// font = new Font(text.getDisplay(), fontData);
	// text.setFont(font);
	// }
	// }
	//
	// /**
	// * Sorts the words in page 0, and shows them in page 2.
	// */
	// void sortWords() {
	//
	// String editorText = editor.getDocumentProvider()
	// .getDocument(editor.getEditorInput()).get();
	//
	// StringTokenizer tokenizer = new StringTokenizer(editorText,
	// " \t\n\r\f!@#\u0024%^&*()-_=+`~[]{};:'\",.<>/?|\\");
	// ArrayList editorWords = new ArrayList();
	// while (tokenizer.hasMoreTokens()) {
	// editorWords.add(tokenizer.nextToken());
	// }
	//
	// Collections.sort(editorWords, Collator.getInstance());
	// StringWriter displayText = new StringWriter();
	// for (int i = 0; i < editorWords.size(); i++) {
	// displayText.write(((String) editorWords.get(i)));
	// displayText.write(System.getProperty("line.separator"));
	// }
	// text.setText(displayText.toString());
	// }
	
	 public boolean isDirty() {
         return dirty;
      }
	
     protected void setDirty(boolean value) {
         dirty = value;
         firePropertyChange(PROP_DIRTY);
      }
     
 	public void editorChanged() {
		setDirty(true);
	}

}
