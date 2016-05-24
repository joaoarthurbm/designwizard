package tests.org.designwizard.design;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InternalClassExtractionTest {
	
	private DesignWizard dw;
	
	@Before
	public void setUp() throws Exception {
		this.dw = new DesignWizard("resources/testFiles/innerclass.jar");
	}
	
	@Test
	public void testInternalClasses() throws InexistentEntityException {
		
		ClassNode classNode = this.dw.getClass("ClassTest");
		ClassNode privateInner = this.dw.getClass("ClassTest$PrivateInternalClass");
		ClassNode protectedInner = this.dw.getClass("ClassTest$ProtectedInternalClass");
		ClassNode publicInner = this.dw.getClass("ClassTest$PublicInternalClass");
		ClassNode packageInner = this.dw.getClass("ClassTest$PackageInternalClass");
		
		Set<ClassNode> innerClasses = classNode.getInnerClasses();
		
		Assert.assertTrue(innerClasses.contains(privateInner));
		Assert.assertTrue(innerClasses.contains(protectedInner));
		Assert.assertTrue(innerClasses.contains(publicInner));
		Assert.assertTrue(innerClasses.contains(packageInner));
		
		// existe uma classe anonima
		Assert.assertEquals(5, innerClasses.size());

		Assert.assertEquals(privateInner.getOuterClass(), classNode);
		Assert.assertEquals(publicInner.getOuterClass(), classNode);
		Assert.assertEquals(protectedInner.getOuterClass(), classNode);
		Assert.assertEquals(packageInner.getOuterClass(), classNode);
	}
}