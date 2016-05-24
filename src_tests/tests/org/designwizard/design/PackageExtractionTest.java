package tests.org.designwizard.design;

import java.io.IOException;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.MethodNode;
import org.designwizard.design.PackageNode;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PackageExtractionTest {
	
	private DesignWizard dw;
	
	@Before
	public void setUp() throws IOException {
		dw = new DesignWizard("classes");
	}
	
	@Test(expected=InexistentEntityException.class)
	public void testPackageExists() throws InexistentEntityException {
	
		PackageNode packageNode = dw.getPackage("org.designwizard.common");
		Assert.assertEquals("org.designwizard.common",packageNode.getName());
		
		packageNode = dw.getPackage("org.designwizard.comparator");
		
		packageNode = dw.getPackage("org.designwizard.design");
		Assert.assertEquals("org.designwizard.design",packageNode.getName());
		
		packageNode = dw.getPackage("org.designwizard.design.factory");
		Assert.assertEquals("org.designwizard.design.factory",packageNode.getName());
		
		packageNode = dw.getPackage("org.designwizard.design.manager");
		Assert.assertEquals("org.designwizard.design.manager",packageNode.getName());
		
		packageNode = dw.getPackage("org.designwizard.design.manager.util");
		Assert.assertEquals("org.designwizard.design.manager.util",packageNode.getName());
		
		packageNode = dw.getPackage("org.designwizard.design.factory");
		Assert.assertEquals("org.designwizard.design.factory",packageNode.getName());
	}

	@Test
	public void testPackagesWithRegularExpressions() throws InexistentEntityException {
		// Testa o retorno dos pacotes que contém a string "api"
		Set<PackageNode> packagesThatContainsApi = dw.getPackages(".*api.*");
		Assert.assertFalse(packagesThatContainsApi.isEmpty());
		Assert.assertTrue(packagesThatContainsApi.contains(dw.getPackage("org.designwizard.api")));
		Assert.assertTrue(packagesThatContainsApi.contains(dw.getPackage("org.designwizard.api.util")));
		Assert.assertTrue(packagesThatContainsApi.size() == 2);
		
		// Testa o retorno dos pacotes que terminam com a string "api"
		Set<PackageNode> packagesFinishWithApi = dw.getPackages(".*api$");
		Assert.assertFalse(packagesFinishWithApi.isEmpty());
		Assert.assertTrue(packagesFinishWithApi.contains(dw.getPackage("org.designwizard.api")));
		Assert.assertTrue(packagesFinishWithApi.size() == 1);
		
		// Testa o retorno dos pacotes que iniciam com a string "api"
		Set<PackageNode> packagesStartingWithApi = dw.getPackages("^api.*");
		Assert.assertTrue(packagesStartingWithApi.isEmpty());
		
		// Testa o retorno dos pacotes que contém a string "api" ou "util"
		Set<PackageNode> packagesThatContainsApiOrUtil = dw.getPackages(".*api.*|.*util.*");
		Assert.assertFalse(packagesThatContainsApiOrUtil.isEmpty());
		Assert.assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("org.designwizard.api")));
		Assert.assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("org.designwizard.api.util")));
		Assert.assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("org.designwizard.extractor.asm.util")));
		Assert.assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("tests.org.designwizard.design.manager.util")));
		Assert.assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("org.designwizard.design.manager.util")));
		Assert.assertTrue(packagesThatContainsApiOrUtil.size() == 5);
	}
	
	@Test
	public void testCallingMethods() throws InexistentEntityException {
		PackageNode packageNode = dw.getPackage("tests.org.designwizard.design");
		Assert.assertEquals("tests.org.designwizard.design",packageNode.getName());
		
		Set<MethodNode> callingMethods = packageNode.getCallerMethods();
		Assert.assertFalse(callingMethods.isEmpty());
		Assert.assertTrue(callingMethods.contains(dw.getMethod("tests.suites.AllTests.suite()")));
		
		packageNode = dw.getPackage("org.designwizard.api.util");
		callingMethods = packageNode.getCallerMethods();
		Assert.assertFalse(callingMethods.isEmpty());
	}
	
	//FIXME pensar neste caso! eu sou contra a maneira como estão sendo interpretado os pacotes.
	@Test(expected=InexistentEntityException.class)
	public void testContainsClass() throws InexistentEntityException {
		// packages that do not contain classes are not extracted (eu não concordo com isso :) )
		PackageNode packageNode = dw.getPackage("org.designwizard");
		
		packageNode = dw.getPackage("tests.org.designwizard.design");
		Assert.assertTrue(packageNode.getAllClasses().contains(this.dw.getClass(PackageExtractionTest.class)));
		
		packageNode = dw.getPackage("org.designwizard.design.relation");
		Assert.assertFalse(packageNode.getAllClasses().contains(this.dw.getClass(PackageExtractionTest.class)));
		
		packageNode = dw.getPackage("default");
	}
}