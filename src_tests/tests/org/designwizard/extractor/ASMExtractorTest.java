package tests.org.designwizard.extractor;

import java.io.File;
import java.io.IOException;

import org.designwizard.api.DesignWizard;
import org.designwizard.exception.InexistentEntityException;
import org.junit.Assert;
import org.junit.Test;


public class ASMExtractorTest {
	
	private DesignWizard dw;
	
	private static final String fs = File.separator;
	
	private static final String EAR_PROJECT_PATH = "resources" + fs + "test_aplications" + fs
			+ "Ear" + fs + "dashsample.ear";
	
	private static final String WAR_PROJECT_PATH = "resources" + fs + "test_aplications" + fs
	+ "Ear" + fs + "dashsample.war";
	
	private static final String DIRECTORY_PATH = "resources" + fs + "test_aplications" + fs
	+ "Ear" + fs + "classes";
	
	@Test(expected=Exception.class)
	public void testEarExtractor() throws IOException, InexistentEntityException {
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample.war", "WEB-INF/lib/jetty.jar");
		                           
		Assert.assertNotNull(dw.getClass("org.mortbay.cometd.AbstractTransport"));
		Assert.assertNotNull(dw.getClass("org.mortbay.cometd.Bayeux$HandshakeHandler"));
		Assert.assertFalse(dw.getClass("org.mortbay.cometd.Bayeux$HandshakeHandler").getAllMethods().isEmpty());
		
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "META-INF/classes/");
        
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		Assert.assertFalse(dw.getClass("com.kbtflawt.server.ejb.Publisher").getAllMethods().isEmpty());
		
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample-support.jar");
		
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		Assert.assertNotNull(dw.getClass("org.mortbay.cometd.AbstractTransport"));
		Assert.assertFalse (dw.getClass("org.mortbay.cometd.AbstractTransport").getAllMethods().isEmpty());
		
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample-support.jar", "tEste");
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "META-INF/classes/", "TEste");
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample.war", "WEB-INF/classes/");
		
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		Assert.assertFalse(dw.getClass("com.kbtflawt.server.ejb.Publisher").getAllMethods().isEmpty());
		
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample.war", "WEB-INF/classes/com/kbtflawt/server/ejb/DirectoryPoller.java");
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample.java");
		this.dw = new DesignWizard(EAR_PROJECT_PATH);
	}
	
	@Test(expected=Exception.class)
	public void testWarExtractor () throws InexistentEntityException, IOException {
		this.dw = new DesignWizard(WAR_PROJECT_PATH, "WEB-INF/classes/");
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		
		this.dw = new DesignWizard(WAR_PROJECT_PATH, "");
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		Assert.assertNotNull(dw.getClass("org.mortbay.cometd.AbstractTransport"));
		Assert.assertNotNull(dw.getClass("org.mortbay.cometd.Bayeux$HandshakeHandler"));
		
		this.dw = new DesignWizard(WAR_PROJECT_PATH,  "WEB-INF/classes/", "teste");
		
		this.dw = new DesignWizard(WAR_PROJECT_PATH,  "WEB-INF/lib/jetty-util-6.0.1.jar", "teste");
		
		this.dw = new DesignWizard(WAR_PROJECT_PATH, "dashsample.java");
		this.dw = new DesignWizard(WAR_PROJECT_PATH);		
	}
	
	@Test(expected=Exception.class)
	public void testDirectoryExtractor () throws InexistentEntityException, IOException {
		this.dw = new DesignWizard(DIRECTORY_PATH);
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		Assert.assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		
		this.dw = new DesignWizard(DIRECTORY_PATH,  "WEB-INF/classes/", "teste");
		this.dw = new DesignWizard(DIRECTORY_PATH,  "WEB-INF/lib/jetty-util-6.0.1.jar", "teste");
		this.dw = new DesignWizard(DIRECTORY_PATH, "dashsample.java");
		this.dw = new DesignWizard(DIRECTORY_PATH, "com");
	}	
}