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