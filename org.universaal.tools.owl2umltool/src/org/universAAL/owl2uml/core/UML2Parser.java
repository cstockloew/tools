package org.universAAL.owl2uml.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mindswap.pellet.jena.PelletInfGraph;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.universAAL.owl2uml.uml2.UML2Factory;

import com.hp.hpl.jena.ontology.DataRange;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.UnionClass;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.OWL;

public class UML2Parser {

	private OntModel model;

	private String NS;
	private boolean optionReasoner;

	private UML2Factory uml2Factory;
	private MyHashMap dataProperties;

	private MyHashMap parents;
	private Set<OntClass> unsatConcepts;
	private Set<String> visitedObjectProperty;

	public UML2Parser() {

	}

	public void loadOntology(String ontology, String uri, boolean reasoner) {

		NS = uri + "#";
		optionReasoner = reasoner;

		if (!optionReasoner) {
			model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

			Information.printInfo("\t Reading..." + ontology);

			String inputFileName = ontology;

			// use the FileManager to find the input file
			InputStream inp = FileManager.get().open(inputFileName);
			if (inp == null) {
				throw new IllegalArgumentException("File: " + inputFileName
						+ " not found");
			}

			// read the RDF/XML file
			model.read(inp, "RDF/XML-ABBREV");

			Information.printInfo("Read DONE " + inputFileName);

			Information.printInfo("Read base name................."
					+ model.getNsPrefixMap()
							.get("")
							.toString()
							.substring(
									0,
									model.getNsPrefixMap().get("").toString()
											.length() - 1));

			uml2Factory = new UML2Factory(
					model.getNsPrefixMap()
							.get("")
							.toString()
							.substring(
									0,
									model.getNsPrefixMap().get("").toString()
											.length() - 1));

			dataProperties = new MyHashMap(10);
			parents = new MyHashMap(30);
			visitedObjectProperty = new HashSet(10);

			// -----------------

		} else {

			model = ModelFactory
					.createOntologyModel(PelletReasonerFactory.THE_SPEC);
			// model =
			// ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);

			Information.printInfo("\t Reading...");
			model.read(ontology);
			Information.printInfo("\t [ok]");

			// load the model to the reasoner
			Information.printInfo("\t Preparing...");
			model.prepare();
			Information.printInfo("\t [ok]");

			Information.printInfo("Classifying...");
			((PelletInfGraph) model.getGraph()).getKB().classify();
			Information.printInfo("done");

		}

	}

	public void generateUMLContent() {

		Information.printInfo(" With reasoner: " + optionReasoner);

		if (!optionReasoner) {
			setAllDateProperty();
			addOWLConcepts();
			addObjectProperties();
		} else {

			OntClass owlThing = model.getOntClass(OWL.Thing.getURI());
			OntClass owlNothing = model.getOntClass(OWL.Nothing.getURI());

			unsatConcepts = collect(owlNothing.listEquivalentClasses());

			CreateTree(owlThing);

			uml2Factory.createClass("Thing", null);

			Set<String> concepts = parents.keySet();
			Iterator<String> it = concepts.iterator();
			while (it.hasNext()) {
				String concept = it.next();

				OntClass cls = model.getOntClass(concept);

				Information.printInfo("\n\t\tPreparing concept: "
						+ cls.getLocalName());

				Set<String> objectsProperties = new HashSet<String>(5);
				Set<ObjectPropertyRepresentation> directPropertiesObj = new HashSet<ObjectPropertyRepresentation>(
						5);
				Set<String> directPropertiesData = new HashSet<String>(5);
				ExtendedIterator propertiesDirect = cls
						.listDeclaredProperties(true);

				while (propertiesDirect.hasNext()) {
					OntProperty pro = (OntProperty) propertiesDirect.next();
					if (pro.isDatatypeProperty()) {
						directPropertiesData.add(pro.getLocalName());
					} else if (pro.isObjectProperty()) {
						objectsProperties.add(pro.getURI());
						ObjectPropertyRepresentation objPr = createDirectObjectPropertyRepresentation(pro);
						if (objPr != null)
							directPropertiesObj.add(objPr);
					}
				}

				ExtendedIterator propertiesIt = cls
						.listDeclaredProperties(false); // check false
				while (propertiesIt.hasNext()) {
					OntProperty pro = (OntProperty) propertiesIt.next();

					if (pro.isDatatypeProperty()) {
						if (!directPropertiesData.contains(pro.getLocalName()))
							directPropertiesData.add("(" + pro.getLocalName()
									+ ")");
					} else if (pro.isObjectProperty()) {
						if (!objectsProperties.contains(pro.getURI())) {
							ObjectPropertyRepresentation objPr = createIndirectObjectPropertyRepresentation(
									pro, cls.getURI());
							if (objPr != null)
								directPropertiesObj.add(objPr);
						}
					}
				}

				uml2Factory.createClass(getName(concept), parents.get(concept)
						.iterator(), directPropertiesData.iterator());
				uml2Factory.createObjectProperties(directPropertiesObj
						.iterator());
			}

		}
	}

