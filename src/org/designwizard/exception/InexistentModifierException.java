package org.designwizard.exception;

public class InexistentModifierException extends Exception {

	private static final String DEFAULT_MSG = "An invalid modifier was detected.";
	
	public InexistentModifierException(String msg) {
		super(msg);
	}
	
	public InexistentModifierException() {
		this(DEFAULT_MSG);
	}
}
