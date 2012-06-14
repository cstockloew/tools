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

import org.universAAL.middleware.ui.rdf.TextArea;
import org.universAAL.ucc.plugin.ui.jambi.model.FormControl.TextAreaModel;

import com.trolltech.qt.core.Qt.FocusPolicy;
import com.trolltech.qt.gui.QScrollArea;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QTextEdit.LineWrapMode;

/**
 * @author pabril
 * 
 */
public class TextAreaLAF extends TextAreaModel {

	/**
	 * {@link JScrollPane} around the {@link JTextArea};
	 */
	// QScrollArea sp;

	/**
	 * Enveloped {@link JComponent}
	 */
	// QWidget ejc;

	/**
	 * Constructor.
	 * 
	 * @param control
	 *            the {@link TextArea} which to model.
	 */
	public TextAreaLAF(TextArea control) {
		super(control);
	}

	/** {@inheritDoc} */
//	public QWidget getNewComponent() {
//		ejc = super.getNewComponent();
//		sp = new QScrollArea();
//		sp.setWidget(ejc);
//		return sp;
//	}
	
	/** {@inheritDoc} */
	public void update() {
//		jc = (jc == sp ? ejc : jc);
		super.update();
		String initialValue = (String) fc.getValue();
		QTextEdit ta = (QTextEdit) jc;
		
		// TODO
		// ta.setRows(10);
		// ta.setColumns(15);
		ta.setAccessibleName(initialValue);
		ta.setLineWrapMode(LineWrapMode.WidgetWidth);
		// TODO ta.setWrapStyleWord(true);
		// sp.setFocusPolicy(FocusPolicy.StrongFocus);
		
		// TODO
		// ta.setFont(ColorLAF.getplain());
		// ta.setForeground(ColorLAF.getfont());
	}
}
