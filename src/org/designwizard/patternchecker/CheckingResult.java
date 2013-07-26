package org.designwizard.patternchecker;

import java.util.HashSet;
import java.util.Set;

public class CheckingResult {
	
	private Set<CheckWarning> warnings;
	private Set<CheckError> errors;
	private boolean veredict;
	
	public CheckingResult() {
		this.veredict = true;
		this.errors = new HashSet<CheckError>();
		this.warnings = new HashSet<CheckWarning>();
	}

	public void addWarning(CheckWarning warning) {
		this.warnings.add(warning);
	}
	
 	public boolean getVeredict() {
		return this.veredict;
	}
	
 	/**
 	 * Defines the new veredict. In fact the veredict can just be switched
 	 * from <code>true</code> to <code>false</code>, because the inverse is 
 	 * a logical mistake (i.e.: once one error has been found the veredict 
 	 * is set to <code>false</code> and it does not make sense set it to 
 	 * <code>true</code> again, once the error can't be recovered).
 	 * @param veredict the veredict of the check.
 	 */
	void setVeredict(boolean veredict) {
		if (this.veredict == true) {
			this.veredict = veredict;
		}
	}

	public void addError(CheckError error) {
		this.errors.add(error);
	}

	public Set<CheckError> getErrors() {
		return this.errors;
	}

	public Set<CheckWarning> getWarnings() {
		return this.warnings;
	}

}
