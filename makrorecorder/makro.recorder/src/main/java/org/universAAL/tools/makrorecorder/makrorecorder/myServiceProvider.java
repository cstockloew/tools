/*
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.tools.makrorecorder.makrorecorder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.universAAL.context.che.ontology.ContextHistoryService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;
import org.universAAL.tools.makrorecorder.Activator;

/**
 * 
 * @author Alexander Marinc, Mark Prediger
 *
 */
public class myServiceProvider {
	
	public static String NAMESPACE_MY_SERVICE_PROVIDER = "http://ontology.igd.fhg.de/myServiceProvider.owl#";
	String OUTPUT_LIST_OF_EVENTS = NAMESPACE_MY_SERVICE_PROVIDER + "listOfEvents";
	MessageContentSerializer contentSerializer;
	ServiceCaller serviceCaller;
	
	private Thread serviceCallingThread = null;
	
	public myServiceProvider(ModuleContext context) {
		serviceCaller = new DefaultServiceCaller(context);
	}
	
	private ServiceResponse callService(ServiceRequest request) {
		ServiceResponse response = serviceCaller.call(request);
		return response;
	}
	
	/**
	 * sends a list of ServiceRequest.
	 **/
	public void startCallingServices(final ArrayList<Object[]> requests, final boolean keepTimeDeps,
			final ServiceCallHandler onFinish) {
		stopCallingServices();
		
		if (requests.isEmpty())
			return;
		
		serviceCallingThread = new Thread() {
			@Override
			public void run() {
				boolean result = false;
				long starttime = System.currentTimeMillis();
				long endtime = (Long) ((Object[]) requests.get(requests.size() - 1))[0];
				long timenow = System.currentTimeMillis();
				
				for (Iterator<Object[]> iterator = requests.iterator(); iterator.hasNext();) {
					Object[] entry = iterator.next();
					if (entry[1] instanceof ServiceRequest) {
						
						ServiceRequest request = (ServiceRequest) entry[1];
						if (keepTimeDeps) { // if true we will wait between each request
							long time = (Long) entry[0];
							
							try {
								if (starttime + time - timenow > 0)
									Thread.sleep(starttime + time - timenow);
							} catch (InterruptedException e) {
								break;
							}
							
							timenow = System.currentTimeMillis();
							if (onFinish != null)
								onFinish.progressChanged((float) (timenow - starttime) / endtime, timenow - starttime);
						}
						
						if (callService(request).getCallStatus() == CallStatus.succeeded)
							result = true;
					}
				}
				
				if (onFinish != null) {
					if (interrupted())
						onFinish.callingCanceled();
					else
						onFinish.callingFinished(result);
				}
			}
		};
		serviceCallingThread.start();
	}
	
	public void startCallingServices(String name, boolean keepTimeDeps, ServiceCallHandler onFinish) {
		Pattern pattern = Activator.resourceOrginazer.getPatternByName(name);
		startCallingServices(pattern.getRequests(), keepTimeDeps, onFinish);
	}
	
	public void stopCallingServices() {
		if (serviceCallingThread != null) {
			serviceCallingThread.interrupt();
			try {
				serviceCallingThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace(); // should not happen
			}
		}
	}
	
	/**
	 * creates the Service Request which will be answered with a list of ContextEvents which occured from a "startTime"
	 * to the Time of this Request
	 **/
	private ServiceRequest buildCheRequest(Long startTime) {
		org.universAAL.context.che.ontology.ContextEvent matchEvent = new org.universAAL.context.che.ontology.ContextEvent(
				"urn:org.universAAL.middleware.context.rdf:ContextEvent#_:0000000000000000:00");
		
		ServiceRequest request = new ServiceRequest(new ContextHistoryService(null), null);
		Service service = request.getRequestedService();
		
		MergedRestriction restriction = MergedRestriction.getFixedValueRestriction(ContextHistoryService.PROP_MANAGES,
				matchEvent);
		service.addInstanceLevelRestriction(restriction, new String[] { ContextHistoryService.PROP_MANAGES });
		
		MergedRestriction restriction2 = MergedRestriction.getFixedValueRestriction(
				ContextHistoryService.PROP_TIMESTAMP_FROM, new Long(startTime));
		service.addInstanceLevelRestriction(restriction2, new String[] { ContextHistoryService.PROP_TIMESTAMP_FROM });
		
		request.addSimpleOutputBinding(new ProcessOutput(OUTPUT_LIST_OF_EVENTS), new PropertyPath(null, false,
				new String[] { ContextHistoryService.PROP_MANAGES }).getThePath());
		
		return request;
	}
	
