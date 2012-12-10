package org.universAAL.tools.makrorecorder.resourcebuilder;

import java.util.Vector;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.tools.makrorecorder.Activator;
import org.universAAL.tools.makrorecorder.gui.editContextEventDialog;

public class ContextEventBuilder {
        
        private static ContextEventBuilder instance = null;
        
        public static ContextEventBuilder getInstance() {
            if(instance == null)
                instance = new ContextEventBuilder();
            instance.refresh();
            return instance;
        }
    
	private Vector<Resource> subjects = null;
	private Vector<String> predicates = null;
	private Vector<Object> objects = null;
	
	public static ContextEvent createContextEvent() {
            editContextEventDialog window = new editContextEventDialog(new javax.swing.JFrame(), true);
            window.setVisible(true);
            return window.getContextEvent();
        }
	
	public ContextEventBuilder() {
		new DefaultServiceCaller(Activator.getModuleContext());
		subjects = new Vector<Resource>();
		predicates = new Vector<String>();
		objects = new Vector<Object>();
	}
	
	
	/*private Vector<ContextEvent> getAllContextEvents() {
		Vector<ContextEvent> ret = new Vector<ContextEvent>();
		
		
		org.universAAL.context.che.ontology.ContextEvent matchEvent = new org.universAAL.context.che.ontology.ContextEvent(
		"urn:org.universAAL.middleware.context.rdf:ContextEvent#_:0000000000000000:00");

		ServiceRequest request = new ServiceRequest(new ContextHistoryService(null), null);
		Service service = request.getRequestedService();
		
		MergedRestriction restriction = MergedRestriction.getFixedValueRestriction(ContextHistoryService.PROP_MANAGES,
				matchEvent);
		service.addInstanceLevelRestriction(restriction, new String[] { ContextHistoryService.PROP_MANAGES });
		
		/*MergedRestriction restriction2 = MergedRestriction.getFixedValueRestriction(
				ContextHistoryService.PROP_TIMESTAMP_FROM, new Long(startTime));
		service.addInstanceLevelRestriction(restriction2, new String[] { ContextHistoryService.PROP_TIMESTAMP_FROM });*/
		
		/*request.addSimpleOutputBinding(new ProcessOutput(OUTPUT_LIST_OF_EVENTS), new PropertyPath(null, false,
				new String[] { ContextHistoryService.PROP_MANAGES }).getThePath());*/
		
		/*ServiceResponse response = callService(request);
		if (response.getCallStatus() == CallStatus.succeeded) {
			Object vaObject = getReturnValue(response.getOutputs(), OUTPUT_LIST_OF_EVENTS);
			if (vaObject == null) {
				return ret; // we return a emtpy List
			} else if (vaObject instanceof Resource) {
				return ret; // we return a emtpy List
			} else if (vaObject instanceof List) {
				ret.addAll((List) vaObject);
				
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
			}
		}
			
		return ret;
	}*/
        
        private void addSubject(Resource subject) {
            if(!subjects.contains(subject)) {
                subjects.add(subject);
            }
        }
        
        private void addPredicates(String predicate) {
            if(!predicates.contains(predicate)) {
                predicates.add(predicate);
            }
        }
	
	public void refresh() {
		/*for(ContextEvent ce : getAllContextEvents()) {
                    addSubject(ce.getRDFSubject());
                    addPredicates(ce.getRDFPredicate());
		}*/
	}
        
        public static Vector<Resource> getSubjects() {
		return getInstance().subjects;
	}
	
	public static Vector<String> getPredicates() {
		return getInstance().predicates;
	}
	
	public static Vector<Object> getObjects() {
		return getInstance().objects;
	}
}
