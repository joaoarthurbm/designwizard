package org.designwizard.api.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.gxl.GXLDocument;
import net.sourceforge.gxl.GXLEdge;
import net.sourceforge.gxl.GXLGraph;
import net.sourceforge.gxl.GXLGraphElement;
import net.sourceforge.gxl.GXLNode;
import net.sourceforge.gxl.GXLString;

import org.designwizard.api.DesignWizard;
import org.designwizard.design.ClassNode;
import org.designwizard.design.FieldNode;
import org.designwizard.design.MethodNode;
import org.designwizard.design.PackageNode;

public class GXLUtil {
	 private GXLDocument gxlDocument;
	 private GXLGraph graph;
	 private DesignWizard designWizard;
	 private Set<ClassNode> classes;
	 private String prefixClass = "class";
	 private String prefixField = "field";
	 private String prefixRelation  = "relation";
	 private String prefixMethod = "method";
	 private String prefixPackage = "package";
	 private int id = 1000000;
	 private Map<String, GXLNode> mapGXLPackageNode;
	 private Map<String, GXLNode> mapGXLClassNodes;
	 private Map<String, GXLNode> mapGXLMethodNodes;
	 private Map<String, GXLNode> mapGXLFieldNodes;
	 	 
	 public GXLUtil(DesignWizard designWizard) {
		this.designWizard = designWizard;
	}

	public void writeGxl(String nameFile) {
		 mapGXLPackageNode = new HashMap<String, GXLNode>();
		 mapGXLClassNodes = new HashMap<String, GXLNode>(); 
		 mapGXLMethodNodes = new HashMap<String, GXLNode>(); 
		 mapGXLFieldNodes = new HashMap<String, GXLNode>(); 
		 
		 classes = this.designWizard.getAllClasses();
		 gxlDocument = new GXLDocument();
		 graph = new GXLGraph("graph1");
		 graph.setAllowsHyperGraphs(false);
		 graph.setEdgeIDs(false);
		 graph.setEdgeMode("directed");
		 genarateGraphElements();
		 gxlDocument.getDocumentElement().add(graph);
		 try {
			 gxlDocument.write(new File(nameFile));
		 }catch (IOException e) {
			 System.out.println("Error while writing to file: " + e);
		 }
	 }
	 
	 public void writeGxl(String nameFile, String idGraph) {
		 mapGXLPackageNode = new HashMap<String, GXLNode>();
		 mapGXLClassNodes = new HashMap<String, GXLNode>(); 
		 mapGXLMethodNodes = new HashMap<String, GXLNode>(); 
		 mapGXLFieldNodes = new HashMap<String, GXLNode>(); 
		 classes = this.designWizard.getAllClasses();
		 gxlDocument = new GXLDocument();
		 graph = new GXLGraph(idGraph);
		 graph.setAllowsHyperGraphs(false);
		 graph.setEdgeIDs(false);
		 graph.setEdgeMode("directed");
		 genarateGraphElements();
		 gxlDocument.getDocumentElement().add(graph);
		 try {
			 gxlDocument.write(new File(nameFile));
		 }catch (IOException e) {
			 System.out.println("Error while writing to file: " + e);
		 }			
	 }	
	
	private void genarateGraphElements(){
		generateNodePackages();
		generateNodeClasses();
		generateRelationPackageContainClass();
		generateRelationClassImplementsClass();
		generateRelationClassExtendsClass();
		generateRelationClassInternalClass();
		generateNodeFields();
		generateNodeMethods();
		generateRelationsFieldIsClass();
		generateRelationMethodAccessField();
		generateRelationMethodCallsMethod();
		generateRelationMethodThrowsClass();
		generateRelationMethodCatchesClass();
		generateRelationMethodReturnClass();
		generateRelationMethodReceivesClass();
	}

	
	private String getMethodName (MethodNode method) {
		String name = method.getName();
		
		if (!method.isConstructor()) return name;
		String classShortName = "";
		if (method.getClassNode() == null) classShortName = getClassShortName(method);
		else classShortName = method.getClassNode().getShortName();
		
		return name.replace("<init>", classShortName);
	}
	
