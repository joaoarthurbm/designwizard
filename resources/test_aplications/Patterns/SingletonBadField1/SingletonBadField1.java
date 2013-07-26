
public class SingletonBadField1 {

	public static SingletonBadField1 sbf1;
	
	private SingletonBadField1() {
		
	}
	
	public static SingletonBadField1 getInstance() {
		if (sbf1 == null) {
			sbf1 = new SingletonBadField1();
		}
		return sbf1;
	}
	
}
