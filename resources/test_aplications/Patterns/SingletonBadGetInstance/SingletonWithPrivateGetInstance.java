public class SingletonWithPrivateGetInstance {

	private static SingletonWithPrivateGetInstance instance;
	
	private SingletonWithPrivateGetInstance() {
		
	}
	
	private static SingletonWithPrivateGetInstance getInstance() {
		if (instance == null) {
			instance = new SingletonWithPrivateGetInstance();
		}
		return instance;
	}
}
