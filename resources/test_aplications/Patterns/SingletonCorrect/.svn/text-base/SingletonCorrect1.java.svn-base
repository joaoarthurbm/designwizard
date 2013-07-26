
public class SingletonCorrect1 {
	
	private static SingletonCorrect1 instance;
	
	private SingletonCorrect1() {
		
	}
	
	public static synchronized SingletonCorrect1 getInstance() {
		if (SingletonCorrect1.instance == null) {
			SingletonCorrect1.instance = new SingletonCorrect1();
		}
		return instance;
	}
}
