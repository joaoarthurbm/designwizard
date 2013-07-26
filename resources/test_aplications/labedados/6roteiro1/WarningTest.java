
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import junit.framework.TestCase;
import designWizard.exception.InexistentEntityException;
import designWizard.main.DesignWizard;

/**
 * This class is a test composition class. I made some tests that must be executed to show to programmers
 * that their source code have some undesirable feature, however, tolerable features in Design. 
 * @author João Arthur Brunet Monteiro - joaoarthur@gmail.com - Grupo de MÃ©todos Formais (GMF)
 */
public class WarningTest extends TestCase {

	/**
	 * Attribute
	 */
	private DesignWizard dw;
	
	/**
	 * Creates a new DesignWizard.
	 * @throws IOException
	 */
	protected void setUp() throws IOException {
		this.dw = new DesignWizard("6.jar");
	}
	
	
	/**
	 * Assests if the number of static methods on classes are not large. (This may be a bad sinal in Desing.)
	 * @throws InexistentEntityException 
	 *
	 */
	public void testWarning() throws InexistentEntityException {
		
		//all classes from code
		Set<String> classes = dw.getAllClasses();
		
		//there is just one class that represents UI
		//identifying UI
		Set<String> uis = dw.getClassesThatUse("java/lang/System.in", "java/lang/System.out");
		assertEquals(1, uis.size());
		String ui = (String) new LinkedList<String>(uis).getFirst();
		
		// UI uses just one facade.
		// identifying CONTROL
		Set<String> control = dw.getClassesUsedBy(ui);
		control.remove(ui);
		assertTrue(control.size() == 1);
		String facade = (String) new LinkedList<String>(control).getFirst();
		
		// Facade has no static methods.
		System.out.println(facade);
		assertEquals(0, dw.getStaticMethods(facade).size());
		Set <String> logic = new HashSet<String>(classes);
		logic.removeAll(control);
		logic.remove(ui);
		
		
		// Classes from business logic have just one static method. if the number of static methods on classes are large, 
		// it may be a bad sinal in Desing.
		for (String classe: logic) {
			assertTrue(1 <= dw.getStaticMethods(classe).size());
		}
		
		//Project has number of classes between 3 and 6.
		assertTrue( classes.size() >= 3 && classes.size() <= 6 );
	}	
}
