package org.universaal.tools.conformanceTools.windows;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.universaal.tools.conformanceTools.run.ToolsRun;
import org.universaal.tools.conformanceTools.utils.RunPlugin;

public class MainFrame {

	public static void draw(final IWorkbenchWindow window, final ExecutionEvent event){

		final ToolsRun instance = ToolsRun.getInstance();

		final Shell shell = new Shell(window.getShell());
		GridLayout layout = new GridLayout();
		layout.numColumns = 2; // buttons number
		layout.makeColumnsEqualWidth = false;
		shell.setLayout(layout);

		GridData data = new GridData(GridData.FILL_BOTH);

		Button two = new Button(shell, SWT.PUSH);
		two.setSize(80, 50);
		two.setText("Check against classical code style rules");
		two.setEnabled(true);
		two.addMouseListener(new MouseListener() {

			public void mouseUp(MouseEvent e) {

				shell.close();
				shell.dispose();
				instance.run(window, RunPlugin.CodeStyle);
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		two.setLayoutData(data);

		Button four = new Button(shell, SWT.PUSH);
		four.setSize(80, 50);
		four.setText("Check against uAAL rules");
		four.setEnabled(true);
		four.addMouseListener(new MouseListener() {

			public void mouseUp(MouseEvent e) {

				shell.close();
				shell.dispose();
				instance.run(window, RunPlugin.CustomChecks);
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		four.setLayoutData(data);

		/*Button five = new Button(shell, SWT.PUSH);
		five.setText("Verify files in project");
		five.setEnabled(false);
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
			}

			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		five.setLayoutData(data);*/

		shell.pack();
		shell.open(); 
	}
}