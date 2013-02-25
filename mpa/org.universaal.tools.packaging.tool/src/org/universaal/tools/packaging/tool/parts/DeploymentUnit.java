package org.universaal.tools.packaging.tool.parts;

import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.karaf.xmlns.features.v1_0.FeaturesRoot;
import org.apache.karaf.xmlns.features.v1_0.ObjectFactory;

public class DeploymentUnit {

	private String id;
	private OS osType;
	private Platform platformType;	
	private ContainerUnit cu;

	public DeploymentUnit(String id, OS osType){		

		SecureRandom random = new SecureRandom();

		this.id = id+"_"+new BigInteger(130, random).toString(32);;
		this.osType = osType;
		this.platformType = null;
		this.cu = null;
	}

	public DeploymentUnit(String id, Platform platformType){		

		SecureRandom random = new SecureRandom();

		this.id = id+"_"+new BigInteger(130, random).toString(32);;
		this.osType = null;
		this.platformType = platformType;
		this.cu = null;
	}

	public DeploymentUnit(String id, ContainerUnit cu){		

		SecureRandom random = new SecureRandom();

		this.id = id+"_"+new BigInteger(130, random).toString(32);;
		this.osType = null;
		this.platformType = null;
		this.cu = null;
	}

	public String getId() {
		return id;
	}

	public OS getOsType() {
		return osType;
	}

	public Platform getPlatformType() {
		return platformType;
	}

	public ContainerUnit getCu() {
		return cu;
	}

	public void setCu(ContainerUnit cu) {
		this.cu = cu;
	}

	public String getXML(){

		String r = "";

		r = r.concat("<deploymentUnit><id>"+id+"</id>");
		if(cu != null)
			r = r.concat("<containerUnit>"+cu.getXML()+"</containerUnit>");
		else if(osType != null)
			r = r.concat("<osUnit>"+osType.toString()+"</osUnit>");
		else if(platformType != null)
			r = r.concat("<platformUnit>"+platformType.toString()+"</platformUnit>");

		r = r.concat("</deploymentUnit>");
		return r;
	}

	public class ContainerUnit {

		private Container container;

		private Embedding embedding;
		private FeaturesRoot features;

		private Android androidPart;

		public ContainerUnit(Embedding embedding, FeaturesRoot features){
			this.container = Container.KARAF;
			this.embedding = embedding;
			this.features = features;

			this.setAndroidPart(null);
		}

		public ContainerUnit(Android androidPart){
			this.container = Container.ANDROID;
			this.setAndroidPart(androidPart);

			this.embedding = null;
			this.features = null;
		}

		public ContainerUnit(Container container){
			if(container != Container.KARAF && container != Container.ANDROID)
				this.container = container;
			else
				throw new IllegalArgumentException("Please consider using proper constructor if container is Karaf or Android!");

			this.setAndroidPart(null);
			this.embedding = null;
			this.features = null;
		}

		public Container getContainer() {
			return container;
		}

		public void setContainer(Container container) {
			this.container = container;
		}

		public Embedding getEmbedding() {
			return embedding;
		}

		public FeaturesRoot getFeatures() {
			return features;
		}

		public Android getAndroidPart() {
			return androidPart;
		}

		public void setAndroidPart(Android androidPart) {
			this.androidPart = androidPart;
		}

		public String getXML(){

			String r = "";

			if(container != Container.KARAF && container != Container.ANDROID)
				return "<"+container.toString()+"/>";
			else{
				if(container == Container.KARAF){
					r = r.concat("<karaf>");
					r = r.concat("<embedding>"+embedding.toString()+"</embedding>");

					Marshaller marshaller = null;
					try {
						marshaller = JAXBContext.newInstance(ObjectFactory.class).createMarshaller();
					} catch (Exception e) {
						e.printStackTrace();
					}
					StringWriter writer = new StringWriter();

					JAXBElement p = new JAXBElement<FeaturesRoot>(
							new QName(
									"http://karaf.apache.org/xmlns/features/v1.0.0",
									"features"), FeaturesRoot.class, features);

					try {
						marshaller.marshal(p, writer);
						r = r.concat(writer.getBuffer().toString()); // karaf features / repositories
					} catch (Exception e) {
						e.printStackTrace();
					}					

					r = r.concat("</karaf>");
				}
				if(container == Container.ANDROID){
					r = r.concat("<android>");
					r = r.concat(androidPart.getXML());
					r = r.concat("</android>");
				}
			}

			return r;
		}

		public class Android{

			private String name, description;
			private URI location;

			public Android(String name, String description, URI location){
				this.name = name;
				this.description = description;
				this.location = location;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public URI getLocation() {
				return location;
			}

			public void setLocation(URI location) {
				this.location = location;
			}			

			public String getXML(){
				return "<name>"+name+"</name><description>"+description+"</description><location>"+location.toASCIIString()+"</location>";
			}
		}
	}

	public enum Container{

		TOMCAT, EQUINOX, FELIX, OSGI_ANDROID, KARAF, ANDROID
	}

	/*
	 * <xs:element name="deploymentUnit">
		<xs:complexType>
			<xs:choice>
				<xs:element name="osUnit" type="uapp:osType">
				</xs:element>
				<xs:element name="platformUnit" type="uapp:platformType">
				</xs:element>
				<xs:element name="containerUnit">
					<xs:complexType>
						<xs:choice>
							<xs:element name="karaf">
								<xs:complexType>
									<xs:sequence>
										<xs:element name="embedding" type="uapp:embeddingType" />
										<xs:element ref="krf:features" />
									</xs:sequence>
								</xs:complexType>
							</xs:element>
							<xs:element name="android">
								<xs:complexType>
									<xs:sequence>
										<xs:element name="name" type="xs:string" />
										<xs:element minOccurs="0" name="description" type="xs:string" />
										<xs:element maxOccurs="unbounded" name="location"
											type="xs:anyURI" />
									</xs:sequence>
								</xs:complexType>
							</xs:element>
							<xs:element name="tomcat" />
							<xs:element name="equinox" />
							<xs:element name="felix" />
							<xs:element name="osgi-android" />
						</xs:choice>
					</xs:complexType>
				</xs:element>
			</xs:choice>
			<xs:attribute name="id" type="xs:ID" />
		</xs:complexType>
	</xs:element>
	 */
}