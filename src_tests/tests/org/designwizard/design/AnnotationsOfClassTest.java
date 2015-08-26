package tests.org.designwizard.design;

import static org.junit.Assert.*;

import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.api.DesignWizard;
import org.junit.After;
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
	public void testGetAllAnnotations() {
		ClassNode sigest, annotationA, annotationB, entity;
		ClassNode id, oneToOne, oneToMany, column;
		Set<ClassNode> annotations = dw.getAllAnnotations();

        for (ClassNode annotationNode : annotations) {
            assertTrue("Annotation: " + annotationNode.getName(), annotationNode.isAnnotation());
        }
        assertFalse("1", annotations.isEmpty());
        
        try {
        	sigest = dw.getClass("br.ufrn.cerescaico.bsi.sigest.Sigest");
        	annotationA = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
        	annotationB = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
        	entity = dw.getClass("javax.persistence.Entity");
        	
        	// Anotações em Classes
        	assertTrue("2", annotations.contains(annotationA));
        	assertTrue("3", annotations.contains(annotationB));
        	assertTrue("4", annotations.contains(entity));
        	assertFalse("5", annotations.contains(sigest));
        	
        	// Criando o classNode que representa à anotação
        	id = new ClassNode("javax.persistence.Id");
        	
        	// Buscando os classNodes que representam às anotações
        	oneToOne = dw.getClass("javax.persistence.OneToOne");
        	oneToMany = dw.getClass("javax.persistence.OneToMany");
        	column = dw.getClass("javax.persistence.Column");
        	
        	// Anotações em Atributos
        	assertTrue("6", annotations.contains(id));
        	assertTrue("7", annotations.contains(oneToOne));
        	assertTrue("8", annotations.contains(oneToMany));
        	assertTrue("9", annotations.contains(column));
        	
        } catch (InexistentEntityException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testIsAnnotationClass() {
		ClassNode sigest, annotationA, annotationB, entity;
		try {
			sigest = dw.getClass("br.ufrn.cerescaico.bsi.sigest.Sigest");
			annotationA = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
			annotationB = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
			entity = dw.getClass("javax.persistence.Entity");

	        assertTrue("1", sigest.isClass());
	        assertFalse("2", sigest.isAnnotation());
	        assertTrue("3", annotationA.isAnnotation());
	        assertTrue("4", annotationB.isAnnotation());
	        assertTrue("5", entity.isAnnotation());
		} catch (InexistentEntityException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetClassesAnnotatedBy() {
        Set<ClassNode> classes = null;
		try {
			classes = dw.getClassesAnnotatedBy("javax.persistence.Entity");
			assertEquals("1", 11, classes.size());
			
			classes = dw.getClassesAnnotatedBy("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
			assertEquals("2", 2, classes.size());
			
			classes = dw.getClassesAnnotatedBy("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
			assertEquals("3", 0, classes.size());
		} catch (InexistentEntityException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetAnnotation() {
		ClassNode entity, annotationA, annotationB, sigest;
		try {
			entity = dw.getAnnotation("javax.persistence.Entity");
			annotationA = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
			annotationB = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
			sigest = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.Sigest");
			
			
			assertNull("1", sigest);
			assertNotNull("2", entity);
			assertNotNull("3", annotationA);
			assertNotNull("4", annotationB);
	        assertTrue("5", annotationA.isAnnotation());
	        assertTrue("6", annotationB.isAnnotation());
	        assertTrue("7", entity.isAnnotation());
			
		} catch (InexistentEntityException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetAnnotatedClasses() {
		ClassNode entity, annotationA, annotationB, sigest;
		Set<ClassNode> classesAnnotationA, classesAnnotationB, classesEntity, classesSigest;
		try {
			entity = dw.getAnnotation("javax.persistence.Entity");
			annotationA = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
			annotationB = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
			sigest = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.Sigest");
			
			assertNull("0", sigest);
			
			classesAnnotationA = annotationA.getAnnotatedClasses();
			classesAnnotationB = annotationB.getAnnotatedClasses();
			classesEntity = entity.getAnnotatedClasses();
			
			sigest = dw.getClass("br.ufrn.cerescaico.bsi.sigest.Sigest");
			classesSigest = sigest.getAnnotatedClasses();
			
			assertNotNull("1", classesAnnotationA);
			assertEquals("2", 2, classesAnnotationA.size());
	        assertNotNull("3", classesAnnotationB);
	        assertEquals("4", 0, classesAnnotationB.size());
	        assertNotNull("5", classesEntity);
	        assertEquals("6", 11, classesEntity.size());
	        assertNotNull("7", classesSigest);
	        assertEquals("8", 0, classesSigest.size());
			
		} catch (InexistentEntityException e) {
			fail(e.getMessage());
		}
	}
}
