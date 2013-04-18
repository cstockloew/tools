package org.universAAL.ucc.controller.aalspace;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Window.Notification;

import org.universAAL.ucc.database.aalspace.DataAccess;
import org.universAAL.ucc.database.model.jaxb.BooleanValue;
import org.universAAL.ucc.database.model.jaxb.CalendarValue;
import org.universAAL.ucc.database.model.jaxb.CollectionValues;
import org.universAAL.ucc.database.model.jaxb.CollectionValues.Values;
import org.universAAL.ucc.database.model.jaxb.DoubleValue;
import org.universAAL.ucc.database.model.jaxb.EnumObject;
import org.universAAL.ucc.database.model.jaxb.IntegerValue;
import org.universAAL.ucc.database.model.jaxb.OntologyInstance;
import org.universAAL.ucc.database.model.jaxb.SimpleObject;
import org.universAAL.ucc.database.model.jaxb.StringValue;
import org.universAAL.ucc.database.model.jaxb.Subprofile;
import org.universAAL.ucc.database.model.jaxb.Subprofile.Collections;
import org.universAAL.ucc.database.model.jaxb.Subprofile.EnumObjects;
import org.universAAL.ucc.database.model.jaxb.Subprofile.SimpleObjects;
import org.universAAL.ucc.windows.AddNewHardwareWindow;
import org.universAAL.ucc.windows.HardwareWindow;
import org.universAAL.ucc.windows.UccUI;
import org.universAAL.ucc.windows.RoomsWindow;
import org.universAAL.ucc.windows.TabForm;

