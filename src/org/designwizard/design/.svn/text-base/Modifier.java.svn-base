package org.designwizard.design;

import org.designwizard.exception.InexistentModifierException;

/**
 * This class contains all the modifiers currently extracted provided by the extractors of Design Wizard.
 * @author João Arthur Brunet Monteiro
 */
public enum Modifier {
		//FIXME colocar todos os modificadores.
		PUBLIC("PUBLIC"),PROTECTED("PROTECTED"),PRIVATE("PRIVATE"),PACKAGE("PACKAGE"), STATIC("STATIC"), 
		ABSTRACT("ABSTRACT"), INTERFACE("INTERFACE"), INNERCLASS("INNER"), ANONYMOUS("ANONYMOUS"),
		FINAL("FINAL");
		
		private String type = "";
		
		private Modifier (String type) {
			this.type = type;
		}
		
		@Override
		public String toString() {
			return this.type;
		}
		
		/**
		 * Verifies whether this <code>Modifier</code> is a visibility (Modifier.PACKAGE, Modifier.PRIVATE, Modifier.PROTECTED or Modifier.PUBLIC).
		 */
		public boolean isVisibility() {
			if (this.equals(Modifier.PACKAGE) || this.equals(Modifier.PRIVATE) || this.equals(Modifier.PROTECTED)
					|| this.equals(Modifier.PUBLIC)) return true;
			return false;
		}

		/**
		 * Translates the given <code>String</code> to the corresponding modifier. 
		 * @param modifierAsString The modifier identifier.
		 * @return the corresponding modifier.
		 */
		public static Modifier extractModifier(String modifierAsString) throws InexistentModifierException {
	    	 if ((modifierAsString.equalsIgnoreCase("PUBLIC"))) {
	            return Modifier.PUBLIC;
	         } else if ((modifierAsString.equalsIgnoreCase("PRIVATE"))) {
	        	 return Modifier.PRIVATE;
	         } else if ((modifierAsString.equalsIgnoreCase("PROTECTED"))) {
	        	 return Modifier.PROTECTED;
	         } else if ((modifierAsString.equalsIgnoreCase("STATIC"))) {
	        	 return Modifier.STATIC;
	         } else if ((modifierAsString.equalsIgnoreCase("ABSTRACT"))) {
		        	 return Modifier.ABSTRACT;
	         } else if ((modifierAsString.equalsIgnoreCase("INTERFACE"))) {
		        	 return Modifier.INTERFACE;
	         } else if ((modifierAsString.equalsIgnoreCase("PACKAGE"))) {
	        	 return Modifier.PACKAGE;
	         } else if ((modifierAsString.equalsIgnoreCase("INNER"))) {
	        	 return Modifier.INNERCLASS;
	         } else if ((modifierAsString.equalsIgnoreCase("ANONYMOUS"))) {
	        	 return Modifier.ANONYMOUS;
	         } else if ((modifierAsString.equalsIgnoreCase("FINAL"))) {
	        	 return Modifier.FINAL;
	         }
		     throw new InexistentModifierException("The modifier "+modifierAsString+ " is not valid." +
		     		" See the valid modifiers at designwizard.design.Modifier class.");
		}
}