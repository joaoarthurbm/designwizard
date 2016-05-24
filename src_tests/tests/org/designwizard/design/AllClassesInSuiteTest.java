package tests.org.designwizard.design;

import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;
import tests.suites.AllTests;

public class AllClassesInSuiteTest {
	
	private DesignWizard dw;
	private static final String DESIGNWIZARD_HEADLINE = "org.";
	private static final String DESIGNWIZARD_DIR = "classes";
	
	@Test
	public void testAllTestClassIsHere() throws IOException, InexistentEntityException {
		
		dw = new DesignWizard(DESIGNWIZARD_DIR);
	
		ClassNode testedClass = dw.getClass(AllTests.class);
		ClassNode testcaseClass =  dw.getClass(TestCase.class); 
		
		Set<ClassNode> testClasses = testcaseClass.getSubClasses();
		testClasses.remove(testedClass);
		
		for (ClassNode subClass : testClasses) {
			if (subClass.getClassNode().getName().startsWith(DESIGNWIZARD_HEADLINE)) {
				Assert.assertTrue("The class "+subClass.getName()+" is not in the suite.",subClass.getCallerClasses().contains(testedClass));
			}
		}
	}
}