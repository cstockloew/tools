package org.universAAL.owl2uml.uml2;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.universAAL.owl2uml.core.Information;
import org.universAAL.owl2uml.core.ObjectPropertyRepresentation;

public class UML2Factory {

	private Model owl2Model;
	private Profile profileTypes;
	private PrimitiveType booleanPrimitive, stringPrimitive, integerPrimitive,
			unlimitedNaturalPrimitive;

	protected static final ResourceSet RESOURCE_SET = new ResourceSetImpl();

	public UML2Factory(String nameModel) {

		registerResourceFactories();
		// Registering pathmaps dynamically
		final String profile = "profiles/UML2.profile.uml";
		java.net.URL url = getClass().getClassLoader().getResource(profile);
		if (url == null) {
			throw new RuntimeException("Error getting UML2.profile.uml");
		}
		String urlString = url.toString();
		if (!urlString.endsWith(profile)) {
			throw new RuntimeException("Error getting UML2.profile.uml. Got: "
					+ urlString);
		}
		urlString = urlString.substring(0,
				urlString.length() - profile.length());
		URI uri = URI.createURI(urlString);

		registerPathmaps(uri);

		Information.printInfo("Creating model...");
		owl2Model = createModel(nameModel);
		registerResourceFactories();

		profileTypes = createProfile("profileTypes");
		booleanPrimitive = importPrimitiveType(profileTypes, "Boolean");
		stringPrimitive = importPrimitiveType(profileTypes, "String");
		integerPrimitive = importPrimitiveType(profileTypes, "Integer");
		unlimitedNaturalPrimitive = importPrimitiveType(profileTypes,
				"UnlimitedNatural");
		defineProfile(profileTypes);
		applyProfile(owl2Model, profileTypes);

		PackageImport prims = UMLFactory.eINSTANCE.createPackageImport();
		prims.setImportedPackage(owl2Model);
		createClass(owl2Model, "Thing", false);
	}

	public static Model createModel(String name) {
		Model model = UMLFactory.eINSTANCE.createModel();
		model.setName(name);

		Information.printInfo("Model '" + model.getQualifiedName()
				+ "' created.");

		return model;
	}

	protected static org.eclipse.uml2.uml.Package createPackage(
			org.eclipse.uml2.uml.Package nestingPackage, String name) {
		org.eclipse.uml2.uml.Package package_ = nestingPackage
				.createNestedPackage(name);

		Information.printInfo("Package '" + package_.getQualifiedName()
				+ "' created.");

		return package_;
	}

	protected static PrimitiveType createPrimitiveType(
			org.eclipse.uml2.uml.Package package_, String name) {
		PrimitiveType primitiveType = package_.createOwnedPrimitiveType(name);

		Information.printInfo("Primitive type '"
				+ primitiveType.getQualifiedName() + "' created.");

		return primitiveType;
	}

	protected static Enumeration createEnumeration(
			org.eclipse.uml2.uml.Package package_, String name) {
		Enumeration enumeration = package_.createOwnedEnumeration(name);

		Information.printInfo("Enumeration '" + enumeration.getQualifiedName()
				+ "' created.");

		return enumeration;
	}

	protected static EnumerationLiteral createEnumerationLiteral(
			Enumeration enumeration, String name) {
		EnumerationLiteral enumerationLiteral = enumeration
				.createOwnedLiteral(name);

		Information.printInfo("Enumeration literal '"
				+ enumerationLiteral.getQualifiedName() + "' created.");

		return enumerationLiteral;
	}

	public static Generalization createGeneralization(
			Classifier specificClassifier, Classifier generalClassifier) {
		Generalization generalization = specificClassifier
				.createGeneralization(generalClassifier);

		Information.printInfo("Generalization "
				+ specificClassifier.getQualifiedName() + " ->> "
				+ generalClassifier.getQualifiedName() + " created.");

		return generalization;
	}

	protected static org.eclipse.uml2.uml.Classifier getClass(
			org.eclipse.uml2.uml.Package package_, String name) {
		PackageableElement pe = null;
		Information.printInfo("Trying to get Class with name " + name);

		pe = package_.getPackagedElement(name);
		if (pe != null) {
			Information.printInfo("Trying to get Class with name '" + name
					+ "'... Found:" + pe.getQualifiedName());
		}

		return (Classifier) pe;
	}

