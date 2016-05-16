package tests.org.designwizard.design;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import junit.framework.TestCase;
import junit.framework.TestSuite;

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

import tests.org.designwizard.design.relation.RelationTest;
import tests.suites.AllTests;

public class ClassNodeTest extends TestCase {
	
	private DesignWizard dw;
	private static final String DESIGNWIZARD_DIR = "classes";
	
	@Override
	protected void tearDown() throws Exception {
		this.dw = null;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
	}	
	
	public void testLoadClass() throws IOException, InexistentEntityException {
		MethodNode allTest = dw.getMethod(AllTests.class.getName()+".suite()");
		Set<ClassNode> usedBy = allTest.getCalleeClasses();
		
		ClassNode loaded = this.dw.getClass(RelationTest.class);
		assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(CatchBlockTest.class);
		assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(CatchedExceptionsTest.class);
		assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(tests.org.designwizard.design.RedBarTest.class);
		assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(FieldNodeTest.class);
		assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(ClassNodeTest.class);
		assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(AllClassesInSuiteTest.class);
		assertTrue(usedBy.remove(loaded));
		
		loaded = this.dw.getClass(TestSuite.class);
		assertTrue(usedBy.remove(loaded));
	}

	public void testIsPrimitive() throws IOException, InexistentEntityException {
		ClassNode entity;
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"returnmethod.jar");
		
		MethodNode method = this.dw.getMethod("AllMethod.voidReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.intReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.byteReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.charReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.booleanReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.doubleReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.floatReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.longReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.charReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.typeReturn()");
		entity = method.getReturnType();
		assertFalse(entity.isPrimitive());
		
		method = this.dw.getMethod("AllMethod.shortReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isPrimitive());
	}
	
