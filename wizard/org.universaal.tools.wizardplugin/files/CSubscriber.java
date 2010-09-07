/*TAG:PACKAGE*/

import org.osgi.framework.BundleContext;
import org.persona.middleware.context.ContextEvent;
import org.persona.middleware.context.ContextEventPattern;
import org.persona.middleware.context.ContextSubscriber;

public class CSubscriber extends ContextSubscriber{

	protected CSubscriber(BundleContext context,
			ContextEventPattern[] initialSubscriptions) {
		super(context, initialSubscriptions);
		// TODO Auto-generated constructor stub
	}
	
	protected CSubscriber(BundleContext context) {
		super(context, getPermanentSubscriptions());
		// TODO Auto-generated constructor stub
	}

	private static ContextEventPattern[] getPermanentSubscriptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public void handleContextEvent(ContextEvent event) {
		// TODO Auto-generated method stub
		
	}

}
