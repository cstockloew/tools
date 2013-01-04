package org.universAAL.tools.makrorecorder.gui.patternedit;

import java.awt.GridBagConstraints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;

import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.tools.makrorecorder.Activator;
import org.universAAL.tools.makrorecorder.MakroRecorder;
import org.universAAL.tools.makrorecorder.gui.editContextEventDialog;

public class ResourcePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Vector<Resource> resources;
	private String title = "";
	
	private JList resourceList;
	private JLabel infoLabel;
	private JButton newButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton sendButton;
	
	public ResourcePanel(Vector<Resource> resources, String title) {
		super();
		this.resources = resources;
		this.title = title;
		init();
		reload();
	}
	
	private void init() {
		setBorder(javax.swing.BorderFactory.createTitledBorder(title));
        setLayout(new java.awt.GridBagLayout());
        GridBagConstraints gridBagConstraints;

        resourceList = new JList();
        resourceList.setModel(new DefaultListModel());
        resourceList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        resourceList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            	if(resourceList.getSelectedIndex()>-1) {
            		Resource r = resources.get(resourceList.getSelectedIndex());
        	        infoLabel.setText(info(r));     
        	        deleteButton.setEnabled(true);
        	        editButton.setEnabled(true);
        	        sendButton.setEnabled(true);
            	}
            }
        });
        
        resourceList.setDragEnabled(true);
        resourceList.setDropMode(DropMode.INSERT);
        resourceList.setTransferHandler(new TransferHandler(){
            /**
             * only support importing strings.
             */
            public boolean canImport(TransferHandler.TransferSupport info) {
                if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    return false;
                }
                return true;
            }
            
            protected Transferable createTransferable(JComponent c) {
                return new StringSelection((String)((JList)c).getSelectedValue());
            }
            
            /**
             * support move actions.
             */
            public int getSourceActions(JComponent c) {
                return TransferHandler.MOVE;
            }
           
            /**
             * Perform the actual import.
             */
            public boolean importData(TransferHandler.TransferSupport info) {
                if (!info.isDrop()) {
                    return false;
                }

                int seleIndex = ((JList)info.getComponent()).getSelectedIndex();
                int dropIndex = ((JList.DropLocation)info.getDropLocation()).getIndex();
                
                if(seleIndex > -1) {
                	Resource r = resources.get(seleIndex);
                	resources.remove(seleIndex);
                	if(seleIndex<dropIndex)
                		dropIndex--;
                	resources.insertElementAt(r, dropIndex);
                	reload();
                }
                return true;
            }
        });
        
        resourceList.setMinimumSize(new java.awt.Dimension(300, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        JScrollPane lsp = new JScrollPane(resourceList);
        lsp.setMinimumSize(new java.awt.Dimension(300, 200));
        lsp.setPreferredSize(lsp.getMaximumSize());
        add(lsp, gridBagConstraints);
        
        infoLabel = new JLabel();
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        JScrollPane isp = new JScrollPane(infoLabel);
        isp.setBorder(BorderFactory.createEmptyBorder());
        add(isp, gridBagConstraints);

        newButton = new JButton();
        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        add(newButton, gridBagConstraints);

        editButton = new JButton();
        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (resourceList.getSelectedIndex()>-1) {
                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        add(editButton, gridBagConstraints);
        
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (resourceList.getSelectedIndex()>-1) {
                    if (JOptionPane.showConfirmDialog(null, "delete Context-Event?") == JOptionPane.YES_OPTION) {
                        resources.remove(resourceList.getSelectedIndex());
                        reload();
                    }
                }
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        add(deleteButton, gridBagConstraints);
        
        sendButton = new JButton();
        sendButton.setText("Test");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if(resourceList.getSelectedIndex()>-1) {
	            	Resource r = resources.get(resourceList.getSelectedIndex());
	            	MakroRecorder.sendOut(r);
            	}
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        add(sendButton, gridBagConstraints);
	}
	
	public String info(Resource r) {
    	String info = "<html><body>";
    	if(r instanceof ServiceRequest)
    		info += genInfo((ServiceRequest)r);
    	else if(r instanceof ContextEvent)
    		info += genInfo((ContextEvent)r);
    	info += "</body></html>";
    	return info;
    }
    
    public String genInfo(ContextEvent ce) {
    	String info = "";
    	info += "<h2>Context-Event</h2>";
    	info += "Subject:<br/>";
    	info += ce.getRDFSubject().toString();
    	info += "<br/><br/>Predicate:<br/>";
    	info += ce.getRDFPredicate().toString();
    	info += "<br/><br/>Object:<br/>";
    	info += ce.getRDFObject().toString();
    	return info;
    }
    
    public String genInfo(ServiceRequest sr) {
    	return "<h2>Service-Request</h2><pre>"+Activator.getSerializer().serialize(sr)+"</pre>";
    }
    
    public String shortURI(String s) {
    	return s.substring(s.indexOf("#")+1);
    }
    
    private String shortResourceInfo(Resource r) {
    	String ret = "";
    	
    	if(r instanceof ContextEvent) {
    		ContextEvent ce = (ContextEvent)r;
    		ret += shortURI(ce.getRDFSubject().toString())+" ";
        	ret += shortURI(ce.getRDFPredicate())+" ";
        	ret += shortURI(ce.getRDFObject().toString());
    	}else if(r instanceof ServiceRequest) {
    		ServiceRequest sr = (ServiceRequest)r;
    		ret += shortURI(sr.getURI());
    	} else {
    		ret+= r.getClass()+": "+r.toString();
    	}
    	
    	return ret;
    }
    
    public void reload() {
    	DefaultListModel rllm = (DefaultListModel)resourceList.getModel();
        rllm.clear();
        for(Resource r : resources) {        	
           rllm.addElement(shortResourceInfo(r));
        }
        resourceList.setSelectedIndex(-1);
        infoLabel.setText("");
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);
        sendButton.setEnabled(false);
    }
}
