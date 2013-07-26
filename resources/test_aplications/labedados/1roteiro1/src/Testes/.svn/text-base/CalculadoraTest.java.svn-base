package Testes;

import junit.framework.TestCase;
import Controladora.Calculadora;

public class CalculadoraTest extends TestCase {
	
	public void testTrataEntrada(){
		Calculadora calculadora = new Calculadora();
		
		assertEquals("ok", calculadora.trataEntrada("def p(x) = 3x2 + 2x"));
		assertEquals("bye", calculadora.trataEntrada("exit"));
		assertEquals("", calculadora.trataEntrada("qualquer coisa"));
		
		calculadora.trataEntrada("def p(x) = 3x2 + 2x");
		assertEquals("p(x) = 3x2 + 2x", calculadora.trataEntrada("print p"));
		
		calculadora.trataEntrada("def casa( x ) = 3x2 + 2x");
		assertEquals("casa(x) = 3x2 + 2x", calculadora.trataEntrada("print casa"));
		
		calculadora.trataEntrada("def p(x) = 5x+    2x3    -   5x2 + 5");
		assertEquals("p(x) = 5x + 2x3 - 5x2 + 5", calculadora.trataEntrada("print p"));
		
		calculadora.trataEntrada("def p(x) =   6x     +5x  +    2x3    -   5x2+ 5");
		assertEquals("p(x) = 6x + 5x + 2x3 - 5x2 + 5", calculadora.trataEntrada("print p"));
		
		calculadora.trataEntrada("def p(x) = 3");
		assertEquals("p(x) = 3", calculadora.trataEntrada("print p"));
		
		calculadora.trataEntrada("def p(x) = ");
		assertEquals("p(x) = 0", calculadora.trataEntrada("print p"));
		
		calculadora.trataEntrada("def p(x) = -             2  ");
		assertEquals("p(x) = -2", calculadora.trataEntrada("print p"));
		
		calculadora.trataEntrada("def p(x) = -             2 + 2x  ");
		assertEquals("p(x) = -2 + 2x", calculadora.trataEntrada("print p"));
		
		calculadora.trataEntrada("def p(x) =    3x2    + 2x");
		assertEquals("p(x) = 3x2 + 2x", calculadora.trataEntrada("print p"));
		
		calculadora.trataEntrada("def p(x) = - 5x5 -2x3 ");
		assertEquals("p(x) = -5x5 - 2x3", calculadora.trataEntrada("print p"));
		
		calculadora.trataEntrada("def p(x) = 3x2 + 2x ");
		assertEquals("p(x) = 3x2 + 2x", calculadora.trataEntrada("print p"));
		assertEquals("16", calculadora.trataEntrada("print p(2)"));
		assertEquals("0", calculadora.trataEntrada("print p(0)"));

		calculadora.trataEntrada("def p(x) = 5x + 2x3 + 5x2");
		assertEquals("p(x) = 5x + 2x3 + 5x2", calculadora.trataEntrada("print p"));
		calculadora.trataEntrada("def p1(x) = 5x + 2x3 + 5x2 + 3x3");
		assertEquals("p1(x) = 5x + 2x3 + 5x2 + 3x3", calculadora.trataEntrada("print p1"));
		calculadora.trataEntrada("def p(x) = 5x+    2x3    +   5x2");
		assertEquals("p(x) = 5x + 2x3 + 5x2", calculadora.trataEntrada("print p"));
		calculadora.trataEntrada("def p1(x) =     -  50x5 -2x3");
		assertEquals("p1(x) = -50x5 - 2x3", calculadora.trataEntrada("print p1"));
		
		assertEquals("ok", calculadora.trataEntrada("def p(x) = -7x3 + 2x2 + 2x3"));
		assertEquals("ok", calculadora.trataEntrada("reduza p"));
		assertEquals("p(x) = -5x3 + 2x2", calculadora.trataEntrada("print p"));
		
		assertEquals("ok", calculadora.trataEntrada("def p(x) = x3 + 4x2 + 2x3 - 1x2"));
		assertEquals("ok", calculadora.trataEntrada("reduza p"));
		assertEquals("p(x) = 3x3 + 3x2", calculadora.trataEntrada("print p"));
		
		assertEquals("ok", calculadora.trataEntrada("def p(x) = 5x + 2 + 2x3 -2"));
		assertEquals("ok", calculadora.trataEntrada("reduza p"));
		assertEquals("p(x) = 2x3 + 5x", calculadora.trataEntrada("print p"));
		
		assertEquals("ok", calculadora.trataEntrada("def p1(x) = 5x + 2x3 + 5x2 + 3x3"));
		assertEquals("ok", calculadora.trataEntrada("reduza p1"));
		assertEquals("p1(x) = 5x3 + 5x2 + 5x", calculadora.trataEntrada("print p1"));
		
		assertEquals("ok", calculadora.trataEntrada("def p2(x) = 6x1 - 3x3 - 4 + 1x2"));
		assertEquals("ok", calculadora.trataEntrada("reduza p2"));
		assertEquals("p2(x) = -3x3 + x2 + 6x - 4", calculadora.trataEntrada("print p2"));
		
		assertEquals("ok", calculadora.trataEntrada("def p2(x) = -2 + 5 + x2 - 2x3 - 5x"));
		assertEquals("ok", calculadora.trataEntrada("reduza p2"));
		assertEquals("p2(x) = -2x3 + x2 - 5x + 3", calculadora.trataEntrada("print p2"));
		
		assertEquals("ok", calculadora.trataEntrada("def p2(x) = -2 + 5 + x2 - x3 - 5x"));
		assertEquals("ok", calculadora.trataEntrada("reduza p2"));
		assertEquals("p2(x) = -x3 + x2 - 5x + 3", calculadora.trataEntrada("print p2"));
		
		assertEquals("ok", calculadora.trataEntrada("def p2(x) =  -   2x0   +  5 + 1x2 - 1x3 - 5x1   "));
		assertEquals("ok", calculadora.trataEntrada("reduza p2"));
		assertEquals("p2(x) = -x3 + x2 - 5x + 3", calculadora.trataEntrada("print p2"));
		
		assertEquals("ok", calculadora.trataEntrada("def p(x) =  3x3"));
		assertEquals("p2(x) = -x3 + x2 - 5x + 3", calculadora.trataEntrada("print p2"));
		
		assertEquals("ok", calculadora.trataEntrada("def p(x) =  5"));
		assertEquals("p(x) = 5", calculadora.trataEntrada("print p"));
		
		assertEquals("ok", calculadora.trataEntrada("def p(x) = 0x5"));
		assertEquals("ok", calculadora.trataEntrada("reduza p"));
		assertEquals("p(x) = 0", calculadora.trataEntrada("print p"));
	}
}
