/*TAG:PACKAGE*/

import org.osgi.framework.BundleContext;
import org.persona.middleware.context.ContextPublisher;
import org.persona.ontology.context.ContextProvider;

public class CPublisher extends ContextPublisher{

	protected CPublisher(BundleContext context, ContextProvider providerInfo) {
		super(context, providerInfo);
		// TODO Auto-generated constructor stub
	}
	
	protected CPublisher(BundleContext context) {
		super(context, getProviderInfo());
		// TODO Auto-generated constructor stub
	}

	private static ContextProvider getProviderInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

}
