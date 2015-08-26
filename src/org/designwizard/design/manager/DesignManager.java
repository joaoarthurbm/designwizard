package org.designwizard.design.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.designwizard.design.ClassNode;
import org.designwizard.design.Design;
import org.designwizard.design.DesignIF;
import org.designwizard.design.Entity;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;
import org.designwizard.design.PackageNode;
import org.designwizard.design.manager.util.TranslatorUtil;
import org.designwizard.design.relation.Relation.TypesOfRelation;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.exception.InexistentModifierException;
import org.designwizard.exception.InexistentTypeOfRelationException;
import org.designwizard.extractor.Extractor;
import org.designwizard.extractor.asm.ASMExtractor;
import org.designwizard.extractor.asm.event.ExtractionListener;
import org.designwizard.extractor.asm.event.FactEvent;

/**
 * @author Joao Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 */
public class DesignManager implements ExtractionListener {
	
	/**
	 * Attributes.
	 */
	private Design design;
	private TranslatorUtil translator;
	private ResultOfImpact resultOfImpact;
	private List<Extractor> extractors;
	
	
	/**
	 * Creates a new DesignManager. An instance of this class is responsible by receiving the extraction events and fill the <code>Design</code> object. 
	 * @param translator
	 */
	public DesignManager(TranslatorUtil translator) {
	
		this.design = new Design();
		
		initializeExtractors();
		
		this.translator = translator;
		this.resultOfImpact = new ResultOfImpact();
	
	}

	private void initializeExtractors() {
		
		this.extractors = new ArrayList<Extractor>();
		this.extractors.add(new ASMExtractor());
				
		for (Extractor extractor : this.extractors) {
			extractor.addListener(this);
		}
	
	}

	/**
	 * Extracts fact from the given bytecode. The path must be a jar file, or the classes directory from project.
	 * @param path The path of a jar file, or the classes directory from project.
	  * @throws IOException If an I/O error occurs.
	 */
	public void extractFacts(String path) throws IOException, FileNotFoundException {
		File file = new File(path);
		
		if (!file.exists()) throw new FileNotFoundException("Couldn't find the given path: " + path);
		
		if (file.getName().endsWith(".jar") ) {
			this.extractFactsFromJar(path);
		} else if (file.isDirectory()) {
			this.extractFactsFromClassesDir(path);
		} else if (file.getName().endsWith(".class")) {
			this.extractFactsFromClassFile(path);
		} else {
			this.extractFactsFromEar(path, "");
		}
	}
	
	public void extractFacts(String path, String ... internalPath) throws IOException, FileNotFoundException {
		File file = new File(path);
		
		if (!file.exists()) throw new FileNotFoundException();
		
		if (file.getName().endsWith(".war") || (file.getName().endsWith(".ear"))) {
			this.extractFactsFromEar(path, internalPath);
		} else {
			throw new IOException("Path does not correspond to a directory or file a valid."); 
		}
	}

	private void extractFactsFromClassFile(String path) throws IOException {
		for (Extractor extractor : this.extractors) {
			extractor.processClass(path);
		}
		this.resolveDependences();
	}

	/**
	 * Extracts facts from the given .jar file.
	 * @param pathOfJarFile
	 * @throws IOException If an I/O error occurs.
	 */
	private void extractFactsFromEar(String path, String ... internalPath) throws IOException {
		for (Extractor extractor : this.extractors) {
			extractor.processEar(path, internalPath);
		}
		this.resolveDependences();
	}

	/**
	 * Extracts facts from the given .jar file.
	 * @param pathOfJarFile
	 * @throws IOException If an I/O error occurs.
	 */
	private void extractFactsFromJar(String pathOfJarFile) throws IOException {
		for (Extractor extractor : this.extractors) {
			extractor.processJar(pathOfJarFile);
		}
		this.resolveDependences();
	}
	
	/**
	 * Extracts facts from a given directory.
	 * @param pathOfClassesDir
	 * @throws IOException If an I/O error occurs.
	 */
	private void extractFactsFromClassesDir(String pathOfClassesDir) throws IOException {
		for (Extractor extractor : this.extractors) {
			extractor.processDir(pathOfClassesDir);
		}
		this.resolveDependences();
	}
	
	/**
	 * Resolves dependences on design after the end of extraction.
	 */
	private void resolveDependences() {
		this.design.resolveDependences();
	}
	
	/**
	 * Returns the design extracted.
	 * @return the design extracted.
	 */
	public Design getDesign() {
		return this.design;
	}

