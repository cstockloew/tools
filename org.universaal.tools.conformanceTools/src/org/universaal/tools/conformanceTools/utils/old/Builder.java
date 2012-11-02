package org.universaal.tools.conformanceTools.utils.old;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.universaal.tools.conformanceTools.utils.RunPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Builder {

	public static boolean create(String projectPath, RunPlugin plugin) throws IllegalArgumentException{

		if(projectPath == null || projectPath.isEmpty())
			throw new IllegalArgumentException();

		projectPath = projectPath.trim();

		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			String reportDir = "";
			if(plugin == RunPlugin.CheckStyle)
				reportDir = OptionsParser.get("reportDirCKS");//"checkstyle_violations_reports";
			else if(plugin == RunPlugin.FindBugs)
				reportDir = OptionsParser.get("reportDirFB");//"findbugs_violations_reports";

			Document doc = docBuilder.newDocument();

			// <project name="checkstyle" default="checkstyle.run" basedir=".">
			Element rootElement = doc.createElement("project");
			rootElement.setAttribute("basedir", ".");
			if(plugin == RunPlugin.CheckStyle)
				rootElement.setAttribute("default", "checkstyle.run");
			else if(plugin == RunPlugin.FindBugs)
				rootElement.setAttribute("default", "findbugs.run");
			rootElement.setAttribute("name", projectPath.substring(1, projectPath.length()));
			doc.appendChild(rootElement);

			// <property name="target.dir" value="${target.project}/checkstyle_violations_reports" />
			Element violFolder = doc.createElement("property");
			violFolder.setAttribute("name", "target.dir");
			violFolder.setAttribute("value", reportDir);
			rootElement.appendChild(violFolder);

			// <path id="run.classpath">
			Element runClasspath = doc.createElement("path");
			runClasspath.setAttribute("id", "run.classpath");
			rootElement.appendChild(runClasspath);

			if(plugin == RunPlugin.CheckStyle){
				//CHECKSTYLE
				// <pathelement location="lib/checkstyle-5.5-all.jar"/>
				Element path = doc.createElement("pathelement");
				path.setAttribute("location", OptionsParser.get("checkstyleJarPath")+"/"+OptionsParser.get("checkstyleJar"));//"./lib/checkstyle-5.5-all.jar");
				runClasspath.appendChild(path);

				// <target name="checkstyle.run" description="">
				Element target = doc.createElement("target");
				target.setAttribute("name", "checkstyle.run");
				target.setAttribute("description", "CheckStyle task");
				rootElement.appendChild(target);

				//<taskdef name="checkstyle" classname="com.puppycrawl.tools.checkstyle.CheckStyleTask">
				Element taskdef = doc.createElement("taskdef");
				taskdef.setAttribute("name", "checkstyle");
				taskdef.setAttribute("classname",  OptionsParser.get("checkstyleAntTask"));//"com.puppycrawl.tools.checkstyle.CheckStyleTask");
				target.appendChild(taskdef);

				//<classpath refid="run.classpath"/>
				Element classpath = doc.createElement("classpath");
				classpath.setAttribute("refid", "run.classpath");
				taskdef.appendChild(classpath);

				//<property name="check.config" location="checkstyle_checks.xml"/>
				Element checkstyle_checks = doc.createElement("property");
				checkstyle_checks.setAttribute("name", "check.config");
				checkstyle_checks.setAttribute("location", OptionsParser.get("checkstyleChecksFile"));//"sun_checks.xml");
				target.appendChild(checkstyle_checks);

				//<property name="translation.severity" value="ignore"/>
				Element translation = doc.createElement("property");
				translation.setAttribute("name", "translation.severity");
				translation.setAttribute("value", "ignore");
				target.appendChild(translation);

				//<checkstyle config="${check.config}" failOnViolation="false">
				Element ckConf = doc.createElement("checkstyle");
				ckConf.setAttribute("config", "${check.config}");
				ckConf.setAttribute("failOnViolation", "false");
				target.appendChild(ckConf);

				//    <fileset dir="${target.project}/src" includes="**/*.java" />
				Element fileset = doc.createElement("fileset");
				fileset.setAttribute("dir", "./src");
				fileset.setAttribute("includes", "**/*.java");
				ckConf.appendChild(fileset);

				//    <formatter type="plain" toFile="${target.dir}/cs_errors.txt" />
				Element formatterP = doc.createElement("formatter");
				formatterP.setAttribute("type", "plain");
				formatterP.setAttribute("toFile", "./${target.dir}/"+OptionsParser.get("checkstyleReportFileName")+".txt");
				ckConf.appendChild(formatterP);

				//    <formatter type="xml" toFile="${target.dir}/cs_errors.xml" />
				Element formatterX = doc.createElement("formatter");
				formatterX.setAttribute("type", "xml");
				formatterX.setAttribute("toFile", "./${target.dir}/"+OptionsParser.get("checkstyleReportFileName")+".xml");
				ckConf.appendChild(formatterX);
				//CHECKSTYLE
			}
			else if(plugin == RunPlugin.FindBugs){
				//FINDBUGS
				//<pathelement location="lib/findbugs.jar"/>
				Element path = doc.createElement("pathelement");
				path.setAttribute("location", OptionsParser.get("findbugsJarPath")+OptionsParser.get("findbugsJar"));//"./lib/findbugs/findbugs.jar");
				runClasspath.appendChild(path);

				//<target name="findbugs" depends="jar">				
				Element target = doc.createElement("target");
				target.setAttribute("name", "findbugs.run");
				target.setAttribute("description", "FindBugs task");
				rootElement.appendChild(target);				

				//<taskdef name="findbugs" classname="edu.umd.cs.findbugs.anttask.FindBugsTask"/>
				Element taskdef = doc.createElement("taskdef");
				taskdef.setAttribute("name", "findbugs");
				taskdef.setAttribute("classname", OptionsParser.get("findbugsAntTask"));//"edu.umd.cs.findbugs.anttask.FindBugsTask");
				target.appendChild(taskdef);

				//<classpath refid="run.classpath"/>
				Element classpath = doc.createElement("classpath");
				classpath.setAttribute("refid", "run.classpath");
				taskdef.appendChild(classpath);

				//<findbugs home="${findbugs.home}" output="xml" outputFile="findbugs.xml">
				Element findbugs = doc.createElement("findbugs");
				findbugs.setAttribute("home", "./lib/findbugs/");
				findbugs.setAttribute("output", "xml");
				findbugs.setAttribute("outputFile", "${target.dir}/"+OptionsParser.get("findbugsReportFileName")+".xml");
				target.appendChild(findbugs);

				//<fileset dir="bin" includes="**/*.class" />
				Element fileset = doc.createElement("fileset");
				fileset.setAttribute("dir", "bin");
				fileset.setAttribute("includes", "**/*.class");
				findbugs.appendChild(fileset);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource sourceDom = new DOMSource(doc);

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectPath);
			StreamResult result = null;
			if(plugin == RunPlugin.CheckStyle)
				result = new StreamResult(new File(project.getLocation()+"/"+OptionsParser.get("checkstyleAntRunner")));//checkstyle_run.xml"));
			else if(plugin == RunPlugin.FindBugs)
				result = new StreamResult(new File(project.getLocation()+"/"+OptionsParser.get("findbugsAntRunner")));//findbugs_run.xml"));

			if(result != null){
				transformer.transform(sourceDom, result);
				return true;
			}
			else
				return false;
		} 
		catch(Exception pce) {
			pce.printStackTrace();
			return false;
		}
	}
}