	/**
	 * handels the Return Value of the Che Service Request
	 **/
	private Object getReturnValue(List outputs, String expectedOutput) {
		Object returnValue = null;
		if (outputs == null)
			Activator.logger.info("History Client: No events found!");
		else
			for (Iterator i = outputs.iterator(); i.hasNext();) {
				ProcessOutput output = (ProcessOutput) i.next();
				if (output.getURI().equals(expectedOutput))
					if (returnValue == null)
						returnValue = output.getParameterValue();
					else
						Activator.logger.info("History Client: redundant return value!");
				else
					Activator.logger.info("History Client - output ignored: " + output.getURI());
			}
		
		return returnValue;
	}
	
	/**
	 * calls the Che and returns a list of ContextEvents which occured between "startTime" and "stopTime"
	 **/
	public Vector<ContextEvent> callChe(Long startTime, Long stopTime) {
		ServiceResponse response = callService(buildCheRequest(startTime));
		Vector<ContextEvent> resources = new Vector<ContextEvent>();
		if (response.getCallStatus() == CallStatus.succeeded) {
			Object vaObject = getReturnValue(response.getOutputs(), OUTPUT_LIST_OF_EVENTS);
			if (vaObject == null) {
				Activator.logger.info("Che Call was null");
				return resources; // we return a emtpy List
			} else if (vaObject instanceof Resource) {
				Activator.logger.info("Che Call was empty");
				return resources; // we return a emtpy List
			} else if (vaObject instanceof List) {
				resources.addAll((List<ContextEvent>) vaObject);
				
				for (Iterator<ContextEvent> iterator = resources.iterator(); iterator.hasNext();) {
					ContextEvent contextEvent = iterator.next();
					System.out.println("----------------------------");
					System.out.println(contextEvent);
					for (Enumeration<String> e = contextEvent.getPropertyURIs(); e.hasMoreElements();) {
						String uri = e.nextElement();
						System.out.println(uri + " : " + contextEvent.getProperty(uri));
					}
					// since the From - To Request doesn't work we have to delete the ContextEvent which are out of our
					// scope by "hand"
					if (contextEvent.getTimestamp() > stopTime) {
						Activator.logger.info("Removing: " + contextEvent.toString());
						iterator.remove();
					}
				}
				
				Activator.logger.info("Che delivered:" + resources.size());
				return resources;
			} else {
				Activator.logger.info("Che Call was neither null nor a list or resource");
				return resources; // we return a emtpy List
			}
		}
		return null;
	}
	
	/**
	 * Reads the ServiceRequest from the file "text.txt" which is created after we modified the Middleware.service
	 * implemantion for futher information regard the doc or the readme.
	 * 
	 * Each entry of the List consists of an array with two entrys, the first is the time intervall between the last
	 * entry (or "startTime" if this is the first entry) and this one, the second entry is the Service Request
	 **/
	public ArrayList<Object[]> readServiceRequests(Long startTime, Long stopTime) throws FileNotFoundException,
			IOException {
		BufferedReader reader = new BufferedReader(new FileReader("test.txt"));
		String line;
		String text = new String("");
		boolean gettext = false;
		long time = 0;
		// long lasttime = startTime;
		ArrayList<Object[]> objects = new ArrayList<Object[]>();
		while (null != (line = reader.readLine())) {
			if (line.contains("New Message:")) {
				if (gettext && text != "" && text.contains("ServiceRequest")) {
					Resource request = (Resource) Activator.contentSerializer.deserialize(text);
					if (request instanceof ServiceRequest) {
						// time will always be set before we get into this part
						Object[] entry = { new Long(time - startTime), (ServiceRequest) request };
						objects.add(entry);
					}
					
					// lasttime = time;
					gettext = false;
				}
				
				time = Long.parseLong(line.split("\\:")[1]);
				if (time > startTime && time < stopTime) {
					gettext = true;
					text = "";
				}
			} else if (gettext) {
				text += line + "\n";
			}
		}
		return objects;
	}
	
}
