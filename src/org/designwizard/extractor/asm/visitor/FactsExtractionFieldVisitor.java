package org.designwizard.extractor.asm.visitor;

import org.designwizard.extractor.asm.event.FactEvent;
import org.designwizard.extractor.asm.event.FactsEventSourceImpl;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;

public class FactsExtractionFieldVisitor extends FactsEventSourceImpl implements FieldVisitor {
	
	private String fieldName;

	public FactsExtractionFieldVisitor(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.fieldName = name;
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String annotationName, boolean isVisible) {
		// Cria um novo FactEvent para Annotations do FieldNode
		super.factEvent = new FactEvent(FactsExtractionFieldVisitor.class, annotationName, isVisible);
		super.fireAnnotationExtracted();

		// Caller = classname and Called = desc (annotation)
		super.factEvent = new FactEvent(FactsExtractionFieldVisitor.class, "ISANNOTATEDBY", this.fieldName, annotationName);
		super.fireRelationExtracted();
		
		return super.visitAnnotation(annotationName, isVisible);
	}
}