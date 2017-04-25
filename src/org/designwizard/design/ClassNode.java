package org.designwizard.design;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.exception.NotAnInterfaceException;

/**
 * 
 * <code>ClassNode</code> objects are constructed automatically by the <code>DesignWizard</code> class when classes
 * are loaded. To get access to a desired class extracted, do not use the constructor of this class. Instead, use the
 * class <code>DesignWizard</code> as it follows:
 * 
 * <blockquote><pre>
 *  	DesignWizard dw = new DesignWizard("/home/user/application/classes");
 *		ClassNode c = dw.getClass("MyAplicationClassExample");  
 * </pre></blockquote>
 * 
 * Instances of the class <code>ClassNode</code> represent classes and
 * interfaces in the code extracted.  An enum and array are a kind of
 * class. The primitive Java types (<code>boolean</code>,
 * <code>byte</code>, <code>char</code>, <code>short</code>,
 * <code>int</code>, <code>long</code>, <code>float</code>, and
 * <code>double</code>), and the keyword <code>void</code> are also
 * represented as <code>Class</code> objects.
 * 
 * Inner Classes are also represented as <code>Class</code> objects, but with
 * the the special identifier $. For example:
 * 
 * <blockquote><pre>
 * 		foo.bar.OuterClass$InnerClass
 * </pre></blockquote>
 * 
 *
 **/

public class ClassNode extends AbstractEntity implements Entity {
	
	/**
	 * Attributes
	 */
	private Set<FieldNode> declaredFields, inheritedFields;
	private ClassNode superClass;
	private Set<ClassNode> subClasses;
	private boolean isPrimitive;
	private Set<MethodNode> inheritedMethods;
	private Set<MethodNode> declaredMethods;
	

	/**
	 * Creates a new <code>ClassNode</code>.
	 * @param name the name of the <code>ClassNode</code>.
	 */
	public ClassNode(String name) {
		super();
		super.name = name;
		
		this.isPrimitive = isPrimitive(name);
		
		super.type = TypesOfEntities.CLASS;
		super.relations = new HashMap<TypesOfRelation,Set<Relation>>();
		this.inheritedFields = new HashSet<FieldNode>();
		this.declaredFields = new HashSet<FieldNode>();
		this.inheritedMethods = new HashSet<MethodNode>();
		this.declaredMethods = new HashSet<MethodNode>();
		this.subClasses = new HashSet<ClassNode>();
	
	}

	/**
     * Determines if the specified <code>ClassNode</code> object represents a
     * primitive type.
     *
     * <p> There are nine predefined <code>ClassNode</code> objects to represent
     * the eight primitive types and void.  These are created by the Java
     * Virtual Machine, and have the same names as the primitive types that
     * they represent, namely <code>boolean</code>, <code>byte</code>,
     * <code>char</code>, <code>short</code>, <code>int</code>,
     * <code>long</code>, <code>float</code>, and <code>double</code>.
     *
     * @return true if and only if this class represents a primitive type
     *
     * @see     java.lang.Boolean#TYPE
     * @see     java.lang.Character#TYPE
     * @see     java.lang.Byte#TYPE
     * @see     java.lang.Short#TYPE
     * @see     java.lang.Integer#TYPE
     * @see     java.lang.Long#TYPE
     * @see     java.lang.Float#TYPE
     * @see     java.lang.Double#TYPE
     * @see     java.lang.Void#TYPE
     */
	private boolean isPrimitive(String name) {
	
		if (name.equals("void") || name.equals("boolean") || name.equals("byte") || name.equals("char")
			|| name.equals("short") || name.equals("int") || name.equals("long") || name.equals("float")
			|| name.equals("double") ) return true;
	
		return false;

	}
	
