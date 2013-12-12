/*

        Copyright 2007-2014 CNR-ISTI, http://isti.cnr.it
        Institute of Information Science and Technologies
        of the Italian National Research Council

	Copyright 2007-2014 SINTEF, http://www.sintef.no
	
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
package org.universaal.tools.packaging.tool.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.universaal.tools.packaging.tool.Activator;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano Lenzi</a>
 * @author <a href="mailto:erlend.stav@sintef.no">Erlend Stav</a>
 * @version $LastChangedRevision$ ( $LastChangedDate$ )
 */
public class PackagerRootPreferencePage extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

    BooleanFieldEditor logToConsole;
    StringFieldEditor mavenGoalKarafFeature;
    StringFieldEditor mavenGoalKar;
    StringFieldEditor mavenKarafPluginGroup;
    StringFieldEditor mavenKarafPluginVersion;
    BooleanFieldEditor offlineMode;
    StringFieldEditor mavenKarafPluginName;
    StringFieldEditor mavenCommand;
    BooleanFieldEditor mavenEmbedded;

    public PackagerRootPreferencePage() {
	super(GRID);
	setPreferenceStore(Activator.getDefault().getPreferenceStore());
	setDescription("Here you can customize the behavior of uAAL UAAP Packager");
    }

    
    public void init(IWorkbench workbench) {
	// Intentionally left blank
    }

    @Override
    protected void createFieldEditors() {

	logToConsole = new BooleanFieldEditor(
		ConfigProperties.ENABLE_CONSOLE_LOG_KEY,
		"Enable log on the console", getFieldEditorParent());

	offlineMode = new BooleanFieldEditor(ConfigProperties.OFFLINE_MODE_KEY,
		"Offline Mode", getFieldEditorParent());

	mavenGoalKarafFeature = new StringFieldEditor(
		ConfigProperties.KARAF_PLUGIN_GOAL_FEATURE_KEY,
		"The Maven goal name to invoke for generating the XML feature file",
		getFieldEditorParent());

	mavenGoalKar = new StringFieldEditor(
		ConfigProperties.KARAF_PLUGIN_GOAL_KAR_KEY,
		"The Maven goal name to invoke for generating the KAR file",
		getFieldEditorParent());

	mavenKarafPluginGroup = new StringFieldEditor(
		ConfigProperties.KARAF_PLUGIN_GROUP_KEY,
		"The groupId of the Karaf Maven plugin to invoke",
		getFieldEditorParent());

	mavenKarafPluginVersion = new StringFieldEditor(
		ConfigProperties.KARAF_PLUGIN_VERSION_KEY,
		"The version of the Karaf Maven plugin to invoke",
		getFieldEditorParent());

	mavenKarafPluginName = new StringFieldEditor(
		ConfigProperties.KARAF_PLUGIN_GROUP_KEY,
		"The artifacId of Karaf Maven plugin to invoke",
		getFieldEditorParent());

	mavenCommand = new StringFieldEditor(
		ConfigProperties.MAVEN_COMMAND_KEY,
		"The command to execute for starting Maven",
		getFieldEditorParent());

	mavenEmbedded = new BooleanFieldEditor(
		ConfigProperties.MAVEN_EMBEDDED_KEY,
		"Use maven embedded in Eclipse", getFieldEditorParent());

	addField(logToConsole);
	addField(offlineMode);
	addField(mavenGoalKar);
	addField(mavenGoalKarafFeature);
	addField(mavenKarafPluginGroup);
	addField(mavenKarafPluginVersion);
	addField(mavenKarafPluginName);
	addField(mavenEmbedded);
	addField(mavenCommand);
    }

}