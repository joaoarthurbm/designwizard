package org.designwizard.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;

/**
* A <code>FieldNode</code> provides information about a
* single field of a class or an interface.  
* 
* 
*  To get access to a desired method extracted, do not use the constructor of this class. Instead, use the
* class <code>DesignWizard</code> as it follows:
* 
* <blockquote><pre>
*  		DesignWizard dw = new DesignWizard("/home/user/application/classes");
*		FieldNode c = dw.getField("MyAplicationClassExample.field");  
* </pre></blockquote>
* 
* @see org.designwizard.design.ClassNode
* @see org.designwizard.design.ClassNode#getAllFields()
* @see org.designwizard.design.ClassNode#getField(String)
* @see org.designwizard.design.ClassNode#getDeclaredFields()
* @see org.designwizard.design.ClassNode#getInheritedFields()
*
* @author Joao Arthur Brunet Monteiro - joaoarthurbm@gmail.com
*/
public class FieldNode extends AbstractEntity implements Entity {

	/**
	 * Creates a new <code>Field</code>.
	 * @param name the name of this entity.
	 */
	public FieldNode(String name) {
	
		super();
		super.name = name;
		super.type = TypesOfEntities.FIELD;
		super.relations = new HashMap<TypesOfRelation,Set<Relation>>();
	
	}

	/**
     * Returns a <code>ClassNode</code> object that identifies the
     * declared type for the field represented by this
     * <code>FieldNode</code> object.
     *
     * @return a <code>ClassNode</code> object identifying the declared
     * type of the field represented by this object
     */
	public ClassNode getType() {
	
		Relation instanceOf = this.getRelations(TypesOfRelation.INSTANCE).iterator().next();
		return (ClassNode) instanceOf.getCalledEntity();
	
	}
	
	/**
     * Returns the <code>ClassNode</code> object representing the class or interface
     * that declares the field represented by this <code>FieldNode</code> object. If
     * it is not possible to define the class that declares the field represented by this 
     * <code>FieldNode</code> object, then null is returned.
     */
	public ClassNode getDeclaringClass() {
	
		Set<Relation> declaredRelation = this.getRelations(TypesOfRelation.IS_DECLARED_ON);
		
		if (!declaredRelation.isEmpty()) {
		
			return (ClassNode) declaredRelation.iterator().next().getCalledEntity();
		
		}
		
		return null;
	
	}
	
	/* (non-Javadoc)
	 * @see designwizard.design.entity.Entity#getImpactOfAChange()
	 */
	public List<String[]> getImpactOfAChange() {
	
		List<String[]> result = new ArrayList<String[]>();
		
		for (MethodNode method: this.getCallerMethods()) {
		
			for (String[] array: method.getImpactOfAChange()) {
			
				result.add(array);
			
			}
		
		}
		
		return result;
	
	}
	
	/**
	 * Method that returns the description of this design element. The description
	 * is a string representation that contains the name of this designElement and
	 * all the relations that this designElement is the caller of the relation.
	 * @return a String representation for this element of design.
	 */
	@Override
	public String toString() {
		
		StringBuffer feedBack = new StringBuffer();
		feedBack.append(this.name+"\n");
		return feedBack.toString();
	
	}
	
	/**
	 * Verifies if this entity is equals the other entity.
	 * @return true if this entity is equals the other entity or false if not.
	 */
	@Override
	public boolean equals(Object other) {
		
		if (!(other instanceof FieldNode)) {
			
			return false;
		
		}
		
		FieldNode parameterEntity = (FieldNode)other;
		
		return this.name.equals(parameterEntity.getName()) && 
			   this.type.equals(parameterEntity.getTypeOfEntity()) &&
			   this.getTypeOfEntity().equals(parameterEntity.getTypeOfEntity());
	}
	
	@Override
	//FIXME reimplementar, colocar o tipo declarado também
	public int hashCode() {
		
		return this.name.hashCode();
	
	}

	/**
     * Returns <tt>true</tt> if this field is a static
     * field; returns <tt>false</tt> otherwise.
     *
     * @return true if and only if this field is a static
     * field.
     * 
     */
	public boolean isStatic() {
		
		return this.containsModifiers(Modifier.STATIC);
	
	}

	public ClassNode getClassNode() {
		//FIXME bug para atributos de superclasses
		//FIXME DRY - don't repeat yourself
		return this.getDeclaringClass();
	}

	public String getClassName() {
		
		ClassNode node = this.getDeclaringClass();
		
		if (node == null) {
		
			return this.getName().substring(0,this.getName().lastIndexOf("."));
		
		} else return node.getName();
	}

	@Override
	public PackageNode getPackage() {
		return this.getDeclaringClass().getPackage();
	}
	
	/**
	 * Returns the Methods that access this field.
	 * @return returns the Methods that access this field.
	 */
	public Set<MethodNode> getCallerMethods(){
	
		Set<MethodNode> methods = new HashSet<MethodNode>();
		Set<Relation> relations = super.getRelations(TypesOfRelation.IS_ACCESSED_BY);
		
		Entity entity = null;
		
		for (Relation relation: relations) {
	
			entity = relation.getCalledEntity();
			methods.add((MethodNode) entity);
		
		}
		
		return methods;
	
	}

	@Override
	/**
	 * This method returns an empty Set. Field does not make any reference in the code.
	 * @return An empty set. Field does not make any reference in the code.
	 */
	public Set<MethodNode> getCalleeMethods() {
		
		return new HashSet<MethodNode>();
		
	}
	
	/**
	 * Returns the classes that reference this FieldNode.
	 * @return A set that contains all classes that reference this FieldNode.
	 */
	@Override
	public Set<ClassNode> getCallerClasses() {
		
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		
		Set<MethodNode> callerMethods = this.getCallerMethods();
		
		for (MethodNode m : callerMethods) {
		
			if ( m.getDeclaringClass() != null) {
		
				feedBack.add(m.getDeclaringClass());
				
			}
		
		}
		
		return feedBack;
	
	}
	
	/**
	 * This method returns an empty Set. Field does not make any reference in the code.
	 * @return An empty set. Field does not make any reference in the code.
	 */
	@Override
	public Set<ClassNode> getCalleeClasses() {

		return new HashSet<ClassNode>();
	
	}
	
	@Override
	public Set<PackageNode> getCallerPackages() {
		
		Set<PackageNode> feedBack = new HashSet<PackageNode>();
		
		Set<ClassNode> callerClasses = this.getCallerClasses();
		
		for (ClassNode c : callerClasses) {
			
			feedBack.add(c.getPackage());
		
		}
		
		return feedBack;
		
	}
	
	@Override
	/**
	 * This method returns an empty Set. Field does not make any reference in the code.
	 * @return An empty set. Field does not make any reference in the code.
	 */
	public Set<PackageNode> getCalleePackages() {
		
		return new HashSet<PackageNode>();
		
	}

}