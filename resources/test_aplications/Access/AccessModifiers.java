import java.util.HashSet;
import java.util.Set;


public class AccessModifiers {

	
	/*
	 * 
	 * all visibilities for static methods
	 */
	public static String pus(String[] args) {
		return "";
	}
	private static Set<String> pris(){
		return new HashSet<String>();
	}
	protected static void pros(){}
	static void fris(){}

	/*
	 * all visibilities for non static methods
	 * */
	public void pub(){}
	private void pri(){}
	protected void pro(){}
	void fri(){}
}