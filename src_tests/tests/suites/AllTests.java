package tests.suites;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tests.org.designwizard.design.AbstractEntityTest;
import tests.org.designwizard.design.AllClassesInSuiteTest;
import tests.org.designwizard.design.CatchBlockTest;
import tests.org.designwizard.design.CatchedExceptionsTest;
import tests.org.designwizard.design.ClassNodeTest;
import tests.org.designwizard.design.EntityTest;
import tests.org.designwizard.design.FieldNodeTest;
import tests.org.designwizard.design.InheritanceTest;
import tests.org.designwizard.design.InternalClassExtractionTest;
import tests.org.designwizard.design.MethodNodeTest;
import tests.org.designwizard.design.PackageExtractionTest;
import tests.org.designwizard.design.RedBarTest;
import tests.org.designwizard.design.manager.util.TranslatorUtilTest;
import tests.org.designwizard.design.relation.RelationTest;
import tests.org.designwizard.extractor.ASMExtractorTest;
import tests.org.designwizard.patternchecker.SingletonPatternCheckerTest;

public class AllTests extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for DesignWizard");
		
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(RelationTest.class));
		suite.addTest(new TestSuite(CatchBlockTest.class));
		suite.addTest(new TestSuite(CatchedExceptionsTest.class));
		suite.addTest(new TestSuite(FieldNodeTest.class));
		suite.addTest(new TestSuite(ClassNodeTest.class));
		suite.addTest(new TestSuite(AllClassesInSuiteTest.class));
		suite.addTest(new TestSuite(SingletonPatternCheckerTest.class));
		suite.addTest(new TestSuite(MethodNodeTest.class));
		suite.addTest(new TestSuite(AbstractEntityTest.class));
		suite.addTest(new TestSuite(InheritanceTest.class));
		suite.addTest(new TestSuite(RedBarTest.class));
		suite.addTest(new TestSuite(EntityTest.class));
		suite.addTest(new TestSuite(TranslatorUtilTest.class));
		suite.addTest(new TestSuite(PackageExtractionTest.class));
		suite.addTest(new TestSuite(InternalClassExtractionTest.class));
		suite.addTest(new TestSuite(ASMExtractorTest.class));
		
		//$JUnit-END$
		return suite;
	
	}
}