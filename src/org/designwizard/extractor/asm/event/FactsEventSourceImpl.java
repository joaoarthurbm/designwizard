package org.designwizard.extractor.asm.event;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.designwizard.extractor.asm.visitor.EmptyVisitor;
import org.designwizard.extractor.asm.visitor.GenericTypeSignatureVisitor;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;

public class FactsEventSourceImpl implements FactsEventSource {

	protected LinkedList<ExtractionListener> listeners = new LinkedList<ExtractionListener>();
	protected FactEvent factEvent;
	protected ClassVisitor cv;

	//FIXME Remover todas as Strings por Constantes ou ENUMS
	private static final String PUBLIC = "PUBLIC";
	private static final String PRIVATE = "PRIVATE";
	private static final String PROTECTED = "PROTECTED";
	private static final String PACKAGE = "PACKAGE";

	public FactsEventSourceImpl(ClassVisitor cv) {

		if (cv == null) {

			this.cv = new EmptyVisitor();

		}

		this.cv = cv;
	}

	public FactsEventSourceImpl() {}


	protected String extractVisibility(int access) {
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			return FactsEventSourceImpl.PUBLIC;
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			return FactsEventSourceImpl.PRIVATE;
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			return FactsEventSourceImpl.PROTECTED;
		} else {
			return FactsEventSourceImpl.PACKAGE;
		}
	}

	public void addListener(ExtractionListener e) {
		this.listeners.add(e);
	}

	public void addListener(List<ExtractionListener> e) {
		this.listeners.addAll(e);
	}

	public void removeListener(ExtractionListener e) {
		this.listeners.remove(e);
	}


	protected void fireRelationExtracted(){
		for (ExtractionListener l : this.listeners) {
			l.relationExtracted(this.factEvent);
		}
	}

	protected void fireSignatureExtracted(){
		for (ExtractionListener l : this.listeners) {
			l.signatureExtracted(this.factEvent);
		}
	}

	protected void fireVisibilityExtracted() {
		for (ExtractionListener l : this.listeners) {
			l.visibilityExtracted(this.factEvent);
		}
	}

	protected void fireModifiersExtracted() {
		for (ExtractionListener l : this.listeners) {
			l.modifiersExtracted(this.factEvent);
		}
	}

	protected void fireClassExtracted() {
		for (ExtractionListener l : this.listeners) {
			l.classExtracted(this.factEvent);
		}
	}
	
	/**
	 * Fire the Annotation Extracted Event.
	 */
	protected void fireAnnotationExtracted() {
        for (ExtractionListener l : this.listeners) {
            l.annotationExtracted(this.factEvent);
        }
    }

	public void firePackageExtracted() {
		for (ExtractionListener l : this.listeners) {
			l.packageExtracted(this.factEvent);
		}
	}


	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		this.cv.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	@Override
	public void visitAttribute(Attribute attr) {
		if (cv != null)	this.cv.visitAttribute(attr);
	}

	@Override
	public void visitEnd() {}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		return this.cv.visitField(access, name, desc, signature, value);
	}

	@Override
	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {
		this.cv.visitInnerClass(name, outerName, innerName, access);

	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		return this.cv.visitMethod(access, name, desc, signature, exceptions);
	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {
		this.cv.visitOuterClass(owner, name, desc);
	}

	@Override
	public void visitSource(String source, String debug) {
		this.cv.visitSource(source, debug);
	}
	
	protected void genericTypeExtration(String entity, String signature) {
		if (signature != null) {
			GenericTypeSignatureVisitor msv = new GenericTypeSignatureVisitor();
			SignatureReader r = new SignatureReader(signature);
			r.accept(msv);
			
			// Extraction of generic types as ClassNode
			Set<String> types = msv.getTypes();
			for (String type : types) {			
				factEvent = new FactEvent(this, "LOAD", entity, type);
				fireRelationExtracted();
			}
		}
	}
}
