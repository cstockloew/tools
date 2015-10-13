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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ProgressMonitor;

/**
 * 
 * @author Carsten Stockloew
 *
 */
public class Downloader {

	public static void download(String url, String branch, File localPath, final IProgressMonitor mon) {
		// File localPath = new File("D:\\temp\\Git");
		// localPath.delete();
		// System.out.println("Cloning from " + url + " to " + localPath);

		class ProgMon implements ProgressMonitor {
			@Override
			public void beginTask(String title, int totalWork) {
				// System.out.println(" - beginTask: " + title + " " +
				// totalWork);
				mon.beginTask(title, totalWork);
			}

			@Override
			public void endTask() {
				// System.out.println(" - endTask: ");
				mon.done();
			}

			@Override
			public boolean isCancelled() {
				return mon.isCanceled();
			}

			@Override
			public void start(int totalTasks) {
				// System.out.println(" - start: " + totalTasks);
			}

			@Override
			public void update(int completed) {
				// System.out.println(" - update: " + completed);
				mon.worked(completed);
			}
		}
		;

		ProgMon monitor = new ProgMon();

		try {
			Git result = Git.cloneRepository().setURI(url).setDirectory(localPath).setProgressMonitor(monitor)
					.setBranch(branch).call();
					// Note: the call() returns an opened repository already
					// which needs to be closed to avoid file handle leaks!
					// System.out.println("Having repository: " +
					// result.getRepository().getDirectory());

			// workaround for
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=474093
			result.getRepository().close();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

}
