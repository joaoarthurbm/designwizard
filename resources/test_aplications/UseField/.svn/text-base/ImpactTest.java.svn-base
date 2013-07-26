

import java.io.IOException;
import java.util.Set;

import junit.framework.TestCase;
import designwizard.exception.InexistentEntityException;
import designwizard.main.DesignWizard;

/**
 * @author João Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 *
 */
public class ImpactTest extends TestCase {

	
	private DesignWizard dw;
	
	protected void setUp() throws Exception {
		this.dw = new DesignWizard("/home/jarthur/design_wizard/project/resources/testFiles/gerador.jar");
	}
	
	
	/**
	 * Tests the impact caused by a change in a Field.
	 * @throws InexistentEntityException 
	 * @throws IOException 
	 */
	public void testFieldImpact() throws InexistentEntityException {
		designwizard.design.entity.ui.Field field = this.dw.getField("FormDefinirGeracao.firstTime");
		Set<String> classesThatUses = field.getClassesThatUse();
		
		System.out.println("Any change on Field A will cause impact on the following classes:\n");		
		for (String uses: classesThatUses) {
			System.out.println(uses+"\n");
		}
		
	}
	
	/**
	 * Tests the impact caused by a change in a Method.
	 * @throws InexistentEntityException 
	 */
	public void testMethodImpact() throws InexistentEntityException {
		designwizard.design.entity.ui.Method method = this.dw.getMethod("CodeFSW.separarXMIModels(java/lang/String,java/lang/String)");
		Set<String> classesThatUses = method.getClassesThatUse();
		
		System.out.println("Any change on method A will cause impact on the following classes:\n");		
		for (String uses: classesThatUses) {
			System.out.println(uses+"\n");
		}
	}
	
	/**
	 * Tests the impact caused by a change in a Class.
	 * @throws InexistentEntityException 
	 */
	public void testClassImpact() throws InexistentEntityException {
		designwizard.design.entity.ui.Class clazz = this.dw.getClass("CodeFSW");
		Set<String> classesThatUses = clazz.getClassesThatUse();
		
		System.out.println("Any change on method class A will cause impact on the following classes:\n");		
		for (String uses: classesThatUses) {
			System.out.println(uses+"\n");
		}
	}
	
}
