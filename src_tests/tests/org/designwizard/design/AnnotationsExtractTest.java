package tests.org.designwizard.design;

import java.util.HashSet;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Issue 39 - Absence of the annotation modifier in external class extraction
 * Link: https://github.com/joaoarthurbm/designwizard/issues/39
 *
 * The error occurs when an internal class (extraction project class) calls one of its methods
 * to an external class of the annotation type. The extraction of external classes is simplified
 * and only creates a corresponding ClassNode without assigning extra information
 * (for example, does not extract modifiers).
 *
 * As the extraction process extracted the external class in a method call, before it appears
 * as entry in a class, the ClassNode of the external class appears without annotation modifier.
 * This modifier will only be identified when the external class is used to annotate a project class.
 *
 * However, the extraction code only adds the modifier in new ClassNodes and did not update
 * the classNodes already extracted.
 *
 * The code below the method {@code Design#annotationExtracted(String)} is responsible
 * for the annotation extraction.
 *
 * @author Taciano Morais Silva <tacianosilva@gmail.com>
 */
public class AnnotationsExtractTest {

    private DesignWizard dw;

    @Before
    public void setUp() throws Exception {
        String arquivoJar = "classes/tests/org/designwizard/design/mocks/annotations/issue39/";
        dw = new DesignWizard(arquivoJar);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAllClasses() throws InexistentEntityException {
        // Internal Classes of the project
        Set<ClassNode> classes = dw.getAllClasses();

        Assert.assertEquals("total classes: ", 8, classes.size());
    }

    @Test
    public void testAllAnnotations() throws InexistentEntityException {
        //Total used annotation (internal e external)
        Set<ClassNode> annotations = dw.getAllAnnotations();

        Assert.assertEquals("total annotations: ", 7, annotations.size());
    }

    @Test
    public void testExtractAnnotationA() throws InexistentEntityException {
        //external annotation and unused
        ClassNode entityA = dw.getClass("tests.org.designwizard.design.mocks.annotations.external.AnnotationA");

        Assert.assertNotNull("AnnotationA is not null?", entityA);
        Assert.assertTrue("AnnotationA is annotation?", !entityA.isAnnotation());

        // Classes annotated by external annotationA
        Set<ClassNode> classesA = classesByAnnotation(dw,
                "tests.org.designwizard.design.mocks.annotations.external.AnnotationA");
        Assert.assertEquals("Num classes annotated by AnnotationB", 0, classesA.size());
    }

    @Test
    public void testExtractAnnotationB() throws InexistentEntityException {
        //external annotation and used
        ClassNode entityB = dw.getClass("tests.org.designwizard.design.mocks.annotations.external.AnnotationB");

        Assert.assertNotNull("AnnotationA is not null?", entityB);
        Assert.assertTrue("AnnotationA is annotation?", entityB.isAnnotation());

        // Classes annotated by external annotationB
        Set<ClassNode> classesB = classesByAnnotation(dw,
                "tests.org.designwizard.design.mocks.annotations.external.AnnotationB");
        Assert.assertEquals("Num classes annotated by AnnotationB", 1, classesB.size());
    }

    @Test(expected=InexistentEntityException.class)
    public void testExtractAnnotationC() throws InexistentEntityException {
        //external annotation, is not called and unused (it isn't extracted)
        dw.getClass("tests.org.designwizard.design.mocks.annotations.external.AnnotationC");
    }

    @Test
    public void testExtractAnnotationD() throws InexistentEntityException {
        //internal annotation and used
        ClassNode entityD = dw.getClass("tests.org.designwizard.design.mocks.annotations.issue39.AnnotationD");

        Assert.assertNotNull("AnnotationA is not null?", entityD);
        Assert.assertTrue("AnnotationA is annotation?", entityD.isAnnotation());

        // Classes annotated by internal annotationD
        Set<ClassNode> classesD = classesByAnnotation(dw,
                "tests.org.designwizard.design.mocks.annotations.issue39.AnnotationD");
        Assert.assertEquals("Num classes annotated by AnnotationD", 1, classesD.size());
    }

    @Test
    public void testExtractAnnotationE() throws InexistentEntityException {
        //internal annotation and unused
        ClassNode entityE = dw.getClass("tests.org.designwizard.design.mocks.annotations.issue39.AnnotationE");

        Assert.assertNotNull("AnnotationA is not null?", entityE);
        Assert.assertTrue("AnnotationA is annotation?", entityE.isAnnotation());

        // Classes annotated by internal annotationE
        Set<ClassNode> classesE = classesByAnnotation(dw,
                "tests.org.designwizard.design.mocks.annotations.issue39.AnnotationE");
        Assert.assertEquals("Num classes annotated by AnnotationE", 0, classesE.size());
    }

    @Test
    public void testExtractAnnotationF() throws InexistentEntityException {
        //internal annotation and used
        ClassNode entityF = dw.getClass("tests.org.designwizard.design.mocks.annotations.issue39.AnnotationF");

        Assert.assertNotNull("AnnotationA is not null?", entityF);
        Assert.assertTrue("AnnotationA is annotation?", entityF.isAnnotation());

        // Classes annotated by internal annotationF
        Set<ClassNode> classesF = classesByAnnotation(dw,
                "tests.org.designwizard.design.mocks.annotations.issue39.AnnotationF");
        Assert.assertEquals("Num classes annotated by AnnotationF", 1, classesF.size());
    }

    @Test
    public void testExtractAnnotationG() throws InexistentEntityException {
        //internal annotation and unused
        ClassNode entityG = dw.getClass("tests.org.designwizard.design.mocks.annotations.issue39.AnnotationG");

        Assert.assertNotNull("AnnotationA is not null?", entityG);
        Assert.assertTrue("AnnotationA is annotation?", entityG.isAnnotation());

        // Classes annotated by internal annotationG
        Set<ClassNode> classesG = classesByAnnotation(dw,
                "tests.org.designwizard.design.mocks.annotations.issue39.AnnotationG");
        Assert.assertEquals("Num classes annotated by AnnotationG", 0, classesG.size());
    }

    public static Set<ClassNode> classesByAnnotation(DesignWizard dw, String annotationName) throws InexistentEntityException {

        Set<ClassNode> allClasses = dw.getAllClasses();
        ClassNode annotationNode = dw.getAnnotation(annotationName);

        Set<ClassNode> classes = new HashSet<ClassNode>();

        for (ClassNode classNode : allClasses) {
            if (hasAnnotation(classNode, annotationNode)) {
                classes.add(classNode);
            }
        }
        return classes;
    }

    public static Boolean hasAnnotation(ClassNode aClass, ClassNode annotation) {
        Set<ClassNode> annotations = aClass.getAnnotations();

        if (annotations.contains(annotation)) {
                return true;
            }
        return false;

    }
}