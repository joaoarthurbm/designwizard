package tests.org.designwizard.design;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.common.Config;
import org.designwizard.design.AbstractEntity;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Design;
import org.designwizard.design.DesignIF;
import org.designwizard.design.Entity;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;
import org.designwizard.design.PackageNode;
import org.designwizard.design.factory.AbstractElementsFactory;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.exception.InvalidTypeOfVisibility;
import org.designwizard.exception.NotAnInterfaceException;
import org.designwizard.patternchecker.ResultListener;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestSuite;
import tests.org.designwizard.design.relation.RelationTest;
import tests.suites.AllTests;

public class ClassNodeTest {
	
	private DesignWizard dw;
	private static final String DESIGNWIZARD_DIR = "classes";
	
	@After
	public void tearDown() throws Exception {
		this.dw = null;
	}

	@Before
	public void setUp() throws Exception {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
	}	
	
	@Test
	public void testLoadClass() throws IOException, InexistentEntityException {
		//FIXME Adequar esses testes para nova suíte de testes.
		MethodNode allTest = dw.getMethod(AllTests.class.getName()+".suite()");
		Set<ClassNode> usedBy = allTest.getCalleeClasses();
				
		ClassNode loaded = this.dw.getClass(RelationTest.class);
		Assert.assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(CatchBlockTest.class);
		Assert.assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(CatchedExceptionsTest.class);
		Assert.assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(tests.org.designwizard.design.RedBarTest.class);
		Assert.assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(FieldNodeTest.class);
		Assert.assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(ClassNodeTest.class);
		Assert.assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(AllClassesInSuiteTest.class);
		Assert.assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(TestSuite.class);
		Assert.assertTrue(usedBy.remove(loaded));
	}

	@Test
	public void testIsPrimitive() throws IOException, InexistentEntityException {
		ClassNode entity;
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"returnmethod.jar");
		
