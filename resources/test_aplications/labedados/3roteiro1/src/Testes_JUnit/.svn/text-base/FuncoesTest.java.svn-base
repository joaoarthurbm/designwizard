
package Testes_JUnit;

import java.util.ArrayList;

import CodigoMelhor.Auxiliar;
import CodigoMelhor.ExecutaFuncoes;
import CodigoMelhor.Funcoes;
import CodigoMelhor.Polinomio;


import junit.framework.TestCase;


public class FuncoesTest extends TestCase {


	public void testExecutaComando() {
		Funcoes fun = new Funcoes();
		ExecutaFuncoes exe = new ExecutaFuncoes();
		Auxiliar aux = new Auxiliar();
		
		assertEquals("ok", fun.executaComando("def p(x) = 2x2 - 2"));
		assertEquals("ok", fun.executaComando("def p1(x) = -2*p(x)"));
		assertEquals("p1(x) = -4x2 + 4", fun.executaComando("print p1"));
		
		assertEquals("2x2+2+2-3", fun.tiraExcessoDeSinais("2x2+2--2+-3") );
		
		
		assertEquals("2x2-2", fun.tiraExcessoDeSinais("2x2+-2"));
		assertEquals("2x2+2-2-3", fun.tiraExcessoDeSinais("2x2+2-2+-3") );
		
		//assertEquals("2x2-2", fun.tiraExcessoDeSinais("2x2+-2"));
		
		assertEquals(2, aux.procuraEscalar("2 * p(x)") );
		assertEquals("-", aux.verificaEscalarEhNegativo(2,"-2 * p(x)") );
		
		assertEquals("def", exe.retornaFuncao("def p1(var) = 2var + 3"));
		assertEquals("ok", fun.executaComando("def p1(var) = 2var + 2"));
		assertEquals("p1(var) = 2var + 2", fun.executaComando("print p1"));
		assertEquals("ok", fun.executaComando("def p2(y) = p1(var)"));
		assertEquals("p2(y) = 2y + 2", fun.executaComando("print p2"));
		assertEquals("ok", fun.executaComando("def p3(y) = p2(y)"));
		assertEquals("p3(y) = 2y + 2", fun.executaComando("print p3"));


		// Atribuir um Polinomio a Partir de outro! ( CASO 3)
		
		assertEquals("ok", fun.executaComando("def p1(x) = 3x + 4x2"));
		assertEquals("ok", fun.executaComando("def p2(x) = p1(x) "));
		assertEquals("ok", fun.executaComando("def p3(y) = p2(y) "));
		assertEquals("p1(x) = 3x + 4x2", fun.executaComando("print p1"));
		assertEquals("p2(x) = 4x2 + 3x", fun.executaComando("print p2 "));
		assertEquals("p3(y) = 4y2 + 3y", fun.executaComando("print p3"));
	
		// Adicionar monomios! ( CASO 3 )
		assertEquals(true, fun.sinalEhNegativo("2x2 + 2 -p(x)") );
		assertEquals("ok", fun.executaComando("def p1(x) = 3x2 + 4x"));
		assertEquals("ok", fun.executaComando("def p2(x) = p1(x) + 3x3 - x"));
		assertEquals("p1(x) = 3x2 + 4x", fun.executaComando("print p1"));
		assertEquals("p2(x) = 3x3 + 3x2 + 3x", fun.executaComando("print p2"));
		assertEquals("ok", fun.executaComando("def p3(x) = 2x2"));
		assertEquals("ok", fun.executaComando("def p4(x) = 2x+2x+2x+2x+2x+p3(x)+60x"));
		assertEquals("ok", fun.executaComando("def p(x) = 2x2 + 2"));
		assertEquals("ok", fun.executaComando("def p1(x) = 2x2 + 2 - p(x)"));
		assertEquals("0", fun.executaComando("print p1"));

		//Multiplicar Polinomio por escalar! ( CASO 3 )
		
		assertEquals("ok", fun.executaComando("def p1(x) = 3x2 + 4x"));
		assertEquals("ok", fun.executaComando("def p2(x) = 2 * p1(x)"));
		assertEquals("p2(x) = 6x2 + 8x", fun.executaComando("print p2"));
		
		assertEquals("ok", fun.executaComando("def p(x) = 2x2 + 4x + 2"));
		assertEquals("ok", fun.executaComando("def p1(x) = 3*p(x)"));
		assertEquals("p1(x) = 6x2 + 12x + 6", fun.executaComando("print p1"));
		
		assertEquals("ok", fun.executaComando("def p(x) = 2x2 + 4x"));
		assertEquals("ok", fun.executaComando("def p1(x) = 0*p(x)"));
		assertEquals("p1(x) = 0", fun.executaComando("print p1"));
		
		assertEquals("ok", fun.executaComando("def p(x) = 2x2 + 4x + 2"));
		assertEquals("ok", fun.executaComando("def p1(x) = 0*p(x)"));
		assertEquals("p1(x) = 0", fun.executaComando("print p1"));
		
		assertEquals("ok", fun.executaComando("def p(x) = 2x2 + 4x + 2"));
		assertEquals("ok", fun.executaComando("def p1(x) = p(x) * 0"));
		assertEquals("p1(x) = 0", fun.executaComando("print p1"));
		
		assertEquals("ok", fun.executaComando("def p(x) = 2x2 + 4x"));
		assertEquals("ok", fun.executaComando("def p1(x) = -2 * p(x)"));
		//assertEquals("p1(x) = -4x2 - 8x", fun.executaComando("print p1"));
		
		
		
		// caso 2 e 3 juntos

		assertEquals("ok", fun.executaComando("def p(x) = 2x2"));
		assertEquals("ok", fun.executaComando("def p1(x) = 2x2 + 2*p(x)"));
		assertEquals("p1(x) = 6x2", fun.executaComando("print p1"));
		
		assertEquals("ok", fun.executaComando("def p1(x) = 2x2 + p(x)*2"));
		assertEquals("p1(x) = 6x2", fun.executaComando("print p1"));
		
		assertEquals("ok", fun.executaComando("def p1(y) = 2y2 + 2*p(y)"));
		assertEquals("p1(y) = 6y2", fun.executaComando("print p1"));
		
		assertEquals("ok", fun.executaComando("def p(x) = 2x2"));
		
		assertEquals("ok", fun.executaComando("def p(x) = 2x2 + 3x"));
		assertEquals("ok", fun.executaComando("def p1(x) = 2x2 - 2*p(x)"));
		assertEquals("p1(x) = -2x2 + 6x", fun.executaComando("print p1"));// duvida!!!
		
		
		assertEquals("ok", fun.executaComando("def p1(x) = 3x2 + 4x"));
		assertEquals("ok", fun.executaComando("def p2(x) = 2 * p1(x) + 3x3 - 8x"));
		assertEquals("p1(x) = 3x2 + 4x", fun.executaComando("print p1"));
		assertEquals("p2(x) = 3x3 + 6x2", fun.executaComando("print p2"));
		
		
		// Multiplica Polin�mios! ( CASO 3 )
		assertEquals("ok", fun.executaComando("def p1(x) = 2x2 + 3x + 2"));
		assertEquals("ok", fun.executaComando("def p2(x) = 3x2 + 4x - 2"));
		assertEquals("ok", fun.executaComando("def p3(z) = p1(z) * p2(z)"));
		assertEquals("p3(z) = 6z4 + 17z3 + 14z2 + 2z - 4", fun.executaComando("print p3"));
		
		assertEquals("ok", fun.executaComando("def p1(x) = 0"));
		assertEquals("ok", fun.executaComando("def p2(x) = 3x2 + 4x - 2"));
		assertEquals("ok", fun.executaComando("def p3(z) = p1(z) * p2(z)"));
		assertEquals("p3(z) = 0", fun.executaComando("print p3"));
		
		assertEquals("ok", fun.executaComando("def p1(x) = 3x2 + 4x"));
		assertEquals("ok", fun.executaComando("def p2(y) = 2y + y "));
		assertEquals("ok", fun.executaComando("def p3(z) = p1(z) * p2(z)"));
		assertEquals("p3(z) = 9z3 + 12z2", fun.executaComando("print p3"));

	}
	public void testcalculaMonomios() {
		// Calculo de mon�mios
		Polinomio polinomio = new Polinomio();
		assertEquals(4,polinomio.calculaMonomio("2var", 2, "var"));
		assertEquals(9,polinomio.calculaMonomio("var2", 3, "var"));
		assertEquals(2,polinomio.calculaMonomio("2", 3, "var"));
		assertEquals(-12,polinomio.calculaMonomio("-4x", 3, "x"));
		assertEquals(20,polinomio.calculaMonomio("x", 20, "x"));
		assertEquals(9,polinomio.calculaMonomio("-y2", 3, "y"));
		assertEquals(-27,polinomio.calculaMonomio("-y3", 3, "y"));
		assertEquals(27,polinomio.calculaMonomio("-y3", -3, "y"));
		assertEquals(8,polinomio.calculaMonomio("-y3", -2, "y"));
		assertEquals(-16,polinomio.calculaMonomio("-2z3", 2, "z"));
		assertEquals(16,polinomio.calculaMonomio("-2z3", -2, "z"));
		assertEquals(-2,polinomio.calculaMonomio("-2", -2, "z"));
	}
	
