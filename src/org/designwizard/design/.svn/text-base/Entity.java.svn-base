package org.designwizard.design;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;

/**
 * Represents the entities of the Design.
 * @author Jo�o Arthur Brunet Monteiro - joaoarthurbm@gmail.com
 *
 */
public interface Entity {
	
	/**
	 * This enum represents the entities of the design.
	 * @author Joao Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
	 *
	 */
	public enum TypesOfEntities {	
			CLASS,
			METHOD,
			FIELD, 
			PACKAGE,
	}
	
	/**
	 * Method that returns the description of this design element.
	 * @return a String representation for this element of design.
	 */
	public String getName();
	
	/**
	 * Returns the <code>ClassNode</code> that represents this <code>Entity</code> object.
	 * For instance, if this <code>Entity</code> object is a <code>FieldNode</code>, this method will return the
	 * <code>ClassNode</code> that contains the field. The same is applied to <code>MethodNode</code>s objects.
	 * @return the <code>ClassNode</code> object that represents this <code>Entity</code> object.
	 */
	public ClassNode getClassNode();
	
	/**
	 * Returns the short name of this <code>Entity</code>.
	 *  
	 * The short name of an attribute is just its name.
	 * For example, if the short name of attribute out from class <code>System</code> is out but not java.lang.System.out.
	 * 
	 * The short name of a method is just its name and signature. 
	 * For example, the short name of clone method from Object class is clone() but not java.lang.Object.clone().
	 *
	 * The short name of a class is just its name. Not the fully qualified name.
	 * For example, the short name of Object class is Object but not java.lang.Object.
	 * 
	 * @return the short name of this <code>Entity</code>.
	 */
	public String getShortName();
	
	/**
	 * Returns the package which this <code>Entity</code> belongs to. If this <code>Entity</code> belongs to
	 * the default package, a package with the name "default" is returned.
	 * @return the package which this <code>Entity</code> belongs to.
	 */
	public PackageNode getPackage();
	
	/**
	 * Adds a relation for this entity.
	 * @param relation the relation to be added.
	 */
	public void addRelation(Relation relation);
	
	/**
	 * Method that verifies if the Entity has the specified relation.
	 * @param relation the relation to be verified.
	 * @return true if the specified relation is on this entity or false if not.
	 */
	public boolean containsRelation(Relation relation);
	
	/**
	 * Method that returns all the relations with the specified type.
	 * @param type the type of the relation.
	 * @return A Collection containing all the relations with the specified type. If there is no <code>Relation</code> of 
	 * the specified type, an empty Set<Realtion> will be returned.
	 * 
	 *  */
	public Collection<Relation> getRelations(TypesOfRelation type);
	
	/**
	 * Method that removes a <code>Relation</code> from this <code>Entity</code>.
	 * @param relation the relation to be removed from this <code>Entity</code>.
	 */
	public boolean removeRelation(Relation relation);
	
	/**
	 * Method that returns the type of this element of design.
	 * @return the type of this element of design.
	 */
	public TypesOfEntities getTypeOfEntity();
	
	/**
	 * Returns a <code>Set</code> of <code>MethodNode</code> containing the methods that call this <code>Entity</code>.
	 * @return a <code>Set</code> containing the methods that calls this <code>Entity</code>.
	 */
	public Set<MethodNode> getCallerMethods();
	
	/**
	 * Returns a <code>Set</code> of <code>MethodNode</code> containing the methods that
	 * are called by this <code>Entity</code>.
	 * @return a <code>Set</code> containing the methods that are called by this <code>Entity</code>.
	 */
	public Set<MethodNode> getCalleeMethods();

	/**
	 * Returns the classes that reference this Entity.
	 * @return A set containing all classes that makes reference to this Entity.
	 */
	public Set<ClassNode> getCallerClasses();
	
	/**
	 * Returns the classes that are referenced by this Entity.
	 * @return A set containing all classes that are referenced by this Entity.
	 */
	public Set<ClassNode> getCalleeClasses();
	
	/**
	 * Returns a <code>Set</code> of <code>PackageNode</code> containing the packages that reference this <code>Entity</code>.
	 * @return a <code>Set</code> containing the packages that reference this <code>Entity</code>.
	 */
	public Set<PackageNode> getCallerPackages();
	
	
	/**
	 * Returns a <code>Set</code> of <code>PackageNode</code> containing the packages that are referenced
	 * by this <code>Entity</code>.
	 * @return a <code>Set</code> containing the packages that are referenced
	 * by this <code>Entity</code>.
	 */
	public Set<PackageNode> getCalleePackages();

	/**
	 * Returns a <code>Collection</code>  of <code>Modifier</code>s representing the modifiers of 
	 * this <code>Entity</code>.
	 * @return a <code>Collection</code>  of <code>Modifier</code>s representing the modifiers of 
	 * this <code>Entity</code>.
	 */
	public Collection<Modifier> getModifiers();
	
	/**
	 * Returns the visibility of this <code>Entity</code>.
	 * @return
	 */
	public Modifier getVisibility();
	
	/**
	 * This method is used to add a <code>Modifier</code> on this <code>Entity</code>.
	 * @param modifier
	 */
	public void addModifier(Modifier modifier);
	
	
	/**
	 * This method is used to add a <code>Collection</code> of <code>Modifier</code> on this <code>Entity</code>.
	 * @param modifier
	 */
	public void addModifiers(Collection<Modifier> modifiers);
	
	
	/**
	 * Verifies whether this <code>Entity</code> has the specified modifiers.
	 * @param modifiers
	 * @return true if this <code>Entity</code> has the specified modifiers or false otherwise. 
	 */
	public boolean containsModifiers(Modifier... modifiers);

	/**
	 * Returns a <code>List</code> of <code>String</code> arrays that represents the trace of calls.
	 * The deep of the search is configured on designwizard.properties
	 * @return
	 */
	public List<String[]> getImpactOfAChange();
	
	/**
	 * Verifies if this <code>Entity</code> is abstract.
	 * @return true if this <code>Entity</code> is abstract; false otherwise.
	 */
	public boolean isAbstract();
	
	/**
	 * Returns the name of the class that represents this <code>Entity</code> object.
	 * For instance, if this <code>Entity</code> object is a <code>FieldNode</code>, this method will return the
	 * name of the class that contains the field. The same is applied to <code>MethodNode</code>s objects. In the case of 
	 * <code>ClassNode</code> objects, calling this method has the same effect as the <code>designwizard.design.ClassNode.getName()</code> method.
	 * @return the name of the class that represents this <code>Entity</code> object.
	 */
	public String getClassName();
}