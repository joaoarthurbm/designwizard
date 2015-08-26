package tests.org.designwizard.system;

import junit.framework.TestCase;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;

public class ExtractingFromClassFileTest extends TestCase {
	
	private DesignWizard dw;
	
	@Override
	protected void setUp() throws Exception {
		dw = new DesignWizard("classes/tests/org/designwizard/system/ExtractingFromClassFileTest.class");
	}
	
	
	public void testExtraction() throws InexistentEntityException {
		
		ClassNode thisTestClass = dw.getClass(ExtractingFromClassFileTest.class);
		
		assertTrue(thisTestClass.getSuperClass().equals(this.dw.getClass(TestCase.class)));
		
		MethodNode m = dw.getMethod("tests.org.designwizard.system.ExtractingFromClassFileTest.testExtraction()");
		assertTrue(m.getDeclaringClass().equals(thisTestClass));
		
		m = dw.getMethod("tests.org.designwizard.system.ExtractingFromClassFileTest.setUp()");
		assertTrue(m.getDeclaringClass().equals(thisTestClass));
		
		
		assertTrue(thisTestClass.getVisibility().equals(org.designwizard.design.Modifier.PUBLIC));
		
		
	}

}