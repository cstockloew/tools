package org.universaal.tools.conformanceTools.windows;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.universaal.tools.conformance.verify.plugin.handlers.VerifierHandler;
import org.universaal.tools.conformanceTools.run.ToolsRun;
import org.universaal.tools.conformanceTools.utils.RunPlugin;

public class MainFrame {

	public static void draw(final IWorkbenchWindow window, final ExecutionEvent event){

		final ToolsRun instance = new ToolsRun();

		final Shell shell = new Shell(window.getShell());
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = true;
		shell.setLayout(layout);

		GridData data = new GridData(GridData.FILL_BOTH);
		/*Button one = new Button(shell, SWT.PUSH);
		one.setText("Run checkstyle plugin - advanced configuration");
		one.setEnabled(false);
		one.setLayoutData(data);*/

		Button two = new Button(shell, SWT.PUSH);
		//two.setText("Run checkstyle plugin - default configuration");
		two.setText("CheckStyle plugin");
		two.setEnabled(true);
		two.addMouseListener(new MouseListener() {

			public void mouseUp(MouseEvent e) {

				shell.close();
				shell.dispose();
				instance.run(window, RunPlugin.CheckStyle);//, event);
			}

			public void mouseDown(MouseEvent e) {
				// Auto-generated method stub
			}

			public void mouseDoubleClick(MouseEvent e) {
				// Auto-generated method stub
			}
		});
		two.setLayoutData(data);

		/*Button three = new Button(shell, SWT.PUSH);
		three.setText("Run findbugs plugin - advanced configuration");
		three.setEnabled(false);
		three.setLayoutData(data);*/

		Button four = new Button(shell, SWT.PUSH);
		//four.setText("Run findbugs plugin - default configuration");
		four.setText("FindBugs plugin");
		four.setEnabled(true);
		four.addMouseListener(new MouseListener() {

			public void mouseUp(MouseEvent e) {

				shell.close();
				shell.dispose();
				instance.run(window, RunPlugin.FindBugs);//, event);
			}

			public void mouseDown(MouseEvent e) {
				// Auto-generated method stub
			}

			public void mouseDoubleClick(MouseEvent e) {
				// Auto-generated method stub
			}
		});
		four.setLayoutData(data);

		Button five = new Button(shell, SWT.PUSH);
		//four.setText("Run findbugs plugin - default configuration");
		five.setText("Maven Verifier plugin");
		five.setEnabled(true);
		five.addMouseListener(new MouseListener() {

			public void mouseUp(MouseEvent e) {

				shell.close();
				shell.dispose();
				try{
					VerifierHandler vh = new VerifierHandler();
					vh.execute(event);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}

			public void mouseDown(MouseEvent e) {
				// Auto-generated method stub
			}

			public void mouseDoubleClick(MouseEvent e) {
				// Auto-generated method stub
			}
		});
		five.setLayoutData(data);

		shell.pack();
		shell.open(); 
	}
}