package org.universaal.tools.newwizard.plugin.wizards;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileStreamUtils {

    /**
     * Puts the template content into the newly generated item
     * 
     * @param fileTemplateName
     *            Name of the template file to use
     * @param packname
     *            Name of the package where it is generated
     * @param filename
     *            Name of the item being generated
     * @return The stream of the generated content
     * @throws IOException
     */
    protected static InputStream customizeFileStream(String fileTemplateName,
	    String packname, String filename) throws IOException {
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		FileStreamUtils.class.getClassLoader().getResourceAsStream(
			"files/" + fileTemplateName))); //$NON-NLS-1$
	StringBuilder output = new StringBuilder();
	String line;
	while ((line = reader.readLine()) != null) {
	    if (line.contains("/*TAG:PACKAGE*/")) { //$NON-NLS-1$
		line = "package " + packname + ";\n"; //$NON-NLS-1$ //$NON-NLS-2$
	    }
	    if (line.contains("/*TAG:CLASSNAME*/")) { //$NON-NLS-1$
		line = line.replace("/*TAG:CLASSNAME*/", filename); //$NON-NLS-1$
	    }
	    output.append(line + "\n"); //$NON-NLS-1$
	}
	return new ByteArrayInputStream(output.toString().getBytes());
    }

    /**
     * This method parses a newly created Activator file to make it init, start
     * and stop as appropriate the rest of uaal-specific files. It also adapts
     * package name to all files.
     * 
     * @param filename
     *            The name of the file (without extension)
     * @param packname
     *            The name of package
     * @param checks
     *            Collection of checked options to browse all checked classes
     * @return
     * @throws IOException
     */
    protected static InputStream customizeFileStream(String filename,
	    String packname, boolean[] checks) throws IOException {

	// TODO: Modify if necessary the rest of files, not only Activator.
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		FileStreamUtils.class.getClassLoader().getResourceAsStream(
			"files/" + filename + ".java"))); //$NON-NLS-1$ //$NON-NLS-2$
	StringBuilder output = new StringBuilder();
	String line;
	while ((line = reader.readLine()) != null) {
	    if (line.contains("/*TAG:PACKAGE*/")) { //$NON-NLS-1$
		output.append("package " + packname + ";\n"); //$NON-NLS-1$ //$NON-NLS-2$
	    } else if (line.contains("/*TAG:IMPORT*/")) {
		if (checks[8]) {
		    output.append("import org.universAAL.middleware.context.ContextPublisher;\n"); //$NON-NLS-1$
		    output.append("import org.universAAL.middleware.context.DefaultContextPublisher;\n"); //$NON-NLS-1$
		}
		if (checks[9]) {
		    output.append("import org.universAAL.middleware.service.ServiceCaller;\n"); //$NON-NLS-1$
		    output.append("import org.universAAL.middleware.service.DefaultServiceCaller;\n"); //$NON-NLS-1$
		}
	    } else if (line.contains("/*TAG:INIT*/")) { //$NON-NLS-1$
		if (checks[4])
		    output.append("	public static SCallee scallee=null;\n"); //$NON-NLS-1$
		if (checks[5])
		    if (checks[9])
			output.append("	public static ServiceCaller scaller=null;\n"); //$NON-NLS-1$
		    else
			output.append("	public static SCaller scaller=null;\n"); //$NON-NLS-1$
		if (checks[2])
		    output.append("	public static ISubscriber isubscriber=null;\n"); //$NON-NLS-1$
		if (checks[3])
		    output.append("	public static OPublisher opublisher=null;\n"); //$NON-NLS-1$
		if (checks[1])
		    output.append("	public static CSubscriber csubscriber=null;\n"); //$NON-NLS-1$
		if (checks[0])
		    if (checks[8])
			output.append("	public static ContextPublisher cpublisher=null;\n"); //$NON-NLS-1$
		    else
			output.append("	public static CPublisher cpublisher=null;\n"); //$NON-NLS-1$
		if (checks[6])
		    output.append("	public static IPublisher ipublisher=null;\n"); //$NON-NLS-1$
		if (checks[7])
		    output.append("	public static OSubscriber osubscriber=null;\n"); //$NON-NLS-1$
	    } else if (line.contains("/*TAG:START*/")) { //$NON-NLS-1$
		if (checks[4])
		    output.append("		scallee=new SCallee(context);\n"); //$NON-NLS-1$
		if (checks[5]) {
		    if (checks[9])
			output.append("		scaller=new DefaultServiceCaller(context);\n"); //$NON-NLS-1$
		    else
			output.append("		scaller=new SCaller(context);\n"); //$NON-NLS-1$
		}
		if (checks[2])
		    output.append("		isubscriber=new ISubscriber(context);\n"); //$NON-NLS-1$
		if (checks[3])
		    output.append("		opublisher=new OPublisher(context);\n"); //$NON-NLS-1$
		if (checks[1])
		    output.append("		csubscriber=new CSubscriber(context);\n"); //$NON-NLS-1$
		if (checks[0]) {
		    if (checks[8])
			output.append("		cpublisher=new DefaultContextPublisher(context,null);\n"); //$NON-NLS-1$
		    else
			output.append("		cpublisher=new CPublisher(context);\n"); //$NON-NLS-1$
		}
		if (checks[6])
		    output.append("		ipublisher=new IPublisher(context);\n"); //$NON-NLS-1$
		if (checks[7])
		    output.append("		osubscriber=new OSubscriber(context);\n"); //$NON-NLS-1$
	    } else if (line.contains("/*TAG:STOP*/")) { //$NON-NLS-1$
		if (checks[4])
		    output.append("		scallee.close();\n"); //$NON-NLS-1$
		if (checks[5])
		    output.append("		scaller.close();\n"); //$NON-NLS-1$
		if (checks[2])
		    output.append("		isubscriber.close();\n"); //$NON-NLS-1$
		if (checks[3])
		    output.append("		opublisher.close();\n"); //$NON-NLS-1$
		if (checks[1])
		    output.append("		csubscriber.close();\n"); //$NON-NLS-1$
		if (checks[0])
		    output.append("		cpublisher.close();\n"); //$NON-NLS-1$
		if (checks[6])
		    output.append("		ipublisher.close();\n"); //$NON-NLS-1$
		if (checks[7])
		    output.append("		osubscriber.close();\n"); //$NON-NLS-1$
	    } else if (line.contains("/*TAG:CLASSNAME*/")) { //$NON-NLS-1$
		line = line
			.replace(
				"/*TAG:CLASSNAME*/", filename.substring(filename.lastIndexOf("/") + 1)); //$NON-NLS-1$
		output.append(line + "\n"); //$NON-NLS-1$
	    } else {
		output.append(line + "\n"); //$NON-NLS-1$
	    }
	}
	return new ByteArrayInputStream(output.toString().getBytes());
    }

    /**
     * This method parses the blank pom template and adds dependencies and
     * configurations for the project to be uaal-compliant
     * 
     * @param checks
     * @throws IOException
     */
    protected static InputStream customizePomStream(String packname,
	    InputStream instream, boolean[] checks, boolean[] template,
	    String mwVersion) throws IOException {
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		instream));
	StringBuilder output = new StringBuilder();
	String line;
	while ((line = reader.readLine()) != null) {
	    if (line.contains("</project>")) { //$NON-NLS-1$
		output.append("  <packaging>bundle</packaging>\n"); //$NON-NLS-1$
		output.append("    <dependencies>\n" //$NON-NLS-1$
			+ "		<dependency>\n" //$NON-NLS-1$
			+ "			<groupId>org.apache.felix</groupId>\n" //$NON-NLS-1$
			+ "			<artifactId>org.osgi.core</artifactId>\n" //$NON-NLS-1$
			+ "			<version>1.0.1</version>\n" //$NON-NLS-1$
			+ "		</dependency>\n" //$NON-NLS-1$
			+ "		<dependency>\n" //$NON-NLS-1$
			+ "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
			+ "			<artifactId>mw.data.representation</artifactId>\n" //$NON-NLS-1$
			+ "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
			+ "		</dependency>\n"); //$NON-NLS-1$
		if (mwVersion.equals(NewProjectWizardPage2.VER_031_S)){
		    output.append("		<dependency>\n" //$NON-NLS-1$
			    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
			    + "			<artifactId>mw.bus.model</artifactId>\n" //$NON-NLS-1$
			    + "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
			    + "		</dependency>\n"); //$NON-NLS-1$
		    output.append("		<dependency>\n" //$NON-NLS-1$
			    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
			    + "			<artifactId>mw.container.xfaces</artifactId>\n" //$NON-NLS-1$
			    + "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
			    + "		</dependency>\n"); //$NON-NLS-1$
		    output.append("		<dependency>\n" //$NON-NLS-1$
			    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
			    + "			<artifactId>mw.container.osgi</artifactId>\n" //$NON-NLS-1$
			    + "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
			    + "		</dependency>\n"); //$NON-NLS-1$
		}
		if (checks[0] || checks[1]) {
		    output.append("		<dependency>\n" //$NON-NLS-1$
			    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
			    + "			<artifactId>mw.bus.context</artifactId>\n" //$NON-NLS-1$
			    + "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
			    + "		</dependency>\n"); //$NON-NLS-1$
		}
		if (checks[2] || checks[3] || checks[6] || checks[7]) {
		    output.append("		<dependency>\n" //$NON-NLS-1$
			    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
			    + "			<artifactId>mw.bus.io</artifactId>\n" //$NON-NLS-1$
			    + "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
			    + "		</dependency>\n"); //$NON-NLS-1$
		}
		if (checks[4] || checks[5]) {
		    output.append("		<dependency>\n" //$NON-NLS-1$
			    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
			    + "			<artifactId>mw.bus.service</artifactId>\n" //$NON-NLS-1$
			    + "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
			    + "		</dependency>\n"); //$NON-NLS-1$
		}
		if (template[0]) {
		    output.append("		<dependency>\n" //$NON-NLS-1$
			    + "			<groupId>org.universAAL.ontology</groupId>\n" //$NON-NLS-1$
			    + "			<artifactId>ont.phWorld</artifactId>\n" //$NON-NLS-1$
			    + "			<version>" + getPhWorlVersion(mwVersion) + "</version>\n" //$NON-NLS-1$
			    + "		</dependency>\n"); //$NON-NLS-1$
		}
		if (template[1]) {
		    output.append("		<dependency>\n" //$NON-NLS-1$
			    + "			<groupId>org.universAAL.ontology</groupId>\n" //$NON-NLS-1$
			    + "			<artifactId>ont.profile</artifactId>\n" //$NON-NLS-1$
			    + "			<version>" + getProfileVersion(mwVersion) + "</version>\n" //$NON-NLS-1$
			    + "		</dependency>\n"); //$NON-NLS-1$
		}
		output.append("	</dependencies>\n"); //$NON-NLS-1$
		output.append("    <build>\n" //$NON-NLS-1$
			+ "		<plugins>\n" //$NON-NLS-1$
			+ "			<plugin>\n" //$NON-NLS-1$
			+ "				<groupId>org.apache.felix</groupId>\n" //$NON-NLS-1$
			+ "				<artifactId>maven-bundle-plugin</artifactId>\n" //$NON-NLS-1$
			+ "				<extensions>true</extensions>\n" //$NON-NLS-1$
			+ "				<configuration>\n" //$NON-NLS-1$
			+ "					<instructions>\n" //$NON-NLS-1$
			+ "						<Bundle-Name>${project.name}</Bundle-Name>\n" //$NON-NLS-1$
			+ "						<Bundle-Activator>" //$NON-NLS-1$
			+ packname
			+ ".Activator</Bundle-Activator>\n" //$NON-NLS-1$
			+ "						<Bundle-Description>${project.description}</Bundle-Description>\n" //$NON-NLS-1$
			+ "						<Bundle-SymbolicName>${project.artifactId}</Bundle-SymbolicName>\n" //$NON-NLS-1$
			+ "					</instructions>\n" //$NON-NLS-1$
			+ "				</configuration>\n" //$NON-NLS-1$
			+ "			</plugin>\n" + "		</plugins>\n" //$NON-NLS-1$ //$NON-NLS-2$
			+ "	</build>\n"); //$NON-NLS-1$
		output.append("	<repositories>\n" //$NON-NLS-1$
			+ "		<repository>\n" //$NON-NLS-1$
			+ "			<id>central</id>\n" //$NON-NLS-1$
			+ "			<name>Central Maven Repository</name>\n" //$NON-NLS-1$
			+ "			<url>http://repo1.maven.org/maven2</url>\n" //$NON-NLS-1$
			+ "			<snapshots>\n" //$NON-NLS-1$
			+ "				<enabled>false</enabled>\n" //$NON-NLS-1$
			+ "			</snapshots>\n" //$NON-NLS-1$
			+ "		</repository>\n" //$NON-NLS-1$
			+ "		<repository>\n" //$NON-NLS-1$
			+ "			<id>apache-snapshots</id>\n" //$NON-NLS-1$
			+ "			<name>Apache Snapshots</name>\n" //$NON-NLS-1$
			+ "			<url>http://people.apache.org/repo/m2-snapshot-repository</url>\n" //$NON-NLS-1$
			+ "			<releases>\n" //$NON-NLS-1$
			+ "				<enabled>false</enabled>\n" //$NON-NLS-1$
			+ "			</releases>\n" //$NON-NLS-1$
			+ "			<snapshots>\n" //$NON-NLS-1$
			+ "				<updatePolicy>daily</updatePolicy>\n" //$NON-NLS-1$
			+ "			</snapshots>\n" //$NON-NLS-1$
			+ "		</repository>\n" //$NON-NLS-1$
			+ "		<repository>\n" //$NON-NLS-1$
			+ "			<id>uaal</id>\n" //$NON-NLS-1$
			+ "			<name>universAAL Repositories</name>\n" //$NON-NLS-1$
			+ "			<url>http://depot.universaal.org/maven-repo/releases/</url>\n" //$NON-NLS-1$
			+ "			<snapshots>\n" //$NON-NLS-1$
			+ "				<enabled>false</enabled>\n" //$NON-NLS-1$
			+ "			</snapshots>\n" //$NON-NLS-1$
			+ "		</repository>\n" //$NON-NLS-1$
			+ "		<repository>\n" //$NON-NLS-1$
			+ "			<id>uaal-snapshots</id>\n" //$NON-NLS-1$
			+ "			<name>universAAL Snapshot Repositories</name>\n" //$NON-NLS-1$
			+ "			<url>http://depot.universaal.org/maven-repo/snapshots/</url>\n" //$NON-NLS-1$
			+ "			<releases>\n" //$NON-NLS-1$
			+ "				<enabled>false</enabled>\n" //$NON-NLS-1$
			+ "			</releases>\n" + "		</repository>\n" //$NON-NLS-1$ //$NON-NLS-2$
			+ "	</repositories>\n"); //$NON-NLS-1$
		output.append("</project>\n"); //$NON-NLS-1$
	    } else {
		output.append(line + "\n"); //$NON-NLS-1$
	    }
	}
	return new ByteArrayInputStream(output.toString().getBytes());
    }

    /**
     * This method parses the existing pom of the project and adds dependencies
     * for the new items
     * 
     * @param instream
     *            Input stream to the POM file
     * @param clsnumber
     *            Type of new item being generated
     * @return The stream of the POM modified
     * @throws IOException
     */
    protected static InputStream modifyPomStream(InputStream instream,
	    int clsnumber, String mwVersion) throws IOException {
	// TODO use XML parsing instead of manually parsing the file.
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		instream));
	StringBuilder output = new StringBuilder();
	String line;
	boolean context = false;
	boolean service = false;
	boolean io = false;
	while ((line = reader.readLine()) != null) {
	    if (line.contains("mw.bus.context")) { //$NON-NLS-1$
		context = true;
	    }
	    if (line.contains("mw.bus.service")) { //$NON-NLS-1$
		service = true;
	    }
	    if (line.contains("mw.bus.io")) { //$NON-NLS-1$
		io = true;
	    }
	    if (line.contains("</dependencies>")) { //$NON-NLS-1$
		StringBuilder outputnew = new StringBuilder();
		if (!context && (clsnumber == 0 || clsnumber == 1)) {
		    outputnew
			    .append("		<dependency>\n" //$NON-NLS-1$
				    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
				    + "			<artifactId>mw.bus.context</artifactId>\n" //$NON-NLS-1$
				    + "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
				    + "		</dependency>\n"); //$NON-NLS-1$
		}
		if (!service
			&& (clsnumber == 2 || clsnumber == 3 || clsnumber == 6)) {
		    outputnew
			    .append("		<dependency>\n" //$NON-NLS-1$
				    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
				    + "			<artifactId>mw.bus.service</artifactId>\n" //$NON-NLS-1$
				    + "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
				    + "		</dependency>\n"); //$NON-NLS-1$
		}
		if (!io
			&& (clsnumber == 4 || clsnumber == 5 || clsnumber == 7 || clsnumber == 8)) {
		    outputnew
			    .append("		<dependency>\n" //$NON-NLS-1$
				    + "			<groupId>org.universAAL.middleware</groupId>\n" //$NON-NLS-1$
				    + "			<artifactId>mw.bus.io</artifactId>\n" //$NON-NLS-1$
				    + "			<version>" + mwVersion + "</version>\n" //$NON-NLS-1$
				    + "		</dependency>\n"); //$NON-NLS-1$
		}
		outputnew.append("</dependencies>"); //$NON-NLS-1$
		line = line.replace("</dependencies>", outputnew.toString()); //$NON-NLS-1$
	    }
	    output.append(line + "\n"); //$NON-NLS-1$
	}
	return new ByteArrayInputStream(output.toString().getBytes());
    }

    private static String getProfileVersion(String mwVersion) {
	if (mwVersion.equals(NewProjectWizardPage2.VER_031_S)) {
	    return "1.0.0";
	}
	return "0.3.0-SNAPSHOT";
    }

    private static String getPhWorlVersion(String mwVersion) {
	if (mwVersion.equals(NewProjectWizardPage2.VER_031_S)) {
	    return "1.0.0";
	}
	return "0.2.2-SNAPSHOT";
    }

}
