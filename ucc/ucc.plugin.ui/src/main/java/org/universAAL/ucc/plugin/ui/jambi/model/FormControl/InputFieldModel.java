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

import java.util.Locale;

import org.universAAL.middleware.ui.rdf.Input;
import org.universAAL.middleware.ui.rdf.InputField;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QWidget;

/**
 * ImputField Model, it condenses the view and controller parts of the MVC
 * methodology.
 * 
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @see InputField
 */
public class InputFieldModel extends InputModel {

    /**
     * Constructor.
     * 
     * @param control
     *            de {@link InputField} which to model.
     */
    public InputFieldModel(InputField control) {
	super(control);
    }

    /**
     * the representation for InputField can either be
     * <ul>
     * <li>a {@link JCheckBox} if the {@link InputField#getValue()} is a boolean
     * type
     * <li>a {@link JTextField} if the {@link InputField#getValue()} is a String
     * and not secret
     * <li>a {@link JPasswordField} if the {@link InputField#getValue()} is
     * String and it is secret
     * <li>a ?? if the {@link InputField#getValue()} is a XMLGregorianCalendar
     * <li>a ?? if the {@link InputField#getValue()} is a Duration
     * <li>a ?? if the {@link InputField#getValue()} is a Integer
     * <li>a ?? if the {@link InputField#getValue()} is a Long
     * <li>a ?? if the {@link InputField#getValue()} is a Float
     * <li>a ?? if the {@link InputField#getValue()} is a Double
     * <li>a {@link JComboBox} if the {@link InputField#getValue()} is a Locale
     * </ul>
     * 
     * @return {@inheritDoc}
     */
    public QWidget getNewComponent() {
	int maxLength = ((InputField) fc).getMaxLength();
	InputField inFi = (InputField) fc;

	if (inFi.isOfBooleanType()) {
	    /*
	     * the input type is boolean therefore it can be represented as a
	     * checkbox.
	     */
	    QCheckBox cb = new QCheckBox(inFi.getLabel().getText());
	    cb.setIcon(new QIcon(inFi.getLabel().getIconURL()));
	    needsLabel = false;
	    cb.toggled.connect(this, "stateChanged(Boolean)");
	    return cb;
	}
	if (inFi.getValue() instanceof String && !inFi.isSecret()) {
	    /*
	     * the input requested is a normal text field
	     */
		QLineEdit tf;
	    if (maxLength > 0) {
		tf = new QLineEdit();
		tf.setMaxLength(maxLength);
	    } else {
		tf = new QLineEdit();
	    }
	    tf.cursorPositionChanged.connect(this, "caretUpdate(Integer,Integer)");
	    return tf;
	}
	if (inFi.getValue() instanceof String && inFi.isSecret()) {
	    /*
	     * the input requested is a password field
	     */
	    QLineEdit pf;
	    if (maxLength > 0) {
		pf = new QLineEdit();
		pf.setMaxLength(maxLength);
	    } else {
		pf = new QLineEdit();
	    }
	    pf.setEchoMode(QLineEdit.EchoMode.Password);
	    pf.cursorPositionChanged.connect(this, "caretUpdate(Integer,Integer)");
	    return pf;
	}
	// if (inFi.getValue() instanceof XMLGregorianCalendar) {}
	// if (inFi.getValue() instanceof Duration) {}
	/*
	 * if (inFi.getValue() instanceof Integer || inFi.getValue() instanceof
	 * Long) {
	 * 
	 * } if (inFi.getValue() instanceof Float || inFi.getValue() instanceof
	 * Double) {
	 * 
	 * }
	 */
	if (inFi.getValue() instanceof Locale) {
	    QComboBox lcb = new QComboBox();
	    lcb.currentIndexChanged.connect(this, "actionPerformed(Integer)");
	    return lcb;
	}
	return null;
    }

    /**
     * Updating the InputField
     */
    protected void update() {
	Object initVal = fc.getValue();
	if (jc instanceof QCheckBox) {
	    ((QCheckBox) jc).setChecked(((Boolean) initVal).booleanValue());
	}
	if (jc instanceof QLineEdit) {
	    if (initVal != null) {
		((QLineEdit) jc).setText(initVal.toString());
	    }
	}
	if (jc instanceof QComboBox && initVal instanceof Integer) {
	    ((QComboBox) jc).setCurrentIndex((Integer)initVal);
	}
	super.update();
    }

    /** {@inheritDoc} */
    public boolean isValid(QWidget component) {
	// TODO check input length!
	return true;
    }

    /**
     * when a checkbox is pressed there will be a input event published.
     * 
     * @param e
     *            the {@link ChangeEvent} to listen to.
     */
    public void stateChanged(Boolean value) {
	/*
	 * Update Model if valid
	 */
    QSignalEmitter emitter = QWidget.signalSender();
    if (!(emitter instanceof QCheckBox))
    	return;
    QCheckBox edit = (QCheckBox)emitter;
	if (isValid(edit)) {
	    ((Input) fc).storeUserInput(Boolean.valueOf((edit.isChecked())));
	}
    }

    /**
     * Input events will be published each time the user types something in the
     * text field.
     * 
     * @param e
     *            the {@link CaretEvent} to listen to.
     */
    public void caretUpdate(Integer start, Integer end) {
	/*
	 * Update Model if valid
	 */
    QSignalEmitter emitter = QWidget.signalSender();
    if (!(emitter instanceof QLineEdit))
       	return;
    QLineEdit tf = (QLineEdit)emitter;
	InputField inFi = (InputField) fc;
	if (isValid(tf)) {
	    try {
		    inFi.storeUserInput(tf.text());
	    } catch (NullPointerException e1) {
		inFi.storeUserInput("");
	    }
	}
    }

    /**
     * Input events will be published each time the user changes the status of
     * an Input
     */
    public void actionPerformed(Integer value) {
    QSignalEmitter emitter = QWidget.signalSender();
	if (emitter instanceof QComboBox) {
	    QComboBox cb = (QComboBox) emitter;
	    InputField inFi = (InputField) fc;
	    inFi.storeUserInput(cb.currentText());
	}

    }

}
