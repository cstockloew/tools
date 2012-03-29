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

import java.util.List;

import org.universAAL.middleware.ui.rdf.Repeat;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QScrollArea;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 * Helper Class just to render RepeatTables.
 * @author amedrano
 *
 */
public class RepeatModelTable extends RepeatModel {


	/**
	 * The table component
	 */
	protected QTableWidget tableComponent;

	/**
	 * Constructor
	 * @param control
	 */
	public RepeatModelTable(Repeat control) {
		super(control);
		// TODO Auto-generated constructor stub
	}

	/** {@inheritDoc}*/
	public QWidget getNewComponent() {
		/* TODO
		 * Representation of a table
		 ********************************************
		 * use:
		 *  boolean         listAcceptsNewEntries()
		 *  boolean         listEntriesDeletable()
		 *  boolean         listEntriesEditable()
		 *  int             getSelectionIndex()
		 *  FormControl     getSearchableField() (add a listener to this component)
		 * to configure the table accordingly!!
		 */
		/*
		 * check:
		 * http://download.oracle.com/javase/tutorial/uiswing/components/table.html
		 */
		Repeat r = (Repeat)fc;

		tableComponent = new QTableWidget();
		QScrollArea scrollPane = new QScrollArea(tableComponent);
		//???tableComponent.setFillsViewportHeight(true);
		
		QFrame buttonPanel = new QFrame();
		QVBoxLayout layout = new QVBoxLayout();
		
		if (r.listAcceptsNewEntries()) {
			layout.addWidget(new AddTableButton());
		}
		if (r.listEntriesDeletable()) {
			layout.addWidget(new DeleteTableButton());
		}
		if (r.listEntriesEditable()) {
			layout.addWidget(new UpTableButton());
			layout.addWidget(new DownTableButton());
		}
		buttonPanel.setLayout(layout);

		QFrame pannelWithAll = new QFrame();
		QHBoxLayout hlayout = new QHBoxLayout();
		hlayout.addWidget(scrollPane);
		hlayout.addWidget(buttonPanel);
		pannelWithAll.setLayout(hlayout);
		return pannelWithAll;
	}

	/** {@inheritDoc}*/
	protected void update() {
		// TODO Auto-generated method stub

	}

	/*public class RepeatTableModel extends AbstractTableModel {

		
		private static final long serialVersionUID = 8263449027626068414L;
		private FormControl[][] table;

		public RepeatTableModel() {
			table = getTable();
		}

		public int getRowCount() {
			return table.length;
		}

		public int getColumnCount() {
			return table[0].length;
		}

		public String getColumnName(int columnIndex) {
			return table[0][columnIndex].getLabel().getText();
		}

		public Class getColumnClass(int columnIndex) {
			Class colClass = table[0][columnIndex].getValue().getClass();
			for (int i = 1; i < getRowCount();i++) {
				while (!colClass.isAssignableFrom(table[i][columnIndex].getValue().getClass())) {
					//not a subclass (or equal) of colClass
					colClass = colClass.getSuperclass();
				}
			}
			return null;
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return (table[rowIndex][columnIndex] instanceof Input)
					&& ((Repeat) fc).listEntriesEditable();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return table[rowIndex][columnIndex].getValue();
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			((Input) table[rowIndex][columnIndex]).storeUserInput(aValue);
			//TODO Check Validity!
		}
	}*/

	/**
	 * Class representing the Add button for Tables.
	 * @author amedrano
	 */
	public class AddTableButton extends QPushButton {

		/**
		 * Java Serializer Variable
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor for Add button
		 */
		public AddTableButton() {
			super();
			this.clicked.connect(this, "actionPerformed()");
			this.setAccessibleName(fc.getURI()+"_Add");
		}

		/** {@inheritDoc}*/
		public void actionPerformed() {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Class representing the Delete button for Tables.
	 * @author amedrano
	 */
	public class DeleteTableButton extends QPushButton {

		/**
		 * Java Serializer Variable
		 */
		private static final long serialVersionUID = 1L;

		
		/**
		 * Constructor for Remove Button
		 */
		public DeleteTableButton() {
			super();
			this.clicked.connect(this, "actionPerformed()");
			this.setAccessibleName(fc.getURI()+"_Delete");
		}
		
		/** {@inheritDoc}*/
		public void actionPerformed() {
			Repeat r = (Repeat) fc;
			List<QModelIndex> list = tableComponent.selectionModel().selectedRows();
			for (int i = 0; i < list.size(); i++) {
				r.setSelection(list.get(i).row());
				r.removeSelection();
				tableComponent.removeRow(list.get(i).row());
			}
		}
	}
	
	/**
	 * Class representing the Up button for Tables.
	 * @author amedrano
	 */
	public class UpTableButton extends QPushButton {

		/**
		 * Java Serializer Variable
		 */
		private static final long serialVersionUID = 1L;

		
		/**
		 * Constructor for Remove Button
		 */
		public UpTableButton() {
			super();
			this.clicked.connect(this, "actionPerformed()");
			this.setAccessibleName(fc.getURI()+"_Up");
		}
		
		/** {@inheritDoc}*/
		public void actionPerformed() {
			Repeat r = (Repeat) fc;
			List<QModelIndex> list = tableComponent.selectionModel().selectedRows();
			for (int i = 0; i < list.size(); i++) {
				r.setSelection(list.get(i).row());
				r.moveSelectionUp();
			}
		}
	}
	
	/**
	 * Class representing the Down button for Tables.
	 * @author amedrano
	 */
	public class DownTableButton extends QPushButton {

		/**
		 * Java Serializer Variable
		 */
		private static final long serialVersionUID = 1L;

		
		/**
		 * Constructor for Remove Button
		 */
		public DownTableButton() {
			super();
			this.clicked.connect(this, "actionPerformed()");
			this.setAccessibleName(fc.getURI()+"_Down");
		}
		
		/** {@inheritDoc}*/
		public void actionPerformed() {
			Repeat r = (Repeat) fc;
			List<QModelIndex> list = tableComponent.selectionModel().selectedRows();
			for (int i = 0; i < list.size(); i++) {
				r.setSelection(list.get(i).row());
				r.moveSelectionDown();
			}

		}
	}
}
