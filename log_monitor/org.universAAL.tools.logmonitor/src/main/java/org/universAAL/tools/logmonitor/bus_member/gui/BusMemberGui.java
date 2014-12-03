/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.bus_member.gui;

import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.universAAL.tools.logmonitor.bus_member.MemberData;

/**
 * The main frame.
 * 
 * @author cstockloew
 * 
 */
public class BusMemberGui extends JPanel implements TreeSelectionListener,
	TreeModelListener {

    private static final long serialVersionUID = 1L;

    /**
     * The graphics panel where the content is drawn.
     */
    BusMemberPane pane = new BusMemberPane();

    private JTree tree;
    private DefaultMutableTreeNode root = null;
    DefaultTreeModel treeModel;

    private HashMap<String, DefaultMutableTreeNode> peers = new HashMap<String, DefaultMutableTreeNode>();
    private HashMap<String, DefaultMutableTreeNode> modules = new HashMap<String, DefaultMutableTreeNode>();
    private HashMap<String, DefaultMutableTreeNode> members = new HashMap<String, DefaultMutableTreeNode>();

    /**
     * Create the main frame.
     */
    public BusMemberGui() {
	this.setLayout(new BorderLayout());
	// Message overview
	root = new DefaultMutableTreeNode("root");
	treeModel = new DefaultTreeModel(root);
	tree = new JTree(treeModel);
	// tree.setRootVisible(false);
	// tree.addTreeSelectionListener(this);
	treeModel.addTreeModelListener(this);

	// overall view
	JScrollPane scrollPaneLeft = new JScrollPane(tree);
	JScrollPane scrollPaneRight = new JScrollPane(pane);

	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		scrollPaneLeft, scrollPaneRight);
	splitPane.setDividerLocation(0.4);
	add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Add a new entry.
     * 
     * @param m
     *            The MemberData.
     */
    public void add(MemberData m) {
	boolean first = false;
	// handle root = Space ID
	if (tree.isRootVisible()) {
	    // first element -> create root
	    root.setUserObject("Space: " + m.space);
	    tree.setRootVisible(true);
	    first = true;
	}

	// handle peers
	DefaultMutableTreeNode peerNode = peers.get(m.peer);
	if (peerNode == null) {
	    // peer not yet available
	    peerNode = new DefaultMutableTreeNode("Peer: " + m.peer);
	    root.add(peerNode);
	    treeModel.reload(root);
	    // add(peerNode, root);
	    peers.put(m.peer, peerNode);
	}

	// handle module
	DefaultMutableTreeNode moduleNode = modules
		.get(m.peer + "#" + m.module);
	if (moduleNode == null) {
	    // module not yet available
	    moduleNode = new DefaultMutableTreeNode("Module: " + m.module);
	    peerNode.add(moduleNode);
	    treeModel.reload(peerNode);
	    modules.put(m.peer + "#" + m.module, moduleNode);
	}

	// handle member
	DefaultMutableTreeNode memberNode = members.get(m.id);
	if (memberNode == null) {
	    // member not yet available (this should always be the case?)
	    memberNode = new DefaultMutableTreeNode("Member: "
		    + m.busType.name() + "-" + m.type + " - " + m.number);
	    moduleNode.add(memberNode);
	    // treeModel.reload(moduleNode);
	    treeModel.nodesWereInserted(moduleNode,
		    new int[] { moduleNode.getChildCount() - 1 });
	    members.put(m.id, memberNode);
	}

	if (first)
	    expand(moduleNode);
    }

    private void expand(DefaultMutableTreeNode node) {
	TreeNode[] path = node.getPath();
	TreePath treePath = new TreePath(path);
	tree.expandPath(treePath);
    }

    public void valueChanged(TreeSelectionEvent e) {
	// TODO Auto-generated method stub
    }

    public void treeNodesChanged(TreeModelEvent e) {
	// TODO Auto-generated method stub
    }

    public void treeNodesInserted(TreeModelEvent e) {
	// TODO Auto-generated method stub
    }

    public void treeNodesRemoved(TreeModelEvent e) {
	// TODO Auto-generated method stub
    }

    public void treeStructureChanged(TreeModelEvent e) {
	// TODO Auto-generated method stub
    }
}
