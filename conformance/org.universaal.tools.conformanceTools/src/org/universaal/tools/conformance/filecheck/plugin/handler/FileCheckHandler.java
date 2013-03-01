package org.universaal.tools.conformance.filecheck.plugin.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.universaal.tools.conformanceTools.run.BugDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FileCheckHandler extends AbstractHandler {

	public ArrayList<BugDescriptor> bugsMap;
	public IProject prj;

	// to have images in the report
	public static String ERROR = "icon_error_sml.gif";
	public static String WARNING = "icon_warning_sml.gif";
	public static String ATTENTION = "icon_question_sml.gif";

	/**
	 * The name of the file containing the list of files to check. The extension
	 * of the file determines which parser will be used. Currently supports .xml
	 * and .txt.
	 */
	private static final String FILENAME = "files/filelist.xml";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// First retrieve current selection and check if its a project
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		// ISelection selection = HandlerUtil.getCurrentSelection(event);
		// TODO: Is this the same and faster?
		ISelection selection = window.getSelectionService().getSelection();
		if ((selection != null) && (selection instanceof StructuredSelection)) {
			Object selected = ((StructuredSelection) selection)
					.getFirstElement();
			// If the selection is a project, start the check
			//IProject prj;
			if (selected instanceof JavaProject) {
				// if you select them from pkg explorer they are JavaProjects
				prj = ((JavaProject) selected).getProject();
			} else if (selected instanceof IProject) {
				// if you select them from project explorer they are IProjects
				prj = ((IProject) selected);
			} else {
				MessageDialog.openInformation(window.getShell(), "FileCheck",
						"Selection is not a valid project.");
				return null;
			}

			// Main task - check files
			// TODO: do this inside a job
			//	    String report = check(prj);
			//@Manlio: VVVV This is where the test execution is invoked. Forget about anything else.
			checkFORPLUGIN(prj);
			//String report = checkFORPLUGIN(prj).toString();
			//			// TODO: Handle better the response
			//			MessageDialog.openInformation(window.getShell(), "FileCheck",
			//					report);

		} else {
			MessageDialog.openInformation(window.getShell(), "FileCheck",
					"No selection was made.");
		}
		return null;
	}

	/**
	 * Checks the existence of a set of files, from "files/filelist.*" config
	 * file.
	 * 
	 * @param prj
	 *            The project where the files should be
	 * @return a report of missing files (or files that shouldnt be there)
	 */
	private String check(IProject prj) {
		StringBuilder report = new StringBuilder();
		// Get the list of files to check
		ArrayList<FileEntry> l;
		if (FILENAME.endsWith(".xml")) {
			l = parseXML();
		} else {
			l = parseTXT();
		}
		// TODO: handle different files per MW-version
		if (l != null) {
			// For each entry inthe list to check...
			for (FileEntry f : l) {
				if (!f.exists) {
					// The file (or folder) should not exist. Check if they do
					if (prj.getFile(f.path).exists()
							|| prj.getFolder(f.path).exists()) {
						report.append("Element < " + f.path
								+ " > is present but it should not exist.\n");
					}
				} else {
					// The file (or folder) should exist. Check if they dont
					if (!prj.getFile(f.path).exists()
							&& !prj.getFolder(f.path).exists()) {
						report.append("Element < " + f.path
								+ " > is not present but it should exist.\n");
					}
				}
			}
			if (report.length() < 1) {
				// No reports
				return "All is OK";
			}
			return report.toString();
		} else {
			// There were no file entries to check, or there was an error...
			// TODO: communicate error?
			return "There are no checks to perform";
		}
	}

	// @Manlio: This is the method you are interested in: It runs the check and
	// generates a list of BugDescriptors, just like you do with the other
	// checks in ToolsRun
	/**
	 * Checks the existence of a set of files, from "files/filelist.*" config
	 * file.
	 * 
	 * @param prj
	 *            The project where the files should be
	 * @return a report of missing files (or files that shouldnt be there)
	 */
	private ArrayList<BugDescriptor> checkFORPLUGIN(IProject prj) {

		bugsMap = new ArrayList<BugDescriptor>();
		// Get the list of files to check
		ArrayList<FileEntry> l;
		if (FILENAME.endsWith(".xml")) {
			l = parseXML();
		} else {
			l = parseTXT();
		}
		// TODO: handle different files per MW-version
		if (l != null) {
			// For each entry inthe list to check...
			for (FileEntry f : l) {
				if (!f.exists) {
					// The file (or folder) should not exist. Check if they do
					if (prj.getFile(f.path).exists()
							|| prj.getFolder(f.path).exists()) {
						// @Manlio: I filled the descriptor data with what I
						// thought was best, but please set whatever you think
						// is better. Or if I missed something.
						BugDescriptor bd = new BugDescriptor();
						bd.setLine(-1);
						bd.setClazz("None");//@Manlio: Maybe we could set here the value of "f.path"? though it is not usually a class
						bd.setDescr("Element < " + f.path + " > is present but it should not exist.\n");
						bd.setErrorType("File Check");
						bd.setSeverity(8); 
						bd.setSeverityDescription("no_severity");
						bd.setImage(ATTENTION);
						bugsMap.add(bd);

					}
				} else {
					// The file (or folder) should exist. Check if they dont
					if (!prj.getFile(f.path).exists()
							&& !prj.getFolder(f.path).exists()) {

						BugDescriptor bd = new BugDescriptor();
						bd.setLine(-1);
						bd.setClazz("None");
						bd.setDescr("Element < " + f.path + " > is not present but it should exist.\n");
						bd.setErrorType("File Check");
						bd.setSeverity(13); 
						bd.setSeverityDescription("normal_severity");
						bd.setImage(WARNING);
						bugsMap.add(bd);
					}
				}
			}
		} 
		return bugsMap;
	}

	/**
	 * Parses the xml file containing the file list configuration.
	 * 
	 * @return A list of the file entries in the config file. Null if there was
	 *         no entry or tehr was an error.
	 */
	private static ArrayList<FileEntry> parseXML() {
		ArrayList<FileEntry> entries = new ArrayList<FileEntry>();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(FileCheckHandler.class
					.getClassLoader().getResourceAsStream(FILENAME));
			NodeList files = doc.getElementsByTagName("file");
			// For all <file> elements...
			for (int i = 0; i < files.getLength(); i++) {
				Node file = files.item(i);
				NodeList params = file.getChildNodes();
				FileEntry entry = new FileEntry();
				entry.exists = true; // Value by default;
				// Get each config parameter (location/exists/contains)
				for (int j = 0; j < params.getLength(); j++) {
					Node param = params.item(j);
					String name = param.getNodeName();
					if (name.equals("location")) {
						entry.path = param.getTextContent();
					} else if (name.equals("exists")) {
						entry.exists = param.getTextContent().equals("true");
					} else if (name.equals("contains")) {
						entry.contains = param.getTextContent();
						// TODO something with this?
					}
				}
				entries.add(entry);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
		// TODO communicate exceptions to user?
		if (entries.isEmpty()) {
			return null;
		}
		return entries;
	}

	/**
	 * Parses the txt file containing the file list configuration.
	 * 
	 * @return A list of the file entries in the config file. Null if there was
	 *         no entry or tehr was an error.
	 */
	private static ArrayList<FileEntry> parseTXT() {
		ArrayList<FileEntry> l = new ArrayList<FileEntry>();
		try {

			String readline = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(
					FileCheckHandler.class.getClassLoader()
					.getResourceAsStream(FILENAME)));

			readline = br.readLine();

			while (readline != null) {
				// For all files listed in the config file
				if (!readline.isEmpty()) {
					FileEntry f = new FileEntry();
					// If file name has a "!" prefix it means it shouldnt exist
					if (readline.startsWith("!")) {
						f.path = readline.substring(1);
						f.exists = false;
						// Otherwise it should exist
					} else {
						f.path = readline;
						f.exists = false;
					}
					l.add(f);
				}
				readline = br.readLine();
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		// TODO communicate exceptions to user?
		if (l.isEmpty()) {
			return null;
		}
		return l;
	}
}