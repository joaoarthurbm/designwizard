import java.io.Serializable;


public class SingletonBadConstructor implements Cloneable,Serializable{

	private static SingletonBadConstructor sw1;
	
	public SingletonBadConstructor() {
	}
	
	public static SingletonBadConstructor getInstance() {
		if (sw1 == null) {
			sw1 = new SingletonBadConstructor();
		}
		return sw1;
	}
	
}
