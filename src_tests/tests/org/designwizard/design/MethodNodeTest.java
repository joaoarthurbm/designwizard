package tests.org.designwizard.design;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import junit.framework.TestCase;

import org.designwizard.api.DesignWizard;
import org.designwizard.common.Config;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;
import org.designwizard.design.factory.AbstractElementsFactory;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.exception.InvalidTypeOfVisibility;

public class MethodNodeTest extends TestCase {
	private DesignWizard dw;
	private static final String DESIGNWIZARD_DIR = "classes";

	public void testGetPackageName() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);

		ClassNode classNode = this.dw.getClass(Config.class);
		for (MethodNode method : classNode.getAllMethods()) {
			assertEquals("org.designwizard.common", method.getPackage().getName());
		}


		classNode = this.dw.getClass(AbstractElementsFactory.class);
		for (MethodNode method : classNode.getAllMethods()) {
			assertEquals("org.designwizard.design.factory", method.getPackage().getName());
		}


		classNode = this.dw.getClass(ClassNodeTest.class);
		for (MethodNode method : classNode.getAllMethods()) {
			assertEquals("tests.org.designwizard.design", method.getPackage().getName());
		}

		classNode = this.dw.getClass(ClassNodeTest.class);
		for (MethodNode method : classNode.getAllMethods()) {
			assertEquals("tests.org.designwizard.design", method.getPackage().getName());
		}

		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"visibility.jar");
		classNode = this.dw.getClass("PackageClass");
		for (MethodNode method : classNode.getAllMethods()) {
			assertEquals("default", method.getPackage().getName());
		}
	}

	public void testGetShortName() throws IOException, InexistentEntityException {

		DesignWizard dw = new DesignWizard("classes");
		MethodNode method = dw.getMethod("org.designwizard.design.AbstractEntity.getShortName()");
		assertEquals("getShortName()",method.getShortName());
		assertEquals(method.getShortName(), method.getShortName());
		
		method = dw.getMethod("org.designwizard.design.Entity.addModifier(org.designwizard.design.Modifier)");
		assertEquals("addModifier(org.designwizard.design.Modifier)",method.getShortName());
		
	}

	public void testReturnMethod() throws InexistentEntityException, IOException {
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"returnmethod.jar");

		MethodNode method = this.dw.getMethod("AllMethod.voidReturn()");
		assertEquals("void",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.intReturn()");
		assertEquals("int",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.byteReturn()");
		assertEquals("byte",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.charReturn()");
		assertEquals("char",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.booleanReturn()");
		assertEquals("boolean",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.doubleReturn()");
		assertEquals("double",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.floatReturn()");
		assertEquals("float",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.longReturn()");
		assertEquals("long",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.typeReturn()");
		assertEquals("java.lang.Object",method.getReturnType().getName());


		// array of one dimension
		method = this.dw.getMethod("AllMethod.intArrayReturn()");
		assertEquals("int[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.byteArrayReturn()");
		assertEquals("byte[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.charArrayReturn()");
		assertEquals("char[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.booleanArrayReturn()");
		assertEquals("boolean[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.doubleArrayReturn()");
		assertEquals("double[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.floatArrayReturn()");
		assertEquals("float[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.longArrayReturn()");
		assertEquals("long[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.typeArrayReturn()");
		assertEquals("java.lang.Object[]",method.getReturnType().getName());


		// array of 2 dimensions
		method = this.dw.getMethod("AllMethod.int2ArrayReturn()");
		assertEquals("int[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.byte2ArrayReturn()");
		assertEquals("byte[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.char2ArrayReturn()");
		assertEquals("char[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.boolean2ArrayReturn()");
		assertEquals("boolean[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.double2ArrayReturn()");
		assertEquals("double[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.float2ArrayReturn()");
		assertEquals("float[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.long2ArrayReturn()");
		assertEquals("long[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.type2ArrayReturn()");
		assertEquals("java.lang.Object[][]",method.getReturnType().getName());
	
	}


	public void testMainThrows() throws InexistentEntityException, IOException {
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"throws.jar");
		MethodNode method = this.dw.getMethod("Throws.main(java.lang.String[])");
		Set<ClassNode> exceptions = method.getThrownExceptions();
		ClassNode classEntity = this.dw.getClass(IOException.class);
		assertTrue(exceptions.contains(classEntity));
		classEntity = this.dw.getClass(FileNotFoundException.class);
		assertTrue(exceptions.contains(classEntity));
		assertTrue(exceptions.size() == 2);
	
	}

	public void testThrowsNothing() throws Exception {
	
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"throws.jar");
		MethodNode method = this.dw.getMethod("Throws.throwsNothing()");
		Set<ClassNode> exceptions = method.getThrownExceptions();
		assertEquals(exceptions.size(),0);
	
	}

	public void testThrowsException() throws Exception {
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"throws.jar");
		MethodNode method = this.dw.getMethod("Throws.throwsException()");
		Set<ClassNode> exceptions = method.getThrownExceptions();
		ClassNode classEntity = this.dw.getClass(Exception.class);
		assertTrue(exceptions.contains(classEntity));
		assertEquals(exceptions.size(),1);
	
	}
	
	public void testReturn() throws IOException, InexistentEntityException {
	
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"returnmethod.jar");
		
		ClassNode clazz = this.dw.getClass("AllMethod");
		
		Set<MethodNode> returnSet = clazz.getAllMethodsThatReturn("void");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("int");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("short");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("byte");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("char");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("boolean");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("double");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("float");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("long");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("java.lang.Object");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn(Object.class.getName());
		assertEquals(returnSet.size() , 1);
		
		
		returnSet = clazz.getAllMethodsThatReturn("int[]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("byte[]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("short[]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("char[]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("boolean[]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("double[]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("float[]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("long[]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("java.lang.Object[]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("int[][]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("byte[][]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("char[][]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("boolean[][]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("double[][]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("float[][]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("long[][]");
		assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("short[][]");
		assertEquals(returnSet.size() , 1);
				
		returnSet = clazz.getAllMethodsThatReturn("java.lang.Object[][]");
		assertEquals(returnSet.size() , 1);
	
	}
	
	public void testGetAllMethodsWithVisibility() throws IOException, InvalidTypeOfVisibility, InexistentEntityException {
	
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		MethodNode method = this.dw.getMethod("AccessModifiers.pub()");
		assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("AccessModifiers.pus(java.lang.String[])");
		assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("AccessModifiers.<init>()");
		assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MorePackage.<init>()");
		assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MorePrivate.<init>()");
		assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MorePrivate.<init>()");
		assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MoreProtected.<init>()");
		assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MorePublic.<init>()");
		assertTrue(method.getModifiers().contains(Modifier.PRIVATE));
	}
	
	public void testMethodsWithRegularExpressions() throws InexistentEntityException, IOException {
			this.dw = new DesignWizard(DESIGNWIZARD_DIR);
		
			// Testa o retorno das method que contém a string "get"
			Set<MethodNode> methodThatCointainsGet = dw.getMethods(".*get.*");
			assertFalse("Expected False but came True",methodThatCointainsGet.isEmpty());
			assertTrue(methodThatCointainsGet.contains(dw.getMethod("org.designwizard.design.Entity.getClassNode()")));
			assertTrue(methodThatCointainsGet.contains(dw.getMethod("org.designwizard.design.PackageNode.getAllMethods()")));
			assertTrue(methodThatCointainsGet.size() == 189);
			
			// Testa o retorno das method que terminam com a string "String)"
			Set<MethodNode> methodsFinishingWithParenteses = dw.getMethods(".*(String(\\W))$");
			assertFalse(methodsFinishingWithParenteses.isEmpty());
			assertTrue(methodsFinishingWithParenteses.contains(dw.getMethod("org.designwizard.design.Design.packageExtracted(java.lang.String)")));
			assertTrue(methodsFinishingWithParenteses.size() == 125);
					
			// Testa o retorno das method que contém a string "get" ou ".set"
			Set<MethodNode> methodsThatContainsGetOrSet = dw.getMethods(".*get.*|.*(\\W)set.*");
			assertFalse(methodsThatContainsGetOrSet.isEmpty());
			assertTrue(methodsThatContainsGetOrSet.contains(dw.getMethod("org.designwizard.design.Design.setReturnType(java.lang.String,java.lang.String)")));
			assertTrue(methodsThatContainsGetOrSet.contains(dw.getMethod("org.designwizard.design.PackageNode.getAllMethods()")));
			assertFalse(methodsThatContainsGetOrSet.contains(dw.getMethod("org.designwizard.api.util.FileUtil.reset()")));
			assertTrue(methodsThatContainsGetOrSet.size() == 220);
	}	
	
}
