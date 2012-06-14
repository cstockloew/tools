package org.universAAL.ucc.plugin.ui.jambi.defaultLookAndFeel;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.qt.gui.QLayoutItemInterface;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QWidgetItem;

public class BorderLayout extends QLayout {
	public static final String NORTH = java.awt.BorderLayout.NORTH;
	public static final String SOUTH = java.awt.BorderLayout.SOUTH;
	public static final String EAST = java.awt.BorderLayout.EAST;
	public static final String WEST = java.awt.BorderLayout.WEST;
	public static final String CENTER = java.awt.BorderLayout.CENTER;

	private static final int MinimumSize = 1;
	private static final int SizeHint = 2;

	private static class ItemWrapper {
		ItemWrapper(QLayoutItemInterface i, String p) {
			item = i;
			position = p;
		}

		QLayoutItemInterface item;
		String position;
	};

	private int spacing;

	private List<ItemWrapper> list = new ArrayList<ItemWrapper>();

	public BorderLayout(QWidget parent, int margin, int spacing) {
		super(parent);
		setMargin(margin);
		setSpacing(spacing);
	}

	public BorderLayout(int spacing) {
		setSpacing(spacing);
	}

	public int getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

	public void addWidget(QWidget widget, String position) {
		add(new QWidgetItem(widget), position);
	}

	@Override
	public Qt.Orientations expandingDirections() {
		return new Qt.Orientations(Qt.Orientation.Horizontal,
				Qt.Orientation.Vertical);
	}

	@Override
	public boolean hasHeightForWidth() {
		return false;
	}

	@Override
	public int count() {
		return list.size();
	}

	@Override
	public QLayoutItemInterface itemAt(int index) {
		if (index < 0 || index >= list.size())
			return null;

		ItemWrapper wrapper = list.get(index);
		if (wrapper != null)
			return wrapper.item;
		else
			return null;
	}

	@Override
	public QSize minimumSize() {
		return calculateSize(MinimumSize);
	}

	@Override
	public void setGeometry(QRect rect) {
		ItemWrapper center = null;
		int eastWidth = 0;
		int westWidth = 0;
		int northHeight = 0;
		int southHeight = 0;
		int centerHeight = 0;
		int i;

		super.setGeometry(rect);

		for (i = 0; i < list.size(); ++i) {
			ItemWrapper wrapper = list.get(i);
			QLayoutItemInterface item = wrapper.item;
			String position = wrapper.position;

			new QRect(item.geometry().x(), item.geometry().y(), rect.width(),
					item.sizeHint().height());

			if (position == NORTH) {
				item.setGeometry(new QRect(rect.x(), northHeight, rect.width(),
						item.sizeHint().height()));
				// TODO
				northHeight += item.geometry().height(); /* + spacing() */
				;
			} else if (position == SOUTH) {
				item.setGeometry(new QRect(item.geometry().x(), item.geometry()
						.y(), rect.width(), item.sizeHint().height()));

				southHeight += item.geometry().height() /* + spacing() TODO */;

				item.setGeometry(new QRect(rect.x(), rect.y() + rect.height()
						- southHeight + getSpacing(), item.geometry().width(),
						item.geometry().height()));
			} else if (position == CENTER) {
				center = wrapper;
			}
		}

		centerHeight = rect.height() - northHeight - southHeight;

		for (i = 0; i < list.size(); ++i) {
			ItemWrapper wrapper = list.get(i);
			QLayoutItemInterface item = wrapper.item;
			String position = wrapper.position;

			if (position == WEST) {
				item.setGeometry(new QRect(rect.x() + westWidth, northHeight,
						item.sizeHint().width(), centerHeight));

				westWidth += item.geometry().width() + getSpacing();
			} else if (position == EAST) {
				item.setGeometry(new QRect(item.geometry().x(), item.geometry()
						.y(), item.sizeHint().width(), centerHeight));

				eastWidth += item.geometry().width() + getSpacing();

				item.setGeometry(new QRect(rect.x() + rect.width() - eastWidth
						+ getSpacing(), northHeight, item.geometry().width(),
						item.geometry().height()));
			}
		}

		if (center != null)
			center.item.setGeometry(new QRect(westWidth, northHeight, rect
					.width()
					- eastWidth - westWidth, centerHeight));
	}

	@Override
	public QSize sizeHint() {
		return calculateSize(SizeHint);
	}

	@Override
	public QLayoutItemInterface takeAt(int index) {
		if (index >= 0 && index < list.size()) {
			ItemWrapper layoutStruct = list.remove(index);
			return layoutStruct.item;
		}
		return null;
	}

	public void add(QLayoutItemInterface item, String position) {
		list.add(new ItemWrapper(item, position));
	}

	private QSize calculateSize(int sizeType) {
		QSize totalSize = new QSize();

		for (int i = 0; i < list.size(); ++i) {
			ItemWrapper wrapper = list.get(i);
			String position = wrapper.position;
			QSize itemSize;

			if (sizeType == MinimumSize)
				itemSize = wrapper.item.minimumSize();
			else
				// (sizeType == SizeHint)
				itemSize = wrapper.item.sizeHint();

			if (position == NORTH || position == SOUTH || position == CENTER)
				totalSize.setHeight(totalSize.height() + itemSize.height());

			if (position == WEST || position == EAST || position == CENTER)
				totalSize.setWidth(totalSize.width() + itemSize.width());
		}
		return totalSize;
	}

	@Override
	public void addItem(QLayoutItemInterface item) {
		add(item, WEST);
	}

}
