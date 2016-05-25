package tests.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.TestSuite;
import tests.org.designwizard.design.AbstractEntityTest;
import tests.org.designwizard.design.AllClassesInSuiteTest;
import tests.org.designwizard.design.AnnotationsExtractTest;
import tests.org.designwizard.design.AnnotationsOfClassTest;
import tests.org.designwizard.design.AnnotationsOfFieldTest;
import tests.org.designwizard.design.AnnotationsOfMethodTest;
import tests.org.designwizard.design.CatchBlockTest;
import tests.org.designwizard.design.CatchedExceptionsTest;
import tests.org.designwizard.design.ClassNodeTest;
import tests.org.designwizard.design.EntityTest;
import tests.org.designwizard.design.FieldNodeTest;
import tests.org.designwizard.design.InheritanceTest;
import tests.org.designwizard.design.InternalClassExtractionTest;
import tests.org.designwizard.design.LocalVariablesExtractionTest;
import tests.org.designwizard.design.MethodNodeTest;
import tests.org.designwizard.design.PackageExtractionTest;
import tests.org.designwizard.design.RedBarTest;
import tests.org.designwizard.design.manager.util.TranslatorUtilTest;
import tests.org.designwizard.design.relation.RelationTest;
import tests.org.designwizard.extractor.ASMExtractorTest;
import tests.org.designwizard.patternchecker.SingletonPatternCheckerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	AbstractEntityTest.class, 
	AllClassesInSuiteTest.class, 
	AnnotationsExtractTest.class,
	AnnotationsOfClassTest.class,
	AnnotationsOfFieldTest.class, 
	AnnotationsOfMethodTest.class, 
	CatchBlockTest.class,
	CatchedExceptionsTest.class, 
	ClassNodeTest.class,
	EntityTest.class,
	FieldNodeTest.class, 
	InheritanceTest.class,
	InternalClassExtractionTest.class,
	LocalVariablesExtractionTest.class,
	MethodNodeTest.class,
	PackageExtractionTest.class,
	RedBarTest.class,

	TranslatorUtilTest.class,

	ASMExtractorTest.class,

	RelationTest.class, SingletonPatternCheckerTest.class })

public final class AllTests {
	//FIXME Esse método não deveria existir.
	// Tá aqui apenas para o test ClassNodeTest.getLoadClass() 
	public void suite() {
		TestSuite suite = new TestSuite("Test for DesignWizard");
		suite.addTest(new TestSuite(AbstractEntityTest.class));
		suite.addTest(new TestSuite(AllClassesInSuiteTest.class));
		suite.addTest(new TestSuite(AnnotationsOfClassTest.class));
		suite.addTest(new TestSuite(AnnotationsOfFieldTest.class));
		suite.addTest(new TestSuite(AnnotationsExtractTest.class));
		suite.addTest(new TestSuite(AnnotationsOfMethodTest.class));
		suite.addTest(new TestSuite(ASMExtractorTest.class));
		suite.addTest(new TestSuite(CatchBlockTest.class));
		suite.addTest(new TestSuite(CatchedExceptionsTest.class));
		suite.addTest(new TestSuite(ClassNodeTest.class));
		suite.addTest(new TestSuite(EntityTest.class));
		suite.addTest(new TestSuite(FieldNodeTest.class));
		suite.addTest(new TestSuite(InheritanceTest.class));
		suite.addTest(new TestSuite(InternalClassExtractionTest.class));
		suite.addTest(new TestSuite(MethodNodeTest.class));
		suite.addTest(new TestSuite(PackageExtractionTest.class));
		suite.addTest(new TestSuite(RedBarTest.class));
		suite.addTest(new TestSuite(TranslatorUtilTest.class));
		suite.addTest(new TestSuite(SingletonPatternCheckerTest.class));
		suite.addTest(new TestSuite(RelationTest.class));
	}
}