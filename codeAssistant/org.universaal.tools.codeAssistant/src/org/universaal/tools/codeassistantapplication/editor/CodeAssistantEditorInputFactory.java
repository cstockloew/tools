/*****************************************************************************************
	Copyright 2012-2014 CERTH-HIT, http://www.hit.certh.gr/
	Hellenic Institute of Transport (HIT)
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
 *****************************************************************************************/

package org.universaal.tools.codeassistantapplication.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.universaal.tools.codeassistantapplication.editor.model.Entity;
import org.universaal.tools.codeassistantapplication.editor.model.OntologyClass;
import org.universaal.tools.codeassistantapplication.editor.model.TreeNode;

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