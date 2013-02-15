//package org.universaal.tools.packaging.tool.marshaller;
//
//import java.io.File;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBElement;
//import javax.xml.bind.JAXBException;
//import javax.xml.namespace.QName;
//
//import org.universaal.aal_mpa.v1_0.AalMpa;
//import org.universaal.aal_mpa.v1_0.AalMpa.App;
//import org.universaal.aal_mpa.v1_0.AalMpa.App.License;
//import org.universaal.aal_mpa.v1_0.AalMpa.ApplicationManagement;
//import org.universaal.aal_mpa.v1_0.AalMpa.ApplicationPart;
//import org.universaal.aal_mpa.v1_0.AalMpa.ApplicationProfile;
//import org.universaal.aal_mpa.v1_0.AalMpa.ApplicationProfile.AalSpace;
//import org.universaal.aal_mpa.v1_0.AalMpa.ApplicationProfile.AalSpace.AlternativeProfiles;
//import org.universaal.aal_mpa.v1_0.AalMpa.ApplicationProfile.AalSpace.RequirredOntologies;
//import org.universaal.aal_mpa.v1_0.AalMpa.ApplicationProfile.Runtime.Brokers;
//import org.universaal.aal_mpa.v1_0.AalMpa.ApplicationProfile.Runtime.Managers;
//import org.universaal.aal_mpa.v1_0.AalMpa.ApplicationProvider;
//import org.universaal.aal_mpa.v1_0.ArtifactType;
//import org.universaal.aal_mpa.v1_0.Broker;
//import org.universaal.aal_mpa.v1_0.ObjectFactory;
//import org.universaal.aal_mpa.v1_0.OntologyType;
//import org.universaal.aal_mpa.v1_0.OntologyType.Location;
//import org.universaal.aal_mpa.v1_0.ProfileType;
//import org.universaal.aal_mpa.v1_0.SpaceType;
//import org.universaal.aal_mpa.v1_0.VersionType;
//
//public class Marshaller {
//
//	// AAL MPA:
//	//	APP
//	//	APPLICATION PROFILE
//	//	APPLICATION PROVIDER
//	//	APPLICATION MANAGEMENT
//	//	APPLICATION PART(S)
//	private AalMpa mpa;
//	private ObjectFactory factory;
//
//	private File xml;
//
//	public Marshaller(File f){
//		xml = f;
//		factory = new ObjectFactory();
//		mpa = factory.createAalMpa();		
//	}
//
//	public void marshalToMPA() throws JAXBException{
//
//		JAXBContext jaxbContext = JAXBContext.newInstance(AalMpa.class);
//		javax.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//		createMPA();
//		jaxbMarshaller.marshal(mpa, xml);
//	}
//
//	private void createMPA(){
//
//		mpa.setApp(createApp());
//
//		mpa.setApplicationProfile(createAppProf());
//
//		ApplicationProvider appProv = new ApplicationProvider();
//		mpa.setApplicationProvider(appProv);
//
//		ApplicationManagement appManag = new ApplicationManagement();
//		mpa.setApplicationManagement(appManag);
//
//		ApplicationPart parts = new ApplicationPart();
//		mpa.setApplicationPart(parts);
//	}
//
//	private App createApp(){
//
//		App app = factory.createAalMpaApp();
//		app.setName("");
//		app.setDistributed(false);
//		app.setAppId("");
//		app.setDescription("");
//		app.setLicense(createLicense());
//		app.setSla("");
//
//		return app;
//	}
//
//	private License createLicense(){
//
//		License l = factory.createAalMpaAppLicense();
//		l.setLink("");
//		l.setName("");
//
//		return l;
//	}
//
//	private ApplicationProfile createAppProf(){
//
//		ApplicationProfile prof = factory.createAalMpaApplicationProfile();
//		prof.setAalSpace(createAALspace());
//		prof.setRuntime(createRuntime());
//
//		return prof;
//	}
//
//	private AalSpace createAALspace(){
//
//		AalSpace space = factory.createAalMpaApplicationProfileAalSpace();
//		space.setAlternativeProfiles(createAlternativeProf());
//		space.setRequirredOntologies(createRequirredOntologies());
//		space.setTargetProfile(createProfile());
//
//		return space;
//	}
//
//	private ProfileType createProfile(){
//
//		ProfileType prof = new ProfileType();
//		prof.setProfileId(createSpace());
//		prof.setVersion(createVersion());
//
//		return prof;
//	}
//
//	private AlternativeProfiles createAlternativeProf(){
//
//		AlternativeProfiles prof = factory.createAalMpaApplicationProfileAalSpaceAlternativeProfiles();
//		prof.getProfile().add(createAltProfile());
//
//		return prof;
//	}
//
//	private ProfileType createAltProfile(){
//
//		ProfileType prof = factory.createProfileType();
//		prof.setProfileId(createSpace());
//		prof.setVersion(createVersion());
//
//		return prof;
//	}
//
//	private SpaceType createSpace(){
//
//		return SpaceType.CAR_SPACE;
//		//return SpaceType.HOME_SPACE;
//		//return SpaceType.MARKET_SPACE;
//	}
//
//	private VersionType createVersion(){
//
//		VersionType version = factory.createVersionType();
//		version.setBuild("");
//		version.setMajor(0);
//		version.setMicro(0);
//		version.setMinor(0);
//		version.setString("");
//
//		return version;
//	}
//
//	private org.universaal.aal_mpa.v1_0.AalMpa.ApplicationProfile.Runtime createRuntime(){
//
//		org.universaal.aal_mpa.v1_0.AalMpa.ApplicationProfile.Runtime runtime = factory.createAalMpaApplicationProfileRuntime();
//		runtime.setBrokers(createRuntimeBroker());
//		runtime.setManagers(createManagers());
//		runtime.setMiddleware(createArtifact());
//
//		return runtime;
//	}
//
//	private Brokers createRuntimeBroker(){
//
//		Brokers brokers = factory.createAalMpaApplicationProfileRuntimeBrokers();
//		brokers.getBroker().add(createBroker());
//
//		return brokers;
//	}
//
//	private Broker createBroker(){
//
//		Broker broker = factory.createBroker();
//		broker.setName("");
//		broker.setSoftware(createArtifact());
//
//		return broker;
//	}
//
//	private ArtifactType createArtifact(){
//
//		ArtifactType artifact = new ArtifactType();
//		artifact.setArtifactId("");
//		artifact.setVersion(createVersion());
//
//		return artifact;
//	}
//
//	private Managers createManagers(){
//
//		Managers managers = new Managers();
//		managers.getManager().add(createArtifact());
//
//		return managers;
//	}
//
//	private RequirredOntologies createRequirredOntologies(){
//
//		RequirredOntologies ontologies = new RequirredOntologies();
//		ontologies.getOntology().add(createOntology());
//
//		return ontologies;
//	}
//
//	private OntologyType createOntology(){
//
//		OntologyType ontology = new OntologyType();
//		ontology.setLocation(createLocation());
//		ontology.setName("");
//		ontology.setUri("");
//
//		return ontology;
//	}
//
//	private Location createLocation(){
//
//		Location loc = new Location();
//		loc.getPathOrRuntimeIdOrUrl().add(new JAXBElement(new QName("http://universaal.org/aal-mpa/v1.0.0"), JAXBElement.class, new String("")));
//
//		return loc;
//	}
//}