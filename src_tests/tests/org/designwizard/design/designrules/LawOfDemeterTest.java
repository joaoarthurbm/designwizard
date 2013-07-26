package tests.org.designwizard.design.designrules;

import java.io.IOException;
import java.util.Set;

import junit.framework.TestCase;

import org.designwizard.design.MethodNode;
import org.designwizard.exception.NotAnInterfaceException;
import org.designwizard.main.DesignWizard;

/*
 * Formally, the Law of Demeter for functions requires that a method M of an object O may only 
 * invoke the methods of the following kinds of objects:

 *  O itself
 *  M's parameters
 *  any objects created/instantiated within M
 *  O's direct component objects

 */
public class LawOfDemeterTest extends TestCase {
	
	public void testLawOfDemeter() throws IOException, NotAnInterfaceException {

		DesignWizard dw = new DesignWizard("classes");
		
		for (MethodNode method : dw.getAllMethods()) {

			Set<MethodNode> calledMethods = method.getCalleeMethods();
			
			for (MethodNode called : calledMethods) {

//				M's parameters
				System.out.println(method.getParameters().contains(called.getDeclaringClass()));
				
//				*  O itself
				System.out.println(method.getDeclaringClass().equals(called.getDeclaringClass()));
				System.out.println(method.getDeclaringClass().getSuperClass().equals(called.getDeclaringClass()));
				System.out.println(method.getDeclaringClass().getImplementedInterfaces().equals(called.getDeclaringClass()));
				
				if (!called.isConstructor()) {
					System.out.println(called.getName());
					Set<MethodNode> constructors = called.getDeclaringClass().getConstructors();
					for (MethodNode m : constructors) {
						System.out.println(calledMethods.contains(m));
					}
					
				}
				
			}
			
		}
		
		
	
	}

}
