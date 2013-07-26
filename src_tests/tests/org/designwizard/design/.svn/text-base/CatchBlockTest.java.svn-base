package tests.org.designwizard.design;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class CatchBlockTest extends TestCase {

	/**
	 * Attribute
	 */
	private DesignWizard dw;
	
	/**
	 * Creates a new DesignWizard.
	 * @throws IOException
	 * @throws InexistentEntityException 
	 */
	@Override
	protected void setUp() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"trycatchblock.jar");
	}
	
	public void testCatchBlock() throws InexistentEntityException {
		
		MethodNode method = this.dw.getMethod("ClassWithManyCatchBlocks.main(java.lang.String[])");
		assertTrue(method.getCatchedExceptions().contains(this.dw.getClass(java.lang.NumberFormatException.class)));
		assertTrue(method.getCatchedExceptions().contains(this.dw.getClass(java.lang.IllegalArgumentException.class)));
	
		method = this.dw.getMethod("ClassWithManyCatchBlocks.<init>()");
		assertTrue(method.getCatchedExceptions().contains(this.dw.getClass(java.io.IOException.class)));
		
		method = this.dw.getMethod("ClassWithManyCatchBlocks.catchRuntimeException()");
		assertTrue(method.getCatchedExceptions().contains(this.dw.getClass(java.lang.IllegalArgumentException.class)));
		assertTrue(method.getCatchedExceptions().contains(this.dw.getClass("MyException")));
	}
}