	private ObjectPropertyRepresentation createDirectObjectPropertyRepresentation(
			OntProperty pr) {
		/*
		 * There are only one property with the same name
		 */

		ObjectPropertyRepresentation obj = new ObjectPropertyRepresentation(
				pr.getURI(), false);

		Information
				.printInfo("\t\t\t---Object Property --------------------- ");
		Information.printInfo("\t\t\tName:" + obj.getLocalName());
		String minCardSource = "0";
		String maxCardSource = "-1"; // equivale a n , por defecto card.
		// 0..n

		String minCardTarget = "0";
		String maxCardTarget = "-1";

		if (pr.isFunctionalProperty()) {
			minCardTarget = "1";
			maxCardTarget = "1";

		}
		if (pr.isInverseFunctionalProperty()) {
			minCardSource = "1";
			maxCardTarget = "1";
		}
		obj.setCardinalitySource(minCardSource, maxCardSource);
		obj.setCardinalityTarget(minCardTarget, maxCardTarget);

		Information.printInfo("\t\t\tDomain: ");

		ExtendedIterator domains = pr.listDomain();
		while (domains.hasNext()) { // only one time
			OntResource res = (OntResource) domains.next();
			if (res.isClass()) {
				Information.printInfo("\t\t\t\t" + res.getLocalName());
				obj.putDomain(res.getLocalName());

			}
		}

		Information.printInfo("\t\t\tRange: ");
		ExtendedIterator ranges = pr.listRange();
		while (ranges.hasNext()) {
			OntResource res = (OntResource) ranges.next();
			if (res.isClass()) {
				OntClass tmp = res.asClass();
				if (tmp.isUnionClass()) {
					Information.printInfo("\t\t\t  asUnionClass:");

					UnionClass uclass = tmp.asUnionClass();
					ExtendedIterator uIt = uclass.listOperands();
					while (uIt.hasNext()) {
						OntResource cl = (OntResource) uIt.next();
						Information.printInfo("\t\t\t\t" + cl.getLocalName());
						obj.putRange(cl.getLocalName());
					}

				} else {
					Information.printInfo("\t\t\t\t" + res.getLocalName());
					obj.putRange(res.getLocalName());
				}
			}
		}
		Information.printInfo("\t\t\t Is inferred Property: "
				+ obj.isInferred());
		Information.printInfo("\t\t\t--------------------------- ");
		return obj;
	}

