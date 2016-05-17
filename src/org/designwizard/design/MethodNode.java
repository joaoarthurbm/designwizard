package org.designwizard.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.designwizard.common.Config;
import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;
import org.designwizard.exception.NotAnInterfaceException;

/**
 * A <code>MethodNode</code> provides information about a single method or a constructor
 * on a class or interface. 
 * 
 * To get access to a desired method extracted, do not use the constructor of this class. Instead, use the
 * class <code>DesignWizard</code> as it follows:
 * 
 * <blockquote><pre>
 *  	DesignWizard dw = new DesignWizard("/home/user/application/classes");
 *		MethodNode c = dw.getMethod("MyAplicationClassExample.method(java.lang.String,int)");  
 * </pre></blockquote>
 * 
 * @see org.designwizard.design.ClassNode
 * @see org.designwizard.design.ClassNode#getAllMethods()
 * @see org.designwizard.design.ClassNode#getMethods(Modifier...)
 * @see org.designwizard.design.ClassNode#getDeclaredMethods()
 * @see org.designwizard.design.ClassNode#getInheritedMethods()
 * @see org.designwizard.design.ClassNode#getDeclaredMethod(String)
 * @see org.designwizard.design.ClassNode#getInheritedMethod(String)
 *
 * @author Joao Arthur Brunet Monteiro - joaoarthurbm@gmail.com
 */
public class MethodNode extends AbstractEntity implements Entity {

	private ClassNode returnType;
	private boolean isConstructor;
	public final static String CONSTRUCTOR_IDENTIFIER = "<init>";
	private Set<ClassNode> parameters;
	
	/**
	 * Creates a new Method.
	 * @param signature the signature of the Method. The signature is composed by its name and parameters.
	 * Example: ClassXPTO.setName(java.lang.String)
	 * @param isConstructor true if the method is a constructor; false otherwise.
	 */
	public MethodNode(String signature, boolean isConstructor) {
		
		super();
		super.name = signature;
		this.isConstructor = isConstructor;
		super.type = TypesOfEntities.METHOD;
		super.relations = new HashMap<TypesOfRelation,Set<Relation>>();
		
	}
	
	/**
     * Returns the short name of the method represented by this <code>MethodNode</code> 
     * object, as a <code>String</code>.
     */
	public String getShortName() {
		String signature = this.name.substring(this.name.indexOf("("));
		
		String shortName = this.name.substring(0,this.name.indexOf("("));
		shortName = shortName.substring(shortName.lastIndexOf(".") + 1);
		
		return shortName + signature;
	}
	
	/**
	 * Verifies whether this <code>MethodNode</code> is a constructor.
	 * @return true if this <code>MethodNode</code> object is a constructor; false otherwise.
	 */
	public boolean isConstructor() {
		return this.isConstructor;
	}
	
	 /**
     * Returns a <code>ClassNode</code> object that represents the formal return type
     * of the method represented by this <code>MethodNode</code> object.
     * 
     * @return the return type for the method this object represents
     */
	public ClassNode getReturnType() {
		return this.returnType ;
	}
	
	/**
     * Returns the <code>ClassNode</code> object representing the class or interface
     * that declares the method represented by this <code>MethodNode</code> object.
     */
	public ClassNode getDeclaringClass() {
		Set<Relation> declaredRelation = this.getRelations(TypesOfRelation.IS_DECLARED_ON);
		if (!declaredRelation.isEmpty()) {
			return (ClassNode) declaredRelation.iterator().next().getCalledEntity();
		}
		return null;
	}
		
	/**
	 * Sets the typeReturn of this <code>Method</code>.
	 * @param returnTypeClass the new value that the typeReturn of this <code>Method</code> 
	 * will receive.
	 */
	void setReturnType(ClassNode returnTypeClass) {
		this.returnType = returnTypeClass;
	}
	
	/**
	 * Returns a set that contains the invoke relations of this method.
	 * @return a set that contains the invoke relations of this method.
	 */
	Set<Relation> getInvokeRelations() {
		Set<Relation> feedBack = new HashSet<Relation>();
		feedBack.addAll(this.getRelations(TypesOfRelation.INVOKEINTERFACE));
		feedBack.addAll(this.getRelations(TypesOfRelation.INVOKESPECIAL));
		feedBack.addAll(this.getRelations(TypesOfRelation.INVOKESTATIC));
		feedBack.addAll(this.getRelations(TypesOfRelation.INVOKEVIRTUAL));
		feedBack.addAll(this.getRelations(TypesOfRelation.LOAD));
		return feedBack;
	}
	
