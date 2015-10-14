package tests.org.designwizard.design;

import java.io.IOException;
import java.util.Set;

import junit.framework.TestCase;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.MethodNode;
import org.designwizard.design.PackageNode;
import org.designwizard.exception.InexistentEntityException;

public class PackageExtractionTest extends TestCase {
	
	private DesignWizard dw;
	
	@Override
	protected void setUp() throws IOException {
	
		dw = new DesignWizard("classes");
	
	}
	
	public void testPackageExists() throws InexistentEntityException {
	
		PackageNode packageNode = dw.getPackage("org.designwizard.common");
		assertEquals("org.designwizard.common",packageNode.getName());
		
		try {
			packageNode = dw.getPackage("org.designwizard.comparator");
			fail("This line cannot be processed");
		} catch (InexistentEntityException e) {}
		
		packageNode = dw.getPackage("org.designwizard.design");
		assertEquals("org.designwizard.design",packageNode.getName());
		
		packageNode = dw.getPackage("org.designwizard.design.factory");
		assertEquals("org.designwizard.design.factory",packageNode.getName());
		
		packageNode = dw.getPackage("org.designwizard.design.manager");
		assertEquals("org.designwizard.design.manager",packageNode.getName());
		
		packageNode = dw.getPackage("org.designwizard.design.manager.util");
		assertEquals("org.designwizard.design.manager.util",packageNode.getName());
		
		packageNode = dw.getPackage("org.designwizard.design.factory");
		assertEquals("org.designwizard.design.factory",packageNode.getName());
	
	}
	
	public void testPackagesWithRegularExpressions() throws InexistentEntityException {
		
		// Testa o retorno dos pacotes que contém a string "api"
		Set<PackageNode> packagesThatContainsApi = dw.getPackages(".*api.*");
		assertFalse(packagesThatContainsApi.isEmpty());
		assertTrue(packagesThatContainsApi.contains(dw.getPackage("org.designwizard.api")));
		assertTrue(packagesThatContainsApi.contains(dw.getPackage("org.designwizard.api.util")));
		assertTrue(packagesThatContainsApi.size() == 2);
		
		// Testa o retorno dos pacotes que terminam com a string "api"
		Set<PackageNode> packagesFinishWithApi = dw.getPackages(".*api$");
		assertFalse(packagesFinishWithApi.isEmpty());
		assertTrue(packagesFinishWithApi.contains(dw.getPackage("org.designwizard.api")));
		assertTrue(packagesFinishWithApi.size() == 1);
		
		// Testa o retorno dos pacotes que iniciam com a string "api"
		Set<PackageNode> packagesStartingWithApi = dw.getPackages("^api.*");
		assertTrue(packagesStartingWithApi.isEmpty());
		
		// Testa o retorno dos pacotes que contém a string "api" ou "util"
		Set<PackageNode> packagesThatContainsApiOrUtil = dw.getPackages(".*api.*|.*util.*");
		assertFalse(packagesThatContainsApiOrUtil.isEmpty());
		assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("org.designwizard.api")));
		assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("org.designwizard.api.util")));
		assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("org.designwizard.extractor.asm.util")));
		assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("tests.org.designwizard.design.manager.util")));
		assertTrue(packagesThatContainsApiOrUtil.contains(dw.getPackage("org.designwizard.design.manager.util")));
		assertTrue(packagesThatContainsApiOrUtil.size() == 5);
	}
	
	public void testCallingMethods() throws InexistentEntityException {
	
		PackageNode packageNode = dw.getPackage("tests.org.designwizard.design");
		assertEquals("tests.org.designwizard.design",packageNode.getName());
		
		Set<MethodNode> callingMethods = packageNode.getCallerMethods();
		assertFalse(callingMethods.isEmpty());
		assertTrue(callingMethods.contains(dw.getMethod("tests.suites.AllTests.suite()")));
		
		packageNode = dw.getPackage("org.designwizard.api.util");
		callingMethods = packageNode.getCallerMethods();
		assertFalse(callingMethods.isEmpty());
	
	}
	
	//FIXME pensar neste caso! eu sou contra a maneira como estÃ£o sendo interpretado os pacotes.
	public void testContainsClass() throws InexistentEntityException {
		
		// packages that do not contain classes are not extracted (eu nÃ£o concordo com isso :) )
		PackageNode packageNode = null;
		try {
			packageNode = dw.getPackage("org.designwizard");
			fail("This line cannot be processed");
		} catch (InexistentEntityException e) {
		}
		
		
		packageNode = dw.getPackage("tests.org.designwizard.design");
		assertTrue(packageNode.getAllClasses().contains(this.dw.getClass(PackageExtractionTest.class)));
		
		packageNode = dw.getPackage("org.designwizard.design.relation");
		assertFalse(packageNode.getAllClasses().contains(this.dw.getClass(PackageExtractionTest.class)));
		
		try {
			packageNode = dw.getPackage("default");
			fail("This line cannot be processed");
		} catch (InexistentEntityException e) {}
	
	}
}