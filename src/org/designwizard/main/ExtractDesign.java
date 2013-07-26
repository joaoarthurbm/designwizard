package org.designwizard.main;

import java.io.IOException;

import org.designwizard.main.util.GXLUtil;

public class ExtractDesign {

	/**
	 * @param args
	 */
	private static String inputFile;
	private static String outputFile; 
	private static String idGraph;
	private static DesignWizard dw = null;
	private static GXLUtil gxlUtil = null;
	
	public static void main(String[] args) {
		
		if (args.length == 3){
			inputFile = args[0];
			outputFile = args[1];
			idGraph = args[2];
			try {
				dw = new DesignWizard(inputFile);
				gxlUtil = new GXLUtil(dw);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			gxlUtil.generateGXLFile(outputFile,idGraph);
		}else if (args.length == 2){
					inputFile = args[0];
					outputFile = args[1];
					try {
						dw = new DesignWizard(inputFile);
						gxlUtil = new GXLUtil(dw);
					} catch (IOException e) {
						e.printStackTrace();
					}
					gxlUtil.generateGXLFile(outputFile);
				}else{
					System.out.println("Java ExtractDesign InputFile OutputFile [idgraph]");
				}
	}
}
