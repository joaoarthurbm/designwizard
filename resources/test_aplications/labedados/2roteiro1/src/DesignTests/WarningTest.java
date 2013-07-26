package DesignTests;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import junit.framework.TestCase;
import designWizard.exception.InexistentEntityException;
import designWizard.main.DesignWizard;

/**
 * This class is a test composition class. I made some tests that must be executed to show to programmers
 * that their source code have some undesirable feature, however, tolerable features in Design. 
 * @author João Arthur Brunet Monteiro - joaoarthur@gmail.com - Grupo de Métodos Formais (GMF)
 */
public class WarningTest extends TestCase {

	/**
	 * Attribute
	 */
	private DesignWizard dw;
	
	protected void setUp() {
		this.dw = new DesignWizard("2.jar");
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
		//identifying IU
		Set<String> ius = dw.getClassesThatUse("java/lang/System.in", "java/lang/System.out");
		assertEquals(1, ius.size());
		String iu = (String) new LinkedList<String>(ius).getFirst();
		
		// Iu uses just one facade.
		Set<String> controle = dw.getClassesUsedBy(iu);
		controle.remove(iu);
		assertTrue(controle.size() == 1);
		String facade = (String) new LinkedList<String>(controle).getFirst();
		
		// Facade has no static methods.
		assertEquals(0, dw.getStaticMethods(facade).size());
		
		Set <String> logica = new HashSet<String>(classes);
		logica.removeAll(controle);
		logica.remove(iu);
		
		// Classes from business logic have just one static method. if the number of static methods on classes are large, 
		// it may be a bad sinal in Desing.
		controle.addAll(logica);
		for (String classe: controle) {
			assertTrue(1 <= dw.getStaticMethods(classe).size());
		}
		 
		//Project has number of classes between 3 and 6.
		assertTrue( classes.size() >= 3 && classes.size() <= 6 );
	}	
}
