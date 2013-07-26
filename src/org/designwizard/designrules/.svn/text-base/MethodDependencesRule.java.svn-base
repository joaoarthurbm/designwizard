package org.designwizard.designrules;

import java.util.Set;

import org.designwizard.design.MethodNode;
import org.designwizard.exception.InexistentEntityException;
import org.designwizard.main.DesignWizard;

public class MethodDependencesRule extends AbstractDependencesRule implements Rule {

	public MethodDependencesRule(String entity, DesignWizard dw) {

		super(entity, dw);
	
	}

	@Override
	public boolean checkRule() throws InexistentEntityException {
		
		return super.allowed.isEmpty() ? checkViolations() : checkAllowed();
	
	}
	
	private boolean checkViolations() throws InexistentEntityException {
		MethodNode method = super.dw.getMethod(super.entity);
		
		Set<MethodNode> references = method.getCalleeMethods();
		
		// checking violatons
		for (String violation : super.violations) {
			
			try {
		
				if (references.contains(this.dw.getMethod(violation)))
					super.report += violation + "\n";
			
			} catch (InexistentEntityException e) {
			
				super.report += violation + "\n";
			
			}
		
		}
		
		return super.report.equals("") ? true : false;
	}

	private boolean checkAllowed() throws InexistentEntityException {
		
		MethodNode methodNode = super.dw.getMethod(entity);
		
		Set<MethodNode> references = methodNode.getCalleeMethods();
		
		for (MethodNode m : references) {
			
			if (!allowed.contains(m.getName())) super.report += m.getName() + "\n";

		}
		
		if (references.size() != super.allowed.size()) return false;
		
		return super.report.equals("") ? true : false;

	}

}