	public void write(String file) throws IOException {

		Information.printInfo("Saving model...");
		String umlPath = file.substring(file.lastIndexOf("/") + 1,
				(file.lastIndexOf("\\") + 1));
		String umlFile = file.substring(file.lastIndexOf("\\") + 1,
				file.lastIndexOf("."));

		/*
		 * UML2Article.save(owl2Model,
		 * URI.createURI("test").appendSegment(umlFile)
		 * .appendFileExtension(UMLResource.FILE_EXTENSION));
		 */
		save(owl2Model, URI.createFileURI(umlPath).appendSegment(umlFile)
				.appendFileExtension(UMLResource.FILE_EXTENSION));
	}

	public void createClass(String name, String nameParent) {

		if (nameParent != null) {

			createClass(owl2Model, name, false);
			getClass(owl2Model, nameParent);

		}

	}

	public static org.eclipse.uml2.uml.Classifier createClass(
			org.eclipse.uml2.uml.Package package_, String name,
			boolean isAbstract) {
		org.eclipse.uml2.uml.Class class_ = package_.createOwnedClass(name,
				isAbstract);

		Information.printInfo("Class '" + class_.getQualifiedName()
				+ "' created.");

		return class_;
	}

	public void createClass(String name, String nameParent,
			Iterator dataProperties) {

		if (nameParent != null) {

			if (getClass(owl2Model, name) == null) {
				createClass(owl2Model, name, false);
			}
			if (getClass(owl2Model, nameParent) == null) {
				createClass(owl2Model, nameParent, false);
			}

			createGeneralization(getClass(owl2Model, name),
					getClass(owl2Model, nameParent));

		}

		// adding dataproperties
		if (dataProperties != null) {
			if (getClass(owl2Model, name) == null) {
				createClass(owl2Model, name, false);
			}
			while (dataProperties.hasNext()) {
				String nameProp = (String) dataProperties.next();
				Information.printInfo("\t\t adding property: " + nameProp);

				String typeOfProp = nameProp
						.substring(nameProp.indexOf('#') + 1);
				String nameOfProp = nameProp
						.substring(0, nameProp.indexOf('#'));
				String enumerations[] = null;
				if (typeOfProp.indexOf("#") > 0) {
					enumerations = typeOfProp.split("#");

					Enumeration enumer = createEnumeration(owl2Model,
							nameOfProp + "Enumeration");
					for (int jj = 0; jj < enumerations.length; jj++) {
						createEnumerationLiteral(enumer, enumerations[jj]);
					}

					createAttribute((Class) getClass(owl2Model, name),
							nameOfProp, enumer, 0, 1);
				} else {

					if (typeOfProp.equals("boolean")) {
						createAttribute((Class) getClass(owl2Model, name),
								nameOfProp, booleanPrimitive, 0, 1);
					} else if (typeOfProp.equals("string")) {
						createAttribute((Class) getClass(owl2Model, name),
								nameOfProp, stringPrimitive, 0, 1);
					} else if (typeOfProp.equals("int")) {
						createAttribute((Class) getClass(owl2Model, name),
								nameOfProp, integerPrimitive, 0, 1);
					} else if ((PrimitiveType) getClass(owl2Model, typeOfProp) != null) {
						createAttribute(
								(Class) getClass(owl2Model, name),
								nameOfProp,
								(PrimitiveType) getClass(owl2Model, typeOfProp),
								0, 1);
					} else {

						createAttribute((Class) getClass(owl2Model, name),
								nameOfProp, unlimitedNaturalPrimitive, 0, 1);
					}
				}

			}
		}

	}

