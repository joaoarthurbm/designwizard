package org.designwizard.designrules;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;

public class HashCodeAndEqualsRule implements Rule {
	
	
	private String entity;
	private DesignWizard dw;
	private String report;

	public HashCodeAndEqualsRule(String entity, DesignWizard dw) {
		
		this.entity = entity;
		this.dw = dw;
		this.report = "";
		
	}

	
	@Override
	public boolean checkRule() throws InexistentEntityException {

		ClassNode entityNode = dw.getClass(this.entity);
		
		MethodNode equalsMethod = entityNode.getDeclaredMethod("equals(java.lang.Object)");
		MethodNode hashCodeMethod = entityNode.getDeclaredMethod("hashCode()");
		
		Set<MethodNode> declaredMethods = entityNode.getDeclaredMethods();
		
		
		if (declaredMethods.contains(equalsMethod) ^ declaredMethods.contains(hashCodeMethod) ) {

			this.report = "< " + this.entity + " >" + "\n";
			return false;
		}
		
		return true;
		
	}

	public String getReport() {
		
		return this.report;
		
	}

}
