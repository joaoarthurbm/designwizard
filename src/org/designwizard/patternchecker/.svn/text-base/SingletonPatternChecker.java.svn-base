package org.designwizard.patternchecker;

import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.DesignIF;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;

public class SingletonPatternChecker implements PatternChecker {

	public static final String VERIFIED_PATTERN_NAME = "Singleton";
	public static final String PUBLIC_CONSTRUCTOR_ERROR = "There is at least one public constructor";
	public static final String MULTIPLE_SINGLETON_FIELDS_ERROR = "There is more than one instance of the class as field";
	public static final String GET_INSTANCE_NOT_FOUND_ERROR = "There is not any instance provider correctly implemented";
	public static final String NO_SINGLETON_FIELDS_ERROR = "There is not any STATIC and PRIVATE instance of the class as field";
	public static final String GET_INSTANCE_FIELD_MISS_WARN = "It is not possible to determine if there is any instance provider without an instance field";
	
	private CheckingResult currentCheckingResult;
	private ClassNode singletonCandidate;
	private FieldNode instanceField;
	
	public SingletonPatternChecker(ClassNode classEntity) {
		this.currentCheckingResult = new CheckingResult();
		this.singletonCandidate = classEntity;
	}
	
	/* (non-Javadoc)
	 * @see designwizard.patternchecker.PatternChecker#getVeredict()
	 */
	public boolean getVeredict() {
		return this.currentCheckingResult.getVeredict();
	}

	/* (non-Javadoc)
	 * @see designwizard.patternchecker.PatternChecker#setDesign(designwizard.design.DesignIF)
	 */
	public void setDesign(DesignIF design) {
		// do nothing. This checker does not need a design to do its job.
	}

	/* (non-Javadoc)
	 * @see designwizard.patternchecker.PatternChecker#verify()
	 */
	public CheckingResult verify() {
		this.currentCheckingResult = new CheckingResult();
		
		this.currentCheckingResult.setVeredict(this.verifyConstructors());
		
		this.currentCheckingResult.setVeredict(this.verifyInstance());
		
		this.currentCheckingResult.setVeredict(this.verifyGetInstance());
		
		return currentCheckingResult;
	}

	/**
	 * This method verifies whether any of the known methods of the
	 * verified class has the property of returning an instance of the class
	 * according to the singleton pattern properties. This verification is
	 * based on the Singleton Pattern described by Gamma et al.
	 * It does not requires the name of the method to be "getInstance" 
	 * or checks whether there is more than one method with the same 
	 * property of returning an instance, being static and public.
     *
	 * @return <code>true</code> if there exists at least one method that
	 * can be a candidate to getInstance method. <code>false</code> otherwise.
	 */
	private boolean verifyGetInstance() {
		if (this.instanceField != null) {
			Set<MethodNode> methods = 
				this.singletonCandidate.getAllMethodsThatReturn(singletonCandidate.getName());
			for (MethodNode method : methods) {
				if (hasAllGetInstanceProperties(method)) {
					return true;
				}
			}
			addError(SingletonPatternChecker.GET_INSTANCE_NOT_FOUND_ERROR);
			return false;
		} else {
			addWarning(SingletonPatternChecker.GET_INSTANCE_FIELD_MISS_WARN);
			return true;
		}
	}

	/**
	 * Verifies if the 
	 * @param method
	 * @return
	 */
	private boolean hasAllGetInstanceProperties(MethodNode method) {
		if (method.isStatic() && (method.getVisibility().equals(Modifier.PUBLIC) ||
				method.getVisibility().equals(Modifier.PROTECTED))) {
			Set<FieldNode> fields = method.getAccessedFields();
			for (FieldNode field : fields) {
				if (this.instanceField.equals(field)) {
					return true;
				}
			}
		}
		
		return false;
	}

	private void addWarning(String warningMessage) {
		CheckWarning warning = new CheckWarning(warningMessage, 
				this.singletonCandidate.getName(), SingletonPatternChecker.VERIFIED_PATTERN_NAME);
		this.currentCheckingResult.addWarning(warning);
	}

	/**
	 * This method checks whether there is at least one field of the
	 * verified class in accordance to the Singleton Pattern, which means
	 * that any of the fields must have private visibility, static modifier
	 * and be of the verified class type.
	 * @return <code>true</code> if any of the fields has the desired 
	 * properties, <code>false</code> otherwise.
	 */
	private boolean verifyInstance() {
		
		Set<FieldNode> allFields = this.singletonCandidate.getAllFields();
		for (FieldNode field : allFields) {
			if (hasSingletonFieldProperties(field)) {
				if (this.instanceField == null) {
					this.instanceField = field;
				} else {
					addError(SingletonPatternChecker.MULTIPLE_SINGLETON_FIELDS_ERROR);
					return false;
				}
			}
		}
		if (this.instanceField == null) {
			addError(SingletonPatternChecker.NO_SINGLETON_FIELDS_ERROR);
			return false;
		}
		return true;
	}

	private void addError(String errorMessage) {
		CheckError error = new CheckError(errorMessage, 
				this.singletonCandidate.getName(), SingletonPatternChecker.VERIFIED_PATTERN_NAME);
		this.currentCheckingResult.addError(error);
	}

	/**
	 * This method checks whether the parameter field of the verified class 
	 * in accordance to the Singleton Pattern, which means that the fields 
	 * must have private visibility, static modifier and be of the verified 
	 * class type.
	 * @return <code>true</code> if the field has the desired properties, 
	 * <code>false</code> otherwise.
	 */
	private boolean hasSingletonFieldProperties(FieldNode field) {
		if (field.containsModifiers(Modifier.PRIVATE,Modifier.STATIC)) {
			if (field.getType().equals(this.singletonCandidate)) {
				return true;
			}
		}
		return false;
	}

	private boolean verifyConstructors() {
		Set<MethodNode> constructors = this.singletonCandidate.getConstructors();
		for ( MethodNode constructor : constructors ) {
			if (!constructor.getVisibility().equals(Modifier.PRIVATE)) {
				addError(SingletonPatternChecker.PUBLIC_CONSTRUCTOR_ERROR);
				return false; 
			}
		}
		return true;
	}
}
