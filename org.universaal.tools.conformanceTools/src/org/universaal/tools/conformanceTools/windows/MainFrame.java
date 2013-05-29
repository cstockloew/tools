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
		layout.numColumns = 3; // buttons number
		layout.makeColumnsEqualWidth = false;
		shell.setLayout(layout);

		GridData data = new GridData(GridData.FILL_BOTH);

		Button cs = new Button(shell, SWT.PUSH);
		cs.setText("Check against code style rules");
		cs.setEnabled(true);
		cs.addMouseListener(new MouseListener() {

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
		cs.setLayoutData(data);

		Button uaal = new Button(shell, SWT.PUSH);
		uaal.setText("Check against uAAL rules");
		uaal.setEnabled(true);
		uaal.addMouseListener(new MouseListener() {

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
		uaal.setLayoutData(data);

		Button fc = new Button(shell, SWT.PUSH);
		fc.setText("Check proper project structure");
		fc.setEnabled(true);
		fc.addMouseListener(new MouseListener() {

			public void mouseUp(MouseEvent e) {

				shell.close();
				shell.dispose();
				instance.run(window, RunPlugin.FileConformance, event);
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseDoubleClick(MouseEvent e) {
			}
		});
		fc.setLayoutData(data);

		shell.pack();
		shell.open(); 
	}
}