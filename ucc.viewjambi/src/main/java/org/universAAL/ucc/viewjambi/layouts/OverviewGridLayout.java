package org.universAAL.ucc.viewjambi.layouts;

import java.util.ArrayList;
import java.util.List;

import org.universAAL.ucc.viewjambi.overview.LabeledIcon;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.qt.gui.QLayoutItem;
import com.trolltech.qt.gui.QLayoutItemInterface;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QWidgetItem;

public class OverviewGridLayout extends QLayout{
	
	private List<QLayoutItemInterface> layoutItems;

	public OverviewGridLayout(QWidget widget){
		super(widget);
		layoutItems = new ArrayList<QLayoutItemInterface>();
	}
	
	public OverviewGridLayout(){
		super();
		layoutItems = new ArrayList<QLayoutItemInterface>();
	}
	
	@Override
	public void addItem(QLayoutItemInterface arg0) {
		layoutItems.add(arg0);
	}
	
	public void addWidget(QWidget widget, int magic)
	         {
	             addChildWidget(widget);
	             addItem(new QWidgetItem(widget));
	         }
	
	@Override
	public int count() {
		return layoutItems.size();
	}

	@Override
	public QLayoutItemInterface itemAt(int arg0) {
		return layoutItems.get(arg0);
	}

	@Override
	public QSize sizeHint() {
		return new QSize(100, 100);
	}

	@Override
	public QLayoutItemInterface takeAt(int arg0) {
		return layoutItems.remove(arg0);
	}
	
	public void setGeometry(QRect rect){
		super.setGeometry(rect);
		doLayout(rect);
	}
	
	public QSize minimumSize(){
		return new QSize(400, 200);
	}
	
	public int doLayout(QRect rect){
		int x = rect.x();
		int y = rect.y();
		int lineHeight = 100;
		
		for(QLayoutItemInterface item: layoutItems){
			QWidget widget = item.widget();
			int nextX = x+100;
			if(nextX > rect.right() && lineHeight > 0){
				x = rect.x();
				y = y + lineHeight;
				nextX = x + 100;
			}
			widget.setGeometry(new QRect(new QPoint(x,y), new QSize(100,100)));
			
			x = nextX;

		}
		return y + lineHeight - rect.y();
		
	}

}