	/**
     * Returns the <code>ClassNode</code> representing the outer class of the entity
     * represented by this <code>ClassNode</code>. If this <code>ClassNode</code> 
     * does not represent an InnerClass then null is returned.
     *
     * @return the outerclass of the class represented by this object.
     */
	public ClassNode getOuterClass() {
		
		if (!this.isInnerClass()) return null;
		Set<Relation> isDeclaredOn = super.getRelations(TypesOfRelation.IS_DECLARED_ON);
				
		for (Relation relation : isDeclaredOn) {
			Entity outer = relation.getCalledEntity();
			if (outer.getTypeOfEntity().equals(TypesOfEntities.CLASS)) return (ClassNode) outer;
		}
	
		//this line will never be executed, but i had to put this return statement here
		return null;
	}
	
	/**
     * Returns a <code>java.util.Set</code> containing the <code>ClassNode</code> objects representing the inner classes
     * of this <code>ClassNode</code>.  If this <code>ClassNode</code> does not contain InnerClasses then
     * an empty set is returned.
     *
     * @return the set of <code>ClassNode</code> objects representing the inner classes
     * of this <code>ClassNode</code>. If this <code>ClassNode</code> does not contain InnerClasses then
     * an empty set is returned.
     */
	public Set<ClassNode> getInnerClasses() {
	
		Set<Relation> containsRelations = super.getRelations(TypesOfRelation.CONTAINS);
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		
		for (Relation relation : containsRelations) {
			Entity entity = relation.getCalledEntity();
			if (entity.getTypeOfEntity().equals(TypesOfEntities.CLASS)) {
				feedBack.add((ClassNode) entity);
			}
		}
	
		return feedBack;

	}
	
	

	/* (non-Javadoc)
	 * @see designwizard.design.entity.AbstractEntity#addRelation(designwizard.design.relation.Relation)
	 */
	@Override
	public void addRelation(Relation relation) {
		super.addRelation(relation);
		if (relation.getType().equals(TypesOfRelation.EXTENDS)) {
			this.superClass = (ClassNode) relation.getCalledEntity();
		}
		
		if (this.isInterface() && relation.getType().equals(TypesOfRelation.IMPLEMENTS)) {
			this.superClass = (ClassNode) relation.getCalledEntity();
		}
		
		if (relation.getType().equals(TypesOfRelation.IS_SUPERCLASS)) {
				this.subClasses.add((ClassNode) relation.getCalledEntity());
		}
		
		if (relation.getType().equals(TypesOfRelation.CONTAINS)) {
			if (relation.getCalledEntity().getTypeOfEntity().equals(TypesOfEntities.FIELD)) {
				FieldNode field = (FieldNode) relation.getCalledEntity();
				this.declaredFields.add(field);
			} 
			if (relation.getCalledEntity().getTypeOfEntity().equals(TypesOfEntities.METHOD)) {
				MethodNode method = (MethodNode) relation.getCalledEntity();
				this.declaredMethods.add(method);
			}
		}
		
	}
	
	/**
	 * Adds a new field to this <code>ClassNode</code>.
	 * @param field the field to be added.
	 */
	void addInheritedField(FieldNode field) {
		this.inheritedFields.add(field);
	}
	
	/* (non-Javadoc)
	 * @see designwizard.design.entity.AbstractEntity#getShortName()
	 */
	@Override
	public String getShortName() {
		if (this.isInnerClass()) {
			return getName().substring(this.getName().lastIndexOf(".")+1, super.getName().length());
		} else return super.getShortName();
	}
	
	
	
	/**
     * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
     * the methods of the class or interface represented by this <code>ClassNode</code> object, including those declared by the class
     * or interface and those inherited from superclasses and superinterfaces.  
     * 
     * @return the set of <code>MethodNode</code> objects representing the
     * methods of this class.
     */
	public Set<MethodNode> getAllMethods() {
		
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		feedBack.addAll(this.inheritedMethods);
		feedBack.addAll(this.declaredMethods);
		return feedBack;
		
	}

	/**
     * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
     * the static methods of the class or interface represented by this <code>ClassNode</code> object.
     * 
     * @return the set of <code>MethodNode</code> objects representing the
     * static methods of this class.
     */
	public Set<MethodNode> getStaticMethods() {
		return this.getMethods(Modifier.STATIC);
	}

