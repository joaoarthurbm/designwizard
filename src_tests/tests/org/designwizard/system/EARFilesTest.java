package tests.org.designwizard.system;

import java.io.File;

import org.designwizard.main.DesignWizard;

public class EARFilesTest {
	private DesignWizard dw;

	public EARFilesTest() throws Exception {
		String file = "resources"+File.separator+"testFiles"+File.separator+"ears"+File.separator+"bmp-simple.ear";
		this.dw = new DesignWizard(file);
	}

	public static void main(String[] args) throws Exception {
		EARFilesTest d = new EARFilesTest();
		d.getDescription();
	}
	
	public void getDescription() {
		this.dw.generateEntitiesFile();
//		System.out.println(this.dw.analyseFromEntitiesFile());
	}
}
