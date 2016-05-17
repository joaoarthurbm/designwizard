package org.designwizard.patternchecker;

import org.designwizard.design.DesignIF;

/**
 * This interface provides operations to check whether a design implements correctly a design pattern.
 * @author João Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 *
 */
public interface PatternChecker {
	
	/**
	 * Sets the design to be checked by this <code>PatternChecker</code>.
	 * @param design the new design.
	 */
	public void setDesign(DesignIF design);
	
	/**
	 * Verifies whether the design implements the pattern which is checked by 
	 * this <code>PatternChecker</code>.
	 * @return the result of the verification.
	 */
	public CheckingResult verify();
	
	/**
	 * 
	 * @return <code>true</code> if the pattern is correctly implemented by the design; <code>false</code> otherwise.
	 */
	public boolean getVeredict();
}