package org.universaal.tools.conformanceTools.windows;

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

	public static void draw(final IWorkbenchWindow window){ 

		final Shell shell = new Shell(window.getShell());
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		shell.setLayout(layout);

		GridData data = new GridData(GridData.FILL_BOTH);
		Button one = new Button(shell, SWT.PUSH);
		one.setText("Run checkstyle plugin - advanced configuration");
		one.setEnabled(false);
		one.setLayoutData(data);

		data = new GridData(GridData.FILL_BOTH);
		Button two = new Button(shell, SWT.PUSH);
		two.setText("Run checkstyle plugin - default configuration");
		two.setEnabled(true);
		two.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {

				shell.close();
				shell.dispose();
				ToolsRun.run(window, RunPlugin.CheckStyle);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// Auto-generated method stub
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// Auto-generated method stub
			}
		});
		two.setLayoutData(data);

		data = new GridData(GridData.FILL_BOTH);
		Button three = new Button(shell, SWT.PUSH);
		three.setText("Run findbugs plugin - advanced configuration");
		three.setEnabled(false);
		three.setLayoutData(data);

		data = new GridData(GridData.FILL_BOTH);
		Button four = new Button(shell, SWT.PUSH);
		four.setText("Run findbugs plugin - default configuration");
		four.setEnabled(true);
		four.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {

				shell.close();
				shell.dispose();
				ToolsRun.run(window, RunPlugin.FindBugs);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// Auto-generated method stub
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// Auto-generated method stub
			}
		});
		four.setLayoutData(data);

		shell.pack();
		shell.open(); 
	}
}