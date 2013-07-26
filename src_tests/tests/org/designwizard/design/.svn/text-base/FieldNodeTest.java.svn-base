package tests.org.designwizard.design;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.designwizard.common.Config;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Design;
import org.designwizard.design.FieldNode;
import org.designwizard.design.factory.ElementsFactory;
import org.designwizard.design.relation.Relation.TypesOfRelation;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class FieldNodeTest extends TestCase {
	
	private static final String DESIGNWIZARD_DIR = "classes";
	
	private DesignWizard dw;
	
	public void testGetDeclaredType() throws InexistentEntityException {
		String fieldName = "Class.fieldName";
		String className = "int";
		String parentClassName = "MyClass";
		
		Design design = new Design();
		design.addRelation(TypesOfRelation.INSTANCE, "F:"+fieldName, className);
		design.addRelation(TypesOfRelation.CONTAINS, "MyClass", "F:"+fieldName );
		
		FieldNode field = design.getField(fieldName);
		assertFalse(field.isStatic());
		ClassNode declaredType = design.getClass(className);
		ClassNode parentClass = design.getClass(parentClassName);
		
		assertEquals(field.getType(), declaredType);
		assertEquals(field.getDeclaringClass(), parentClass);
	}
	
	public void testGetShortName() throws InexistentEntityException {
		String fieldName = "Class.fieldName";
		String className = "int";
		Design design = new Design();
		design.addRelation(TypesOfRelation.INSTANCE, "F:"+fieldName, className);
		FieldNode field = design.getField(fieldName);
		assertEquals("fieldName",field.getShortName());
	}
	
	public void testIsStatic() throws IOException, InexistentEntityException {
		DesignWizard dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"singleton.jar");
		FieldNode field = dw.getField("SingletonImplementation.uniqueInstance");
		assertTrue(field.isStatic());
	}
	
	public void testGetPackageName() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
		
		ClassNode classNode = this.dw.getClass(Config.class);
		for (FieldNode field : classNode.getAllFields()) {
			assertEquals("org.designwizard.common", field.getPackage().getName());
		}
		
		classNode = this.dw.getClass(ElementsFactory.class);
		for (FieldNode field : classNode.getAllFields()) {
			assertEquals("org.designwizard.design.factory", field.getPackage().getName());
		}
		
		classNode = this.dw.getClass(ClassNodeTest.class);
		for (FieldNode field : classNode.getAllFields()) {
			assertEquals("tests.org.designwizard.design", field.getPackage().getName());
		}
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"visibility.jar");
		classNode = this.dw.getClass("PrivateFields");
		for (FieldNode field : classNode.getAllFields()) {
			//default package
			assertEquals("default", field.getPackage().getName());
		}
	}
	
	/*public void testFinalAttribute() throws IOException, InexistentEntityException {
		dw = new DesignWizard("resources/testFiles/finalAttCall.jar");
		FieldNode field = dw.getField("Class.field");
		assertTrue(field.isFinal());
		
		//FIXME pass on this test
		assertTrue(field.getCallers().contains(dw.
				getMethod("Class.m()")));
	}*/
	
}