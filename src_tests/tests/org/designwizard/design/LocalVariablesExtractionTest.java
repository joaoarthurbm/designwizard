package tests.org.designwizard.design;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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

	private static DesignWizard dw;
    private ClassNode localVariablesExamples;
    private ClassNode localVariable;
    
    @BeforeClass
	public static void setUpBeforeClass() throws Exception {
    	String arquivoJar = "classes/tests/org/designwizard/design/mocks/localvariables/";
        dw = new DesignWizard(arquivoJar);
	}

    @Before
    public void setUp() throws Exception {
        localVariablesExamples = dw.getClass("tests.org.designwizard.design.mocks.localvariables.LocalVariablesExamples");
        localVariable = new ClassNode("tests.org.designwizard.design.mocks.localvariables.LocalVariable");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAllClasses() throws InexistentEntityException {
        // Internal Classes of the project
        Set<ClassNode> classes = dw.getAllClasses();
 
        Set<FieldNode> fields = localVariablesExamples.getAllFields();
        Set<MethodNode> methods = localVariablesExamples.getAllMethods();
        
        assertNotNull("LocalVariable", localVariable);
        assertNotNull("LocalVariablesExamples", localVariablesExamples);

        assertTrue("Contains LocalVariable class?", classes.contains(localVariable));
        assertTrue("Contains LocalVariablesExamples class?", classes.contains(localVariablesExamples));
        
        assertEquals("total classes: ", 2, classes.size());
        assertEquals("total fields: ", 0, fields.size());
        // include LocalVariablesExamples.<init>() - Constructor
        assertEquals("total methods: ", 7, methods.size());
    }

    
    @Test
    public void testPrintLocalVariable() throws InexistentEntityException {
    	assertNotNull("LocalVariablesExamples", localVariablesExamples);
    	
    	MethodNode printLocalVariableMethod = localVariablesExamples.getDeclaredMethod("printLocalVariable()");
    	Set<ClassNode> callees = printLocalVariableMethod.getCalleeClasses();
    	        
    	assertTrue("Contains LocalVariable class?", callees.contains(localVariable));
    }
    
    @Test
    public void testCastLocalVariable() throws InexistentEntityException {
    	assertNotNull("LocalVariablesExamples", localVariablesExamples);
    	
    	MethodNode castLocalVariableMethod = localVariablesExamples.getDeclaredMethod("castLocalVariable()");
    	Set<ClassNode> callees = castLocalVariableMethod.getCalleeClasses();
    	        
    	assertTrue("Contains LocalVariable class?", callees.contains(localVariable));
    }
    
    @Test
    public void testAssignmentLocalVariable() throws InexistentEntityException {
    	assertNotNull("LocalVariablesExamples", localVariablesExamples);
    	
    	MethodNode assignmentLocalVariableMethod = localVariablesExamples.getDeclaredMethod("assignmentLocalVariable()");
    	Set<ClassNode> callees = assignmentLocalVariableMethod.getCalleeClasses();
    	        
    	assertTrue("Contains LocalVariable class?", callees.contains(localVariable));
    }
    
    @Test
    public void testPrintLocalVariableInstantiated() throws InexistentEntityException {
    	assertNotNull("LocalVariablesExamples", localVariablesExamples);
    	
    	MethodNode printLocalVariableInstatiatedMethod = localVariablesExamples.getDeclaredMethod("printLocalVariableInstantiated()");
    	Set<ClassNode> callees = printLocalVariableInstatiatedMethod.getCalleeClasses();
    	
    	assertTrue("Contains LocalVariable class?", callees.contains(localVariable));
    }
}
