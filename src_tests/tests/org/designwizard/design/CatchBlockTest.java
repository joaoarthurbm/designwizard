package tests.org.designwizard.design;

import java.io.File;
import java.io.IOException;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CatchBlockTest {

	/**
	 * Attribute
	 */
	private DesignWizard dw;
	
	/**
	 * Creates a new DesignWizard.
	 * @throws IOException
	 * @throws InexistentEntityException 
	 */
	@Before
	public void setUp() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"trycatchblock.jar");
	}
	
	@Test
	public void testCatchBlock() throws InexistentEntityException {
		
		MethodNode method = this.dw.getMethod("ClassWithManyCatchBlocks.main(java.lang.String[])");
		Assert.assertTrue(method.getCatchedExceptions().contains(this.dw.getClass(java.lang.NumberFormatException.class)));
		Assert.assertTrue(method.getCatchedExceptions().contains(this.dw.getClass(java.lang.IllegalArgumentException.class)));
	
		method = this.dw.getMethod("ClassWithManyCatchBlocks.<init>()");
		Assert.assertTrue(method.getCatchedExceptions().contains(this.dw.getClass(java.io.IOException.class)));
		
		method = this.dw.getMethod("ClassWithManyCatchBlocks.catchRuntimeException()");
		Assert.assertTrue(method.getCatchedExceptions().contains(this.dw.getClass(java.lang.IllegalArgumentException.class)));
		Assert.assertTrue(method.getCatchedExceptions().contains(this.dw.getClass("MyException")));
	}
}
