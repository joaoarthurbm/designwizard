package pack2;

import pack1.Avo3;
import pack1.Filho;
import pack1.Pai;

public class Avo3User2 {

	public void metodo1() {
		Avo3 a3 = new Pai();
		a3.metodo1();
		int cinco = a3.CINCO;
	}
	
	public void metodo2() {
		Pai p = new Pai();

		((Avo3)p).metodo1();
		int cinco = ((Avo3)new Pai()).CINCO;

		Filho f = new Filho();

		((Avo3)f).metodo1();
		cinco = ((Avo3)new Pai()).CINCO;
	}
}
