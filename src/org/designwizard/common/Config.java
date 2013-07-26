package org.designwizard.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.designwizard.main.util.FileUtil;

/**
 * Configuration for Design Wizard.
 * @author Joao Arthur Brunet Monteiro - jarthur@dsc.ufcg.edu.br
 *
 */
public class Config {
	
	private static Config uniqueInstance = null;
	private int depth;
	private String entitiesFile;
	private static String properties = "designwizard.properties";
	private static String DEEP_KEY = "depth";
	private static String FILE_KEY = "file";
	
	/**
	 * Customizes properties of Design Wizard using the specified properties file.
	 * @param properties
	 */
	private Config(File properties) {
		
		Properties prop = new Properties();
		FileInputStream input = null;
		
		try {
			input = new FileInputStream(properties);
			prop.load(input);
			this.depth = Integer.parseInt((String) prop.get(DEEP_KEY));
			this.entitiesFile = prop.getProperty(FILE_KEY);
		} catch (FileNotFoundException e) { 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {}
		}
	}
	
	/**
	 * Returns the unique that manages the configuration of Design Wizard.
	 * @return the unique that manages the configuration of Design Wizard.
	 */
	public static Config getInstance() {
		if (uniqueInstance == null) {
			File prop = new File(Config.properties);
			if (prop.exists()) {
				uniqueInstance = new Config(new File(Config.properties));
			} else {
				uniqueInstance = new Config(createDefaultProperties());
			}
		}
		return uniqueInstance;
	}

	/**
	 * Creates the default properties file.
	 * @return the created file.
	 */
	private static File createDefaultProperties() {
		StringBuffer st = new StringBuffer();
		st.append("# This file contains the properties of Design Wizard.\n");
		st.append("# Do not add new keys in this file without change the common.Config class code. Just modify the values!\n\n");
		st.append("# Depth of analysis\n");
		st.append("depth 7\n\n");
		st.append("# path of the file containing the entities to be analyzed\n");
		st.append("file entities.txt");
		
		File returnValue = new File(properties);
		
		FileUtil.writeResult(returnValue, st.toString());
		return returnValue;
	}

	public File getEntitiesFile() {
		File file = new File(this.entitiesFile);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public int getDepthOfAnalysis() {
		return this.depth;
	}
}