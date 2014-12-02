package org.universAAL.tools.logmonitor.test;

import org.universAAL.middleware.bus.junit.BusTestCase;
import org.universAAL.middleware.bus.permission.AccessControl;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.owl.OntologyManagement;
import org.universAAL.middleware.service.DefaultServiceCaller;
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
//	while (true) {
//	    try {
//		Thread.sleep(100);
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

	Activator a = new Activator();
	a.start();
	((JUnitContainer)mc.getContainer()).registerLogListeners(a.lm);
    }

    public void testAddScript() {
	LogUtils.logDebug(mc, this.getClass(), "method", "msg");
	caller = new DefaultServiceCaller(mc);
	caller.close();
    }
}
