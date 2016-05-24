package tests.org.designwizard.design;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Design;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.relation.Relation.TypesOfRelation;
import org.designwizard.exception.InexistentEntityException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

//FIXME not a unit test
public class InheritanceTest {

	private DesignWizard dw;

	@After
	public void tearDown() throws Exception {
		// TODO Auto-generated method stub
		dw = null;
	}

	@Test
	public void testInheritance() throws InexistentEntityException {
		Design design = new Design();
		design.addRelation(TypesOfRelation.EXTENDS, "SuperClass", "java.lang.Object");
		design.addRelation(TypesOfRelation.EXTENDS, "SubClass", "SuperClass");
		design.addRelation(TypesOfRelation.CONTAINS, "SuperClass" , "F:SuperClass.id");
		design.addRelation(TypesOfRelation.CONTAINS, "SubClass" , "M:SubClass.getID()");
		design.addRelation(TypesOfRelation.GETFIELD, "M:SubClass.getID()", "F:SubClass.id");
		design.addClassExtractedFromCode("SuperClass");
		design.addClassExtractedFromCode("SubClass");
		design.resolveDependences();

		ClassNode subClass = design.getClass("SubClass");
		Set<FieldNode> declaredFields = subClass.getDeclaredFields();
		Assert.assertTrue(declaredFields.isEmpty());
		Set<FieldNode> inheritedFields = subClass.getInheritedFields();
		Assert.assertEquals(1, inheritedFields.size());

		Set<FieldNode> inheritedField = design.getClass("SubClass").getInheritedFields();
		Assert.assertEquals("SuperClass.id", inheritedField.iterator().next().getName());

		MethodNode methodNode = design.getMethod("SubClass.getID()");

		Assert.assertEquals(methodNode.getAccessedFields().size(), 1);

		Assert.assertEquals("F:SuperClass.id", "F:"+methodNode.getAccessedFields().iterator().next().getName());
		Assert.assertFalse(design.getField("SuperClass.id").getCallerMethods().isEmpty());
	}

	@Test
	public void testDesignWizardInheritance() throws IOException, InexistentEntityException {
		dw = new DesignWizard("classes");

		MethodNode method = dw.getMethod("org.designwizard.design.ClassNode.<init>(java.lang.String)");
		Set<FieldNode> accessedFields = method.getAccessedFields();

		FieldNode field = dw.getField("org.designwizard.design.AbstractEntity.name");
		Assert.assertTrue(accessedFields.contains(field));

		field = dw.getField("org.designwizard.design.AbstractEntity.type");
		Assert.assertTrue(accessedFields.contains(field));

		field = dw.getField("org.designwizard.design.AbstractEntity.relations");
		Assert.assertTrue(accessedFields.contains(field));

		method = dw.getMethod("org.designwizard.design.AbstractEntity.getName()");
		accessedFields = method.getAccessedFields();
		field = dw.getField("org.designwizard.design.AbstractEntity.name");
		Assert.assertTrue(accessedFields.contains(field));

		method = dw.getMethod("org.designwizard.design.ClassNode.getShortName()");
		Set<MethodNode> methods = method.getCalleeMethods();
		Assert.assertFalse(methods.contains(dw.getMethod("org.designwizard.design.ClassNode.getName()")));
		Assert.assertTrue(methods.contains(dw.getMethod("org.designwizard.design.AbstractEntity.getName()")));
		Assert.assertTrue(methods.contains(dw.getMethod("org.designwizard.design.AbstractEntity.getShortName()")));
		Assert.assertTrue(methods.contains(dw.getMethod("org.designwizard.design.ClassNode.isInnerClass()")));
		Assert.assertEquals(6, methods.size());
	}

	@Test
	public void testAttributeRedefinedBySubClass() throws IOException, InexistentEntityException {

		/**
		 * Primeiro cenário:
		 *  O atributo é redefinido pela subclasse.
		 *  A subclasse acessa de 3 maneiras: 
		 *     id
		 *     this.id
		 *     super.id
		 */
		DesignWizard dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"attributeredefined.jar");

		ClassNode subClass = dw.getClass("SubClass");

		Assert.assertEquals(subClass.getDeclaredFields().size(),1);
		Assert.assertEquals(subClass.getInheritedFields().size(),3);

		MethodNode method = dw.getMethod("SubClass.<init>()");
		Set<FieldNode> accessedFields = method.getAccessedFields();
		Assert.assertEquals(4, accessedFields.size());

		FieldNode declaredField = dw.getField("SubClass.id");
		Assert.assertEquals(subClass.getDeclaredFields().iterator().next(),declaredField);

