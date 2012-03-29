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

import javax.swing.JSlider;
import javax.swing.JSpinner;

import org.universAAL.middleware.ui.rdf.Range;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.gui.QSlider;
import com.trolltech.qt.gui.QSpinBox;
import com.trolltech.qt.gui.QWidget;

/**
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @see Range
 */
public class RangeModel extends InputModel {

	/**
	 * Threshold where to decide if it should be rendenred as {@link JSpinner} or
	 * as {@link JSlider}.
	 */
    private static final int SPINNER_SLIDER_THRESHOLD = 25;
    
    /**
     * Minimum value by the model.
     */
	private int mnValue;
	
	/**
     * Maximum value by the model.
     */
	private int mxValue;
	
	/**
     * Actual value by the model.
     */
	private int initValue;

    /**
     * Constructor.
     * @param control the {@link Range} which to model.
     */
    public RangeModel(Range control) {
        super(control);
        Comparable<?> min_Value = ((Range) fc).getMinValue();
        mnValue = ((Integer) min_Value).intValue();
        Comparable<?> max_Value = ((Range) fc).getMaxValue();
        mxValue = ((Integer) max_Value).intValue();
        initValue = ((Integer) fc.getValue()).intValue();
    }

    /**
     * Ranges can yield a {@link JSpinner} if the specified range
     * is less than a threshold, or it can also be {@link JSlider}.
     * @return {@inheritDoc}
     */
    public QWidget getNewComponent() {
        if ((mxValue - mnValue) < SPINNER_SLIDER_THRESHOLD) {
            QSpinBox spinner = new QSpinBox();
            spinner.setValue(initValue);
            spinner.setMaximum(mxValue); 
            spinner.setMinimum(mnValue);
            spinner.setSingleStep(((Range) fc).getStep().intValue());
            spinner.valueChanged.connect(this, "stateChanged(Integer)");
            return spinner;
        }
        else {
            QSlider slider = new QSlider();
            slider.setMinimum(mnValue);
            slider.setMaximum(mxValue);
            slider.setValue(initValue);
            slider.valueChanged.connect(this, "stateChanged(Integer)");
            return slider;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isValid(QWidget component) {
        // Swing makes sure it's all ways valid
        // XXX check the above affirmation
        return true;
    }

    /**
     * when a range is change, it will produce an input event
     * {@inheritDoc}
     */
    public void stateChanged(Integer curValue) {
        int value;
        // Check UserInput Type is Integer!
        QSignalEmitter emitter = QWidget.signalSender();
  
        if (emitter instanceof QSpinBox) {
            value = curValue.intValue();
        }
        else {
            value = ((QSlider) emitter).value();
        }
        ((Range) fc).storeUserInput(new Integer(value));
    }

}