	public void testOrganizaPolinomio() {
		Polinomio polinomio = new Polinomio();
		assertEquals("p(x) = 5x + 2x3 + 5x2", polinomio.espacaPolinomio("p(x) =      5x+    2x3    +   5x2    "));
		assertEquals("p(x) = -5x + 2x3 + 5x2", polinomio.espacaPolinomio("p(x) =      -5x+    2x3    +   5x2    "));
		assertEquals("p1(x) = -50x5 - 2x3", polinomio.espacaPolinomio("p1(x)= - 50x5 -2x3"));
		assertEquals("p(x) = -5x + 2x3 - 5x2", polinomio.espacaPolinomio("p(x) =   -   5x+    2x3    -5x2    "));
		assertEquals("p(x) = -3x2 + 5x - 4", polinomio.espacaPolinomio("p(x) =  -  3x2  +5x-4   "));
		assertEquals("p1(x) = -50x5 - 2x3 - 3", polinomio.espacaPolinomio("p1(x)= -50x5-2x3-3"));

	}	

	public void testSomaMonomios(){
		Polinomio polinomio = new Polinomio();
		ArrayList<Integer> arrayCoeficientes = new ArrayList<Integer>();
		ArrayList<Integer> arrayExpoentes = new ArrayList<Integer>(){};

		
		
//==========================================================================
		arrayCoeficientes.add(2);
		arrayExpoentes.add(3);
		
		arrayCoeficientes.add(7);
		arrayExpoentes.add(4);
		
		assertEquals("p(x) = 7x4 + 2x3", polinomio.somaMonomios(arrayCoeficientes, arrayExpoentes, "x", "p"));
		
//		==========================================================================		
		
		arrayCoeficientes.clear();
		arrayExpoentes.clear();
		
		arrayCoeficientes.add(-2);
		arrayExpoentes.add(2);
		arrayCoeficientes.add(3);
		arrayExpoentes.add(2);
		arrayCoeficientes.add(5);
		arrayExpoentes.add(7);
		
		assertEquals("p(x) = 5x7 + x2", polinomio.somaMonomios(arrayCoeficientes, arrayExpoentes, "x", "p"));
		
//		==========================================================================		
		
		arrayCoeficientes.clear();
		arrayExpoentes.clear();
		
		arrayCoeficientes.add(-2);
		arrayExpoentes.add(2);
		arrayCoeficientes.add(-3);
		arrayExpoentes.add(3);
		arrayCoeficientes.add(4);
		arrayExpoentes.add(5);
			
		assertEquals("p(x) = 4x5 - 3x3 - 2x2", polinomio.somaMonomios(arrayCoeficientes, arrayExpoentes, "x", "p"));
		
//		==========================================================================
		
		arrayCoeficientes.clear();
		arrayExpoentes.clear();
		
		arrayCoeficientes.add(-4);
		arrayExpoentes.add(11);
			
		assertEquals("p(y) = -4y11", polinomio.somaMonomios(arrayCoeficientes, arrayExpoentes, "y", "p"));
		
//      ==========================================================================		
		
		arrayCoeficientes.clear();
		arrayExpoentes.clear();
		
		arrayCoeficientes.add(2);
		arrayExpoentes.add(4);
		arrayCoeficientes.add(2);
		arrayExpoentes.add(1);
		arrayCoeficientes.add(-1);
		arrayExpoentes.add(3);
			
		assertEquals("p(x) = 2x4 - x3 + 2x", polinomio.somaMonomios(arrayCoeficientes, arrayExpoentes, "x", "p"));
		
		
//		==========================================================================
		
		arrayCoeficientes.clear();
		arrayExpoentes.clear();
		

		arrayCoeficientes.add(2);
		arrayExpoentes.add(2);
		arrayCoeficientes.add(2);
		arrayExpoentes.add(2);
		arrayCoeficientes.add(2);
		arrayExpoentes.add(2);
		arrayCoeficientes.add(2);
		arrayExpoentes.add(2);
		arrayCoeficientes.add(2);
		arrayExpoentes.add(2);
		arrayCoeficientes.add(2);
		arrayExpoentes.add(2);
			
		assertEquals("p(x) = 12x2", polinomio.somaMonomios(arrayCoeficientes, arrayExpoentes, "x", "p"));
		
	}