	/**
     * Returns a <code>java.util.Set</code> representing the subclasses of the entity
     * represented by this <code>ClassNode</code>.  If this <code>ClassNode</code> represents an entity
     * that does not have subclasses, then an empty set is returned.
     *
     * @return the <code>java.util.Set</code> representing the subclasses of the entity
     * represented by this <code>ClassNode</code>. If this <code>ClassNode</code> represents an entity
     * that does not have subclasses, then an empty set is returned.
     */
	public Set<ClassNode> getSubClasses() {

		return this.subClasses;
	
	}
	
	/**
     * Returns the <code>ClassNode</code> representing the superclass of the entity
     * represented by this <code>ClassNode</code>.
     *
     * @return the superclass of the class represented by this object.
     */
	public ClassNode getSuperClass() {
		return this.superClass;
	}

	/**
     * Returns a <code>java.util.Set</code> representing the methods of the class or interface represented by this
     * <code>ClassNode</code> object that returns the type specified by type. 
     * The <code>type</code> parameter is a <code>String</code> that specifies the fully qualified name of the desired
     * return type. For example <code>java.lang.String</code>.
     * 
     * <p>
     * 
     * If the the class or interface represented by this
     * <code>ClassNode</code> object does not declare a method
     * with the specified return type, then an empty set is returned.
     * 
     * @param type return desired type
     * @return  the <code>MethodNode</code> object for the method of this class
     * matching the specified return type. 
     * 
     * If the the class or interface represented by this
     * <code>ClassNode</code> object does not declare any method
     * with the specified return type, then an empty set is returned
     */
	public Set<MethodNode> getAllMethodsThatReturn(String type) {
		Set<MethodNode> allMethods = this.getAllMethods();
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		for (MethodNode method: allMethods) {
			if (method.getReturnType().getName().equals(type)) {
				feedBack.add(method);
			}
		}
		return feedBack;
	}

	/**
     * Returns a <code>java.util.Set</code> representing the methods of the class or interface represented by this
     * <code>ClassNode</code> object that contains the specified modifiers. 
     
     * If the the class or interface represented by this
     * <code>ClassNode</code> object does not declare any method
     * with the specified modifiers, then an empty set is returned.
     * 
     
     * @param	modifiers the desired modifiers
     * @return  the <code>MethodNode</code> object for the method of this class
     * matching the specified modifiers
     * 
     * If the the class or interface represented by this
     * <code>ClassNode</code> object does not declare a method
     * with the specified modifiers, then an empty set is returned.
     */
	
	public Set<MethodNode> getMethods(Modifier... modifiers) {
		Set<MethodNode> allMethods = this.getAllMethods();
		Set<MethodNode> methodsToReturn = new HashSet<MethodNode>();
		for (MethodNode method: allMethods) {
			if (method.containsModifiers(modifiers)) {
				methodsToReturn.add(method);
			}
		}
		return methodsToReturn;
	}

	/**
     * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting
     * all constructors of the class represented by this
     * <code>ClassNode</code> object.  An empty set is returned if the
     * class has no constructors, or if the class is an array class, or
     * if the class reflects a primitive type or void.
     *
     *
     * @return the set of <code>MethodNode</code> objects representing the
     *  constructors of this class
     */
	public Set<MethodNode> getConstructors() {
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		Set<MethodNode> methods = this.getAllMethods();
		for (MethodNode method : methods) {
			if (method.isConstructor()) {
				feedBack.add(method);
			}
		}
		return feedBack;
	}

	/**
     * Returns a <code>java.util.Set</code> array of <code>FieldNode</code> objects reflecting all the fields
     * declared (excluding inhireted fields) by the class or interface represented by this
     * <code>ClassNode</code> object. This method returns an empty set if the class
     * or interface declares no fields, or if this <code>ClassNode</code> object
     * represents a primitive type, an array class, or void.
     *
     * @return    the set of <code>FieldNode</code> objects representing all the
     * declared fields of this class
     */
	public Set<FieldNode> getDeclaredFields() {
		return Collections.unmodifiableSet(this.declaredFields);
	}
	
