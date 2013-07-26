import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarFile;


public class ClassWithManyCatchBlocks {
	
	public static void main(String args[]) {
		try {
			System.out.println("");
		} catch (NumberFormatException e) {
			// TODO: handle exception
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
	}
	
	public ClassWithManyCatchBlocks() {
		try {
			JarFile f = new JarFile("");
			f.close();
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	private int catchRuntimeException() {
		try {
			System.out.println();
			this.methodThrows();
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		} catch (MyException e) {
			
		}
		return 1;
	}
	public void methodThrows() throws MyException {
		try {
			JarFile j = new JarFile("");
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
