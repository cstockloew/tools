package org.universaal.tools.conformanceTools.markers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.texteditor.ITextEditor;

public class MarkerResolution extends ExtensionContributionFactory {

	@Override
	public void createContributionItems(final IServiceLocator serviceLocator,	final IContributionRoot additions) {

		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {

				if((PlatformUI.getWorkbench().isStarting()))			
					Display.getDefault().timerExec(500, this); // delay if workbench is starting
				else{
					if(PlatformUI.getWorkbench() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null
							&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart() != null){

						ITextEditor editor = (ITextEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();

						try {
							if(editor != null)
								additions.addContributionItem(new MarkerMenuContribution(editor), null);
						} catch (CoreException e) {
							e.printStackTrace();
						}		
					}		
				}
			}
		});
	}

	public class MarkerMenuContribution extends ContributionItem {

		private ITextEditor editor;
		private IVerticalRulerInfo rulerInfo;
		private List<IMarker> markers;

		public MarkerMenuContribution(ITextEditor editor) throws CoreException{
			this.editor = editor;
			this.rulerInfo = getRulerInfo();
			this.markers = getMarkers();
		}

		private IVerticalRulerInfo getRulerInfo(){
			return (IVerticalRulerInfo) editor.getAdapter(IVerticalRulerInfo.class);
		}

		private List<IMarker> getMarkers() throws CoreException{
			List<IMarker> clickedOnMarkers = new ArrayList<IMarker>();
			for (IMarker marker : getAllMarkers()){
				if (markerHasBeenClicked(marker)){
					clickedOnMarkers.add(marker);
				}
			}

			return clickedOnMarkers;
		}

		//Determine whether the marker has been clicked using the ruler's mouse listener
		private boolean markerHasBeenClicked(IMarker marker){
			if(marker != null && rulerInfo.getLineOfLastMouseButtonActivity() != -1)
				return (marker.getAttribute(IMarker.LINE_NUMBER, 0)) == (rulerInfo.getLineOfLastMouseButtonActivity() + 1);
			return false;
		}

		//Get all My Markers for this source file
		private IMarker[] getAllMarkers() throws CoreException{

			List<IMarker> ctMarkers = new ArrayList<IMarker>();

			IProject[] ps = ResourcesPlugin.getWorkspace().getRoot().getProjects(); // search in projects in workspace
			for(int i = 0; i < ps.length; i++){
				if(ps[i] != null){
					IMarker[] ms = ps[i].findMarkers(null, true, IResource.DEPTH_ZERO);
					for(int j = 0; j < ms.length; j++){
						if(ms[i] != null && ((String)ms[i].getAttribute(IMarker.SOURCE_ID)).equals("uAAL_CT")){
							ctMarkers.add(ms[i]);
						}
					}
				}
			}

			//IMarker[] ms = ((FileEditorInput) editor.getEditorInput()).getFile().findMarkers(null, true, IResource.DEPTH_ZERO);

			return ctMarkers.toArray(new IMarker[0]);

			/*IMarker[] bk = ((FileEditorInput) editor.getEditorInput()).getFile().findMarkers(CTMarker.BOOKMARK, true, IResource.DEPTH_ZERO);
			IMarker[] pb = ((FileEditorInput) editor.getEditorInput()).getFile().findMarkers(CTMarker.PROBLEM, true, IResource.DEPTH_ZERO);
			IMarker[] tsk = ((FileEditorInput) editor.getEditorInput()).getFile().findMarkers(CTMarker.TASK, true, IResource.DEPTH_ZERO);

			IMarker[] ret = new IMarker[bk.length + pb.length + tsk.length];

			int r = -1;
			for(int i = 0; i < bk.length; i++){
				if(bk[i] != null)
					ret[++r] = bk[i];
			}
			for(int i = 0; i < pb.length; i++){
				if(pb[i] != null)
					ret[++r] = pb[i];
			}
			for(int i = 0; i < tsk.length; i++){
				if(tsk[i] != null)
					ret[++r] = tsk[i];
			}

			return ret;*/
		}

		@Override
		//Create a menu item for each marker on the line clicked on
		public void fill(Menu menu, int index){
			for (final IMarker marker : markers){
				MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
				menuItem.setText(marker.getAttribute(IMarker.MESSAGE, "PROVA PROVA"));
				menuItem.addSelectionListener(createDynamicSelectionListener(marker));
			}
		}

		//Action to be performed when clicking on the menu item is defined here
		private SelectionAdapter createDynamicSelectionListener(final IMarker marker){
			return new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					System.out.println(marker.getAttribute(IMarker.MESSAGE, "PROVA PROVA"));
				}
			};
		}
	}
}