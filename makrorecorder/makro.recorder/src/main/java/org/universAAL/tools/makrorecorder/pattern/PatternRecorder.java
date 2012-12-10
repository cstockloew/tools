package org.universAAL.tools.makrorecorder.pattern;

import org.osgi.framework.ServiceRegistration;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.tools.makrorecorder.Activator;

public class PatternRecorder extends ContextSubscriber {

	private Pattern pattern = null;
	
	private ServiceRegistration sbr = null;
	
	public PatternRecorder(Pattern pattern) {
		super(Activator.getModuleContext(),new ContextEventPattern[]{new ContextEventPattern()});
		this.pattern = pattern;
		
	}

	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void handleContextEvent(ContextEvent ce) {
		pattern.addInput(ce);
	}
	
	public void start() {
		sbr = Activator.getBundleContext().registerService(new String[] { ServiceBusRecorder.class.getName() }, new ServiceBusRecorder(pattern), null);
	}
	
	public void stop() {
		sbr.unregister();
		super.close();
		
	}
	
}
