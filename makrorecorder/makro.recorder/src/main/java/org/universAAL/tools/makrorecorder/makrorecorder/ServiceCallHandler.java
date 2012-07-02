package org.universAAL.tools.makrorecorder.makrorecorder;

public interface ServiceCallHandler {
	/**
	 * @param progress between 0 and 1
	 * @param time time in ms elapsed since playback was started
	 */
	public void progressChanged(float progress, long time);
	public void callingFinished(boolean result);
	public void callingCanceled();
}
