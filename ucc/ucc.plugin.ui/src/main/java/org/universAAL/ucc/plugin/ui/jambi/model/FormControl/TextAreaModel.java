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
/**
 *
 */
package org.universAAL.ucc.plugin.ui.jambi.model.FormControl;

import org.universAAL.middleware.ui.rdf.FormControl;
import org.universAAL.middleware.ui.rdf.TextArea;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QWidget;

/**
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @see TextArea
 */
public class TextAreaModel extends InputModel {

    /**
     * Constructor.
     * @param control
     *      the {@link FormControl} which to model.
     */
    public TextAreaModel(TextArea control) {
        super(control);
    }

    /**
     * {@inheritDoc}
     * @return
     *      a {@link JTextArea}.
     */
    public QWidget getNewComponent() {
    	QTextEdit ta = new QTextEdit();
    	ta.cursorPositionChanged.connect(this, "caretUpdate()");
        //ta.addCaretListener(this);
        return ta;
    }

    /**
     * {@inheritDoc}
     */
    protected void update() {
    	QTextEdit ta = (QTextEdit) jc;
        ta.setText((String) fc.getValue());
        super.update();
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isValid(QWidget component) {
        /*
         *  TODO Check Validity!
         *  length
         */
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void caretUpdate() {
    	QSignalEmitter emitter = QWidget.signalSender();
    	if (!(emitter instanceof QTextEdit))
    		return;
    	QTextEdit edit = (QTextEdit)emitter;	
        // update Model if valid
        if (isValid(edit)) {
            ((TextArea) fc).storeUserInput(edit.toPlainText());
        }
    }
}
