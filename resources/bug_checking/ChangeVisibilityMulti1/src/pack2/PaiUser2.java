package pack2;

import pack1.Avo1;
import pack1.Avo2;
import pack1.Avo3;
import pack1.Filho;
import pack1.Pai;

public class PaiUser2 {

	public void metodo1() {
		new Filho().metodo1();
		int cinco = new Filho().CINCO;
		
		Pai p = new Pai();
		((Pai)p).metodo1();
		
		Avo1 a1 = new Pai();
		((Pai)a1).metodo1();

		Avo2 a2 = new Pai();
		((Pai)a2).metodo1();

		Avo3 a3 = new Pai();
		((Pai)a3).metodo1();
	}
}