	private String getMethodShortName (MethodNode method) {
		String name = getMethodName(method);
		int pos = name.lastIndexOf("(");

		String args = name.substring(pos + 1, name.length() - 1);
		String shortName = name.substring(0, pos);
		
		int posAux = shortName.lastIndexOf(".");
		if (posAux >= 0 ) {
			shortName = shortName.substring(posAux+1, shortName.length());
		}
		
		String aux = "(" + getSimpleArgs(args) + ")";
		
		return shortName + aux;
	}
	
	
	private String getSimpleArgs (String args) {
		String[] split = args.split(",");
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < split.length-1; i++) {
			result.append(split[i].substring(split[i].lastIndexOf(".") + 1));
			result.append(",");
		}
		result.append(split[split.length - 1].substring(split[split.length - 1].lastIndexOf(".") + 1));
		return result.toString();
	}
	
	private String getClassShortName (MethodNode method) {
		int pos1 = method.getName().lastIndexOf("(");
		String aux = method.getName().substring(0, pos1);
		
		int pos2 = aux.lastIndexOf(".");
		aux = aux.substring(0, pos2);
		pos2 = aux.lastIndexOf(".");
		if (pos2 > 0) {
			return aux.substring(pos2 + 1);
		}
		return aux;
	}
	

	private void generateNodePackages(){
		Collection<PackageNode> allPackageNodes = this.designWizard.getAllPackages();
		String idPackage;
		GXLNode nodeGXLPackage;
		GXLString valueLabelPackage;
		GXLString valueType;
		GXLString valueShortLabelPackage;
		for(PackageNode packagee: allPackageNodes){
			idPackage = prefixPackage + id;
			nodeGXLPackage = createGXLNode(idPackage);
			valueLabelPackage = new GXLString(packagee.getName());
			valueShortLabelPackage = new GXLString(packagee.getShortName());
			valueType = new GXLString("package");
			nodeGXLPackage.setAttr("type", valueType);
			nodeGXLPackage.setAttr("label", valueLabelPackage);
			nodeGXLPackage.setAttr("shortlabel", valueShortLabelPackage);
			id++;
			mapGXLPackageNode.put(packagee.getName(), nodeGXLPackage);
			graph.add(nodeGXLPackage);
		}
	}
	
	private void generateRelationPackageContainClass(){
		Collection<PackageNode> allPackageNodes = this.designWizard.getAllPackages();
		for(PackageNode packagee : allPackageNodes){
			Set<ClassNode> listClassNode = packagee.getAllClasses();
			GXLNode gxlNodePackage = mapGXLPackageNode.get(packagee.getName());
			for(ClassNode classInPackage : listClassNode){
				GXLNode gxlNodeClassInPackage = mapGXLClassNodes.get(classInPackage.getClassName());
				createEdge(gxlNodePackage, gxlNodeClassInPackage, "contains");
			}
		}
	}
		
	private void generateNodeClasses(){
		List<Object> listGXLClassNode = new LinkedList<Object>();
		String idClass;
		GXLNode nodeGXLClass;
		GXLString valueLabelClass;
		GXLString valueShortLabelClass;
		GXLString valueAcessClass;
		GXLString valueType;
		for (ClassNode classs: classes) {
			 idClass = prefixClass + id;
			 nodeGXLClass = createGXLNode(idClass);
			 valueType = new GXLString("class");
			 valueLabelClass = new GXLString(classs.getClassName());
			 valueShortLabelClass = new GXLString(classs.getShortName());
			 valueAcessClass = new GXLString(classs.getVisibility().toString().toLowerCase());
			 if(valueAcessClass.getValue().equals("package"))
				 valueAcessClass.setValue("friendly");
			 nodeGXLClass.setAttr("type", valueType);
			 nodeGXLClass.setAttr("label", valueLabelClass);
			 nodeGXLClass.setAttr("shortlabel", valueShortLabelClass);
			 nodeGXLClass.setAttr("access", valueAcessClass);
			 if (classs.isAbstract())
				 nodeGXLClass.setAttr("abstract", new GXLString("y"));
			 if (classs.isInterface())
				 nodeGXLClass.setAttr("interface", new GXLString("y"));
			 if (classs.isInnerClass())
				 nodeGXLClass.setAttr("inner", new GXLString("y"));
			 if (classs.isAnonymous())
				 nodeGXLClass.setAttr("anonymous", new GXLString("y"));
			 listGXLClassNode.add(classs);
			 listGXLClassNode.add(nodeGXLClass);
			 mapGXLClassNodes.put(classs.getClassName() , nodeGXLClass);
			 graph.add(nodeGXLClass);
			 id++;
		}
	}
	
	private void generateNodeFields(){
		for(ClassNode classNode: classes){
			Set<FieldNode> allFields = classNode.getAllFields();
			GXLNode nodeGXLclass = mapGXLClassNodes.get(classNode.getName());
			for(FieldNode field : allFields){
				String idfield = prefixField + id;
				GXLNode nodeGXLField = createGXLNode(idfield);
				GXLString valueType = new GXLString("field");
				GXLString valueLabelField = new GXLString(field.getName());
				GXLString valueShortLabelField = new GXLString(field.getShortName());
				GXLString valueAccessf = new GXLString(field.getVisibility().toString().toLowerCase());
				nodeGXLField.setAttr("type", valueType);
				nodeGXLField.setAttr("label", valueLabelField);
				nodeGXLField.setAttr("shortlabel", valueShortLabelField);
				nodeGXLField.setAttr("access", valueAccessf);
				if (field.isStatic())
					nodeGXLField.setAttr("static", new GXLString("y"));
				id++;
				mapGXLFieldNodes.put(field.getName(), nodeGXLField);
				graph.add(nodeGXLField);
				createEdge(nodeGXLclass, nodeGXLField, "contains");
			}
		}
	}
	
	
	
	private void generateNodeMethods(){
		for(ClassNode classNode: classes){
			Set<MethodNode> allMethods = classNode.getAllMethods();
			GXLNode nodeGXLclass = mapGXLClassNodes.get(classNode.getName());
			for(MethodNode method : allMethods){
				String idMethod = prefixMethod + id;
				try {
					GXLNode nodeGXLMethod = createGXLNode(idMethod);
					GXLString valueType = new GXLString("method");
					
					String name = getMethodName(method);
					String shorName = getMethodShortName(method);
					
					
					GXLString valueLabelMethod = new GXLString(name);
					GXLString valueShortLabelMethod = new GXLString(shorName);
					nodeGXLMethod.setAttr("type", valueType);
					nodeGXLMethod.setAttr("label", valueLabelMethod);
					nodeGXLMethod.setAttr("shortlabel", valueShortLabelMethod);
					if (method.getVisibility() != null) {
						GXLString valueMAccess = new GXLString(method.getVisibility().toString()
								.toLowerCase());
						nodeGXLMethod.setAttr("access", valueMAccess);
					}
					if (method.isStatic())
						nodeGXLMethod.setAttr("static", new GXLString("y"));
					mapGXLMethodNodes.put(name, nodeGXLMethod);
					graph.add(nodeGXLMethod);
					id++;
					createEdge(nodeGXLclass, nodeGXLMethod, "contains");
				} catch (NullPointerException e) {
					System.out.println(method.getName());
				}
				
			}
		}
	}
		
	private void generateRelationClassImplementsClass(){
		for(ClassNode classNode: classes){
			GXLNode gxlNodeClass = mapGXLClassNodes.get(classNode.getClassName());
			Set<ClassNode> ImplementedsInterfaces = classNode.getImplementedInterfaces();
			for(ClassNode ImplementedInterface : ImplementedsInterfaces){
				if(mapGXLClassNodes.containsKey(ImplementedInterface.getClassName())){
				GXLNode gxlNodeImplementedInterface = mapGXLClassNodes.get(ImplementedInterface.getClassName());
				createEdge(gxlNodeClass, gxlNodeImplementedInterface, "implements");
				}
			}
		}
	}
	
	private void generateRelationClassExtendsClass(){
		for(ClassNode classNode: classes){
			GXLNode gxlNodeClass = mapGXLClassNodes.get(classNode.getClassName());
			ClassNode superClass = classNode.getSuperClass();
			if(superClass != null)
				if(mapGXLClassNodes.containsKey(superClass.getClassName())){
					GXLNode gxlsuperClass = mapGXLClassNodes.get(superClass.getClassName());
					createEdge(gxlNodeClass, gxlsuperClass, "extends");
			}
		}
	}
	
	private void generateRelationClassInternalClass() {
		for(ClassNode classNode: classes) {
			GXLNode gxlNodeClass = mapGXLClassNodes.get(classNode.getClassName());
			Set<ClassNode> innerClasses = classNode.getInnerClasses();
			if(innerClasses.size() > 0){
				for (ClassNode innerClass : innerClasses) {
					GXLNode gxlNodeInnerClass = mapGXLClassNodes.get(innerClass.getClassName());
					createEdge(gxlNodeClass, gxlNodeInnerClass, "contains");
				}
			}
		}
		
	}
	
	private void generateRelationMethodAccessField(){
		for(ClassNode classNode: classes){
			Set<MethodNode> allMethods = classNode.getAllMethods();
			for(MethodNode method : allMethods){
				
				String name = getMethodName(method);
				
				GXLNode GXLMethodNode = mapGXLMethodNodes.get(name);
				Set<FieldNode> allAccessedFieldNodes = method.getAccessedFields();
				for(FieldNode field : allAccessedFieldNodes){
					if(mapGXLFieldNodes.containsKey(field.getName())){
						GXLNode GXLFieldNode = mapGXLFieldNodes.get(field.getName());
						createEdge(GXLMethodNode, GXLFieldNode, "accesses");
					}
				}
			}
		}
	}
	
	private void generateRelationMethodCallsMethod(){
		for(ClassNode classNode: classes){
			Set<MethodNode> allMethods = classNode.getAllMethods();
			for(MethodNode method : allMethods){
				String name = getMethodName(method);
				
				GXLNode GXLMethodNode = mapGXLMethodNodes.get(name);
				Set<MethodNode> allCallesMethodNodes = method.getCalleeMethods();
				for(MethodNode calledMethod : allCallesMethodNodes){
					
					String calledName = getMethodName(calledMethod);
					
					
					if(mapGXLMethodNodes.containsKey(calledName)){
						GXLNode GXLCalledMethodNode = mapGXLMethodNodes.get(calledName);
						createEdge(GXLMethodNode, GXLCalledMethodNode, "calls");
					}
				}
			}
		}
	}
	
	private void generateRelationMethodThrowsClass(){
		for(ClassNode classNode: classes){
			Set<MethodNode> allMethods = classNode.getAllMethods();
			for(MethodNode method : allMethods){
				
				String name = getMethodName(method);
				
				GXLNode GXLMethodNode = mapGXLMethodNodes.get(name);
				Set<ClassNode> allThrowsClassNodes = method.getThrownExceptions();
				for(ClassNode throwsClassNode : allThrowsClassNodes){
					if(mapGXLClassNodes.containsKey(throwsClassNode.getName())){
						GXLNode GXLThrowsClassNode = mapGXLClassNodes.get(throwsClassNode.getName());
						createEdge(GXLMethodNode, GXLThrowsClassNode,"throws");
					}
				}
			}
		}
	}
	
	private void generateRelationMethodCatchesClass(){
		for(ClassNode classNode: classes){
			Set<MethodNode> allMethods = classNode.getAllMethods();
			for(MethodNode method : allMethods){
				
				
				String name = getMethodName(method);
				
				GXLNode GXLMethodNode = mapGXLMethodNodes.get(name);
				Set<ClassNode> allCatchedClassNodes = method.getCatchedExceptions();
				for(ClassNode catchedClassNode : allCatchedClassNodes){
					if(mapGXLClassNodes.containsKey(catchedClassNode.getName())){
						GXLNode GXLcatchedClassNode = mapGXLClassNodes.get(catchedClassNode.getName());
						createEdge(GXLMethodNode, GXLcatchedClassNode, "catches");
					}
				}
			}
		}
	}
	
	private void generateRelationMethodReturnClass() {
		for(ClassNode classNode: classes){
			
			Set<MethodNode> allMethods = classNode.getAllMethods();
			for(MethodNode method : allMethods){
				
				String name = getMethodName(method);
				
				GXLNode GXLMethodNode = mapGXLMethodNodes.get(name);
				if(mapGXLClassNodes.containsKey(method.getReturnType().getName())){
					GXLNode GXLReturnedClassNode = mapGXLClassNodes.get(method.getReturnType().getName());
					createEdge(GXLMethodNode, GXLReturnedClassNode, "returns");
				}
	
			
			}
			
		}		
	}
	
	private void generateRelationsFieldIsClass() {
		for(ClassNode classNode: classes){
			Set<FieldNode> allFields = classNode.getAllFields();
			for(FieldNode field : allFields){
				GXLNode GXLFieldNode = mapGXLFieldNodes.get(field.getName());
				if(mapGXLClassNodes.containsKey(field.getType().getName())){
					GXLNode GXLClassNode = mapGXLClassNodes.get(field.getType().getName());
					createEdge(GXLFieldNode, GXLClassNode, "is");
				}
			}
		}
	}
	
	private void generateRelationMethodReceivesClass() {
		for(ClassNode classNode: classes){
			Set<MethodNode> allMethods = classNode.getAllMethods();
			for(MethodNode method : allMethods){
				
				String name = getMethodName(method);
				
				Set<ClassNode> parameters = method.getParameters();
				if(parameters != null){
					for(ClassNode parameter : parameters){
						GXLNode GXLMethodNode = mapGXLMethodNodes.get(name);
						if(mapGXLClassNodes.containsKey(parameter.getName())){
							GXLNode GXLClassNode = mapGXLClassNodes.get(parameter.getName());
							createEdge(GXLMethodNode, GXLClassNode, "receives");
						}
					}
				}
				
				
			}
		}
	}
	
	
	private GXLNode createGXLNode(String id){
		return new GXLNode(id);
	}
	 
	private GXLEdge createGXLEdge(GXLGraphElement source, GXLGraphElement target){
		return new GXLEdge(source, target);
	}
	
	private void createEdge(GXLNode node1, GXLNode node2, String typeRelation){
		String idedge = prefixRelation + id;
		GXLEdge edge = createGXLEdge(node1, node2);
		edge.setID(idedge);
		GXLString GXLtypeRelation = new GXLString(typeRelation);
		edge.setAttr("type", GXLtypeRelation);
		graph.add(edge);
		id++;
	}
	
	public void generateGXLFile(String nameFile){
		
		this.writeGxl(nameFile);
	
	}
	
	public void generateGXLFile(String nameFile, String idGraph){
	
		this.writeGxl(nameFile, idGraph);
	
	}
	
	
	public static void main(String[] args) throws IOException {
		
		GXLUtil gxlUtil = new GXLUtil(new DesignWizard("lib/gxl.jar"));
		gxlUtil.generateGXLFile("Teste.gxl");
	
	}

	
}
