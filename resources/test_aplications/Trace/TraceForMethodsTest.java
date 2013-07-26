
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import designwizard.design.entity.ui.Class;
import designwizard.design.entity.ui.Method;
import designwizard.exception.InexistentEntityException;
import designwizard.main.Config;
import designwizard.main.DesignWizard;
import junit.framework.TestCase;

/**
 * Test for getting trace of methods.
 * @author João Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 *
 */
public class TraceForMethodsTest extends TestCase {

	private DesignWizard dw;
	
	protected void setUp() throws Exception {
		this.dw = new DesignWizard("/home/jarthur/design_wizard/project/resources/testFiles/gerador.jar");
	}
	
	/**
	 * Tests the impact caused by a change in a Method.
	 * @throws InexistentEntityException 
	 * @throws IOException 
	 */
	public void testMethodImpactWithDeep() throws InexistentEntityException, IOException {
		Class clazz = this.dw.getClass(CodeFSW.class);
		Set<String> methods = clazz.getAllMethods();
		Method method = null;
		for (String methodName : methods) {
			method = this.dw.getMethod(methodName);
			System.out.println("Trace for method: "+methodName);
			System.out.println();
			ArrayList<String[]> result = (ArrayList<String[]>) method.getMethodsImpactedByAChange(Config.deep);
			for (String[] strings : result) {
				System.out.println(strings[0]+" <is called by> "+strings[1]);
			}
			System.out.println();
		}
	}
}
