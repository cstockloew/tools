/*
        Copyright 2007-2014 CNR-ISTI, http://isti.cnr.it
        Institute of Information Science and Technologies
        of the Italian National Research Council

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
package org.universaal.tools.packaging.tool.util;


/**
 * This class contains all configuration parameters and theirs default values,
 * which change the behavior of the plugin 
 *  
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano Lenzi</a>
 * @author <a href="mailto:federico.volpini@isti.cnr.it">Federico Volpini</a>
 * @version $LastChangedRevision$ ( $LastChangedDate$ )
 */
public interface ConfigProperties {

    public static final String TMP_DIR_KEY = "org.uAAL.packager.log.dir";
    public static final String TMP_DIR_DEFAULT = null;  
    
    public static final String LOG_DIR_KEY = "org.uAAL.packager.log.dir";    
    public static final String LOG_DIR_DEFAULT = TMP_DIR_DEFAULT;
    
    public static final String RECOVERY_MODE_KEY = "org.uAAL.packager.recovery";    
    public static final String RECOVERY_MODE_KEY_DEFAULT = "true";
    public static final String RECOVERY_FILE_NAME_KEY = "org.uAAL.packager.recovery.filename";    
    public static final String RECOVERY_FILE_NAME_DEFAULT = "/.recovery";    
    public static final String RECOVERY_PARTS_NAME_KEY = "org.uAAL.packager.recovery.partsname"; 
    public static final String RECOVERY_PARTS_NAME_DEFAULT = "/.parts"; 
    
    public static final String ENABLE_CONSOLE_LOG_KEY = "org.uAAL.packager.log.console";
    public static final String ENABLE_CONSOLE_LOG_DEFAULT = "true";
    
    public static final String KARAF_PLUGIN_GROUP_KEY = "karaf.tool.groupId";
    public static final String KARAF_PLUGIN_GROUP_DEFAULT = "org.apache.karaf.tooling";
    
    public static final String KARAF_PLUGIN_NAME_KEY = "karaf.tool.artifactId";
    public static final String KARAF_PLUGIN_NAME_DEFAULT = "features-maven-plugin";
    
    public static final String KARAF_PLUGIN_VERSION_KEY = "karaf.tool.version";
    public static final String KARAF_PLUGIN_VERSION_DEFAULT = "2.3.1";
    
    public static final String KARAF_PLUGIN_GOAL_FEATURE_KEY = "karaf.tool.goal.feature";
    public static final String KARAF_PLUGIN_GOAL_FEATURE_DEFAULT = "generate-features-xml";
    
    public static final String KARAF_PLUGIN_GOAL_KAR_KEY = "karaf.tool.goal.karfile";
    public static final String KARAF_PLUGIN_GOAL_KAR_DEFAULT = "create-kar";
    
    public static final String OFFLINE_MODE_KEY = "org.uAAL.packager.offlineMode";
    public static final String OFFLINE_MODE_DEFAULT = "true";

    public static final String MAVEN_COMMAND_KEY = "org.uAAL.packager.mavenCommand";
    public static final String MAVEN_COMMAND_DEFAULT = "mvn";
    
    public static final String MAVEN_EMBEDDED_KEY = "org.uAAL.packager.mavenEmbedded";
    public static final String MAVEN_EMBEDDED_DEFAULT = "false";
    
}
