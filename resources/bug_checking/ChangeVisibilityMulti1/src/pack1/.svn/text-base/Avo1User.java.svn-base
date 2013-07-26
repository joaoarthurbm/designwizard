package pack1;

public class Avo1User {

	public void metodo1() {
		Avo1 a1 = new Pai();
		a1.metodo1();
		int cinco = a1.CINCO;
	}
	
	public void metodo2() {
		Pai p = new Pai();

		((Avo1)p).metodo1();
		int cinco = ((Avo1)new Pai()).CINCO;

		Filho f = new Filho();

		((Avo1)f).metodo1();
		cinco = ((Avo1)new Pai()).CINCO;
	}
}
