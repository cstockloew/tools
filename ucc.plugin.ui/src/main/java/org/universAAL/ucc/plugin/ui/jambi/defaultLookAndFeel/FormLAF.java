/*******************************************************************************
 * Copyright 2011 Universidad Politécnica de Madrid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.universAAL.ucc.plugin.ui.jambi.defaultLookAndFeel;


import javax.swing.JScrollPane;

import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.ucc.plugin.ui.Activator;
import org.universAAL.ucc.plugin.ui.jambi.model.FormModel;
import org.universAAL.ucc.viewjambi.common.SubWindow;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt.ScrollBarPolicy;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QScrollArea;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * The Look and Feel for Forms
 * 
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @author pabril
 * @see FormModel
 */
public class FormLAF extends FormModel {

	/**
	 * internal accounting for the frame being displayed.
	 */
	private SubWindow frame = null;

	/**
	 * Constructor.
	 * 
	 * @param f
	 *            {@link Form} which to model.
	 */
	public FormLAF(Form f) {
		super(f);
	}

	/**
	 * get the io panel wrapped in a scroll pane.
	 * 
	 * @return the {@link FormModel#getIOPanel} wrapped in a {@link JScrollPane} .
	 */
	protected QWidget getIOPanelScroll() {
		QWidget ioPanel = super.getIOPanel();

		QScrollArea sa = new QScrollArea();
		sa.setWidgetResizable(true);
		sa.setWidget(ioPanel);
		sa.setHorizontalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOff);
		sa.setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAsNeeded);

		return sa;
	}

	/**
	 * get the submit panel wrapped in a scroll pane.
	 * 
	 * @return the {@link FormModel#getSubmitPanel} wrapped in a {@link JScrollPane}.
	 */
	protected QScrollArea getSubmitPanelScroll(int depth) {
		QWidget submit = super.getSubmitPanel(depth);

		QScrollArea sa = new QScrollArea();
		sa.setWidgetResizable(true);
		sa.setWidget(submit);
		sa.setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAsNeeded);
		sa.setHorizontalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOff);

		return sa;
	}

	/**
	 * get the system panel wrapped in a scroll pane.
	 * 
	 * @return the {@link FormModel#getSystemPanel} wrapped in a {@link JScrollPane}.
	 */
	protected QScrollArea getSystemPanelScroll() {
		QWidget sys = super.getSystemPanel();

		QScrollArea sa = new QScrollArea();
		sa.setWidgetResizable(true);
		sa.setWidget(sys);
		sa.setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOff);
		sa.setHorizontalScrollBarPolicy(ScrollBarPolicy.ScrollBarAsNeeded);
		return sa;
	}

	/**
	 * generate the header panel.
	 * 
	 * @return a pannel with universAAL icon in it.
	 */
	protected QWidget getHeader() {
		QWidget header = new QWidget();
		header.setLayout(new QVBoxLayout());
		QPixmap icon = new QPixmap("classpath:/main/UniversAAl_logo.png");

		// TODO icon.setDescription("UniversAAL Logo Image");
		QLabel logo = new QLabel(header);
		logo.setPixmap(icon);
		logo.setAccessibleName("UniversAAL Logo");
		header.layout().addWidget(logo);
		return header;
	}

	/**
	 * render the frame for the {@link Form}.
	 */
	@Override
	public SubWindow getFrame() {
		if (form.isMessage()) {
			frame = new SubWindow(null);
			frame.setWindowTitle(form.getTitle());
			frame.setAccessibleName(form.getTitle());

			QWidget content = frame.getContentWidget();

			QWidget io = getIOPanelScroll();
			io.setAccessibleName(IO_NAME);

			QScrollArea sub = new QScrollArea();
			sub.setWidgetResizable(true);
			sub.setWidget(super.getSubmitPanel());
			sub.setVerticalScrollBarPolicy(ScrollBarPolicy.ScrollBarAlwaysOff);
			sub.setHorizontalScrollBarPolicy(ScrollBarPolicy.ScrollBarAsNeeded);
			sub.setAccessibleName(SUB_NAME);

			BorderLayout l = new BorderLayout(0);

			l.addWidget(io, BorderLayout.CENTER);
			l.addWidget(sub, BorderLayout.SOUTH);

			content.setLayout(l);
		}
		if (form.isSystemMenu()) {
			frame = new SubWindow(null);
			frame.setWindowTitle(form.getTitle());
			frame.setAccessibleName(form.getTitle());

			QWidget content = frame.getContentWidget();

			BorderLayout l = new BorderLayout(0);

			l.addWidget(getHeader(), BorderLayout.NORTH);
			l.addWidget(getIOPanel(), BorderLayout.CENTER);
			l.addWidget(getSystemPanelScroll(), BorderLayout.SOUTH);

			content.setLayout(l);
		}
		if (form.isStandardDialog()) {
			/*
			 * some further LAF can be done here: if only submits (no sub dialogs) and <4 (and priority hi?) then show
			 * like a popup.
			 */

			frame = new SubWindow(null);
			
			frame.setWindowTitle(form.getTitle());
			frame.setAccessibleName(form.getTitle());

			QWidget content = frame.getContentWidget();

			BorderLayout l = new BorderLayout(0);

			l.addWidget(getHeader(), BorderLayout.NORTH);
			QWidget io = getIOPanelScroll();
			io.setAccessibleName(IO_NAME);
			QScrollArea sub = getSubmitPanelScroll(0);
			sub.setAccessibleName(SUB_NAME);
			QScrollArea sys = getSystemPanelScroll();
			sys.setAccessibleName(SYS_NAME);

			l.addWidget(io, BorderLayout.CENTER);
			l.addWidget(sub, BorderLayout.EAST);
			l.addWidget(sys, BorderLayout.SOUTH);

			content.setLayout(l);
		}
		if (form.isSubdialog()) {
			frame = new SubWindow(null);
			frame.setWindowTitle(form.getTitle());
			frame.setAccessibleName(form.getTitle());

			QWidget content = frame.getContentWidget();

			BorderLayout l = new BorderLayout(0);

			l.addWidget(getHeader(), BorderLayout.NORTH);
			QScrollArea sub = getSubmitPanelScroll(0);
			sub.setAccessibleName(SUB_NAME);
			QScrollArea sys = getSystemPanelScroll();
			sys.setAccessibleName(SYS_NAME);
			QWidget subpanel = new QWidget();

			BorderLayout lSub = new BorderLayout(0);

			lSub.addWidget(getIOPanelScroll(), BorderLayout.CENTER);
			for (int i = super.getSubdialogLevel(); i > 1; i--) {
				lSub.addWidget(getSubmitPanel(i), BorderLayout.EAST);
				subpanel.setLayout(lSub);

				QWidget tempanel = new QWidget();
				BorderLayout lTmp = new BorderLayout(0);

				lTmp.addWidget(subpanel, BorderLayout.CENTER);
				tempanel.setLayout(lTmp);

				subpanel = tempanel;
				lSub = lTmp;
			}

			l.addWidget(subpanel, BorderLayout.CENTER);
			l.addWidget(sub, BorderLayout.EAST);
			l.addWidget(sys, BorderLayout.SOUTH);

			content.setLayout(l);
		}
		
		return frame;
	}

	/** {@inheritDoc} */
	public void terminateDialog() {
		if (frame != null) {
			QApplication.invokeAndWait(new Runnable() {
				public void run() {
					Activator.pluginBase().getMainView().removeSubWindow(frame);
					frame = null;
				}
			});
		}
	}
}
