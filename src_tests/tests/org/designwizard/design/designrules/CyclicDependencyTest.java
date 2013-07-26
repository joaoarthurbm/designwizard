package tests.org.designwizard.design.designrules;
import java.io.IOException;

import junit.framework.TestCase;

import org.designwizard.designrules.CyclicDependencyRule;
import org.designwizard.designrules.Rule;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;


public class CyclicDependencyTest extends TestCase {
	
	public void testCyclicDependency() throws IOException, InexistentEntityException {
	
		DesignWizard dw = new DesignWizard("classes");
		
		Rule rule = new CyclicDependencyRule(dw);
		boolean result = rule.checkRule();
		
		System.out.println(rule.getReport());
		assertFalse(result);
		
	}

}