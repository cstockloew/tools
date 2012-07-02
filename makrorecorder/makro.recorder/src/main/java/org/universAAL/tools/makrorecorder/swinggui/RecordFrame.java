/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
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
package org.universAAL.tools.makrorecorder.swinggui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.tools.makrorecorder.Activator;
import org.universAAL.tools.makrorecorder.makrorecorder.Pattern;
import org.universAAL.tools.makrorecorder.makrorecorder.myContextSubscriber;


public class RecordFrame extends JFrame implements ActionListener,MouseListener, ListSelectionListener{
	private static final long serialVersionUID = 8508553212473882557L;
	
	JButton stopButton;
	JButton higherButton;
	JButton lowerButton;
	JButton deleteButton;

	ImageIcon stopIcon;
	ImageIcon higherIcon;
	ImageIcon lowerIcon;
	ImageIcon deleteIcon;
	JLabel durationLabel;

	JList contextEventList;
	JList serviceRequestList;
	DefaultListModel serviceRequestModel;
	DefaultListModel contextEventModel;
	Timer timer;

	MainFrame parent;
	String Name;

	AbstractAction stopAction;
	AbstractAction higherAction;
	AbstractAction lowerAction;
	AbstractAction deleteAction;

/**
this constructor is used when a new recording session is started
**/
public RecordFrame(String Name, MainFrame parent) {
	this.parent = parent;
	this.Name = Name;

	buildGui();
	timer = new Timer(1000, this);
	timer.setInitialDelay(1000);
	timer.start();
	durationLabel.setText("recording");

	higherButton.setEnabled(false);
	deleteButton.setEnabled(false);
	lowerButton.setEnabled(false);
}

/**
this constructor is used when a existing record is viewed
**/

public RecordFrame(Pattern pattern, MainFrame parentFrame) {
	this.parent = parentFrame;
	this.Name = pattern.getName();
	buildGui();


	long currentTime = pattern.getDuration();
	long timeInSec = (currentTime / 1000) % 60;
	long timeInMin = (currentTime / 60000);
	if (timeInSec < 10) 
		durationLabel.setText(timeInMin +":0" + timeInSec);	
	else 
		durationLabel.setText(timeInMin +":" + timeInSec);
	durationLabel.setText("duration: "+durationLabel.getText());


	fillModel();
	contextEventList.repaint();
	serviceRequestList.repaint();
	higherButton.setEnabled(false);
	deleteButton.setEnabled(false);
	lowerButton.setEnabled(false);
	stopButton.setEnabled(false);
	stopButton.setVisible(false);
}

private void buildGui() {
	this.setBounds(300, 100, 900, 400);
	this.setMinimumSize(new Dimension(600, 300));
	this.setLayout(new BorderLayout(10,10));
	this.setTitle("Record of " +Name);

	//creates all image
	stopIcon = new ImageIcon(RecordFrame.class.getClassLoader().getResource("images/StopButton.png"));
	Image icon = stopIcon.getImage();
	stopIcon = new ImageIcon(icon.getScaledInstance(50, 50, Image.SCALE_SMOOTH));

	higherIcon = new ImageIcon(RecordFrame.class.getClassLoader().getResource("images/Higher.png"));
	icon = higherIcon.getImage();
	higherIcon = new ImageIcon(icon.getScaledInstance(50, 50, Image.SCALE_SMOOTH));

	lowerIcon = new ImageIcon(RecordFrame.class.getClassLoader().getResource("images/lower.png"));
	icon = lowerIcon.getImage();
	lowerIcon = new ImageIcon(icon.getScaledInstance(50, 50, Image.SCALE_SMOOTH));

	deleteIcon = new ImageIcon(RecordFrame.class.getClassLoader().getResource("images/Delete.png"));
	icon = deleteIcon.getImage();
	deleteIcon = new ImageIcon(icon.getScaledInstance(50, 50, Image.SCALE_SMOOTH));

	durationLabel = new JLabel();
	//durationLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	durationLabel.setVerticalAlignment(SwingConstants.CENTER);
	durationLabel.setHorizontalAlignment(SwingConstants.CENTER);

	stopButton = new JButton();
	stopButton.setAction(stopRecording());
	stopButton.setIcon(stopIcon);

	higherButton = new JButton();
	higherButton.setAction(setHigher());
	higherButton.setIcon(higherIcon);

	deleteButton = new JButton();
	deleteButton.setAction(deleteResource());
	deleteButton.setIcon(deleteIcon);

	lowerButton = new JButton();
	lowerButton.setAction(setLower());
	lowerButton.setIcon(lowerIcon);


	contextEventModel = new DefaultListModel();
	contextEventList = new JList(contextEventModel);
	contextEventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	contextEventList.setLayoutOrientation(JList.VERTICAL);
	contextEventList.addMouseListener(this);
	contextEventList.addListSelectionListener(this);
	//contextEventList.setPreferredSize(new Dimension(280, 200));

	serviceRequestModel = new DefaultListModel();
	serviceRequestList = new JList(serviceRequestModel);
	serviceRequestList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	serviceRequestList.setLayoutOrientation(JList.VERTICAL);
	serviceRequestList.addMouseListener(this);
	serviceRequestList.addListSelectionListener(this);
	//serviceRequestList.setPreferredSize(new Dimension(280, 200));

	JScrollPane scrollPane1 = new JScrollPane(contextEventList);
	//scrollPane1.setPreferredSize(new Dimension(400, 200));
	//scrollPane1.setMinimumSize(new Dimension(400, 200));
	JScrollPane scrollPane2 = new JScrollPane(serviceRequestList);
	//scrollPane2.setPreferredSize(new Dimension(400, 200));
	//scrollPane2.setMinimumSize(new Dimension(400, 200));
	
	//JPanel left = new JPanel(new GridLayout(1,1));
	//JPanel right = new JPanel(new GridLayout(1,1));
	JPanel middle = new JPanel(new GridBagLayout());
	JPanel south = new JPanel(new GridBagLayout());
	JPanel center = new JPanel(new GridBagLayout());

	GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1;
	c.gridx = 0;
	c.gridy = 0;
	south.add(durationLabel,c);
	c.fill = GridBagConstraints.NONE;
	c.weightx = 0;
	c.gridx = 1;
	c.gridy = 0;
	south.add(stopButton,c);
	
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	middle.add(higherButton,c);
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 1;
	middle.add(deleteButton,c);
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 2;
	middle.add(lowerButton,c);
	/*c.fill = GridBagConstraints.BOTH;
	c.gridx = 0;
	c.gridy = 3;
	middle.add(new JPanel(),c);*/
	/*middle.setMinimumSize(new Dimension(100, 150));
	middle.setSize(new Dimension(100, 150));
	middle.setMaximumSize(new Dimension(100, 150));*/
	
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1;
	c.weighty = 1;
	c.gridx = 0;
	c.gridy = 0;
	c.ipadx = 10;
	c.ipady = 10;
	center.add(scrollPane1,c);
	c.fill = GridBagConstraints.VERTICAL;
	c.weightx = 0;
	c.weighty = 1;
	c.gridx = 1;
	c.gridy = 0;
	c.ipadx = 10;
	c.ipady = 10;
	center.add(middle,c);
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 1;
	c.weighty = 1;
	c.gridx = 2;
	c.gridy = 0;
	c.ipadx = 10;
	c.ipady = 10;
	center.add(scrollPane2,c);
	this.getContentPane().add(center,BorderLayout.CENTER);
	this.getContentPane().add(south,BorderLayout.PAGE_END);
}

public AbstractAction stopRecording(){
	if (stopAction == null) {
		stopAction = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				Activator.myContextSubscriber.stopRecording(Name);

				parent.setEnabled();

				stopButton.setEnabled(false);
				stopButton.setVisible(false);

				higherButton.setEnabled(true);
				deleteButton.setEnabled(true);
				lowerButton.setEnabled(true);

				parent.addToTable(fillModel());

				timer.stop();

				long currentTime = Activator.resourceOrginazer.getPatternByName(Name).getDuration(); 
				long timeInSec = (currentTime / 1000) % 60;
				long timeInMin = (currentTime / 60000);
				if (timeInSec < 10) 
					durationLabel.setText(timeInMin +":0" + timeInSec);	
				else 
					durationLabel.setText(timeInMin +":" + timeInSec);
				durationLabel.setText("duration: "+durationLabel.getText());
			}
		};
	}
	return stopAction;
}

