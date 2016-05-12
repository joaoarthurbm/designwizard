package tests.org.designwizard.design;

import static org.junit.Assert.*;

import java.util.Set;

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
public class LocalVariablesExtractionTest {

	private DesignWizard dw;
    private ClassNode localVariablesExample;
    private ClassNode localVariable;

    @Before
    public void setUp() throws Exception {
        String arquivoJar = "classes/tests/org/designwizard/design/mocks/localvariables/";
        dw = new DesignWizard(arquivoJar);
        localVariablesExample = dw.getClass("tests.org.designwizard.design.mocks.localvariables.LocalVariablesExample");
        localVariable = new ClassNode("tests.org.designwizard.design.mocks.localvariables.LocalVariable");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAllClasses() throws InexistentEntityException {
        // Internal Classes of the project
        Set<ClassNode> classes = dw.getAllClasses();
 
        Set<FieldNode> fields = localVariablesExample.getAllFields();
        Set<MethodNode> methods = localVariablesExample.getAllMethods();
        for (MethodNode method : methods) {
			System.out.println("Method: " + method.getName());
		}
        
        assertNotNull("LocalVariable", localVariable);
        assertNotNull("LocalVariablesExample", localVariablesExample);

        assertTrue("Contains LocalVariable class?", classes.contains(localVariable));
        assertTrue("Contains LocalVariablesExamples class?", classes.contains(localVariablesExample));
        
        assertEquals("total classes: ", 2, classes.size());
        assertEquals("total fields: ", 0, fields.size());
        // include LocalVariablesExample.<init>() - Constructor
        assertEquals("total methods: ", 7, methods.size());
    }

    
    @Test
    public void testPrintLocalVariable() throws InexistentEntityException {
    	assertNotNull("LocalVariablesExample", localVariablesExample);
    	
    	MethodNode printLocalVariableMethod = localVariablesExample.getDeclaredMethod("printLocalVariable()");
    	Set<ClassNode> callees = printLocalVariableMethod.getCalleeClasses();
    	for (ClassNode classNode : callees) {
			System.out.println("Not Instantiated Test Callee: " + classNode.getName());
		}
        
    	assertTrue("Contains LocalVariable class?", callees.contains(localVariable));
    }
    
    @Test
    public void testCastLocalVariable() throws InexistentEntityException {
    	assertNotNull("LocalVariablesExample", localVariablesExample);
    	
    	MethodNode castLocalVariableMethod = localVariablesExample.getDeclaredMethod("castLocalVariable()");
    	Set<ClassNode> callees = castLocalVariableMethod.getCalleeClasses();
    	for (ClassNode classNode : callees) {
			System.out.println("Cast Test Callee: " + classNode.getName());
		}
        
    	assertTrue("Contains LocalVariable class?", callees.contains(localVariable));
    }
    
    @Test
    public void testAssignmentLocalVariable() throws InexistentEntityException {
    	assertNotNull("LocalVariablesExample", localVariablesExample);
    	
    	MethodNode assignmentLocalVariableMethod = localVariablesExample.getDeclaredMethod("assignmentLocalVariable()");
    	Set<ClassNode> callees = assignmentLocalVariableMethod.getCalleeClasses();
    	for (ClassNode classNode : callees) {
			System.out.println("Assignment Test Callee: " + classNode.getName());
		}
        
    	assertTrue("Contains LocalVariable class?", callees.contains(localVariable));
    }
    
    @Test
    public void testPrintLocalVariableInstantiated() throws InexistentEntityException {
    	assertNotNull("LocalVariablesExample", localVariablesExample);
    	
    	MethodNode printLocalVariableInstatiatedMethod = localVariablesExample.getDeclaredMethod("printLocalVariableInstantiated()");
    	Set<ClassNode> callees = printLocalVariableInstatiatedMethod.getCalleeClasses();
    	for (ClassNode classNode : callees) {
			System.out.println("Instantiated Test Callee: " + classNode.getName());
		}

    	assertTrue("Contains LocalVariable class?", callees.contains(localVariable));
    }
}
