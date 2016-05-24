package tests.org.designwizard.patternchecker;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.patternchecker.CheckError;
import org.designwizard.patternchecker.CheckWarning;
import org.designwizard.patternchecker.CheckingResult;
import org.designwizard.patternchecker.PatternChecker;
import org.designwizard.patternchecker.SingletonPatternChecker;
import org.junit.Assert;
import org.junit.Test;

public class SingletonPatternCheckerTest {
	
	private DesignWizard dw;

	@Test
	public void testWhetherIsSingleton() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"singleton.jar");

		ClassNode classEntity = this.dw.getClass("SingletonImplementation");
		PatternChecker checker = new SingletonPatternChecker(classEntity);
		checker.verify();
		Assert.assertTrue(checker.getVeredict());

		/* Test with a public constructor */
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"singletonBadConstructor1.jar");

		classEntity = this.dw.getClass("SingletonBadConstructor");
		checker = new SingletonPatternChecker(classEntity);
		CheckingResult resultOfCheck = checker.verify();
		Assert.assertFalse(checker.getVeredict());
		
		Set<CheckError> errors = resultOfCheck.getErrors();
		Assert.assertEquals(1, errors.size());
		CheckError uniqueError = errors.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.PUBLIC_CONSTRUCTOR_ERROR, 
				uniqueError.getErrorMessage());

		/* Test with a public Singleton instance */
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"singletonBadField.jar");

		classEntity = this.dw.getClass("SingletonPublicField");
		checker = new SingletonPatternChecker(classEntity);
		resultOfCheck = checker.verify();
		Assert.assertFalse(checker.getVeredict());
		
		errors = resultOfCheck.getErrors();
		Assert.assertEquals(1, errors.size());
		uniqueError = errors.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.NO_SINGLETON_FIELDS_ERROR, 
				uniqueError.getErrorMessage());

		Set<CheckWarning> warnings = resultOfCheck.getWarnings();
		Assert.assertEquals(1, warnings.size());
		CheckWarning uniqueWarning = warnings.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.GET_INSTANCE_FIELD_MISS_WARN, 
				uniqueWarning.getMessage());
		
		/* Test without Singleton instance as field */
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"singletonBadField.jar");

		classEntity = this.dw.getClass("SingletonWithoutField");
		checker = new SingletonPatternChecker(classEntity);
		resultOfCheck = checker.verify();
		Assert.assertFalse(checker.getVeredict());
		
		errors = resultOfCheck.getErrors();
		Assert.assertEquals(1, errors.size());
		uniqueError = errors.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.NO_SINGLETON_FIELDS_ERROR, 
				uniqueError.getErrorMessage());

		warnings = resultOfCheck.getWarnings();
		Assert.assertEquals(1, warnings.size());
		uniqueWarning = warnings.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.GET_INSTANCE_FIELD_MISS_WARN, 
				uniqueWarning.getMessage());
		
		/* Test with non static Singleton instance as field */
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"singletonBadField.jar");

		classEntity = this.dw.getClass("SingletonNonStaticField");
		checker = new SingletonPatternChecker(classEntity);
		resultOfCheck = checker.verify();
		Assert.assertFalse(checker.getVeredict());
		
		errors = resultOfCheck.getErrors();
		Assert.assertEquals(1, errors.size());
		uniqueError = errors.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.NO_SINGLETON_FIELDS_ERROR, 
				uniqueError.getErrorMessage());

		warnings = resultOfCheck.getWarnings();
		Assert.assertEquals(1, warnings.size());
		uniqueWarning = warnings.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.GET_INSTANCE_FIELD_MISS_WARN, 
				uniqueWarning.getMessage());


		/* *********** TESTING THE GETINSTANCE METHODS ************* */
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"singletonBadGetInstance.jar");

		/* Test getInstance without field access */

		classEntity = this.dw.getClass("SingletonGetInstanceWithoutFieldAcess");
		checker = new SingletonPatternChecker(classEntity);
		resultOfCheck = checker.verify();
		Assert.assertFalse(checker.getVeredict());
		
		errors = resultOfCheck.getErrors();
		Assert.assertEquals(1, errors.size());
		uniqueError = errors.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.GET_INSTANCE_NOT_FOUND_ERROR, 
				uniqueError.getErrorMessage());

		/* Test getInstance without static modifier */
		
		classEntity = this.dw.getClass("SingletonNonStaticGetInstance");
		checker = new SingletonPatternChecker(classEntity);
		resultOfCheck = checker.verify();
		Assert.assertFalse(checker.getVeredict());
		
		errors = resultOfCheck.getErrors();
		Assert.assertEquals(1, errors.size());
		uniqueError = errors.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.GET_INSTANCE_NOT_FOUND_ERROR, 
				uniqueError.getErrorMessage());

		/* Test without getInstance */
		
		classEntity = this.dw.getClass("SingletonWithoutGetInstance");
		checker = new SingletonPatternChecker(classEntity);
		resultOfCheck = checker.verify();
		Assert.assertFalse(checker.getVeredict());
		
		errors = resultOfCheck.getErrors();
		Assert.assertEquals(1, errors.size());
		uniqueError = errors.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.GET_INSTANCE_NOT_FOUND_ERROR, 
				uniqueError.getErrorMessage());

		/* Test getInstance with private modifier */
		
		classEntity = this.dw.getClass("SingletonWithPrivateGetInstance");
		checker = new SingletonPatternChecker(classEntity);
		resultOfCheck = checker.verify();
		Assert.assertFalse(checker.getVeredict());
		
		errors = resultOfCheck.getErrors();
		Assert.assertEquals(1, errors.size());
		uniqueError = errors.iterator().next();
		Assert.assertEquals(SingletonPatternChecker.GET_INSTANCE_NOT_FOUND_ERROR, 
				uniqueError.getErrorMessage());

		/* *********** TESTING CORRECT IMPLEMENTATIONS ************* */
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"singletonCorrect.jar");

		classEntity = this.dw.getClass("SingletonCorrect1");
		checker = new SingletonPatternChecker(classEntity);
		resultOfCheck = checker.verify();
		Assert.assertTrue(checker.getVeredict());
		
		errors = resultOfCheck.getErrors();
		Assert.assertEquals(0, errors.size());

		classEntity = this.dw.getClass("SingletonCorrect2");
		checker = new SingletonPatternChecker(classEntity);
		resultOfCheck = checker.verify();
		Assert.assertTrue(checker.getVeredict());
		
		errors = resultOfCheck.getErrors();
		Assert.assertEquals(0, errors.size());
	}
}
