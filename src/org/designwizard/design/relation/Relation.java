package org.designwizard.design.relation;
import org.designwizard.design.Entity;
import org.designwizard.exception.InexistentTypeOfRelationException;


/**
 * This class models a relation between two entities of the code.
 * @author João Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 *
 */
public class Relation {
	
	/**
	 * Types of relations
	 */
	public enum TypesOfRelation {
		
		INSTANCE("INSTANCEOF"), 
		CONTAINS("CONTAINS"),
		EXTENDS("EXTENDS"),
		IMPLEMENTS("IMPLEMENTS"),
		GETSTATIC("GETSTATIC"),
		PUTSTATIC("PUTSTATIC"),
		GETFIELD("GETFIELD"),
		PUTFIELD("PUTFIELD"),
		INVOKEVIRTUAL("INVOKEVIRTUAL"),
		INVOKESPECIAL("INVOKESPECIAL"),
		INVOKESTATIC("INVOKESTATIC"),
		INVOKEINTERFACE("INVOKEINTERFACE"),
		IS_INVOKED_BY("ISINVOKEDBY"),
		IS_ACCESSED_BY("ISACCESSEDBY"),
		IS_SUPERCLASS("ISSUPERCLASS"),
		CATCH("CATCH"),
		THROWS("THROWS"),
		IS_DECLARED_ON("IS_DECLARED_ON"),
		LOAD("LOAD"),
		IS_IMPLEMENTED_BY("IS_IMPLEMENTED_BY"),
		IS_ANNOTATED_BY("IS_ANNOTATED_BY");
		
		
		private String type = "";
		
		private TypesOfRelation (String type) {
			this.type = type;
		}
		
		/**
		 * Verifies if the relationType is equals to any kind of invoke relation (INVOKEVIRTUAL,INVOKESPECIAL,INVOKESTATIC
		 * or INVOKEINTERFACE).
		 * @return
		 */
		public boolean isInvokeRelation() {
			return (this.equals(INVOKEVIRTUAL) || this.equals(INVOKESPECIAL) || this.equals(INVOKESTATIC) 
					|| this.equals(INVOKEINTERFACE));
		}
		
		/**
		 * Verifies if the relationType is equals to any kind of access relation (GETSTATIC,PUTSTATIC,GETFIELD
		 * or PUTFIELD).
		 * @return
		 */
		public boolean isAccessRelation() {
			return (this.equals(GETSTATIC) || this.equals(PUTSTATIC) || this.equals(GETFIELD) 
					|| this.equals(PUTFIELD));
		}
		
		/**
		 * Returns the reverse relation of this relation.
		 */
		public TypesOfRelation getReverseRelation() {
			if (this.equals(TypesOfRelation.EXTENDS)) {
				return IS_SUPERCLASS;
			} else if (this.isInvokeRelation() || this.equals(TypesOfRelation.LOAD)) {
				return IS_INVOKED_BY;
			} else if (this.isAccessRelation()) {
				return IS_ACCESSED_BY;
			} else if (this.equals(TypesOfRelation.CONTAINS)) {
				return IS_DECLARED_ON;
			} else if (this.equals(TypesOfRelation.IMPLEMENTS)) {
				return IS_IMPLEMENTED_BY;
			}
			return null;
		}
		
		@Override
		public String toString() {
			return this.type;
		}
		
