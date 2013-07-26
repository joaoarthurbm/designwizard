
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
	 * Attribute
	 */
	private DesignWizard dw;
	
	/**
	 * Creates a new DesignWizard.
	 */
	protected void setUp() {
		this.dw = new DesignWizard("4.jar");
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
		
		
        //source code is organized in UI + CONTROL + BUSINESS LOGIC
		
		
		//identifying CONTROL
		Set<String> control = dw.getClassesUsedBy(ui);
		control.remove(ui);
		
		//identifying LOGIC
		Set <String> logic = classes;
		logic.removeAll(control);
		logic.remove(ui);
		
		assertFalse(uis.isEmpty());
		assertFalse(control.isEmpty());
		assertFalse(logic.isEmpty());
		
		// there is no entity that knows or use UI.
		Set<String> useUI = dw.getClassesThatUse(ui);
		useUI.remove(ui);
		assertTrue(useUI.isEmpty());
	}
}
