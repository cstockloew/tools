package org.universAAL.ucc.deploymanager.client;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.universAAL.ucc.deploymanagerservice.DeployManagerService;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private ServiceTracker tracker;

	private ServiceDialog serviceDialog;
	
	private DeployManagerService service;

	static BundleContext getContext() {
		return context;
	}

	public void start(final BundleContext bundleContext) throws Exception {
        tracker = new ServiceTracker(bundleContext, DeployManagerService.class.getName(), null) {
            @Override
            public Object addingService(ServiceReference reference) {
                Object result = super.addingService(reference);

                useService(bundleContext, reference);

                return result;
            }
        };
        tracker.open();
	}

    protected void useService(final BundleContext bc, ServiceReference reference) {
        Object svc = bc.getService(reference);
        if (!(svc instanceof DeployManagerService)) {
            return;
        }
        service = (DeployManagerService) svc;

        if (serviceDialog != null) {
        	return;
        }

        serviceDialog = new ServiceDialog(new ServiceDialog.ServiceDialogListener() {   		          
    		
    		@Override
			public void install(String sessionKey, String usrvfile) {
				System.out.println("install with sessionKey: " + sessionKey + " usrvfile: " + usrvfile);
				try {
					service.install(sessionKey, usrvfile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                
			}          
    		
    		@Override
			public void update(String sessionKey, String usrvfile) {
				System.out.println("update with sessionKey: " + sessionKey + " usrvfile: " + usrvfile);
				try {
					service.update(sessionKey, usrvfile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                
			}                
		});
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                serviceDialog.setVisible(true);
            }
        });
    }

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		tracker.close();
		service = null;
		if (serviceDialog != null) {
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                serviceDialog.setVisible(false);
	                serviceDialog.dispose();
	                serviceDialog = null;
	            }
	        });
		}
	}

}
