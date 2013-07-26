package org.designwizard.patternchecker;

public class CheckError {

	private String errorMessage;
	private String candidateName;
	private String patternName;
	private String fimDeLinha;

	public CheckError(String errorMessage, String candidateName, String patternName) {
		this.errorMessage = errorMessage;
		this.candidateName = candidateName;
		this.patternName = patternName;
		this.fimDeLinha = System.getProperty("line.separator");
	}

	public String getCandidateName() {
		return candidateName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getPatternName() {
		return patternName;
	}

	@Override
	public String toString() {
		return "ERROR ON " + this.patternName + ": " + fimDeLinha + 
				" Class: " + this.candidateName + "." + fimDeLinha + 
				" Reason: " + this.errorMessage;
	}
}
