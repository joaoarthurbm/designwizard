package org.designwizard.exception;

/**
 * @author João Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 */
public class InexistentEntityException extends Exception {
	
	/**
	 * Create a new InexistentEntityException.
	 * @param msg
	 */
	public InexistentEntityException(String msg) {
		super(msg);
	}
}