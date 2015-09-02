package tests.org.designwizard.design;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.After;
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
     */
    @Test
    public void testGetMethodsAnnotations() {
    	ClassNode anexo;
		MethodNode methodNode;
		
		try{
			anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
			methodNode = anexo.getDeclaredMethod("getAnexo(java.lang.Long)");
			
			ClassNode parameterAnnotation = new ClassNode("javax.ws.rs.PathParam");
			ClassNode annotation = new ClassNode("javax.ws.rs.Path");

			assertTrue("1 - contains anotation", methodNode.getAnnotations().contains(annotation));
			assertTrue("3 - contains parameterAnnotation", methodNode.getAnnotations().contains(parameterAnnotation));
			assertEquals("2 - Size", 4, methodNode.getAnnotations().size());
			
		}catch(InexistentEntityException e){
			fail(e.getMessage());
		}
    }
    
    /**
     * Tests the extraction of multiple annotations
     * on parameters of methods.
     */
    @Test
    public void getMultipleParametersAnnotationTest() {
    	ClassNode anexo;
		MethodNode methodNode;
		
		try{
			anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
			methodNode = anexo.getDeclaredMethod("getAnexoMultipleParams(java.lang.Long,java.lang.Long)");
			
			ClassNode paramAnnotation1 = new ClassNode("javax.ws.rs.PathParam");
			ClassNode paramAnnotation2 = new ClassNode("javax.ws.rs.PathParam");
			assertTrue("2 - contains parameterAnnotation", methodNode.getAnnotations().contains(paramAnnotation1));
			assertTrue("3 - contains parameterAnnotation", methodNode.getAnnotations().contains(paramAnnotation2));
			assertEquals("4 - Size", 3, methodNode.getAnnotations().size());
			
		}catch(InexistentEntityException e){
			fail(e.getMessage());
		}
    }
    
    
    /**
     * Tests the extraction of identical annotations presented on methods
     * and their parameters
     */
    @Test
    public void equalParamAndMethodAnnoteTest() {
    	ClassNode anexo;
		MethodNode methodNode;
		
		try{
			anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
			methodNode = anexo.getDeclaredMethod("getCaso(java.lang.Long)");
			
			ClassNode annotation = new ClassNode("javax.ws.rs.Path");
			assertFalse("1 - contains anotation", methodNode.getAnnotations().contains(annotation));

			ClassNode paramAnnotation1 = new ClassNode("javax.ws.rs.PathParam");
			assertTrue("2 - contains parameterAnnotation", methodNode.getAnnotations().contains(paramAnnotation1));
			assertEquals("3 - Size", 2, methodNode.getAnnotations().size());
			
		}catch(InexistentEntityException e){
			fail(e.getMessage());
		}
    }
    
    /**
     * Tests extraction of annotations in methods with no annotations
     * or parameters annotations
     */
    @Test
    public void noneAnnotationTest() {
    	ClassNode anexo;
		MethodNode methodNode;
		
		try{
			anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
			methodNode = anexo.getDeclaredMethod("getNome(java.lang.String)");
			
			ClassNode annotation = new ClassNode("javax.ws.rs.Path");
			assertFalse("1 - contains anotation", methodNode.getAnnotations().contains(annotation));

			ClassNode paramAnnotation1 = new ClassNode("javax.ws.rs.PathParam");
			assertFalse("2 - contains parameterAnnotation", methodNode.getAnnotations().contains(paramAnnotation1));
			assertEquals("3 - Size", 0, methodNode.getAnnotations().size());
			
		}catch(InexistentEntityException e){
			fail(e.getMessage());
		}
    }
    
    /**
     * Tests extraction of annotations in methods with no annotation on
     * parameters
     */
    @Test
    public void annotationOnlyInMethodsTest() {
    	ClassNode anexo;
		MethodNode methodNode;
		
		try{
			anexo = dw.getClass("tests.org.designwizard.design.mocks.Anexo");
			methodNode = anexo.getDeclaredMethod("setAnexo(java.lang.String)");
			
			ClassNode annotation1 = new ClassNode("javax.ws.rs.Path");
			assertTrue("1 - contains anotation", methodNode.getAnnotations().contains(annotation1));

			ClassNode annotation2 = new ClassNode("javax.ws.rs.POST");
			assertTrue("2 - contains parameterAnnotation", methodNode.getAnnotations().contains(annotation2));
			assertEquals("3 - Size", 3, methodNode.getAnnotations().size());
			
		}catch(InexistentEntityException e){
			fail(e.getMessage());
		}
    }

}
