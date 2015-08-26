package org.designwizard.designrules;

import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.PackageNode;

public class CyclicDependencyRule implements Rule {

	private DesignWizard dw;
	private String report;

	public CyclicDependencyRule(DesignWizard dw) {

		this.dw = dw;
		this.report = "";
	
	}

	@Override
	public boolean checkRule() {
		
		for (PackageNode packageNode : dw.getAllPackages()) {
		
			Set<PackageNode> callersPackages = packageNode.getCallerPackages();
		
			for (PackageNode caller : callersPackages) {
			
				if (!caller.equals(packageNode) && caller.getCallerPackages().contains(packageNode)) { 
				
					this.report += "Cycle between <" + packageNode + ">" + " <" + caller.getName() + ">" + "\n";
					
				}
		
			}
		}
		
		return this.report.equals("") ? true : false;
		
	}


	@Override
	public String getReport() {

		return this.report;
		
	}


}
