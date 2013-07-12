/*****************************************************************************************
	Copyright 2012-2014 CERTH-HIT, http://www.hit.certh.gr/
	Hellenic Institute of Transport (HIT)
	Centre For Research and Technology Hellas (CERTH)
	
	
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
 *****************************************************************************************/

package org.universaal.tools.codeassistantapplication.editor.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.universaal.tools.codeassistantapplication.Activator;
import org.universaal.tools.codeassistantapplication.editor.model.TreeNode;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class TreeConstruction{
	private static OntModel ontologyModel;  
	private static InputStream in;
	private static String codeAssistantDir = new String("CodeAssistantFiles");
	private static String iconsDir = new String("icons");
	private static String filesDir = new String("owlfiles");
	private static URL owlFilesDir = null;
	private static Image repoImage=null;
	private static Image classImage=null;
	private static Image propImage=null;
	private static TreeNode tn;
	
	public TreeConstruction(){
		repoImage =ImageDescriptor.createFromURL((URL)Platform.getBundle("org.universaal.tools.codeAssistant").getEntry(codeAssistantDir+"/"+iconsDir+"/"+"repo.gif")).createImage();
		classImage =ImageDescriptor.createFromURL((URL)Platform.getBundle("org.universaal.tools.codeAssistant").getEntry(codeAssistantDir+"/"+iconsDir+"/"+"class.gif")).createImage();
		propImage =ImageDescriptor.createFromURL((URL)Platform.getBundle("org.universaal.tools.codeAssistant").getEntry(codeAssistantDir+"/"+iconsDir+"/"+"properties.gif")).createImage();

		//repoImage =ImageDescriptor.createFromURL((URL)Platform.getBundle("org.universaal.tools.codeAssistant").getEntry(codeAssistantDir+File.separator+iconsDir+File.separator+"repo.gif")).createImage();
		//classImage =ImageDescriptor.createFromURL((URL)Platform.getBundle("org.universaal.tools.codeAssistant").getEntry(codeAssistantDir+File.separator+iconsDir+File.separator+"class.gif")).createImage();
		//propImage =ImageDescriptor.createFromURL((URL)Platform.getBundle("org.universaal.tools.codeAssistant").getEntry(codeAssistantDir+File.separator+iconsDir+File.separator+"properties.gif")).createImage();
		//owlFilesDir = (URL)Platform.getBundle("org.universaal.tools.codeAssistant").getEntry(codeAssistantDir+File.separator+filesDir+File.separator);
	}

	public static TreeNode getRootNode() {
		TreeNode root = new TreeNode("root",new Entity("root"));
		try{
			//File[] files = getDirectoryFiles(owlFilesDir);
			File[] files = getDirectoryFiles();
			if (files!=null){
				String[] fileNames = getFileNames(files);
				for (int i=0; i<files.length; i++) {
					tn = new TreeNode("",new Entity(""));
					Entity q = new Entity(fileNames[i]);
					tn.setName(fileNames[i]);
					tn.setQ(q);
					tn.setImage(repoImage);
					root.addChild(tn);
					//System.out.println("File: "+fileNames[i]);
					loadOntology(files[i].toString());
					ArrayList owlClasses = (ArrayList)getOntologyClassesStructured();
					for (int j=0; j<owlClasses.size(); j++){
						OntologyClass c = (OntologyClass)owlClasses.get(j);
						constructTreeNode(tn, c);
						getChildrenStructured(c);
					}
					owlClasses.clear();
				}
			}
		}
		catch(Exception e){ 
			//e.printStackTrace(); 
		}
		
		return root;
	}

	public static List<OntologyClass> getOntologyClassesStructured() {
        List<OntologyClass> list = new ArrayList<OntologyClass>();
        ExtendedIterator classes = ontologyModel.listHierarchyRootClasses();
        while (classes.hasNext()) {
            OntClass essaClasse = (OntClass) classes.next();
            String vClasse = new String();
            if (essaClasse.getLocalName() != null){ 
            	vClasse = essaClasse.getLocalName().toString();
	            List<OntologyClass> l = new ArrayList<OntologyClass>();
	            if (essaClasse.hasSubClass()) {
	                for (Iterator i = essaClasse.listSubClasses(true); i.hasNext();) {
	                    OntClass c = (OntClass) i.next();
	                    l.add(getConceptChildrenStructured(c));
	                }
	            }
	            OntologyClass on = new OntologyClass();
	            on.setClassName(vClasse);
	            on.setChildren(l);
	            on.setProperties(getPropertyByClass(essaClasse));
	            on.setURI(essaClasse.getURI());
	            
	            list.add(on);
            }
        }
        
		ontologyModel.close();
		try {
			in.close();
		}
		catch(IOException ioe){ 
			//ioe.printStackTrace(); 
		}
        return list;
    }

    public static OntologyClass getConceptChildrenStructured(OntClass c) {
            List<OntologyClass> l = new ArrayList<OntologyClass>();
            if (c.hasSubClass()) {
                for (Iterator i = c.listSubClasses(true); i.hasNext();) {
                    OntClass cc = (OntClass) i.next();
                    l.add(getConceptChildrenStructured(cc));
                }
            }
            OntologyClass on = new OntologyClass();
            on.setClassName(c.getLocalName());
            on.setChildren(l);
            on.setProperties(getPropertyByClass(c));
            on.setURI(c.getURI());
            return on;
    }

    public static int getTreeNodeChildren(TreeNode n, OntologyClass c, OntologyClass cc, int found) {

		if (n.getName().equals(c.getClassName())){
			constructTreeNode(n, cc);
			return 1;
		}
    	
    	for (int i = 0; i<n.getChildren().size(); i++) {
    		TreeNode nn = (TreeNode) n.getChildren().get(i);
			if (nn.getName().equals(c.getClassName())){
				constructTreeNode(nn, cc);
				return 1;
			}
			found = getTreeNodeChildren(nn, c, cc, found);
    	}

    	return found;
    }
    
    public static void getChildrenStructured(OntologyClass c) {
    	List<OntologyClass> l = new ArrayList<OntologyClass>();
        if (c.getChildren().size()>0) {
            for (int i = 0; i<c.getChildren().size(); i++) {
                OntologyClass cc = (OntologyClass) c.getChildren().get(i);
            	
            	List<TreeNode> listOfNodes = tn.getChildren();
            	if (listOfNodes.size()>0){
            		int found = 0;
            		for (int j=0; j<listOfNodes.size(); j++){
            			TreeNode aNode = listOfNodes.get(j);
            			//if (aNode.getChildren().size()>0){
                    	if (c.getChildren().size()>0){
            				found = getTreeNodeChildren(aNode, c, cc, 0);
            				if (found==1)
            					break;
            			}
            		}            		
            		if (found==0){
            			// Currently there is no possibility found variable to be equal to zero
                    	System.out.println(">"+c.getClassName());
                		//TreeNode child1Node = constructTreeNode(tn, c);
                		//constructTreeNode(child1Node, cc);
            		}
            	}
            	else{
            		//System.out.println(c.getClassName() + "-"+cc.getClassName());
            		TreeNode child1Node = constructTreeNode(tn, c);
            		constructTreeNode(child1Node, cc);
            	}
                getChildrenStructured(cc);
            }
        }      
    }

	private static TreeNode constructTreeNode(TreeNode parent, OntologyClass c){
		TreeNode childNode = new TreeNode("",new Entity(""));
		Entity qNode = new Entity(c.getClassName());
		qNode.setUri(c.getURI());
		qNode.setProperty("");
		childNode.setName(c.getClassName());
		childNode.setQ(qNode);
		childNode.setImage(classImage);
		// Fill in the properties of the ontology
		Enumeration en = (Enumeration)c.getProperties().keys();
		while (en.hasMoreElements()){
			String propertyName = (String)en.nextElement();
			Vector details = (Vector)c.getProperties().get(propertyName);
			TreeNode propertyNode = new TreeNode("",new Entity(""));
			Entity q3Node = new Entity(propertyName);
			q3Node.setUri((String)details.get(0));
			q3Node.setProperty((String)details.get(1));
			q3Node.setRange((String)details.get(2));
			q3Node.setRDFType((String)details.get(3));
			propertyNode.setName(propertyName);
			propertyNode.setQ(q3Node);
			propertyNode.setImage(propImage);
			childNode.addChild(propertyNode);
		}	
		parent.addChild(childNode);
		return childNode;
	}
    
	private static void loadOntology(String pathToOWLFile) {
		try{
			ontologyModel = ModelFactory.createOntologyModel();
			in = FileManager.get().open(pathToOWLFile);
			if (in == null) {
				throw new IllegalArgumentException("File: " + pathToOWLFile + " not found");
			}
			ontologyModel.read(in, "");
		}
		catch(Exception e){}
	}

	private static Hashtable getOntologyClasses() {
		Hashtable returnClasses = new Hashtable();
		ExtendedIterator classes = ontologyModel.listClasses();
		while (classes.hasNext()) {
			OntClass essaClasse = (OntClass) classes.next();
			if (essaClasse.getLocalName() != null) {
				returnClasses.put(essaClasse.getNameSpace()+essaClasse.getLocalName(), new Vector());
				//System.out.println("Class: "+essaClasse.getLocalName());
			}
			Vector subClasses = new Vector();
			for (Iterator i = essaClasse.listSubClasses(); i.hasNext();) {
				OntClass c = (OntClass) i.next();
				if (c.getLocalName() != null) {
					subClasses.add(c.getNameSpace()+c.getLocalName());
					//System.out.println("Sub Class: "+c.getLocalName());
				}
			}
			if (essaClasse.getLocalName() != null){
				// Get the properties of the class
				Hashtable properties = getPropertyByClass(essaClasse);
				Hashtable classValues = new Hashtable();
				classValues.put("subclasses", subClasses);
				classValues.put("properties", properties);				
				
				returnClasses.put(essaClasse.getNameSpace()+essaClasse.getLocalName(), classValues);
			}
		}
		return returnClasses;
	}
	
	private static Hashtable getPropertyByClass(OntClass aClass) {
		Hashtable classProperties = new Hashtable();
		if (aClass.getLocalName() != null) {
			//ExtendedIterator listOfproperties = aClass.listDeclaredProperties(true);
			//OntClass c = ontologyModel.getOntClass(aClass.getLocalName());
			ExtendedIterator listOfproperties = aClass.listDeclaredProperties(true);
			while (listOfproperties.hasNext()) {
				Vector propDetails = new Vector();
				OntProperty prop = (OntProperty)listOfproperties.next();
				propDetails.add(prop.getNameSpace()+prop.getLocalName());
				if (prop.isDatatypeProperty())
					propDetails.add("Datatype");
				else if (prop.isFunctionalProperty())
					propDetails.add("Functional");
				else if (prop.isObjectProperty())
					propDetails.add("Object");
				if (prop.getRange()!=null)
					propDetails.add(prop.getRange().toString());
				else
					propDetails.add("");
				if (prop.getRDFType()!=null)
					propDetails.add(prop.getRDFType().toString());
				else
					propDetails.add("");

				classProperties.put(prop.getLocalName(), propDetails);
			}
		}
		return classProperties;
	}
	
	
	//private static File[] getDirectoryFiles(URL directoryName) throws IOException{
	private static File[] getDirectoryFiles() throws IOException{
		File[] files = null;
		ArrayList owlfiles = new ArrayList();
		IPath ipath = Activator.getDefault().getStateLocation();

		try {
			//File directory = new File(FileLocator.resolve(directoryName).toURI());
			File parentDir = ipath.toFile();
			File directory = new File(parentDir.toString(),filesDir);

			if (directory.isDirectory()){
				files=directory.listFiles();
				for (int i=0; i<files.length; i++){
					File path=files[i];
					if (((path.toString()).endsWith(".owl")))
						owlfiles.add(path);
/*					
					FileReader fr = new FileReader(path);
					BufferedReader br = new BufferedReader(fr);
					String s = "";
					while (br.ready()) {
						s += br.readLine() + "\n";
					}
*/					
				}	
			}
		} 
		catch (Exception e) { 
			//e.printStackTrace(); 
		}    

		File[] res = new File[owlfiles.size()];
		for (int i=0; i<res.length; i++){
			res[i] = (File)owlfiles.get(i);
			//System.out.println("---Files to be parsed---");
			//System.out.println(res[i]);
		}
		return res;
	}

	private static String[] getFileNames(File[] files) throws IOException{
		String[] fileNames = new String[files.length];
		for (int i=0; i<files.length; i++){
			File path=files[i];
			fileNames[i] = (path.toString()).substring((path.toString()).lastIndexOf(File.separator)+1, (path.toString()).lastIndexOf("."));
			//fileNames[i] = (path.toString()).substring((path.toString()).lastIndexOf("/")+1, (path.toString()).lastIndexOf("."));
		}
		return fileNames;
	}
}