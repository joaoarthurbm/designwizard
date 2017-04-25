/***
 * ASM: a very small and fast Java bytecode manipulation framework
 * Copyright (c) 2000-2005 INRIA, France Telecom
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holders nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.designwizard.extractor.asm.visitor;

import org.designwizard.extractor.asm.event.FactEvent;
import org.designwizard.extractor.asm.event.FactsEventSourceImpl;
import org.designwizard.extractor.asm.util.OpcodesTranslator;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * @author jarthur@dsc.ufcg.edu.br
 */
public class FactsExtractionMethodVisitor extends FactsEventSourceImpl implements MethodVisitor {

	private String method;

	public FactsExtractionMethodVisitor(String method) {
		this.method = method;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String annotationName,boolean isVisible) {
		// Cria um novo FactEvent para Annotations do MethodNode
		super.factEvent = new FactEvent(FactsExtractionMethodVisitor.class, annotationName, isVisible);
		super.fireAnnotationExtracted();

		// Caller = classname and Called = desc (annotation)
		super.factEvent = new FactEvent(FactsExtractionMethodVisitor.class, "ISANNOTATEDBY", this.method, annotationName);
		super.fireRelationExtracted();
		
		return super.visitAnnotation(annotationName, isVisible);
	}

	public AnnotationVisitor visitAnnotationDefault() {
		return new EmptyVisitor();
	}

	public AnnotationVisitor visitParameterAnnotation(final int parameter, final String annotationName, final boolean visible) {
		// Cria um novo FactEvent para Annotations do MethodNode
		super.factEvent = new FactEvent(FactsExtractionMethodVisitor.class, annotationName, visible);
		super.fireAnnotationExtracted();
		
		// Caller = classname and Called = desc (annotation)
		super.factEvent = new FactEvent(FactsExtractionMethodVisitor.class, "ISANNOTATEDBY", this.method, annotationName);
		super.fireRelationExtracted();

		return super.visitAnnotation(annotationName, visible);
	}

	public void visitCode() {}

	public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
	
		String field = "F:"+owner+"."+name;
		super.factEvent = new FactEvent(this,OpcodesTranslator.OPCODES[opcode],method,field);
		super.fireRelationExtracted();
	
	}

	public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc) {

		String calledMethod = "M:"+owner+"."+name+" "+desc;

		if (!calledMethod.contains("$SWITCH_TABLE$") && !method.contains("$SWITCH_TABLE$") 
				&& !method.contains("clinit")) {

			super.factEvent = new FactEvent(this,OpcodesTranslator.OPCODES[opcode],method,calledMethod);
			super.fireRelationExtracted();

		}

	}


	public void visitLdcInsn(final Object cst) {

		if (cst instanceof Type) {

			Type obj = (Type) cst;
			super.factEvent = new FactEvent(this,"LOAD",this.method,obj.getClassName());
			this.fireRelationExtracted();
	
		}
	
	}

	public void visitTryCatchBlock(final Label start, final Label end, final Label handler, final String type) {
	
		// try without catch
		if (type != null) {
		
			super.factEvent = new FactEvent(this,"CATCH",this.method,type);
			super.fireRelationExtracted();
		
		}
	
	}

	@Override
	public void visitEnd() {}

	public void appendLabel(final Label l) {}

	public void visitJumpInsn(final int opcode, final Label label) {}

	public void visitLabel(final Label label) {}

	public void visitIincInsn(final int var, final int increment) {}

	public void visitInsn(final int opcode) {}

	public void visitIntInsn(final int opcode, final int operand) {}

	public void visitVarInsn(final int opcode, final int var) {}

	public void visitTableSwitchInsn(final int min, final int max, final Label dflt, final Label labels[]) {}

	public void visitLookupSwitchInsn(final Label dflt, final int keys[], final Label labels[]) {}

	public void visitMultiANewArrayInsn(final String desc, final int dims) {}

	public void visitLocalVariable(final String name, final String desc, final String signature, final Label start, final Label end, final int index) {
		// If name != this (All methods locally call the class itself "this")
		if (!"this".equals(name)) {
			if (signature == null) {
				super.factEvent = new FactEvent(this, "LOAD", this.method, desc);
				super.fireRelationExtracted();
			} else {
				// If signature != null extract GenericType
				// Extraction of generic types of the type of the local variable
				genericTypeExtration(this.method, signature);
			}
		}
	}

	public void visitLineNumber(final int line, final Label start) {}

	public void visitMaxs(final int maxStack, final int maxLocals) {}

	public void visitTypeInsn(final int opcode, final String desc) {}

	@Override
	public void visitAttribute(final Attribute attr) {}

	public void visitFrame(int type, int local, Object[] local2, int stack, Object[] stack2) {}
}