	/**
	 * Returns a set that contains the invoke relations of this method.
	 * @return a set that contains the invoke relations of this method.
	 */
	Set<Relation> getAccessRelations() {
		Set<Relation> attRelations = new HashSet<Relation>();
		attRelations.addAll(this.getRelations(TypesOfRelation.GETFIELD));
		attRelations.addAll(this.getRelations(TypesOfRelation.GETSTATIC));
		attRelations.addAll(this.getRelations(TypesOfRelation.PUTFIELD));
		attRelations.addAll(this.getRelations(TypesOfRelation.PUTSTATIC));
		return attRelations;
	}
	
	/**
	 * Returns a Set<String> that contains the name of Methods and Fields used by this method.
	 * @return a Set<String> that contains the name of Methods and Fields used by this method.
	 */ 
	private Set<Entity> getEntitiesUsedBy() {
		Set<Entity> feedBack = new HashSet<Entity>();
		Set<Relation> invokeRelations = this.getInvokeRelations();
		
		for (Relation relation: invokeRelations) {
			feedBack.add(relation.getCalledEntity());
		}
		
		Set<Relation> accessRelations = this.getAccessRelations();
		
		for (Relation relation: accessRelations) {
			feedBack.add(relation.getCalledEntity());
		}
		return feedBack;
	}

	/**
	 * @param entities
	 * @return
	 */
	private Set<ClassNode> extractClasses(Set<MethodNode> entities) {
	
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
	
		for (MethodNode entity: entities) {
			feedBack.add(entity.getDeclaringClass());
		}
	
		return feedBack;
	
	}
	
	/**
	 * Verifies whether this <code>MethodNode</code> is static or not.
	 * @return true if this method is static; false otherwise.
	 */
	public boolean isStatic() {
		return this.containsModifiers(Modifier.STATIC);
	}
	
	/**
     * Returns a <code>java.util.Set</code> of <code>ClassNode</code> objects that represent 
     * the types of the exceptions catched by the underlying method
     * represented by this <code>MethodNode</code> object. Returns an empty set
     * if the method catches no exceptions.
     * 
     * @return a <code>java.util.Set</code> of <code>ClassNode</code> objects that represent 
     * the types of the exceptions catched by the underlying method
     * represented by this <code>MethodNode</code> object.
     */
	public Set<ClassNode> getCatchedExceptions() {
		
		Set<ClassNode> feedBack = new HashSet<ClassNode>();

		Set<Relation> catchRelations = this.relations.get(TypesOfRelation.CATCH);
		if (catchRelations == null) return new HashSet<ClassNode>();
		
		for (Relation relation: catchRelations) {
		
			feedBack.add((ClassNode) relation.getCalledEntity());
		
		}
		
		return feedBack;
		
	}

	/**
	 * Return all methods impacted by a change in this method.
	 * The depth of the search is configured on designwizard.properties
	 * @return All pairs(called/caller) of methods. The trace generated by this method.
	 */
	public List<String[]> getImpactOfAChange() {
		ArrayList<String[]> result = new ArrayList<String[]>();
		build(result,Config.getInstance().getDepthOfAnalysis(),this);
		resolveInterfaceProblems(result,this);
		return result;
	}

	//FIXME resolver problemas de herança
	private void resolveInterfaceProblems(ArrayList<String[]> result, MethodNode method) {
		
		ClassNode classNode = method.getDeclaringClass();
		
		try {
		
			if (classNode.isInterface()) {
			
				Set<ClassNode> entitiesThatImplements = classNode.getEntitiesThatImplements();
				MethodNode methodImplements = null;
				
				for (ClassNode classNodeImpl : entitiesThatImplements) {
				
					methodImplements = classNodeImpl.getDeclaredMethod(this.getShortName());
					
					if (methodImplements != null) {
					
						build(result,Config.getInstance().getDepthOfAnalysis()-1,methodImplements); 
						resolveInterfaceProblems(result, methodImplements);
					
					}
				
				}
			
			}
		} catch (NotAnInterfaceException e) {
			
			e.printStackTrace();
		
		}
	}
	
	/**
	 * Build the trace of a method.
	 * @param list
	 * @param deep
	 * @param method
	 */
	private void build(ArrayList<String[]> list, int deep, MethodNode method) {
		
		if (deep == 0) return;
		
		Set<MethodNode> methods = method.getCallerMethods();
		
		for (MethodNode methodBL : methods) {
		
			String[] pair = new String[2];
			pair[0] = method.getName();
			pair[1] = methodBL.getName();
			
			list.add(pair);
			build(list,deep-1,methodBL);
		
		}
	
	}
	
