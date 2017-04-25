package tests.org.designwizard.design;

import java.util.Set;

import junit.framework.TestCase;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO cadastrar nova issue
 * 
 * Test for Issue XX - Issue Name
 * Link: https://github.com/joaoarthurbm/designwizard/issues/XX
 *
 * @author Taciano Morais Silva <tacianosilva@gmail.com>
 */
public class GenericTypeExtractTest extends TestCase {

    private DesignWizard dw;
    private ClassNode genericTypeExamples;
    private ClassNode elementType;
    private ClassNode fieldNode;

    @Before
    public void setUp() throws Exception {
        String arquivoJar = "classes/tests/org/designwizard/design/mocks/generictypes/";
        dw = new DesignWizard(arquivoJar);
        genericTypeExamples = dw.getClass("tests.org.designwizard.design.mocks.generictypes.GenericTypesExamples");
        elementType = new ClassNode("tests.org.designwizard.design.mocks.generictypes.ElementType");
        fieldNode = new ClassNode("org.designwizard.design.FieldNode");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAllClasses() throws InexistentEntityException {
        // Internal Classes of the project
        Set<ClassNode> classes = dw.getAllClasses();
 
        Set<FieldNode> fields = genericTypeExamples.getAllFields();
        Set<MethodNode> methods = genericTypeExamples.getAllMethods();

        assertNotNull("GenericTypeExamples class?", genericTypeExamples);
        assertNotNull("ElementType class?", dw.getClass("tests.org.designwizard.design.mocks.generictypes.ElementType"));
        
        assertTrue("Contains GenericTypeExamples class?", classes.contains(genericTypeExamples));
        assertTrue("Contains ElementType class?", classes.contains(elementType));
        
        assertEquals("total classes: ", 2, classes.size());
        assertEquals("total fields: ", 1, fields.size());

        // include GenericTypeExamples.<init>() - Constructor
        assertEquals("total methods: ", 6, methods.size());
    }

    
    @Test
    public void testGetElements() throws InexistentEntityException {
    	assertNotNull("GenericTypeExamples class?", genericTypeExamples);
    	
    	MethodNode getElements = genericTypeExamples.getDeclaredMethod("getElements()");
    	Set<ClassNode> callees = getElements.getCalleeClasses();
    	
    	assertTrue("Contains ElementType class?", callees.contains(elementType));
    }
    
    @Test
    public void testSetElements() throws InexistentEntityException {
    	assertNotNull("GenericTypeExamples class?", genericTypeExamples);
    	
    	MethodNode setElements = genericTypeExamples.getDeclaredMethod("setElements(java.util.Set)");
    	Set<ClassNode> callees = setElements.getCalleeClasses();
        
    	assertTrue("Contains ElementType class?", callees.contains(elementType));
    }
    
    @Test
    public void testExtractionFieldNode() throws InexistentEntityException {

    	MethodNode getAccessedFields = genericTypeExamples.getDeclaredMethod("getAccessedFields()");
    	Set<ClassNode> callees = getAccessedFields.getCalleeClasses();
        
        assertTrue("Contains FieldNode?", callees.contains(fieldNode));
    }
    
    @Test
    public void testExtractionElementType() throws InexistentEntityException {

    	MethodNode extractElementType = genericTypeExamples.getDeclaredMethod("extractElementType()");
    	Set<ClassNode> callees = extractElementType.getCalleeClasses();
        
    	assertTrue("Contains ElementType class?", callees.contains(elementType));
    }
    
    @Test
    public void testExtractFieldNode() throws InexistentEntityException {

    	MethodNode extractFieldNode = genericTypeExamples.getDeclaredMethod("extractFieldNode()");
    	Set<ClassNode> callees = extractFieldNode.getCalleeClasses();
        
    	assertTrue("Contains FieldNode?", callees.contains(fieldNode));
    }
}