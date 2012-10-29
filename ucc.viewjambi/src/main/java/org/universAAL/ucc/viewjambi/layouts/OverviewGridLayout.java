/*
	Copyright 2007-2014 FZI, http://www.fzi.de
	Forschungszentrum Informatik - Information Process Engineering (IPE)

	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package org.universAAL.ucc.viewjambi.layouts;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QLayout;
import com.trolltech.qt.gui.QLayoutItemInterface;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QWidgetItem;

/**
 * 
 * @author tzentek - <a href="mailto:zentek@fzi.de">Tom Zentek</a>
 * 
 */

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
		if (arg0 >= 0 && arg0 < layoutItems.size())
			return layoutItems.get(arg0);
		return null;
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
