package org.universaal.tools.buildserviceapplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class StartupClass implements IStartup {

	@Override
	public void earlyStartup() {
		String workspacePath = ResourcesPlugin.getWorkspace().getRoot()
				.getLocation().toOSString();
		File keyDir = new File(workspacePath + File.separator + "rundir"
				+ File.separator + "confadmin" + File.separator
				+ "mw.bus.model.osgi");
		keyDir.mkdirs();
		File keyFile = new File(keyDir + File.separator + "sodapop.key");
		if (!keyFile.exists()) {
			try {
				InputStream inputStream = getClass().getResourceAsStream(
						"/files/sodapop.key");
				OutputStream out = new FileOutputStream(keyFile);
				byte buf[] = new byte[1024];
				int len;
				while ((len = inputStream.read(buf)) > 0)
					out.write(buf, 0, len);
				out.close();
				inputStream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
		//redirect output to default console		
		MessageConsole console = new MessageConsole("My Console", null);
		console.activate();
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ console });
		MessageConsoleStream stream = console.newMessageStream();
		System.setOut(new PrintStream(stream));
		System.setErr(new PrintStream(stream)); 
	
		
	}
}
