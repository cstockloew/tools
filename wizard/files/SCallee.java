/*TAG:PACKAGE*/

import org.osgi.framework.BundleContext;
import org.persona.middleware.service.ServiceCall;
import org.persona.middleware.service.ServiceCallee;
import org.persona.middleware.service.ServiceResponse;
import org.persona.middleware.service.profile.ServiceProfile;

public class SCallee extends ServiceCallee{

	protected SCallee(BundleContext context, ServiceProfile[] realizedServices) {
		super(context, realizedServices);
		// TODO Auto-generated constructor stub
	}
	
	protected SCallee(BundleContext context) {
		super(context, ProvidedService.profiles);
		// TODO Auto-generated constructor stub
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	public ServiceResponse handleCall(ServiceCall call) {
		// TODO Auto-generated method stub
		return null;
	}

}
