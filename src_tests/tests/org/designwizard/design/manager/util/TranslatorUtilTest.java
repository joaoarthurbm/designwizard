package tests.org.designwizard.design.manager.util;

import java.io.IOException;

import junit.framework.TestCase;

import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class TranslatorUtilTest extends TestCase {

	
	public void testJustOneLetter() throws IOException, InexistentEntityException {
		DesignWizard dw = new DesignWizard("resources/testFiles/justOneLetter.jar");
		ClassNode clazz = dw.getClass("Z");
		MethodNode method = clazz.getDeclaredMethod("start()");
		ClassNode returnType = method.getReturnType();
		assertEquals("void", returnType.getName());
	}
}