/*TAG:PACKAGE*/

/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_6#Publishing_context_events */
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;

public class /*TAG:CLASSNAME*/ extends ContextPublisher{

	protected /*TAG:CLASSNAME*/(ModuleContext context, ContextProvider providerInfo) {
		super(context, providerInfo);
		// TODO Auto-generated constructor stub
	}
	
	protected /*TAG:CLASSNAME*/(ModuleContext context) {
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
