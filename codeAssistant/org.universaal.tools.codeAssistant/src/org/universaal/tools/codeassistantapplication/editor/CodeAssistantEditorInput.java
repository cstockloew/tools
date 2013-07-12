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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.universaal.tools.codeassistantapplication.editor.model.Entity;

public class CodeAssistantEditorInput implements IEditorInput, IPersistableElement {
	public static final String KEY_NAME = "PersistedEntity";
	private Entity entity;
    private final int id;

	public CodeAssistantEditorInput(Entity entity) {
		super();
		this.id = entity.getId();
		this.entity = entity;
	}
	public IPersistableElement getPersistable() {
		return this;
	}
	public String getFactoryId() {
		return CodeAssistantEditorInputFactory.ID;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void saveState(IMemento memento) {
		memento.putString(KEY_NAME, this.entity.toString());
	}
	
    public CodeAssistantEditorInput(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
	
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName() {
		return "Code Assistant Editor";
	}
    
	@Override
    public String getToolTipText() {
        return "Displays an OWL element";
    }
	
    @Override
	public boolean equals(Object obj) { 
		return false; 
	}
	
}