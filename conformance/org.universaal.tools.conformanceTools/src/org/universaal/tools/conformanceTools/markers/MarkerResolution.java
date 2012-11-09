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
	public void createContributionItems(final IServiceLocator serviceLocator, final IContributionRoot additions) {

		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {

				if((PlatformUI.getWorkbench().isStarting()))			
					Display.getDefault().timerExec(500, this); // delay if workbench is starting
				else{
					if(PlatformUI.getWorkbench() != null 
							&& PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null 
							&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null
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
		private volatile IVerticalRulerInfo rulerInfo;
		private List<IMarker> markers = new ArrayList<IMarker>();
		private CTMarker mks = Markers.getInstance();

		public MarkerMenuContribution(ITextEditor editor) throws CoreException{
			this.editor = editor;
			this.rulerInfo = getRulerInfo();

			mks.subscribe(new NewCTmarkerListener() {

				@Override
				public void newMarker(IMarker marker) {
					try {
						markers = getMarkers(marker);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			});
		}

		private IVerticalRulerInfo getRulerInfo(){
			return (IVerticalRulerInfo) editor.getAdapter(IVerticalRulerInfo.class);					
		}

		private List<IMarker> getMarkers(IMarker m) throws CoreException{
			List<IMarker> clickedOnMarkers = new ArrayList<IMarker>();
			for (IMarker marker : getAllMarkers(m)){
				if(markerHasBeenClicked(marker)){
					clickedOnMarkers.add(marker);
				}
			}

			return clickedOnMarkers;
		}

		//Determine whether the marker has been clicked using the ruler's mouse listener
		private boolean markerHasBeenClicked(final IMarker marker){
			if(marker != null)
				return (marker.getAttribute(IMarker.LINE_NUMBER, 0) == (rulerInfo.getLineOfLastMouseButtonActivity() + 1));
			return false;
		}

		//Get all My Markers for this source file
		private IMarker[] getAllMarkers(IMarker m) throws CoreException{

			List<IMarker> ctMarkers = new ArrayList<IMarker>();
			ctMarkers.add(m);

			//** search in projects in workspace only the first time, new markers added by me are already tracked
			IProject[] ps = ResourcesPlugin.getWorkspace().getRoot().getProjects();

			for(int i = 0; i < ps.length; i++){
				if(ps[i] != null){
					IMarker[] ms = ps[i].findMarkers(null, true, IResource.DEPTH_ZERO);
					for(int j = 0; j < ms.length; j++){
						if(ms[i] != null && /*ms[i].getAttribute(IMarker.SOURCE_ID) != null && ((String)ms[i].getAttribute(IMarker.SOURCE_ID)).equals(CTMarker.ID)*/
								ms[i].getType().equals(CTMarker.ID)){
							ctMarkers.add(ms[i]);
						}
					}
				}
			} 

			return ctMarkers.toArray(new IMarker[ctMarkers.size()]);
		}

		@Override
		//Create a menu item for each marker on the line clicked on
		public void fill(Menu menu, int index){
			for (final IMarker marker : markers){
				MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
				menuItem.setText(marker.getAttribute(IMarker.MESSAGE, ""));
				menuItem.addSelectionListener(createDynamicSelectionListener(marker));
			}
		}

		//Action to be performed when clicking on the menu item is defined here
		private SelectionAdapter createDynamicSelectionListener(final IMarker marker){
			return new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					System.out.println(marker.getAttribute(IMarker.MESSAGE, ""));
				}
			};
		}
	}
}