package org.designwizard.design;

import java.util.Collection;
import java.util.Set;

import org.designwizard.design.relation.Relation.TypesOfRelation;
import org.designwizard.exception.InexistentEntityException;

/**
 * Interface for Design. A Design is a model for the facts extracted from .class file and source code.
 * @author Joao Arthur Brunet Monteiro - joaoarthurbm@gmail.com
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
    public void addRelation(TypesOfRelation type, String caller, String called);

	/**
	 * Method that return the entity with the specified name.
	 * @param name the name of the entity to be looked for.
	 * @return the entity with the specified name.
	 */
	public Entity getEntity(String name) throws InexistentEntityException; 

	/**
	 * Adds a class that was extracted from code.
	 * @param className the name of the class extracted.
	 */
	public void addClassExtractedFromCode(String className);

	/**
	 * Put the visibility value on visibility of the specified method.
	 * @param method The method's name on design. 
	 * @param visibility The visibility value in the {@link Modifier}.
	 * @throws InexistentEntityException If there isn't method.
	 */
	public void setVisibility(String method, Modifier visibility) throws InexistentEntityException;

	/**
	 * Put the returnType value on return type of the specified method.
	 * @param method The method's name on design.
	 * @param returnType The return type to the specified method.
	 * @throws InexistentEntityException If there isn't method.
	 */
	public void setReturnType(String method, String returnType) throws InexistentEntityException;
	
	/**
	 * Put the parameters value on parameters of the specified method.
	 * @param method The method's name on design.
	 * @param parameters The list of parameters.
	 * @throws InexistentEntityException If there isn't method.
	 */
	public void setParameters(String method, String parameters) throws InexistentEntityException;

	/**
	 * Returns a ClassEntity represented by the specified className.
	 * @param className the name of the class.
	 * @return a ClassEntity represented by the specified className.
	 * @throws InexistentEntityException If there isn't class.
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
	 * Returns a <code>Set</code> containing <code>ClassNode</code> objects that 
	 * represents all classes from code. 
	 * @return All classes from application's code.
	*/
	public Set<ClassNode> getAllClassesFromCode();

	/**
	 * Resolve all dependences between entities after extraction.
	 */
	public void resolveDependences();

	/**
	 * Returns a <code>Collection</code> containing <code>PackageNode</code> objects that
	 * represents all packages from code.
	 * @return All packages from application's code.
	 */
	public Collection<PackageNode> getAllPackagesFromCode();

	/**
	 * @param entity
	 */
	public void packageExtracted(String entity);

	/**
	 * Returns a <code>PackageNode</code> object with the fully qualified name.
	 * represents all packages from code.
	 * @param fullyQualifiedNamePackage The package's name.
	 * @return A <code>PackageNode</code> object if the package exists.
	 * @throws InexistentEntityException If the package doesn't exist.
	 */
	public PackageNode getPackage(String fullyQualifiedNamePackage) throws InexistentEntityException;
	
	/**
	 * @param entityName
	 * @return
	 */
	public boolean containsEntity(String entityName);
	
	/**
	 * @param entity
	 */
	public void annotationExtracted(String entity);
}