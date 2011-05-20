package org.istmusic.tools.transformationcommand.handlers;

public class TransformToAdaptationModelHandler extends TransformationHandler {
	static final String TRANSFORMATION_FILENAME = "SmallTest.m2t";
	static final String THIS_BUNDLE_NAME = "org.istmusic.tools.transformationcommand";

	public TransformToAdaptationModelHandler() {
		setFileAndBundleName(TRANSFORMATION_FILENAME, THIS_BUNDLE_NAME);
	}
}