	//TODO document this method.
	public Set<Entity> getEntitiesAnnotatedBy() {
		Set<Relation> annotatesRelations = this.getRelations(TypesOfRelation.ANNOTATES);
		Set<Entity> feedBack = new HashSet<Entity>();
		
		for (Relation relation : annotatesRelations) {
			Entity entity = relation.getCalledEntity();
			feedBack.add(entity);
		}
	
		return feedBack;
	}
	
	

	/* (non-Javadoc)
	 * @see designwizard.design.entity.Entity#getImpactOfAChange()
	 */
	public List<String[]> getImpactOfAChange() {
		List<String[]> result = new ArrayList<String[]>();
		Set<MethodNode> set = this.getAllMethods();
		for (MethodNode method: set) {
			for (String[] array: method.getImpactOfAChange()) {
				result.add(array);
			}
		}
		return result;
	}
	
	/**
     * Converts the object to a string.
     *
     * @return a string representation of this class object.
     */
	public String toString() {
		StringBuffer feedBack = new StringBuffer();
		feedBack.append("#"+this.name+"\n");
		feedBack.append("\t###Fields### \n\n");
		feedBack.append(printComponents(this.getDeclaredFields())+"\n");
		feedBack.append("\t###Methods### \n\n");
		feedBack.append(printComponents(this.getAllMethods())+"\n");
		return feedBack.toString();
	}
	
	/**
	 * Append the toString() of the entities inside the collection parameter to a StringBuffer.
	 * @param collection
	 * @return
	 */
	private StringBuffer printComponents(Set<? extends Entity> collection) {
		StringBuffer st = new StringBuffer();
		for (Entity entity: collection) {
			st.append("\t#"+entity.toString());
		}
		return st;
	}
	
	

	/**
	 * Returns a <code>java.util.Set</code> of <code>FieldNode</code> objects inherited
	 * by this <code>ClassNode</code>.
	 * @return a <code>java.util.Set</code> of <code>FieldNode</code> objects inherited
	 * by this <code>ClassNode</code>.
	 */
	public Set<FieldNode> getInheritedFields() {
		return Collections.unmodifiableSet(this.inheritedFields);
	}

	  /**
     * Returns a <code>java.util.Set</code> containing <code>FieldNode</code> objects reflecting all
     * the fields (inherited and declared) of the class or interface represented by this <code>Class</code> object.  
     * This method returns an empty set if the class or interface has fields, 
     * or if it represents an array class, a primitive type, or void.
     *
     * @return the set of <code>FieldNode</code> objects representing the
     * fields.  * This method returns an empty set if the class or interface has fields, 
     * or if it represents an array class, a primitive type, or void.
     */
	public Set<FieldNode> getAllFields() {
	
		Set<FieldNode> feedBack = new HashSet<FieldNode>();
		feedBack.addAll(this.inheritedFields);
		feedBack.addAll(this.declaredFields);
		return feedBack;
	
	}

	/**
     * Determines if the specified <code>ClassNode</code> object represents a
     * primitive type.
     *
     * <p> There are nine predefined <code>ClassNode</code> objects to represent
     * the eight primitive types and void.  These are created by the Java
     * Virtual Machine, and have the same names as the primitive types that
     * they represent, namely <code>boolean</code>, <code>byte</code>,
     * <code>char</code>, <code>short</code>, <code>int</code>,
     * <code>long</code>, <code>float</code>, and <code>double</code>.
     *
     * @return true if and only if this class represents a primitive type.
     *
     */
	public boolean isPrimitive() {
		return this.isPrimitive;
	}
	
	/**
	 * Determines if the specified <code>ClassNode</code> object represents an
	 * annotation.
	 *
	 * @return true if and only if this class represents an annotation.
	 *
	 */
	public boolean isAnnotation() {
		return this.modifiers.contains(Modifier.ANNOTATION);
	}
	
	/**
     * Determines if the specified <code>ClassNode</code> object represents an
     * inner class.
     *
     * @return true if and only if this class represents an inner class.
     *
     */
	public boolean isInnerClass() {
		return this.modifiers.contains(Modifier.INNERCLASS);
	}
	
