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

import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.ui.rdf.FormControl;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.ucc.plugin.ui.jambi.ModelMapper;
import org.universAAL.ucc.plugin.ui.jambi.model.LabelModel;
import org.universAAL.ucc.plugin.ui.jambi.model.Model;

import com.trolltech.qt.gui.QBoxLayout;
import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.qt.gui.QTabWidget;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QBoxLayout.Direction;

/**
 * 
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @see Group
 */
public class GroupModel extends Model {
	boolean isStandardButtons = false;

	/**
	 * Constructor.
	 * 
	 * @param control
	 *            the {@link Group} which to model.
	 */
	public GroupModel(Group control) {
		super(control);
	}

	public GroupModel(Group control, boolean b) {
		super(control);
		isStandardButtons = b;
	}

	/**
	 * The {@link JComponent} returned may be either
	 * <ul>
	 * <li>a {@link QFrame}
	 * <li>a {@link JTabbedPane}
	 * </ul>
	 * 
	 * depending on the complexity (and other factors) of the group
	 * 
	 * @return {@inheritDoc}
	 * */
	public QWidget getNewComponent() {
		if (isStandardButtons)
			return boxPanel();

		LevelRating complexity = ((Group) fc).getComplexity();
		if (complexity == LevelRating.none || ((Group) fc).isRootGroup()) {
			return simplePannel();
		}
		if (complexity == LevelRating.low) {
			return simplePannel();
		}
		if (complexity == LevelRating.middle) {
			return tabbedPanel();
		}
		if (complexity == LevelRating.high) {
			return tabbedPanel();
		}
		if (complexity == LevelRating.full) {
			return tabbedPanel();
		}
		return null;
	}

	/** {@inheritDoc} */
	public boolean isValid(QWidget component) {
		// TODO: only valid if ALL elements are valid!
		return true;
	}

	/**
	 * create a simple panel with the children in it
	 * 
	 * @return a {@link QFrame} with all the group's children.
	 */
	protected QFrame simplePannel() {
		QFrame pane = new QFrame();
		pane.setLayout(new QFormLayout());
		return pane;
	}

	protected QFrame boxPanel() {
		QFrame pane = new QFrame();
		pane.setLayout(new QHBoxLayout());
		return pane;
	}

	/**
	 * Update a simple panel with the children in it
	 */
	protected void updateSimplePanel() {
		/*
		 * a Simple Group containing FormControls or one of the main Groups go into simple panes
		 */
		jc.setAccessibleName(fc.getURI());
		for (int i = 0; i < jc.layout().children().size(); i++)
			jc.layout().children().get(i).setParent(null);

		FormControl[] children = ((Group) fc).getChildren();
		for (int i = 0; i < children.length; i++) {
			addComponentTo(children[i], jc);
		}

		// ((QBoxLayout) jc.layout()).addStretch();
	}

	/**
	 * create a tabbed panel with diferent groups in different pannels.
	 * 
	 * @return a {@link JTabbedPane} with children groups as panels
	 */
	protected QTabWidget tabbedPanel() {
		QTabWidget tp = new QTabWidget();
		tp.setLayout(new QVBoxLayout());
		return tp;
	}

	/**
	 * Update a tabbed panel with diferent groups in different pannels.
	 */
	protected void updateTabbedPanel() {
		QTabWidget tp = (QTabWidget) jc;
		tp.clear();
		FormControl[] children = ((Group) fc).getChildren();
		QWidget pane;
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Group) {
				pane = getComponentFrom(children[i]);
			} else {
				pane = new QWidget();
				addComponentTo(children[i], pane);
			}
			tp.addTab(pane, new QIcon(children[i].getLabel().getIconURL()), children[i].getLabel().getText());
		}
	}

	/**
	 * Override of Update, so it updates correctly the {@link GroupModel}
	 */
	protected void update() {
		if (jc instanceof QTableWidget) {
			updateTabbedPanel();
		} else if (jc instanceof QFrame) {
			updateSimplePanel();
		}

		super.update();
	}

	/**
	 * check whether it is the submit root group.
	 * 
	 * @return true is it is.
	 */
	public boolean isTheSubmitGroup() {
		return ((Group) fc).isRootGroup() && isInSubmitGroup();
	}

	/**
	 * check whether it is the system root group.
	 * 
	 * @return true is it is.
	 */
	public boolean isTheMainGroup() {
		return ((Group) fc).isRootGroup() && isInStandardGroup();
	}

	/**
	 * check whether it is the io root group.
	 * 
	 * @return true is it is.
	 */
	public boolean isTheIOGroup() {
		return ((Group) fc).isRootGroup() && isInIOGroup();
	}

	/**
	 * Access to the Model mapper. it will load the {@link QWidget} for a given {@link FormControl} which is a child of
	 * the current group.
	 * 
	 * @param fc
	 *            the child from which to obtain it's model and {@link QWidget}
	 * @return the {@link QWidget} build by the {@link Model} of the child
	 */
	private QWidget getComponentFrom(FormControl fc) {

		return ModelMapper.getModelFor(fc).getComponent();
	}

	/**
	 * Access to the Model mapper. it will load the {@link QWidget} for a given {@link FormControl} which is a child of
	 * the current group, and add it to a {@link Container}.
	 * 
	 * @param fc
	 *            the child from which to obtain it's model and {@link QWidget}
	 * @param c
	 *            the {@link Container} to which to add the {@link QWidget}
	 */
	private void addComponentTo(FormControl fc, QWidget c) {
		Model m = ModelMapper.getModelFor(fc);
		QWidget jc = m.getComponent();
		QLabel label = null;

		if (jc != null) {
			if (fc.getLabel() != null) {
				LabelModel labelmodel = ModelMapper.getModelFor(fc.getLabel());
				if (labelmodel.hasInfo() && m.needsLabel())
					label = labelmodel.getComponent();
			}

			if (c.layout() instanceof QFormLayout) {
				if (label != null)
					((QFormLayout) c.layout()).addRow(label, jc);
				else
					((QFormLayout) c.layout()).addRow(jc);
			} else {
				if(label == null)
					c.layout().addWidget(jc);
				else {
					QWidget w = new QWidget();
					w.setLayout(new QHBoxLayout());
					w.layout().addWidget(label);
					w.layout().addWidget(jc);
					c.layout().addWidget(w);
				}
			}
		}
	}

}
