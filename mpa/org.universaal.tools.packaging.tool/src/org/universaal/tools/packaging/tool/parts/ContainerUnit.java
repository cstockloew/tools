package org.universaal.tools.packaging.tool.parts;

import java.io.StringWriter;
import java.net.URI;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.karaf.xmlns.features.v1_0.FeaturesRoot;
import org.apache.karaf.xmlns.features.v1_0.ObjectFactory;

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

	public enum Container{

		TOMCAT, EQUINOX, FELIX, OSGI_ANDROID, KARAF, ANDROID
	}
}