	private ObjectPropertyRepresentation createIndirectObjectPropertyRepresentation(
			OntProperty pr, String fromURI) {

		if (!visitedObjectProperty.contains(pr.toString())) {

			ObjectPropertyRepresentation obj = new ObjectPropertyRepresentation(
					pr.getURI());

			Information
					.printInfo("\t\t\t---Object Property --------------------- ");
			Information.printInfo("\t\t\tName:*()" + obj.getLocalName());
			String minCardSource = "0";
			String maxCardSource = "-1";
			// 0..n

			String minCardTarget = "0";
			String maxCardTarget = "-1";

			if (pr.isFunctionalProperty()) {
				minCardTarget = "1";
				maxCardTarget = "1";

			}
			if (pr.isInverseFunctionalProperty()) {
				minCardSource = "1";
				maxCardTarget = "1";
			}
			obj.setCardinalitySource(minCardSource, maxCardSource);
			obj.setCardinalityTarget(minCardTarget, maxCardTarget);

			Information.printInfo("\t\t\tDomain: ");

			ExtendedIterator domains = pr.listDomain();
			while (domains.hasNext()) {
				OntResource res = (OntResource) domains.next();
				if (res.isClass()) {
					OntClass tmp = res.asClass();
					if (tmp.isUnionClass()) {
						Information.printInfo("\t\t\t\t  asUnionClass:");

						UnionClass uclass = tmp.asUnionClass();
						ExtendedIterator uIt = uclass.listOperands();
						while (uIt.hasNext()) {
							OntResource cl = (OntResource) uIt.next();
							Information.printInfo("\t\t\t\t"
									+ cl.getLocalName());
							obj.putDomain(cl.getLocalName());
							if (obj.isInferred())
								if (!cl.getURI().equals(fromURI)) {
									obj.setInferred(true);
								} else {
									obj.setInferred(false);

								}
						}

						if (obj.isInferred())
							return null;
						else {
							visitedObjectProperty.add(pr.toString());
						}

					} else {
						Information.printInfo("\t\t\t\t" + res.getLocalName());

						if (!res.getURI().equals(fromURI)) {
							obj.setInferred(true);
							obj.putDomain(getName(fromURI));
						} else {
							obj.setInferred(false);
							visitedObjectProperty.add(pr.toString());
							obj.putDomain(res.getLocalName());
						}
					}
				}
			}

			Information.printInfo("\t\t\tRange: ");
			ExtendedIterator ranges = pr.listRange();
			while (ranges.hasNext()) {
				OntResource res = (OntResource) ranges.next();
				if (res.isClass()) {
					OntClass tmp = res.asClass();
					if (tmp.isUnionClass()) {
						Information.printInfo("\t\t\t  asUnionClass:");

						UnionClass uclass = tmp.asUnionClass();
						ExtendedIterator uIt = uclass.listOperands();
						while (uIt.hasNext()) {
							OntResource cl = (OntResource) uIt.next();
							Information.printInfo("\t\t\t\t"
									+ cl.getLocalName());
							obj.putRange(cl.getLocalName());
						}

					} else {
						Information.printInfo("\t\t\t\t" + res.getLocalName());
						obj.putRange(res.getLocalName());
					}
				}
			}
			Information.printInfo("\t\t\t Is inferred Property: "
					+ obj.isInferred());
			Information.printInfo("\t\t\t--------------------------- ");
			return obj;
		}
		return null;
	}

	private OntClass CreateTree(OntClass cls) {
		if (unsatConcepts.contains(cls)) {
			return null;
		}

		Information.printInfo("\t\tAnalysing : " + cls.getLocalName());

		Set<OntClass> processedSubs = new HashSet<OntClass>();
		// get only direct subclasses
		ExtendedIterator subs = cls.listSubClasses(true);
		while (subs.hasNext()) {

			OntClass sub = (OntClass) subs.next();

			if (sub.isAnon())
				continue;
			if (processedSubs.contains(sub))
				continue;

			OntClass subTree = CreateTree(sub);
			// if set contains owl:Nothing tree will be null
			if (subTree != null) {
				parents.put(subTree.toString(), getName(cls.toString()));
				processedSubs.add(subTree);
			}
		}

		return cls;
	}

	public void write(String file) throws IOException {

		uml2Factory.write(file);
	}

	private void addOWLConcepts() {
		Set<String> processedSubs = new HashSet<String>();

		// Direct class from Thing and equivalent class not appear due to
		// inference troubles.
		String queryString = "PREFIX  rdfs:  <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX : <"
				+ NS
				+ "> \n"
				+ "SELECT ?subclasses ?superclase \n"
				+ "WHERE { ?subclasses rdfs:subClassOf ?superclase } \n";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		// All Class and direct class from thing
		String queryString2 = "PREFIX  rdfs:  <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX  owl:  <http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX : <"
				+ NS
				+ "> \n"
				+ "SELECT ?classes  \n"
				+ "WHERE { ?classes rdf:type owl:Class } \n";
		Query query2 = QueryFactory.create(queryString2);
		QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);

