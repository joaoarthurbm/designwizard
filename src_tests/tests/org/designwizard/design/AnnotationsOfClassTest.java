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
            System.out.println("Annotations");
            System.out.println("Name: " + annotationNode.getName());
            // System.out.println("Annotations: " +
            // annotationNode.getClassesAnnotated());
            System.out.println("Is Annotation: " + annotationNode.isAnnotationClass());
        }
        
        Set<ClassNode> classes = dw.getAllClasses();

        for (ClassNode classNode : classes) {
            System.out.println(classNode.getClassName());
            System.out.println(classNode.getAnnotations());
        }
	}

	@Test
	public void testIsAnnotationClass() {
		ClassNode sigest, annotationA, annotationB;
		try {
			sigest = dw.getClass("br.ufrn.cerescaico.bsi.sigest.Sigest");
			annotationA = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationA");
			annotationB = dw.getClass("br.ufrn.cerescaico.bsi.sigest.annotation.AnnotationB");
			
			System.out.println("Classe >>>> Sigest");
	        System.out.println("Name: " + sigest.getName());
	        System.out.println("ClassName: " + sigest.getClassName());
	        System.out.println("All Annotations:");
	        
	        Set<ClassNode> annotations = sigest.getAnnotations();
	        for (ClassNode annotationNode : annotations) {
	            System.out.println("Annotations");
	            System.out.println("Name: " + annotationNode.getName());
	            System.out.println("Is Annotation: " + annotationNode.isAnnotationClass());
	        }

	        assertTrue("1", sigest.isClass());
	        assertFalse("2", sigest.isAnnotationClass());
	        assertTrue("3", annotationA.isAnnotationClass());
	        assertTrue("4", annotationB.isAnnotationClass());
		} catch (InexistentEntityException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetClassesByAnnotation() {
        /*Set<ClassNode> models = dw.getClassesByAnnotation("javax.persistence.Entity");

        for (ClassNode classNode : models) {
            System.out.println(classNode.getClassName());
            System.out.println(classNode.getAllAnnotations());
        }*/
	}
	
	//@Test
	public void testGetAnnotation() {
		/*ClassNode annotation = dw.getAnnotation("javax.persistence.Entity");
        System.out.println("Annotations >>>> ");
        System.out.println("Name: " + annotation.getName());
        System.out.println("ClassName: " + annotation.getClassName());
        System.out.println("is Annotation: " + annotation.isAnnotationClass());*/
	}
	
	//@Test
	public void testGetClassesAnnotated() {
		// TODO Implementar Relação reversa entre classes e suas anotações
		//ClassNode annotation = dw.getAnnotation("javax.persistence.Entity");
        //System.out.println("All Classes: " + annotation.getClassesAnnotated());
	}
}
