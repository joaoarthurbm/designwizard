package tests.org.designwizard.design;

import static org.junit.Assert.*;

import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;
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
		Set<ClassNode> annotations = dw.getAllAnnotations();

        for (ClassNode annotationNode : annotations) {
            assertTrue("Annotation: " + annotationNode.getName(), annotationNode.isAnnotationClass());
        }
        assertEquals("1", 9, annotations.size());
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
	        assertFalse("2", sigest.isAnnotationClass());
	        assertTrue("3", annotationA.isAnnotationClass());
	        assertTrue("4", annotationB.isAnnotationClass());
	        assertTrue("5", entity.isAnnotationClass());
		} catch (InexistentEntityException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetClassesByAnnotation() {
        Set<ClassNode> classes = null;
		try {
			classes = dw.getClassesByAnnotation("javax.persistence.Entity");
			assertEquals("1", 11, classes.size());
			
			classes = dw.getClassesByAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
			assertEquals("2", 2, classes.size());
			
			classes = dw.getClassesByAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
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
	        assertTrue("5", annotationA.isAnnotationClass());
	        assertTrue("6", annotationB.isAnnotationClass());
	        assertTrue("7", entity.isAnnotationClass());
			
		} catch (InexistentEntityException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetClassesAnnotated() {
		ClassNode entity, annotationA, annotationB;
		Set<ClassNode> classesAnnotationA, classesAnnotationB, classesEntity;
		try {
			entity = dw.getAnnotation("javax.persistence.Entity");
			annotationA = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
			annotationB = dw.getAnnotation("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
			classesAnnotationA = annotationA.getClassesAnnotated();
			classesAnnotationB = annotationB.getClassesAnnotated();
			classesEntity = entity.getClassesAnnotated();
			
			assertNotNull("1", classesAnnotationA);
			assertEquals("2", 2, classesAnnotationA.size());
	        assertNotNull("3", classesAnnotationB);
	        assertEquals("4", 0, classesAnnotationB.size());
	        assertNotNull("5", classesEntity);
	        assertEquals("6", 11, classesEntity.size());
			
		} catch (InexistentEntityException e) {
			fail(e.getMessage());
		}
	}
}
