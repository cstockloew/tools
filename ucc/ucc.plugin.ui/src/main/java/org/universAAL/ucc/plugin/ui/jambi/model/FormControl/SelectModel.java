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

import java.util.ArrayList;
import java.util.List;

import org.universAAL.middleware.ui.rdf.FormControl;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.Select;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.gui.QAbstractItemView;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QTreeWidget;
import com.trolltech.qt.gui.QWidget;

/**
 *
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @see Select
 */
public class SelectModel extends InputModel {

	/**
	 * the selected items
	 */
	protected ArrayList<Object> selected;
    /**
     * Constructor.
     *
     * @param control
     *            the {@link FormControl} which to model.
     */
    public SelectModel(Select control) {
        super(control);
    }

    /**
     * {@inheritDoc}
     *
     * @return either a {@link QListWidget} or a {@link QTreeWidget}
     */
    public QWidget getNewComponent() {
        // XXX add icons to component!
        // TODO use getMaxCardinality and getMinCardinality to get the max and
        // min #ofSelections
        Label[] items = ((Select) fc).getChoices();
        if (!((Select) fc).isMultilevel()) {
            /*
             * Not a tree, then it is a simple select list with multiple
             * selection power.
             */
            QListWidget list = new QListWidget();
            for (Label item : items)
            	list.addItem(item.getText());
            list.itemSelectionChanged.connect(this, "valueChanged()");
            return list;
        } else {
            QTreeWidget jt = new QTreeWidget();
            return jt;
        }
    }
    
    /**
     * Update the selections
     */
    protected void update() {
    	Label[] items = ((Select) fc).getChoices();
        if (jc instanceof QListWidget) {
            /*
             * Not a tree, then it is a simple select list with multiple
             * selection power.
             */
            QListWidget list = (QListWidget) jc;
            list.setSelectionMode(QAbstractItemView.SelectionMode.MultiSelection);
            // list.setSelectedIndex(0);
            // TODO the selected indexES should be defined in the RDF!
            setSelected();
            for (int i = 0; i < items.length; i++) {
            	if (selected.contains(items[i])) {
            		list.setCurrentRow(i);
            	}
            }
            list.itemSelectionChanged.connect(this, "valueChanged()");
        }
        if (jc instanceof QTreeWidget) {
            QTreeWidget jt = (QTreeWidget) jc;
            jt.setEnabled(false);
            jt.setSelectionMode(QAbstractItemView.SelectionMode.MultiSelection);
        }
        super.update();
    }

    @SuppressWarnings("unchecked")
	private void setSelected() {
		Object val = ((Select) fc).getValue();
		if (val instanceof List){
			selected = new ArrayList<Object>((List<Object>) val);
		}
		else if (val instanceof Object[]){
			selected = new ArrayList<Object>();
			for (int i = 0; i < ((Object[]) val).length; i++){
				selected.add(((Object[]) val)[i]);
			}
		}
		else {
			selected = new ArrayList<Object>();
			selected.add(val);
		}
	}

	/**
     * {@inheritDoc}
     */
    public boolean isValid(QWidget component) {
        // TODO check validity (in selection models...)
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void valueChanged() {
    	QSignalEmitter emitter = QWidget.signalSender();
    	if (!(emitter instanceof QListWidget))
    		return;
    	QListWidget list = (QListWidget)emitter;
        if (!((Select) fc).isMultilevel()) {
           	Label[] items = ((Select) fc).getChoices();
        	selected.removeAll(selected);
        	for (int i = 0; i < items.length; i++) {
        		if (list.item(i).isSelected())
        			selected.add(items[i]);
			}
            ((Select) fc).storeUserInput(selected);
        }
    }

    /**
     * the {@link TreeModel} for generating a {@link QTreeWidget}.
     *
     * @author amedrano
     *
     */
   /* protected class SelectionTreeModel implements TreeModel {

        Label[] root;

        public Object getRoot() {
            root = ((Select) fc).getChoices();
            if (root.length == 1) {
                return root[1];
            }
            else {
                return root;
            }
        }

        public Object getChild(Object parent, int index) {
            if (parent == root) {
                return root[index];
            }
            else {
                return ((ChoiceList) parent).getChildren()[index];
            }
        }

        public int getChildCount(Object parent) {
            if (parent == root) {
                return root.length;
            }
            else {
                return ((ChoiceList) parent).getChildren().length;
            }
        }

        public boolean isLeaf(Object node) {
            if (node == root) {
                return root.length == 0;
            }
            else {
                return node instanceof ChoiceItem;
            }
        }

        public void valueForPathChanged(TreePath path, Object newValue) {
            // XXX editable trees?
        }

        public int getIndexOfChild(Object parent, Object child) {
            Label[] children = ((ChoiceList) parent).getChildren();
            int i = 0;
            while (i < children.length && children[i] != child) 
            { i++; }
            return i;
        }

        public void addTreeModelListener(TreeModelListener l) {
        }

        public void removeTreeModelListener(TreeModelListener l) {
        }

    }*/

    /**
     * the selection model for the multple selection QTreeWidget.
     *
     * @author amedrano
     */
   /* private class MultipleTreeSelectionModel extends DefaultTreeSelectionModel {
        **
         * serial version for {@link Serializable} objects.
         *
        private static final long serialVersionUID = 1L;
        *
         * TODO Model the selection! and gather Tree input use getMaxCardinality
         * and getMinCardinality to get the max and min #ofSelections
         *
    }*/
}
