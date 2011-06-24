package org.universaal.tools.buildserviceapplication.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.cli.MavenCli;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class CreateFelixPropertiesFile {
	static public String groupId = "";
	static public String artifactId = "";
	static public String artifactVersion = "";

	public CreateFelixPropertiesFile() {
		getBundleProperties();
	}

	private void getBundleProperties() {
		try {
			Reader reader = new FileReader(BuildAction.getSelectedProjectPath()
					+ "/pom.xml");
			MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
			Model model = xpp3Reader.read(reader);
			groupId = model.getGroupId();
			artifactId = model.getArtifactId();
			artifactVersion = model.getVersion();

			// if groupId is null then search within its parent
			if (groupId == null && model.getParent() != null) {
				groupId = model.getParent().getGroupId();
			}
			// if artifactId is null then search within its parent
			if (artifactId == null && model.getParent() != null) {
				artifactId = model.getParent().getArtifactId();
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean createFile() {

		// add Felix jar to project classPath
		// addFelixToClassPath();

		List<Dependency> pomDependencies = new ArrayList<Dependency>();
		List<Dependency> ontologyDependencies = new ArrayList<Dependency>();
		try {
			// Path to your local Maven repository
			Reader reader = new FileReader(BuildAction.getSelectedProjectPath()
					+ "/pom.xml");
			try {
				MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
				Model model = xpp3Reader.read(reader);
				List<Dependency> dependencies = model.getDependencies();

				for (int i = 0; i < dependencies.size(); i++) {
					pomDependencies.add(dependencies.get(i));
					Dependency dependency = dependencies.get(i);
					if (dependency.getGroupId().equals(
							"org.universAAL.ontology")) {
						ontologyDependencies.add(dependency);
					}
				}
				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				reader.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// path to add properties: ./conf
		File conf = new File(Platform.getLocation().toString()
				+ "/.felix/conf/"+artifactId);
		conf.mkdirs();

		String pathToAddConf = conf.getAbsolutePath();
		// File dir = new File(pathToAddConf);
		// dir.mkdirs();
		File launchFile = new File(pathToAddConf + "/config.properties");
		if (launchFile.exists()) {
			launchFile.delete();
		}
		try {
			FileWriter fstream = new FileWriter(pathToAddConf
					+ "/config.properties");
			BufferedWriter out = new BufferedWriter(fstream);

			out.write("##############################\n");
			out.write("# Felix settings\n");
			out.write("##############################\n");
			out.write("org.osgi.framework.startlevel.beginning=8\n");
			out.write("felix.startlevel.bundle=8\n");
			out.write("felix.startlevel.framework=8\n");
			out.write("felix.log.level=8\n");
			out.write("org.osgi.framework.storage.clean=none\n");
			out.write("org.osgi.framework.storage="+Platform.getLocation().toString()+"/.felix/"+artifactId+"\n");
			out.write("felix.cache.rootdir="+Platform.getLocation().toString()+"/.felix/"+artifactId+"\n");
			out.write("felix.service.urlhandlers=true\n");
			out.write("org.osgi.framework.executionenvironment=J2SE-1.2,J2SE-1.3,J2SE-1.4,J2SE-1.5,JRE-1.1,JavaSE-1.6,OSGi/Minimum-1.0,OSGi/Minimum-1.1,OSGi/Minimum-1.2\n");
			out.write("org.osgi.framework.system.packages=javax.accessibility,javax.activation,javax.activity,javax.annotation,javax.annotation.processing,javax.crypto,javax.crypto.interfaces,javax.crypto.spec,javax.imageio,javax.imageio.event,javax.imageio.metadata,javax.imageio.plugins.bmp,javax.imageio.plugins.jpeg,javax.imageio.spi,javax.imageio.stream,javax.jws,javax.jws.soap,javax.lang.model,javax.lang.model.element,javax.lang.model.type,javax.lang.model.util,javax.management,javax.management.loading,javax.management.modelmbean,javax.management.monitor,javax.management.openmbean,javax.management.relation,javax.management.remote,javax.management.remote.rmi,javax.management.timer,javax.naming,javax.naming.directory,javax.naming.event,javax.naming.ldap,javax.naming.spi,javax.net,javax.net.ssl,javax.print,javax.print.attribute,javax.print.attribute.standard,javax.print.event,javax.rmi,javax.rmi.CORBA,javax.rmi.ssl,javax.script,javax.security.auth,javax.security.auth.callback,javax.security.auth.kerberos,javax.security.auth.login,javax.security.auth.spi,javax.security.auth.x500,javax.security.cert,javax.security.sasl,javax.sound.midi,javax.sound.midi.spi,javax.sound.sampled,javax.sound.sampled.spi,javax.sql,javax.sql.rowset,javax.sql.rowset.serial,javax.sql.rowset.spi,javax.swing,javax.swing.border,javax.swing.colorchooser,javax.swing.event,javax.swing.filechooser,javax.swing.plaf,javax.swing.plaf.basic,javax.swing.plaf.metal,javax.swing.plaf.multi,javax.swing.plaf.synth,javax.swing.table,javax.swing.text,javax.swing.text.html,javax.swing.text.html.parser,javax.swing.text.rtf,javax.swing.tree,javax.swing.undo,javax.tools,javax.transaction,javax.transaction.xa,javax.xml,javax.xml.bind,javax.xml.bind.annotation,javax.xml.bind.annotation.adapters,javax.xml.bind.attachment,javax.xml.bind.helpers,javax.xml.bind.util,javax.xml.crypto,javax.xml.crypto.dom,javax.xml.crypto.dsig,javax.xml.crypto.dsig.dom,javax.xml.crypto.dsig.keyinfo,javax.xml.crypto.dsig.spec,javax.xml.datatype,javax.xml.namespace,javax.xml.parsers,javax.xml.soap,javax.xml.stream,javax.xml.stream.events,javax.xml.stream.util,javax.xml.transform,javax.xml.transform.dom,javax.xml.transform.sax,javax.xml.transform.stax,javax.xml.transform.stream,javax.xml.validation,javax.xml.ws,javax.xml.ws.handler,javax.xml.ws.handler.soap,javax.xml.ws.http,javax.xml.ws.soap,javax.xml.ws.spi,javax.xml.xpath,org.ietf.jgss,org.omg.CORBA,org.omg.CORBA.DynAnyPackage,org.omg.CORBA.ORBPackage,org.omg.CORBA.TypeCodePackage,org.omg.CORBA.portable,org.omg.CORBA_2_3,org.omg.CORBA_2_3.portable,org.omg.CosNaming,org.omg.CosNaming.NamingContextExtPackage,org.omg.CosNaming.NamingContextPackage,org.omg.Dynamic,org.omg.DynamicAny,org.omg.DynamicAny.DynAnyFactoryPackage,org.omg.DynamicAny.DynAnyPackage,org.omg.IOP,org.omg.IOP.CodecFactoryPackage,org.omg.IOP.CodecPackage,org.omg.Messaging,org.omg.PortableInterceptor,org.omg.PortableInterceptor.ORBInitInfoPackage,org.omg.PortableServer,org.omg.PortableServer.CurrentPackage,org.omg.PortableServer.POAManagerPackage,org.omg.PortableServer.POAPackage,org.omg.PortableServer.ServantLocatorPackage,org.omg.PortableServer.portable,org.omg.SendingContext,org.omg.stub.java.rmi,org.w3c.dom,org.w3c.dom.bootstrap,org.w3c.dom.css,org.w3c.dom.events,org.w3c.dom.html,org.w3c.dom.ls,org.w3c.dom.ranges,org.w3c.dom.stylesheets,org.w3c.dom.traversal,org.w3c.dom.views ,org.xml.sax,org.xml.sax.ext,org.xml.sax.helpers,org.osgi.framework;version=1.4.0,org.osgi.service.packageadmin;version=1.2.0,org.osgi.service.startlevel;version=1.1.0,org.osgi.service.url;version=1.0.0,org.osgi.util.tracker;version=1.3.3\n");
			out.write("##############################\n");
			out.write("# Client bundles to install\n");
			out.write("##############################\n");
			out.write("felix.auto.start.1=\\\n");
			out.write("\"mvn:org.apache.felix/org.apache.felix.shell\" \\\n");
			out.write("\"mvn:org.apache.felix/org.apache.felix.shell.tui\"\n");
			out.write("felix.auto.start.2=\\\n");
			out.write("\"mvn:org.apache.felix/org.apache.felix.configadmin\" \\\n");
			out.write("\"mvn:org.universaal.middleware/mw.acl.interfaces/0.3.0-SNAPSHOT\" \\\n");
			out.write("\"wrap:mvn:jp.go.ipa/jgcl/1.0\" \\\n");
			out.write("\"wrap:mvn:java3d/vecmath/1.3.1\" \\\n");
			out.write("\"wrap:mvn:org.bouncycastle/jce.jdk13/144\" \\\n");
			out.write("\"wrap:mvn:java3d/j3d-core/1.3.1\" \\\n");
			out.write("\"wrap:mvn:org.osgi/osgi_R4_compendium/1.0\" \\\n");
			out.write("\"wrap:mvn:org.ops4j.pax.logging/pax-logging-api/1.4\"\n");
			out.write("felix.auto.start.3=\\\n");
			out.write("\"wrap:mvn:org.ops4j.pax.logging/pax-logging-service/1.4\" \\\n");
			out.write("\"mvn:org.universaal.middleware/mw.bus.model/0.3.0-SNAPSHOT\"\n");
			out.write("felix.auto.start.4=\\\n");
			out.write("\"mvn:org.universaal.middleware/mw.bus.context/0.3.0-SNAPSHOT\" \\\n");
			out.write("\"mvn:org.universaal.middleware/mw.bus.service/0.3.0-SNAPSHOT\" \\\n");
			out.write("\"mvn:org.universaal.middleware/mw.data.serialization/0.3.0-SNAPSHOT\" \\\n");
			out.write("\"mvn:org.universaal.middleware/mw.data.representation/0.3.0-SNAPSHOT\"\n");

			// put ontology artifacts according to project's pom
			if (ontologyDependencies.size() != 0) {
				out.write("felix.auto.start.5=\\\n");
				if (ontologyDependencies.size() == 1) {
					out.write("\"mvn:"
							+ ontologyDependencies.get(0).getGroupId() + "/"
							+ ontologyDependencies.get(0).getArtifactId() + "/"
							+ ontologyDependencies.get(0).getVersion()
							+ "\" \n");
				} else {
					for (int i = 0; i < ontologyDependencies.size() - 1; i++) {
						out.write("\"mvn:"
								+ ontologyDependencies.get(i).getGroupId()
								+ "/"
								+ ontologyDependencies.get(i).getArtifactId()
								+ "/"
								+ ontologyDependencies.get(i).getVersion()
								+ "\" \\\n");
					}
					out.write("\"mvn:"
							+ ontologyDependencies.get(
									ontologyDependencies.size() - 1)
									.getGroupId()
							+ "/"
							+ ontologyDependencies.get(
									ontologyDependencies.size() - 1)
									.getArtifactId()
							+ "/"
							+ ontologyDependencies.get(
									ontologyDependencies.size() - 1)
									.getVersion() + "\" \n");
				}
			}

			out.write("felix.auto.install.6=\\\n");
			out.write("\"mvn:" + groupId + "/" + artifactId + "/"
					+ artifactVersion + "\" \n");
			out.write("felix.auto.start.8=\\\n");
			out.write("\"mvn:org.apache.felix/org.apache.felix.bundlerepository/1.4.2\"\n");
			out.write("##############################\n");
			out.write("# System properties\n");
			out.write("##############################\n");
			out.write("org.universAAL.middleware.peer.member_of=urn:org.universAAL.aal_space:test_env\n");
			out.write("felix.log.level=4\n");
			out.write("user.language=en\n");
			out.write("org.universAAL.middleware.peer.is_coordinator=true\n");
			out.write("felix.upnpbase.exporter.enabled=true\n");
			out.write("felix.upnpbase.importer.enabled=true\n");
			out.write("felix.upnpbase.log=4\n");
			out.write("felix.upnpbase.cyberdomo.log=true\n");
			out.write("felix.upnpbase.cyberdomo.net.loopback=false\n");
			out.write("felix.upnpbase.cyberdomo.net.onlyIPV4=true\n");
			out.write("felix.upnpbase.cyberdomo.net.onlyIPV6=false\n");
			out.write("cyberdomo.ssdp.mx=5\n");
			out.write("cyberdomo.ssdp.buffersize=2048\n");
			out.write("cyberdomo.ssdp.port=13531\n");
			out.write("felix.fileinstall.poll=15000\n");
			out.write("felix.fileinstall.dir=${workspace_loc}/rundir/test/cache\n");

			// Close the output stream
			out.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
