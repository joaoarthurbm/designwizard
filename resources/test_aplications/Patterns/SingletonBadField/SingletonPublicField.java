
public class SingletonPublicField {

	public static SingletonPublicField sbf1;
	
	private SingletonPublicField() {
		
	}
	
	public static SingletonPublicField getInstance() {
		if (sbf1 == null) {
			sbf1 = new SingletonPublicField();
		}
		return sbf1;
	}
	
}
