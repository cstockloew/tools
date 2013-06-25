package org.universaal.tools.externalserviceintegrator.ontology;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.PlatformUI;
import org.universAAL.ri.wsdlToolkit.ioApi.ComplexObject;
import org.universAAL.ri.wsdlToolkit.ioApi.NativeObject;
import org.universAAL.ri.wsdlToolkit.ioApi.WSOperation;
import org.universaal.tools.externalserviceintegrator.ontology.tools.StringComparison;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class OntologyMatcher {

	static String pathOfOntologyFiles = ResourcesPlugin.getWorkspace()
			.getRoot().getLocation().toFile()
			+ File.separator
			+ ".metadata"
			+ File.separator
			+ ".plugins"
			+ File.separator
			+ "org.universaal.tools.externalServiceIntegrator"
			+ File.separator;

	private List<OntologyMapping> mappings = new ArrayList<OntologyMapping>();
	private WSOperation selectedOperation;

	public void computeMappings(WSOperation selectedOperation) {
		this.selectedOperation=selectedOperation;
		List<NativeObject> NoList = getListOfNativeObjects(selectedOperation
				.getHasInput().getHasNativeOrComplexObjects());
		Map<NativeObject,ArrayList<OntologyMapping>> multiMap = new HashMap<NativeObject,ArrayList<OntologyMapping>>();
		for (int j = 0; j < NoList.size(); j++) {
			multiMap.put(NoList.get(j), new ArrayList<OntologyMapping>());
		}
		File dir = new File(pathOfOntologyFiles);
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.getName().endsWith("owl")) {

				OntModel ontologyModel = ModelFactory.createOntologyModel();
				try {
					InputStream in = FileManager.get().open(
							file.getAbsolutePath());
					ontologyModel.read(in, "");

					for (int j = 0; j < NoList.size(); j++) {

						ExtendedIterator properties = ontologyModel
								.listObjectProperties();
						while (properties.hasNext()) {
							ObjectProperty prop = (ObjectProperty) properties
									.next();
							if (prop.getURI().contains("#")) {
								String propName = prop.getURI().split("#")[1];
								double score=StringComparison.CompareStrings(propName, NoList.get(j).getObjectName().getLocalPart());
								OntologyMapping map=new OntologyMapping();
								map.setNativeObject(NoList.get(j));
								map.setOntologyFileName(file.getName());
								map.setOntologyURI(prop.getURI());
								map.setScore(score);								
								multiMap.get(NoList.get(j)).add(map);
								
							}
						}
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		System.out.println();
		//show ontology matching dialog
		OntologyMatchingDialog ontologyMatchingDialog =new OntologyMatchingDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), 1, multiMap, selectedOperation);
		ontologyMatchingDialog.open();
	}

	private List<NativeObject> getListOfNativeObjects(Vector vec) {
		List<NativeObject> list = new ArrayList<NativeObject>();
		for (int i = 0; i < vec.size(); i++) {
			if (vec.get(i) instanceof NativeObject) {
				list.add((NativeObject) vec.get(i));
			} else if (vec.get(i) instanceof ComplexObject) {
				ComplexObject co = (ComplexObject) vec.get(i);
				List<NativeObject> l1 = getListOfNativeObjects(co
						.getHasComplexObjects());
				List<NativeObject> l2 = getListOfNativeObjects(co
						.getHasNativeObjects());
				list.addAll(l1);
				list.addAll(l2);
			}
		}
		return list;
	}

}
