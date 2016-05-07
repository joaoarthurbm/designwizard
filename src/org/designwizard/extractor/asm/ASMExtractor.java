package org.designwizard.extractor.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.designwizard.api.util.FileUtil;
import org.designwizard.extractor.Extractor;
import org.designwizard.extractor.asm.event.ExtractionListener;
import org.designwizard.extractor.asm.event.FactsEventSource;
import org.designwizard.extractor.asm.visitor.FactsExtractionClassVisitor;
import org.objectweb.asm.ClassReader;

public class ASMExtractor implements Extractor {

	private List<ExtractionListener> managers = new ArrayList<ExtractionListener>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.objectweb.asm.Extractor#processJar(java.lang.String)
	 */
	public void processJar(String fileName) throws IOException {

		String entryName = null;
		JarEntry jarEntry = null;
		JarFile jarFile = new JarFile(fileName);
		Enumeration<JarEntry> e = jarFile.entries();

		URL[] urls = { new File(fileName).toURI().toURL() };
		URLClassLoader classLoader = new URLClassLoader(urls, null);

		while (e.hasMoreElements()) {
			jarEntry = e.nextElement();
			entryName = jarEntry.toString();
			if (entryName.endsWith(".class")) {
				this.processClass(entryName, classLoader);
			}
		}
		jarFile.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.objectweb.asm.Extractor#processClassesDir(java.lang.String)
	 */
	public void processDir(String fileName) throws IOException {

		File file = new File(fileName);
		URLClassLoader classLoader = new URLClassLoader(new URL[] { new File(fileName).toURI().toURL() });

		if (file.isFile() && file.getName().endsWith(".class")) {

			processClass(fileName, classLoader);

		} else if (file.isDirectory()) {

			File[] files = file.listFiles();

			for (File classFile : files) {

				processDir(fileName + File.separator + classFile.getName());

			}

		}
		classLoader.close();
	}

	private String removeDirectoryName(String fileName) {
		int pos = fileName.indexOf(File.separator);
		if (pos > 0) {

			return fileName.substring(pos + 1);

		}

		return "";

	}

	/**
	 * Process the class with the specified className.
	 * 
	 * @param className
	 *            The name of the class to be extracted.
	 * @param loader
	 *            A loader for the class.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	private void processClass(String className, URLClassLoader loader) throws IOException {
		
		String toPut = className.replace(".class", "");

		InputStream in = loader.getResourceAsStream(toPut.replace('.',File.separatorChar) + ".class");

		if (in == null)	in = new FileInputStream(new File(className).getAbsoluteFile());
		ClassReader cr = new ClassReader(in);
		

		FactsEventSource cv = new FactsExtractionClassVisitor(className.replace(".class", ""));
		cv.addListener(this.managers);
		cr.accept(cv, 0);
		in.close();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.objectweb.asm.Extractor#addListener(org.objectweb.asm.ExtractionListener
	 * )
	 */
	public void addListener(ExtractionListener manager) {
		this.managers.add(manager);
	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.objectweb.asm.Extractor#processEar(java.lang.String)
	 */
	public void processEar(String pathOfEarFile, String... internalPath) throws IOException {
		File directory = null;
		try {
			
			directory = FileUtil.extractFilesFromZipFile(pathOfEarFile, internalPath[0]);
			File resultFile = new File(directory.getAbsolutePath() + File.separator + internalPath[0]);
			if (resultFile.isDirectory()) {
				if (internalPath.length > 1) throw new IOException("Incomplete Path");
				processDir(resultFile);
			} else if (resultFile.getAbsolutePath().endsWith(".war") || resultFile.getAbsolutePath().endsWith(".jar")) {
				if (internalPath.length == 1) processEar(resultFile.getAbsolutePath(), "");
				else {
					processEar(resultFile.getAbsolutePath(), Arrays
							.copyOfRange(internalPath, 1, internalPath.length));
				}
//			} else if (resultFile.getAbsolutePath().endsWith(".jar")) {
//				if (internalPath.length > 1) throw new IOException("Incomplete Path");
//				processEar(resultFile.getAbsolutePath(), "");
			} else {
				throw new IOException("Incorrect Path");
			}
				
		} finally {
			boolean deleted = FileUtil.deleteDir(directory);
			if (!deleted) FileUtil.deleteDir(directory);
		}
	}

	/**
	 * Searches recursively for jar files and processes them.
	 * 
	 * @param directory
	 *            The directory containing the jar files.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	private void processDir(File directory) throws IOException {
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				processDir(file);
			} else if (file.getAbsolutePath().endsWith(".jar")) {
				processJar(file.getAbsolutePath());
			} else if (file.getAbsolutePath().endsWith(".class")) {

				String className = file.getAbsolutePath();
				FileInputStream in = new FileInputStream(className);
				ClassReader cr = new ClassReader(in);
				
				FactsEventSource cv = new FactsExtractionClassVisitor(className.replace(".class",
						""));
				cv.addListener(this.managers);
				cr.accept(cv, 0);
				in.close();

			} else if (file.getAbsolutePath().endsWith(".ear") || file.getAbsolutePath().endsWith(".war")) {
				processEar(file.getAbsolutePath(), "");
			}
		}
	}


	@Override
	public void processClass(String classFilePath) throws IOException {
		URLClassLoader classLoader = new URLClassLoader(
				new URL[] { new File(classFilePath).toURI().toURL() });
		classFilePath = removeDirectoryName(classFilePath);
		processClass(classFilePath, classLoader);
	}
}