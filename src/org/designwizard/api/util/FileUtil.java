package org.designwizard.api.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.common.io.Files;

public class FileUtil {
	
	private File file;
	private BufferedReader input;
	private static final int BUFFER = 2048;
	
	public FileUtil(File file) throws IOException {
		this.file = file;
		this.input = new BufferedReader(new FileReader(file));
	}
	
	public static boolean writeResult(File file, String contents) {
		Writer output = null;
	    try (FileWriter localFile = new FileWriter(file)) {
	      output = new BufferedWriter(localFile);
	      output.write(contents);
	    } catch (IOException e) {
			return false;
		} 
	    finally {
	      if (output != null)
			try {
				output.close();
			} catch (IOException e) {
				return false;
			}
	    }
	    return true;
	}
	
	/**
     * Reads a line of text. A line is considered to be terminated by any one
     * of a line feed ('\n'), a carriage return ('\r'), or a carriage return
     * followed immediately by a linefeed.
     *
     * @return     A String containing the contents of the line, not including
     *             any line-termination characters, or null if the end of the
     *             stream has been reached
     *
     **/
	public String readLn() {
		String result = null;
	    try {
			result = this.input.readLine();
	    } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * This method extracts the files within an ear file to the directory returned.
	 * @param pathOfEarFile The path of the ear file to be extracted.
	 * @return the directory where the ear file was extracted.
	 * @throws FileNotFoundException 
	 */
	public static File extractFilesFromZipFile(String pathOfEarFile, String internalPath) throws IOException {
		File directory = Files.createTempDir();
		
		File sourceZipFile = new File(pathOfEarFile);

		// Open Zip file for reading
		ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
		
		// Create an enumeration of the entries in the zip file
		Enumeration<? extends ZipEntry> zipFileEntries = zipFile.entries();

		// Process each entry
		while (zipFileEntries.hasMoreElements()) {
			// grab a zip file entry
			ZipEntry entry = zipFileEntries.nextElement();
			
			String currentEntry = entry.getName();

			if (currentEntry.startsWith(internalPath)) {

				File destFile = new File(directory, currentEntry);

				// grabs file's parent directory structure
				File destinationParent = destFile.getParentFile();

				// create the parent directory structure if needed
				destinationParent.mkdirs();

				// extract file if not a directory
				if (!entry.isDirectory()) {
					BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
					int currentByte;
					// establish buffer for writing file
					byte data[] = new byte[BUFFER];

					// write the current file to disk
					FileOutputStream fos = new FileOutputStream(destFile);
					BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

					// read and write until last byte is encountered
					while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, currentByte);
					}
					dest.flush();
					dest.close();
					fos.close();
					is.close();
				}
			}
		}
		zipFile.close();
		
		return directory;
	}
	
	
	/**
	 * Deletes the given directory.
	 * @param directory The directory to be deleted.
	 * @return true if the directory was deleted; false otherwise.
	 * @throws IOException 
	 */
	public static boolean deleteDir(File directory) throws IOException {
		if (directory.isDirectory()) {
            File[] children = directory.listFiles();
            for (File file : children) {
                boolean success = deleteDir(file);
                if (!success) {
                    return false;
                }
            }
		}
        return directory.delete();
    }
	
	public void reset() throws IOException {
		this.input.close();
		this.input = new BufferedReader(new FileReader(file));
	}
}