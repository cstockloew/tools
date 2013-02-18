package org.universaal.tools.packaging.tool.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.tool.parts.LogicalCriteria;
import org.universaal.tools.packaging.tool.parts.LogicalRelation;
import org.universaal.tools.packaging.tool.parts.Requirement;
import org.universaal.tools.packaging.tool.parts.RequirementsGroup;
import org.universaal.tools.packaging.tool.parts.SingleRequirement;

public class Page4 extends PageImpl {

	private int partNumber;
	private List<String> reqs, vals, logicalCriteria, logicalRelations;
	private Text req1, req2, req3, req4, req5;
	private Text val1, val2, val3, val4, val5;
	private Combo c1, c2, c3, c4, c5, c12, c23, c34, c45;

	protected Page4(String pageName, int pn) {
		super(pageName, "Specify requirements for the MPA you are creating.", pn);
		partNumber = pn;
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);		

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 3;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		reqs = new ArrayList<String>();
		vals = new ArrayList<String>();
		logicalCriteria = new ArrayList<String>();
		logicalRelations = new ArrayList<String>();

		List<Requirement> list = multipartApplication.getApplications().get(partNumber).getRequirements().getRequirementsList();

		for(int i = 0; i < list.size(); i++){
			if(list.get(i).isSingleReq()){
				reqs.add(list.get(i).getSingleRequirement().getRequirementName().toString());
				vals.add(list.get(i).getSingleRequirement().getRequirementValue().toString());
				logicalCriteria.add(list.get(i).getSingleRequirement().getRequirementCriteria().toString());
			}
			else{
				reqs.add(list.get(i).getRequirementGroup().getReq1().getRequirementName().toString());
				vals.add(list.get(i).getRequirementGroup().getReq1().getRequirementValue().toString());
				logicalCriteria.add(list.get(i).getRequirementGroup().getReq1().getRequirementCriteria().toString());

				reqs.add(list.get(i).getRequirementGroup().getReq2().getRequirementName().toString());
				vals.add(list.get(i).getRequirementGroup().getReq2().getRequirementValue().toString());
				logicalCriteria.add(list.get(i).getRequirementGroup().getReq2().getRequirementCriteria().toString());

				logicalRelations.add(list.get(i).getRequirementGroup().getRelation().toString());
			}
		}

		if(reqs.isEmpty() && vals.isEmpty())
			for(int i = 0; i < 5; i++){
				reqs.add("");
				vals.add("");
				logicalCriteria.add(LogicalCriteria.EQUAL.toString());

				if(i != 0)
					logicalRelations.add(LogicalRelation.NONE.toString());
			}

		Label l1 = new Label(container, SWT.NULL);
		l1.setText("Requirement name");

		Label l2 = new Label(container, SWT.NULL);
		l2.setText("Relation");

		Label l3 = new Label(container, SWT.NULL);
		l3.setText("Requirement value");

		req1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		req1.setText(reqs.get(0));			
		req1.setLayoutData(gd);	

		//LogicalCriteria
		c1 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(req1);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c1.add(LogicalCriteria.values()[i].toString());
		c1.setText(LogicalCriteria.valueOf(logicalCriteria.get(0)).toString());			
		c1.setLayoutData(gd);

		val1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		val1.setText(vals.get(0));			
		val1.setLayoutData(gd);


		//LogicalRelation
		Label l4 = new Label(container, SWT.NULL);
		c12 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(req1);
		l4.setText("Relation between rule #1 and #2");
		for(int i = 0; i < LogicalRelation.values().length; i++)
			c12.add(LogicalRelation.values()[i].toString());
		c12.setText(LogicalRelation.valueOf(logicalRelations.get(0)).toString());			
		c12.setLayoutData(gd);

		Label empty1 = new Label(container, SWT.NULL);
		empty1.setText("");


		req2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		req2.setText(reqs.get(1));			
		req2.setLayoutData(gd);

		c2 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(req1);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c2.add(LogicalCriteria.values()[i].toString());
		c2.setText(LogicalCriteria.valueOf(logicalCriteria.get(1)).toString());			
		c2.setLayoutData(gd);

		val2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		val2.setText(vals.get(1));			
		val2.setLayoutData(gd);


		//LogicalRelation
		Label l5 = new Label(container, SWT.NULL);
		c23 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(req1);
		l5.setText("Relation between rule #2 and #3");
		for(int i = 0; i < LogicalRelation.values().length; i++)
			c23.add(LogicalRelation.values()[i].toString());
		c23.setText(LogicalRelation.valueOf(logicalRelations.get(1)).toString());			
		c23.setLayoutData(gd);

		Label empty2 = new Label(container, SWT.NULL);
		empty2.setText("");


		req3 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		req3.setText(reqs.get(2));			
		req3.setLayoutData(gd);	

		c3 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(req1);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c3.add(LogicalCriteria.values()[i].toString());
		c3.setText(LogicalCriteria.valueOf(logicalCriteria.get(2)).toString());			
		c3.setLayoutData(gd);

		val3 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		val3.setText(vals.get(2));			
		val3.setLayoutData(gd);


