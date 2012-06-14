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

import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.ucc.plugin.ui.jambi.model.FormControl.SimpleOutputModel;

import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QScrollArea;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QTextEdit.LineWrapMode;

/**
 * @author pabril
 * 
 */
public class SimpleOutputLAF extends SimpleOutputModel {

	/**
	 * Added Scroll pane to contain TextArea
	 */
	QScrollArea sp;

	/**
	 * Enveloped {@link JComponent}
	 */
	QWidget ejc;

	/**
	 * Constructor.
	 * 
	 * @param control
	 *            the {@link SimpleOutput} which to model.
	 */
	public SimpleOutputLAF(SimpleOutput control) {
		super(control);

	}

	/** {@inheritDoc} */
	@Override
	public QWidget getNewComponent() {
		Object content = ((SimpleOutput) fc).getContent();
		QWidget sjc = super.getNewComponent();
		ejc = sjc;
		if (content instanceof String) {
			if (((String) content).length() >= TOO_LONG) {
				sp = new QScrollArea();
				sp.setWidget(sjc);
				sjc = sp;
			}
		}
		return sjc;
	}

	/** {@inheritDoc} */
	public void update() {
		Object content = ((SimpleOutput) fc).getContent();
		if (content instanceof String) {
			if (((String) content).length() >= TOO_LONG) {
				jc = (jc == sp ? ejc : jc);
				QTextEdit ta = (QTextEdit) jc;
				// TODO ta.setAccessibleName(ta.);
				ta.setLineWrapMode(LineWrapMode.WidgetWidth);
				// ta.setWrapStyleWord(true);
				// ta.getAccessibleContext();
				// TODO ta.setFont(ColorLAF.getplain());

				// TODO ta.setBorder(BorderFactory.createEmptyBorder(8, 8, 8,
				// 8));
				// ta.setForeground(ColorLAF.getfont());
			} else {
				QLineEdit tf = (QLineEdit) jc;
				tf.setAccessibleName(tf.text());
				// TODO tf.setFont(ColorLAF.getplain());

				// TODO tf.setPreferredSize(new Dimension(150, 30));
				// TODO tf.setForeground(ColorLAF.getBackMM());
			}
		}
		if (content instanceof Boolean) {
			QCheckBox cb = (QCheckBox) jc;
			cb.setAccessibleName(cb.text());
		}
		super.update();
	}

}
