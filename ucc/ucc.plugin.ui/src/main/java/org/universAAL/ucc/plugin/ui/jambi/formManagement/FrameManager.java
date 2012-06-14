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
package org.universAAL.ucc.plugin.ui.jambi.formManagement;

import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.ucc.plugin.ui.Activator;
import org.universAAL.ucc.plugin.ui.jambi.ModelMapper;
import org.universAAL.ucc.plugin.ui.jambi.model.FormModel;
import org.universAAL.ucc.viewjambi.common.SubWindow;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QCloseEvent;
import com.trolltech.qt.gui.QHideEvent;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QShowEvent;
import com.trolltech.qt.gui.QWidget;

/**
 * Manage a single {@link JFrame} corresponding to a {@link Form}
 * 
 * @author amedrano
 */
public class FrameManager {

	/**
	 * The frame being displayed
	 */
	private SubWindow frame;

	/**
	 * the {@link Form} for which {@link FrameManager#frame} corresponds to.
	 */
	private FormModel model;

	/**
	 * Constructor. Sets the actual rendering of the {@link Form} in motion
	 * 
	 * @param f
	 *            {@link Form} to be rendered
	 */
	public FrameManager(final Form f) {
		model = ModelMapper.getModelFor(f);

		System.out.println("creating form manager");
		
		QApplication.invokeAndWait(new Runnable() {
			public void run() {
				frame = model.getFrame();
				Activator.pluginBase().getMainView().showSubWindow(frame);
				
				frame.parentWidget().resize(new QSize(500, 400));
			}
		});

		/*
		 * TODO add a close action closing = log off if closing while in logging
		 * screen or no log required = closing
		 */
	}

	/**
	 * close the Frame and command the finalization of the form
	 * 
	 * @see FormModel#finalizeForm()
	 */
	public void disposeFrame() {
		model.finalizeForm();
	}
}
