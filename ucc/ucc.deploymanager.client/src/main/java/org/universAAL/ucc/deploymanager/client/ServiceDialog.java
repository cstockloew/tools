package org.universAAL.ucc.deploymanager.client;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebParam;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ServiceDialog extends JFrame implements ChangeListener  {

	private static final long serialVersionUID = 1L;

    public interface ServiceDialogListener {
    
    	void install(String sessionKey, String usrvfile);
        
    	void update(String sessionKey, String usrvfile);
    }
    
    private final JPanel panel1 = new JPanel(new GridBagLayout());

    private final JPanel panel2 = new JPanel(new GridBagLayout());

    private final JRadioButton rb1 = new JRadioButton("install");

    private final JRadioButton rb2 = new JRadioButton("update");

    private final ServiceDialogListener serviceDialogListener;
    
	public ServiceDialog(ServiceDialogListener dialogListener) {
    
        serviceDialogListener = dialogListener;
    
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setContentPane(panel);

        // install panel
        rb1.setSelected(true);
        rb1.setAlignmentX(Component.LEFT_ALIGNMENT);
        rb1.addChangeListener(this);
        panel.add(rb1);

        panel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        GridBagConstraints c3 = new GridBagConstraints();
        GridBagConstraints c4 = new GridBagConstraints();

        JLabel lbId1 = new JLabel("sessionKey: ");
        c1.weightx = 0.0;
        c1.gridx = 0;
        c1.gridy = 0;
        c1.insets = new Insets(0, 25, 0, 0);
        c1.anchor = GridBagConstraints.LINE_START;
        panel1.add(lbId1, c1);

        final JTextField tfValue1 = new JTextField(20);
        c2.weightx = 0.2;
        c2.gridx = 1;
        c2.gridy = 0;
        c2.insets = new Insets(0, 10, 0, 0);
        c2.anchor = GridBagConstraints.LINE_START;
        panel1.add(tfValue1, c2);

        JLabel lbId3 = new JLabel("usrv file url: ");
        c3.weightx = 0.0;
        c3.gridx = 0;
        c3.gridy = 1;
        c3.insets = new Insets(0, 25, 0, 0);
        c3.anchor = GridBagConstraints.LINE_START;
        panel1.add(lbId3, c3);

        final JTextField tfValue2 = new JTextField(20);
        c4.weightx = 0.2;
        c4.gridx = 1;
        c4.gridy = 1;
        c4.insets = new Insets(0, 10, 0, 0);
        c4.anchor = GridBagConstraints.LINE_START;
        panel1.add(tfValue2, c4);
        
        panel.add(panel1);
        enablePanel(panel1, true);

        panel.add(new JLabel(" ")); // add a spacer

        // update panel
        rb2.setAlignmentX(Component.LEFT_ALIGNMENT);
        rb2.addChangeListener(this);
        panel.add(rb2);

        panel2.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbId2 = new JLabel("sessionKey: ");
        c1.weightx = 0.0;
        c1.gridx = 0;
        c1.gridy = 0;
        c1.insets = new Insets(0, 25, 0, 0);
        c1.anchor = GridBagConstraints.LINE_START;
        panel2.add(lbId2, c1);

        final JTextField tfValue3 = new JTextField(20);
        c2.weightx = 0.2;
        c2.gridx = 1;
        c2.gridy = 0;
        c2.insets = new Insets(0, 10, 0, 0);
        c2.anchor = GridBagConstraints.LINE_START;
        panel2.add(tfValue3, c2);
        
        JLabel lbId4 = new JLabel("usrv file url: ");
        c3.weightx = 0.0;
        c3.gridx = 0;
        c3.gridy = 1;
        c3.insets = new Insets(0, 25, 0, 0);
        c3.anchor = GridBagConstraints.LINE_START;
        panel2.add(lbId4, c3);

        final JTextField tfValue4 = new JTextField(20);
        c4.weightx = 0.2;
        c4.gridx = 1;
        c4.gridy = 1;
        c4.insets = new Insets(0, 10, 0, 0);
        c4.anchor = GridBagConstraints.LINE_START;
        panel2.add(tfValue4, c4);

        panel.add(panel2);
        enablePanel(panel2, false);
        
        panel.add(new JLabel(" ")); // add a spacer

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton b1 = new JButton("Invoke");
        buttons.add(b1);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (rb1.isSelected()) {
                    if (serviceDialogListener != null) {
                        String valueStr = tfValue1.getText();    
                        String urlStr = tfValue2.getText();                       
						serviceDialogListener.install(valueStr, urlStr);						                                                                     
                    }
                } else if (rb2.isSelected()) {
                        String valueStr = tfValue3.getText();  
                        String urlStr = tfValue4.getText();
						serviceDialogListener.update(valueStr, urlStr);						                        
	            }
            }
        });

        panel.add(buttons);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);

        setLocationRelativeTo(null); // centers frame on screen

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Deploy Manager Client");
        pack();
	}

	@Override
	public void stateChanged(ChangeEvent changeEvent) {
        enablePanel(panel1, rb1.isSelected());
        enablePanel(panel2, rb2.isSelected());
	}

    private static void enablePanel(JPanel panel, boolean b) {
        for (Component c : panel.getComponents()) {
            c.setEnabled(b);
        }
    }

	public static void main(String[] args) {
        final ServiceDialog testDialog = new ServiceDialog(null);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                testDialog.setVisible(true);
            }
        });
	}

}
