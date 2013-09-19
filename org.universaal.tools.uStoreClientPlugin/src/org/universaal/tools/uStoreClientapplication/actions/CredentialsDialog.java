/*
	Copyright 2011-2014 CERTH-ITI, http://www.iti.gr
	Information Technologies Institute (ITI)
	Centre For Research and Technology Hellas (CERTH)
	
	
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
package org.universaal.tools.uStoreClientapplication.actions;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.universaal.commerce.ustore.tools.AALApplicationManager;
import org.universaal.commerce.ustore.tools.AALApplicationManagerServiceLocator;
import org.universaal.commerce.ustore.tools.CatalogManager;
import org.universaal.commerce.ustore.tools.CatalogManagerServiceLocator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CredentialsDialog extends Dialog {

	protected Object result;
	protected Shell shlProvideUstoreCredentials;
	private Text text;
	private Text text_1;
	private Shell parent;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public CredentialsDialog(Shell parent, int style) {
		super(parent, style);
		this.parent = parent;
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlProvideUstoreCredentials.open();
		shlProvideUstoreCredentials.layout();
		Display display = getParent().getDisplay();
		while (!shlProvideUstoreCredentials.isDisposed()) {
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
		shlProvideUstoreCredentials = new Shell(getParent(), SWT.DIALOG_TRIM);
		shlProvideUstoreCredentials.setSize(257, 139);
		shlProvideUstoreCredentials.setText("Provide uStore credentials");
		shlProvideUstoreCredentials.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(shlProvideUstoreCredentials,
				SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite.heightHint = 68;
		gd_composite.widthHint = 363;
		composite.setLayoutData(gd_composite);

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_composite_2 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_2.widthHint = 355;
		composite_2.setLayoutData(gd_composite_2);

		Label lblUsername = new Label(composite_2, SWT.NONE);
		lblUsername.setText("Username:");

		text = new Text(composite_2, SWT.BORDER);
		text.setToolTipText("uStore username");
		text.setLayoutData(new RowData(145, SWT.DEFAULT));

		Composite composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_composite_3 = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_3.widthHint = 244;
		composite_3.setLayoutData(gd_composite_3);

		Label lblPassword = new Label(composite_3, SWT.NONE);
		lblPassword.setText("Password:");

		text_1 = new Text(composite_3, SWT.BORDER | SWT.PASSWORD);
		text_1.setToolTipText("uStore user password");
		text_1.setLayoutData(new RowData(147, SWT.DEFAULT));

		Composite composite_1 = new Composite(shlProvideUstoreCredentials,
				SWT.NONE);
		composite_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_1.widthHint = 244;
		composite_1.setLayoutData(gd_composite_1);

		Button btnOk = new Button(composite_1, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// get list of available uStore applications
				try {
					AALApplicationManagerServiceLocator loc = new AALApplicationManagerServiceLocator();
					AALApplicationManager man = loc
							.getAALApplicationManagerPort();
					String result = man.getAALApplications(text.getText(),
							text_1.getText(), "");
					// parse result
					List<Application> applications = parseXml(result);
					
					ApplicationsDialog applicationsDialog = new ApplicationsDialog(
							parent, 1, applications,text.getText(),text_1.getText());
					applicationsDialog.open();
					shlProvideUstoreCredentials.close();

				} catch (Exception ex) {
					ex.printStackTrace();
					MessageDialog.openError(parent, "Error", "The following error occured:\n "+ex.toString());
					shlProvideUstoreCredentials.close();
				}

			}
		});
		btnOk.setText("OK");

		Button btnCancel = new Button(composite_1, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				shlProvideUstoreCredentials.close();

			}
		});
		btnCancel.setText("Cancel");

	}

	private List<Application> parseXml(String xml) {
		List<Application> list = new ArrayList<Application>();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = loadXMLFromString(xml);
			NodeList nList = doc.getElementsByTagName("application");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Application app = new Application();
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					app.setId(getTagValue("id", eElement));
					app.setName(getTagValue("name", eElement));

					list.add(app);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list;
	}

	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	private Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

}