		//LogicalRelation
		Label l6 = new Label(container, SWT.NULL);
		c34 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(req1);
		l6.setText("Relation between rule #3 and #4");
		for(int i = 0; i < LogicalRelation.values().length; i++)
			c34.add(LogicalRelation.values()[i].toString());
		c34.setText(LogicalRelation.valueOf(logicalRelations.get(2)).toString());			
		c34.setLayoutData(gd);

		Label empty3 = new Label(container, SWT.NULL);
		empty3.setText("");


		req4 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		req4.setText(reqs.get(3));			
		req4.setLayoutData(gd);	

		//LogicalCriteria
		c4 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(req1);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c4.add(LogicalCriteria.values()[i].toString());
		c4.setText(LogicalCriteria.valueOf(logicalCriteria.get(3)).toString());			
		c4.setLayoutData(gd);

		val4 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		val4.setText(vals.get(3));			
		val4.setLayoutData(gd);


		//LogicalRelation
		Label l7 = new Label(container, SWT.NULL);
		c45 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(req1);
		l7.setText("Relation between rule #4 and #5");
		for(int i = 0; i < LogicalRelation.values().length; i++)
			c45.add(LogicalRelation.values()[i].toString());
		c45.setText(LogicalRelation.valueOf(logicalRelations.get(3)).toString());			
		c45.setLayoutData(gd);

		Label empty4 = new Label(container, SWT.NULL);
		empty4.setText("");

		req5 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		req5.setText(reqs.get(4));			
		req5.setLayoutData(gd);	

		//LogicalCriteria
		c5 = new Combo(container, SWT.READ_ONLY);
		//mandatory.add(req1);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c5.add(LogicalCriteria.values()[i].toString());
		c5.setText(LogicalCriteria.valueOf(logicalCriteria.get(4)).toString());			
		c5.setLayoutData(gd);

		val5 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//mandatory.add(req1);
		val5.setText(vals.get(4));			
		val5.setLayoutData(gd);

		req1.addKeyListener(new FullListener());
		req2.addKeyListener(new FullListener() {});
		req3.addKeyListener(new FullListener() {});
		req4.addKeyListener(new FullListener() {});
		req5.addKeyListener(new FullListener() {});
		val1.addKeyListener(new FullListener() {});
		val2.addKeyListener(new FullListener() {});
		val3.addKeyListener(new FullListener() {});
		val4.addKeyListener(new FullListener() {});
		val5.addKeyListener(new FullListener() {});
		c1.addKeyListener(new FullListener() {});
		c2.addKeyListener(new FullListener() {});
		c3.addKeyListener(new FullListener() {});
		c4.addKeyListener(new FullListener() {});
		c5.addKeyListener(new FullListener() {});
		c12.addKeyListener(new FullListener() {});
		c23.addKeyListener(new FullListener() {});
		c34.addKeyListener(new FullListener() {});
		c45.addKeyListener(new FullListener() {});
	}

	@Override
	public IWizardPage getNextPage(){

		if(c12.getText().equals(LogicalRelation.NONE.toString())){
			single(req1, val1, LogicalCriteria.valueOf(c1.getText()));
			single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
		}			
		else{
			group(LogicalRelation.valueOf(c12.getText()), req1, val1, LogicalCriteria.valueOf(c1.getText()), req2, val2, LogicalCriteria.valueOf(c2.getText()));
		}
		if(c23.getText().equals(LogicalRelation.NONE.toString())){
			//single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
			single(req3, val3, LogicalCriteria.valueOf(c3.getText()));
		}			
		else{
			group(LogicalRelation.valueOf(c23.getText()), req2, val2, LogicalCriteria.valueOf(c2.getText()), req3, val3, LogicalCriteria.valueOf(c3.getText()));
		}
		if(c34.getText().equals(LogicalRelation.NONE.toString())){
			//single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
			single(req4, val4, LogicalCriteria.valueOf(c4.getText()));
		}			
		else{
			group(LogicalRelation.valueOf(c34.getText()), req3, val3, LogicalCriteria.valueOf(c3.getText()), req4, val4, LogicalCriteria.valueOf(c4.getText()));
		}
		if(c45.getText().equals(LogicalRelation.NONE.toString())){
			//single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
			single(req5, val5, LogicalCriteria.valueOf(c4.getText()));
		}			
		else{
			group(LogicalRelation.valueOf(c45.getText()), req4, val4, LogicalCriteria.valueOf(c4.getText()), req5, val5, LogicalCriteria.valueOf(c5.getText()));
		}

		return super.getNextPage();
	}

	private void group(LogicalRelation lr, Text req1, Text val1, LogicalCriteria lc1, Text req2, Text val2, LogicalCriteria lc2){
		SingleRequirement r1 = new SingleRequirement(req1.getText(), val1.getText(), lc1);
		SingleRequirement r2 = new SingleRequirement(req2.getText(), val2.getText(), lc2);

		RequirementsGroup r = new RequirementsGroup(r1, r2, lr);
		multipartApplication.getApplications().get(partNumber).getRequirements().getRequirementsList().add(new Requirement(r, false));
	}

	private void single(Text req1, Text val1, LogicalCriteria lc1){
		SingleRequirement r = new SingleRequirement(req1.getText(), val1.getText(), lc1);
		multipartApplication.getApplications().get(partNumber).getRequirements().getRequirementsList().add(new Requirement(r, false));
	}
}