		ClassNode superClass = dw.getClass("SuperClass");
		Assert.assertEquals(superClass.getDeclaredFields().size(),1);
		Assert.assertEquals(superClass.getInheritedFields().size(),2);
	}

	@Test
	public void testSubClassExtendsSuperClassAttributeCallers() throws IOException, InexistentEntityException {
		/**
		 * 1
		 */
		dw = new DesignWizard("resources/testFiles/commands/removecommandsubsupercase1.jar");
		FieldNode field = dw.getField("Filha.A");
		Set<MethodNode> callers = field.getCallerMethods();
		MethodNode hasToBeThere = dw.getMethod("FUser.metodo()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		Assert.assertEquals(2,callers.size());

		hasToBeThere = dw.getMethod("FUser.metodo()");
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("FUserUser.metodo()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(1,callers.size());
	}

	@Test
	public void testSubClassExtendsSuperClassAttributeCallers2() throws IOException, InexistentEntityException {
		/**
		 * 2
		 */
		dw = new DesignWizard("resources/testFiles/commands/removecommandsubsupercase2.jar");
		FieldNode field = dw.getField("Pai.A");
		Set<MethodNode> callers = field.getCallerMethods();
		MethodNode hasToBeThere = dw.getMethod("FUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = dw.getMethod("FUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = dw.getMethod("FUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = dw.getMethod("PUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		Assert.assertEquals(5,callers.size());

		hasToBeThere = dw.getMethod("FUser.metodo1()");
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("FUserUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		Assert.assertEquals(1,callers.size());

		hasToBeThere = dw.getMethod("PUser.metodo1()");
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("PUserUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		Assert.assertEquals(1,callers.size());
	}

	@Test
	public void testSubClassExtendsSuperClassAttributeCallers3() throws IOException, InexistentEntityException {
		/**
		 * 3
		 */
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandsubsupercase3.jar");
		FieldNode field = dw.getField("Pai.A");
		Set<MethodNode> callers = field.getCallerMethods();
		MethodNode hasToBeThere = dw.getMethod("Pai.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("Pai.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
	}

	@Test
	public void testSubClassExtendsSuperClassAttributeCallers4() throws IOException, InexistentEntityException {
		/**
		 * 4.1
		 */
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandsubsupercase4.jar");

		FieldNode field = dw.getField("Pai.A");
		Set<MethodNode> callersFirstLevel = field.getCallerMethods();

		MethodNode hasToBeThere = dw.getMethod("Pai.<init>(java.lang.Object)");
		Assert.assertTrue(callersFirstLevel.contains(hasToBeThere));
		hasToBeThere = dw.getMethod("Pai.metodo1()");
		Assert.assertTrue(callersFirstLevel.contains(hasToBeThere));
		Assert.assertEquals(2, callersFirstLevel.size());

		Set<MethodNode> callersSecondLevel = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("Pai.metodo2()");
		Assert.assertTrue(callersSecondLevel.contains(hasToBeThere));
		
		hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callersSecondLevel.contains(hasToBeThere));
		
		Assert.assertEquals(2, callersSecondLevel.size());

		/**
		 * 4.2
		 */
		field = dw.getField("Filha.A");
		callersFirstLevel = field.getCallerMethods();
		hasToBeThere = dw.getMethod("Filha.<init>(java.lang.String)");
		Assert.assertTrue(callersFirstLevel.contains(hasToBeThere));

		callersSecondLevel = hasToBeThere.getCallerMethods();

		hasToBeThere = dw.getMethod("FUser.metodo2()");
		Assert.assertTrue(callersSecondLevel.contains(hasToBeThere));

		callersSecondLevel = hasToBeThere.getCallerMethods(); 
		hasToBeThere = dw.getMethod("FUserUser.metodo2()");
		Assert.assertTrue(callersSecondLevel.contains(hasToBeThere));
	}
	
	@Test
	public void testSubClassExtendsSuperClassAttributeCallers5() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandsubsupercase5.jar");
		
		MethodNode method = dw.getMethod("Pai.metodo3()");
		Set<MethodNode> callersFirstLevel = method.getCallerMethods();
		Assert.assertTrue(callersFirstLevel.contains(dw.getMethod("FUser.metodo4()")));
		Assert.assertEquals(1, callersFirstLevel.size());
	}
	
	@Test
	public void testSubClassExtendsSuperClassAttributeCallers6() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandsubsupercase6.jar");
		
		MethodNode method = dw.getMethod("Pai.metodo3()");
		Set<MethodNode> callersFirstLevel = method.getCallerMethods();
		
		Assert.assertTrue(callersFirstLevel.contains(dw.getMethod("FUser.metodo3()")));
		Assert.assertTrue(callersFirstLevel.contains(dw.getMethod("FUser.metodo4()")));
		Assert.assertTrue(callersFirstLevel.contains(dw.getMethod("FUser.metodo5()")));

		Assert.assertEquals(3, callersFirstLevel.size());
	}
	
	@Test
	public void testRemoveCommandSubAbs1() throws IOException, InexistentEntityException {
		/**
		 * 1
		 */
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandsubabscase1.jar");
		FieldNode field = dw.getField("Filha.A");
		Set<MethodNode> callers = field.getCallerMethods();
		MethodNode hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		hasToBeThere = dw.getMethod("FUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(2, callers.size());

		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("FUserUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(1, callers.size());
	}

	@Test
	public void testRemoveCommandSubAbs2() throws IOException, InexistentEntityException {
		/**
		 * 2
		 */
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandsubabscase2.jar");
		FieldNode field = dw.getField("Pai.A");
		Set<MethodNode> callers = field.getCallerMethods();
		MethodNode hasToBeThere = dw.getMethod("FUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		hasToBeThere = dw.getMethod("FUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		hasToBeThere = dw.getMethod("PUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		hasToBeThere = dw.getMethod("Filha.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(5, callers.size());
		
		callers = dw.getMethod("FUser.metodo1()").getCallerMethods();
		hasToBeThere = dw.getMethod("FUserUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(1, callers.size());
		
		callers = dw.getMethod("PUser.metodo1()").getCallerMethods();
		hasToBeThere = dw.getMethod("PUserUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(1, callers.size());
	}
	
	@Test
	public void testRemoveCommandSubAbs3() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandsubabscase3.jar");
		FieldNode field = dw.getField("Pai.A");
		Set<MethodNode> callers = field.getCallerMethods();
		MethodNode hasToBeThere = dw.getMethod("Pai.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(1,callers.size());
		
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("Pai.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(2,callers.size());
	}
	
	@Test
	public void testRemoveCommandSubAbs4() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandsubsabscase4.jar");
		
		/**
		 * 1.1
		 */
		FieldNode field = dw.getField("Pai.A");
		Set<MethodNode> callers = field.getCallerMethods();
		MethodNode hasToBeThere = dw.getMethod("Pai.<init>(java.lang.Object)");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		hasToBeThere = dw.getMethod("Pai.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(2,callers.size());
		
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("Pai.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(2,callers.size());
		
		/**
		 * 1.2
		 */
		field = dw.getField("Filha.A");
		callers = field.getCallerMethods();
		hasToBeThere = dw.getMethod("Filha.<init>(java.lang.String)");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(1,callers.size());
		
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = this.dw.getMethod("FUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = this.dw.getMethod("FUserUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));
	}
	
	@Test
	public void testRemoveCommandAbsAbs1() throws IOException, InexistentEntityException {
		/**
		 * 1.1
		 */
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandabsabscase1.jar");
		FieldNode field = dw.getField("Filha.A");
		Set<MethodNode> callers = field.getCallerMethods();
		MethodNode hasToBeThere = dw.getMethod("Filha.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = dw.getMethod("FUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		Assert.assertEquals(3,callers.size());
		
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("FUserUser.metodo1()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(1,callers.size());
		
		/**
		 * 1.2
		 */
		MethodNode method = this.dw.getMethod("Filha.metodo6()");
		callers = method.getCallerMethods();

		hasToBeThere = this.dw.getMethod("PUser.metodo3()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		hasToBeThere = this.dw.getMethod("FUser.metodo6User()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(2,callers.size());
		
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = this.dw.getMethod("FUserUser.metodo6UserUser()");
		
		Assert.assertEquals(1,callers.size());
	}
	
	@Test
	public void testRemoveCommandSubInt1() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard("resources/testFiles/commands/removecommandasubintcase1.jar");
		
		/**
		 * 1.1
		 */
		FieldNode field = dw.getField("Filha.A");
		Set<MethodNode> callers = field.getCallerMethods();

		MethodNode hasToBeThere = dw.getMethod("PUser.metodo2()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = dw.getMethod("FUser.metodo()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(2,callers.size());
		
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = dw.getMethod("FUserUser.metodo()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(1,callers.size());
		
		/**
		 * 1.2
		 */
		MethodNode method = dw.getMethod("Filha.metodo6()");
		callers = method.getCallerMethods();
		hasToBeThere = this.dw.getMethod("PUser.metodo5()");
		Assert.assertTrue(callers.contains(hasToBeThere));

		hasToBeThere = this.dw.getMethod("FUser.metodo6User()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(2,callers.size());
		
		callers = hasToBeThere.getCallerMethods();
		hasToBeThere = this.dw.getMethod("FUserUser.metodo6UserUser()");
		Assert.assertTrue(callers.contains(hasToBeThere));
		
		Assert.assertEquals(1,callers.size());
	}
}