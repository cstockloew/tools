/*	
	Copyright 2007-2016 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institute for Computer Graphics Research
	
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
 */
package org.universaal.tools.envsetup.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * 
 * @author Carsten Stockloew
 *
 */
public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		// just a dummy to get the activator started
		// System.out.println(" -- earlyStartup");

		createRundir();
		createConsole();
	}

	private void createConsole() {
		// redirect output to default console
		MessageConsole console = new MessageConsole("My Console", null);
		console.activate();
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
		MessageConsoleStream stream = console.newMessageStream();
		System.setOut(new PrintStream(stream));
		System.setErr(new PrintStream(stream));
	}

	private void createRundir() {

		URL url = Startup.class.getResource("files/");
		if (url == null) {
			// error - missing folder
		} else {
			File dir;
			try {
				dir = new File(url.toURI());
				for (File nextFile : dir.listFiles()) {
					System.out.println(" -- file: " + nextFile.toString());
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

		if (true)
			return;
		String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString();
		File keyDir = new File(workspacePath + File.separator + "rundir" + File.separator + "confadmin" + File.separator
				+ "mw.bus.model.osgi");
		keyDir.mkdirs();
		File keyFile = new File(keyDir + File.separator + "sodapop.key");
		if (!keyFile.exists()) {
			try {
				InputStream inputStream = getClass().getResourceAsStream("/files/sodapop.key");
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
	}
}
