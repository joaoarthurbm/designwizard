package org.designwizard.design.factory;


import org.designwizard.design.Entity;
import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;


/**
 * Interface to object creation.
 * @author João Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 */
public interface AbstractElementsFactory {

	/**
	 * Creates a new entity with the specified name.
	 * @param entityName the name of the entity.
	 * @return a new Entity with the specified name.
	 */
	public Entity createEntity(String entityName);
	
	/**
	 * Creates a new <code>Relation</code>.
	 * @param type the type of the relation.
	 * @param caller the caller of the relation.
	 * @param called the called of the relation.
	 * @return a new Relation.
	 */
	public Relation createRelation(TypesOfRelation type, Entity caller, Entity called);
}
