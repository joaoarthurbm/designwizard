package tests.org.designwizard.design;

import java.io.IOException;

import junit.framework.TestCase;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.AbstractEntity;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Entity;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;
import org.designwizard.design.factory.AbstractElementsFactory;
import org.designwizard.design.factory.ElementsFactory;
import org.designwizard.exception.InexistentEntityException;

public class AbstractEntityTest extends TestCase {
	
	private static final String DESIGNWIZARD_DIR = "classes";
	private DesignWizard dw;

	public void testIsAbstract() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
		
		AbstractEntity abstractClass = this.dw.getClass(AbstractEntity.class);
		assertTrue(abstractClass.isAbstract());
		
		abstractClass = this.dw.getClass(AbstractElementsFactory.class);
		assertTrue(abstractClass.isAbstract());
		
		abstractClass = this.dw.getClass(AbstractElementsFactory.class);
		assertTrue(abstractClass.isAbstract());
		
		AbstractEntity concreteClass = this.dw.getClass(ElementsFactory.class);
		assertFalse(concreteClass.isAbstract());
		
		AbstractEntity abstractMethod = this.dw.getMethod("org.designwizard.design.Entity.getName()");
		assertTrue(abstractMethod.isAbstract());
	}
	
	/**
	 * Todos os m�todos na interface s�o p�blicos e abstratos
	 * @throws InexistentEntityException
	 * @throws IOException 
	 */
	public void testInterface() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
		ClassNode interfaceEntity = this.dw.getClass(Entity.class);
		
		assertTrue(interfaceEntity.isAbstract());
		
		for (MethodNode method: interfaceEntity.getAllMethods()) {
			assertTrue(method.isAbstract());
			assertTrue(method.getVisibility().equals(Modifier.PUBLIC));
		}
	}
}
