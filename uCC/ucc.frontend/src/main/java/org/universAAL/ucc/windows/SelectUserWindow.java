package org.universAAL.ucc.windows;

import java.util.List;
import java.util.Map;

import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.profile.User;
import org.universAAL.ucc.model.AALService;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SelectUserWindow extends Window implements Button.ClickListener {
	private ListSelect list;
	private Button ok;
	private Button cancel;
	private AALService aal;
	
	public SelectUserWindow(List<String>users, AALService aal) {
		super("Select User");
		this.aal = aal;
		VerticalLayout vl = new VerticalLayout();
		vl.setSizeFull();
		vl.setMargin(true);
		vl.setSpacing(true);
		setContent(vl);
		Label l = new Label("For which user do you want to install the AAL service?");
		list = new ListSelect("List of users in AAL space");
		list.setImmediate(true);
		list.setMultiSelect(false);
		list.setWidth("200px");
		list.setNullSelectionAllowed(false);
//		list.setNewItemsAllowed(false);
		for(String u : users) {
			list.addItem(u);
		}
		vl.addComponent(l);
		vl.addComponent(list);
		vl.setComponentAlignment(list, Alignment.TOP_CENTER);
		
		ok = new Button("OK");
		ok.addListener(this);
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.setMargin(true);
		hl.addComponent(ok);
		cancel = new Button("Cancel");
		cancel.addListener(this);
		hl.addComponent(cancel);
		vl.addComponent(hl);
		vl.setComponentAlignment(hl, Alignment.BOTTOM_CENTER);
		
		setWidth("450px");
		setHeight("400px");
		center();
		
	}

	public void buttonClick(ClickEvent event) {
		if(event.getButton() == ok) {
//			addEntry((new StringBuilder()).append(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX).append(list.getValue().toString()), 
//					aal.getName(), aal.getProvider(), 
//					aal.getOntologyUri(), aal.getIconPath());
			close();
			
		}
		if(event.getButton() == cancel) {
			close();
		}
		
	}
	
	//Adds a MenuEntry for new installed AAL service to Endusers view
//	private void addEntry(String userID, String entryName, String vendor, String serviceClass, String iconURL)
//    {
//        MenuEntry me = new MenuEntry((new StringBuilder()).append(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX).append(/*"nutritonalEntry"*/ entryName).toString());
//        me.setVendor(new Resource(vendor));
//        me.setServiceClass(new Resource(serviceClass));
//        Resource pathElem = new Resource(iconURL);
//        pathElem.setResourceLabel(entryName);
//        me.setPath(new Resource[] {
//            pathElem
//        });
//        ServiceRequest sr = new ServiceRequest(new ProfilingService(), null);
//        sr.addValueFilter(new String[] {
//            "http://ontology.universAAL.org/Profile.owl#controls"
//        }, new User(userID));
//        sr.addAddEffect(new String[] {
//            "http://ontology.universAAL.org/Profile.owl#controls", "http://ontology.universAAL.org/Profile.owl#hasProfile", "http://ontology.universAAL.org/Profile.owl#hasSubProfile", "http://ontology.universaal.org/UIMainMenuProfile.owl#hasEntry"
//        }, me);
//        ServiceResponse res = Activator.getSc().call(sr);
//        if(res.getCallStatus() == CallStatus.succeeded)
//            LogUtils.logDebug(Activator.getmContext(), Activator.class, "addEntry", new Object[] {
//                "new user ", userID, " added."
//            }, null);
//        else
//            LogUtils.logDebug(Activator.getmContext(), Activator.class, "addEntry", new Object[] {
//                "callstatus is not succeeded"
//            }, null);
//    }


}
