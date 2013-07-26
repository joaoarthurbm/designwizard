
public class SingletonNonStaticField {

	public SingletonNonStaticField sbf1;
	
	private SingletonNonStaticField() {
		
	}
	
	public static SingletonNonStaticField getInstance() {
		return new SingletonNonStaticField();
	}
	
}
