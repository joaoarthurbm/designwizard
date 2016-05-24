package tests.org.designwizard.design.relation;

import org.designwizard.design.AbstractEntity;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;
import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jarthur
 * This class tests all the classes that implements the Relation interface.
 */
public class RelationTest {
	
	private Relation r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12;
	private AbstractEntity c1,c2,c3,c4, m1,m2,m3,m4;
	
	
	@Before
	public void setUp() {
	
		this.c1 = new ClassNode("ContainRelation");
		c1.addModifier(Modifier.PUBLIC);
		
		this.c2 = new ClassNode("ExtendRelation");
		c2.addModifier(Modifier.PUBLIC);
		
		this.c3 = new ClassNode("InvokeRelation");
		this.c3.addModifier(Modifier.PROTECTED);
		
		this.c4 = new ClassNode("Method");
		this.c4.addModifier(Modifier.PRIVATE);
		
		boolean isConstructor = false;
		this.m1 = new MethodNode ("Method.add()",isConstructor);
		this.m1.addModifier(Modifier.PUBLIC);
		
		this.m2 = new MethodNode ("Method.remove()",isConstructor);
		this.m2.addModifier(Modifier.PUBLIC);
		
		this.m3 = new MethodNode ("Method.get()",isConstructor);
		this.m3.addModifier(Modifier.PUBLIC);
		
		this.m4 = new MethodNode ("Method.set()",isConstructor);
		this.m4.addModifier(Modifier.PRIVATE);
		
		r1 = new Relation(c1,c2,TypesOfRelation.EXTENDS); 
		r2 = new Relation(c2,c1,TypesOfRelation.EXTENDS);
		
		r3 = new Relation(c4,m2,TypesOfRelation.INVOKEVIRTUAL);
		r4 = new Relation(m2,c4,TypesOfRelation.INVOKEVIRTUAL);
		
		
		r5 = new Relation(c1,m3,TypesOfRelation.CONTAINS);
		r6 = new Relation(m3,c1,TypesOfRelation.CONTAINS);
		
		r7 = new Relation(c4,m2,TypesOfRelation.INVOKEVIRTUAL);
		r8 = new Relation(c4,m2,TypesOfRelation.INVOKEVIRTUAL);
		
		r9 = new Relation(m3,m4,TypesOfRelation.EXTENDS);
		r10 = new Relation(m3,m4,TypesOfRelation.EXTENDS);
		
		r11 = new Relation(c1,m1,TypesOfRelation.CONTAINS);
		r12 = new Relation(c1,m1,TypesOfRelation.CONTAINS);
	}
	
	@After
	public void tearDown() {
		this.r1 = null;
		this.r2 = null;
		this.r3 = null;
		this.r4 = null;
		
		this.c1 = null;
		this.c2 = null;
		this.c3 = null;
		this.c4 = null;
		
		this.m1 = null;
		this.m2 = null;
		this.m3= null;
		this.m4 = null;
		
	}
	
	@Test
	public void testEquals() {
		Assert.assertFalse(r1.equals(r2));
		Assert.assertFalse(r3.equals(r4));
		Assert.assertFalse(r5.equals(r6));
		Assert.assertTrue(r7.equals(r8));
		Assert.assertTrue(r9.equals(r10));
		Assert.assertTrue(r11.equals(r12));
	}
}
