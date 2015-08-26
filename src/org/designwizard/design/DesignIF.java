package org.designwizard.design;

import java.util.Collection;
import java.util.Set;

import org.designwizard.design.relation.Relation.TypesOfRelation;
import org.designwizard.exception.InexistentEntityException;


/**
 * Interface for Design. A Design is a model for the facts extracted from .class file and source code.
 * @author Jo�o Arthur Brunet Monteiro - joaoarthurbm@gmail.com
 */
public interface DesignIF {
	
	/**
	 * Identifiers for methods and fields.
	 */
	public static final String METHOD_IDENTIFIER = "M:";
	public static final String STATIC_IDENTIFIER = "S:";
	public static final String FIELD_IDENTIFIER = "F:";
	
	/**
	 * Adds a new <code>Relation</code> on the <code>Design</code>.
	 * @param type the type of the <code>Relation</code>.
	 * @param caller the caller of the <code>Relation</code>.
	 * @param called the called of the <code>Relation</code>.
	 */
    public void addRelation( TypesOfRelation type, String caller, String called );

	/**
	 * Method that return the entity with the specified name.
	 * @param entityName the name of the entity to be looked for.
	 * @return the entity with the specified name.
	 */
	public Entity getEntity(String name) throws InexistentEntityException; 

	
	/**
	 * Adds a class that was extrated from code.
	 * @param className the name of the class extracted.
	 */
	public void addClassExtractedFromCode(String className);

	/**
	 * Put the visibility value on visibility of the specified method.
	 * @param method 
	 * @param visibility 
	 * @throws InexistentEntityException
	 */
	public void setVisibility(String method, Modifier visibility) throws InexistentEntityException;

	/**
	 * Put the retunrType value on return type of the specified method.
	 * @param method 
	 * @param visibility 
	 * @throws InexistentEntityException
	 */
	public void setReturnType(String m, String returnType) throws InexistentEntityException;
	
	public void setParameters(String m, String parameters) throws InexistentEntityException;

	/**
	 * Returns a ClassEntity represented by the specified className.
	 * @param className - the name of the class.
	 * @return a ClassEntity represented by the specified className.
	 * @throws InexistentEntityException 
	 */
	public ClassNode getClass(String className) throws InexistentEntityException;

	/**
	 * Returns a Field with the specified name.
	 * @param fieldName the name of the field.
	 * @return a Field with the specified name.
	 * @throws InexistentEntityException
	 */
	public FieldNode getField(String fieldName) throws InexistentEntityException;

	/**
	 * Returns a Method with the specified name.
	 * @param methodName the name of the method.
	 * @return a Method with the specified name.
	 * @throws InexistentEntityException
	 */
	public MethodNode getMethod(String methodName) throws InexistentEntityException;

	/**
	 * Returns a <code>Set</code> containing <code>Class</code> objects that 
	 * represents all classes from code. 
	*/
	public Set<ClassNode> getAllClassesFromCode();

	/**
	 * Resolve all dependences between entities after extraction.
	 */
	public void resolveDependences();

	public Collection<PackageNode> getAllPackagesFromCode();

	public void packageExtracted(String entity);

	public PackageNode getPackage(String fullyQualifiedNamePackage) throws InexistentEntityException;
	
	public boolean containsEntity(String entityName);
	
	public void annotationExtracted(String entity);
}