	public void createClass(String name, Iterator<String> parents,
			Iterator<String> dataProperties) {

		// adding dataproperties
		if (dataProperties != null) {
			if (getClass(owl2Model, name) == null) {
				createClass(owl2Model, name, false);
			}
			while (dataProperties.hasNext()) {
				String nameProp = dataProperties.next();
				Information.printInfo("\t\t adding property: " + nameProp);

				String typeOfProp = nameProp
						.substring(nameProp.indexOf('#') + 1);
				String nameOfProp = nameProp
						.substring(0, nameProp.indexOf('#'));
				String enumerations[] = null;
				if (typeOfProp.indexOf("#") > 0) {
					enumerations = typeOfProp.split("#");

					Enumeration enumer = createEnumeration(owl2Model,
							nameOfProp + "Enumeration");
					for (int jj = 0; jj < enumerations.length; jj++) {
						createEnumerationLiteral(enumer, enumerations[jj]);
					}

					createAttribute((Class) getClass(owl2Model, name),
							nameOfProp, enumer, 0, 1);
				} else {
					if (typeOfProp.equals("boolean")) {
						createAttribute((Class) getClass(owl2Model, name),
								nameOfProp, booleanPrimitive, 0, 1);
					} else if (typeOfProp.equals("string")) {
						createAttribute((Class) getClass(owl2Model, name),
								nameOfProp, stringPrimitive, 0, 1);
					} else if (typeOfProp.equals("int")) {
						createAttribute((Class) getClass(owl2Model, name),
								nameOfProp, integerPrimitive, 0, 1);
					} else if ((PrimitiveType) getClass(owl2Model, typeOfProp) != null) {
						createAttribute(
								(Class) getClass(owl2Model, name),
								nameOfProp,
								(PrimitiveType) getClass(owl2Model, typeOfProp),
								0, 1);
					} else {

						createAttribute((Class) getClass(owl2Model, name),
								nameOfProp, unlimitedNaturalPrimitive, 0, 1);
					}
				}

			}
		}

		// String generalization = "";
		while (parents.hasNext()) {

			String nameParent = parents.next();

			if (getClass(owl2Model, name) == null) {
				createClass(owl2Model, name, false);
			}

			if (getClass(owl2Model, nameParent) == null) {
				createClass(owl2Model, nameParent, false);
			}

			createGeneralization(getClass(owl2Model, name),
					getClass(owl2Model, nameParent));

		}

	}

	public void createObjectProperties(Iterator<ObjectPropertyRepresentation> it) {
		while (it.hasNext()) {
			ObjectPropertyRepresentation obj = it.next();

			String name = obj.getLocalName();
			// System.out.println(" CREATING OBJECT: " + name);

			Iterator<String> domains = obj.getDomains();
			if (domains != null) {
				while (domains.hasNext()) {
					String domain = domains.next();
					// System.out.println(" \t Domain " + domain);
					if (domain == null) {
						Information
								.printInfo("[Warning] Domain property not specified: "
										+ name);
						break;
					}
					Iterator<String> ranges = obj.getRanges();
					while (ranges.hasNext()) {
						String range = ranges.next();
						// System.out.println("\t\t Range " + range);

						createAssociation(getClass(owl2Model, domain), true,
								AggregationKind.NONE_LITERAL, name,
								Integer.valueOf(obj.getCardinalityTarget()[0]),
								Integer.valueOf(obj.getCardinalityTarget()[1]),
								getClass(owl2Model, range), false,
								AggregationKind.NONE_LITERAL, "",
								Integer.valueOf(obj.getCardinalitySource()[0]),
								Integer.valueOf(obj.getCardinalitySource()[1]));
					}

				}
			}
		}

	}

	public static Profile createProfile(String name) {
		Profile profile = UMLFactory.eINSTANCE.createProfile();
		profile.setName(name);

		Information.printInfo("Profile '" + profile.getQualifiedName()
				+ "' created.");

		return profile;
	}

	public static PrimitiveType importPrimitiveType(
			org.eclipse.uml2.uml.Package package_, String name) {
		Model umlLibrary = (Model) load(URI
				.createURI(UMLResource.UML_PRIMITIVE_TYPES_LIBRARY_URI));

		PrimitiveType primitiveType = (PrimitiveType) umlLibrary
				.getOwnedType(name);

		package_.createElementImport(primitiveType);

		Information.printInfo("Primitive type '"
				+ primitiveType.getQualifiedName() + "' imported.");

		return primitiveType;
	}

	protected static void defineProfile(Profile profile) {
		profile.define();

		Information.printInfo("Profile '" + profile.getQualifiedName()
				+ "' defined.");
	}

	protected static void applyProfile(org.eclipse.uml2.uml.Package package_,
			Profile profile) {
		package_.applyProfile(profile);

		Information
				.printInfo("Profile '" + profile.getQualifiedName()
						+ "' applied to package '"
						+ package_.getQualifiedName() + "'.");
	}

