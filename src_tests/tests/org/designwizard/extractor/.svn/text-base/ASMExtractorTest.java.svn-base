package tests.org.designwizard.extractor;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;


public class ASMExtractorTest extends TestCase {
	
	private DesignWizard dw;
	
	private static final String fs = File.separator;
	
	private static final String EAR_PROJECT_PATH = "resources" + fs + "test_aplications" + fs
			+ "Ear" + fs + "dashsample.ear";
	
	private static final String WAR_PROJECT_PATH = "resources" + fs + "test_aplications" + fs
	+ "Ear" + fs + "dashsample.war";
	
	private static final String DIRECTORY_PATH = "resources" + fs + "test_aplications" + fs
	+ "Ear" + fs + "classes";
	


	public void testEarExtractor() throws IOException, InexistentEntityException {
		
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample.war", "WEB-INF/lib/jetty.jar");
		                           
		assertNotNull(dw.getClass("org.mortbay.cometd.AbstractTransport"));
		assertNotNull(dw.getClass("org.mortbay.cometd.Bayeux$HandshakeHandler"));
		assertFalse(dw.getClass("org.mortbay.cometd.Bayeux$HandshakeHandler").getAllMethods().isEmpty());
		
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "META-INF/classes/");
        
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		assertFalse(dw.getClass("com.kbtflawt.server.ejb.Publisher").getAllMethods().isEmpty());
		
		
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample-support.jar");
		
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		assertNotNull(dw.getClass("org.mortbay.cometd.AbstractTransport"));
		assertFalse (dw.getClass("org.mortbay.cometd.AbstractTransport").getAllMethods().isEmpty());
		
		try {
			this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample-support.jar", "tEste");
			fail();
		}catch (Exception e) {	}
		
		try {
			this.dw = new DesignWizard(EAR_PROJECT_PATH, "META-INF/classes/", "TEste");
			fail();
		}catch (Exception e) {	}
		
		
		this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample.war", "WEB-INF/classes/");
		
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		assertFalse(dw.getClass("com.kbtflawt.server.ejb.Publisher").getAllMethods().isEmpty());
		
		try {
			this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample.war", "WEB-INF/classes/com/kbtflawt/server/ejb/DirectoryPoller.java");
			fail();
		}catch (Exception e) {	}
		
		
		try {
			this.dw = new DesignWizard(EAR_PROJECT_PATH, "dashsample.java");
			fail();
		}catch (Exception e) {	}
		
		try {
			this.dw = new DesignWizard(EAR_PROJECT_PATH);
			fail();
		}catch (Exception e) {	}
		
	}
	
	public void testWarExtractor () throws InexistentEntityException, IOException {
		this.dw = new DesignWizard(WAR_PROJECT_PATH, "WEB-INF/classes/");
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		
		this.dw = new DesignWizard(WAR_PROJECT_PATH, "");
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		assertNotNull(dw.getClass("org.mortbay.cometd.AbstractTransport"));
		assertNotNull(dw.getClass("org.mortbay.cometd.Bayeux$HandshakeHandler"));
		
		try {
			this.dw = new DesignWizard(WAR_PROJECT_PATH,  "WEB-INF/classes/", "teste");
			fail();
		}catch (Exception e) {	}
		
		try {
			this.dw = new DesignWizard(WAR_PROJECT_PATH,  "WEB-INF/lib/jetty-util-6.0.1.jar", "teste");
			fail();
		}catch (Exception e) {	}
		
		try {
			this.dw = new DesignWizard(WAR_PROJECT_PATH, "dashsample.java");
			fail();
		}catch (Exception e) {	}
		
		try {
			this.dw = new DesignWizard(WAR_PROJECT_PATH);
			fail();
		}catch (Exception e) {	}
		
	}
	
	public void testDirectoryExtractor () throws InexistentEntityException, IOException {
		this.dw = new DesignWizard(DIRECTORY_PATH);
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPoller"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.DirectoryPollerEJBLocalHome"));
		assertNotNull(dw.getClass("com.kbtflawt.server.ejb.Publisher"));
		
		try {
			this.dw = new DesignWizard(DIRECTORY_PATH,  "WEB-INF/classes/", "teste");
			fail();
		}catch (Exception e) {	}
		
		try {
			this.dw = new DesignWizard(DIRECTORY_PATH,  "WEB-INF/lib/jetty-util-6.0.1.jar", "teste");
			fail();
		}catch (Exception e) {	}
		
		try {
			this.dw = new DesignWizard(DIRECTORY_PATH, "dashsample.java");
			fail();
		}catch (Exception e) {	}
		
		try {
			this.dw = new DesignWizard(DIRECTORY_PATH, "com");
			fail();
		}catch (Exception e) {	}
		
	}

	
	
}