public AbstractAction setHigher() {
	if (higherAction == null) {
		higherAction = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				if (contextEventList.getSelectedIndex() != -1) {
					Pattern pattern = Activator.resourceOrginazer.getPatternByName(Name);
					pattern.changePositionOfContextEvent(contextEventList.getSelectedIndex(),Pattern.HIGHER);
					contextEventList.clearSelection();
				}else if (serviceRequestList.getSelectedIndex() != -1) {
					Pattern pattern = Activator.resourceOrginazer.getPatternByName(Name);
					pattern.changePositionOfServiceRequest(serviceRequestList.getSelectedIndex(),Pattern.HIGHER);
					serviceRequestList.clearSelection();
				}
				contextEventModel.clear();
				serviceRequestModel.clear();

				fillModel();
				contextEventList.repaint();
				serviceRequestList.repaint();
			}
		};
	}
	return higherAction;
}

public AbstractAction setLower() {
	if (lowerAction == null) {
		lowerAction = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				if (contextEventList.getSelectedIndex() != -1) {
					Pattern pattern = Activator.resourceOrginazer.getPatternByName(Name);
					pattern.changePositionOfContextEvent(contextEventList.getSelectedIndex(), Pattern.LOWER);
					contextEventList.clearSelection();
				}else if (serviceRequestList.getSelectedIndex() != -1) {
					Pattern pattern = Activator.resourceOrginazer.getPatternByName(Name);
					pattern.changePositionOfServiceRequest(serviceRequestList.getSelectedIndex(),Pattern.LOWER);
					serviceRequestList.clearSelection();
				}
				contextEventModel.clear();
				serviceRequestModel.clear();



				fillModel();
				contextEventList.repaint();
				serviceRequestList.repaint();
			}
		};
	}
	return lowerAction;
}

