package org.designwizard.design.factory;


import org.designwizard.design.ClassNode;
import org.designwizard.design.DesignIF;
import org.designwizard.design.Entity;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;
import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;

/**
 * Factory for Design elements.
 * @author João Brunet - jarthur@dsc.ufcg.edu.br
 */
public class ElementsFactory implements AbstractElementsFactory {

	
	/**
	 * Unique Instance for ElementsFactory.
	 */
	private static ElementsFactory uniqueInstance = null;
	private ElementsFactory() {}

	/**
	 * Returns the unique instance for this factory.
	 * @return ElementsFactory the unique instance for this factory.
	 */
	public static ElementsFactory getInstance() {
		if (uniqueInstance == null)	return new ElementsFactory();
		return uniqueInstance;
	}
	
	/**
	 * Creates a new entity with the specified name.
	 * @param entityName the name of the entity.
	 * @return a new Entity with the specified name.
	 */
	public Entity createEntity(String entityName) {
		
		if (entityName.startsWith(DesignIF.FIELD_IDENTIFIER)) {
		
			return new FieldNode(entityName.replace(DesignIF.FIELD_IDENTIFIER,""));
		
		} else if (entityName.startsWith(DesignIF.STATIC_IDENTIFIER)) {
		
			MethodNode method = null;
			String methodName = "";
			
			methodName = entityName.replace(DesignIF.STATIC_IDENTIFIER,"");
			method = new MethodNode(methodName.replace(DesignIF.METHOD_IDENTIFIER, ""),methodName.contains(MethodNode.CONSTRUCTOR_IDENTIFIER));
			method.addModifier(Modifier.STATIC);
			
			return method;
		
		} else if (entityName.startsWith(DesignIF.METHOD_IDENTIFIER )) {
		
			return new MethodNode(entityName.replace(DesignIF.METHOD_IDENTIFIER, ""),entityName.contains(MethodNode.CONSTRUCTOR_IDENTIFIER));	 
		
		} else {
		
			return new ClassNode(entityName);
		
		}
	}
	
	/**
	 * Creates a new <code>Relation</code>.
	 * @param type the type of the relation.
	 * @param caller the caller of the relation.
	 * @param called the called of the relation.
	 * @return a new Relation.
	 */
	public Relation createRelation(TypesOfRelation type, Entity caller, Entity called) {
		if (type.equals(TypesOfRelation.INVOKESTATIC)) {
			((MethodNode) called).addModifier(Modifier.STATIC);
		}
		return new Relation(caller,called,type);
	}
}
