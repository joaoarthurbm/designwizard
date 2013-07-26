package Testes;

import java.io.IOException;
import java.util.Set;

import junit.framework.TestCase;

public class JustOneUITest extends TestCase {

	private designwizard.main.DesignWizard dw;
	
	public void testJustOneUI() throws designwizard.exception.InexistentEntityException, IOException {
		this.dw = new designwizard.main.DesignWizard("teste1.jar");
		
		//there is just one class that represents UI
		//identifying UI
		Set<String> uis = dw.getClassesThatUse("java/lang/System.in", "java/lang/System.out");
		
		System.out.println(uis);
		
		assertEquals(1, uis.size());
	}
	
}
