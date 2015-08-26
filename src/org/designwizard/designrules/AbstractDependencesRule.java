package org.designwizard.designrules;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.designwizard.api.DesignWizard;
import org.designwizard.exception.InexistentEntityException;

public abstract class AbstractDependencesRule implements DependencesRuleIF {

	protected String entity;
	protected List<String> violations;
	protected Set<String> allowed;
	protected DesignWizard dw;
	protected String report;
	
	
	
	protected AbstractDependencesRule(String entity, DesignWizard dw) {
		
		this.entity = entity;
		this.violations = new LinkedList<String>();
		this.allowed = new HashSet<String>();
		this.dw = dw;
		this.report = "";
		
	}
	
	public void addAllowedEntities(String... entities) {

		for (String	 e : entities) {
			
			this.allowed.add(e);

		}
		
	}


	public void addDeniedEntities(String... entities) {

		for (String	 e : entities) {
			
			this.violations.add(e);

		}
		
	}


	@Override
	public String getReport() {
		return this.report;
	}


	@Override
	public abstract boolean checkRule() throws InexistentEntityException;
	
}