	/**
     * Returns <tt>true</tt> if and only if the underlying class
     * is an anonymous class.
     *
     * @return <tt>true</tt> if and only if this class is an anonymous class.
     */
	public boolean isAnonymous() {
		return this.modifiers.contains(Modifier.ANONYMOUS);
	}

	 /**
     * Determines if this <code>ClassNode</code> object represents an array class.
     *
     * @return  <code>true</code> if this object represents an array class;
     *          <code>false</code> otherwise.
     */
	public boolean isArray() {
		
		return name.contains("[");
	
	}

	/**
     * Determines the interfaces implemented by the class or interface
     * represented by this object.
     *
     * <p> If this object represents a class, the return value is a <code>java.util.Set</code>
     * containing objects representing all interfaces implemented by the
     * class.
     * <p> If this object represents an interface, the set contains objects
     * representing all interfaces extended by the interface. 
     *
     * <p> If this object represents a class or interface that implements no
     * interfaces, the method returns an empty set.
     *
     * <p> If this object represents a primitive type or void, the method
     * returns an empty set.
     *
     * @return a <code>java.util.Set</code> containing objects representing all interfaces implemented by the
     * class.
     */
	public Set<ClassNode> getImplementedInterfaces() {
		Set<ClassNode> returnValue = new HashSet<ClassNode>();
		
		for (Relation relation : this.getRelations(TypesOfRelation.IMPLEMENTS)) {
			returnValue.add((ClassNode) relation.getCalledEntity());
		}
		return returnValue;
	}

	/**
     * Gets the <code>PackageNode</code> object that represents 
     * the package of this class.
     *
     * @return the package of the class.
     */
	public PackageNode getPackage() {
		Set<Relation> isInstance = this.getRelations(TypesOfRelation.IS_DECLARED_ON);

		// A class may be declared in another class or in a package
		for (Relation r : isInstance) {
			Entity e = r.getCalledEntity();
			if (e.getTypeOfEntity().equals(TypesOfEntities.PACKAGE)) {
				return (PackageNode) e;
			}
		}
		//FIXME gambiarra :/
		return new PackageNode(this.name.substring(0,this.name.lastIndexOf(".")));
	
	}

	/* (non-Javadoc)
	 * @see designwizard.design.Entity#getClassNode()
	 */
	public ClassNode getClassNode() {
		return this;
	}
	
	/**
     * Determines if the specified <code>ClassNode</code> object represents a
     * class type.
     *
     * @return  <code>true</code> if this object represents a class;
     *          <code>false</code> otherwise.
     */
	public boolean isClass() {
		return !isInterface();
	}
	
	
	/**
     * Determines if the specified <code>ClassNode</code> object represents an
     * interface type.
     *
     * @return  <code>true</code> if this object represents an interface;
     *          <code>false</code> otherwise.
     */
	public boolean isInterface() {
		return super.containsModifiers(Modifier.INTERFACE);
	}
	
	 /**
     * Returns a <code>FieldNode</code> object that reflects the specified field 
     * of the class or interface represented by this <code>ClassNode</code> object. 
     * The <code>name</code> parameter is a <code>String</code> specifying the simple 
     * name of the desired field.
     *
     * @param name the field name
     * @return  the <code>FieldNode</code> object of this class specified by 
     * <code>name</code>
     * @exception InexistentEntityException if a field with the specified name is not found.
     */
	public FieldNode getField(String name) throws InexistentEntityException {
		for (FieldNode field: this.getAllFields()) {
			if (field.getShortName().equals(name))	return field;
		}
		throw new InexistentEntityException(name);
	}

	/**
	 * Adds a inherited method on this <code>ClassNode</code>.
	 * @param methodNode the method to be added.
	 */
	void addInheritedMethod(MethodNode methodNode) {
		this.inheritedMethods.add(methodNode);
	}

