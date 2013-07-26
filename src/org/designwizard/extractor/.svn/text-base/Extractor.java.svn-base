package org.designwizard.extractor;

import java.io.IOException;

import org.designwizard.extractor.asm.event.ExtractionListener;


public interface Extractor {

	/**
	 * Extracts facts from the jar file with the specified pathOfJarFile.
	 * @param pathOfJarFile The path of the jar file.
	 * @throws IOException If an I/O error occurs.
	 */
	public void processJar(String pathOfJarFile) throws IOException;
	
	/**
	 * Extracts from the ear file with the specified pathOfEarFile.
	 * @param pathOfEarFile The path of the ear file.
	 * @throws IOException If an I/O error occurs.
	 */
	public void processEar(String pathOfEarFile, String ... internalPath) throws IOException;
	
	/**
	 * Adds a listener that is interested in this extractor.
	 * @param listener The extraction listener.
	 */
	public void addListener(ExtractionListener listener);
	
	/**
	 * Extracts from the specified directory file with the specified directoryPath.
	 * @param directoryPath The path of the directory.
	 * @throws IOException If an I/O error occurs.
	 */
	public void processDir(String directoryPath) throws IOException;
	
	/**
	 * Extracts facts from the given .class file.
	 * @param classFilePath The path of the .class file. 
	 * @throws IOException 
	 */
	public void processClass(String classFilePath) throws IOException;
}