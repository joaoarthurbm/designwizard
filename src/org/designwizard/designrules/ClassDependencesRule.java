package org.designwizard.designrules;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.exception.InexistentEntityException;

public class ClassDependencesRule extends AbstractDependencesRule {

	public ClassDependencesRule(String entity, DesignWizard dw) {

		super(entity, dw);

	}

	@Override
	public boolean checkRule() throws InexistentEntityException {
		
		return super.allowed.isEmpty() ? checkViolations() : checkAllowed();

	}

	private boolean checkViolations() throws InexistentEntityException {
	
		ClassNode classNode = super.dw.getClass(entity);

		Set<ClassNode> references = classNode.getCalleeClasses();

		// checking violations
		for (String violation : super.violations) {

			try {

				if (references.contains(this.dw.getClass(violation)))
					super.report += violation + "\n";

			} catch (InexistentEntityException e) {
				super.report += violation + "\n";

			}

		}

		return super.report.equals("") ? true : false;
	
	}
	
	private boolean checkAllowed() throws InexistentEntityException {
		
		ClassNode classNode = super.dw.getClass(entity);
		
		Set<ClassNode> references = classNode.getCalleeClasses();
		
		for (ClassNode c : references) {
			
			if (!allowed.contains(c.getName())) super.report += c.getName() + "\n";

		}
		
		if (references.size() != super.allowed.size()) return false;
		
		return super.report.equals("") ? true : false;
	
	}

}
