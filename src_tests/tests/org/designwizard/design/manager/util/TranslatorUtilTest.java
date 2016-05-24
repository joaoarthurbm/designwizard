package tests.org.designwizard.design.manager.util;

import java.io.IOException;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Test;

public class TranslatorUtilTest {
	
	@Test
	public void testJustOneLetter() throws IOException, InexistentEntityException {
		DesignWizard dw = new DesignWizard("resources/testFiles/justOneLetter.jar");
		ClassNode clazz = dw.getClass("Z");
		MethodNode method = clazz.getDeclaredMethod("start()");
		ClassNode returnType = method.getReturnType();
		Assert.assertEquals("void", returnType.getName());
	}
}