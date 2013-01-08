package org.universaal.uaalpax.model;

public class UnknownBundleFormatException extends Exception {
	private static final long serialVersionUID = -4834761751202863162L;
	
	public UnknownBundleFormatException() {
	}

	public UnknownBundleFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownBundleFormatException(String message) {
		super(message);
	}

	public UnknownBundleFormatException(Throwable cause) {
		super(cause);
	}
}
