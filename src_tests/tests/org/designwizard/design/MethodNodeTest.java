package tests.org.designwizard.design;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.common.Config;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;
import org.designwizard.design.factory.AbstractElementsFactory;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.exception.InvalidTypeOfVisibility;
import org.junit.Assert;
import org.junit.Test;

public class MethodNodeTest {
	private DesignWizard dw;
	private static final String DESIGNWIZARD_DIR = "classes";

	@Test
	public void testGetPackageName() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);

		ClassNode classNode = this.dw.getClass(Config.class);
		for (MethodNode method : classNode.getAllMethods()) {
			Assert.assertEquals("org.designwizard.common", method.getPackage().getName());
		}


		classNode = this.dw.getClass(AbstractElementsFactory.class);
		for (MethodNode method : classNode.getAllMethods()) {
			Assert.assertEquals("org.designwizard.design.factory", method.getPackage().getName());
		}

		classNode = this.dw.getClass(ClassNodeTest.class);
		for (MethodNode method : classNode.getAllMethods()) {
			Assert.assertEquals("tests.org.designwizard.design", method.getPackage().getName());
		}

		classNode = this.dw.getClass(ClassNodeTest.class);
		for (MethodNode method : classNode.getAllMethods()) {
			Assert.assertEquals("tests.org.designwizard.design", method.getPackage().getName());
		}

		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"visibility.jar");
		classNode = this.dw.getClass("PackageClass");
		for (MethodNode method : classNode.getAllMethods()) {
			Assert.assertEquals("default", method.getPackage().getName());
		}
	}

	@Test
	public void testGetShortName() throws IOException, InexistentEntityException {
		DesignWizard dw = new DesignWizard("classes");
		MethodNode method = dw.getMethod("org.designwizard.design.AbstractEntity.getShortName()");
		Assert.assertEquals("getShortName()",method.getShortName());
		Assert.assertEquals(method.getShortName(), method.getShortName());
		
		method = dw.getMethod("org.designwizard.design.Entity.addModifier(org.designwizard.design.Modifier)");
		Assert.assertEquals("addModifier(org.designwizard.design.Modifier)",method.getShortName());
	}

	@Test
	public void testReturnMethod() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"returnmethod.jar");

		MethodNode method = this.dw.getMethod("AllMethod.voidReturn()");
		Assert.assertEquals("void",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.intReturn()");
		Assert.assertEquals("int",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.byteReturn()");
		Assert.assertEquals("byte",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.charReturn()");
		Assert.assertEquals("char",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.booleanReturn()");
		Assert.assertEquals("boolean",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.doubleReturn()");
		Assert.assertEquals("double",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.floatReturn()");
		Assert.assertEquals("float",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.longReturn()");
		Assert.assertEquals("long",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.typeReturn()");
		Assert.assertEquals("java.lang.Object",method.getReturnType().getName());

		// array of one dimension
		method = this.dw.getMethod("AllMethod.intArrayReturn()");
		Assert.assertEquals("int[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.byteArrayReturn()");
		Assert.assertEquals("byte[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.charArrayReturn()");
		Assert.assertEquals("char[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.booleanArrayReturn()");
		Assert.assertEquals("boolean[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.doubleArrayReturn()");
		Assert.assertEquals("double[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.floatArrayReturn()");
		Assert.assertEquals("float[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.longArrayReturn()");
		Assert.assertEquals("long[]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.typeArrayReturn()");
		Assert.assertEquals("java.lang.Object[]",method.getReturnType().getName());

		// array of 2 dimensions
		method = this.dw.getMethod("AllMethod.int2ArrayReturn()");
		Assert.assertEquals("int[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.byte2ArrayReturn()");
		Assert.assertEquals("byte[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.char2ArrayReturn()");
		Assert.assertEquals("char[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.boolean2ArrayReturn()");
		Assert.assertEquals("boolean[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.double2ArrayReturn()");
		Assert.assertEquals("double[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.float2ArrayReturn()");
		Assert.assertEquals("float[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.long2ArrayReturn()");
		Assert.assertEquals("long[][]",method.getReturnType().getName());

		method = this.dw.getMethod("AllMethod.type2ArrayReturn()");
		Assert.assertEquals("java.lang.Object[][]",method.getReturnType().getName());
	}

	@Test
	public void testMainThrows() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"throws.jar");
		MethodNode method = this.dw.getMethod("Throws.main(java.lang.String[])");
		Set<ClassNode> exceptions = method.getThrownExceptions();
		ClassNode classEntity = this.dw.getClass(IOException.class);
		Assert.assertTrue(exceptions.contains(classEntity));
		classEntity = this.dw.getClass(FileNotFoundException.class);
		Assert.assertTrue(exceptions.contains(classEntity));
		Assert.assertTrue(exceptions.size() == 2);
	}

	@Test
	public void testThrowsNothing() throws Exception {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"throws.jar");
		MethodNode method = this.dw.getMethod("Throws.throwsNothing()");
		Set<ClassNode> exceptions = method.getThrownExceptions();
		Assert.assertEquals(exceptions.size(),0);
	}

	@Test
	public void testThrowsException() throws Exception {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"throws.jar");
		MethodNode method = this.dw.getMethod("Throws.throwsException()");
		Set<ClassNode> exceptions = method.getThrownExceptions();
		ClassNode classEntity = this.dw.getClass(Exception.class);
		Assert.assertTrue(exceptions.contains(classEntity));
		Assert.assertEquals(exceptions.size(),1);
	}
	
	@Test
	public void testReturn() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"returnmethod.jar");
		
		ClassNode clazz = this.dw.getClass("AllMethod");
		
		Set<MethodNode> returnSet = clazz.getAllMethodsThatReturn("void");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("int");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("short");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("byte");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("char");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("boolean");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("double");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("float");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("long");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("java.lang.Object");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn(Object.class.getName());
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("int[]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("byte[]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("short[]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("char[]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("boolean[]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("double[]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("float[]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("long[]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("java.lang.Object[]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("int[][]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("byte[][]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("char[][]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("boolean[][]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("double[][]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("float[][]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("long[][]");
		Assert.assertEquals(returnSet.size() , 1);
		
		returnSet = clazz.getAllMethodsThatReturn("short[][]");
		Assert.assertEquals(returnSet.size() , 1);
				
		returnSet = clazz.getAllMethodsThatReturn("java.lang.Object[][]");
		Assert.assertEquals(returnSet.size() , 1);
	}
	
	@Test
	public void testGetAllMethodsWithVisibility() throws IOException, InvalidTypeOfVisibility, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		MethodNode method = this.dw.getMethod("AccessModifiers.pub()");
		Assert.assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("AccessModifiers.pus(java.lang.String[])");
		Assert.assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("AccessModifiers.<init>()");
		Assert.assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MorePackage.<init>()");
		Assert.assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MorePrivate.<init>()");
		Assert.assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MorePrivate.<init>()");
		Assert.assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MoreProtected.<init>()");
		Assert.assertTrue(method.getModifiers().contains(Modifier.PUBLIC));
		
		method = this.dw.getMethod("MorePublic.<init>()");
		Assert.assertTrue(method.getModifiers().contains(Modifier.PRIVATE));
	}
	
	@Test
	public void testMethodsWithRegularExpressions() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
		
		// Testa o retorno das method que contém a string "get"
		Set<MethodNode> methodThatCointainsGet = dw.getMethods(".*get.*");
		Assert.assertFalse("Expected False but came True", methodThatCointainsGet.isEmpty());
		Assert.assertTrue(
				methodThatCointainsGet.contains(dw.getMethod("org.designwizard.design.Entity.getClassNode()")));
		Assert.assertTrue(
				methodThatCointainsGet.contains(dw.getMethod("org.designwizard.design.PackageNode.getAllMethods()")));
		Assert.assertEquals(193, methodThatCointainsGet.size());

		// Testa o retorno das method que terminam com a string "String)"
		Set<MethodNode> methodsFinishingWithParenteses = dw.getMethods(".*(String(\\W))$");
		Assert.assertFalse(methodsFinishingWithParenteses.isEmpty());
		Assert.assertTrue(methodsFinishingWithParenteses
				.contains(dw.getMethod("org.designwizard.design.Design.packageExtracted(java.lang.String)")));
		Assert.assertEquals(130, methodsFinishingWithParenteses.size());

		// Testa o retorno das method que contém a string "get" ou ".set"
		Set<MethodNode> methodsThatContainsGetOrSet = dw.getMethods(".*get.*|.*(\\W)set.*");
		Assert.assertFalse(methodsThatContainsGetOrSet.isEmpty());
		Assert.assertTrue(methodsThatContainsGetOrSet.contains(
				dw.getMethod("org.designwizard.design.Design.setReturnType(java.lang.String,java.lang.String)")));
		Assert.assertTrue(methodsThatContainsGetOrSet
				.contains(dw.getMethod("org.designwizard.design.PackageNode.getAllMethods()")));
		Assert.assertFalse(methodsThatContainsGetOrSet.contains(dw.getMethod("org.designwizard.api.util.FileUtil.reset()")));
		Assert.assertEquals(226, methodsThatContainsGetOrSet.size());
	}	
}
