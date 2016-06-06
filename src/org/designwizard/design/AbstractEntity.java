package org.designwizard.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;

/**
 * Abstract class that represent the entities of Design.
 * @author Joao Arthur Brunet Monteiro - joaoarthurbm@gmail.com
 */
public abstract class AbstractEntity implements Entity {
	
	/**
	 * Attributes
	 * 
	 * The relations Map contains all the relations that this AbstractEntity is the caller. The key of the map 
	 * represents the type of the relation and the object that is mapped for the key is a set of relations of the specified type.
	 */
	protected Map<TypesOfRelation, Set<Relation>> relations;
	protected String name;
	protected TypesOfEntities type;
	protected Collection<Modifier> modifiers;
	private Modifier visibility;
	
	/* (non-Javadoc)
	 * @see designwizard.design.entity.Entity#getShortName()
	 */
	@Override
	public String getShortName() {
		return this.getName().substring(this.getName().lastIndexOf(".")+1, this.getName().length());
	}

	@Override
	public abstract Set<ClassNode> getCalleeClasses();
	
	@Override
	public abstract Set<ClassNode> getCallerClasses();
	
	/**
     * Returns the set of <code>ClassNode</code> representing the annotations within this Entity.
     * @return the set of the annotations within this object.
     */
	@Override
	public Set<ClassNode> getAnnotations() {
		
		Set<Relation> containsRelations = this.getRelations(TypesOfRelation.IS_ANNOTATED_BY);
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		
		for (Relation relation : containsRelations) {
			Entity entity = relation.getCalledEntity();
			if (entity.getTypeOfEntity().equals(TypesOfEntities.CLASS))
					feedBack.add((ClassNode) entity);
		}
	
		return feedBack;
	}
	
	
	
	protected AbstractEntity() {
		this.modifiers = new ArrayList<Modifier>();
	}
	
	@Override
	public Modifier getVisibility() {
		return this.visibility;
	}

	/**
	 * Adds a relation for this entity.
	 * @param relation the relation to be added.
	 */
	@Override
	public void addRelation(Relation relation) {
		if (this.relations.get(relation.getType()) == null){
			Set<Relation> allRelations = new HashSet<Relation>();
			allRelations.add(relation);
			this.relations.put(relation.getType(), allRelations);
		}
		else {
			Set<Relation> allRelations = this.relations.get(relation.getType());
			
			if (!this.containsRelation(relation)) {
				allRelations.add(relation);
				this.relations.put(relation.getType(), allRelations);
			}	
		}
	}
	
	/**
	 * Removes a relation from this <code>Entity</code>.
	 * @param relation the relation to be removed.
	 * @return <tt>true</tt> if this <code>Entity</code> contains the specified relation.
	 */
	@Override
	public boolean removeRelation(Relation relation) {
		Set<Relation> allRelations = this.relations.get(relation.getType());
		return allRelations != null && allRelations.remove(relation);
	}
	
	/**
	 * Method that returns the description of this design element.
	 * @return a String representation for this element of design.
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * Method that returns the type of this element of design.
	 * @return the type of this element of design.
	 */
	@Override
	public TypesOfEntities getTypeOfEntity() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see designwizard.design.entity.Entity#addModifier(designwizard.design.entity.ui.Modifier)
	 */
	@Override
	public void addModifier(Modifier modifier) {
		if (modifier.isVisibility()) {
			this.setVisibility(modifier);
		}
		this.modifiers.add(modifier);
	}
	
	/* (non-Javadoc)
	 * @see designwizard.design.Entity#addModifiers(java.util.Collection)
	 */
	@Override
	public void addModifiers(Collection<Modifier> modifiers) {
		for (Modifier mod : modifiers) {
			if (mod.isVisibility()) {
				this.setVisibility(mod);
				break;
			}
		}
		this.modifiers.addAll(modifiers);
	}
	
	private void setVisibility(Modifier modifier) {
		this.visibility = modifier;
	}

	/* (non-Javadoc)
	 * @see designwizard.design.entity.Entity#containsModifiers(designwizard.design.entity.ui.Modifier[])
	 */
	@Override
	public boolean containsModifiers(Modifier... modifiers) {
		return this.modifiers.containsAll(Arrays.asList(modifiers)); 
	}
	
	/**
	 * Returns the visibility of this <code>Method</code>.
	 * @return the visibility of this <code>Method</code>.
	 */
	@Override
	public Collection<Modifier> getModifiers() {
		return this.modifiers;
	}

	/**
	 * Method that verifies if the Entity has the specified relation.
	 * @param relation the relation to be verified.
	 * @return true if the specified relation is on this entity or false if not.
	 */
	@Override
	public boolean containsRelation(Relation relation) {
		Set<Relation> set = this.relations.get(relation.getType());
		if (set == null) return false;
		return set.contains(relation);
	}
	
	/**
	 * Method that returns all the relations with the specified type.
	 * @param type the type of the relation.
	 * @return A Collection containing all the relations with the specified type. If there is no <code>Relation</code> of 
	 * the specified type, an empty Set<Realtion> will be returned.
	 */
	@Override
	public Set<Relation> getRelations(TypesOfRelation type) {
		if (this.relations.get(type) == null) {
			return new HashSet<Relation>();
		}
		return this.relations.get(type);
	}
	
	/* (non-Javadoc)
	 * @see designwizard.design.Entity#isAbstract()
	 */
	@Override
	public boolean isAbstract() {
		return this.containsModifiers(Modifier.ABSTRACT);
	}
	
	/**
	 * A String representation for the Entity.
	 * @return A string representation for the entities.
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode() + this.type.hashCode() + modifiers.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractEntity)) {
			return false; 
		 } else {
	        AbstractEntity abstractEntity = (AbstractEntity) obj;
	        return this.name.equals(abstractEntity.getName()) &&
	        	   this.type.equals(abstractEntity.getTypeOfEntity()) &&
	        	   this.modifiers.equals(abstractEntity.getModifiers());
	    }
	}
}