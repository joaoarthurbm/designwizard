package org.designwizard.extractor.asm.visitor;

import java.util.Collection;

import org.designwizard.extractor.asm.event.FactEvent;
import org.designwizard.extractor.asm.event.FactsEventSourceImpl;
import org.designwizard.extractor.asm.util.OpcodesTranslator;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class FactsExtractionClassVisitor extends FactsEventSourceImpl {

	private String className;

	public FactsExtractionClassVisitor(String className) {

		this.className = className;

	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

		this.className = name;

		this.visitPackage(name);

		super.factEvent = new FactEvent(FactsExtractionClassVisitor.class, name);
		super.fireClassExtracted();

		Collection<String> modifiers = OpcodesTranslator.extractOpcodes(access);
		super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,name,modifiers);
		super.fireModifiersExtracted();

		
		// java.lang.Object does not extend any class
		if (superName != null) {
	
			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,"EXTENDS",name,superName);
			super.fireRelationExtracted();
		
		}

		for (int i = 0; i < interfaces.length; ++i) {

			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,"IMPLEMENTS",name,interfaces[i]);
			super.fireRelationExtracted();
		
		}

		super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,name,extractVisibility(access));
		super.fireVisibilityExtracted();

	}

	private void visitPackage(String className) {

		FactEvent event = extractPackage(className);
	

		super.factEvent = event;
		String packageName = super.factEvent.getEntity();
		super.firePackageExtracted();

		super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,"CONTAINS", packageName, className);
		super.fireRelationExtracted();
		
		
	}

	private FactEvent extractPackage(String className) {

		String classNameWithDots = className.replaceAll("[/\\\\]+", ".");

		if (!classNameWithDots.contains(".")) {
		
			return new FactEvent(FactsExtractionClassVisitor.class,"default");
		
		}
		
		String packageName = classNameWithDots.substring(0,classNameWithDots.lastIndexOf("."));
		return new FactEvent(FactsExtractionClassVisitor.class, packageName);

	}

	/*private Collection<FactEvent> extractPackages(String name) {

		Collection<FactEvent> returnCollection = new ArrayList<FactEvent>();

		String classNameWithDots = name.replaceAll("[/\\\\]+", ".");

		if (!classNameWithDots.contains(".")) {
		
			returnCollection.add(new FactEvent(FactsExtractionClassVisitor.class,"default"));
		
		} else {

			String packageName = classNameWithDots.substring(0,classNameWithDots.lastIndexOf("."));
	
			StringTokenizer st = new StringTokenizer(packageName,".");
			String builtString = "";
			builtString = st.nextToken();
	
			returnCollection.add(new FactEvent(FactsExtractionClassVisitor.class,builtString));
	
			while (st.hasMoreTokens()) {
				
				builtString = builtString + "." + st.nextToken();
				returnCollection.add(new FactEvent(FactsExtractionClassVisitor.class,builtString));
			
			}
		}
		
		return returnCollection;

	}*/
	 
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {

		String field = "F:"+this.className+"."+name;
		
		if (!name.contains("$")) {
		
			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,"INSTANCEOF",field,desc);
			super.fireRelationExtracted();

			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,"CONTAINS",this.className,field);
			super.fireRelationExtracted();

			Collection<String> modifiers = OpcodesTranslator.extractOpcodes(access);
			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,this.className+"."+name,modifiers);
			super.fireModifiersExtracted();

			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,field,extractVisibility(access));
			super.fireVisibilityExtracted();
		
		}
		
		if (signature == null) {
			super.factEvent = new FactEvent(this, "LOAD", this.className, desc);
			super.fireRelationExtracted();
		} else {
			// If signature != null extract GenericType
			// Extraction of generic types of the type of the field
			genericTypeExtration(field, signature);
		}
		
		FactsExtractionFieldVisitor fieldVisitor = new FactsExtractionFieldVisitor(field);
		fieldVisitor.addListener(super.listeners.getFirst());
		
		return fieldVisitor;

	}

	@Override
	public void visitInnerClass(String name, String outerName, String shortName, int access) {
		String formattedName = name.replaceAll("[/\\\\]+", ".");
		
		// Anonymous Class
		if ( outerName == null) {
			visitAnonymousClass(formattedName, access);
		} else {
			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class, formattedName);
			super.fireClassExtracted();

			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,"CONTAINS",outerName,formattedName);
			super.fireRelationExtracted();

			super.factEvent = new FactEvent(this,formattedName,extractVisibility(access));
			super.fireVisibilityExtracted();

			Collection<String> modifiers = OpcodesTranslator.extractOpcodes(access);
			modifiers.add("INNER");
			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,formattedName,modifiers);
			super.fireModifiersExtracted();
		}

	}

	private void visitAnonymousClass(String name, int access) {
	
		super.factEvent = new FactEvent(FactsExtractionClassVisitor.class, name);
		super.fireClassExtracted();
		
		String outerName = name.substring(0,name.lastIndexOf("$"));
				
		super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,"CONTAINS",outerName,name);
		super.fireRelationExtracted();
		
		super.factEvent = new FactEvent(this,name,extractVisibility(access));
		super.fireVisibilityExtracted();
		
		Collection<String> modifiers = OpcodesTranslator.extractOpcodes(access);
		modifiers.add("INNER");
		modifiers.add("ANONYMOUS");
		
		super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,name,modifiers);
		super.fireModifiersExtracted();
	
	}
	
	/**
	 * Método de visita de Annotações.
	 * @param annotationName
	 * @param isVisible
	 */
	@Override
	public AnnotationVisitor visitAnnotation(String annotationName, boolean isVisible) {
		// Cria um novo FactEvent para Annotations
		super.factEvent = new FactEvent(FactsExtractionClassVisitor.class, annotationName, isVisible);
		super.fireAnnotationExtracted();

		// Caller = classname and Called = desc (annotation)
		super.factEvent = new FactEvent(FactsExtractionClassVisitor.class, "ISANNOTATEDBY", this.className, annotationName);
		super.fireRelationExtracted();
		
		return new EmptyVisitor();
	};

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

		String method = ""; 

		if ((access & Opcodes.ACC_STATIC) != 0) {
		
			method = "S:"+" "+"M:"+this.className+"."+name+" "+desc;
		
		} else {
		
			method = "M:"+this.className+"."+name+" "+desc;
		
		}

		if (!method.contains("$SWITCH_TABLE$")) {
	
			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,"CONTAINS",this.className,method);
			super.fireRelationExtracted();
		
		}

		method = "M:"+this.className+"."+name+" "+desc;
		
		if (!method.contains("$SWITCH_TABLE$")) {
		
			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,method,extractVisibility(access));
			super.fireSignatureExtracted();

			if (exceptions != null) {
			
				for (String exception : exceptions) {
				
					super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,"THROWS",method,exception);
					super.fireRelationExtracted();
				
				}
			
			}
			
			Collection<String> modifiers = OpcodesTranslator.extractOpcodes(access);
			super.factEvent = new FactEvent(FactsExtractionClassVisitor.class,method,modifiers);
			super.fireModifiersExtracted();
		
		}
		
		// Extraction of generic types of the return type of the method
		genericTypeExtration(method, signature);

		FactsExtractionMethodVisitor tcv = new FactsExtractionMethodVisitor(method);
		tcv.addListener(super.listeners.getFirst());
		return tcv;

	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {}
	@Override
	public void visitSource(String source, String debug) {}
}