	/**
     * Returns a <code>java.util.Set</code> of <code>ClassNode</code> objects that represent 
     * the types of the exceptions declared to be thrown
     * by the underlying method
     * represented by this <code>Method</code> object.  Returns an empty set if the method declares no 
     * exceptions in its <code>throws</code> clause.
     * 
     * @return the exception types declared as being thrown by the
     * method this object represents
     */
	public Set<ClassNode> getThrownExceptions() {
		
		Set<Relation> exceptions = super.getRelations(TypesOfRelation.THROWS);
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		
		for (Relation exception: exceptions) {
			feedBack.add((ClassNode) exception.getCalledEntity());
		}
		
		return feedBack;
	}

	/**
	 * Returns a <code>Set</code> of <code>FieldNode</code> objects representing the 
	 * fields accessed by this <code>MethodNode</code>.
	 */
	public Set<FieldNode> getAccessedFields() {
		Set<FieldNode> feedBack = new HashSet<FieldNode>();
		Set<Relation> accessRelations = this.getAccessRelations();
		FieldNode field = null;
		for (Relation relation: accessRelations) {
			field = (FieldNode) relation.getCalledEntity();
			feedBack.add(field);
		}
		return feedBack;
	}
	
	/**
	 * Verifies if this entity is equals the other entity.
	 * @return true if this entity is equals the other entity; false otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (!(other instanceof MethodNode)) {
			return false;
		}
		MethodNode parameterEntity = (MethodNode)other;
		return this.name.equals(parameterEntity.getName()); 
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
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

	public ClassNode getClassNode() {
	
		return this.getDeclaringClass();
	
	}

	public String getClassName() {
		
		ClassNode node = this.getDeclaringClass();
		
		if (node == null) {
		
			String returnValue = this.getName().substring(0,this.getName().lastIndexOf("("));
			return returnValue.substring(0,returnValue.lastIndexOf("."));
		
		} else return node.getName();
	
	}

	public void setParameters(Set<ClassNode> methodParameters) {

		this.parameters = methodParameters;

	}
	
	/**
     * Returns a <code>java.util.Set</code> of <code>ClassNode</code> objects that represent the formal
     * parameter types of the method represented by this <code>Method</code> object.  Returns an empty set
     * if the underlying method takes no parameters.
     * 
     * @return the parameter types for the method this object
     * represents
     */
	public Set<ClassNode> getParameters() {
	
		return this.parameters;
	
	}
	
	/**
     * Gets the <code>PackageNode</code> object that represents 
     * the package of this method.
     *
     * @return the package of the method.
     */
	public PackageNode getPackage() {
	
		return this.getDeclaringClass().getPackage();
	
	}
	
	/**
	 * Returns the Methods that calls this method.
	 * @return returns the Methods that calls this method.
	 */
	public Set<MethodNode> getCallerMethods() {
		
		Set<MethodNode> callerMethods = new HashSet<MethodNode>();
		Set<Relation> invonkedRelations = this.getRelations(TypesOfRelation.IS_INVOKED_BY);
		
		for (Relation aux: invonkedRelations) {
		
			callerMethods.add((MethodNode) aux.getCalledEntity());	
		
		}
	
		return callerMethods;
	} 
		
	/**
	 * Returns the methods that are called by this MethodNode.
	 * @return the methods that are called by this MethodNode.
	 */
	public Set<MethodNode> getCalleeMethods() {

		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		Set<Relation> invokeRelations = this.getInvokeRelations();
		
		for (Relation relation: invokeRelations) {
			
			if (relation.getCalledEntity().getTypeOfEntity().equals(TypesOfEntities.METHOD) && 
					!relation.getCalledEntity().getName().equals("java.lang.Object.<init>()")) {
				
				feedBack.add((MethodNode) relation.getCalledEntity());
			
			}
		
		}
		
		return feedBack;
	}
	
	/**
	 * Returns the classes that reference this Method.
	 * @return A set that contains all classes that uses this Method.
	 */
	@Override
	public Set<ClassNode> getCallerClasses() {
	
		Set<MethodNode> methods = this.getCallerMethods();
		return extractClasses(methods);
	
	}
	
	/**
	 * Returns the classes that are referenced by this <code>MethodNode</code>
	 */
	@Override
	public Set<ClassNode> getCalleeClasses() {
	
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		
		Set<Entity> set = this.getEntitiesUsedBy();
		set.addAll(getThrownExceptions());
		set.addAll(getCatchedExceptions());
		
		ClassNode classNode = null; 
		for (Entity entity: set) {
		
			classNode = entity.getClassNode();
			//TODO refatorar
			if (classNode == null) {
			
				classNode = new ClassNode(entity.getClassName());
			
			}
			
			feedBack.add(classNode);
		
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

}