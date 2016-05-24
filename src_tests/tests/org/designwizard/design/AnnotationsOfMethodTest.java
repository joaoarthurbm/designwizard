package tests.org.designwizard.design;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnnotationsOfMethodTest {
	
	DesignWizard dw;
	
    @Before
    public void setUp() throws Exception {
    	String arquivoJar = "classes/tests/org/designwizard/design/mocks";
        dw = new DesignWizard(arquivoJar);
    }

    @After
    public void tearDown() throws Exception {

    }
    
    /**
     * Tests the extraction of annotation on methods and annotation 
     * on parameters of these methods.
     * @throws InexistentEntityException 
     */
    @Test
    public void testGetMethodsAnnotations() throws InexistentEntityException {
		ClassNode anexo;
		MethodNode methodNode;

		anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
		methodNode = anexo.getDeclaredMethod("getAnexo(java.lang.Long)");

		ClassNode parameterAnnotation = new ClassNode("javax.ws.rs.PathParam");
		ClassNode annotation = new ClassNode("javax.ws.rs.Path");

		Assert.assertTrue("1 - contains anotation", methodNode.getAnnotations().contains(annotation));
		Assert.assertTrue("3 - contains parameterAnnotation", methodNode.getAnnotations().contains(parameterAnnotation));
		Assert.assertEquals("2 - Size", 4, methodNode.getAnnotations().size());
    }
    
    /**
     * Tests the extraction of multiple annotations
     * on parameters of methods.
     * @throws InexistentEntityException 
     */
    @Test
    public void getMultipleParametersAnnotationTest() throws InexistentEntityException {
    	ClassNode anexo;
		MethodNode methodNode;
		
		anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
		methodNode = anexo.getDeclaredMethod("getAnexoMultipleParams(java.lang.Long,java.lang.Long)");

		ClassNode paramAnnotation1 = new ClassNode("javax.ws.rs.PathParam");
		ClassNode paramAnnotation2 = new ClassNode("javax.ws.rs.PathParam");
		Assert.assertTrue("2 - contains parameterAnnotation", methodNode.getAnnotations().contains(paramAnnotation1));
		Assert.assertTrue("3 - contains parameterAnnotation", methodNode.getAnnotations().contains(paramAnnotation2));
		Assert.assertEquals("4 - Size", 3, methodNode.getAnnotations().size());
    }
    
    /**
     * Tests the extraction of identical annotations presented on methods
     * and their parameters
     * @throws InexistentEntityException 
     */
    @Test
    public void equalParamAndMethodAnnoteTest() throws InexistentEntityException {
    	ClassNode anexo;
		MethodNode methodNode;
		
		anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
		methodNode = anexo.getDeclaredMethod("getCaso(java.lang.Long)");

		ClassNode annotation = new ClassNode("javax.ws.rs.Path");
		Assert.assertFalse("1 - contains anotation", methodNode.getAnnotations().contains(annotation));

		ClassNode paramAnnotation1 = new ClassNode("javax.ws.rs.PathParam");
		Assert.assertTrue("2 - contains parameterAnnotation", methodNode.getAnnotations().contains(paramAnnotation1));
		Assert.assertEquals("3 - Size", 2, methodNode.getAnnotations().size());
    }
    
    /**
     * Tests extraction of annotations in methods with no annotations
     * or parameters annotations
     * @throws InexistentEntityException 
     */
    @Test
    public void noneAnnotationTest() throws InexistentEntityException {
    	ClassNode anexo;
		MethodNode methodNode;
		
		anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
		methodNode = anexo.getDeclaredMethod("getNome(java.lang.String)");

		ClassNode annotation = new ClassNode("javax.ws.rs.Path");
		Assert.assertFalse("1 - contains anotation", methodNode.getAnnotations().contains(annotation));

		ClassNode paramAnnotation1 = new ClassNode("javax.ws.rs.PathParam");
		Assert.assertFalse("2 - contains parameterAnnotation", methodNode.getAnnotations().contains(paramAnnotation1));
		Assert.assertEquals("3 - Size", 0, methodNode.getAnnotations().size());
    }
    
    /**
     * Tests extraction of annotations in methods with no annotation on
     * parameters
     * @throws InexistentEntityException 
     */
    @Test
    public void annotationOnlyInMethodsTest() throws InexistentEntityException {
		ClassNode anexo;
		MethodNode methodNode;

		anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
		methodNode = anexo.getDeclaredMethod("setAnexo(java.lang.String)");

		ClassNode annotation1 = new ClassNode("javax.ws.rs.Path");
		Assert.assertTrue("1 - contains anotation", methodNode.getAnnotations().contains(annotation1));

		ClassNode annotation2 = new ClassNode("javax.ws.rs.POST");
		Assert.assertTrue("2 - contains parameterAnnotation", methodNode.getAnnotations().contains(annotation2));
		Assert.assertEquals("3 - Size", 3, methodNode.getAnnotations().size());
    }
}