	public void testMinimizaPolinomio(){
		Polinomio polinomio = new Polinomio();	
		assertEquals("p(x) = -5x3 + 2x2", polinomio.minimizaPolinomio("p(x) = -7x3 + 2x2 + 2x3", "x"));
		assertEquals("p(x) = 3x3 + 3x2", polinomio.minimizaPolinomio("p(x) = x3 + 4x2 + 2x3 - 1x2", "x"));
		assertEquals("p(x) = 2x3 + 5x", polinomio.minimizaPolinomio("p(x) = 5x + 2 + 2x3 -2", "x"));
		assertEquals("p1(x) = 5x3 + 5x2 + 5x", polinomio.minimizaPolinomio("p1(x) = 5x + 2x3 + 5x2 + 3x3", "x"));
		assertEquals("p2(x) = -3x3 + x2 + 6x - 4", polinomio.minimizaPolinomio("p2(x) = 6x1 - 3x3 - 4 + 1x2", "x"));
		assertEquals("p2(x) = -2x3 + x2 - 5x + 3", polinomio.minimizaPolinomio("p2(x) = -2 + 5 + x2 - 2x3 - 5x", "x"));
		assertEquals("p2(x) = -x3 + x2 - 5x + 3", polinomio.minimizaPolinomio("p2(x) = -2 + 5 + x2 - x3 - 5x", "x"));
		
	}
	
	public void testCalculaPoli(){
		Polinomio polinomio = new Polinomio();	
		assertEquals(12, polinomio.calculaPoli("2var2 + 2var",2,"var"));
		assertEquals(84, polinomio.calculaPoli("3x3 + 3",3,"x"));
		assertEquals(42, polinomio.calculaPoli("2x3 - 3x - 3",3,"x"));
		assertEquals(54, polinomio.calculaPoli("2x3 - 3x - 3 + 2 + 1 + 3x",3,"x"));
	}
	
	public void testA(){}
	
}
