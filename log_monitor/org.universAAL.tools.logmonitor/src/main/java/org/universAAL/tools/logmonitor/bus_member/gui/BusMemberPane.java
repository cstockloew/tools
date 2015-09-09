/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut f�r Graphische Datenverarbeitung
 */
package org.universAAL.tools.logmonitor.bus_member.gui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.KeyStroke;
import javax.swing.text.DefaultCaret;

import org.universAAL.middleware.interfaces.PeerCard;
import org.universAAL.middleware.interfaces.aalspace.AALSpaceCard;
import org.universAAL.middleware.interfaces.aalspace.AALSpaceDescriptor;
import org.universAAL.tools.logmonitor.bus_member.MemberData;
import org.universAAL.tools.logmonitor.util.ClipboardHandling;
import org.universAAL.tools.logmonitor.util.HTMLPaneBase;

/**
 * 
 * @author Carsten Stockloew
 *
 */
public class BusMemberPane extends HTMLPaneBase {

    private static final long serialVersionUID = 1L;

    enum enType {
	NOTHING, PEER, SPACE, MEMBER
    }

    private enType type = enType.NOTHING;
    private PeerCard peerCard = null;
    private AALSpaceDescriptor space = null;
    private MemberData member = null;

    public BusMemberPane() {
	setEditable(false);
	setContentType("text/html");
	setCaret(new DefaultCaret());
	((DefaultCaret) getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

	// overwrite ctrl-c
	final BusMemberPane pane = this;
	getInputMap()
		.put(KeyStroke.getKeyStroke(KeyEvent.VK_C,
			InputEvent.CTRL_DOWN_MASK), "uaal_copy");
	getActionMap().put(
		"uaal_copy",
		new ClipboardHandling(new HashMap<String, String>(),
			getTransferHandler(), pane));
    }

    public void show() {
	type = enType.NOTHING;
	setNull();
	showHTML();
    }

    public void show(PeerCard peerCard) {
	type = enType.PEER;
	setNull();
	this.peerCard = peerCard;
	showHTML();
    }

    public void show(AALSpaceDescriptor space) {
	type = enType.SPACE;
	setNull();
	this.space = space;
	showHTML();
    }

    public void show(MemberData m) {
	type = enType.MEMBER;
	setNull();
	this.member = m;
	showHTML();
    }

    private void setNull() {
	peerCard = null;
	space = null;
    }

    private void showHTML() {
	StringBuilder s = new StringBuilder(
		"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"><html><body>\n");
	// s.append("Future version will show the selected profiles/patterns here.\n");
	switch (type) {
	case NOTHING:
	    s.append("");
	    break;
	case PEER:
	    createPeerHTML(s, peerCard);
	    break;
	case SPACE:
	    createSpaceHTML(s, space);
	    break;
	case MEMBER:
	    s.append("Future version will show member information here.");
	    break;
	}
	s.append("\n</body></html>");
	setText(s.toString());
    }

    private void createPeerHTML(StringBuilder s, PeerCard pc) {
	s.append("<h1>Peer</h1>\n");
	if (pc == null) {
	    s.append("no peer card available<br>\n");
	    return;
	}
	s.append(getTableStartHTML());
	s.append(getVTableRowWithTitleHTML("Peer ID", pc.getPeerID()));
	s.append(getVTableRowWithTitleHTML("Platform", pc.getPLATFORM_UNIT()));
	s.append(getVTableRowWithTitleHTML("Container", pc.getCONTAINER_UNIT()));
	s.append(getVTableRowWithTitleHTML("OS", pc.getOS()));
	s.append(getVTableRowWithTitleHTML("Role", pc.getRole().toString()));
	s.append(getTableEndHTML());
    }

    private void createSpaceHTML(StringBuilder s, AALSpaceDescriptor space) {
	s.append("<h1>Space</h1>\n");
	if (space == null) {
	    s.append("no space descriptor available<br>\n");
	    return;
	}
	AALSpaceCard sc = space.getSpaceCard();
	if (sc == null) {
	    s.append("no space card available<br>\n");
	    return;
	}
	s.append(getTableStartHTML());
	s.append(getVTableRowWithTitleHTML("Space name", sc.getSpaceName()));
	s.append(getVTableRowWithTitleHTML("Space ID", sc.getSpaceID()));
	s.append(getVTableRowWithTitleHTML("Description", sc.getDescription()));
	s.append(getVTableRowWithTitleHTML("Coordinator peer ID",
		sc.getPeerCoordinatorID()));
	s.append(getVTableRowWithTitleHTML("Peering channel",
		sc.getPeeringChannel()));
	s.append(getVTableRowWithTitleHTML("Peering channel name",
		sc.getPeeringChannelName()));
	s.append(getVTableRowWithTitleHTML("Retry",
		String.valueOf(sc.getRetry())));
	s.append(getVTableRowWithTitleHTML("Space life time",
		String.valueOf(sc.getAalSpaceLifeTime())));
	s.append(getTableEndHTML());
    }
}
