package org.universAAL.tools.logmonitor.test;

import java.util.LinkedList;
import java.util.List;

import org.universAAL.middleware.bus.junit.BusTestCase;
import org.universAAL.middleware.bus.member.BusMember;
import org.universAAL.middleware.bus.permission.AccessControl;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.tracker.IBusMemberRegistry;
import org.universAAL.ontology.lighting.LightingOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.phThing.PhThingOntology;
import org.universAAL.ontology.shape.ShapeOntology;
import org.universAAL.tools.logmonitor.Activator;
import org.universAAL.container.JUnit.JUnitContainer;
import org.universAAL.container.JUnit.JUnitModuleContext;
import org.universAAL.container.JUnit.JUnitModuleContext.LogLevel;

public class Test extends BusTestCase {
    static boolean isSetUp = false;
    static DefaultServiceCaller caller;

    public void tearDown() {
	// don't do anything here so we don't have to set up again
//	List<BusMember> l = new LinkedList<BusMember>();
//	int i = 0;
//	while (true) {
//	    try {
//		Thread.sleep(20);
//		l.add(new DefaultServiceCaller(mc));
//		i++;
//		if (i % 3 == 0) {
//		    // remove one
//		    //BusMember m = l.remove((int) (Math.random() * l.size()));
//		    BusMember m = l.remove(l.size()-1);
//		    System.out.println(" -- removing member: " + m.getURI());
//		    m.close();
//		}
//	    } catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	    }
//	}
    }

    public void setUp() throws Exception {
	if (isSetUp)
	    return;

	super.setUp();
	isSetUp = true;

	OntologyManagement.getInstance().register(mc, new LocationOntology());
	OntologyManagement.getInstance().register(mc, new ShapeOntology());
	OntologyManagement.getInstance().register(mc, new PhThingOntology());
	OntologyManagement.getInstance().register(mc, new LightingOntology());

	mc.setAttribute(AccessControl.PROP_MODE, "none");
	mc.setAttribute(AccessControl.PROP_MODE_UPDATE, "always");

	Activator.mc = mc;
	((JUnitModuleContext) mc).setLogLevel(LogLevel.DEBUG);

	// init bus tracker
	org.universAAL.middleware.tracker.impl.Activator.fetchParams = new Object[] { IBusMemberRegistry.class
		.getName() };
	ModuleContext mcTracker = new JUnitModuleContext();
	org.universAAL.middleware.tracker.impl.Activator actTracker = new org.universAAL.middleware.tracker.impl.Activator();
	actTracker.start(mcTracker);

	// start log monitor
	Activator a = new Activator();
	a.start();
	((JUnitContainer) mc.getContainer()).registerLogListeners(a.lm);
    }

    public void testAddScript() {
	LogUtils.logDebug(mc, this.getClass(), "method", "msg");
	caller = new DefaultServiceCaller(mc);
	//ContextPublisher cp = new DefaultContextPublisher(mc, null);
	
	// try {
	// Thread.sleep(20000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	
	caller.close();
	//cp.close();
    }
}