		MethodNode method = this.dw.getMethod("AllMethod.voidReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.intReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.byteReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.charReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.booleanReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.doubleReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.floatReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.longReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.charReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.typeReturn()");
		entity = method.getReturnType();
		Assert.assertFalse(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.shortReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isPrimitive());
	}
	
	@Test
	public void testIsArray() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"returnmethod.jar");
		MethodNode method = this.dw.getMethod("AllMethod.intArrayReturn()");
		ClassNode entity = method.getReturnType();
		Assert.assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.intArrayReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.int2ArrayReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.shortArrayReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.short2ArrayReturn()");
		entity = method.getReturnType();
		Assert.assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.typeReturn()");
		entity = method.getReturnType();
		Assert.assertFalse(entity.isArray());
	}
	
	@Test
	public void testImplementedInterfaces() throws InexistentEntityException, IOException {
		// No interface is implemented.
		ClassNode classNode = this.dw.getClass(ClassNode.class);
		Set<ClassNode> implementedInterfaces = classNode.getImplementedInterfaces();
		Assert.assertEquals(1,implementedInterfaces.size());
		Assert.assertTrue(implementedInterfaces.contains(this.dw.getClass(Entity.class)));
		
		classNode = this.dw.getClass(AbstractEntity.class);
		implementedInterfaces = classNode.getImplementedInterfaces();
		Assert.assertEquals(1,implementedInterfaces.size());
		Assert.assertEquals(this.dw.getClass(Entity.class), implementedInterfaces.iterator().next());
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"implements2Interfaces.jar");
		classNode = this.dw.getClass("SingletonBadConstructor");
		implementedInterfaces = classNode.getImplementedInterfaces();
		Assert.assertEquals(2,implementedInterfaces.size());
	}
	
	@Test
	public void testGetPackage() throws IOException, InexistentEntityException {
		
		ClassNode classNode = this.dw.getClass(Config.class);
		Assert.assertEquals("org.designwizard.common", classNode.getPackage().getName());
		
		classNode = this.dw.getClass(AbstractElementsFactory.class);
		Assert.assertEquals("org.designwizard.design.factory", classNode.getPackage().getName());
		
		classNode = this.dw.getClass(ClassNodeTest.class);
		Assert.assertEquals("tests.org.designwizard.design", classNode.getPackage().getName());
		
		ClassNode anotherClassNode = this.dw.getClass(FieldNodeTest.class);
		Assert.assertEquals(anotherClassNode.getPackage().getName(), classNode.getPackage().getName());
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"visibility.jar");
		classNode = this.dw.getClass("PackageClass");
		Assert.assertEquals("default",classNode.getPackage().getName());
	}
	
	@Test
	public void testIsInterface() throws InexistentEntityException {
		ClassNode interfaceEntity = this.dw.getClass(Entity.class);
		Assert.assertTrue(interfaceEntity.isInterface());
		
		interfaceEntity = this.dw.getClass(DesignIF.class);
		Assert.assertTrue(interfaceEntity.isInterface());
		Assert.assertTrue(interfaceEntity.containsModifiers(Modifier.ABSTRACT));
		
		interfaceEntity = this.dw.getClass(ResultListener.class);
		Assert.assertTrue(interfaceEntity.isInterface());
		
		interfaceEntity = this.dw.getClass(AbstractElementsFactory.class);
		Assert.assertTrue(interfaceEntity.isInterface());
		
		ClassNode classEntity = this.dw.getClass(ClassNodeTest.class);
		Assert.assertFalse(classEntity.isInterface());
		
		classEntity = this.dw.getClass(AbstractEntity.class);
		Assert.assertFalse(classEntity.isInterface());
		Assert.assertTrue(interfaceEntity.containsModifiers(Modifier.ABSTRACT));
		
		classEntity = this.dw.getClass(FieldNode.class);
		Assert.assertFalse(classEntity.isInterface());
		Assert.assertFalse(classEntity.containsModifiers(Modifier.ABSTRACT));
		
		classEntity = this.dw.getClass(FieldNodeTest.class);
		Assert.assertFalse(classEntity.isInterface());
	}
	
	@Test
	public void testGetCallers() throws InexistentEntityException {
		ClassNode classNode = this.dw.getClass(ClassNodeTest.class);
		Set<MethodNode> callers = classNode.getCallerMethods();
		Assert.assertTrue(callers.contains(this.dw.getMethod("tests.suites.AllTests.suite()")));
	}
	
	@Test
	public void testInheritedFieldsAndMethods() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
		
		ClassNode classNode = this.dw.getClass(ClassNode.class);
		ClassNode abstractEntity = this.dw.getClass(AbstractEntity.class);
		
		Set<MethodNode> inheritedMethods = classNode.getInheritedMethods();
		for (MethodNode method: abstractEntity.getAllMethods()) {
			Assert.assertTrue(method.getName(),inheritedMethods.contains(method));
		}
		
		Assert.assertEquals(inheritedMethods.size(), abstractEntity.getAllMethods().size());
		
		Set<MethodNode> declaredMethods = classNode.getDeclaredMethods();
		MethodNode method = this.dw.getMethod("org.designwizard.design.ClassNode.<init>(java.lang.String)");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.addRelation(org.designwizard.design.relation.Relation)");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.addInheritedField(org.designwizard.design.FieldNode)");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getShortName()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getAllMethods()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getStaticMethods()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getCallerClasses()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getCalleeClasses()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getSuperClass()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getSubClasses()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getMethods(org.designwizard.design.Modifier[])");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getConstructors()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getDeclaredFields()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getImpactOfAChange()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.toString()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.printComponents(java.util.Set)");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.equals(java.lang.Object)");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getCallerMethods()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getInheritedFields()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getAllFields()");
		Assert.assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getInheritedFields()");
		Assert.assertTrue(declaredMethods.contains(method));
	}
	
	@Test(expected=NotAnInterfaceException.class)
	public void testGetEntitiesThatImplements() throws IOException, InexistentEntityException, NotAnInterfaceException {
		ClassNode entity = dw.getClass(AbstractEntity.class);
		Set<ClassNode> implementsEntity = null;
		
		implementsEntity = entity.getEntitiesThatImplements();
		
		entity = dw.getClass(Entity.class);
		implementsEntity = entity.getEntitiesThatImplements();
		
		Assert.assertTrue(implementsEntity.contains(dw.getClass(AbstractEntity.class)));
		Assert.assertTrue(implementsEntity.contains(dw.getClass(FieldNode.class)));
		Assert.assertTrue(implementsEntity.contains(dw.getClass(ClassNode.class)));
		Assert.assertTrue(implementsEntity.contains(dw.getClass(MethodNode.class)));
		Assert.assertTrue(implementsEntity.contains(dw.getClass(PackageNode.class)));
		
		Assert.assertEquals(5,implementsEntity.size());
		
		entity = dw.getClass(DesignIF.class);
		implementsEntity = entity.getEntitiesThatImplements();
		
		Assert.assertTrue(implementsEntity.contains(dw.getClass(Design.class)));
		Assert.assertEquals(1,implementsEntity.size());
		
		dw = new DesignWizard("resources/testFiles/impactwithinheritance.jar");
		entity = dw.getClass("IVeiculo");
		implementsEntity = entity.getEntitiesThatImplements();
		
		Assert.assertTrue(implementsEntity.contains(dw.getClass("IMoto")));
		Assert.assertTrue(implementsEntity.contains(dw.getClass("ICarro")));
		Assert.assertEquals(2,implementsEntity.size());
		
		entity = dw.getClass("IMoto");
		implementsEntity = entity.getEntitiesThatImplements();
		Assert.assertTrue(implementsEntity.contains(dw.getClass("Moto")));
		Assert.assertEquals(1,implementsEntity.size());
		
		entity = dw.getClass("ICarro");
		implementsEntity = entity.getEntitiesThatImplements();
		Assert.assertTrue(implementsEntity.contains(dw.getClass("Carro")));
		Assert.assertEquals(1,implementsEntity.size());
	}
	
	@Test
	public void testGetAllMethodsWithVisibilityAndStaticOption() throws IOException, InvalidTypeOfVisibility, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		// dont forget the default constructors.
		ClassNode classEntity = this.dw.getClass("AccessModifiers");
		Set<MethodNode> allMethods = classEntity.getMethods(Modifier.PUBLIC, Modifier.STATIC);
		Assert.assertEquals(allMethods.size(),1);
		
		classEntity = this.dw.getClass("MorePublic");
		allMethods = classEntity.getMethods(Modifier.PUBLIC, Modifier.STATIC);
		Assert.assertEquals(allMethods.size(),1);
	}
	
	@Test
	public void testGetAllMethods() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		Set<ClassNode> allClasses = this.dw.getAllClasses();
		
		int total = 0;
		for (ClassNode clazz : allClasses) {
			total+=clazz.getAllMethods().size();
		}
		Assert.assertEquals(total,21);
	}
	
	@Test
	public void testGetWithVisibilityAndStaticOption() throws IOException, InexistentEntityException, InvalidTypeOfVisibility{
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		Set<MethodNode> allMethods = this.dw.getClass("AccessModifiers").getAllMethods();
		Assert.assertEquals(allMethods.size(),9);
		
		Set<MethodNode> methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PUBLIC,Modifier.STATIC);
		Assert.assertEquals(methods.size(),1);
		
		methods = this.dw.getClass("MorePublic").getMethods(Modifier.PUBLIC, Modifier.STATIC);
		Assert.assertEquals(methods.size(),1);
		
		/**
		 * Methods public and static
		 */
		methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PUBLIC);
		int count = 0;
		for (MethodNode method: methods) {
			if (method.containsModifiers(Modifier.STATIC)) count+=1;
		}
		Assert.assertEquals(count,1);
		
		methods = this.dw.getClass("MorePublic").getMethods(Modifier.PUBLIC);
		count = 0;
		for (MethodNode method: methods) {
			if (method.containsModifiers(Modifier.STATIC)) count+=1;
		}
		Assert.assertEquals(count,1);
		
		/**
		 * Looking for methods that are just public
		 */
		methods = this.dw.getClass("MorePublic").getMethods(Modifier.PUBLIC);
		count = 0;
		for (MethodNode method: methods) {
			if (!method.containsModifiers(Modifier.STATIC)) count+=1;
		}
		Assert.assertEquals(count,1);
		
		methods = this.dw.getClass("MorePrivate").getMethods(Modifier.PUBLIC);
		Assert.assertEquals(methods.size(),1);
		
		methods = this.dw.getClass("MorePrivate").getMethods(Modifier.PUBLIC);
		Assert.assertEquals(methods.size(),1);
	}
	
	@Test
	public void testGetWithVisibility() throws IOException, InexistentEntityException, InvalidTypeOfVisibility{
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		Set<MethodNode> allMethods = this.dw.getClass("AccessModifiers").getAllMethods();
		Assert.assertEquals(allMethods.size(),9);
		
		// remember that the classes without constructors have a default public constructor
		Set<MethodNode> methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PUBLIC);
		Assert.assertEquals(methods.size(),3);
		
		methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PRIVATE);
		Assert.assertEquals(methods.size(),2);
	
		methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PROTECTED);
		Assert.assertEquals(methods.size(),2);
	
		methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PACKAGE);
		Assert.assertEquals(methods.size(),2);
	}
	
	@Test
	public void testGetAllMethodsInClass() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		Set<MethodNode> methods = this.dw.getClass("AccessModifiers").getAllMethods();
		Assert.assertEquals(9,methods.size());
		
		MethodNode method = this.dw.getMethod("AccessModifiers.pus(java.lang.String[])");
		Assert.assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pros()");
		Assert.assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pris()");
		Assert.assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.fris()");
		Assert.assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pub()");
		Assert.assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pri()");
		Assert.assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pro()");
		Assert.assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.fri()");
		Assert.assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.<init>()");
		Assert.assertTrue(methods.contains(method));
	}
	
	@Test
	public void testGetSubClasses() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"hierarchy.jar");
		ClassNode clazz = this.dw.getClass("SuperClass");
		Set<ClassNode> subClasses = clazz.getSubClasses();
		Assert.assertEquals(subClasses.size(),2);
		Assert.assertTrue(subClasses.contains(this.dw.getClass("SubClass")));
		Assert.assertTrue(subClasses.contains(this.dw.getClass("ExtendsAndImplements")));
			
		subClasses = this.dw.getClass("Interface").getSubClasses();
		Assert.assertEquals(subClasses.size(),0);
	}
	
	@Test
	public void testGetClass() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("classes");
		ClassNode classEntity = this.dw.getClass(DesignWizard.class);
		Assert.assertTrue(classEntity.containsModifiers(Modifier.PUBLIC));
		Assert.assertFalse(classEntity.containsModifiers(Modifier.STATIC));
		Assert.assertEquals(1,classEntity.getModifiers().size());
	}
	
	@Test
	public void testGetSuperClasses() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"hierarchy.jar");
		ClassNode superClass = this.dw.getClass("SubClass").getSuperClass();
		Assert.assertEquals(superClass, this.dw.getClass("SuperClass"));
		superClass = this.dw.getClass("ExtendsAndImplements").getSuperClass();
		Assert.assertEquals(superClass, this.dw.getClass("SuperClass"));
	}
	
	/**
	 * Assests if the number of static methods on classes are not large. (This may be a bad sinal in Desing.)
	 * @throws InexistentEntityException 
	 * @throws IOException 
	 *
	 */
	@Test
	public void testNumStaticMethods() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		Set<MethodNode> staticMethods = this.dw.getClass("AccessModifiers").getStaticMethods();
		Assert.assertEquals(4,staticMethods.size());
	}	
	
	@Test
	public void testGetClassesThatUses() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"edados1.jar");
	
		// ha apenas uma classe na IU
		Set<ClassNode> ius;
		MethodNode m = this.dw.getMethod("Inteiro.ehInteiro(char)");
		ius = m.getCallerClasses();
		Assert.assertTrue(ius.contains(this.dw.getClass("Polinomio")));
		Assert.assertEquals(1,ius.size());
		
		FieldNode f = this.dw.getField("java.lang.System.in");
		ius = f.getCallerClasses();
		Assert.assertTrue(ius.contains(this.dw.getClass("CalculadoraMain")));
		Assert.assertEquals(1,ius.size());
		
		f = this.dw.getField("Logica.mapaEntrada");
		ius = f.getCallerClasses();
		Assert.assertTrue(ius.contains(this.dw.getClass("Logica")));
		Assert.assertTrue(ius.contains(this.dw.getClass("Polinomio")));
		Assert.assertEquals(2,ius.size());
		
		f = this.dw.getField("Polinomio.map");
		ius = f.getCallerClasses();
		Assert.assertTrue(ius.contains(this.dw.getClass("Polinomio")));
		Assert.assertEquals(1, ius.size());
		
		f = this.dw.getField("java.lang.System.out");
		ius = f.getCallerClasses();
		Assert.assertEquals(1, ius.size());
		Assert.assertTrue(ius.contains(this.dw.getClass("CalculadoraMain")));
		
		f = this.dw.getField("pacote.outro.Teste.variavel");
		ius = f.getCallerClasses();
		Assert.assertEquals(1, ius.size());
		Assert.assertTrue(ius.contains(this.dw.getClass("pacote.ClasseDentroDoPacote")));
		
		ClassNode clazz = this.dw.getClass("pacote.outro.Teste");
		ius = clazz.getCallerClasses();
		Assert.assertEquals(1, ius.size());
		Assert.assertTrue(ius.contains(this.dw.getClass("pacote.ClasseDentroDoPacote")));
		
		clazz = this.dw.getClass("TratamentosComuns");
		ius = clazz.getCallerClasses();
		Assert.assertEquals(2, ius.size());
		Assert.assertTrue(ius.contains(this.dw.getClass("Logica")));
		Assert.assertTrue(ius.contains(this.dw.getClass("Polinomio")));
	}
	
	@Test
	public void testGetClassesUsedBy() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"edados1.jar");
		
		ClassNode clazz = this.dw.getClass("CalculadoraMain");
		
		MethodNode m = this.dw.getMethod("CalculadoraMain.main(java.lang.String[])");
		Set<ClassNode> usedBy = m.getCalleeClasses();
		Assert.assertEquals(7,usedBy.size());
		Assert.assertTrue(usedBy.contains(this.dw.getClass("CalculadoraMain")));
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Logica")));
		
		clazz = this.dw.getClass("CalculadoraMain");
		usedBy = clazz.getCalleeClasses();
		Assert.assertEquals(8,usedBy.size());
		Assert.assertTrue(usedBy.contains(this.dw.getClass("CalculadoraMain")));
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Logica")));
		
		clazz = this.dw.getClass("CalculadoraPolinomial");
		usedBy = clazz.getCalleeClasses();
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Polinomio")));
		Assert.assertEquals(7,usedBy.size());
		
		clazz = this.dw.getClass("Comando");
		usedBy = clazz.getCalleeClasses();
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Comando")));
		Assert.assertEquals(5,usedBy.size());
		
		clazz = this.dw.getClass("IF");
		usedBy = clazz.getCalleeClasses();
		Assert.assertEquals(2, usedBy.size());
		
		clazz = this.dw.getClass("Inteiro");
		usedBy = clazz.getCalleeClasses();
		Assert.assertEquals(6,usedBy.size());
		
		clazz = this.dw.getClass("Logica");
		usedBy = clazz.getCalleeClasses();
		Assert.assertTrue(usedBy.contains(this.dw.getClass("TratamentosComuns")));
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Polinomio")));
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Logica")));
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Comando")));
		Assert.assertTrue(usedBy.contains(this.dw.getClass("CalculadoraPolinomial")));
		Assert.assertEquals(23,usedBy.size());
		
		clazz = this.dw.getClass("Polinomio");
		usedBy = clazz.getCalleeClasses();
		Assert.assertTrue(usedBy.contains(this.dw.getClass("TratamentosComuns")));
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Polinomio")));
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Inteiro")));
		Assert.assertTrue(usedBy.contains(this.dw.getClass("Logica")));
		Assert.assertEquals(18,usedBy.size());
		
		clazz = this.dw.getClass("TratamentosComuns");
		usedBy = clazz.getCalleeClasses();
		Assert.assertFalse(usedBy.isEmpty());
		
		clazz = this.dw.getClass("pacote.outro.Teste");
		usedBy = clazz.getCalleeClasses();
		Assert.assertFalse(usedBy.isEmpty());

		clazz = this.dw.getClass("pacote.ClasseDentroDoPacote");
		usedBy = clazz.getCalleeClasses();
		
		Assert.assertTrue(usedBy.contains(this.dw.getClass("pacote.outro.Teste")));
		Assert.assertEquals(2,usedBy.size());
		Assert.assertFalse(usedBy.isEmpty());
	}
	
	@Test
	public void testGetAllClasses() throws IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"edados1.jar");
		Assert.assertEquals(10,dw.getAllClasses().size());
	}
	
	@Test
	public void testInnerClass() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"innerclass-relations.jar");
		ClassNode clazz = this.dw.getClass("Logica.Polinomio$Monomio");
		
		Set<ClassNode> innerusers = clazz.getCallerClasses();
		Assert.assertEquals(2,innerusers.size());
		Assert.assertTrue(innerusers.contains(this.dw.getClass("Logica.Polinomio$Monomio")));
		Assert.assertTrue(innerusers.contains(this.dw.getClass("Logica.Polinomio")));
	
		Set<ClassNode> innerUse = clazz.getCalleeClasses();
		Assert.assertEquals(9,innerUse.size());
		Assert.assertTrue(innerUse.contains(this.dw.getClass("Logica.Polinomio$Monomio")));
	}

	@Test
	public void testGetConstructors() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"constructors.jar");
		ClassNode entity = this.dw.getClass("MultiplyConstructors");
		Set<MethodNode> constructors = entity.getConstructors();
		
		Assert.assertEquals(4,constructors.size());
		MethodNode method = this.dw.getMethod("MultiplyConstructors.<init>()");
		Assert.assertTrue(constructors.contains(method));
		
		method = this.dw.getMethod("MultiplyConstructors.<init>(double)");
		Assert.assertTrue(constructors.contains(method));
		
		method = this.dw.getMethod("MultiplyConstructors.<init>(int)");
		Assert.assertTrue(constructors.contains(method));
		
		method = this.dw.getMethod("MultiplyConstructors.<init>(float)");
		Assert.assertTrue(constructors.contains(method));
		
		/**
		 * Don not forget the default constructor.
		 */
		entity = this.dw.getClass("NoneConstructors");
		constructors = entity.getConstructors();
		Assert.assertEquals(1,constructors.size());
		method = this.dw.getMethod("NoneConstructors.<init>()");
		Assert.assertTrue(constructors.contains(method));
		
		entity = this.dw.getClass("PackageConstructor");
		constructors = entity.getConstructors();
		Assert.assertEquals(1,constructors.size());
		method = this.dw.getMethod("PackageConstructor.<init>()");
		Assert.assertTrue(constructors.contains(method));
		
		entity = this.dw.getClass("PrivateConstructor");
		constructors = entity.getConstructors();
		Assert.assertEquals(1,constructors.size());
		method = this.dw.getMethod("PrivateConstructor.<init>()");
		Assert.assertTrue(constructors.contains(method));
		
		entity = this.dw.getClass("PublicConstructor");
		constructors = entity.getConstructors();
		Assert.assertEquals(1,constructors.size());
		method = this.dw.getMethod("PublicConstructor.<init>(int)");
		Assert.assertTrue(constructors.contains(method));
	}
	
	@Test
	public void testClassesWithRegularExpressions() throws InexistentEntityException {
		// Testa o retorno das classes que contém a string "api"
		Set<ClassNode> classesThatContainsApi = dw.getClasses(".*api.*");
		Assert.assertFalse(classesThatContainsApi.isEmpty());
		Assert.assertTrue(classesThatContainsApi.contains(dw.getClass("org.designwizard.api.DesignWizard")));
		Assert.assertTrue(classesThatContainsApi.contains(dw.getClass("org.designwizard.api.util.FileUtil")));
		Assert.assertTrue(classesThatContainsApi.size() == 2);
		
		// Testa o retorno das classes que terminam com a string "Wizard"
		Set<ClassNode> classesFinishWithApi = dw.getClasses(".*Wizard$");
		Assert.assertFalse(classesFinishWithApi.isEmpty());
		Assert.assertTrue(classesFinishWithApi.contains(dw.getClass("org.designwizard.api.DesignWizard")));
		Assert.assertTrue(classesFinishWithApi.size() == 1);
				
		// Testa o retorno das classes que iniciam com a string "api"
		Set<ClassNode> classesStartingWithApi = dw.getClasses("^api.*");
		Assert.assertTrue(classesStartingWithApi.isEmpty());
		
		// Testa o retorno das classes que contém a string "common" ou "factory"
		Set<ClassNode> classesThatContainsCommonOrFactory = dw.getClasses(".*common.*|.*factory.*");
		Assert.assertFalse(classesThatContainsCommonOrFactory.isEmpty());
		Assert.assertTrue(classesThatContainsCommonOrFactory.contains(dw.getClass("org.designwizard.design.factory.ElementsFactory")));
		Assert.assertTrue(classesThatContainsCommonOrFactory.contains(dw.getClass("org.designwizard.design.factory.AbstractElementsFactory")));
		Assert.assertTrue(classesThatContainsCommonOrFactory.contains(dw.getClass("org.designwizard.common.Config")));
		Assert.assertTrue(classesThatContainsCommonOrFactory.size() == 3);
	}	
}