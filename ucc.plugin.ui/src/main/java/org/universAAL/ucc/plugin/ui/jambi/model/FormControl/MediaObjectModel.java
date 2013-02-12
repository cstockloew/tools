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

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.universAAL.middleware.ui.rdf.MediaObject;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

/**
 * 
 * @author <a href="mailto:amedrano@lst.tfo.upm.es">amedrano</a>
 * @see MediaObject
 */
public class MediaObjectModel extends OutputModel {

    /**
     * Constructor.
     * 
     * @param control
     *            the {@link MediaObject} which to model.
     */
    public MediaObjectModel(MediaObject control) {
	super(control);
    }

    /**
     * The {@link JComponent} returned is a {@link JLabel}. in future versions
     * it may accommodate other components for videos, audio and other media
     * files.
     * 
     * @return {@inheritDoc}
     */
    public QWidget getNewComponent() {
	return new QLabel(fc.getLabel().getText());
    }

    /**
     * Updating the {@link JLabel}
     */
    protected void update() {

	MediaObject mo = (MediaObject) fc;
	if (mo.getContentType().startsWith("image")) {
		QLabel jl = (QLabel) jc;
	    QImage icon = new QImage(mo.getContentURL());
	    jl.setPixmap(QPixmap.fromImage(icon));
	    jl.setAccessibleName(fc.getURI());
	    int x, y;
	    x = mo.getResolutionPreferredX();
	    y = mo.getResolutionPreferredY();
	    if (x != 0 && y != 0) {
		jl.setMinimumSize(new QSize(x, y));
	    }
	    x = mo.getResolutionMaxX();
	    y = mo.getResolutionMaxY();
	    if (x != 0 && y != 0) {
		jl.setMaximumSize(new QSize(x, y));
	    }
	    x = mo.getResolutionMinX();
	    y = mo.getResolutionMinY();
	    if (x != 0 && y != 0) {
		jl.setMinimumSize(new QSize(x, y));
	    }
	}
    }

    /*
     * XXX: Media Type for images, audio, video) URL Parser: know
     * where to locate the resource (DONE) - in jar - in file system - in config
     * dir - in remote repo (like http) - other cases? use VFS.. Media Cache :
     * once located resources store and index them in config dir for faster
     * location.
     * 
     * Use Locator and cache for the other Icons (using IconFactory)
     */

}