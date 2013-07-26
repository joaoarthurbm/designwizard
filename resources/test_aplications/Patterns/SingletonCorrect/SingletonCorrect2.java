
public class SingletonCorrect2 {
	
	private static SingletonCorrect2 instance;
	
	private SingletonCorrect2() {
		
	}
	
	protected static SingletonCorrect2 getInstance() {
		if (SingletonCorrect2.instance == null) {
			SingletonCorrect2.instance = new SingletonCorrect2();
		}
		return instance;
	}
}