	/**
	 * Adds a new <code>Relation</code> on the <code>Design</code>.
	 * @param type the type of the <code>Relation</code>.
	 * @param caller the caller of the <code>Relation</code>.
	 * @param called the called of the <code>Relation</code>.
	 */
	private void addRelation(TypesOfRelation type, String caller, String called) {
		String toPutCaller = caller;
			
		if (caller.length() != 1) {
			toPutCaller = this.translator.translateBytecodeToJavaPattern(caller);
		}
		String toPutCalled = this.translator.translateBytecodeToJavaPattern(called);
		this.design.addRelation(type, toPutCaller, toPutCalled);
	}

	/**
	 * Event generated by the extractor.
	 */
	public void relationExtracted(FactEvent event) {
		try {
			this.addRelation(TypesOfRelation.extractElement(event.getType()), event.getCaller(), event.getCalled());
		} catch (InexistentTypeOfRelationException e) {
			//TODO if the follow line is processed, the extractor is not functioning properly. The type of relation is invalid.
			e.printStackTrace();
		}
	}
	
	/**
	 * Event generated by the extractor when a visibility is extracted.
	 */
	public void visibilityExtracted(FactEvent event) {
	
		if (!event.getEntity().startsWith(DesignIF.FIELD_IDENTIFIER)) {
	
			this.design.addClassExtractedFromCode(event.getEntity());
		
		}
		
		try {
		
			Modifier type = Modifier.extractModifier(event.getModifier());
			this.design.setVisibility(event.getEntity().replace(DesignIF.FIELD_IDENTIFIER, ""), type);
		
		} catch (InexistentEntityException e) {
		
			e.printStackTrace();
		
		} catch (InexistentModifierException e) {

			e.printStackTrace();
		
		}
	}

	/**
	 * Event generated by the extractor. When a signature of method is extracted.
	 */
	public void signatureExtracted(FactEvent event) {
	
		try {
		
			String entity = event.getEntity();
			this.setSignatureOfMethod(entity,Modifier.extractModifier(event.getModifier()));
			
			if(entity.contains("ILegacyMatcherMethods")){
			
				System.out.println(entity);
				
			}
		
		} catch (InexistentModifierException e) {
		
			//TODO if the follow line is processed, the extractor is not functioning properly. The type of modifier is invalid.
			e.printStackTrace();
		
		}
	}
	
	/**
	 * Event generated by the extractor. When a class is extracted.
	 */
	public void classExtracted(FactEvent event) {
		this.design.addClassExtractedFromCode(event.getEntity());
	}
	
	public void packageExtracted(FactEvent event) {
		this.design.packageExtracted(event.getEntity());
	}
	
	/**
	 * Event generated by the extractor. When a class with {@link Modifier#ANNOTATION} is extracted.
	 */
	public void annotationExtracted(FactEvent event) {
		String entityID = this.translator.translateBytecodeToJavaPattern(event.getEntity());
		this.design.annotationExtracted(entityID);
	}
	
	/**
	 * Returns a set containing all annotations classes extracted.
	 * @return a set containing all annotations classes extracted.
	 * If no annotation class was extracted, this method will return a empty Set<String>;
	 */
	public Set<ClassNode> getAllAnnotations() {
		return this.design.getAllAnnotations();
	}

	/**
	 * Returns a {@link ClassNode} represented by the specified class annotation. 
	 * Classes of Annotation are classes with {@link Modifier#ANNOTATION} that define Annotations.
	 * @param annnotationName the fully qualified name of the desired annotation.
	 * @return a <code>ClassNode</code> represented by the specified annotationName.
	 * @throws InexistentEntityException if the annotation cannot be located.
	 */
	public ClassNode getAnnotation(String annotationName) throws InexistentEntityException {
		return this.design.getAnnotation(annotationName);
	}
	
	private void setSignatureOfMethod(String method, Modifier visibility) {
	
		StringTokenizer st = new StringTokenizer(method);
		st.nextToken();
		
		String sig = st.nextToken();
		
		String methodName = this.translator.translateBytecodeToJavaPattern(method);
		String parameters = this.translator.translateBytecodeToJavaPattern(sig.substring(1, sig.indexOf(")")));
		String returnType = this.translator.translateBytecodeToJavaPattern(sig.substring(sig.indexOf(")")+1));
		
		try {
		
			this.design.setReturnType(methodName.replace(DesignIF.METHOD_IDENTIFIER, ""),returnType);
			this.design.setVisibility(methodName.replace(DesignIF.METHOD_IDENTIFIER, ""), visibility);
			this.design.setParameters(methodName.replace(DesignIF.METHOD_IDENTIFIER, ""), parameters);
		
		} catch (InexistentEntityException e) {
		
			e.printStackTrace();
		
		}
	}
	
