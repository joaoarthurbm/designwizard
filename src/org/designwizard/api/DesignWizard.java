package org.designwizard.api;

import java.io.IOException;
import java.util.Set;

import org.designwizard.api.util.FileUtil;
import org.designwizard.common.Config;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;
import org.designwizard.design.PackageNode;
import org.designwizard.design.manager.DesignManager;
import org.designwizard.design.manager.ResultOfImpact;
import org.designwizard.design.manager.util.TranslatorUtil;
import org.designwizard.exception.InexistentEntityException;

/**
 * This class provides methods to get access to information about a given application. The information is extracted from
 * the binary files of the application when an instance of DesignWizard is created:
 * <br>
 * <blockquote><pre>
 *  	DesignWizard dw = new DesignWizard("/home/user/application/classes");
 * </pre></blockquote>
 * 
 * Once you have created an instance of DesignWizard class, you can use its methods to acquire information about the 
 * components (Packages, Classes, Methods and Fields) of the application, such as "which are the methods that call 
 * the class MyAplicationClassExample":
 * <br>
 * <blockquote><pre>
 * 		ClassNode c = dw.getClass("MyAplicationClassExample");
 * 		Set<MethodNode> callers = c.getCallers();
 * </pre></blockquote>
 * 
 * @author Joao Brunet
 *
 */
public class DesignWizard {
	
	/**
	 * Attributes
	 */
	private DesignManager manager;
	private FileUtil fileUtil;
	
	
	/**
	 * Creates a new DesignWizard. DesignWizard will extract information about the jar/class files and 
	 * you can use the methods of this class to get this information.
	 * @param 	path	the path of the .jar file or the path of binaries (.class) to be extracted.
	 * @throws	IOException	An exception used for signaling run-time failure of reading jar file.
	 */
	public DesignWizard(String path) throws IOException {
		
		this.manager = new DesignManager(new TranslatorUtil());
		this.manager.extractFacts(path);
	
	}
	
	
	/**
	 * Creates a new DesignWizard. DesignWizard will extract information about the jar/class files and 
	 * you can use the methods of this class to get this information.
	 * @param 	path	the path of the .jar file or the path of binaries (.class) to be extracted.
	 * @throws	IOException	An exception used for signaling run-time failure of reading jar file.
	 */
	public DesignWizard(String path, String... internalPath) throws IOException {
	
		this.manager = new DesignManager(new TranslatorUtil());
		this.manager.extractFacts(path, internalPath);
	
	}
	
	/**
	 * Returns the <code>PackageNode</code> object associated with the package
	 * with the given string name.
	 * @param	fullyQualifiedNamePackage   the fully qualified name of the desired package.
	 * @return	the <code>PackageNode</code> object for the package with the
     *         	specified name.
	 * @throws 	InexistentEntityException	if the class cannot be located
	 */
	public PackageNode getPackage(String fullyQualifiedNamePackage) throws InexistentEntityException {

		return this.manager.getPackage(fullyQualifiedNamePackage);
	
	}
	
	/**
	 * Returns the <code>ClassNode</code> object associated with the class or
     * interface with the given string name.
	 * @param	className   the fully qualified name of the desired class.
	 * @return	the <code>ClassNode</code> object for the class or interface with the
     *         	specified name.
	 * @throws 	InexistentEntityException	if the class cannot be located
	 */
	public ClassNode getClass(String className) throws InexistentEntityException {

		return this.manager.getClass(className);

	}
	
	/**
	 * Returns the <code>ClassNode</code> object associated with the annotation
	 * with the given string name. The {@link ClassNode} contains the {@link Modifier#ANNOTATION}.
	 * @param    annotationName   the fully qualified name of the desired annotation.
	 * @return    the <code>ClassNode</code> object for the annotation with the
	 *             specified name.
	 * @throws     InexistentEntityException    if the annotation cannot be located
	 */
	public ClassNode getAnnotation(String annotationName) throws InexistentEntityException {
		return this.manager.getAnnotation(annotationName);
	}
	
	/**
	 * Returns the <code>ClassNode</code> object associated with the class or
     * interface with the given java class.
	 * @param	classEntity   the desired class.
	 * @return	the <code>ClassNode</code> object for the class or interface represented
	 * 			by the classEntity.
	 * @throws 	InexistentEntityException	if the class cannot be located
	 */
	public ClassNode getClass(java.lang.Class<?> classEntity) throws InexistentEntityException {
	
		return this.getClass(classEntity.getName());
	
	}
	
