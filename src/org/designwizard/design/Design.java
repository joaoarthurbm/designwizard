package org.designwizard.design;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.designwizard.design.Entity.TypesOfEntities;
import org.designwizard.design.factory.AbstractElementsFactory;
import org.designwizard.design.factory.ElementsFactory;
import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.exception.NotAnInterfaceException;

/**
 * @author João Arthur Brunet Monteiro - joaoarthurbm@gmail.com
 * 
 * This class represents a model for the facts extracts from .jar file and source code.
 */
public class Design implements DesignIF {

	/**
	 * Attributes
	 */
	private Map<String,Entity> entities;
	private Set<ClassNode> classesExtracted;
	private AbstractElementsFactory factory = ElementsFactory.getInstance();

	/**
	 * Constructs a new Design. A design is a set of entities and all the relations between this entities. 
	 */
	public Design() {
		
		this.entities = new  HashMap<String,Entity>();
		this.classesExtracted = new HashSet<ClassNode>();
	
	}

	/**
	 * Adds a class that was extracted from code.
	 * @param className the name of the class extracted.
	 */
	public void addClassExtractedFromCode(String className){
		
		String toPut = className.replaceAll("[/\\\\]+", ".");
		
		try {
		
			if (!this.entities.containsKey(toPut)) {
			
				this.entities.put(toPut, new ClassNode(toPut));
			
			}
			
			this.classesExtracted.add((ClassNode) this.getEntity(toPut));
		
		} catch (InexistentEntityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void packageExtracted(String entity) {
		
		if (!this.entities.containsKey(entity)) {
		
			this.entities.put(entity, new PackageNode(entity));
		
		}
	
	}
	
	@Override
	public void annotationExtracted(String entityName) {
		if (!this.entities.containsKey(entityName)) {
			ClassNode annotation = new ClassNode(entityName);
			annotation.modifiers.add(Modifier.ANNOTATION);
			this.entities.put(entityName, annotation);
		} else {
			// If the entity extracted without the annotation modifier and It was using as Annotation.
            ClassNode classNode = (ClassNode) this.entities.get(entityName);
            if (classNode != null && !classNode.isAnnotation()) {
                classNode.modifiers.add(Modifier.ANNOTATION);
            }
        }
	}
	
	public Set<ClassNode> getAllAnnotations() {
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		for (Entity entity: this.entities.values()) {
			if (entity.getModifiers().contains(Modifier.ANNOTATION)) {
				feedBack.add((ClassNode) entity);
			}
		}
		return feedBack;
	}
	
	/**
	 * Returns a <code>AnnotationNode</code> represented by the specified annotationName.
	 * @param annotationName the name of the class.
	 * @return a <code>AnnotationNode</code> represented by the specified annotationName or <code>null</code>.
	 * @throws InexistentEntityException if the annotation cannot be located.
	 */
	public ClassNode getAnnotation(String annotationName) throws InexistentEntityException {
		ClassNode classNode = (ClassNode) this.getEntity(annotationName);
		if (classNode != null && classNode.isAnnotation()) {
			return classNode;
		}
		return null;
	}

	/**
	 * Adds a new relation in the design.
	 * 
	 * pattern:
	 * 	- Methods: M:Class.method(int,double,...)
	 *  - Fields: F:Class.fieldName
	 *  - Class: package.subpackage.ClassName
	 *  
	 *  ps1.: Varargs must be describe as an array.
	 *  ps2.: It is not necessary to describe generics.
	 *  ps3.: put S: for static methods. Example: S:M:Main.main(String[])
	 *  
	 * @param typeOfRelation the relation's type to be added.
	 * @param callerName the caller of the relation.
	 * @param calledName the called of the relation.
	 */
	public void addRelation(TypesOfRelation typeOfRelation, String callerName, String calledName) {
		
		Entity caller = null,called = null;
		caller = factory.createEntity(callerName);
		called = factory.createEntity(calledName);
		Relation relation = factory.createRelation(typeOfRelation, caller, called);

		this.addNewEntities(caller, called, relation);

		Relation reverseRelation = this.resolveReverseRelation(caller,called,typeOfRelation);
		if (reverseRelation != null) this.addNewEntities(called, caller,reverseRelation);
	
	}

	/**
	 * Creates the reverse relation for the entities and adds them in the Design.
	 * @return The reverse relation or null if the there is no reverse relation for the specified type.
	 */
	 //TODO especificar quais são as reverse relations.
	private Relation resolveReverseRelation(Entity caller, Entity called, TypesOfRelation typeOfRelation) {
		TypesOfRelation typeReverseRelation = typeOfRelation.getReverseRelation();
		return (typeReverseRelation != null) ? factory.createRelation(typeReverseRelation, called, caller) : null;
	}

	/**
	 * Adds new entities in the Design.
	 * @param caller the entity that will be added as a caller of the specified relation.
	 * @param called the entity that will be added as a called of the specified relation.
	 * @param relation the relation to be added.
	 * @param typeOfRelationString the type of the relation to be added.
	 */
	private void addNewEntities(Entity caller, Entity called, Relation relation) {

		if (this.containsEntity(caller.getName())) {
			Entity entity = this.entities.get(caller.getName());
			if ( this.containsEntity(called.getName())) {
				Entity c = this.entities.get(called.getName());
				entity.addRelation(new Relation(entity,c,relation.getType()));
			} else {
				entity.addRelation(relation);
				this.entities.put(called.getName(),called);
			}
		}
		else {
			if ( this.containsEntity(called.getName())) {
				Entity c = this.entities.get(called.getName());
				caller.addRelation(new Relation(caller,c,relation.getType()));
			} else {
				caller.addRelation(relation);
				this.entities.put(called.getName(),called);
			}
			this.entities.put(caller.getName(),caller);
		}
	}

	/* (non-Javadoc)
	 * @see designwizard.design.DesignIF#resolveDependeces()
	 */
	//FIXME it should not be public
	public void resolveDependences() {
		try {
			resolveInheritedFieldsAndMethods(this.getClass(Object.class.getName()));
			resolveRelationsBetweenInheritedEntities();
			resolveInterfaces();
		} catch (InexistentEntityException e) {
			// TODO tomar decisões
			e.printStackTrace();
		}
	}

	private void resolveInterfaces() {
		for (ClassNode classNode : this.getAllClasses()) {
			if (classNode.isInterface()) {
				try {
					Set<ClassNode> implementsIf = classNode.getEntitiesThatImplements();
					for (ClassNode impl : implementsIf) {
						if (impl.isInterface()) {
							for (MethodNode methodNode: classNode.getAllMethods()) {
								MethodNode aux = (MethodNode) this.factory.createEntity(DesignIF.METHOD_IDENTIFIER+impl.getName()+"."+methodNode.getShortName());
								this.addNewEntities(impl, aux , factory.createRelation(TypesOfRelation.CONTAINS, impl, aux));
								this.addNewEntities(aux, impl, this.resolveReverseRelation(impl, aux,TypesOfRelation.CONTAINS ));
								
								this.getMethod(impl.getName()+"."+methodNode.getShortName()).setReturnType(methodNode.getReturnType());
								
								impl.addInheritedMethod(this.getMethod(impl.getName()+"."+methodNode.getShortName()));
							}
						}
					}
				} catch (NotAnInterfaceException e) {
				} catch (InexistentEntityException e) {
//					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Adds the inherited fields and methods on the subclasses of <code>ClassNode</code>.
	 */
	private void resolveInheritedFieldsAndMethods(ClassNode classNode) {
		
		Set<ClassNode> subClasses = classNode.getSubClasses();
		for (ClassNode subClass : subClasses) {
			Set<FieldNode> fields = classNode.getAllFields();
			for (FieldNode field : fields) {
				subClass.addInheritedField(field);
			}
			Set<MethodNode> methods = classNode.getAllMethods();
			for (MethodNode methodNode : methods) {
				subClass.addInheritedMethod(methodNode);
			}
			resolveInheritedFieldsAndMethods(subClass);
		}
		
	}
	

	
	private void resolveRelationsBetweenInheritedEntities() {
		MethodNode method = null;
		for (Entity entity: this.entities.values()) {
			if (entity.getTypeOfEntity().equals(Entity.TypesOfEntities.METHOD)) {
				method = (MethodNode) entity;
				Set<Relation> relations = method.getInvokeRelations();
				relations.addAll(method.getAccessRelations());

				Entity withoutParentClass = null;
				Entity fromSuperClass = null;
				String className = null;
				ClassNode subClass = null;
				ClassNode superClass = null;

				for (Relation relation : relations) {
					if (!relation.getCalledEntity().getTypeOfEntity().equals(TypesOfEntities.CLASS)) {
						withoutParentClass = relation.getCalledEntity();
						if (withoutParentClass.getClassNode() == null) {
							className = withoutParentClass.getClassName();
							if (this.isClassFromCode(className)) {
								try {
									subClass = this.getClass(className);
								
									superClass = subClass.getSuperClass();
	
									if (withoutParentClass.getName().contains("$") || superClass == null || !this.isClassFromCode(superClass.getName())) continue;
	
									entity.removeRelation(relation);
									Relation reverseRelation = this.resolveReverseRelation(relation.getCallerEntity(), relation.getCalledEntity(), relation.getType());
									entity.removeRelation(reverseRelation);
	
									if (withoutParentClass.getTypeOfEntity().equals(TypesOfEntities.METHOD)) {
										fromSuperClass = superClass.getDeclaredMethod(withoutParentClass.getShortName());
										
										if (fromSuperClass != null)									
											this.addRelation(relation.getType(), DesignIF.METHOD_IDENTIFIER+method.getName(), DesignIF.METHOD_IDENTIFIER+fromSuperClass.getName());
									} else {
										fromSuperClass = superClass.getField(withoutParentClass.getShortName());
										this.addRelation(relation.getType(), DesignIF.METHOD_IDENTIFIER+method.getName(), DesignIF.FIELD_IDENTIFIER+fromSuperClass.getName());
									}
								} catch (InexistentEntityException e) {
//									e.printStackTrace();
								}
							}
						}
					}

				}

			}
		}
	}
	
	/**
	 * Verifies whether the class with the specified className if from the application's code extracted.
	 * @param className the class's name that it will be checked.
	 * @return True if class from the application's code. 
	 */
	private boolean isClassFromCode(String className) {
		boolean isClassFromCode = false;
		for (ClassNode classNode : this.getAllClassesFromCode()) {
			if (classNode.getName().equals(className)) {
				isClassFromCode = true;
				break;
			}
		}
		return isClassFromCode;
	}

	/**
	 * Verifies if this design has a entity with the parameter entityName.
	 * @param entityName the name of the entity. 
	 * @return true if the entity with the name equals the parameter exists in this <code>Design</code> or false if not.
	 */
	public boolean containsEntity(String entityName) {
		return this.entities.containsKey(entityName);
	}

	/* (non-Javadoc)
	 * @see designWizard.design.DesignIF#setVisibility(java.lang.String, designWizard.design.TypesOfVisibility)
	 */
	public void setVisibility(String entityName, Modifier visibility) throws InexistentEntityException {
		Entity entity = this.getEntity(entityName);
		entity.addModifier(visibility);
	}


	/* (non-Javadoc)
	 * @see designWizard.design.DesignIF#setReturnType(java.lang.String, java.lang.String)
	 */
	public void setReturnType(String method, String returnType) throws InexistentEntityException {
		MethodNode methodNode = this.getMethod(method);
		ClassNode returnTypeNode;

		if (!this.containsEntity(returnType)) {
			returnTypeNode = new ClassNode(returnType);
		} else {
			returnTypeNode = this.getClass(returnType);
		}
		methodNode.setReturnType(returnTypeNode);
	}
	
	/* (non-Javadoc)
	 * @see org.designwizard.design.DesignIF#setParameters(java.lang.String, java.lang.String)
	 */
	public void setParameters(String method, String parameters) throws InexistentEntityException {
		MethodNode methodNode = this.getMethod(method);
		ClassNode parameter;
		Set<ClassNode> methodParameters = new HashSet<ClassNode>();
		Scanner sc = new Scanner(parameters);
		sc.useDelimiter(",");
		String nextParameter;
		while(sc.hasNext()){
			nextParameter = sc.next();
			if (!this.containsEntity(nextParameter)) {
				parameter = new ClassNode(nextParameter);
			} else {
				parameter = this.getClass(nextParameter);
			}
			methodParameters.add(parameter);
		}
		methodNode.setParameters(methodParameters);
		sc.close();
	}

	/**
	 * Method that return the entity with the specified name.
	 * @param entityName the name of the entity to be looked for.
	 * @return the entity with the specified name.
	 * @throws InexistentEntityException 
	 */
	public Entity getEntity(String entityName) throws InexistentEntityException {
		if (this.entities.get(entityName) == null) throw new InexistentEntityException(entityName);
		else return this.entities.get(entityName);
	}

	/**
	 * Returns a ClassEntity represented by the specified className.
	 * @param className - the name of the class.
	 * @return a ClassEntity represented by the specified className.
	 * @throws InexistentEntityException 
	 */
	public ClassNode getClass(String className) throws InexistentEntityException {
		return (ClassNode) this.getEntity(className);
	}

	/**
	 * Returns a Method with the specified name.
	 * @param methodName the name of the method.
	 * @return a Method with the specified name.
	 * @throws InexistentEntityException
	 */
	public MethodNode getMethod(String methodName) throws InexistentEntityException {
		return (MethodNode) this.getEntity(methodName);
	}

	/**
	 * Returns a Field with the specified name.
	 * @param fieldName the name of the field.
	 * @return a Field with the specified name.
	 * @throws InexistentEntityException
	 */
	public FieldNode getField(String fieldName) throws InexistentEntityException {
		return (FieldNode) this.getEntity(fieldName);
	}

	/**
	 * Returns all the classes from the application's code.
	 * @return A set with all the classes from the application's code.
	 */
	public Set<ClassNode> getAllClassesFromCode() {
		return this.classesExtracted;
	}

	/**
	 * Returns a <code>Set</code> of <code>ClassNode</code> objects representing all classes extracted. This includes those
	 * classes from java and libraries used by the code extracted. Realize that classes from java are not entirely extracted.
	 * If you need only the classes from your source code, you must call the {@link Design#getAllClassesFromCode()}.
	 * @return a <code>Set</code> of <code>ClassNode</code> objects representing all the classes extracted.
	 */
	public Set<ClassNode> getAllClasses() {
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		for (Entity entity: this.entities.values()) {
			if (entity.getTypeOfEntity().equals(TypesOfEntities.CLASS)) {
				feedBack.add((ClassNode) entity);
			}
		}
		return feedBack;
	}
	
	/**
     * Returns the set of <code>Entity</code> with the annotated classes to the entity
     * represented by this <code>annotationName</code>.
     * @param annotationName The name of the entity that It is an annotation.
     *
     * @return the set of the annotated classes or <code>null</code> if this parameter wasn't an annotation.
     */
	public Set<Entity> getEntitiesAnnotatedBy(String annotationName) throws InexistentEntityException {
        ClassNode annotationNode = this.getAnnotation(annotationName);
        if (annotationNode != null) {
			return annotationNode.getEntitiesAnnotatedBy();
        }
        return null;
    }
	
	
	/**
	 * A String representation for this Design.
	 */
	@Override
	public String toString(){
		StringBuffer st = new StringBuffer();

		for (ClassNode classEntity : this.getAllClasses()) {
			st.append(classEntity.toString());
		}
		return st.toString();
	}


	@Override
	public Set<PackageNode> getAllPackagesFromCode() {
		Set<PackageNode> feedBack = new HashSet<PackageNode>();
		for (Entity entity : this.entities.values()) {
			if (entity.getTypeOfEntity().equals(TypesOfEntities.PACKAGE)) feedBack.add((PackageNode) entity);
		}
		return feedBack;
	}

	@Override
	public PackageNode getPackage(String fullyQualifiedNamePackage) throws InexistentEntityException {
		return (PackageNode) this.getEntity(fullyQualifiedNamePackage);
	}

	public Set<MethodNode> getAllMethods() {
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		for (ClassNode c : this.getAllClasses()) {
			feedBack.addAll(c.getAllMethods());
		}
		return feedBack;
	}
	
}





