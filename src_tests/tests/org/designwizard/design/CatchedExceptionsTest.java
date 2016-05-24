package tests.org.designwizard.design;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CatchedExceptionsTest {

	private DesignWizard dw;
	
	@Before
	public void setUp() throws IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"trycatchblock.jar");
	}
	
	@Test
	public void testNonExceptionCatch() throws InexistentEntityException {
		ClassNode testedClass = this.dw.getClass(Exception.class);
		Set<ClassNode> classes = this.dw.getAllClasses();
		for (ClassNode clazz: classes) {
			Set<MethodNode> methods = clazz.getAllMethods();
			for (MethodNode method: methods) {
				Set<ClassNode> exceptions = method.getCatchedExceptions();
				Assert.assertFalse("The method "+method.getName()+" catches Exception. Fix it!.",exceptions.contains(testedClass));
			}
		}
	}
}