	protected static Property createAttribute(
			org.eclipse.uml2.uml.Class class_, String name, Type type,
			int lowerBound, int upperBound) {
		Property attribute = class_.createOwnedAttribute(name, type,
				lowerBound, upperBound);

		StringBuffer sb = new StringBuffer();

		sb.append("Attribute '");

		sb.append(attribute.getQualifiedName());

		sb.append("' : ");

		sb.append(type.getQualifiedName());

		sb.append(" [");
		sb.append(lowerBound);
		sb.append("..");
		sb.append(LiteralUnlimitedNatural.UNLIMITED == upperBound ? "*"
				: String.valueOf(upperBound));
		sb.append("]");

		sb.append(" created.");

		Information.printInfo(sb.toString());

		return attribute;
	}

	protected static Association createAssociation(Type type1,
			boolean end1IsNavigable, AggregationKind end1Aggregation,
			String end1Name, int end1LowerBound, int end1UpperBound,
			Type type2, boolean end2IsNavigable,
			AggregationKind end2Aggregation, String end2Name,
			int end2LowerBound, int end2UpperBound) {

		Association association = type1.createAssociation(end1IsNavigable,
				end1Aggregation, end1Name, end1LowerBound, end1UpperBound,
				type2, end2IsNavigable, end2Aggregation, end2Name,
				end2LowerBound, end2UpperBound);

		StringBuffer sb = new StringBuffer();

		sb.append("Association ");

		if (null == end1Name || 0 == end1Name.length()) {
			sb.append('{');
			sb.append(type1.getQualifiedName());
			sb.append('}');
		} else {
			sb.append("'");
			sb.append(type1.getQualifiedName());
			sb.append(NamedElement.SEPARATOR);
			sb.append(end1Name);
			sb.append("'");
		}

		sb.append(" [");
		sb.append(end1LowerBound);
		sb.append("..");
		sb.append(LiteralUnlimitedNatural.UNLIMITED == end1UpperBound ? "*"
				: String.valueOf(end1UpperBound));
		sb.append("] ");

		sb.append(end2IsNavigable ? '<' : '-');
		sb.append('-');
		sb.append(end1IsNavigable ? '>' : '-');
		sb.append(' ');

		if (null == end2Name || 0 == end2Name.length()) {
			sb.append('{');
			sb.append(type2.getQualifiedName());
			sb.append('}');
		} else {
			sb.append("'");
			sb.append(type2.getQualifiedName());
			sb.append(NamedElement.SEPARATOR);
			sb.append(end2Name);
			sb.append("'");
		}

		sb.append(" [");
		sb.append(end2LowerBound);
		sb.append("..");
		sb.append(LiteralUnlimitedNatural.UNLIMITED == end2UpperBound ? "*"
				: String.valueOf(end2UpperBound));
		sb.append("]");

		sb.append(" created.");

		Information.printInfo(sb.toString());

		return association;
	}

	public static void registerResourceFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
	}

	public static void registerPathmaps(URI uri) {
		URIConverter.URI_MAP.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP),
				uri.appendSegment("libraries").appendSegment(""));

		URIConverter.URI_MAP.put(URI.createURI(UMLResource.METAMODELS_PATHMAP),
				uri.appendSegment("metamodels").appendSegment(""));

		URIConverter.URI_MAP.put(URI.createURI(UMLResource.PROFILES_PATHMAP),
				uri.appendSegment("profiles").appendSegment(""));
	}

	public static void save(org.eclipse.uml2.uml.Package package_, URI uri) {
		Resource resource = RESOURCE_SET.createResource(uri);
		EList contents = resource.getContents();

		contents.add(package_);

		for (Iterator allContents = UML2Util.getAllContents(package_, true,
				false); allContents.hasNext();) {

			EObject eObject = (EObject) allContents.next();

			if (eObject instanceof Element) {
				contents.addAll(((Element) eObject).getStereotypeApplications());
			}
		}

		try {
			resource.save(null);

			Information.printInfo("Done.");
		} catch (IOException ioe) {
			err(ioe.getMessage());
		}
	}

	protected static org.eclipse.uml2.uml.Package load(URI uri) {
		org.eclipse.uml2.uml.Package package_ = null;

		try {
			Resource resource = RESOURCE_SET.getResource(uri, true);

			package_ = (org.eclipse.uml2.uml.Package) EcoreUtil
					.getObjectByType(resource.getContents(),
							UMLPackage.Literals.PACKAGE);
		} catch (WrappedException we) {
			err(we.getMessage());
			System.exit(1);
		}

		return package_;
	}

	protected static void err(String error) {
		System.err.println(error);
	}
}
