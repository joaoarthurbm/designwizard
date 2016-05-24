package tests.org.designwizard.design;

import java.io.File;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.Entity;
import org.designwizard.design.Modifier;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EntityTest {
	
private DesignWizard dw;
	
	@Before
	public void setUp() throws Exception {
		dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"visibility.jar");
	}
	
	@Test
	public void testVisibilityOfClasses() throws InexistentEntityException{
		 
		Entity entity = this.dw.getClass("PackageClass");
		Assert.assertEquals(entity.getVisibility(), Modifier.PACKAGE);
	
		entity = this.dw.getClass("PublicClass");
		Assert.assertEquals(entity.getVisibility(), Modifier.PUBLIC);
		
		entity = this.dw.getClass("PublicClass$privateClass");
		Assert.assertEquals(entity.getVisibility(), Modifier.PRIVATE);
		
		entity = this.dw.getClass("PublicClass$PackageInternalClass");
		Assert.assertEquals(entity.getVisibility(), Modifier.PACKAGE);
		
		entity = this.dw.getClass("PackageClass$PrivateClass");
		Assert.assertEquals(entity.getVisibility(), Modifier.PRIVATE);
		
		entity = this.dw.getClass("PackageClass$PublicInternalClass");
		Assert.assertEquals(entity.getVisibility(), Modifier.PUBLIC);
		
	}
	
	@Test
	public void testVisibilityOfMethods() throws InexistentEntityException{
		
		Entity method = this.dw.getMethod("PrivateMethods.getint()");
		Assert.assertEquals(method.getVisibility(), Modifier.PRIVATE);
		
		method = this.dw.getMethod("PrivateMethods.morePrivate()");
		Assert.assertEquals(method.getVisibility(), Modifier.PRIVATE);
		
		method = this.dw.getMethod("ProtectedMethods.X()");
		Assert.assertEquals(method.getVisibility(), Modifier.PROTECTED);
		
		method = this.dw.getMethod("ProtectedMethods.X1()");
		Assert.assertEquals(method.getVisibility(), Modifier.PROTECTED);

		method = this.dw.getMethod("PublicMethods.main(java.lang.String[])");
		Assert.assertEquals(method.getVisibility(), Modifier.PUBLIC);

		method = this.dw.getMethod("PublicMethods.returnInt()");
		Assert.assertEquals(method.getVisibility(), Modifier.PUBLIC);
	}
	
	@Test
	public void testVisibilityOfFields() throws InexistentEntityException{
		
		Entity field = this.dw.getField("PrivateFields.ps");
		Assert.assertEquals(field.getVisibility(), Modifier.PRIVATE);
		
		field = this.dw.getField("PrivateFields.x");
		Assert.assertEquals(field.getVisibility(), Modifier.PRIVATE);
		
		field = this.dw.getField("ProtectedFields.x");
		Assert.assertEquals(field.getVisibility(), Modifier.PROTECTED);
		
		field = this.dw.getField("ProtectedFields.ps");
		Assert.assertEquals(field.getVisibility(), Modifier.PROTECTED);
		
		field = this.dw.getField("PublicFields.x");
		Assert.assertEquals(field.getVisibility(), Modifier.PUBLIC);
		
		field = this.dw.getField("PublicFields.ps");
		Assert.assertEquals(field.getVisibility(), Modifier.PUBLIC);
	}
}