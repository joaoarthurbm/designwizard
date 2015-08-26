package tests.org.designwizard.design;

import java.io.File;

import junit.framework.TestCase;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.Entity;
import org.designwizard.design.Modifier;
import org.designwizard.exception.InexistentEntityException;

public class EntityTest extends TestCase {
	
private DesignWizard dw;
	
	@Override
	protected void setUp() throws Exception {
		dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"visibility.jar");
	}
	
	public void testVisibilityOfClasses() throws InexistentEntityException{
		 
		Entity entity = this.dw.getClass("PackageClass");
		assertEquals(entity.getVisibility(), Modifier.PACKAGE);
	
		entity = this.dw.getClass("PublicClass");
		assertEquals(entity.getVisibility(), Modifier.PUBLIC);
		
		entity = this.dw.getClass("PublicClass$privateClass");
		assertEquals(entity.getVisibility(), Modifier.PRIVATE);
		
		entity = this.dw.getClass("PublicClass$PackageInternalClass");
		assertEquals(entity.getVisibility(), Modifier.PACKAGE);
		
		entity = this.dw.getClass("PackageClass$PrivateClass");
		assertEquals(entity.getVisibility(), Modifier.PRIVATE);
		
		entity = this.dw.getClass("PackageClass$PublicInternalClass");
		assertEquals(entity.getVisibility(), Modifier.PUBLIC);
		
	}
	
	public void testVisibilityOfMethods() throws InexistentEntityException{
		
		Entity method = this.dw.getMethod("PrivateMethods.getint()");
		assertEquals(method.getVisibility(), Modifier.PRIVATE);
		
		method = this.dw.getMethod("PrivateMethods.morePrivate()");
		assertEquals(method.getVisibility(), Modifier.PRIVATE);
		
		method = this.dw.getMethod("ProtectedMethods.X()");
		assertEquals(method.getVisibility(), Modifier.PROTECTED);
		
		method = this.dw.getMethod("ProtectedMethods.X1()");
		assertEquals(method.getVisibility(), Modifier.PROTECTED);

		method = this.dw.getMethod("PublicMethods.main(java.lang.String[])");
		assertEquals(method.getVisibility(), Modifier.PUBLIC);

		method = this.dw.getMethod("PublicMethods.returnInt()");
		assertEquals(method.getVisibility(), Modifier.PUBLIC);
	}
	
	public void testVisibilityOfFields() throws InexistentEntityException{
		
		Entity field = this.dw.getField("PrivateFields.ps");
		assertEquals(field.getVisibility(), Modifier.PRIVATE);
		
		field = this.dw.getField("PrivateFields.x");
		assertEquals(field.getVisibility(), Modifier.PRIVATE);
		
		field = this.dw.getField("ProtectedFields.x");
		assertEquals(field.getVisibility(), Modifier.PROTECTED);
		
		field = this.dw.getField("ProtectedFields.ps");
		assertEquals(field.getVisibility(), Modifier.PROTECTED);
		
		field = this.dw.getField("PublicFields.x");
		assertEquals(field.getVisibility(), Modifier.PUBLIC);
		
		field = this.dw.getField("PublicFields.ps");
		assertEquals(field.getVisibility(), Modifier.PUBLIC);
	}

}
