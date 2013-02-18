package org.universaal.tools.packaging.tool.gui;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class Page1 extends PageImpl {

	private Text name, id, description, tags, version_major, version_minor, version_micro, version_build, app_profile;
	private int partNumber;

	protected Page1(String pageName, int pn) {
		super(pageName, "Specify details of the MPA you are creating", pn);
		partNumber = pn;
	}

	public void createControl(Composite parent) {

		container = new Composite(parent, SWT.NULL);
		setControl(container);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		layout.numColumns = 2;
		gd = new GridData(GridData.FILL_HORIZONTAL);

		Label label1 = new Label(container, SWT.NULL);
		name = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(name);
		label1.setText("Application name");
		name.setText(multipartApplication.getApplications().get(partNumber).getApplication().getName());			
		name.setLayoutData(gd);				

		Label label2 = new Label(container, SWT.NULL);
		id = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(id);
		label2.setText("Application ID");
		id.setText(multipartApplication.getApplications().get(partNumber).getApplication().getAppID());
		id.setLayoutData(gd);

		Label label3 = new Label(container, SWT.NULL);
		description = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(description);
		label3.setText("Description");
		description.setText(multipartApplication.getApplications().get(partNumber).getApplication().getDescription());
		description.setLayoutData(gd);		

		Label label4 = new Label(container, SWT.NULL);
		tags = new Text(container, SWT.BORDER | SWT.SINGLE);
		label4.setText("Insert tags (if any) separated by commas");

		List<String> tgs = multipartApplication.getApplications().get(partNumber).getApplication().getTags();
		String tbv = "";
		for(int i = 0; i < tgs.size(); i++)
			tbv.concat(tgs.get(i));

		tags.setText(tbv);
		tags.setLayoutData(gd);

		Label label5 = new Label(container, SWT.NULL);
		version_major = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(version_major);
		label5.setText("Major version");
		version_major.setText(multipartApplication.getApplications().get(partNumber).getApplication().getVersion().getMajor());
		version_major.setLayoutData(gd);

		Label label6 = new Label(container, SWT.NULL);
		version_minor = new Text(container, SWT.BORDER | SWT.SINGLE);
		mandatory.add(version_minor);
		label6.setText("Minor version");
		version_minor.setText(multipartApplication.getApplications().get(partNumber).getApplication().getVersion().getMinor());
		version_minor.setLayoutData(gd);

		Label label7 = new Label(container, SWT.NULL);
		version_micro = new Text(container, SWT.BORDER | SWT.SINGLE);
		label7.setText("Micro version");
		version_micro.setText(multipartApplication.getApplications().get(partNumber).getApplication().getVersion().getMicro());
		version_micro.setLayoutData(gd);

		Label label8 = new Label(container, SWT.NULL);
		version_build = new Text(container, SWT.BORDER | SWT.SINGLE);
		label8.setText("Build");
		version_build.setText(multipartApplication.getApplications().get(partNumber).getApplication().getVersion().getBuild());
		version_build.setLayoutData(gd);

		Label label9 = new Label(container, SWT.NULL);
		app_profile = new Text(container, SWT.BORDER | SWT.SINGLE);
		label9.setText("Application profile");
		app_profile.setText(multipartApplication.getApplications().get(partNumber).getApplication().getApplicationProfile());
		app_profile.setLayoutData(gd);

		name.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				multipartApplication.getApplications().get(partNumber).getApplication().setName(name.getText());				
			}
		});
		id.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				multipartApplication.getApplications().get(partNumber).getApplication().setAppID(id.getText());				
			}
		});
		description.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				multipartApplication.getApplications().get(partNumber).getApplication().setDescription(description.getText());					
			}
		});
		version_major.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				multipartApplication.getApplications().get(partNumber).getApplication().getVersion().setMajor(version_major.getText());				
			}
		});
		version_minor.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				multipartApplication.getApplications().get(partNumber).getApplication().getVersion().setMinor(version_minor.getText());				
			}
		});
		version_micro.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				multipartApplication.getApplications().get(partNumber).getApplication().getVersion().setMicro(version_micro.getText());				
			}
		});
		version_build.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				multipartApplication.getApplications().get(partNumber).getApplication().getVersion().setBuild(version_build.getText());				
			}
		});
		app_profile.addKeyListener(new QL() {

			@Override
			public void keyReleased(KeyEvent e) {
				multipartApplication.getApplications().get(partNumber).getApplication().setApplicationProfile(app_profile.getText());				
			}
		});
	}

	@Override
	public IWizardPage getNextPage(){

		if(!tags.getText().isEmpty()){
			String[] tag = tags.getText().split(",");
			if(tag != null){
				for(int i = 0; i < tag.length; i++)
					multipartApplication.getApplications().get(partNumber).getApplication().getTags().add(tag[i]);
			}
			else{
				multipartApplication.getApplications().get(partNumber).getApplication().getTags().add(tags.getText());
			}
		}
		return super.getNextPage();
	}
}