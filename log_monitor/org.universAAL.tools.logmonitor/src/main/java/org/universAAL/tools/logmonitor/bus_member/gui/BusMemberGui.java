/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.bus_member.gui;

import java.awt.BorderLayout;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.interfaces.aalspace.AALSpaceDescriptor;
import org.universAAL.tools.logmonitor.bus_member.MemberData;

/**
 * The main frame.
 * 
 * @author cstockloew
 * 
 */
public class BusMemberGui extends JPanel implements TreeSelectionListener {

    private static final long serialVersionUID = 1L;

    /**
     * The graphics panel where the content is drawn.
     */
    BusMemberPane pane = new BusMemberPane();

    private JTree tree;
    private DefaultMutableTreeNode root = null;
    DefaultTreeModel treeModel;

    private Map<String, DefaultMutableTreeNode> peers = new Hashtable<String, DefaultMutableTreeNode>();
    private Map<String, DefaultMutableTreeNode> modules = new Hashtable<String, DefaultMutableTreeNode>();
    private Map<String, DefaultMutableTreeNode> members = new Hashtable<String, DefaultMutableTreeNode>();

    private Hashtable<String, PeerCard> peerCards = new Hashtable<String, PeerCard>();
    private Hashtable<String, MemberData> memberData = new Hashtable<String, MemberData>();
    private AALSpaceDescriptor space = null;

    /**
     * Create the main frame.
     */
    public BusMemberGui() {
	this.setLayout(new BorderLayout());
	// Message overview
	root = new DefaultMutableTreeNode("root");
	treeModel = new DefaultTreeModel(root);
	tree = new JTree(treeModel);
	tree.setRootVisible(false);
	tree.addTreeSelectionListener(this);

	// overall view
	JScrollPane scrollPaneLeft = new JScrollPane(tree);
	JScrollPane scrollPaneRight = new JScrollPane(pane);

	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		scrollPaneLeft, scrollPaneRight);
	splitPane.setDividerLocation(0.4);
	add(splitPane, BorderLayout.CENTER);
    }

    public void add(final PeerCard peer) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		if (peerCards.put(peer.getPeerID(), peer) != null)
		    // this peer was known already
		    return;
		// add a tree node
		addPeerNode(peer.getPeerID());
	    }
	});
    }

    public void remove(final PeerCard peer) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		peerCards.remove(peer.getPeerID());
	    }
	});
    }

    private DefaultMutableTreeNode addPeerNode(String id) {
	DefaultMutableTreeNode peerNode = peers.get(id);
	if (peerNode == null) {
	    // peer not yet available
	    peerNode = new DefaultMutableTreeNode("Peer: " + id);
	    root.add(peerNode);
	    treeModel.reload(root);
	    // add(peerNode, root);
	    peers.put(id, peerNode);
	}
	return peerNode;
    }

    /**
     * Add a new entry.
     * 
     * @param m
     *            The MemberData.
     */
    public void add(final MemberData m) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		memberData.put(m.id, m);
		boolean first = false;
		// handle root = Space ID
		if (!tree.isRootVisible()) {
		    // first element -> create root
		    root.setUserObject("Space: " + m.space);
		    tree.setRootVisible(true);
		    first = true;
		}

		// handle peers
		DefaultMutableTreeNode peerNode = addPeerNode(m.peer);

		// handle module
		DefaultMutableTreeNode moduleNode = modules.get(m.peer + "#"
			+ m.module);
		if (moduleNode == null) {
		    // module not yet available
		    moduleNode = new DefaultMutableTreeNode("Module: "
			    + m.module);
		    peerNode.add(moduleNode);
		    treeModel.reload(peerNode);
		    modules.put(m.peer + "#" + m.module, moduleNode);
		}

		// handle member
		DefaultMutableTreeNode memberNode = members.get(m.id);
		if (memberNode == null) {
		    // member not yet available (this should always be the
		    // case?)
		    memberNode = new DefaultMutableTreeNode("Member: "
			    + m.busType.name() + "-" + m.type + " - "
			    + m.number);
		    moduleNode.add(memberNode);
		    // treeModel.reload(moduleNode);
		    treeModel.nodesWereInserted(moduleNode,
			    new int[] { moduleNode.getChildCount() - 1 });
		    members.put(m.id, memberNode);
		}

		if (first)
		    expand(moduleNode);
	    }
	});
    }

    public void remove(final MemberData m) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		memberData.remove(m.id);
		// get module (= parent of this member)
		final DefaultMutableTreeNode moduleNode = modules.get(m.peer
			+ "#" + m.module);
		if (moduleNode == null) {
		    System.out
			    .println("ERROR: module node not found while removing");
		    return;
		}

		// get member
		final DefaultMutableTreeNode memberNode = members.get(m.id);
		if (memberNode == null) {
		    System.out
			    .println("ERROR: member node not found while removing");
		    return;
		}

		// remove node (from node and from internal map)
		final int idx = moduleNode.getIndex(memberNode);
		if (idx == -1) {
		    System.out
			    .println("ERROR: member node not indexed while removing");
		    return;
		}
		moduleNode.remove(idx);
		members.remove(m.id);

		// update view
		// SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		treeModel.nodesWereRemoved(moduleNode, new int[] { idx },
			new Object[] { memberNode });
		// }
		// });
	    }
	});
    }

    private void expand(DefaultMutableTreeNode node) {
	TreeNode[] path = node.getPath();
	TreePath treePath = new TreePath(path);
	tree.expandPath(treePath);
    }

    public void setSpace(AALSpaceDescriptor space) {
	this.space = space;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
	final JTree tree = (JTree) e.getSource();
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
			.getLastSelectedPathComponent();
		// String selectedNodeName = selectedNode.toString();

		if (members.containsValue(node)) {
		} else if (modules.containsValue(node)) {
		} else if (peers.containsValue(node)) {
		} else if (node == root) {
		}
	    }
	});
    }
}
