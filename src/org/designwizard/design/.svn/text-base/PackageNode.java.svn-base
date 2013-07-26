package org.designwizard.design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.designwizard.design.relation.Relation;
import org.designwizard.design.relation.Relation.TypesOfRelation;

/**
* <code>PackageNode</code> objects are constructed automatically by the <code>DesignWizard</code> class when classes
* are loaded. To get access to a desired package extracted, do not use the constructor of this class. Instead, use the
* class <code>DesignWizard</code> as it follows:
* 
* <blockquote><pre>
*  		DesignWizard dw = new DesignWizard("/home/user/application/classes");
*		PackageNode c = dw.getPackage("foo.bar.mypackage")
* </pre></blockquote>
* 
* Instances of the class <code>PackageNode</code> represent packages in 
* the code extracted.
*/
public class PackageNode extends AbstractEntity implements Entity {

	public PackageNode(String entity) {
	
		super.name = entity;
		super.type = TypesOfEntities.PACKAGE;
		super.relations = new HashMap<TypesOfRelation,Set<Relation>>();
	
	}

	@Override
	public String getClassName() {
	
		// TODO Auto-generated method stub
		return "";
	
	}

	@Override
	public ClassNode getClassNode() {
	
		// TODO Auto-generated method stub
		return null;
	
	}

	@Override
	public List<String[]> getImpactOfAChange() {

		return new ArrayList<String[]>();
	
	}

	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes inside the package represented by this <code>PackageNode</code>.
	 * @return a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes inside the package represented by this <code>PackageNode</code>.
	 */
	public Set<ClassNode> getAllClasses() {

		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		Set<Relation> relations = super.getRelations(TypesOfRelation.CONTAINS);

		for (Relation relation: relations) {

			Entity node = relation.getCalledEntity();

			if (node.getTypeOfEntity().equals(TypesOfEntities.CLASS)) {
				
				feedBack.add((ClassNode) node);
			
			}
			
		}
	
		return feedBack;
	
	}
	
	@Override
	/**
	 * @return This object.
	 */
	public PackageNode getPackage() {
		
		return this;
	
	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes that reference the package represented by this <code>PackageNode</code>.
	 */
	@Override
	public Set<ClassNode> getCallerClasses() {
	
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		Set<ClassNode> classes = this.getAllClasses();

		for (ClassNode classNode : classes) {
	
			feedBack.addAll(classNode.getCallerClasses());
	
		}
	
		return feedBack;
	
	}

	/**
	 * Returns a <code>java.util.Set</code> containing <code>ClassNode</code> objects reflecting all
	 * the classes that are referenced by the classes inside the package represented by this <code>PackageNode</code>.
	 */
	@Override
	public Set<ClassNode> getCalleeClasses() {
		
		Set<ClassNode> classes = this.getAllClasses();
		Set<ClassNode> feedBack = new HashSet<ClassNode>();
		
		for(ClassNode classNode: classes) {
		
			feedBack.addAll(classNode.getCalleeClasses());
		
		}
	
		return feedBack;
	
	}

	/**
	 * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
	 * the methods that reference the classes or interfaces inside the package
	 * represented by this <PackageNode>.
	 */
	@Override
	public Set<MethodNode> getCallerMethods() {
	
		Set<ClassNode> classes = this.getAllClasses();
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		
		for(ClassNode classNode: classes) {
		
			feedBack.addAll(classNode.getCallerMethods());
		
		}
	
		return feedBack;
	
	}
	
	@Override
	public Set<MethodNode> getCalleeMethods() {
		
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		Set<ClassNode> allClasses = this.getAllClasses();
		
		for (ClassNode c : allClasses) {
			
			feedBack.addAll(c.getCalleeMethods());
		}
		
		return feedBack;

	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>PackageNode</code> objects reflecting all
	 * the packages that reference the package represented by this <code>PackageNode</code>.
	 * @return a <code>java.util.Set</code> containing <code>PackageNode</code> objects reflecting all
	 * the packages that reference the package represented by this <code>PackageNode</code>.
	 */
	public Set<PackageNode> getCallerPackages() {
		
		Set<MethodNode> callers = this.getCallerMethods();
		Set<PackageNode> returnValue = new HashSet<PackageNode>();
		
		for (MethodNode m : callers) {
			returnValue.add(m.getPackage());
		}
		//FIXME lembrar dessa mudança: um pacote chama ele mesmo!
		returnValue.remove(this);
		
		return returnValue;
	
	}

	/**
	 * Returns a <code>java.util.Set</code> containing <code>PackageNode</code> objects reflecting all
	 * the packages that are referenced by the package represented by this <code>PackageNode</code>.
	 * @return a <code>java.util.Set</code> containing <code>PackageNode</code> objects reflecting all
	 * the packages that are referenced by the package represented by this <code>PackageNode</code>.
	 */
	public Set<PackageNode> getCalleePackages() {
		
		Set<ClassNode> called = this.getCalleeClasses();
		Set<PackageNode> returnValue = new HashSet<PackageNode>();
		
		for (ClassNode c : called) {
			returnValue.add(c.getPackage());
		}
		
		//FIXME lembrar dessa mudança: um pacote chama ele mesmo!
		returnValue.remove(this);
		
		return returnValue;
		
	}
	
	/**
	 * Returns a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
	 * the methods inside the package represented by this <code>PackageNode</code>.
	 * @return a <code>java.util.Set</code> containing <code>MethodNode</code> objects reflecting all
	 * the methods inside the package represented by this <code>PackageNode</code>.
	 */
	public Set<MethodNode> getAllMethods() {
		
		Set<MethodNode> feedBack = new HashSet<MethodNode>();
		
		for(ClassNode classInsidePackage : this.getAllClasses())
			feedBack.addAll(classInsidePackage.getAllMethods());
		
		return feedBack;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	
	public boolean equals(Object other) {
		
		if (!(other instanceof PackageNode)) return false;
		
		PackageNode aux = (PackageNode) other;
		
		return this.name.equals(aux.name);
		
	}

}
