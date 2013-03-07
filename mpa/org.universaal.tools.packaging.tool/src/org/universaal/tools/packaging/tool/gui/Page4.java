package org.universaal.tools.packaging.tool.gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.universaal.tools.packaging.api.Page;
import org.universaal.tools.packaging.impl.PageImpl;
import org.universaal.tools.packaging.tool.parts.LogicalCriteria;
import org.universaal.tools.packaging.tool.parts.LogicalRelation;
import org.universaal.tools.packaging.tool.parts.Requirement;
import org.universaal.tools.packaging.tool.parts.RequirementsGroup;
import org.universaal.tools.packaging.tool.parts.SingleRequirement;

public class Page4 extends PageImpl {

	private List<String> reqs, vals, logicalCriteria, logicalRelations;
	private Text req1, req2, req3, req4, req5;
	private Text val1, val2, val3, val4, val5;
	private Combo c1, c2, c3, c4, c5, c01, c12, c23, c34, c45, c56;

	private int offset;
	private boolean moreRequirementsInNextPage;
	private LogicalRelation fromPreviousPage;
	//private Map<Requirement, Boolean> addedInThisPage;

	protected Page4(String pageName, int offset, LogicalRelation fromPreviousPage) {
		super(pageName, "Specify requirements for the MPA you are creating.");
		this.offset = offset;
		this.moreRequirementsInNextPage = false;
		this.fromPreviousPage = fromPreviousPage;
		//this.addedInThisPage = new HashMap<Requirement, Boolean>();
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

		List<Requirement> list = app.getRequirements().getRequirementsList();
		//List<Requirement> toBeRemoved = new ArrayList<Requirement>();

		for(int i = offset; (i < list.size() && i < offset+5); i++){

			//toBeRemoved.add(list.get(i));

			if(list.get(i).isSingleReq()){
				reqs.add(list.get(i).getSingleRequirement().getRequirementName().toString());
				vals.add(list.get(i).getSingleRequirement().getRequirementValue().toString());
				logicalCriteria.add(list.get(i).getSingleRequirement().getRequirementCriteria().toString());

				logicalRelations.add(LogicalRelation.NONE.toString());
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
			//			}
			//
			//			// EXPERIMENTAL
			//			List<Requirement> toBeRemoved = new ArrayList<Requirement>();
			//			for(int i = currentreqs[0]; i < currentreqs[1]; i++){
			//				toBeRemoved.add(app.getRequirements().getRequirementsList().get(i));
			//			}
			//			app.getRequirements().getRequirementsList().remove(toBeRemoved);
			//			// EXPERIMENTAL
			//
			//			System.out.println("currentreqs[0] "+currentreqs[0]+" currentreqs[1] "+currentreqs[1]);
		}

		//app.getRequirements().getRequirementsList().remove(toBeRemoved);

		if((reqs.isEmpty() && vals.isEmpty()) || (reqs.size() < 5)) // create five elements for current page if you can't read them from current MPA
			for(int i = reqs.size(); i < 5; i++){
				reqs.add("");
				vals.add("");
				logicalCriteria.add(LogicalCriteria.EQUAL.toString());
			}

		if(logicalRelations.isEmpty() || logicalRelations.size() < 5)
			for(int i = logicalRelations.size(); i < 5; i++)
				logicalRelations.add(LogicalRelation.NONE.toString());

		Label l1 = new Label(container, SWT.NULL);
		l1.setText("Requirement name");

		Label l2 = new Label(container, SWT.NULL);
		l2.setText("Relation");

		Label l3 = new Label(container, SWT.NULL);
		l3.setText("Requirement value");

		if(offset != 0){
			// consider previous page logical relation
			Label l0 = new Label(container, SWT.NULL);
			c01 = new Combo(container, SWT.READ_ONLY);
			l0.setText("Relation between rule #"+(offset)+" (previous page) and #"+(1+offset));
			for(int i = 0; i < LogicalRelation.values().length; i++)
				c01.add(LogicalRelation.values()[i].toString());
			c01.setText(this.fromPreviousPage.toString());			
			c01.setLayoutData(gd);

			Label empty0 = new Label(container, SWT.NULL);
			empty0.setText("");
		}

		req1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//req1.setText(reqs.get(0));	
		req1.setText("");		
		req1.setLayoutData(gd);	

		//LogicalCriteria
		c1 = new Combo(container, SWT.READ_ONLY);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c1.add(LogicalCriteria.values()[i].toString());
		//c1.setText(LogicalCriteria.valueOf(logicalCriteria.get(0)).toString());	
		c1.setText(LogicalCriteria.EQUAL.toString());
		c1.setLayoutData(gd);

		val1 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//val1.setText(vals.get(0));			
		val1.setText("");
		val1.setLayoutData(gd);


		//LogicalRelation
		Label l4 = new Label(container, SWT.NULL);
		c12 = new Combo(container, SWT.READ_ONLY);
		l4.setText("Relation between rule #"+(1+offset)+" and #"+(2+offset));
		for(int i = 0; i < LogicalRelation.values().length; i++)
			c12.add(LogicalRelation.values()[i].toString());
		//c12.setText(LogicalRelation.valueOf(logicalRelations.get(0)).toString());			
		c12.setText(LogicalRelation.NONE.toString());
		c12.setLayoutData(gd);

		Label empty1 = new Label(container, SWT.NULL);
		empty1.setText("");


		req2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//req2.setText(reqs.get(1));	
		req2.setText("");
		req2.setLayoutData(gd);

		c2 = new Combo(container, SWT.READ_ONLY);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c2.add(LogicalCriteria.values()[i].toString());
		//c2.setText(LogicalCriteria.valueOf(logicalCriteria.get(1)).toString());			
		c2.setText(LogicalCriteria.EQUAL.toString());
		c2.setLayoutData(gd);

		val2 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//val2.setText(vals.get(1));		
		val2.setText("");
		val2.setLayoutData(gd);


		//LogicalRelation
		Label l5 = new Label(container, SWT.NULL);
		c23 = new Combo(container, SWT.READ_ONLY);
		l5.setText("Relation between rule #"+(2+offset)+" and #"+(3+offset));
		for(int i = 0; i < LogicalRelation.values().length; i++)
			c23.add(LogicalRelation.values()[i].toString());
		//c23.setText(LogicalRelation.valueOf(logicalRelations.get(1)).toString());			
		c23.setText(LogicalRelation.NONE.toString());
		c23.setLayoutData(gd);

		Label empty2 = new Label(container, SWT.NULL);
		empty2.setText("");


		req3 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//req3.setText(reqs.get(2));		
		req3.setText("");
		req3.setLayoutData(gd);	

		c3 = new Combo(container, SWT.READ_ONLY);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c3.add(LogicalCriteria.values()[i].toString());
		//c3.setText(LogicalCriteria.valueOf(logicalCriteria.get(2)).toString());			
		c3.setText(LogicalCriteria.EQUAL.toString());
		c3.setLayoutData(gd);

		val3 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//val3.setText(vals.get(2));	
		val3.setText("");	
		val3.setLayoutData(gd);


		//LogicalRelation
		Label l6 = new Label(container, SWT.NULL);
		c34 = new Combo(container, SWT.READ_ONLY);
		l6.setText("Relation between rule #"+(3+offset)+" and #"+(4+offset));
		for(int i = 0; i < LogicalRelation.values().length; i++)
			c34.add(LogicalRelation.values()[i].toString());
		//c34.setText(LogicalRelation.valueOf(logicalRelations.get(2)).toString());		
		c34.setText(LogicalRelation.NONE.toString());
		c34.setLayoutData(gd);

		Label empty3 = new Label(container, SWT.NULL);
		empty3.setText("");


		req4 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//req4.setText(reqs.get(3));		
		req4.setText("");
		req4.setLayoutData(gd);	

		//LogicalCriteria
		c4 = new Combo(container, SWT.READ_ONLY);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c4.add(LogicalCriteria.values()[i].toString());
		//c4.setText(LogicalCriteria.valueOf(logicalCriteria.get(3)).toString());			
		c4.setText(LogicalCriteria.EQUAL.toString());
		c4.setLayoutData(gd);

		val4 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//val4.setText(vals.get(3));			
		val4.setText("");
		val4.setLayoutData(gd);


		//LogicalRelation
		Label l7 = new Label(container, SWT.NULL);
		c45 = new Combo(container, SWT.READ_ONLY);
		l7.setText("Relation between rule #"+(4+offset)+" and #"+(5+offset));
		for(int i = 0; i < LogicalRelation.values().length; i++)
			c45.add(LogicalRelation.values()[i].toString());
		//c45.setText(LogicalRelation.valueOf(logicalRelations.get(3)).toString());		
		c45.setText(LogicalRelation.NONE.toString());
		c45.setLayoutData(gd);

		Label empty4 = new Label(container, SWT.NULL);
		empty4.setText("");

		req5 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//req5.setText(reqs.get(4));			
		req5.setText("");
		req5.setLayoutData(gd);	

		//LogicalCriteria
		c5 = new Combo(container, SWT.READ_ONLY);
		for(int i = 0; i < LogicalCriteria.values().length; i++)
			c5.add(LogicalCriteria.values()[i].toString());
		//c5.setText(LogicalCriteria.valueOf(logicalCriteria.get(4)).toString());			
		c5.setText(LogicalCriteria.EQUAL.toString());
		c5.setLayoutData(gd);

		val5 = new Text(container, SWT.BORDER | SWT.SINGLE);
		//val5.setText(vals.get(4));		
		val5.setText("");
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

		Label l8 = new Label(container, SWT.NULL);
		l8.setText("Relation between rule #"+(5+offset)+" and #"+(6+offset)+" (next page)");
		c56 = new Combo(container, SWT.READ_ONLY);

		for(int i = 0; i < LogicalRelation.values().length; i++)
			c56.add(LogicalRelation.values()[i].toString());
		//c56.setText(LogicalRelation.valueOf(logicalRelations.get(4)).toString());			
		c56.setText(LogicalRelation.NONE.toString());
		c56.setLayoutData(gd);
		final Button b = new Button(container, SWT.PUSH);
		b.setText("Click to add more requirements");
		b.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				moreRequirementsInNextPage = !moreRequirementsInNextPage;
				if(!moreRequirementsInNextPage){
					b.setText("Click to add more requirements");
				}
				else{
					b.setText("No more requirements");
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		b.setLayoutData(gd);		

		setPageComplete(true); // requirements are optional
	}

	@Override
	public boolean nextPressed() {

		//		if(!app.getRequirements().getRequirementsList().isEmpty())
		//			for(int i = offset; (i < offset+5 && i < app.getRequirements().getRequirementsList().size()); i++)
		//				//if(app.getRequirements().getRequirementsList().get(i) != null)
		//				app.getRequirements().getRequirementsList().remove(i); // remove current page of requirements 

		//		if(!app.getRequirements().getRequirementsList().isEmpty()){
		//
		//			int[] currentreqs = findReqInThisPage(offset/5);
		//
		//		}

		//			for(int i = offset; i < size; i++){
		//				Requirement req = app.getRequirements().getRequirementsList().get(i);
		//				if(req.isSingleReq()){
		//					app.getRequirements().getRequirementsList().remove(i);
		//					System.out.println("removed one "+i);
		//					j++;
		//				}
		//				else{ // is a group
		//					app.getRequirements().getRequirementsList().remove(i);
		//					System.out.println("removed two "+i);
		//					j++;
		//					j++;
		//				}
		//				if(j == 5){
		//					System.out.println("removed five, break");
		//					break;
		//				}
		//			}
		//		}

		// and

		// insert them again
		if(offset == 0){
			// first page of requirements
			if(c12.getText().equals(LogicalRelation.NONE.toString())){
				if(isValid(req1, val1, c1))
					single(req1, val1, LogicalCriteria.valueOf(c1.getText()));
				if(isValid(req2, val2, c2) && c23.getText().equals(LogicalRelation.NONE.toString()))
					single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
			}			
			else{
				if(isValid(req1, val1, c1) && isValid(req2, val2, c2) && isValid(c12))
					group(LogicalRelation.valueOf(c12.getText()), req1, val1, LogicalCriteria.valueOf(c1.getText()), req2, val2, LogicalCriteria.valueOf(c2.getText()));
			}
			if(c23.getText().equals(LogicalRelation.NONE.toString())){
				//single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
				if(isValid(req3, val3, c3) && c34.getText().equals(LogicalRelation.NONE.toString()))
					single(req3, val3, LogicalCriteria.valueOf(c3.getText()));
			}			
			else{
				if(isValid(req2, val2, c2) && isValid(req3,  val3, c3) && isValid(c23))
					group(LogicalRelation.valueOf(c23.getText()), req2, val2, LogicalCriteria.valueOf(c2.getText()), req3, val3, LogicalCriteria.valueOf(c3.getText()));
			}
			if(c34.getText().equals(LogicalRelation.NONE.toString())){
				//single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
				if(isValid(req4, val4, c4) && c45.getText().equals(LogicalRelation.NONE.toString()))
					single(req4, val4, LogicalCriteria.valueOf(c4.getText()));
			}			
			else{
				if(isValid(req3, val3, c3) && isValid(req4, val4, c4) && isValid(c34))
					group(LogicalRelation.valueOf(c34.getText()), req3, val3, LogicalCriteria.valueOf(c3.getText()), req4, val4, LogicalCriteria.valueOf(c4.getText()));
			}
			if(c45.getText().equals(LogicalRelation.NONE.toString())){
				//single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
				if(isValid(req5, val5, c5) && 
						(!moreRequirementsInNextPage || // no more pages
								(c56.getText().equals(LogicalRelation.NONE.toString()) && moreRequirementsInNextPage))) //new page and NONE
					single(req5, val5, LogicalCriteria.valueOf(c5.getText()));
			}			
			else{
				if(isValid(req4, val4, c4) && isValid(req5, val5, c5) && isValid(c45))
					group(LogicalRelation.valueOf(c45.getText()), req4, val4, LogicalCriteria.valueOf(c4.getText()), req5, val5, LogicalCriteria.valueOf(c5.getText()));
			}
		}
		else{
			// other page of requirements
			if(c01.getText().equals(LogicalRelation.NONE.toString())){
				// nothing to do - already added as single requirement in previous page	
			}			
			else{
				// get last requirement inserted
				SingleRequirement last = null;
				int lastReq = this.app.getRequirements().getRequirementsList().size()-1;

				if(this.app.getRequirements().getRequirementsList().get(lastReq).getSingleRequirement() != null)
					last = this.app.getRequirements().getRequirementsList().get(lastReq).getSingleRequirement();
				if(this.app.getRequirements().getRequirementsList().get(lastReq).getRequirementGroup() != null)
					last = this.app.getRequirements().getRequirementsList().get(lastReq).getRequirementGroup().getReq2();

				if(isValid(req1, val1, c1))
					group(LogicalRelation.valueOf(c01.getText()), last.getRequirementName(), last.getRequirementValue(), LogicalCriteria.valueOf(c1.getText()), 
							req1.getText(), val1.getText(), LogicalCriteria.valueOf(c1.getText()));
			}
			if(c12.getText().equals(LogicalRelation.NONE.toString())){
				if(isValid(req1, val1, c1) && c01.getText().equals(LogicalRelation.NONE.toString()))
					single(req1, val1, LogicalCriteria.valueOf(c1.getText()));
				if(isValid(req2, val2, c2) && c23.getText().equals(LogicalRelation.NONE.toString()))
					single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
			}			
			else{
				if(isValid(req1, val1, c1) && isValid(req2, val2, c2) && isValid(c12))
					group(LogicalRelation.valueOf(c12.getText()), req1, val1, LogicalCriteria.valueOf(c1.getText()), req2, val2, LogicalCriteria.valueOf(c2.getText()));
			}
			if(c23.getText().equals(LogicalRelation.NONE.toString())){
				//single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
				if(isValid(req3, val3, c3) && c34.getText().equals(LogicalRelation.NONE.toString()))
					single(req3, val3, LogicalCriteria.valueOf(c3.getText()));
			}			
			else{
				if(isValid(req2, val2, c2) && isValid(req3,  val3, c3) && isValid(c23))
					group(LogicalRelation.valueOf(c23.getText()), req2, val2, LogicalCriteria.valueOf(c2.getText()), req3, val3, LogicalCriteria.valueOf(c3.getText()));
			}
			if(c34.getText().equals(LogicalRelation.NONE.toString())){
				//single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
				if(isValid(req4, val4, c4) && c45.getText().equals(LogicalRelation.NONE.toString()))
					single(req4, val4, LogicalCriteria.valueOf(c4.getText()));
			}			
			else{
				if(isValid(req3, val3, c3) && isValid(req4, val4, c4) && isValid(c34))
					group(LogicalRelation.valueOf(c34.getText()), req3, val3, LogicalCriteria.valueOf(c3.getText()), req4, val4, LogicalCriteria.valueOf(c4.getText()));
			}
			if(c45.getText().equals(LogicalRelation.NONE.toString())){
				//single(req2, val2, LogicalCriteria.valueOf(c2.getText()));
				if(isValid(req5, val5, c5) && 
						(!moreRequirementsInNextPage || // no more pages
								(c56.getText().equals(LogicalRelation.NONE.toString()) && moreRequirementsInNextPage))) //new page and NONE
					single(req5, val5, LogicalCriteria.valueOf(c5.getText()));
			}			
			else{
				if(isValid(req4, val4, c4) && isValid(req5, val5, c5) && isValid(c45))
					group(LogicalRelation.valueOf(c45.getText()), req4, val4, LogicalCriteria.valueOf(c4.getText()), req5, val5, LogicalCriteria.valueOf(c5.getText()));
			}
		}

		if(moreRequirementsInNextPage && !(getNextPage() instanceof Page4)){
			Page4 p_req = new Page4(Page.PAGE4+" #"+PageImpl.otherGeneralReqs++, offset+5, LogicalRelation.valueOf(c56.getText()));
			p_req.setMPA(multipartApplication);
			addPageCustom(this, p_req);
		}

		return true;
	}

	/*private int[] findReqInThisPage(int pageNumber){

		int[] firstLast = new int[]{0, 0};

		List<Requirement> list = this.app.getRequirements().getRequirementsList();
		int counter = 0;

		for(int i = 0; i < list.size(); i++){
			if(list.get(i).isSingleReq())
				counter = counter + 1;
			else
				counter = counter + 2;

			if(counter == (5*pageNumber+5)){
				firstLast[1] = i;
				break;
			}
			else if(counter == (5*pageNumber+6)){
				//TODO 
				firstLast[1] = i;
				break;
			}
		}

		counter = 0;
		for(int i = firstLast[1]; i >= 0; i--){
			if(list.get(i).isSingleReq())
				counter = counter + 1; 
			else
				counter = counter + 2;

			if(counter == (5*pageNumber+5)){
				firstLast[0] = i;
				break;
			}
			else if(counter == (5*pageNumber+6)){
				//TODO 
				firstLast[0] = i;
				break;
			}
		}

		System.out.println("RequirementsList "+app.getRequirements().getRequirementsList().size());
		System.out.println("currentreqs[0] "+firstLast[0]+" currentreqs[1] "+firstLast[1]);

		return firstLast;		
	}*/

	private void group(LogicalRelation lr, Text req1, Text val1, LogicalCriteria lc1, Text req2, Text val2, LogicalCriteria lc2){

		SingleRequirement r1 = new SingleRequirement(req1.getText(), val1.getText(), lc1);
		SingleRequirement r2 = new SingleRequirement(req2.getText(), val2.getText(), lc2);

		RequirementsGroup r = new RequirementsGroup(r1, r2, lr);
		Requirement rr = new Requirement(r, false);

		if(!alreadyIn(null, r)){
			app.getRequirements().getRequirementsList().add(rr);
			//this.addedInThisPage.add(rr);
		}
	}

	private void single(Text req1, Text val1, LogicalCriteria lc1){

		SingleRequirement r = new SingleRequirement(req1.getText(), val1.getText(), lc1);

		Requirement rr = new Requirement(r, false);

		if(!alreadyIn(r, null)){
			app.getRequirements().getRequirementsList().add(rr);
			//this.addedInThisPage.add(rr);
		}
	}

	private void group(LogicalRelation lr, String req1, String val1, LogicalCriteria lc1, String req2, String val2, LogicalCriteria lc2){

		SingleRequirement r1 = new SingleRequirement(req1, val1, lc1);
		SingleRequirement r2 = new SingleRequirement(req2, val2, lc2);

		RequirementsGroup r = new RequirementsGroup(r1, r2, lr);
		Requirement rr = new Requirement(r, false);

		if(!alreadyIn(null, r)){
			app.getRequirements().getRequirementsList().add(rr);
			//this.addedInThisPage.add(rr);
		}
	}

	//	private void single(String req1, String val1, LogicalCriteria lc1){
	//
	//		SingleRequirement r = new SingleRequirement(req1, val1, lc1);
	//
	//		Requirement rr = new Requirement(r, false);
	//
	//		if(!alreadyIn(r, null)){
	//			app.getRequirements().getRequirementsList().add(rr);
	//			//this.addedInThisPage.add(rr);
	//		}
	//	}

	private boolean alreadyIn(SingleRequirement r, RequirementsGroup rr){

		for(int i = 0; i < app.getRequirements().getRequirementsList().size(); i++){
			if(r != null){
				if(this.app.getRequirements().getRequirementsList().get(i).isSingleReq() && 
						this.app.getRequirements().getRequirementsList().get(i).getSingleRequirement().equals(r))
					return true;			
			}
			else if(rr != null){
				if(!this.app.getRequirements().getRequirementsList().get(i).isSingleReq() && 
						this.app.getRequirements().getRequirementsList().get(i).getRequirementGroup().equals(rr))
					return true;	
			}
		}

		return false;
	}
}