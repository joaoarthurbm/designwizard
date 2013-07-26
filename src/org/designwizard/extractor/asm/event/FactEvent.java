package org.designwizard.extractor.asm.event;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;

/**
 * Event that encapsulates informations extracted.
 * @author Joao Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 */
//FIXME tirar caller.replace("/", "."); dos construtoress
public class FactEvent extends EventObject {

	private String caller,called;
	private String type;
	private String entity;
	private String modifier;
	private Collection<String> modifiers = new ArrayList<String>();
	
	/**
	 * Creates a new FactEvent for relation extracted.
	 * @param source 
	 * @param type the type of the relation	
	 * @param caller
	 * @param called
	 */
	public FactEvent(Object source,String type, String caller, String called) {
	
		super(source);
		this.caller = caller.replace("/", ".");
		this.called = called.replace("/", ".");
		this.type = type;
	
	}

	/**
	 * Creates a new FactEvent for entity and visibility/modifier  extracted.
	 * @param source
	 * @param entity the name of the entity
	 * @param type modifier
	 */
	public FactEvent(Object source, String entity, String modifier) {
	
		super(source);
		this.entity = entity.replace("/", ".");
		this.modifier = modifier;
	
	}
	
	public FactEvent(Object source, String entity, Collection<String> modifiers) {
	
		super(source);
		this.entity = entity.replace("/", ".");
		this.modifiers = modifiers;
	
	}
	
	
	/**
	 * Used to classExtracted.
	 * @param source
	 * @param name
	 */
	public FactEvent(Object source, String name) {
	
		super(source);
		this.entity = name;
	
	}

	/**
	 * Returns the entity extracted.
	 */
	public String getEntity() {
	
		return entity;
	
	}

	/**
	 * Returns the called of the relation extracted. This method must be used when a relation was extracted.
	 */
	public String getCalled() {
	
		return called;
	
	}
	
	/**
	 * Returns the caller of the relation extracted. This method must be used when a relation was extracted. 
	 */
	public String getCaller() {
	
		return caller;
	
	}
	
	/**
	 * Returns the type of the relation extracted. This method must be used when a relation was extracted. 
	 */
	public String getType() {
	
		return type;
	
	}
	
	/**
	 * Returns the modifier extracted.
	 */
	public String getModifier() {

		return modifier;
	
	}
	
	/**
     * Returns the Java language modifiers for the entity of this fact.
     * @see Modifier
     */
	public Collection<String> getModifiers() {

		return this.modifiers;
	
	}
	
}
