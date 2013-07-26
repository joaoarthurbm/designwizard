package tests.org.designwizard.system;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class TraceForMethods {
	
	public static void main(String[] args) throws InexistentEntityException, IOException {
//		printMethodImpactWithDeep();
		printMethodImpactWithInheritanceCase1();
//		printMethodImpactWithInheritanceCase2();
//		printMethodImpactWithInheritanceCaseDesignWizard();
	}

	public static void printMethodImpactWithDeep() throws InexistentEntityException, IOException {
		DesignWizard dw = new DesignWizard("resources"+File.separator+"testFiles"+File.separator+"gerador.jar");
		ClassNode clazz = dw.getClass("CodeFSW");
		Set<MethodNode> methods = clazz.getAllMethods();
		
		for (MethodNode method : methods) {
			System.out.println("Trace for method: "+method.getName());
			System.out.println();
			ArrayList<String[]> result = (ArrayList<String[]>) method.getImpactOfAChange();
			for (String[] strings : result) {
				System.out.println(strings[0]+" <is called by> "+strings[1]);
			}
			System.out.println();
		}
	}
	
	public static void printMethodImpactWithInheritanceCase1() throws InexistentEntityException, IOException {
		DesignWizard dw = new DesignWizard("resources/testFiles/impactwithinheritance.jar");
		ClassNode clazz = dw.getClass("IVeiculo");
		MethodNode method = clazz.getDeclaredMethod("acelera()");
		
		System.out.println("Trace for method: "+method.getName());
		System.out.println();
		ArrayList<String[]> result = (ArrayList<String[]>) method.getImpactOfAChange();
		for (String[] strings : result) {
			System.out.println(strings[0]+" <is called by> "+strings[1]);
		}
	}
	
	public static void printMethodImpactWithInheritanceCase2() throws InexistentEntityException, IOException {
		DesignWizard dw = new DesignWizard("resources/testFiles/simpleimpactwithinheritance.jar");
		ClassNode clazz = dw.getClass("ICarro");
		MethodNode method = clazz.getDeclaredMethod("acelera()");
		
		System.out.println("Trace for method: "+method.getName());
		System.out.println();
		ArrayList<String[]> result = (ArrayList<String[]>) method.getImpactOfAChange();
		for (String[] strings : result) {
			System.out.println(strings[0]+" <is called by> "+strings[1]);
		}
	}
	
	public static void printMethodImpactWithInheritanceCaseDesignWizard() throws InexistentEntityException, IOException {
		DesignWizard dw = new DesignWizard("classes");
		ClassNode clazz = dw.getClass("designwizard.design.Entity");
		MethodNode method = clazz.getDeclaredMethod("getName()");
		
		System.out.println("Trace for method: "+method.getName());
		System.out.println();
		ArrayList<String[]> result = (ArrayList<String[]>) method.getImpactOfAChange();
		for (String[] strings : result) {
			System.out.println(strings[0]+" <is called by> "+strings[1]);
		}
	}
}