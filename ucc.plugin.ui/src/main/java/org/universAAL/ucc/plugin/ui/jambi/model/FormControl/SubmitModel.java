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
package org.universAAL.ucc.plugin.ui.jambi.model.FormControl;

import org.universAAL.middleware.ui.rdf.FormControl;
import org.universAAL.middleware.ui.rdf.Input;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ucc.plugin.ui.Activator;
import org.universAAL.ucc.plugin.ui.jambi.Renderer;
import org.universAAL.ucc.plugin.ui.jambi.model.Model;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

/**
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @see Submit
 */
public class SubmitModel extends Model {

	/**
	 * Constructor.
	 * 
	 * @param control
	 *            the {@link FormControl} which to model.
	 */
	public SubmitModel(Submit control) {
		super(control);
		needsLabel = false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return a {@link JButton}
	 */
	public QWidget getNewComponent() {
		QPushButton s = new QPushButton(fc.getLabel().getText());
		s.setIcon(new QIcon(fc.getLabel().getIconURL()));
		s.clicked.connect(this, "actionPerformed()");
		return s;
	}

	/**
	 * a Submit is allways valid.
	 * 
	 * @return <code>true</code>
	 */
	public boolean isValid(QWidget component) {
		// always valid
		return true;
	}

	public void actionPerformed() {
		Input missing = ((Submit) fc).getMissingInputControl();
		QSignalEmitter emitter = QWidget.signalSender();
		if (!(emitter instanceof QWidget))
			return;
		else if (missing != null) {
			String str = "";
			if(missing.getLabel() != null)
				str = " for " + missing.getLabel().getText();
			
			QMessageBox.information((QWidget)emitter, "Input is missing" + str, missing.getAlertString());
		} else if (isValid((QWidget) emitter) && missing == null) {
			Renderer.getInstance().getFormManagement().closeCurrentDialog();
			Renderer.getInstance().handler.submit((Submit) fc);
		}
	}

}
