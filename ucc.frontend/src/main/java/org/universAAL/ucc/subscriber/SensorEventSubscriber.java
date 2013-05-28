package org.universAAL.ucc.subscriber;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.ModuleConfigHome;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.phThing.Sensor;

import org.universAAL.ucc.database.aalspace.DataAccess;
import org.universAAL.ucc.model.jaxb.CalendarValue;
import org.universAAL.ucc.model.jaxb.OntologyInstance;
import org.universAAL.ucc.model.jaxb.SimpleObject;
import org.universAAL.ucc.model.jaxb.Subprofile;
import org.universAAL.ucc.service.manager.Activator;

public class SensorEventSubscriber extends ContextSubscriber {
	private static String room1;
	private static String room2;
	private static String room3;
	private static String flat1DB;
	private static String flat2DB;
	private static String flat3DB;
	private static SensorEventSubscriber sub;
	private ArrayList<SensorActivityTimeChangedListener>listener;
	private DataAccess db;
	private static BundleContext bContext;
	private HashMap<String, ArrayList<Subprofile>>ontInstances;
	private String device;
	private ModuleConfigHome mc;
	
	private static ContextEventPattern[] getSubscriptions() {
		ContextEventPattern ev = new ContextEventPattern();
		ev.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_TYPE, Sensor.MY_URI));
		return new ContextEventPattern[] {ev};
	}
	
	private SensorEventSubscriber(ModuleContext context) {
		super(context, getSubscriptions());
		mc = new ModuleConfigHome("uccDB", "");
		device = mc.getAbsolutePath();
		room1 = device+"/Rooms.xml";
//		room2 = device+"/jcc_datastore/flat2/Rooms.xml";
//		room3 = device+"/jcc_datastore/flat3/Rooms.xml";
		flat1DB = device+"/Hardware.xml";
//		flat2DB = device+"/jcc_datastore/flat2/Hardware.xml";
//		flat3DB = device+"/jcc_datastore/flat3/Hardware.xml";
		listener = new ArrayList<SensorActivityTimeChangedListener>();
		ontInstances = new HashMap<String, ArrayList<Subprofile>>();
		ServiceReference ref = bContext.getServiceReference(DataAccess.class.getName());
		db = (DataAccess)bContext.getService(ref);
		bContext.ungetService(ref);
		
	}
	
	public static SensorEventSubscriber getInstance(ModuleContext ctxt, BundleContext context) {
		if(sub == null) {
			SensorEventSubscriber.bContext = context;
			sub = new SensorEventSubscriber(ctxt);
		} return sub;
	}

	@Override
	public void communicationChannelBroken() {	
		
	}

	@Override
	public void handleContextEvent(ContextEvent event) {
		String uri = event.getRDFSubject().toString();
		String adress = uri.substring(uri.lastIndexOf(":")+1).trim();
		updateDB(adress, room1, flat1DB, new Date(event.getTimestamp()));
//		updateDB(adress, room2, flat2DB, new Date(event.getTimestamp()));
//		updateDB(adress, room3, flat3DB, new Date(event.getTimestamp()));
		
	}
	
	public void addListener(SensorActivityTimeChangedListener l) {
		listener.add(l);
	}
	
	public void removeListener(SensorActivityTimeChangedListener l) {
		listener.remove(l);
	}
	
	private void updateDB(String adress, String room, String device, Date time) {
		ArrayList<OntologyInstance> rooms = db.getFormFields(room);
		for(OntologyInstance oi : rooms) {
			if(oi.getId().equals(adress)) {
				for(Subprofile sp : oi.getSubprofiles()) {
					for(SimpleObject so : sp.getSimpleObjects()) {
						if(so instanceof CalendarValue) {
							CalendarValue cv = (CalendarValue)so;
							if(cv.getName().equals("lastActivityTime")) {
								DateFormat df = new SimpleDateFormat();
								String date = df.format(time);
								cv.setCalendar(date);
							}
						}
					}
				} 
				ontInstances.put(oi.getId(), oi.getSubprofiles());
				db.updateUserData(room, oi.getId(), ontInstances);
			}
		}
		ArrayList<OntologyInstance> hw = db.getFormFields(device);
		for(OntologyInstance oi : hw) {
			if(oi.getId().equals(adress)) {
				for(Subprofile sp : oi.getSubprofiles()) {
					for(SimpleObject so : sp.getSimpleObjects()) {
						if(so instanceof CalendarValue) {
							CalendarValue cv = (CalendarValue)so;
							if(cv.getName().equals("lastActivityTime")) {
								DateFormat df = new SimpleDateFormat();
								String date = df.format(time);
								cv.setCalendar(date);
							}
						}
					}
				} 
				ontInstances.put(oi.getId(), oi.getSubprofiles());
				db.updateUserData(device, oi.getId(), ontInstances);
			}
		} 
//		for(SensorActivityTimeChangedListener l : listener) {
//			System.err.println(listener.size());
//			l.sensorActivityTimeChanged(adress, time);
//			
//		} 
	}
	

}
