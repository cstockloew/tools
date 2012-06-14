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

import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.ucc.plugin.ui.jambi.model.FormControl.GroupModel;

import com.trolltech.qt.gui.QTableWidget;

/**
 * @author pabril
 * 
 */
public class GroupLAF extends GroupModel {

	/**
	 * Constructor.
	 * 
	 * @param control
	 *            the {@link Group} which to model
	 */
	public GroupLAF(Group control) {
		super(control);
	}

	/** {@inheritDoc} */
	public void update() {
		super.update();
		if (jc instanceof QTableWidget) {
			/*
			 * Tabbed group
			 */
			// jc.getAccessibleContext();
			// TODO
			// jc.setFont(ColorLAF.getplain());
		} else if (!((Group) fc).isRootGroup()) {
			/*
			 * simple group control
			 */
			String label;
			if (fc.getLabel() != null) {
				label = fc.getLabel().getText();
			} else {
				label = "";
			}
			// Border empty = BorderFactory.createEmptyBorder(5,5,5,5);
			// TODO
			// Border line =
			// BorderFactory.createLineBorder(ColorLAF.getOrange());
			// TitledBorder title;
			// title = BorderFactory.createTitledBorder
			// (line, label, 0, 0,
			// ColorLAF.getbold(), ColorLAF.getborderLineMM());
			// jc.setBorder(title);
			needsLabel = false;
			// XXX try add icon
		}

		// jc.setLayout(new BoxLayout(jc, BoxLayout.PAGE_AXIS));
	}
}
