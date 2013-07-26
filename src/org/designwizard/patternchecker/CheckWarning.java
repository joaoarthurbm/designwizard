package org.designwizard.patternchecker;

import org.designwizard.design.ClassNode;

public class CheckWarning {

	private String message;
	private String className;
	private String patternName;
	private String fimDeLinha;
	
	public CheckWarning(String message, String className, String checkerName) {
		this.message = message;
		this.className = className;
		this.patternName = checkerName;
		this.fimDeLinha = System.getProperty("line.separator");
	}

	public CheckWarning(String message, ClassNode clazz, 
			String checkerName) {
		this.message = message;
		this.patternName = checkerName;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPatternCheckerName() {
		return patternName;
	}

	public void setPatternCheckerName(String patternCheckerName) {
		this.patternName = patternCheckerName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public String toString() {
		return "WARNING ON " + this.patternName + ": " + fimDeLinha + 
			" Class: " + this.className + fimDeLinha + 
			" Reason: " + this.message;
	}

}
