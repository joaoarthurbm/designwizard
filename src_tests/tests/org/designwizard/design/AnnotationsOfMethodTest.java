package tests.org.designwizard.design;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.AbstractEntity;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
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

}