public AbstractAction deleteResource() {
	if (deleteAction == null) {
		deleteAction = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				if (contextEventList.getSelectedIndex() != -1) {
					Pattern pattern = Activator.resourceOrginazer.getPatternByName(Name);
					pattern.deleteContextEvent(contextEventList.getSelectedIndex());
					contextEventList.clearSelection();
				}else if (serviceRequestList.getSelectedIndex() != -1) {
					Pattern pattern = Activator.resourceOrginazer.getPatternByName(Name);
					pattern.deleteServiceRequest(serviceRequestList.getSelectedIndex());
					serviceRequestList.clearSelection();
				}
				contextEventModel.clear();
				serviceRequestModel.clear();

				fillModel();
				contextEventList.repaint();
				serviceRequestList.repaint();
			}
		};
	}
	return deleteAction;
}

/**
adds one ContexEvent to the ContexEvent-List
**/
public void addContexEvent(ContextEvent event) {
	String [] arry = ((Resource)event.getProperty(ContextEvent.PROP_RDF_SUBJECT)).getURI().split("#");
	String status = "Subject: ";
	status += arry[arry.length -1];

	status += " Predicate: ";
	arry = ((Resource)event.getProperty(ContextEvent.PROP_RDF_PREDICATE)).getURI().split("#");
	status += arry[arry.length -1];

	status += " Object: ";
//	arry = ((Resource) event.getProperty(ContextEvent.PROP_RDF_OBJECT)).getURI().split("#");
	status += event.getProperty(ContextEvent.PROP_RDF_OBJECT);
//	status += arry[arry.length -1];

	contextEventModel.addElement(status);
}

/**
adds one ServiceRequest to the ServiceRequest-List
**/
public void addServiceRequest(ServiceRequest request) {
	if (request.getProperty(ServiceRequest.PROP_REQUESTED_SERVICE) instanceof Resource) {
		Resource res = (Resource) request.getProperty(ServiceRequest.PROP_REQUESTED_SERVICE);
		if (res.getURI().contains("urn:anonymous")) {
			Object ject =  res.getProperty(Resource.PROP_RDF_TYPE);
			if (ject instanceof ArrayList<?>) {
				ArrayList<?> list = (ArrayList<?>) ject;
				res = (Resource) list.get(0);
			}
		}
		serviceRequestModel.addElement(res);
	}
	else {
		Object object = request.getProperty(ServiceRequest.PROP_REQUESTED_SERVICE);
		if(object==null)
			object="unknown ServiceRequest";
		serviceRequestModel.addElement(object);
	}
}

/**
fills the two Lists after recording is stoped or changed
**/
private Pattern fillModel() {
	Pattern pattern = Activator.resourceOrginazer.getPatternByName(Name);

	Vector<Resource> events = pattern.getEvents();
	if(events.size() != 0){
		for (Iterator iterator = events.iterator(); iterator.hasNext();) {
			addContexEvent( (ContextEvent)iterator.next() );
		}
	}
	ArrayList<Object[]> requests = pattern.getRequests();
	if (requests.size() != 0) {
		for (Iterator iterator = requests.iterator(); iterator.hasNext();) {
			Object[] entry = (Object[]) iterator.next();
			ServiceRequest request = (ServiceRequest)entry[1];
			addServiceRequest(request);
		}
	}
	return pattern;
}

public void actionPerformed(ActionEvent e) {
	long currentTime = (new Date()).getTime(); 
	currentTime -= myContextSubscriber.getStartTime();

	long timeInSec = (currentTime / 1000) % 60;
	long timeInMin = (currentTime / 60000);

	if (timeInSec < 10) {
		durationLabel.setText(timeInMin +":0" + timeInSec);
	}
	else {
		durationLabel.setText(timeInMin +":" + timeInSec);
	}
	durationLabel.setText("recording: "+durationLabel.getText());
}

public void mouseClicked(MouseEvent e) {
	if (e.getClickCount() == 2) {
		JList source = (JList)e.getSource();

		String name = "";
		Resource resource;
		Pattern pattern = Activator.resourceOrginazer.getPatternByName(Name);

		if (source.equals(contextEventList)) {
			resource = pattern.getEvent(contextEventList.getSelectedIndex());
			name = "ContextEvent: " + resource.getURI();
		}else if(source.equals(serviceRequestList)){
			resource = pattern.getRequest(serviceRequestList.getSelectedIndex());
			name = "ServiceRequest: " + resource.getURI();
			}else
				return;
			JFrame frame = new JFrame(name); 
			JTextArea area = new JTextArea(Activator.contentSerializer.serialize(resource));
			frame.getContentPane().add(area);
			frame.pack();

			frame.setVisible(true);

			source.clearSelection();
		}

	}

	public void mouseEntered(MouseEvent e){}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			higherButton.setEnabled(true);
			deleteButton.setEnabled(true);
			lowerButton.setEnabled(true);
		}
	}
}
