import junit.framework.TestCase;

public class CalculadoraPolinomialTest extends TestCase {
public void testPolinomioEhValido(){
		
		assertTrue(Polinomio.polinomioEhValido("f(x)=          5x2 + 3x"));
		assertTrue(Polinomio.polinomioEhValido("f(x)= 0"));
		assertTrue(Polinomio.polinomioEhValido("meupolinomio(variavel)= variavel2 + 5variavel3 + 3"));
		assertTrue(Polinomio.polinomioEhValido("f(x)=+5x+9+25x235+99-525"));
		assertTrue(Polinomio.polinomioEhValido("f(x)=-5x+9+25x235+99-525"));
		assertFalse(Polinomio.polinomioEhValido("f(x) = 5y2"));
		assertFalse(Polinomio.polinomioEhValido("f =x2"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x++9"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x+-9"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x+/9"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x+*9"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x-*9"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x-/9"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x--9"));
		assertFalse(Polinomio.polinomioEhValido("f(x) 4x2"));
		assertFalse(Polinomio.polinomioEhValido("f(x)== 3x2"));
		assertFalse(Polinomio.polinomioEhValido("f(x)"));
		assertFalse(Polinomio.polinomioEhValido("f(x)="));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5xxx3"));
		assertFalse(Polinomio.polinomioEhValido("(x)="));
		assertFalse(Polinomio.polinomioEhValido("x=x3"));
		assertFalse(Polinomio.polinomioEhValido(""));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x--9"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x+9+25x235+99-525=2"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x+9+25x235+99-525+"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5x+9+25x235+99*525"));
		assertFalse(Polinomio.polinomioEhValido("meupolinomio(variavel)= variaveisl2 + 5variavel3 + 3"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5,5x+9+25x235+99-525"));
		assertFalse(Polinomio.polinomioEhValido("f(x)=5.5x+9+25x235+99-525"));
	}
	
	public void testGetChave(){
	
		assertEquals("f",Logica.getChave("f(x)=x2"));
		assertEquals("doido",Logica.getChave("doido(x)=x2"));
		assertEquals("eu",Logica.getChave("eu(x)=x2"));
		assertEquals("falecido",Logica.getChave("falecido(x)=x2"));
		assertEquals("f",Logica.getChave("f"));
		assertEquals("f",Logica.getChave("    f   "));
		assertEquals("x",Logica.getChave(("x")));
		
	}
	
	public void testGetPolinomioModificado(){
		assertEquals("5 2 3 3 9 1 10 0 -25 5 1 1", 
				Polinomio.getCoeficientesExpoentes("x","5x2   +3x3+  9x+10 - 25x5 +x"));
		assertEquals("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1", 
				Polinomio.getCoeficientesExpoentes("x","x+x+x+x+x+x+x+x+x+x+x+x"));
		assertEquals("5 2 -3 3 -9 1 -10 0 -25 5 -1 1", 
				Polinomio.getCoeficientesExpoentes("x","5x2   -3x3-  9x-10 - 25x5 -x"));
		assertEquals("5 2 -3 3 -9 1 -10 0 -25 5 -1 1 15 0", 
				Polinomio.getCoeficientesExpoentes("x","5x2   -3x3-  9x-10 - 25x5 -x + 15"));
		assertEquals("15 0 5 2 -3 3 -9 1 -10 0 -25 5 -1 1 15 0", 
				Polinomio.getCoeficientesExpoentes("x","15   + 5x2   -3x3-  9x-10 - 25x5 -x + 15"));
		assertEquals("15 0 5 2 -3 3 -9 1 -10 0 -25 0 25 5 -1 1 15 0", 
				Polinomio.getCoeficientesExpoentes("x","15   + 5x2   -3x3-  9x-10 -25+ 25x5 -x + 15"));
		assertEquals("6 1 -3 3 -4 0 1 2", 
				Polinomio.getCoeficientesExpoentes("x","6x1 - 3x3 - 4 + 1x2"));
		assertEquals("-6 1 -3 3 -4 0 1 2", 
				Polinomio.getCoeficientesExpoentes("x","-6x1 - 3x3 - 4 + 1x2"));
		assertEquals("1 1 -3 3 -4 0 1 2", 
				Polinomio.getCoeficientesExpoentes("x","x - 3x3 - 4 + 1x2"));
		assertEquals("-1 1 -3 3 -4 0 1 2", 
				Polinomio.getCoeficientesExpoentes("x","-x - 3x3 - 4 + 1x2"));
//		assertEquals("-1 1 -3 3 -4 0 1 2", 
//				Polinomio.getCoeficientesExpoentes("x","25x2 + 23x + 290"));
		
	}
	
	public void testOrganizaPrint(){
		assertEquals("p1(x) = -5x5 - 2x3", Logica.organizaPrint("p1(x)= - 5x5 -2x3"));
		assertEquals("p(x) = 5x + 2x3 + 5x2", Logica.organizaPrint("p(x) =5x+    2x3    +   5x2"));
		assertEquals("p(x) = x - x3 - x - 2", Logica.organizaPrint("p(x) =x-    x3    -   x - 2"));
	}
	
	@SuppressWarnings("unchecked")
	public void testToString(){
		Polinomio teste = new Polinomio("p(x)=25x2 + 35x + 290 - 12x");
		Polinomio p;
		assertEquals("(x) = 25x2 + 23x + 290",teste.toString());
		teste = new Polinomio("p(x) = - 3x3");
		assertEquals("(x) = -3x3",teste.toString());
		teste = new Polinomio("p(x) = 6x1 - 3x3 - 4 + 1x2");
		assertEquals("(x) = -3x3 + x2 + 6x -4",teste.toString());
		teste = new Polinomio("p(x) = -6x1 - 3x3 - 4 - 1x2");
		assertEquals("(x) = -3x3 -x2 -6x -4",teste.toString());
		teste = new Polinomio("p(x) = -61 - 33 - 4 - 12");
		assertEquals("(x) = -110",teste.toString());
		teste = new Polinomio("p(x) = 100 - 0x");
		assertEquals("(x) = 100",teste.toString());
		p = new Polinomio("p(x) = 3x2 + 2x");
		assertEquals("(x) = 3x2 + 2x",p.toString());
		p = new Polinomio("p(x) =7x4+90x+2x2");
		assertEquals("(x) = 7x4 + 2x2 + 90x",p.toString());
		p = new Polinomio("p(x) =-  7x4  +  90x + 2x2");
		assertEquals("(x) = -7x4 + 2x2 + 90x",p.toString());
		p = new Polinomio("p(x) =0");
		assertEquals("(x) = 0",p.toString());
		p = new Polinomio("p(x) =2x3 + x - 13");
		assertEquals("(x) = 2x3 + x -13",p.toString());
		p = new Polinomio("p(x) =1 + 0x");
		assertEquals("(x) = 1",p.toString());
		p = new Polinomio("p(x) =0 + 0x - 0x2 - 0x3");
		assertEquals("(x) = 0",p.toString());
		p = new Polinomio("p(x) =0 +    0x - 1x0 -     1x3");
		assertEquals("(x) = -x3 -1",p.toString());
		p = new Polinomio("p(x) =6x1 - 3x3 - 4 + 1x2");
		assertEquals("(x) = -3x3 + x2 + 6x -4",p.toString());
	

		
	}
	
	public void testFassaOperacao(){
		Logica teste = new Logica();
		assertEquals("ok",teste.fassaOperacao("def p(x) = 5x"));
//		assertEquals("ok",teste.fassaOperacao("def p1(z) = p(z) + 3x + 5"));
//		assertEquals("ok",teste.fassaOperacao("def p1(z) = p(x) + 2x2"));
		assertEquals("ok",teste.fassaOperacao("reduza p"));
		assertEquals("p(x) = 5x",teste.fassaOperacao("print p"));
		assertEquals("p(1) = 5",teste.fassaOperacao("print p(1)"));
		assertEquals("ok",teste.fassaOperacao("def p(c) = 5c + 2c"));
		assertEquals("p(c) = 5c + 2c",teste.fassaOperacao("print p"));
		assertEquals("ok",
				teste.fassaOperacao("def p10(x) = -x5 + 4x4-3x2+5x3-4x3+2x-10"));
		assertEquals("p10(x) = -x5 + 4x4 - 3x2 + 5x3 - 4x3 + 2x - 10",
				teste.fassaOperacao("print p10"));
		assertEquals("ok",teste.fassaOperacao("reduza p10"));
		assertEquals("p10(x) = -x5 + 4x4 + x3 - 3x2 + 2x - 10",teste.fassaOperacao("print p10"));
		assertEquals("p10(1) = -7",teste.fassaOperacao("print p10(1)"));
		assertEquals("bye",teste.fassaOperacao("EXIT"));
		assertEquals("Erro: Entrada Incorreta",teste.fassaOperacao("print p()"));
		assertEquals("ok",teste.fassaOperacao("def p(x) = 3x2 + 2x"));
		assertEquals("p(2) = 16",teste.fassaOperacao("print p(2)"));
		assertEquals("p(0) = 0",teste.fassaOperacao("print p(0)"));
		assertEquals("ok",teste.fassaOperacao("def p(x) = 5x2+9+7x"));
		assertEquals("p(-3) = 33",teste.fassaOperacao("print p(-3)"));
		assertEquals("ok",teste.fassaOperacao("def p(x) = -5x2-9-7x"));
		assertEquals("p(-3) = -33",teste.fassaOperacao("print p(-3)"));
		assertEquals("p(0) = -9",teste.fassaOperacao("print p(0)"));
		assertEquals("ok",teste.fassaOperacao("def p(x) = 7x"));
		assertEquals("p(0) = 0",teste.fassaOperacao("print p(0)"));
		assertEquals("p(2) = 14",teste.fassaOperacao("print p(2)"));
	}
	
	
	
	
	
}

