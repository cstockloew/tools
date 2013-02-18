package org.universaal.tools.packaging.tool.parts;

import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;

import org.apache.karaf.xmlns.features.v1_0.FeaturesRoot;

public class DeploymentUnit {

	private String id;
	private OS osType;
	private Platform platformType;	

	public DeploymentUnit(String id, OS osType, Platform platformType){		

		SecureRandom random = new SecureRandom();

		this.id = id+"_"+new BigInteger(130, random).toString(32);;
		this.osType = osType;
		this.platformType = platformType;
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

	public class ContainerUnit {

		private Container container;

		private Embedding embedding;
		private FeaturesRoot features;

		private String description;
		private URI location;

		public ContainerUnit(Embedding embedding, FeaturesRoot features){
			this.container = Container.KARAF;
			this.embedding = embedding;
			this.features = features;

			this.description = null;
			this.location = null;
		}

		public ContainerUnit(String description, URI location){
			this.container = Container.ANDROID;
			this.description = description;
			this.location = location;

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

		public String getDescription() {
			return description;
		}

		public URI getLocation() {
			return location;
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
					<xs:annotation>
						<xs:documentation>Not used for the moment. Intended to describe
							application that are installed on OS level rpm, exe, msi...
						</xs:documentation>
					</xs:annotation>
				</xs:element>
				<xs:element name="platformUnit" type="uapp:platformType">
					<xs:annotation>
						<xs:documentation>Not used for the moment. Intended to describe
							units that are OS independent like POJO java apps
						</xs:documentation>
					</xs:annotation>
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