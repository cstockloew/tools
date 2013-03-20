package org.universaal.tools.externalserviceintegrator.actions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import org.universAAL.ri.wsdlToolkit.ioApi.ComplexObject;
import org.universAAL.ri.wsdlToolkit.ioApi.NativeObject;
import org.universAAL.ri.wsdlToolkit.ioApi.WSOperation;
import org.universaal.tools.modelling.servicemodel.Parameter;
import org.universaal.tools.modelling.servicemodel.ServiceModelFactory;
import org.universaal.tools.modelling.servicemodel.ServiceInterface;
import org.universaal.tools.modelling.servicemodel.ServiceOperation;

public class CreateServiceModel {

	static void createServiceModel(WSOperation operation) {
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the default resource factory -- only needed for stand-alone!
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());

		// Get the URI of the model file.
		URI fileURI = URI.createFileURI(new File(operation.getOperationName()
				+ ".model").getAbsolutePath());

		// Create a resource for this file.
		Resource resource = resourceSet.createResource(fileURI);

		ServiceInterface userService = ServiceModelFactory.eINSTANCE
				.createServiceInterface();
		userService.setName(operation.getOperationName() + "Service");
		ServiceOperation oper = ServiceModelFactory.eINSTANCE
				.createServiceOperation();
		oper.setName(operation.getOperationName());

		// addInputsToModel(operation.getHasInput().getHasNativeOrComplexObjects());

		//add inputs
		
		
		for (int i = 0; i < operation.getHasInput()
				.getHasNativeOrComplexObjects().size(); i++) {
			if (operation.getHasInput().getHasNativeOrComplexObjects().get(i) instanceof NativeObject) {
				NativeObject no = (NativeObject) operation.getHasInput()
						.getHasNativeOrComplexObjects().get(i);
				Parameter in = ServiceModelFactory.eINSTANCE.createParameter();
				in.setName(no.getObjectName().getLocalPart());
				oper.getInput().add(in);
			} else if (operation.getHasInput().getHasNativeOrComplexObjects()
					.get(i) instanceof ComplexObject) {
				ComplexObject co = (ComplexObject) operation.getHasInput()
						.getHasNativeOrComplexObjects().get(i);
//				Parameter in = ServiceModelFactory.eINSTANCE.createParameter();
//				in.setName(co.getObjectName().getLocalPart());
//				oper.getInput().add(in);
				List<Parameter> parameters = addInputsToModel(co
						.getHasComplexObjects());
				for (int j = 0; j < parameters.size(); j++) {
					oper.getInput().add(parameters.get(j));
				}
			}
		}

		
		//add outputs
		for (int i = 0; i < operation.getHasOutput()
				.getHasNativeOrComplexObjects().size(); i++) {
			if (operation.getHasOutput().getHasNativeOrComplexObjects().get(i) instanceof NativeObject) {
				NativeObject no = (NativeObject) operation.getHasOutput()
						.getHasNativeOrComplexObjects().get(i);
				Parameter in = ServiceModelFactory.eINSTANCE.createParameter();
				in.setName(no.getObjectName().getLocalPart());
				oper.getOutput().add(in);
			} else if (operation.getHasOutput().getHasNativeOrComplexObjects()
					.get(i) instanceof ComplexObject) {
				ComplexObject co = (ComplexObject) operation.getHasOutput()
						.getHasNativeOrComplexObjects().get(i);
//				Parameter in = ServiceModelFactory.eINSTANCE.createParameter();
//				in.setName(co.getObjectName().getLocalPart());
//				oper.getOutput().add(in);
				List<Parameter> parameters = addInputsToModel(co
						.getHasComplexObjects());
				for (int j = 0; j < parameters.size(); j++) {
					oper.getOutput().add(parameters.get(j));
				}
			}
		}
		
		
		
		userService.getOperations().add(oper);

		resource.getContents().add(userService);

		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
		}
	}

	static private List<Parameter> addInputsToModel(Vector vec) {
		List<Parameter> parameters = new ArrayList<Parameter>();
		for (int i = 0; i < vec.size(); i++) {
			if (vec.get(i) instanceof NativeObject) {
				NativeObject no = (NativeObject) vec.get(i);
				Parameter in = ServiceModelFactory.eINSTANCE.createParameter();
				in.setName(no.getObjectName().getLocalPart());
				parameters.add(in);

			} else if (vec.get(i) instanceof ComplexObject) {
				ComplexObject co = (ComplexObject) vec.get(i);
				Parameter in = ServiceModelFactory.eINSTANCE.createParameter();
				in.setName(co.getObjectName().getLocalPart());
				parameters.add(in);
				if (co.getHasComplexObjects() != null) {
					List<Parameter> list = addInputsToModel(co
							.getHasComplexObjects());
					parameters.addAll(list);
				}
				if (co.getHasNativeObjects() != null) {
					List<Parameter> list = addInputsToModel(co
							.getHasNativeObjects());
					parameters.addAll(list);
				}
			}
		}
		return parameters;
	}

}
