package tests.org.designwizard.design;

import java.io.IOException;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.AbstractEntity;
import org.designwizard.design.ClassNode;
import org.designwizard.design.Entity;
import org.designwizard.design.MethodNode;
import org.designwizard.design.Modifier;
import org.designwizard.design.factory.AbstractElementsFactory;
import org.designwizard.design.factory.ElementsFactory;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Test;

public class AbstractEntityTest {
	
	private static final String DESIGNWIZARD_DIR = "classes";
	private DesignWizard dw;

	@Test
	public void testIsAbstract() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
		
		AbstractEntity abstractClass = this.dw.getClass(AbstractEntity.class);
		Assert.assertTrue(abstractClass.isAbstract());
		
		abstractClass = this.dw.getClass(AbstractElementsFactory.class);
		Assert.assertTrue(abstractClass.isAbstract());
		
		abstractClass = this.dw.getClass(AbstractElementsFactory.class);
		Assert.assertTrue(abstractClass.isAbstract());
		
		AbstractEntity concreteClass = this.dw.getClass(ElementsFactory.class);
		Assert.assertFalse(concreteClass.isAbstract());
		
		AbstractEntity abstractMethod = this.dw.getMethod("org.designwizard.design.Entity.getName()");
		Assert.assertTrue(abstractMethod.isAbstract());
	}
	
	/**
	 * Todos os métodos na interface são públicos e abstratos
	 * @throws InexistentEntityException
	 * @throws IOException 
	 */
	public void testInterface() throws InexistentEntityException, IOException {
		this.dw = new DesignWizard(DESIGNWIZARD_DIR);
		ClassNode interfaceEntity = this.dw.getClass(Entity.class);
		
		Assert.assertTrue(interfaceEntity.isAbstract());
		
		for (MethodNode method: interfaceEntity.getAllMethods()) {
			Assert.assertTrue(method.isAbstract());
			Assert.assertTrue(method.getVisibility().equals(Modifier.PUBLIC));
		}
	}
}