	/**
	 * Returns the <code>MethodNode</code> object associated with the method or
     * constructor with the given string signature. Constructors have the special identifier &lt;init&gt;. For example:
     * 
     * <blockquote><pre>
     * 		MethodNode constructor = dw.getMethod("foo.bar.MyClass.&lt;init&gt;()")
     * </pre></blockquote>
     * 
	 * @param	methodSignature   the signature of the desired 
	 * 			method (e.g <code>foo.bar.MyClass.methodName(java.lang.Integer,AnotherType)</code>).
	 * @return	the <code>MethodNode</code> object for the method or constructor
	 * 			represented by the methodName.
	 * @throws 	InexistentEntityException	if the method cannot be located
	 */
	public MethodNode getMethod(String methodSignature) throws InexistentEntityException {
		
		return this.manager.getMethod(methodSignature);
	
	}

	/**
	 * Returns the <code>FieldNode</code> object associated with the field
	 * with the given string name.
	 *  
	 * @param	fieldName the fully qualified name the desired 
	 * 			field (e.g <code>foo.bar.MyClass.fieldName</code>).
	 * @return	the <code>FieldNode</code> object for the field
	 * with the given string name.
	 * @throws 	InexistentEntityException	if the field cannot be located
	 */
	public FieldNode getField(String fieldName) throws InexistentEntityException {

		return this.manager.getField(fieldName);
	
	}

	/**
     * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
     * the classes extracted.
     * @return	the set of <code>ClassNode</code> objects representing the
     * 			classes extracted.
	 */
	public Set<ClassNode> getAllClasses() {
	
		return this.manager.getAllClasses();
	
	}
	
	/**
     * Returns the set of <code>ClassNode</code> with the annotated classes to the entity
     * represented by this <code>annotationName</code>.
     * @param annotationName The name of the entity that It is an annotation.
     *
     * @return the set of the annotated classes or <code>null</code> if this parameter wasn't an annotation.
     */
	public Set<ClassNode> getClassesAnnotatedBy(String annotationName) throws InexistentEntityException {
        return this.manager.getClassesAnnotatedBy(annotationName);
    }
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the annotations extracted that they are classes with {@link Modifier#ANNOTATION}.
	 * @return    the set of <code>AnnotationNode</code> objects representing the
	 *             annotations extracted.
	 */
	public Set<ClassNode> getAllAnnotations() {
		return this.manager.getAllAnnotations();
	}
	
	/**
     * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
     * the methods extracted.
     * @return	the set of <code>MethodNode</code> objects representing the
     * 			methods extracted.
	 */
	public Set<MethodNode> getAllMethods() {
	
		return this.manager.getAllMethods();
	
	}
	
	/**
     * Returns a <code>java.util.Set</code> containing <code>PackageNode</code> objects reflecting all
     * the packages extracted.
     * @return	the set of <code>PackageNode</code> objects representing the
     * 			packages extracted.
	 */
	public Set<PackageNode> getAllPackages(){
	
		return this.manager.getAllPackages();
	
	}
	
	/**
	 * This method generates a file containing all the entities extracted from jar file. The file will be saved as a text one
	 * named entities.txt in the directory specified on designwizard.properties. Developers can use this
	 * file to optimize the task of analyzing impacts of a change on several methods.
	 * The entities in the file will be preceded with the special character # before its name. Remove the special character before
	 * the entities that you want to analyze impact.
	 */
	public void generateEntitiesFile() {
		
		FileUtil.writeResult(Config.getInstance().getEntitiesFile(), this.manager.getDesign().toString());
	
	}
	
	/**
	 * Returns a <code>ResultManager</code> object that contains the result of impact analysis.
	 * on each entity read from entities.txt.
	 * @throws IOException 
	 * @see <code>ResultManager</code>.
	 */
	public ResultOfImpact analyseFromEntitiesFile() throws IOException {
	
		this.fileUtil = new FileUtil(Config.getInstance().getEntitiesFile());
		String line = "";
		
		while ((line = this.fileUtil.readLn()) != null) {
			// if the line is not commented
			if (!line.contains("#") && !(line.length() == 0)) {
				this.manager.analyzeImpact(line.trim());
			}
		}
		
		return this.manager.getResultOfAnalyzedEntities();
	}
	
	/**
	 * Returns the result of the analysis.
	 * @return the result of the analysis.
	 */
	public ResultOfImpact getResultOfAnalyzedEntities() {
		
		return this.manager.getResultOfAnalyzedEntities();
	
	}

}
