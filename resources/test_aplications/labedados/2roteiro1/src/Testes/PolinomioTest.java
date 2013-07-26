package Testes;

import Logica.Polinomio;

import junit.framework.TestCase;

public class PolinomioTest extends TestCase {
	
	public void testCalculaPolinomio(){
		Polinomio polinomio = new Polinomio("3x2","x");
		assertEquals("12", polinomio.calculaPolinomio(2));
		
		polinomio = new Polinomio("2x","x");
		assertEquals("4", polinomio.calculaPolinomio(2));
		
		polinomio = new Polinomio("2","x");
		assertEquals("2", polinomio.calculaPolinomio(2));
		
		polinomio = new Polinomio("-2","x");
		assertEquals("-2", polinomio.calculaPolinomio(2));
		
		polinomio = new Polinomio("3x2 + 2x","x");
		assertEquals("16", polinomio.calculaPolinomio(2));
		
		polinomio = new Polinomio("3x2 + 2x","x");
		assertEquals("0", polinomio.calculaPolinomio(0));
		
		polinomio = new Polinomio("3x2 + 2x","x");
		assertEquals("5", polinomio.calculaPolinomio(1));
		
		polinomio = new Polinomio("x + x","x");
		assertEquals("6", polinomio.calculaPolinomio(3));
		
		polinomio = new Polinomio("-3x4 + 2 + 2x2 +  4x + x","x");
		assertEquals("6", polinomio.calculaPolinomio(1));
	}
	
	public void testOrdemDecrescente(){
		Polinomio polinomio = new Polinomio("3x2 + 2x3","x");
		polinomio.ordemDecrescente();
		assertEquals("2x3+3x2", polinomio.toString());
		
		polinomio = new Polinomio("15x20 + 2x3","x");
		polinomio.ordemDecrescente();
		assertEquals("15x20+2x3", polinomio.toString());
		
		polinomio = new Polinomio("229x30 + 300x30 + 22x29 + 1","x");
		polinomio.ordemDecrescente();
		assertEquals("300x30+229x30+22x29+1x0", polinomio.toString());
		
		polinomio = new Polinomio("7x1 + 2x3","x");
		polinomio.ordemDecrescente();
		assertEquals("2x3+7x1", polinomio.toString());
		
		polinomio = new Polinomio("5x + 2 + 2x3 -2","x");
		polinomio.ordemDecrescente();
		assertEquals("2x3+5x1+2x0-2x0", polinomio.toString());
		
		polinomio = new Polinomio("2 + 2 -2","x");
		polinomio.ordemDecrescente();
		assertEquals("2x0+2x0-2x0", polinomio.toString());
		
		polinomio = new Polinomio("-2 +2 -2 + 2 -2","x");
		polinomio.ordemDecrescente();
		assertEquals("2x0+2x0-2x0-2x0-2x0", polinomio.toString());
		
		polinomio = new Polinomio("-2 -2 +2 + 2 -2","x");
		polinomio.ordemDecrescente();
		assertEquals("2x0+2x0-2x0-2x0-2x0", polinomio.toString());
		
		polinomio = new Polinomio("-7x3 + 2x2 + 2x3","x");
		polinomio.ordemDecrescente();polinomio.ordemDecrescente();
		assertEquals("-7x3+2x3+2x2", polinomio.toString());
	}
	