		/**
		 * Translate an UPPERCASE String to a type.
		 * @param type
		 * @return
		 *
		 */
		public static TypesOfRelation extractElement(String type) throws InexistentTypeOfRelationException {
			if ((type.equals("INSTANCEOF"))) {
	           return TypesOfRelation.INSTANCE;
	        } else if ((type.equals("CONTAINS"))) {
	       	 return TypesOfRelation.CONTAINS;
	        } else if ((type.equals("EXTENDS"))) {
	       	 return TypesOfRelation.EXTENDS;
	        } else if ((type.equals("IMPLEMENTS"))) {
	       	 return TypesOfRelation.IMPLEMENTS;
	        } else if ((type.equals("GETSTATIC"))) {
	       	 return TypesOfRelation.GETSTATIC;
	        } else if ((type.equals("PUTSTATIC"))) {
	       	 return TypesOfRelation.PUTSTATIC;
	        } else if ((type.equals("GETFIELD"))) {
	       	 return TypesOfRelation.GETFIELD;
	        } else if ((type.equals("PUTFIELD"))) {
	       	 return TypesOfRelation.PUTFIELD;
	        } else if ((type.equals("INVOKEVIRTUAL"))) {
	       	 return TypesOfRelation.INVOKEVIRTUAL;
	        } else if ((type.equals("INVOKESPECIAL"))) {
	       	 return TypesOfRelation.INVOKESPECIAL;
	        } else if ((type.equals("INVOKESTATIC"))) {
	       	 return TypesOfRelation.INVOKESTATIC;
	        } else if ((type.equals("INVOKEINTERFACE"))) {
	       	 return TypesOfRelation.INVOKEINTERFACE;
	        } else if ((type.equals("ISACCESSEDBY"))) {
	       	 return TypesOfRelation.IS_ACCESSED_BY;
	        } else if ((type.equals("ISINVOKEDBY"))) {
	       	 return TypesOfRelation.IS_INVOKED_BY;
	        } else if ((type.equals("ISSUPERCLASS"))) {
	       	 return TypesOfRelation.IS_SUPERCLASS;
	        } else if ((type.equals("CATCH"))) {
	       	 return TypesOfRelation.CATCH;
	        } else if ((type.equals("THROWS"))) {
	       	 return TypesOfRelation.THROWS;
	        } else if ((type.equals("IS_DECLARED_ON"))) {
	       	 return TypesOfRelation.IS_DECLARED_ON;
	        } else if ((type.equals("LOAD"))) {
	       	 return TypesOfRelation.LOAD;
	        } else if ((type.equals("IS_IMPLEMENTED_BY"))) {
	       	 return TypesOfRelation.IS_IMPLEMENTED_BY;
	        } else if ((type.equals("ISANNOTATEDBY"))) {
	        	return TypesOfRelation.IS_ANNOTATED_BY;
	        }
			throw new InexistentTypeOfRelationException(type);
		}
	}
	
	/**
	 * Atributs
	 */
	private Entity caller, called;
	private TypesOfRelation type;
	
	/**
	 * Constructs a new ContainRealtion.
	 * @param caller the caller entity of this relation (the entity that calls another entity.)
	 * @param called the called entity of this relation (the entity called by another one.)
	 */
	public Relation(Entity caller, Entity called, TypesOfRelation type) {
		this.caller = caller;
		this.called = called;
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see designeWizard.relation.Relation#getCallerEntity()
	 */
	public Entity getCallerEntity() {
		return this.caller;
	}

	/* (non-Javadoc)
	 * @see designeWizard.relation.Relation#getCalledEntity()
	 */
	public Entity getCalledEntity() {
		return this.called;
	}
	
	/**
	 * A String representation for the relation. This string representation contains the 
	 * two entities within this relation (separated by the type of this relation) and the status of
	 * this relation.
	 */
	@Override
	public String toString() {
		return this.caller+" "+this.type+" "+ this.called;
	}

	/**
	 * Returns a string representing the type of this relation.
	 * @return an string representing the type of the relation.
	 */
	public TypesOfRelation getType() {
		return this.type;
	}

	/**
	 * Method that verifies if this element of design is an Entity or not.
	 * @return true if this element of design is an Entity or false if not.
	 */
	public boolean isEntity() {
		return false;
	}
	
	/**
	 * Method that verifies if this element of design is a Relation or not.
	 * @return true if this element of design is a Relation or false if not.
	 */
	public boolean isRelation() {
		return true;
	}

	/**
	 * Returns the name of this <code>Relation</code>.
	 * @return the name of this <code>Relation</code>.
	 */
	public String getName() {
		return this.caller+" "+this.type+" "+ this.called + " - ";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.caller.getName()+this.called.getName()+this.type).hashCode();
	}
	
	/**
	 * Method that verifies if a relation is equals another one.
	 * @return true if this relation is equals the another one or false if not.
	 */
	@Override
	public boolean equals(Object other) {
		
		if (!(other instanceof Relation)) {
			return false;
		}
		Relation parameterRelation = (Relation)other;
		return (this.caller.getName().equals(parameterRelation.caller.getName())) && (this.called.getName().equals(parameterRelation.called.getName()))
				&& this.getType().equals(parameterRelation.getType());
	}
}