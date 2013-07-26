package tests.org.designwizard.design.designrules;

import java.io.IOException;
import java.util.Set;

import junit.framework.TestCase;

import org.designwizard.design.ClassNode;
import org.designwizard.designrules.HashCodeAndEqualsRule;
import org.designwizard.designrules.Rule;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class HashCodeAndEqualsRuleTest extends TestCase {
	
	public void testHashCodeAndEqualsRule() throws IOException, InexistentEntityException {
		
		DesignWizard dw = new DesignWizard("classes");
		
		Set<ClassNode> allClasses = dw.getAllClasses();
		
		for (ClassNode classNode : allClasses) {
		
			Rule rule = new HashCodeAndEqualsRule(classNode.getName() ,dw);
			boolean result = rule.checkRule();
		
			System.out.println(rule.getReport());
//			assertTrue(result);
			
		}
		
	}
	
}
