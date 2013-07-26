package DesignTests;

import java.util.LinkedList;
import java.util.Set;

import junit.framework.TestCase;

import designWizard.exception.InexistentEntityException;
import designWizard.main.DesignWizard;

/**
 * This class is a test composition class. I made some tests that must be executed to show to programmers
 * that their source code have some undesirable feature in Design. 
 * @author João Arthur Brunet Monteiro - joaoarthur@gmail.com - Grupo de Métodos Formais (GMF)
 */
public class RedBarTest extends TestCase{
	
	/**
	 * Attributes
	 */
	private DesignWizard dw;
	private Set<String> allClasses;
	
	/**
	 * Creates a new DesignWizard.
	 */
	protected void setUp() {
		this.dw = new DesignWizard("2.jar");
	}
	
	/**
	 * Tests structural features from software source code.
	 * @throws InexistentEntityException
	 */
	public void testDesign() throws InexistentEntityException {
		
		 
		//all classes from code
		Set<String> classes = dw.getAllClasses();
		 
		//there is just one class that represents UI
		//identifying UI
		Set<String> uis = dw.getClassesThatUse("java/lang/System.in", "java/lang/System.out");
		assertEquals(1, uis.size());
		String ui = (String) new LinkedList<String>(uis).getFirst();
		System.out.println("UI: "+ui);
		
		
        //source code is organized in IU + CONTROLE + LOGICA
		Set<String> controle = dw.getClassesUsedBy(ui);
		controle.remove(ui);
		System.out.println("Controller: "+controle);
		
		Set <String> logica = classes;
		logica.removeAll(controle);
		logica.remove(ui);
		System.out.println("Business Logic: "+logica);
		
		assertFalse(uis.isEmpty());
		assertFalse(controle.isEmpty());
		assertFalse(logica.isEmpty());
		
		// there is no entity that knows or use UI.
		Set<String> usamIu = dw.getClassesThatUse(ui);
		usamIu.remove(ui);
		assertTrue(usamIu.isEmpty());
	}
}
