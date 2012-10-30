package org.universAAL.codeassistant;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.part.EditorInputTransfer;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

    @Override
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        configurer.setTitle("---- PRE WINDOW OPEN----");
        configurer.addEditorAreaTransfer(EditorInputTransfer.getInstance());
        configurer.configureEditorAreaDropListener(new CodeAssistantEditorDropAdapter(configurer.getWindow()));        
    }
}
