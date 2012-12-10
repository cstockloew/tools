package org.universAAL.tools.makrorecorder.pattern;

import org.universAAL.middleware.container.LogListener;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.UnmodifiableResource;
import org.universAAL.middleware.sodapop.msg.MessageContentSerializer;
import org.universAAL.tools.makrorecorder.Activator;

public class ServiceBusRecorder  implements LogListener {

	public void start() {	}
	public void stop() {	}
	private Pattern pattern = null;
	private MessageContentSerializer serializer = (MessageContentSerializer) Activator.getBundleContext().getService(Activator.getBundleContext().getServiceReference(MessageContentSerializer.class.getName()));
	
	public ServiceBusRecorder(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public void log(int logLevel, String module, String pkg, String cls,
		    String method, Object[] msgPart, Throwable t) {
		if(pattern != null) {
			System.out.println("\n\nFROM: "+module);
			if (module.equals("mw.bus.service.osgi")) {
				for(Object o : msgPart){
					if(o.getClass().equals(UnmodifiableResource.class)){
						Resource r = (Resource)serializer.deserialize(serializer.serialize((UnmodifiableResource)o));
						pattern.addOutput(r);					
					}
				}
			}
			//if (module.equals("mw.bus.context.osgi")) {
				for(Object o : msgPart){
					/*if(o.getClass().equals(UnmodifiableResource.class)){
						Resource r = (Resource)serializer.deserialize(serializer.serialize((UnmodifiableResource)o));
						pattern.addOutput(r);					
					}*/
					System.out.println(o.getClass().getName());
					System.out.println(o.toString().replace("\n", " "));
				}
			//}
		}
	}
	
	public static String getMessage(Object[] msgPart) {
		StringBuffer sb = new StringBuffer(256);
		if (msgPart != null)
		    for (int i = 0; i < msgPart.length - 1; i++)
			sb.append(msgPart[i]);
		return sb.toString();
	    }

}
