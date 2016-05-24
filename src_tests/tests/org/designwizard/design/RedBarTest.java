package tests.org.designwizard.design;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is a test composition class. I made some tests that must be executed to show to programmers
 * that their source code have some undesirable feature in Design. 
 * @author João Arthur Brunet Monteiro - joaoarthur@gmail.com - Grupo de Métodos Formais (GMF)
 */
public class RedBarTest  {
	
	/**
	 * Attribute
	 */
	private DesignWizard dw;
	
	/**
	 * Creates a new DesignWizard.
	 * @throws IOException
	 */
	@Before
	public void setUp() throws IOException {
		this.dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"edados1.jar");
	}
	
	/**
	 * Tests structural features from software source code.
	 * @throws InexistentEntityException
	 */
	@Test
	public void testDesign() throws InexistentEntityException {
		 
		//all classes from code
		Set<ClassNode> classes = dw.getAllClasses();
		 
		//there is just one class that represents UI
		//identifying UI
		Set<ClassNode> uis;
		FieldNode f = this.dw.getField("java.lang.System.in");
		
		uis = f.getCallerClasses();
		f = this.dw.getField("java.lang.System.out");
		uis.addAll(f.getCallerClasses());
		
		Assert.assertEquals(1, uis.size());
		ClassNode ui = new LinkedList<ClassNode>(uis).getFirst();
		
        //source code is organized in UI + CONTROL + BUSINESS LOGIC
		
		//identifying CONTROL
		
		Set<ClassNode> control = ui.getCalleeClasses();
		control.remove(ui);
		
		//identifying LOGIC
		Set <ClassNode> logic = classes;
		logic.removeAll(control);
		logic.remove(ui);
		
		Assert.assertFalse(uis.isEmpty());
		Assert.assertFalse(control.isEmpty());
		Assert.assertFalse(logic.isEmpty());
		
		// there is no entity that knows or use UI.
		Set<ClassNode> useUI = ui.getCallerClasses();
		useUI.remove(ui);
		Assert.assertTrue(useUI.isEmpty());
	}
}
