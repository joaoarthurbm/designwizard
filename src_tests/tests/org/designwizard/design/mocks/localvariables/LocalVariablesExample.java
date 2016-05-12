package tests.org.designwizard.design.mocks.localvariables;

public class LocalVariablesExample {
	
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
			var = (LocalVariable) getObject();
		}
		System.out.println("Local Variavle: " + var);
	}
	
	/**
	 * Tests the local variable extraction (with assignment in local variable).
	 */
	public void assignmentLocalVariable() {
		LocalVariable var = null;
		if (true) {
			var = getLocalVariable();
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
	
	private Object getObject() {
		return new LocalVariable();
	}
	
	private LocalVariable getLocalVariable() {
		return new LocalVariable();
	}
}
