package tests.org.designwizard.design.designrules;

import java.io.IOException;

import org.designwizard.designrules.ClassDependencesRule;
import org.designwizard.designrules.DependencesRuleIF;
import org.designwizard.designrules.MethodDependencesRule;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class DependencesTest {
	
	public static void main(String[] args) throws IOException, InexistentEntityException {
		
		DesignWizard dw = new DesignWizard("classes");
		
		DependencesRuleIF rule = new MethodDependencesRule("tests.org.designwizard.design.designrules.CommunicationTest.main(java.lang.String[])", dw);
		rule.addDeniedEntities("org.designwizard.designrules.Rule.addViolations(java.lang.String[])");
		
		System.out.println(rule.getReport());
		
		rule = new ClassDependencesRule("tests.org.designwizard.design.designrules.CommunicationTest",dw);
		rule.addDeniedEntities("org.designwizard.design.MethodNode","java.io.IOException","org.designwizard.exception.InexistentEntityException", "java.lang.Exception");
		
		System.out.println(rule.getReport());
		
		rule = new ClassDependencesRule("tests.org.designwizard.design.designrules.DependencesTest",dw);
		rule.addAllowedEntities("java.io.IOException");
		rule.addAllowedEntities("org.designwizard.exception.InexistentEntityException");
		rule.addAllowedEntities("org.designwizard.designrules.Rule");
		rule.addAllowedEntities("java.io.PrintStream");
		rule.addAllowedEntities("org.designwizard.designrules.MethodDependencesRule");
		rule.addAllowedEntities("java.lang.System");
		rule.addAllowedEntities("org.designwizard.designrules.ClassDependencesRule");
		rule.addAllowedEntities("org.designwizard.main.DesignWizard");
		rule.addAllowedEntities("java.lang.Object");
		rule.addAllowedEntities("org.designwizard.designrules.DependencesRuleIF");
		
		System.out.println(rule.checkRule());
		System.out.println(rule.getReport());
		
	}

}