	public void testReduza(){
		Polinomio polinomio = new Polinomio("3x3 + 3x3 + 2x2 + 2x2 + x + x","x");
		polinomio.reduza();
		assertEquals("6x3 + 4x2 + 2x", polinomio.toString());
		
		polinomio = new Polinomio("3x3 + 3x3 + 2x2 + 2x2 + x","x");
		polinomio.reduza();
		assertEquals("6x3 + 4x2 + x", polinomio.toString());
		
		polinomio = new Polinomio("x + 1","x");
		polinomio.reduza();
		assertEquals("x + 1", polinomio.toString());
		
		polinomio = new Polinomio("1","x");
		polinomio.reduza();
		assertEquals("1", polinomio.toString());
		
		polinomio = new Polinomio("x2","x");
		polinomio.reduza();
		assertEquals("x2", polinomio.toString());
		
		polinomio = new Polinomio("2x2 - 1x2 + 2x - 3x","x");
		polinomio.reduza();
		assertEquals("x2 - x", polinomio.toString());
		
		polinomio = new Polinomio("0x5","x");
		polinomio.reduza();
		assertEquals("0", polinomio.toString());
		
		polinomio = new Polinomio("-7x3 + 2x2 + 2x3","x");
		polinomio.reduza();
		assertEquals("- 5x3 + 2x2", polinomio.toString());
		
		polinomio = new Polinomio("x3 + 4x2 + 2x3 - 1x2","x");
		polinomio.reduza();
		assertEquals("3x3 + 3x2", polinomio.toString());
		
		polinomio = new Polinomio("5x + 2 + 2x3 -2","x");
		polinomio.reduza();
		assertEquals("2x3 + 5x", polinomio.toString());
		
		polinomio = new Polinomio("5x + 2x3 + 5x2 + 3x3","x");
		polinomio.reduza();
		assertEquals("5x3 + 5x2 + 5x", polinomio.toString());
		
		polinomio = new Polinomio("- x - x - x + 4x","x");
		polinomio.reduza();
		assertEquals("x", polinomio.toString());
		
		polinomio = new Polinomio("6x1 - 3x3 - 4 + 1x2","x");
		polinomio.reduza();
		assertEquals("- 3x3 + x2 + 6x - 4", polinomio.toString());
		
		polinomio = new Polinomio("-2 + 5 + x2 - 2x3 - 5x","x");
		polinomio.reduza();
		assertEquals("- 2x3 + x2 - 5x + 3", polinomio.toString());
		
		polinomio = new Polinomio("-2 + 5 + x2 - x3 - 5x","x");
		polinomio.reduza();
		assertEquals("- x3 + x2 - 5x + 3", polinomio.toString());

		polinomio = new Polinomio("x2 + x2 + x2 + x2 + x2","x");
		polinomio.reduza();
		assertEquals("5x2", polinomio.toString());
	}
	
	public void testMinimizaPolinomio(){
		Polinomio polinomio = new Polinomio("1x1","x");
		polinomio.minimizaPolinomio();
		assertEquals("x", polinomio.toString());
		
		polinomio = new Polinomio("1x10","x");
		polinomio.minimizaPolinomio();
		assertEquals("x10", polinomio.toString());
		
		polinomio = new Polinomio("0x10","x");
		polinomio.minimizaPolinomio();
		assertEquals("0", polinomio.toString());
		
		polinomio = new Polinomio("10x0","x");
		polinomio.minimizaPolinomio();
		assertEquals("10", polinomio.toString());
		
		polinomio = new Polinomio("x0","x");
		polinomio.minimizaPolinomio();
		assertEquals("1", polinomio.toString());
		
		polinomio = new Polinomio("0x0","x");
		polinomio.minimizaPolinomio();
		assertEquals("0", polinomio.toString());
		
		polinomio = new Polinomio("10x1","x");
		polinomio.minimizaPolinomio();
		assertEquals("10x", polinomio.toString());
		
		polinomio = new Polinomio("0x1","x");
		polinomio.minimizaPolinomio();
		assertEquals("0", polinomio.toString());
		
		polinomio = new Polinomio("1","x");
		polinomio.minimizaPolinomio();
		assertEquals("1", polinomio.toString());
		
		polinomio = new Polinomio("x3","x");
		polinomio.minimizaPolinomio();
		assertEquals("x3", polinomio.toString());
		
		polinomio = new Polinomio("3x1","x");
		polinomio.minimizaPolinomio();
		assertEquals("3x", polinomio.toString());
		
		polinomio = new Polinomio("x","x");
		polinomio.minimizaPolinomio();
		assertEquals("x", polinomio.toString());
		
		polinomio = new Polinomio("3x1","x");
		polinomio.minimizaPolinomio();
		assertEquals("3x", polinomio.toString());
		
		polinomio = new Polinomio("6x1 - 3x3 - 4 + 1x2","x");
		polinomio.minimizaPolinomio();
		assertEquals("6x - 3x3 - 4 + x2", polinomio.toString());
		
		polinomio = new Polinomio("x + 1","x");
		polinomio.minimizaPolinomio();
		assertEquals("x + 1", polinomio.toString());
		
		polinomio = new Polinomio("1","x");
		polinomio.minimizaPolinomio();
		assertEquals("1", polinomio.toString());
		
	}
}
