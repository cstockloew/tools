package org.universaal.tools.codeassistantapplication.editor.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

public class TreeNode {
	private static TreeNode model;
	private TreeNode parent;
	private String name;
	private Image image;
	private Entity q;
	private List children = new ArrayList();

	public void setChildren(List children) {
		this.children = children;
	}

	public static TreeNode getInstance() {
		if (model == null) {
			model = getRootNode();
		}
		return model;
	}
	  
	private static TreeNode getRootNode() {
		TreeConstruction t = new TreeConstruction();
		
		return TreeConstruction.getRootNode();
	}

	public TreeNode(String name) {
		this.name = name;
	}

	public TreeNode(String name, Entity q) {
		this.name = name;
		this.q = q;
	}

	public Object getParent() {
		return parent;
	}

	public TreeNode addChild(TreeNode child) {
		children.add(child);
	    child.parent = this;
	    return this;
	}

	public List getChildren() {
	    return children;
	}

	public Entity getEntity() {
		return q;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public Entity getQ() {
		return q;
	}

	public void setQ(Entity q) {
		this.q = q;
	}

}
