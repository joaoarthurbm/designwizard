package org.designwizard.extractor.asm.util;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassPathHacker {
	 
	private final Class[] parameters = new Class[]{URL.class};
	 
	/**
	 * Adds the file to classpath.
	 * @param pathOfFile
	 * @throws IOException
	 */
	public void addFile(String pathOfFile) throws IOException {
		File f = new File(pathOfFile);
		addFile(f);
	}
	 
	public void addFile(File file) throws IOException {
		addURL(file.toURL());
	}
	 	 
	private void addURL(URL url) throws IOException {
		URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Class<URLClassLoader> sysclass = URLClassLoader.class;
		try {
			java.lang.reflect.Method method = sysclass.getDeclaredMethod("addURL",parameters);
			method.setAccessible(true);
			method.invoke(sysloader,new Object[]{ url });
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}
}

