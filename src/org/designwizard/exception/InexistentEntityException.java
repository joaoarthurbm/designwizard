package org.designwizard.exception;

/**
 * @author Jo�o Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 */
public class InexistentEntityException extends Exception {
	
	/**
	 * Create a new InexistentEntityException.
	 * @param message
	 */
	public InexistentEntityException(String msg) {
		super(msg);
	}
}