	/**
	 * Returns a set containing all classes extracted.
	 * @return a set containing all classes extracted.
	 * If no class was extracted, this method will return a empty Set<String>;
	 */
	public Set<ClassNode> getAllClasses() {
		return this.design.getAllClassesFromCode();
	}
	
	/**
     * Returns the set of <code>ClassNode</code> with the annotated classes to the entity
     * represented by this <code>annotationName</code>.
     * @param annotationName The name of the entity that It is an annotation.
     *
     * @return the set of the annotated classes or <code>null</code> if this parameter wasn't an annotation.
     */
	public Set<ClassNode> getClassesAnnotatedBy(String annotationName) throws InexistentEntityException {
        return this.design.getClassesAnnotatedBy(annotationName);
    }

	public Entity getEntity(String entityName) throws InexistentEntityException {
		return this.design.getEntity(entityName);
	}

	/**
	 * Returns a ClassEntity represented by the specified className.
	 * @param className - the name of the class.
	 * @return a ClassEntity represented by the specified className.
	 * @throws InexistentEntityException 
	 */
	public ClassNode getClass(String className) throws InexistentEntityException {
		return this.design.getClass(className);
	}
	
	/**
	 * Returns a Method with the specified name.
	 * @param methodName the name of the method.
	 * @return a Method with the specified name.
	 * @throws InexistentEntityException
	 */
	public MethodNode getMethod(String methodName) throws InexistentEntityException {
		return this.design.getMethod(methodName);
		
	}

	/**
	 * Returns a Field with the specified name.
	 * @param fieldName the name of the field.
	 * @return a Field with the specified name.
	 * @throws InexistentEntityException
	 */
	public FieldNode getField(String fieldName) throws InexistentEntityException {
		return this.design.getField(fieldName);
	}

	/**
	 *  Analyze the impact of a change in the <code>Entity</code> object specified by the entityName.
	 *  
	 *  This method fill the <code>ResultManager</code> object with the impact of a change in
	 *  the <code>Entity</code> object specified by the entityName.
	 *  To see the result of the analyzed entities, get the <code>ResultManager</code> object invoking
	 *  the getResultOfAnalyzedEntities() of this class.
	 *  
	 */
	public void analyzeImpact(String entityName) {
	
		try {

			Entity entity = this.design.getEntity(entityName);
			this.resultOfImpact.addTrace(entityName, entity.getImpactOfAChange());
		
		} catch (InexistentEntityException e1) {
		
			e1.printStackTrace();
		
		}
	
	}

	/**
	 * The result generated by the analyzeImpact(String entityName) method.
	 * @return the result generated by the analyzeImpact(String entityName) method.
	 */
	public ResultOfImpact getResultOfAnalyzedEntities() {
		return this.resultOfImpact;
	}

	/**
	 * Event generated by the extractor when modifiers are extracted.
	 */
	//TODO refatorar, pedir ao Design para fazer isso.
	public void modifiersExtracted(FactEvent event) {
	
		Entity entity;
		try {
			
			if (event.getEntity().replace(DesignIF.METHOD_IDENTIFIER, "").length() != 1) {
				
				String entityID = this.translator.translateBytecodeToJavaPattern(event.getEntity()).replace(DesignIF.METHOD_IDENTIFIER, "");

				entity = this.design.getEntity(entityID);
				
			} else {
				
				entity = this.design.getEntity(event.getEntity().replace(DesignIF.METHOD_IDENTIFIER, ""));
			
			}
			
			Collection<Modifier> modifiers = new ArrayList<Modifier>();
	
			for (String mod : event.getModifiers()) {
			
				modifiers.add(Modifier.extractModifier(mod));
			
			}
			
			entity.addModifiers(modifiers);
		
		} catch (InexistentEntityException e) {
		
			e.printStackTrace();
		
		} catch (InexistentModifierException e) {
		
			e.printStackTrace();
		
		}
	}

	public Set<PackageNode> getAllPackages() {
		return this.design.getAllPackagesFromCode();
	}

	public PackageNode getPackage(String fullyQualifiedNamePackage) throws InexistentEntityException {
		return this.design.getPackage(fullyQualifiedNamePackage);
	}

	public Set<MethodNode> getAllMethods() {
		return this.design.getAllMethods();
	}

	public Set<PackageNode> getPackagesStartingWith(String prefix) {
		Set<PackageNode> packages = new HashSet<PackageNode>();
		
		for (PackageNode p : this.getAllPackages()) {
			if (p.getName().startsWith(prefix))
				packages.add(p);
		}
		
		return packages;
	}

}