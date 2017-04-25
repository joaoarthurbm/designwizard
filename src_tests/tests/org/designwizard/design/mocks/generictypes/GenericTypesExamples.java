package tests.org.designwizard.design.mocks.generictypes;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.designwizard.design.FieldNode;

public class GenericTypesExamples {
	
	private Set<ElementType> elements;
	
	/**
	 * Returns a <code>Set</code> of <code>ElementType</code> objects.
	 */
	public Set<ElementType> getElements() {
		return this.elements;
	}
	
	/**
	 * Sets a <code>Set</code> of <code>ElementType</code> objects.
	 */
	public void setElements(Set<ElementType> elements) {
		this.elements = elements;
	}
	
	/**
	 * Returns a <code>Set</code> of <code>FieldNode</code> objects representing the 
	 * fields accessed by this <code>MethodNode</code>.
	 */
	public Set<FieldNode> getAccessedFields() {
		Set<FieldNode> feedBack = new HashSet<FieldNode>();
		Set<ElementType> accessElements = this.getElements();
		FieldNode field = null;
		for (ElementType element: accessElements) {
			field = (FieldNode) element.getEntity();
			feedBack.add(field);
		}
		return feedBack;
	}
	
	/**
	 * Returns a <code>Set</code> of <code>FieldNode</code> objects representing the 
	 * fields accessed by this <code>MethodNode</code>.
	 */
	public Set<ElementType> extractElementType() {
		Set<ElementType> accessElements = this.getElements();
		return accessElements;
	}
	
	/**
	 * Returns a <code>Set</code> of <code>FieldNode</code> objects representing the 
	 * fields accessed by this <code>MethodNode</code>.
	 */
	public void extractFieldNode() {
		
		Set<FieldNode> feedBack = new HashSet<>();
		Iterator<FieldNode> it = feedBack.iterator();
		
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}