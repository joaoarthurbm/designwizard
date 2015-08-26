package tests.org.designwizard.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;

/**
 * @author João Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 *
 */
public class ImpactTest {

	private DesignWizard dw;
	
	public ImpactTest() throws IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"gerador.jar");
	}
	

	public static void main(String[] args) throws IOException, InexistentEntityException {
		ImpactTest impact = new ImpactTest();
		impact.testClassImpact();
		impact.testFieldImpact();
		impact.testMethodImpact();
		impact.testMethodImpactWithDeep();
	}
	
	/**
	 * Tests the impact caused by a change in a Field.
	 * @throws InexistentEntityException 
	 * @throws IOException 
	 */
	public void testFieldImpact() throws InexistentEntityException {
		String fieldName = "FormDefinirGeracao.firstTime";
		FieldNode field = this.dw.getField(fieldName);
		Set<ClassNode> classesThatUses = field.getCallerClasses();
		
		System.out.println("Any change on Field "+fieldName+ " will cause impact on the following classes:\n");		
		for (ClassNode uses: classesThatUses) {
			System.out.println(uses);
		}
		System.out.println();
	}
	
	/**
	 * Tests the impact caused by a change in a Method.
	 * @throws InexistentEntityException 
	 */
	public void testMethodImpact() throws InexistentEntityException {
		String methodName = "CodeFSW.separarXMIModels(java.lang.String,java.lang.String)";
		MethodNode method = this.dw.getMethod(methodName);
		Set<ClassNode> classesThatUses = method.getCallerClasses();
		
		System.out.println("Any change on method method "+methodName+ " will cause impact on the following classes:\n");		
		for (ClassNode uses: classesThatUses) {
			System.out.println(uses);
		}
		System.out.println();
	}
	
	/**
	 * Tests the impact caused by a change in a Method.
	 * @throws InexistentEntityException 
	 * @throws IOException 
	 */
	public void testMethodImpactWithDeep() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"trace.jar");
		MethodNode method = this.dw.getMethod("Monitored.called()");
		
		System.out.println("Trace for method:");
	
		ArrayList<String[]> result = (ArrayList<String[]>) method.getImpactOfAChange();
		for (String[] strings : result) {
			System.out.println(strings[0]+"  "+strings[1]);
		}
		System.out.println();
	}

	
	/**
	 * Tests the impact caused by a change in a Class.
	 * @throws InexistentEntityException 
	 */
	public void testClassImpact() throws InexistentEntityException {
		String className = "CodeFSW";
		ClassNode clazz = this.dw.getClass(className);
		Set<ClassNode> classesThatUses = clazz.getCallerClasses();
		System.out.println("Any change on method class "+className+ " will cause impact on the following classes:\n");		
		for (ClassNode uses: classesThatUses) {
			System.out.println(uses);
		}
	}
}
