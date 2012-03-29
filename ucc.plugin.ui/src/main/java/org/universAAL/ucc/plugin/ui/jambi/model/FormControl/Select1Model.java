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

import org.universAAL.middleware.ui.rdf.ChoiceItem;
import org.universAAL.middleware.ui.rdf.FormControl;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select;
import org.universAAL.middleware.ui.rdf.Select1;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QTreeWidget;
import com.trolltech.qt.gui.QWidget;

/**
 *
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @see Select1
 */
public class Select1Model extends SelectModel {

    /**
     * constructor.
     * @param control
     *         the {@link FormControl} which this model represents.
     */
    public Select1Model(Select1 control) {
        super(control);
    }

    /**
     * {@inheritDoc}
     * @return either a {@link JTree} or a {@link JComboBox}
     */
    public QWidget getNewComponent() {
        if (!((Select) fc).isMultilevel()) {
            Label[] items = ((Select1) fc).getChoices();
            QComboBox cb = new QComboBox(); 
            for (Label item : items)
            	cb.addItem(item.getText());
            cb.currentIndexChanged.connect(this, "actionPerformed(Integer)");
            return cb;
        } else {
            QTreeWidget jt = new QTreeWidget(/*???new SelectionTreeModel()*/);
            return jt;
        }
    }

    /**
     * Update the {@link JComponent}
     */
    protected void update() {
    	//XXX add icons to component!
        if (!((Select) fc).isMultilevel()) {
            Label[] items = ((Select1) fc).getChoices();
            QComboBox cb = (QComboBox) jc;
            for (int i = 0; i < items.length; i++) {
                if (((ChoiceItem) items[i]).getValue()
                        == fc.getValue()) {
                    cb.setCurrentIndex(i);
                }
            }
            cb.setEditable(true);
        } else {
        	QTreeWidget jt = (QTreeWidget) jc;
            jt.setEnabled(false);
            /*???jt.setSelectionModel(new SingleTreeSelectionModel());*/
        }
        super.update();
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isValid(QWidget component) {
        // TODO check validity.
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void actionPerformed(Integer index) {
        if (!((Select) fc).isMultilevel()) {
            int i = index.intValue();
            ((Select1) fc).storeUserInput(
                    ((ChoiceItem) ((Select1) fc).getChoices()[i]).getValue());
        }
    }
}
