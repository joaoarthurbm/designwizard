package tests.org.designwizard.design.mocks.generictypes;

import org.designwizard.design.Entity;
import org.designwizard.design.FieldNode;

public class ElementType {
	
	public ElementType() {
	}
	
	public Entity getEntity() {
		return new FieldNode("GenericTypesTest");
	}
}