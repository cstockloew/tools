package org.universAAL.codeassistant.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.universAAL.codeassistant.editor.model.Entity;
import org.universAAL.codeassistant.editor.model.OntologyClass;
import org.universAAL.codeassistant.editor.model.TreeNode;

import com.hp.hpl.jena.ontology.OntClass;


public class CodeAssistantEditorInputFactory implements IElementFactory {
	public static final String ID ="org.universAAL.codeassistant.editor.codeassistanteditorinputfactory";
	public static List<Entity> entities = new ArrayList<Entity>(); 
	
    public static Entity getTreeNodeChildren(TreeNode n) {
        if (n.getChildren().size()>0) {
        	for (int i = 0; i<n.getChildren().size(); i++) {
        		TreeNode nn = (TreeNode) n.getChildren().get(i);
        		entities.add(getTreeNodeChildren(nn));
        	}
        }
        Entity entity = new Entity(n.getEntity().getText(), n.getEntity().getUri(), n.getEntity().getProperty(), n.getEntity().getRDFType(), n.getEntity().getRange());
        return entity;
    }
	
	public IAdaptable createElement(IMemento memento) {
		String entityText = memento.getString(CodeAssistantEditorInput.KEY_NAME);
		List list = TreeNode.getInstance().getChildren();
		for (int i=0; i<list.size(); i++){
			TreeNode tn = (TreeNode)list.get(i);
			entities.add(new Entity(tn.getEntity().getText(), tn.getEntity().getUri(), tn.getEntity().getProperty(), tn.getEntity().getRDFType(), tn.getEntity().getRange()));
			if (tn.getChildren().size()>0)
				for (int j=0; j<tn.getChildren().size(); j++)
					entities.add(getTreeNodeChildren(tn));
		}

		for (Entity q : entities) { 
			if (q.getText().equals(entityText)) { 
				return new CodeAssistantEditorInput(q); 
			} 
		}
		return null;
	}
}