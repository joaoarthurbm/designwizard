package tests.org.designwizard.design;

import java.util.Set;

import junit.framework.TestCase;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

public class InternalClassExtractionTest extends TestCase {
	
	private DesignWizard dw;
	
	@Override
	protected void setUp() throws Exception {
		this.dw = new DesignWizard("resources/testFiles/innerclass.jar");
	}
	
	
	public void testInternalClasses() throws InexistentEntityException {
		
		ClassNode classNode = this.dw.getClass("ClassTest");
		ClassNode privateInner = this.dw.getClass("ClassTest$PrivateInternalClass");
		ClassNode protectedInner = this.dw.getClass("ClassTest$ProtectedInternalClass");
		ClassNode publicInner = this.dw.getClass("ClassTest$PublicInternalClass");
		ClassNode packageInner = this.dw.getClass("ClassTest$PackageInternalClass");
		
		Set<ClassNode> innerClasses = classNode.getInnerClasses();
		
		assertTrue(innerClasses.contains(privateInner));
		assertTrue(innerClasses.contains(protectedInner));
		assertTrue(innerClasses.contains(publicInner));
		assertTrue(innerClasses.contains(packageInner));
		
		// existe uma classe anonima
		assertEquals(5, innerClasses.size());

		assertEquals(privateInner.getOuterClass(), classNode);
		assertEquals(publicInner.getOuterClass(), classNode);
		assertEquals(protectedInner.getOuterClass(), classNode);
		assertEquals(packageInner.getOuterClass(), classNode);
		
	}
}