	 /**
     * Returns a <code>java.util.Set</code> of <code>MethodNode</code> objects reflecting all the
     * methods declared (excluding the inherited methods) by the class or interface represented by this
     * <code>ClassNode</code> object. The elements in the set returned are not sorted and 
     * are not in any particular order.  This method returns an empty set if the class
     * or interface declares no methods, or if this <code>ClassNode</code> object
     * represents a primitive type, an array class, or void.  The class
     * initialization method <code>&lt;clinit&gt;</code> is not included in the
     * returned set.
     *
     * @return    the set of <code>MethodNode</code> objects representing all the
     * declared methods of this class
     */
	public Set<MethodNode> getDeclaredMethods() {
		return Collections.unmodifiableSet(declaredMethods);
	}

	/**
     * Returns a <code>java.util.Set</code> of <code>MethodNode</code> objects reflecting all the
     * methods inherited by the class or interface represented by this
     * <code>ClassNode</code> object. 
     *
     * @return    the set of <code>MethodNode</code> objects representing all the
     * inherited methods of this class
     */
	public Set<MethodNode> getInheritedMethods() {
		return Collections.unmodifiableSet(inheritedMethods);
	}

	
	 /**
     * Returns a <code>MethodNode</code> object that reflects the specified
     * declared method of the class or interface represented by this
     * <code>ClassNode</code> object. The <code>name</code> parameter is a
     * <code>String</code> that specifies the short name of the desired
     * method. For example, the short name of this method is:
     * <br>
     * <blockquote><pre>
     *  	getDeclaredMethod("equals(java.lang.Object)")
     * </pre></blockquote>
     * <p>
     * 
     * If the the class or interface represented by this
     * <code>ClassNode</code> object does not declare a method
     * with the specified short name, then null is returned.
     * 
     * @param shortName the name of the method.
     * @return the <code>MethodNode</code> object for the method of this class
     * matching the specified short name. If the the class or interface 
     * represented by this <code>ClassNode</code> object does not declare a method
     * with the specified short name, then null is returned.
     */
	public MethodNode getDeclaredMethod(String shortName) {
		
		for (MethodNode method: this.getDeclaredMethods()) {
		
			if (method.getShortName().equals(shortName)) {
			
				return method;
			
			}
		
		}
		
		return null;
	}
	
	 /**
     * Returns a <code>MethodNode</code> object that reflects the specified
     * inherited method of the class or interface represented by this
     * <code>ClassNode</code> object. The <code>name</code> parameter is a
     * <code>String</code> that specifies the short name of the desired
     * method. For example, the short name of this method is:
     * <br>
     * <blockquote><pre>
     *  	getInheritedMethod(java.lang.String)
     * </pre></blockquote>
     * <p>
     * 
     * If the the class or interface represented by this
     * <code>ClassNode</code> object does not have an inherited method
     * with the specified short name, then null is returned.
     * 
     * @param shortName the name of the method.
     * @return the <code>MethodNode</code> object for the method of this class
     * matching the specified short name. If the the class or interface 
     * represented by this <code>ClassNode</code> object does not have a method
     * with the specified short name, then null is returned.
     */
	public MethodNode getInheritedMethod(String shortName) {
		
		for (MethodNode method: this.getInheritedMethods()) {
			if (method.getShortName().equals(shortName)) {
				return method;
			}
		}
		
		return null;
	}

	 /**
     * Returns the  name of the entity (class, interface, array class,
     * primitive type, or void) represented by this <tt>ClassNode</tt> object,
     * as a <tt>String</tt>.
     * 
     * <p> If this class object represents a primitive type or void, then the
     * name returned is the own name (<code>boolean</code>, <code>byte</code>,
     * <code>char</code>, <code>short</code>, <code>int</code>,
     * <code>long</code>, <code>float</code>, and <code>double</code>).
     * 
     *
     * @return  the name of the class or interface
     *          represented by this object.
     */
	public String getClassName() {
		return super.name;
	}
	
