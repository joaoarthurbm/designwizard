public class SubClass extends SuperClass {
	
	private String id = "oi";
	
	public SubClass() {
		System.out.println(id);
		System.out.println(this.id);
		System.out.println(super.id);
		System.out.println(this.name);
		System.out.println(super.name);
	}
}
