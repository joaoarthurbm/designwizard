package tests.org.designwizard.design;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Entity;
import org.designwizard.exception.InexistentEntityException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnotationsOfClassTest {
	
	DesignWizard dw;
	
    @Before
    public void setUp() throws Exception {
    	String arquivoJar = "resources/sigest.jar";
        dw = new DesignWizard(arquivoJar);
    }

    @After
    public void tearDown() throws Exception {

    }

	@Test
	public void testGetAllAnnotations() throws InexistentEntityException {
		ClassNode sigest, annotationA, annotationB, entity;
		ClassNode id, oneToOne, oneToMany, column;
		Set<ClassNode> annotations = dw.getAllAnnotations();

		for (ClassNode annotationNode : annotations) {
			Assert.assertTrue("Annotation: " + annotationNode.getName(), annotationNode.isAnnotation());
		}
		Assert.assertFalse("1", annotations.isEmpty());

		sigest = dw.getClass("br.ufrn.cerescaico.bsi.sigest.Sigest");
		annotationA = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
		annotationB = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
		entity = dw.getClass("javax.persistence.Entity");

		// Anotações em Classes
		Assert.assertTrue("2", annotations.contains(annotationA));
		Assert.assertTrue("3", annotations.contains(annotationB));
		Assert.assertTrue("4", annotations.contains(entity));
		Assert.assertFalse("5", annotations.contains(sigest));

		// Criando o classNode que representa à anotação
		id = new ClassNode("javax.persistence.Id");

		// Buscando os classNodes que representam às anotações
		oneToOne = dw.getClass("javax.persistence.OneToOne");
		oneToMany = dw.getClass("javax.persistence.OneToMany");
		column = dw.getClass("javax.persistence.Column");

		// Anotações em Atributos
		Assert.assertTrue("6", annotations.contains(id));
		Assert.assertTrue("7", annotations.contains(oneToOne));
		Assert.assertTrue("8", annotations.contains(oneToMany));
		Assert.assertTrue("9", annotations.contains(column));
	}

	@Test
	public void testIsAnnotationClass() throws InexistentEntityException {
		ClassNode sigest, annotationA, annotationB, entity;
		sigest = dw.getClass("br.ufrn.cerescaico.bsi.sigest.Sigest");
		annotationA = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
		annotationB = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
		entity = dw.getClass("javax.persistence.Entity");

		Assert.assertTrue("1", sigest.isClass());
		Assert.assertFalse("2", sigest.isAnnotation());
		Assert.assertTrue("3", annotationA.isAnnotation());
		Assert.assertTrue("4", annotationB.isAnnotation());
		Assert.assertTrue("5", entity.isAnnotation());
	}
	
	@Test
	public void testGetClassesAnnotatedBy() throws InexistentEntityException {
        Set<Entity> classes = null;
		classes = dw.getEntitiesAnnotatedBy("javax.persistence.Entity");
		Assert.assertEquals("1", 11, classes.size());

		classes = dw.getEntitiesAnnotatedBy("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
		Assert.assertEquals("2", 2, classes.size());

		classes = dw.getEntitiesAnnotatedBy("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
		Assert.assertEquals("3", 0, classes.size());
	}
	
	@Test
	public void testGetAnnotation() throws InexistentEntityException {
		ClassNode entity, annotationA, annotationB, sigest;
		entity = dw.getAnnotation("javax.persistence.Entity");
		annotationA = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
		annotationB = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
		sigest = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.Sigest");

		Assert.assertNull("1", sigest);
		Assert.assertNotNull("2", entity);
		Assert.assertNotNull("3", annotationA);
		Assert.assertNotNull("4", annotationB);
		Assert.assertTrue("5", annotationA.isAnnotation());
		Assert.assertTrue("6", annotationB.isAnnotation());
		Assert.assertTrue("7", entity.isAnnotation());
	}
	
	@Test
	public void testGetAnnotatedClasses() throws InexistentEntityException {
		ClassNode entity, annotationA, annotationB, sigest;
		Set<Entity> classesAnnotationA, classesAnnotationB, classesEntity, classesSigest;
		entity = dw.getAnnotation("javax.persistence.Entity");
		annotationA = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
		annotationB = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
		sigest = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.Sigest");

		Assert.assertNull("0", sigest);

		classesAnnotationA = annotationA.getEntitiesAnnotatedBy();
		classesAnnotationB = annotationB.getEntitiesAnnotatedBy();
		classesEntity = entity.getEntitiesAnnotatedBy();

		sigest = dw.getClass("br.ufrn.cerescaico.bsi.sigest.Sigest");
		classesSigest = sigest.getEntitiesAnnotatedBy();

		Assert.assertNotNull("1", classesAnnotationA);
		Assert.assertEquals("2", 2, classesAnnotationA.size());
		Assert.assertNotNull("3", classesAnnotationB);
		Assert.assertEquals("4", 0, classesAnnotationB.size());
		Assert.assertNotNull("5", classesEntity);
		Assert.assertEquals("6", 11, classesEntity.size());
		Assert.assertNotNull("7", classesSigest);
		Assert.assertEquals("8", 0, classesSigest.size());
	}
}