	/**
     * Determines the entities that implements the interface
     * represented by this object.
     *
     * @return a set of <code>ClassNode</code> representing the classes that implement
     * this interface.
     */
	public Set<ClassNode> getEntitiesThatImplements() throws NotAnInterfaceException { 
		if (!this.isInterface()) throw new NotAnInterfaceException("The entity "+this.name+" is not an interface");
		Set<Relation> relations = this.getRelations(TypesOfRelation.IS_IMPLEMENTED_BY);
		Set<ClassNode> returnValue = new HashSet<ClassNode>();
		for (Relation relation : relations) {
			returnValue.add((ClassNode) relation.getCalledEntity());
		}
		return returnValue;
	}
	
	/**
	 * Verifies if this entity is equals the other entity.
	 * @return true if this entity is equals the other entity or false if not.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		try {
			ClassNode parameterEntity = (ClassNode)other;
			return this.name.equals(parameterEntity.getName());
		} catch (ClassCastException e) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
	 * the methods that reference the class or interface represented by this <ClassNode>.
	 */
	public Set<MethodNode> getCallerMethods() {
		
		Set<MethodNode> result = new HashSet<MethodNode>();
		
		for (MethodNode method : this.getAllMethods()) {
		
			result.addAll(method.getCallerMethods());
		
		}
		
		// when the method load the class.
		Set<Relation> loaded = this.getRelations(TypesOfRelation.IS_INVOKED_BY);
		
		for (Relation relation: loaded) {
			
			Entity e = relation.getCalledEntity();
			if (e instanceof MethodNode) {
				result.add((MethodNode)e);
			}
		
		}
		
		return result;
	}

	@Override
	public Set<MethodNode> getCalleeMethods() {
		
		Set<MethodNode> methods = this.getAllMethods();
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		
		for(MethodNode method: methods) {
	
			feedBack.addAll(method.getCalleeMethods());
		
		}
		
		return feedBack;
		
	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes that reference the class or interface represented by this ClassNode.
	 */
	@Override
	public Set<ClassNode> getCallerClasses() {
		
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		Set<Relation> relations = this.getRelations(TypesOfRelation.CONTAINS);
		
		for (Relation relation: relations) {
		
			Entity e = relation.getCalledEntity();
			feedBack.addAll(e.getCallerClasses());
		
		}

		//classes that load this class
		Set<Relation> set = this.getRelations(TypesOfRelation.IS_INVOKED_BY);
		
		for (Relation relation : set) {
		
			Entity e = relation.getCalledEntity();
			if (e instanceof MethodNode) {
				MethodNode method = (MethodNode) relation.getCalledEntity();
				if (method.getDeclaringClass() != null)	feedBack.add(method.getDeclaringClass());
			} else if (e instanceof ClassNode) {
				feedBack.add((ClassNode)e);
			}
		}
		
		return feedBack;
	
	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes that are referenced by the class or interface represented by this ClassNode.
	 */
	@Override
	public Set<ClassNode> getCalleeClasses() {
		
		Set<MethodNode> methods = this.getAllMethods();
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		
		for(MethodNode method: methods) {
	
			feedBack.addAll(method.getCalleeClasses());
		
		}
		
		return feedBack;
	
	}

	@Override
	public Set<PackageNode> getCalleePackages() {
		
		Set<PackageNode> feedBack = new HashSet<PackageNode>();
		
		Set<ClassNode> calleeClasses = this.getCalleeClasses();
		
		for (ClassNode c : calleeClasses) {
		
			feedBack.add(c.getPackage());
		
		}
				
		return feedBack;
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
	
	/**
	 * Checks whether the class represented by this <code>ClasNode</code> object
	 * extends the parameter class.
	 * @param classNode The class to be verified as super class of this <code>ClassNode</code>.
	 * @return true whether this class extends the parameter class; false otherwise.
	 */
	public boolean extendsClass(ClassNode classNode) {
		return this.getSuperClass().equals(classNode);
	}
	
	/**
	 * Checks whether the class represented by this <code>ClasNode</code> object
	 * implements the parameter interface.
	 * @param interfaceClassNode The interface to be verified.
	 * @return true whether this class implements the parameter interface; false otherwise.
	 */
	public boolean implementsInterface(ClassNode interfaceClassNode) {
		return this.getImplementedInterfaces().contains((interfaceClassNode));
	}
	
}