	public void testIsArray() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"returnmethod.jar");
		MethodNode method = this.dw.getMethod("AllMethod.intArrayReturn()");
		ClassNode entity = method.getReturnType();
		assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.intArrayReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.int2ArrayReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.shortArrayReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.short2ArrayReturn()");
		entity = method.getReturnType();
		assertTrue(entity.isArray());
		
		method = this.dw.getMethod("AllMethod.typeReturn()");
		entity = method.getReturnType();
		assertFalse(entity.isArray());
	}
	
	public void testImplementedInterfaces() throws InexistentEntityException, IOException {
		// No interface is implemented.
		ClassNode classNode = this.dw.getClass(ClassNode.class);
		Set<ClassNode> implementedInterfaces = classNode.getImplementedInterfaces();
		assertEquals(1,implementedInterfaces.size());
		assertTrue(implementedInterfaces.contains(this.dw.getClass(Entity.class)));
		
		classNode = this.dw.getClass(AbstractEntity.class);
		implementedInterfaces = classNode.getImplementedInterfaces();
		assertEquals(1,implementedInterfaces.size());
		assertEquals(this.dw.getClass(Entity.class), implementedInterfaces.iterator().next());
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"implements2Interfaces.jar");
		classNode = this.dw.getClass("SingletonBadConstructor");
		implementedInterfaces = classNode.getImplementedInterfaces();
		assertEquals(2,implementedInterfaces.size());
	}
	
	public void testGetPackage() throws IOException, InexistentEntityException {
		
		ClassNode classNode = this.dw.getClass(Config.class);
		assertEquals("org.designwizard.common", classNode.getPackage().getName());
		
		classNode = this.dw.getClass(AbstractElementsFactory.class);
		assertEquals("org.designwizard.design.factory", classNode.getPackage().getName());
		
		classNode = this.dw.getClass(ClassNodeTest.class);
		assertEquals("tests.org.designwizard.design", classNode.getPackage().getName());
		
		ClassNode anotherClassNode = this.dw.getClass(FieldNodeTest.class);
		assertEquals(anotherClassNode.getPackage().getName(), classNode.getPackage().getName());
		
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"visibility.jar");
		classNode = this.dw.getClass("PackageClass");
		assertEquals("default",classNode.getPackage().getName());
	}
	
	public void testIsInterface() throws InexistentEntityException {
		ClassNode interfaceEntity = this.dw.getClass(Entity.class);
		assertTrue(interfaceEntity.isInterface());
		
		interfaceEntity = this.dw.getClass(DesignIF.class);
		assertTrue(interfaceEntity.isInterface());
		assertTrue(interfaceEntity.containsModifiers(Modifier.ABSTRACT));
		
		interfaceEntity = this.dw.getClass(ResultListener.class);
		assertTrue(interfaceEntity.isInterface());
		
		interfaceEntity = this.dw.getClass(AbstractElementsFactory.class);
		assertTrue(interfaceEntity.isInterface());
		
		ClassNode classEntity = this.dw.getClass(ClassNodeTest.class);
		assertFalse(classEntity.isInterface());
		
		classEntity = this.dw.getClass(AbstractEntity.class);
		assertFalse(classEntity.isInterface());
		assertTrue(interfaceEntity.containsModifiers(Modifier.ABSTRACT));
		
		classEntity = this.dw.getClass(FieldNode.class);
		assertFalse(classEntity.isInterface());
		assertFalse(classEntity.containsModifiers(Modifier.ABSTRACT));
		
		classEntity = this.dw.getClass(FieldNodeTest.class);
		assertFalse(classEntity.isInterface());
	}
	
	public void testGetCallers() throws InexistentEntityException {
		ClassNode classNode = this.dw.getClass(ClassNodeTest.class);
		Set<MethodNode> callers = classNode.getCallerMethods();
		assertTrue(callers.contains(this.dw.getMethod("tests.suites.AllTests.suite()")));
	}
	
	public void testInheritedFieldsAndMethods() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
		
		ClassNode classNode = this.dw.getClass(ClassNode.class);
		ClassNode abstractEntity = this.dw.getClass(AbstractEntity.class);
		
		Set<MethodNode> inheritedMethods = classNode.getInheritedMethods();
		for (MethodNode method: abstractEntity.getAllMethods()) {
			assertTrue(method.getName(),inheritedMethods.contains(method));
		}
		
		assertEquals(inheritedMethods.size(), abstractEntity.getAllMethods().size());
		
		Set<MethodNode> declaredMethods = classNode.getDeclaredMethods();
		MethodNode method = this.dw.getMethod("org.designwizard.design.ClassNode.<init>(java.lang.String)");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.addRelation(org.designwizard.design.relation.Relation)");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.addInheritedField(org.designwizard.design.FieldNode)");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getShortName()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getAllMethods()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getStaticMethods()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getCallerClasses()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getCalleeClasses()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getSuperClass()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getSubClasses()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getMethods(org.designwizard.design.Modifier[])");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getConstructors()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getDeclaredFields()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getImpactOfAChange()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.toString()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.printComponents(java.util.Set)");
		assertTrue(declaredMethods.contains(method));
		
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.equals(java.lang.Object)");
		assertTrue(declaredMethods.contains(method));
		
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getCallerMethods()");
		assertTrue(declaredMethods.contains(method));
		
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getInheritedFields()");
		assertTrue(declaredMethods.contains(method));
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getAllFields()");
		assertTrue(declaredMethods.contains(method));
		
		
		method = this.dw.getMethod("org.designwizard.design.ClassNode.getInheritedFields()");
		assertTrue(declaredMethods.contains(method));
	}
	
	public void testGetEntitiesThatImplements() throws IOException, InexistentEntityException, NotAnInterfaceException {
		ClassNode entity = dw.getClass(AbstractEntity.class);
		Set<ClassNode> implementsEntity = null;
		try {
			implementsEntity = entity.getEntitiesThatImplements();
			fail("This line can not be executed");
		} catch (NotAnInterfaceException e) {} 
		
		entity = dw.getClass(Entity.class);
		implementsEntity = entity.getEntitiesThatImplements();
		
		assertTrue(implementsEntity.contains(dw.getClass(AbstractEntity.class)));
		assertTrue(implementsEntity.contains(dw.getClass(FieldNode.class)));
		assertTrue(implementsEntity.contains(dw.getClass(ClassNode.class)));
		assertTrue(implementsEntity.contains(dw.getClass(MethodNode.class)));
		assertTrue(implementsEntity.contains(dw.getClass(PackageNode.class)));
		
		assertEquals(5,implementsEntity.size());
		
		entity = dw.getClass(DesignIF.class);
		implementsEntity = entity.getEntitiesThatImplements();
		
		assertTrue(implementsEntity.contains(dw.getClass(Design.class)));
		assertEquals(1,implementsEntity.size());
		
		dw = new DesignWizard("resources/testFiles/impactwithinheritance.jar");
		entity = dw.getClass("IVeiculo");
		implementsEntity = entity.getEntitiesThatImplements();
		
		assertTrue(implementsEntity.contains(dw.getClass("IMoto")));
		assertTrue(implementsEntity.contains(dw.getClass("ICarro")));
		assertEquals(2,implementsEntity.size());
		
		entity = dw.getClass("IMoto");
		implementsEntity = entity.getEntitiesThatImplements();
		assertTrue(implementsEntity.contains(dw.getClass("Moto")));
		assertEquals(1,implementsEntity.size());
		
		entity = dw.getClass("ICarro");
		implementsEntity = entity.getEntitiesThatImplements();
		assertTrue(implementsEntity.contains(dw.getClass("Carro")));
		assertEquals(1,implementsEntity.size());
	}
	
	public void testGetAllMethodsWithVisibilityAndStaticOption() throws IOException, InvalidTypeOfVisibility, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		// dont forget the default constructors.
		ClassNode classEntity = this.dw.getClass("AccessModifiers");
		Set<MethodNode> allMethods = classEntity.getMethods(Modifier.PUBLIC, Modifier.STATIC);
		assertEquals(allMethods.size(),1);
		
		classEntity = this.dw.getClass("MorePublic");
		allMethods = classEntity.getMethods(Modifier.PUBLIC, Modifier.STATIC);
		assertEquals(allMethods.size(),1);
	}
	
	public void testGetAllMethods() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		Set<ClassNode> allClasses = this.dw.getAllClasses();
		
		int total = 0;
		for (ClassNode clazz : allClasses) {
			total+=clazz.getAllMethods().size();
		}
		assertEquals(total,21);
	}
	
	public void testGetWithVisibilityAndStaticOption() throws IOException, InexistentEntityException, InvalidTypeOfVisibility{
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		Set<MethodNode> allMethods = this.dw.getClass("AccessModifiers").getAllMethods();
		assertEquals(allMethods.size(),9);
		
		Set<MethodNode> methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PUBLIC,Modifier.STATIC);
		assertEquals(methods.size(),1);
		
		methods = this.dw.getClass("MorePublic").getMethods(Modifier.PUBLIC, Modifier.STATIC);
		assertEquals(methods.size(),1);
		
		/**
		 * Methods public and static
		 */
		methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PUBLIC);
		int count = 0;
		for (MethodNode method: methods) {
			if (method.containsModifiers(Modifier.STATIC)) count+=1;
		}
		assertEquals(count,1);
		
		methods = this.dw.getClass("MorePublic").getMethods(Modifier.PUBLIC);
		count = 0;
		for (MethodNode method: methods) {
			if (method.containsModifiers(Modifier.STATIC)) count+=1;
		}
		assertEquals(count,1);
		
		
		/**
		 * Looking for methods that are just public
		 */
		methods = this.dw.getClass("MorePublic").getMethods(Modifier.PUBLIC);
		count = 0;
		for (MethodNode method: methods) {
			if (!method.containsModifiers(Modifier.STATIC)) count+=1;
		}
		assertEquals(count,1);
		
		methods = this.dw.getClass("MorePrivate").getMethods(Modifier.PUBLIC);
		assertEquals(methods.size(),1);
		
		methods = this.dw.getClass("MorePrivate").getMethods(Modifier.PUBLIC);
		assertEquals(methods.size(),1);
	}
	
	public void testGetWithVisibility() throws IOException, InexistentEntityException, InvalidTypeOfVisibility{
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		
		Set<MethodNode> allMethods = this.dw.getClass("AccessModifiers").getAllMethods();
		assertEquals(allMethods.size(),9);
		
		// remember that the classes without constructors have a default public constructor
		Set<MethodNode> methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PUBLIC);
		assertEquals(methods.size(),3);
		
		methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PRIVATE);
		assertEquals(methods.size(),2);
	
		methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PROTECTED);
		assertEquals(methods.size(),2);
	
		methods = this.dw.getClass("AccessModifiers").getMethods(Modifier.PACKAGE);
		assertEquals(methods.size(),2);
	}
	
	public void testGetAllMethodsInClass() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		Set<MethodNode> methods = this.dw.getClass("AccessModifiers").getAllMethods();
		assertEquals(9,methods.size());
		
		MethodNode method = this.dw.getMethod("AccessModifiers.pus(java.lang.String[])");
		assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pros()");
		assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pris()");
		assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.fris()");
		assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pub()");
		assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pri()");
		assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.pro()");
		assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.fri()");
		assertTrue(methods.contains(method));
		
		method = this.dw.getMethod("AccessModifiers.<init>()");
		assertTrue(methods.contains(method));
	}
	
	public void testGetSubClasses() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"hierarchy.jar");
		ClassNode clazz = this.dw.getClass("SuperClass");
		Set<ClassNode> subClasses = clazz.getSubClasses();
		assertEquals(subClasses.size(),2);
		assertTrue(subClasses.contains(this.dw.getClass("SubClass")));
		assertTrue(subClasses.contains(this.dw.getClass("ExtendsAndImplements")));
			
		subClasses = this.dw.getClass("Interface").getSubClasses();
		assertEquals(subClasses.size(),0);
	}
	
	public void testGetClass() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("classes");
		ClassNode classEntity = this.dw.getClass(DesignWizard.class);
		assertTrue(classEntity.containsModifiers(Modifier.PUBLIC));
		assertFalse(classEntity.containsModifiers(Modifier.STATIC));
		assertEquals(1,classEntity.getModifiers().size());
	}
	
	public void testGetSuperClasses() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"hierarchy.jar");
		ClassNode superClass = this.dw.getClass("SubClass").getSuperClass();
		assertEquals(superClass, this.dw.getClass("SuperClass"));
		superClass = this.dw.getClass("ExtendsAndImplements").getSuperClass();
		assertEquals(superClass, this.dw.getClass("SuperClass"));
	}
	
	/**
	 * Assests if the number of static methods on classes are not large. (This may be a bad sinal in Desing.)
	 * @throws InexistentEntityException 
	 * @throws IOException 
	 *
	 */
	public void testNumStaticMethods() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"accessmodifiers.jar");
		Set<MethodNode> staticMethods = this.dw.getClass("AccessModifiers").getStaticMethods();
		assertEquals(4,staticMethods.size());
	}	
	
	public void testGetClassesThatUses() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"edados1.jar");
	
		// ha apenas uma classe na IU
		Set<ClassNode> ius;
		MethodNode m = this.dw.getMethod("Inteiro.ehInteiro(char)");
		ius = m.getCallerClasses();
		assertTrue(ius.contains(this.dw.getClass("Polinomio")));
		assertEquals(1,ius.size());
		
		FieldNode f = this.dw.getField("java.lang.System.in");
		ius = f.getCallerClasses();
		assertTrue(ius.contains(this.dw.getClass("CalculadoraMain")));
		assertEquals(1,ius.size());
		
		f = this.dw.getField("Logica.mapaEntrada");
		ius = f.getCallerClasses();
		assertTrue(ius.contains(this.dw.getClass("Logica")));
		assertTrue(ius.contains(this.dw.getClass("Polinomio")));
		assertEquals(2,ius.size());
		
		f = this.dw.getField("Polinomio.map");
		ius = f.getCallerClasses();
		assertTrue(ius.contains(this.dw.getClass("Polinomio")));
		assertEquals(1, ius.size());
		
		f = this.dw.getField("java.lang.System.out");
		ius = f.getCallerClasses();
		assertEquals(1, ius.size());
		assertTrue(ius.contains(this.dw.getClass("CalculadoraMain")));
		
		f = this.dw.getField("pacote.outro.Teste.variavel");
		ius = f.getCallerClasses();
		assertEquals(1, ius.size());
		assertTrue(ius.contains(this.dw.getClass("pacote.ClasseDentroDoPacote")));
		
		ClassNode clazz = this.dw.getClass("pacote.outro.Teste");
		ius = clazz.getCallerClasses();
		assertEquals(1, ius.size());
		assertTrue(ius.contains(this.dw.getClass("pacote.ClasseDentroDoPacote")));
		
		clazz = this.dw.getClass("TratamentosComuns");
		ius = clazz.getCallerClasses();
		assertEquals(2, ius.size());
		assertTrue(ius.contains(this.dw.getClass("Logica")));
		assertTrue(ius.contains(this.dw.getClass("Polinomio")));
	}
	
	public void testGetClassesUsedBy() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"edados1.jar");
		
		ClassNode clazz = this.dw.getClass("CalculadoraMain");
		
		MethodNode m = this.dw.getMethod("CalculadoraMain.main(java.lang.String[])");
		Set<ClassNode> usedBy = m.getCalleeClasses();
		assertEquals(7, usedBy.size());
		assertTrue(usedBy.contains(this.dw.getClass("CalculadoraMain")));
		assertTrue(usedBy.contains(this.dw.getClass("Logica")));
		
		clazz = this.dw.getClass("CalculadoraMain");
		usedBy = clazz.getCalleeClasses();
		assertEquals(8, usedBy.size());
		assertTrue(usedBy.contains(this.dw.getClass("CalculadoraMain")));
		assertTrue(usedBy.contains(this.dw.getClass("Logica")));
		
		clazz = this.dw.getClass("CalculadoraPolinomial");
		usedBy = clazz.getCalleeClasses();
		assertTrue(usedBy.contains(this.dw.getClass("Polinomio")));
		assertEquals(7, usedBy.size());
		
		clazz = this.dw.getClass("Comando");
		usedBy = clazz.getCalleeClasses();
		assertTrue(usedBy.contains(this.dw.getClass("Comando")));
		assertEquals(5, usedBy.size());
		
		clazz = this.dw.getClass("IF");
		usedBy = clazz.getCalleeClasses();
		assertEquals(0,usedBy.size());
		
		clazz = this.dw.getClass("Inteiro");
		usedBy = clazz.getCalleeClasses();
		assertEquals(6,usedBy.size());
		
		clazz = this.dw.getClass("Logica");
		usedBy = clazz.getCalleeClasses();
		assertTrue(usedBy.contains(this.dw.getClass("TratamentosComuns")));
		assertTrue(usedBy.contains(this.dw.getClass("Polinomio")));
		assertTrue(usedBy.contains(this.dw.getClass("Logica")));
		assertTrue(usedBy.contains(this.dw.getClass("Comando")));
		assertTrue(usedBy.contains(this.dw.getClass("CalculadoraPolinomial")));
		assertEquals(23, usedBy.size());
		
		clazz = this.dw.getClass("Polinomio");
		usedBy = clazz.getCalleeClasses();
		assertTrue(usedBy.contains(this.dw.getClass("TratamentosComuns")));
		assertTrue(usedBy.contains(this.dw.getClass("Polinomio")));
		assertTrue(usedBy.contains(this.dw.getClass("Inteiro")));
		assertTrue(usedBy.contains(this.dw.getClass("Logica")));
		assertEquals(18, usedBy.size());
		
		
		clazz = this.dw.getClass("TratamentosComuns");
		usedBy = clazz.getCalleeClasses();
		assertFalse(usedBy.isEmpty());
		
		clazz = this.dw.getClass("pacote.outro.Teste");
		usedBy = clazz.getCalleeClasses();
		assertFalse(usedBy.isEmpty());

		clazz = this.dw.getClass("pacote.ClasseDentroDoPacote");
		usedBy = clazz.getCalleeClasses();
		
		assertTrue(usedBy.contains(this.dw.getClass("pacote.outro.Teste")));
		assertEquals(2,usedBy.size());
		assertFalse(usedBy.isEmpty());
	}
	
	public void testGetAllClasses() throws IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"edados1.jar");
		assertEquals(10,dw.getAllClasses().size());
	}
	
	
	public void testInnerClass() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"innerclass-relations.jar");
		ClassNode clazz = this.dw.getClass("Logica.Polinomio$Monomio");
		
		Set<ClassNode> innerusers = clazz.getCallerClasses();
		assertEquals(2,innerusers.size());
		assertTrue(innerusers.contains(this.dw.getClass("Logica.Polinomio$Monomio")));
		assertTrue(innerusers.contains(this.dw.getClass("Logica.Polinomio")));
	
		Set<ClassNode> innerUse = clazz.getCalleeClasses();
		assertEquals(9, innerUse.size());
		assertTrue(innerUse.contains(this.dw.getClass("Logica.Polinomio$Monomio")));
		
	}
	
	public void testGetConstructors() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"constructors.jar");
		ClassNode entity = this.dw.getClass("MultiplyConstructors");
		Set<MethodNode> constructors = entity.getConstructors();
		
		assertEquals(4,constructors.size());
		MethodNode method = this.dw.getMethod("MultiplyConstructors.<init>()");
		assertTrue(constructors.contains(method));
		
		method = this.dw.getMethod("MultiplyConstructors.<init>(double)");
		assertTrue(constructors.contains(method));
		
		method = this.dw.getMethod("MultiplyConstructors.<init>(int)");
		assertTrue(constructors.contains(method));
		
		method = this.dw.getMethod("MultiplyConstructors.<init>(float)");
		assertTrue(constructors.contains(method));
		
		
		/**
		 * Don not forget the default constructor.
		 */
		entity = this.dw.getClass("NoneConstructors");
		constructors = entity.getConstructors();
		assertEquals(1,constructors.size());
		method = this.dw.getMethod("NoneConstructors.<init>()");
		assertTrue(constructors.contains(method));
		
		entity = this.dw.getClass("PackageConstructor");
		constructors = entity.getConstructors();
		assertEquals(1,constructors.size());
		method = this.dw.getMethod("PackageConstructor.<init>()");
		assertTrue(constructors.contains(method));
		
		entity = this.dw.getClass("PrivateConstructor");
		constructors = entity.getConstructors();
		assertEquals(1,constructors.size());
		method = this.dw.getMethod("PrivateConstructor.<init>()");
		assertTrue(constructors.contains(method));
		
		entity = this.dw.getClass("PublicConstructor");
		constructors = entity.getConstructors();
		assertEquals(1,constructors.size());
		method = this.dw.getMethod("PublicConstructor.<init>(int)");
		assertTrue(constructors.contains(method));
	}
	
	public void testClassesWithRegularExpressions() throws InexistentEntityException {
		
		// Testa o retorno das classes que cont�m a string "api"
		Set<ClassNode> classesThatContainsApi = dw.getClasses(".*api.*");
		assertFalse(classesThatContainsApi.isEmpty());
		assertTrue(classesThatContainsApi.contains(dw.getClass("org.designwizard.api.DesignWizard")));
		assertTrue(classesThatContainsApi.contains(dw.getClass("org.designwizard.api.util.FileUtil")));
		assertTrue(classesThatContainsApi.size() == 2);
		
		// Testa o retorno das classes que terminam com a string "Wizard"
		Set<ClassNode> classesFinishWithApi = dw.getClasses(".*Wizard$");
		assertFalse(classesFinishWithApi.isEmpty());
		assertTrue(classesFinishWithApi.contains(dw.getClass("org.designwizard.api.DesignWizard")));
		assertTrue(classesFinishWithApi.size() == 1);
				
		// Testa o retorno das classes que iniciam com a string "api"
		Set<ClassNode> classesStartingWithApi = dw.getClasses("^api.*");
		assertTrue(classesStartingWithApi.isEmpty());
		
		// Testa o retorno das classes que cont�m a string "common" ou "factory"
		Set<ClassNode> classesThatContainsCommonOrFactory = dw.getClasses(".*common.*|.*factory.*");
		assertFalse(classesThatContainsCommonOrFactory.isEmpty());
		assertTrue(classesThatContainsCommonOrFactory.contains(dw.getClass("org.designwizard.design.factory.ElementsFactory")));
		assertTrue(classesThatContainsCommonOrFactory.contains(dw.getClass("org.designwizard.design.factory.AbstractElementsFactory")));
		assertTrue(classesThatContainsCommonOrFactory.contains(dw.getClass("org.designwizard.common.Config")));
		assertTrue(classesThatContainsCommonOrFactory.size() == 3);
	}
	
}