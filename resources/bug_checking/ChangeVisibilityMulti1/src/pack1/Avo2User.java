package pack1;

public class Avo2User {

	public void metodo1() {
		Avo2 a2 = new Pai();
		a2.metodo1();
		int cinco = a2.CINCO;
	}
	
	public void metodo2() {
		Pai p = new Pai();

		((Avo2)p).metodo1();
		int cinco = ((Avo2)new Pai()).CINCO;

		Filho f = new Filho();

		((Avo2)f).metodo1();
		cinco = ((Avo2)new Pai()).CINCO;
	}
}