		try {
			ResultSet results = qexec.execSelect();

			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String x = soln.get("subclasses").toString();
				String r = soln.get("superclase").toString();

				if (x.indexOf('#') > 1 && r.indexOf('#') > 1 && !x.equals(r)) {
					String nameChild = x.substring(x.indexOf('#') + 1);
					String nameParent = r.substring(r.indexOf('#') + 1);

					if (!processedSubs.contains(nameChild)) {

						// Multiple parents
						Information.printInfo("\tAnalysing: " + nameChild);
						OntClass cl = model.getOntClass(x);
						ExtendedIterator it = cl.listSuperClasses(true);
						List<String> list = new ArrayList<String>(1);
						while (it.hasNext()) {
							Object val = it.next();
							if (val instanceof OntClass) {
								OntClass cl2 = (OntClass) val;
								String name = cl2.getLocalName();
								if (name != null) {
									Information
											.printInfo("\t\t adding Parent: "
													+ name);
									list.add(name);
								}

							}
						}
						// Information.printInfo("\tPARENT " + nameParent);
						// Information.printInfo("\t adding CLASS: " +
						// nameChild+ ".hasParent." + nameParent);

						uml2Factory.createClass(nameChild, list.iterator(),
								(dataProperties.get(nameChild) == null ? null
										: dataProperties.get(nameChild)
												.iterator()));

						processedSubs.add(nameChild);
					}
				}
			}

