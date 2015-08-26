package org.designwizard.designrules;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.PackageNode;
import org.designwizard.exception.InexistentEntityException;

public class PackageDependences extends AbstractDependencesRule implements Rule {

	public PackageDependences(String entity, DesignWizard dw) {
		
		super(entity, dw);
	
	}

	@Override
	public boolean checkRule() throws InexistentEntityException {
		
		return super.allowed.isEmpty() ? checkViolations() : checkAllowed();
		
	}


	private boolean checkViolations() throws InexistentEntityException {
		
		PackageNode packageNode = super.dw.getPackage(entity);

		Set<PackageNode> references = packageNode.getCalleePackages();

		// checking violations
		for (String violation : super.violations) {

			try {

				if (references.contains(this.dw.getPackage(violation)))
					super.report += violation + "\n";

			} catch (InexistentEntityException e) {
				super.report += violation + "\n";

			}

		}

		return super.report.equals("") ? true : false;
	
	}

	private boolean checkAllowed() throws InexistentEntityException {

		PackageNode packageNode = super.dw.getPackage(entity);
		
		Set<PackageNode> references = packageNode.getCalleePackages();
		
		for (PackageNode p : references) {
			
			if (!allowed.contains(p.getName())) super.report += p.getName() + "\n";

		}
		
		if (references.size() != super.allowed.size()) return false;
		
		return super.report.equals("") ? true : false;

	}

}