package org.designwizard.extractor.asm.visitor;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.signature.SignatureVisitor;

public class GenericTypeSignatureVisitor implements SignatureVisitor {
	
	private Set<String> types;

	public GenericTypeSignatureVisitor() {
		this.types = new HashSet<>();
	}
	
	public Set<String> getTypes() {
		return types;
	}
	
	@Override
	public void visitFormalTypeParameter(String name) {
	}

	@Override
	public SignatureVisitor visitClassBound() {
		return this;
	}

	@Override
	public SignatureVisitor visitInterfaceBound() {
		return this;
	}

	@Override
	public SignatureVisitor visitSuperclass() {
		return this;
	}

	@Override
	public SignatureVisitor visitInterface() {
		return this;
	}

	@Override
	public SignatureVisitor visitParameterType() {
		return this;
	}

	@Override
	public SignatureVisitor visitReturnType() {
		return this;
	}

	@Override
	public SignatureVisitor visitExceptionType() {
		return this;
	}

	@Override
	public void visitBaseType(char descriptor) {
	}

	@Override
	public void visitTypeVariable(String name) {
		types.add(name);
	}

	@Override
	public SignatureVisitor visitArrayType() {
		return this;
	}

	@Override
	public void visitClassType(String name) {
		types.add(name);
	}

	@Override
	public void visitInnerClassType(String name) {
		visitClassType(name);
	}

	@Override
	public void visitTypeArgument() {
	}

	@Override
	public SignatureVisitor visitTypeArgument(char wildcard) {
		return this;
	}

	@Override
	public void visitEnd() {
	}
}