			ResultSet results2 = qexec2.execSelect();
			for (; results2.hasNext();) {
				QuerySolution soln2 = results2.nextSolution();
				String x2 = soln2.get("classes").toString();
				if (x2.indexOf('#') > 1) {
					String name = x2.substring(x2.indexOf('#') + 1);
					if (!processedSubs.contains(name)) {
						OntClass cls = model.getOntClass(x2);
						OntClass s = cls.getEquivalentClass();
						if (s == null) {

							Information.printInfo("\t adding CLASS: " + name
									+ ".hasParent.Thing");
							if (dataProperties.get(name) != null) {
								uml2Factory.createClass(name, "" + "Thing",
										dataProperties.get(name).iterator());
							} else {
								uml2Factory.createClass(name, "" + "Thing",
										null);
							}
						} else {
							Information.printInfo("[Warning: Equivalent Class]"
									+ s.toString());
						}
					}
				}
			}
			Information.printInfo("\t adding CLASS: Thing");
			uml2Factory.createClass("Thing", null);

		} finally {
			qexec2.close();
		}
	}

	private String getName(String r) {
		return r.substring(r.indexOf('#') + 1);
	}

	private void addObjectProperties() {

		Information
				.printInfo("\t--- Adding Object Properties --------------------- ");

		Set<ObjectPropertyRepresentation> directPropertiesObj = new HashSet<ObjectPropertyRepresentation>(
				5);

		String queryString = "PREFIX  rdfs:  <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX  owl:  <http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX : <"
				+ NS
				+ "> \n"
				+ "SELECT ?proper ?domain ?range \n"
				+ "WHERE { ?proper rdf:type owl:ObjectProperty ."
				+ " ?proper rdfs:domain ?domain ."
				+ " OPTIONAL{?proper rdfs:range ?range}} \n";

		Information.printInfo(queryString);

		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		try {
			ResultSet results = qexec.execSelect();

			// ResultSetFormatter.out(System.out, results, query);

			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				String p = soln.get("proper").toString();

				ObjectPropertyRepresentation obj = new ObjectPropertyRepresentation(
						p, false);
				Information.printInfo("\tName:" + obj.getLocalName());

				OntProperty pr = model.getOntProperty(p);

				String minCardSource = "0";
				String maxCardSource = "-1";
				// 0..n

				String minCardTarget = "0";
				String maxCardTarget = "-1";

				if (pr.isFunctionalProperty()) {
					minCardTarget = "1";
					maxCardTarget = "1";

				}
				if (pr.isInverseFunctionalProperty()) {
					minCardSource = "1";
					maxCardTarget = "1";
				}
				obj.setCardinalitySource(minCardSource, maxCardSource);
				obj.setCardinalityTarget(minCardTarget, maxCardTarget);

				Information.printInfo("\tDomain: ");
				Resource res3 = (Resource) soln.get("domain");
				OntResource res = model.getOntResource(res3);
				if (res.isClass()) {
					OntClass tmp = res.asClass();
					if (tmp.isUnionClass()) {
						Information.printInfo("\t  asUnionClass:");

						UnionClass uclass = tmp.asUnionClass();
						ExtendedIterator uIt = uclass.listOperands();
						while (uIt.hasNext()) {
							OntResource cl = (OntResource) uIt.next();
							Information.printInfo("\t\t" + cl.getLocalName());
							obj.putDomain(cl.getLocalName());

						}

					} else {
						Information.printInfo("\t\t" + res.getLocalName());
						obj.putDomain(tmp.getLocalName());
					}
				}

				Information.printInfo("\tRange: ");
				Resource res2 = (Resource) soln.get("range");
				res = model.getOntResource(res2);
				if (res.isClass()) {
					OntClass tmp = res.asClass();
					if (tmp.isUnionClass()) {
						Information.printInfo("\t  asUnionClass:");

						UnionClass uclass = tmp.asUnionClass();
						ExtendedIterator uIt = uclass.listOperands();
						while (uIt.hasNext()) {
							OntResource cl = (OntResource) uIt.next();
							Information.printInfo("\t\t" + cl.getLocalName());
							obj.putRange(cl.getLocalName());

						}

					} else {
						Information.printInfo("\t\t" + res.getLocalName());
						obj.putRange(tmp.getLocalName());
					}
				}

				directPropertiesObj.add(obj);

			}

			uml2Factory.createObjectProperties(directPropertiesObj.iterator());

		} finally {
			qexec.close();
		}
	}

	private void setAllDateProperty() {
		String queryString = "PREFIX  rdfs:  <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "PREFIX  owl:  <http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX  rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				// + "PREFIX myonto: <"
				// + NS
				// + "> \n"
				+ "SELECT ?proper ?range ?domain \n"
				+ "WHERE { ?proper rdf:type owl:DatatypeProperty ."
				+ " ?proper rdfs:domain ?domain ."
				+ " OPTIONAL{?proper rdfs:range ?range}} \n";
		Information.printInfo(queryString);
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

		ResultSet results = qexec.execSelect();
		// ResultSetFormatter.out(System.out, results, query);

		Information.printInfo("\tAnalysing datatypesproperties: ");

		while (results.hasNext()) {

			QuerySolution soln = results.nextSolution();
			String p = soln.get("proper").toString();
			String d = soln.get("domain").toString(); // Get a result
			String nameProp = p.substring(p.indexOf('#') + 1);
			String nameDomain = d.substring(d.indexOf('#') + 1);
			RDFNode rg = soln.get("range");
			String r = "";
			ArrayList<String> rangeProp = new ArrayList<String>();
			if (rg != null) {
				r = rg.toString();

				if (rg.isAnon()) {

					DatatypeProperty hg = model.getDatatypeProperty(p);
					OntResource hgRange = hg.getRange();
					DataRange dr = hgRange.asDataRange();
					Iterator i = dr.listOneOf();
					while (i.hasNext()) {
						Literal l = (Literal) i.next();
						System.out.println("-------------------------------"
								+ l.getLexicalForm());
						rangeProp.add(l.getLexicalForm());
					}
				} else {

					rangeProp.add(r.substring(r.indexOf('#') + 1));
				}
			} else {
				rangeProp.add("any");
			}
			Information.printInfo("\t\t " + nameDomain + " has " + nameProp);
			dataProperties.putRange(nameDomain, nameProp, rangeProp);

		}

	}

	private Set collect(Iterator i) {
		Set set = new HashSet();
		while (i.hasNext()) {
			OntResource res = (OntResource) i.next();
			if (res.isAnon())
				continue;
			set.add(res);
		}
		return set;
	}
}
