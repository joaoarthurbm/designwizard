package tests.org.designwizard.design.mocks.localvariables;

public class LocalVariablesExamples {
	
	/**
	 * Tests the local variable extraction (not instantiated variable).
	 */
	public void printLocalVariable() {
		LocalVariable var = null;
		System.out.println("Local Variavle: " + var);
	}
	
	/**
	 * Tests the local variable extraction (with cast in local variable).
	 */
	public void castLocalVariable() {
		LocalVariable var = null;
		if (true) {
			var = (LocalVariable) anObject();
		}
		System.out.println("Local Variavle: " + var);
	}
	
	/**
	 * Tests the local variable extraction (with assignment in local variable).
	 */
	public void assignmentLocalVariable() {
		LocalVariable var = null;
		if (true) {
			var = aLocalVariable();
		}
		System.out.println("Local Variavle: " + var);
	}
	
	/**
	 * Tests the local variable extraction (instantiated variable).
	 */
	public void printLocalVariableInstantiated() {
		LocalVariable var = new LocalVariable();
		System.out.println("Local Variavle: " + var);
	}
	
	private Object anObject() {
		return new LocalVariable();
	}
	
	private LocalVariable aLocalVariable() {
		return new LocalVariable();
	}
}