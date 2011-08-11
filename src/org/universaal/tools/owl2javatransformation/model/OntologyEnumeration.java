package org.universaal.tools.owl2javatransformation.model;

import java.util.HashSet;
import java.util.Set;

public final class OntologyEnumeration extends OntologyClass {

	private final Set<Instance> instanceUris;

	public OntologyEnumeration(String simpleName, String uri, String comment,
			Set<OntologyProperty> declaredProps, String superClassName,
			Set<String> importedPackages, String packageName,
			Set<PropertyRestriction> propertyRestrictions, Set<String> instanceUris) {
		super(simpleName, uri, comment, declaredProps, superClassName,
				importedPackages, packageName, propertyRestrictions);
		
		Set<Instance> names = new HashSet<Instance>();
		for (String instanceUri : instanceUris) {
			names.add(new Instance(instanceUri, uriToSimpleName(instanceUri)));
		}
		this.instanceUris = names;
	}

	public Set<Instance> getInstances() {
		return instanceUris;
	}
	
	public static final class Instance {
		
		private final String uri;
		private final String name;
		
		public Instance(String uri, String name) {
			super();
			this.name = name;
			this.uri = uri;
		}

		public String getName() {
			return name;
		}

		public String getUri() {
			return uri;
		}
		
		
	}

}
