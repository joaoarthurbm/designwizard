import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTest extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for DesignWizard.test");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(TraceForMethodsTest.class));
		//$JUnit-END$
		return suite;
	}
}