public class RoomsWindowController implements Property.ValueChangeListener,
		Button.ClickListener {
	private RoomsWindow win;
	private UccUI app;
	private BundleContext context;
	private DataAccess dataAccess;
	private TabSheet tabSheet;
	private HashMap<String, Subprofile> subprofiles;
	private HashMap<String, Subprofile>roomprofiles;
	private HashMap<String, ArrayList<TabForm>> userForms;
	private HashMap<String, ArrayList<Subprofile>> ontInstances;
	private HashMap<String, ArrayList<Subprofile>> roomInstances;
	private String selectedItem;
//	private String flatId;
//	private static String hw1;
//	private static String hw2;
//	private static String hw3;
//	private static String flat1DB;
//	private static String flat2DB;
//	private static String flat3DB;
	private String actualFlat;
	private String actualHW;
	private String device;

	public RoomsWindowController(RoomsWindow window,
			UccUI app) throws JAXBException,
			IOException, ParseException {
		device = System.getenv("systemdrive");
//		hw1 = device+"/jcc_datastore/flat1/Hardware.xml";
//	    hw2 = device+"/jcc_datastore/flat2/Hardware.xml";
//		hw3 = device+"/jcc_datastore/flat3/Hardware.xml";
//		flat1DB = device+"/jcc_datastore/flat1/Rooms.xml";
//		flat2DB = device+"/jcc_datastore/flat2/Rooms.xml";
//		flat3DB = device+"/jcc_datastore/flat3/Rooms.xml";
		this.app = app;
		this.win = window;
//		this.flatId = window.getFlatId();
		// if(flatId.equals("Basement")) {
		// actualFlat = basement;
		// actualHW = hwBasement;
		// } else
//		if (flatId.equals("Flat1")) {
//			actualFlat = flat1DB;
//			actualHW = hw1;
//		} else if (flatId.equals("Flat2")) {
//			actualFlat = flat2DB;
//			actualHW = hw2;
//		} else if (flatId.equals("Flat3")) {
//			actualFlat = flat3DB;
//			actualHW = hw3;
//		}
		
		actualFlat = device + "/uccDB/Rooms.xml";
		actualHW = device + "/uccDB/Hardware.xml";
		context = FrameworkUtil.getBundle(getClass()).getBundleContext();
		ServiceReference ref = context.getServiceReference(DataAccess.class
				.getName());
		dataAccess = (DataAccess) context.getService(ref);
		context.ungetService(ref);
		ontInstances = new HashMap<String, ArrayList<Subprofile>>();
		roomInstances = new HashMap<String, ArrayList<Subprofile>>();
		subprofiles = new HashMap<String, Subprofile>();
		roomprofiles = new HashMap<String, Subprofile>();
		userForms = new HashMap<String, ArrayList<TabForm>>();
		tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		tabSheet.setImmediate(true);
		win.getUserTree().addListener(this);
		loadData();
		win.addFirstComponent(win.getUserTree());
		win.addSecondComponent(tabSheet);

	}

	private void loadData() throws JAXBException, IOException, ParseException {
		// Creating Tabs with Forms
		ArrayList<OntologyInstance> tabs = dataAccess.getFormFields(actualFlat);
		ArrayList<OntologyInstance>roomSubs = dataAccess.getFormFields(actualHW);
		TabForm f = null;
		for(OntologyInstance r : roomSubs) {
			if(roomInstances.get(r.getId()) == null) {
				roomInstances.put(r.getId(), new ArrayList<Subprofile>());
			} else {
				roomInstances.get(r.getId()).clear();
			}

			for(Subprofile s : r.getSubprofiles().getSubprofile()) {
				roomInstances.get(r.getId()).add(s);
				roomprofiles.put(s.getName(),s);
			}
			
		}
		for (OntologyInstance o : tabs) {
			if(userForms.get(o.getId()) == null) {
				userForms.put(o.getId(), new ArrayList<TabForm>());
			} else {
				userForms.get(o.getId()).clear();
			}
			if(ontInstances.get(o.getId())==null) {
				ontInstances.put(o.getId(), new ArrayList<Subprofile>());
			} else {
				ontInstances.get(o.getId()).clear();
			}
		
			// Every Subprofile is shown in a seperate tab
			for (Subprofile tab : o.getSubprofiles().getSubprofile()) {
				f = new TabForm();
				if(subprofiles.get(tab.getName())!=null)
					subprofiles.remove(tab.getName());
				// Save Subprofile Tabs for later use
				subprofiles.put(tab.getName(), tab);
				String selectedRole = null;
				// Creating User tree and Comboboxes
				for (EnumObject enumObj : tab.getEnumObjects().getEnumObject()) {
					NativeSelect box = new NativeSelect(enumObj.getLabel());
					box.setImmediate(true);
					if (enumObj.isTreeParentNode()) {
						for (String item : enumObj.getValues().getValue()) {
							win.getUserTree().addItem(item);
							win.getUserTree().setChildrenAllowed(item, true);
							win.getUserTree().expandItemsRecursively(item);
						}
						
						selectedRole = enumObj.getSelectedValue();
						win.getUserTree().addItem(o.getId());
						win.getUserTree()
								.setParent(o.getId(), selectedRole);
						win.getUserTree().setChildrenAllowed(o.getId(),
								false);
					} 
					
					// Create ComboBox with enum objects and add to form
					for (String item : enumObj.getValues().getValue()) {
						box.addItem(item);
						box.setValue(enumObj.getSelectedValue());
						box.setNullSelectionAllowed(false);
						box.setNewItemsAllowed(false);
//						box.select(enumObj.getSelectedValue());
					}
					box.setImmediate(true);
					box.setDescription(enumObj.getDescription());
					f.addField(enumObj.getType(), box);
					if (enumObj.isRequired()) {
						box.setRequired(true);
						box.setRequiredError(enumObj.getLabel()
								+ " is required");
					}
				}
				// Add simpel objects to form
				for (SimpleObject simpl : tab.getSimpleObjects().getStringOrIntegerOrBoolean()) {
					createForm(simpl, f);
				}
				// Adding collection objects as a list to form
				if (tab.getCollections().getCollection().size() > 0) {
					for (CollectionValues cols : tab.getCollections().getCollection()) {
						ListSelect list = new ListSelect();
						list.setCaption(cols.getLabel());
						list.setWidth("120px");
						list.setDescription(cols.getDescription());
						if (cols.isMultiselection()) {
							list.setMultiSelect(true);
						}

						if (cols.getValueType().equals("string")) {
							for (SimpleObject sim : cols.getValues().getStringOrIntegerOrBoolean()) {
								StringValue s = (StringValue) sim;
								list.addItem(s.getValue());
								list.select(s.getValue());
							}
						}
						if (cols.getValueType().equals("integer")) {
							for (SimpleObject sim : cols.getValues().getStringOrIntegerOrBoolean()) {
								IntegerValue i = (IntegerValue) sim;
								list.addItem(i.getValue());
								list.select(i.getValue());
							}

						}
						if (cols.getValueType().equals("double")) {
							for (SimpleObject sim : cols.getValues().getStringOrIntegerOrBoolean()) {
								DoubleValue d = (DoubleValue) sim;
								list.addItem(d.getValue());
								list.select(d.getValue());
							}

						}
						// Adding List to Form
						list.setImmediate(true);
						list.setNullSelectionAllowed(true);
						list.setRows(5);
						list.setNewItemsAllowed(true);
						f.addField(cols.getLabel(), list);
					}
				}
				f.createFooter();
				f.getSaveButton().addListener((Button.ClickListener) this);
				f.getEditButton().addListener((Button.ClickListener) this);
				f.getResetButton().addListener((Button.ClickListener) this);
				f.getDeleteButton().addListener((Button.ClickListener) this);
				f.setReadOnly(true);
				f.setHeader(tab.getName());
				userForms.get(o.getId()).add(f);
				ontInstances.get(o.getId()).add(tab);
			}
		}
	}

	private TabForm createForm(SimpleObject simpleObject, TabForm form)
			throws ParseException {
		if (simpleObject instanceof CalendarValue) {
			CalendarValue cal = (CalendarValue) simpleObject;
			PopupDateField date = new PopupDateField(cal.getLabel());
			DateFormat format = new SimpleDateFormat();
			if (cal.getValue() != null && !cal.getValue().equals("")) {
				String d = cal.getValue();
				date.setValue(d);
				date.setResolution(PopupDateField.RESOLUTION_MIN);
				date.setImmediate(true);
				date.setInputPrompt(cal.getLabel());
				date.setShowISOWeekNumbers(true);
				date.setDescription(cal.getDescription());
				form.addField(cal.getLabel(), date);
			} else {
				if(cal.getName().equals("hardwareSettingTime")) {
					date.setValue(format.format(new Date()));
				} else {
					date.setInputPrompt("Last activity");
				}
				date.setDescription(cal.getDescription());
				form.addField(cal.getLabel(), date);
			}
			if (cal.isRequired()) {
				date.setRequired(true);
				date.setRequiredError(cal.getLabel() + " is required");
			}
		} else if (simpleObject instanceof StringValue) {
			StringValue st = (StringValue) simpleObject;
			if (st.getValue().length() > 30) {
				TextArea area = new TextArea(st.getLabel());
				area.setImmediate(true);
				area.setWriteThrough(false);
				area.setRows(5);
				area.setValue(st.getValue());
				area.setDescription(st.getDescription());
				form.addField(st.getLabel(), area);
				if (st.isRequired()) {
					area.setRequired(true);
					area.setRequiredError(st.getLabel() + " is required");
				}
				if (st.getValidator() != null) {
					if (st.getValidator().equals("EmailValidator")) {
						area.addValidator(new EmailValidator(
								"Emailaddress isn't correct"));
					}
				}
			} else {
				TextField tf = new TextField(st.getLabel());
				tf.setWriteThrough(false);
				tf.setImmediate(true);
				tf.setValue(st.getValue());
				tf.setDescription(st.getDescription());
				form.addField(simpleObject.getLabel(), tf);
				if (st.isRequired()) {
					tf.setRequired(true);
					tf.setRequiredError(st.getLabel() + " is required");
				}
				if (st.getValidator() != null) {
					if (st.getValidator().equals("EmailValidator")) {
						tf.addValidator(new EmailValidator(
								"Emailaddress isn't correct"));
					}
				}
			}
			form.createFooter();
		} else if (simpleObject instanceof IntegerValue) {
			IntegerValue integer = (IntegerValue) simpleObject;
			TextField t = new TextField(integer.getLabel());
			t.setImmediate(true);
			t.setWriteThrough(false);
			t.setValue(((IntegerValue) simpleObject).getValue());
			t.setDescription(integer.getDescription());
			form.addField(simpleObject.getLabel(), t);
			if (integer.isRequired()) {
				t.setRequired(true);
				t.setRequiredError(integer.getLabel() + " is required");
			}
			if (integer.getValidator() != null) {
				if (integer.getValidator().equals("RegexpValidator")) {
					if (integer.getName().contains("postalCode")) {
						t.addValidator(new RegexpValidator("[1-9][0-9]{4}",
								"Postal Code isn't correct"));
					} else {
						t.addValidator(new RegexpValidator("[1-9][0-9]*",
								"Number isn't correct"));
					}
				}
			}
		} else if (simpleObject instanceof BooleanValue) {
			BooleanValue bool = (BooleanValue) simpleObject;
			CheckBox box = new CheckBox(bool.getLabel());
			box.setImmediate(true);
			box.setWriteThrough(false);
			if (bool.isValue()) {
				box.setValue(true);
			} else {
				box.setValue(false);
			}
			box.setDescription(bool.getDescription());
			form.addField(bool.getLabel(), box);
			if (bool.isRequired()) {
				box.setRequired(true);
				box.setRequiredError(bool.getLabel() + " is required");
			}
		} else if (simpleObject instanceof DoubleValue) {
			DoubleValue doub = (DoubleValue) simpleObject;
			TextField tf = new TextField(doub.getLabel());
			tf.setImmediate(true);
			tf.setWriteThrough(false);
			tf.setValue(doub.getValue());
			tf.setDescription(doub.getDescription());
			form.addField(doub.getLabel(), tf);
			if (doub.isRequired()) {
				tf.setRequired(true);
				tf.setRequiredError(doub.getLabel() + " is required");
			}
			if (doub.getValidator() != null) {
				if (doub.getValidator().equals("RegexpValidator")) {
					tf.addValidator(new RegexpValidator("[0-9]*[.][0-9]{5}",
							"The floating value isn't correct"));
				}
			}
		}

		return form;
	}


	public void buttonClick(ClickEvent event) {
		String id = selectedItem;
		if (event.getButton() == ((TabForm) tabSheet.getSelectedTab())
				.getSaveButton()) {
			TabForm tab = ((TabForm) tabSheet.getSelectedTab());
			Subprofile sub = subprofiles.get(tabSheet.getTab(tab).getCaption());
			Subprofile subRoom = roomprofiles.get(tabSheet.getTab(tab).getCaption());
			// Aktuelles Subprofile übernimmt die Änderungen des Formulars
			ArrayList<SimpleObject>tempSim = new ArrayList<SimpleObject>();
			for(SimpleObject simi : sub.getSimpleObjects().getStringOrIntegerOrBoolean()) {
				tempSim.add(simi);
			}
			for (SimpleObject simpl : tempSim/*sub.getSimpleObjects()*/) {
				// tab.getItemProperty(simpl.getLabel());
				if (simpl instanceof StringValue) {
					StringValue val = (StringValue) simpl;
					if(val.isId()) 
						id = (String)tab.getField(simpl.getLabel()).getValue();
					val.setValue((String) tab.getField(simpl.getLabel())
							.getValue());
				} else if (simpl instanceof IntegerValue) {
					IntegerValue val = (IntegerValue) simpl;
					if (isIntegerNum(tab.getField(val.getLabel()).getValue()
							.toString())) {
						val.setValue(Integer.parseInt(tab
								.getField(simpl.getLabel()).getValue()
								.toString()));
					} else
						val.setValue(val.getDefaultValue());
				} else if (simpl instanceof DoubleValue) {
					DoubleValue val = (DoubleValue) simpl;
					if (isDoubleNum(tab.getField(val.getLabel()).getValue()
							.toString()))
						val.setValue(Double.parseDouble(tab
								.getItemProperty(simpl.getLabel()).getValue()
								.toString()));
					else
						val.setValue(val.getDefaultValue());
				} else if (simpl instanceof BooleanValue) {
					BooleanValue val = (BooleanValue) simpl;
					val.setValue((Boolean) tab.getField(simpl.getLabel())
							.getValue());

				} else if (simpl instanceof CalendarValue) {
					CalendarValue cal = (CalendarValue) simpl;
					DateFormat df = new SimpleDateFormat();
					if(cal.getName().equals("hardwareSettingTime")) {
						String date = df.format(new Date());
						cal.setValue(date);
					}
				}
			}
			// Enum Objecte
			ArrayList<EnumObject>tempEnums = new ArrayList<EnumObject>();
			for(EnumObject e : sub.getEnumObjects().getEnumObject()) {
				tempEnums.add(e);
			}
			for (EnumObject en : tempEnums/*sub.getEnums()*/) {
				if (en.isTreeParentNode()) {
					String sel = (String) ((Tree) win.getUserTree()).getValue();
					win.getUserTree().setParent(sel,
							tab.getField(en.getType()).getValue());
				}
				en.setSelectedValue((String) tab.getField(en.getType())
						.getValue());
			}

			//Collections
			ArrayList<CollectionValues>tempCols = new ArrayList<CollectionValues>();
			for(CollectionValues v : sub.getCollections().getCollection()) {
				tempCols.add(v);
			}
			for (CollectionValues col : tempCols /*sub.getCollections()*/) {
				Collection<SimpleObject> values = new ArrayList<SimpleObject>();
				Collection<SimpleObject> newVal = null;
				for (SimpleObject sim : col.getValues().getStringOrIntegerOrBoolean()) {
					if (sim instanceof StringValue) {
						newVal = (Collection<SimpleObject>) tab.getField(
								col.getLabel()).getValue();
						Object[] array = newVal.toArray();
						for (int i = 0; i < array.length; i++) {
							StringValue n = new StringValue();
							n.setDescription(sim.getDescription());
							n.setLabel(sim.getLabel());
							n.setRequired(sim.isRequired());
							n.setValidator(sim.getValidator());
							n.setValue(array[i].toString());
							values.add(n);
						}

					} else if (sim instanceof IntegerValue) {
						newVal = (Collection<SimpleObject>) tab.getField(
								col.getLabel()).getValue();
						Object[] array = newVal.toArray();
						for (int i = 0; i < array.length; i++) {
							IntegerValue n = new IntegerValue();
							n.setDescription(sim.getDescription());
							n.setLabel(sim.getLabel());
							n.setRequired(sim.isRequired());
							n.setValidator(sim.getValidator());
							if (isIntegerNum(array[i].toString()))
								n.setValue(Integer.parseInt(array[i].toString()));
							else
								n.setValue(n.getDefaultValue());
							values.add(n);
						}

					} else if (sim instanceof DoubleValue) {
						newVal = (Collection<SimpleObject>) tab.getField(
								col.getLabel()).getValue();
						Object[] array = newVal.toArray();
						for (int i = 0; i < array.length; i++) {
							DoubleValue n = new DoubleValue();
							n.setDescription(sim.getDescription());
							n.setLabel(sim.getLabel());
							n.setRequired(sim.isRequired());
							n.setValidator(sim.getValidator());
							if (isDoubleNum(array[i].toString()))
								n.setValue(Double.parseDouble(array[i]
										.toString()));
							else
								n.setValue(n.getDefaultValue());
							values.add(n);
						}

					}
					Values v = new Values();
					for(SimpleObject so : values) {
						v.getStringOrIntegerOrBoolean().add(so);
					}
					col.setValues(v);
				}
			}
			
			//Roomsfile
			ArrayList<SimpleObject>roomSimpls = new ArrayList<SimpleObject>();
			for(SimpleObject teSim : subRoom.getSimpleObjects().getStringOrIntegerOrBoolean()) {
				roomSimpls.add(teSim);
			}
			for (SimpleObject simpl : roomSimpls/*subRoom.getSimpleObjects()*/) {
				// tab.getItemProperty(simpl.getLabel());
				if (simpl instanceof StringValue) {
					StringValue val = (StringValue) simpl;
					val.setValue((String) tab.getField(simpl.getLabel())
							.getValue());
				} else if (simpl instanceof IntegerValue) {
					IntegerValue val = (IntegerValue) simpl;
					if (isIntegerNum(tab.getField(val.getLabel()).getValue()
							.toString())) {
						val.setValue(Integer.parseInt(tab
								.getField(simpl.getLabel()).getValue()
								.toString()));
					} else
						val.setValue(val.getDefaultValue());
				} else if (simpl instanceof DoubleValue) {
					DoubleValue val = (DoubleValue) simpl;
					if (isDoubleNum(tab.getField(val.getLabel()).getValue()
							.toString()))
						val.setValue(Double.parseDouble(tab
								.getItemProperty(simpl.getLabel()).getValue()
								.toString()));
					else
						val.setValue(val.getDefaultValue());
				} else if (simpl instanceof BooleanValue) {
					BooleanValue val = (BooleanValue) simpl;
					val.setValue((Boolean) tab.getField(simpl.getLabel())
							.getValue());

				} else if (simpl instanceof CalendarValue) {
					CalendarValue cal = (CalendarValue) simpl;
					DateFormat df = new SimpleDateFormat();
					if(cal.getName().equals("hardwareSettingTime")) {
						String date = df.format(new Date());
						cal.setValue(date);
					}
				}
			}
			// Enum Objecte
			ArrayList<EnumObject>roomEns = new ArrayList<EnumObject>();
			for(EnumObject te : subRoom.getEnumObjects().getEnumObject()) {
				roomEns.add(te);
			}
			for (EnumObject en : roomEns/*subRoom.getEnums()*/) {
				en.setSelectedValue((String) tab.getItemProperty(en.getType())
						.getValue());
			}

			ArrayList<CollectionValues>roomCols = new ArrayList<CollectionValues>();
			for(CollectionValues c : subRoom.getCollections().getCollection()) {
				roomCols.add(c);
			}
			for (CollectionValues col : roomCols /*subRoom.getCollections()*/) {
				Collection<SimpleObject> values = new ArrayList<SimpleObject>();
				Collection<SimpleObject> newVal = null;
				for (SimpleObject sim : col.getValues().getStringOrIntegerOrBoolean()) {
					if (sim instanceof StringValue) {
						newVal = (Collection<SimpleObject>) tab.getField(
								col.getLabel()).getValue();
						Object[] array = newVal.toArray();
						for (int i = 0; i < array.length; i++) {
							StringValue n = new StringValue();
							n.setDescription(sim.getDescription());
							n.setLabel(sim.getLabel());
							n.setRequired(sim.isRequired());
							n.setValidator(sim.getValidator());
							n.setValue(array[i].toString());
							values.add(n);
						}

					} else if (sim instanceof IntegerValue) {
						newVal = (Collection<SimpleObject>) tab.getField(
								col.getLabel()).getValue();
						Object[] array = newVal.toArray();
						for (int i = 0; i < array.length; i++) {
							IntegerValue n = new IntegerValue();
							n.setDescription(sim.getDescription());
							n.setLabel(sim.getLabel());
							n.setRequired(sim.isRequired());
							n.setValidator(sim.getValidator());
							if (isIntegerNum(array[i].toString()))
								n.setValue(Integer.parseInt(array[i].toString()));
							else
								n.setValue(n.getDefaultValue());
							values.add(n);
						}

					} else if (sim instanceof DoubleValue) {
						newVal = (Collection<SimpleObject>) tab.getField(
								col.getLabel()).getValue();
						Object[] array = newVal.toArray();
						for (int i = 0; i < array.length; i++) {
							DoubleValue n = new DoubleValue();
							n.setDescription(sim.getDescription());
							n.setLabel(sim.getLabel());
							n.setRequired(sim.isRequired());
							n.setValidator(sim.getValidator());
							if (isDoubleNum(array[i].toString()))
								n.setValue(Double.parseDouble(array[i]
										.toString()));
							else
								n.setValue(n.getDefaultValue());
							values.add(n);
						}

					}
					Values val =  new Values();
					for(SimpleObject s : values) {
						val.getStringOrIntegerOrBoolean().add(s);
					}
					col.setValues(val);
				}
			}
			Collections cosl = new Collections();
			for(CollectionValues cv : tempCols) {
				cosl.getCollection().add(cv);
			}
			sub.setCollections(cosl);
			
			EnumObjects ens = new EnumObjects();
			for(EnumObject eno : tempEnums) {
				ens.getEnumObject().add(eno);
			}
			sub.setEnumObjects(ens);
			
			SimpleObjects sims = new SimpleObjects();
			for(SimpleObject so : tempSim) {
				sims.getStringOrIntegerOrBoolean().add(so);
			}
			sub.setSimpleObjects(sims);
			
			Collections cs = new Collections();
			for(CollectionValues colvs : roomCols) {
				cs.getCollection().add(colvs);
			}
			subRoom.setCollections(cs);
			
			EnumObjects enes = new EnumObjects();
			for(EnumObject ene : roomEns) {
				enes.getEnumObject().add(ene);
			}
			subRoom.setEnumObjects(enes);
			
			SimpleObjects os = new SimpleObjects();
			for(SimpleObject sp : roomSimpls) {
				os.getStringOrIntegerOrBoolean().add(sp);
			}
			subRoom.setSimpleObjects(os);
			HashMap<String, List<Subprofile>> nOntInstances = new HashMap<String, List<Subprofile>>();
			for(Map.Entry<String, ArrayList<Subprofile>>tOnt : ontInstances.entrySet()) {
				
				if(tOnt.getKey().equals(selectedItem)) {
					nOntInstances.put(tOnt.getKey(), new ArrayList<Subprofile>());
					for(Subprofile sp : tOnt.getValue()) {
						if(sp.getName().equals(sub.getName())) {
							nOntInstances.get(tOnt.getKey()).add(sub);
						} else {
							nOntInstances.get(tOnt.getKey()).add(sp);
						}
					}
				} else {
					nOntInstances.put(tOnt.getKey(), tOnt.getValue());
				}
			}
//			for(Map.Entry<String, ArrayList<Subprofile>>tOnt : ontInstances.entrySet()) {
//				if(tOnt.getKey().equals(id)) {
//					nOntInstances.put(tOnt.getKey(), new ArrayList<Subprofile>());
//					nOntInstances.get(tOnt.getKey()).add(sub);
//				} else  {
//					nOntInstances.put(tOnt.getKey(), tOnt.getValue());
//				}
//			}
			//For room
			HashMap<String, List<Subprofile>> ri = new HashMap<String, List<Subprofile>>();
			for(Map.Entry<String, ArrayList<Subprofile>>tOnt : roomInstances.entrySet()) {
				
				if(tOnt.getKey().equals(id)) {
					ri.put(tOnt.getKey(), new ArrayList<Subprofile>());
					for(Subprofile sp : tOnt.getValue()) {
						if(sp.getName().equals(subRoom.getName())) {
							ri.get(tOnt.getKey()).add(subRoom);
						} else {
							ri.get(tOnt.getKey()).add(sp);
						}
					}
				} else {
					ri.put(tOnt.getKey(), tOnt.getValue());
				}
			}
//			for(Map.Entry<String, ArrayList<Subprofile>>rOnt : roomInstances.entrySet()) {
//				if(rOnt.getKey().equals(id)) {
//					ri.put(rOnt.getKey(), new ArrayList<Subprofile>());
//					ri.get(rOnt.getKey()).add(subRoom);
//				} else  {
//					ri.put(rOnt.getKey(), rOnt.getValue());
//				}
//			}
			dataAccess.updateUserData(actualFlat, id, nOntInstances);
	        dataAccess.updateUserData(actualHW, id, ri);
			tab.setReadOnly(true);
			tab.getSaveButton().setVisible(false);
			tab.getEditButton().setVisible(true);
			tab.getDeleteButton().setVisible(true);
			app.getMainWindow().showNotification(
					tab.getHeader() + " was updated",
					Notification.POSITION_CENTERED);

		} // Edit button was pushed
		else if (event.getButton() == ((TabForm) tabSheet.getSelectedTab())
				.getEditButton()) {
			TabForm tab = ((TabForm) tabSheet.getSelectedTab());
			tab.getSaveButton().setVisible(true);
			tab.getEditButton().setVisible(false);
			tab.getResetButton().setVisible(true);
			tab.getDeleteButton().setVisible(false);
			tab.setReadOnly(false);
			if(tab.getField("Device Address:") != null)
				tab.getField("Device Address:").setReadOnly(true);
			if(tab.getField("Last activity time:") != null)
				tab.getField("Last activity time:").setReadOnly(true);
			if(tab.getField("Setting time:") != null)
				tab.getField("Setting time:").setReadOnly(true);
		} // Delete Button was pushed
		else if (event.getButton() == ((TabForm) tabSheet.getSelectedTab())
				.getDeleteButton()) {
			dataAccess.deleteUserData(actualFlat, selectedItem);
			dataAccess.deleteUserData(actualHW, selectedItem);
			win.getUserTree().removeListener(this);
			win.getUserTree().removeItem(selectedItem);
			win.getUserTree().addListener(this);
			
			HardwareWindow hWin = null;
			TabForm tab = (TabForm)tabSheet.getSelectedTab();
			for(Window w : app.getMainWindow().getChildWindows()) {
				if(w instanceof HardwareWindow) {
					hWin = (HardwareWindow)w;
					if(hWin.getUserTree().containsId(tab.getField("Device Address:").getValue())) {
						hWin.getUserTree().removeItem(tab.getField("Device Address:").getValue());
						if(hWin.getHwc().getTabSheet().getComponentCount() > 0)
							hWin.getHwc().getTabSheet().removeAllComponents();
					}
				}
			}
			tabSheet.removeAllComponents();
			app.getMainWindow().showNotification(selectedItem + " was deleted",
					Notification.POSITION_CENTERED);
		}

	}

	public TabSheet getTabSheet() {
		return tabSheet;
	}

	public void setTabSheet(TabSheet tabSheet) {
		this.tabSheet = tabSheet;
	}


	public void valueChange(ValueChangeEvent event) {
		Tree tree = ((Tree) event.getProperty());
		if(tree.getValue() != null) {
		if (!tree.isRoot(tree.getValue())) {
			selectedItem = (String) tree.getValue();

			try {
					loadData();
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			tabSheet.removeAllComponents();
			for (TabForm form : userForms.get(selectedItem)) {
				Tab act = tabSheet.addTab(form, form.getHeader());
				act.setClosable(true);
			}

		} else {
			try {
				AddNewHardwareWindow roomWindow = new AddNewHardwareWindow(
						/*win.getFlatId(),*/ null, win, app);
				app.getMainWindow().addWindow(roomWindow);
			} catch (JAXBException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		}

	}

	private boolean isDoubleNum(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private boolean isIntegerNum(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
