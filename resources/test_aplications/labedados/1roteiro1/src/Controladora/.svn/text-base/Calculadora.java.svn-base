package Controladora;

import java.util.*;

import Logica.ColecaoPolinomios;
import Logica.Polinomio;

/**
 * Classe Calculadora que calcula o polinomio 
 * @author Allysson Makens
 * @author Carla Souza
 */
public class Calculadora {

	private ColecaoPolinomios colecPolin;
	
	/**
	 * Construtor de uma calculadora criando uma colecao de polinomios
	 */
	public Calculadora(){
		colecPolin = new ColecaoPolinomios();
	}
	
	
	/**
	 * Trata a entrada do usuario sendo def, print, reduza e exit
	 * @param entrada A entrada do usuario
	 * @return O resultado de acordo com a entrada do usuario
	 */
	public String trataEntrada(String entrada) {
		String saida = "";
		StringTokenizer st = new StringTokenizer(entrada.toLowerCase());
		String comando = st.nextToken();
		if (comando.equals("def")){
			saida = valida();
			String variav = retornaVariavel(entrada);
			colecPolin.adicionaPolinomio(nomePolinomio(entrada), polinomio(entrada,variav));
		}else if (comando.equals("exit")){
			saida = finaliza();
		}	
		else if (comando.equals("print")){
			try {
			saida = this.print(entrada);
			} catch( Exception e) {
				System.err.println("Erro: Polinomio nao definido.");
			}
		}
		
		else if (comando.equals("reduza")){
			try {
				String nomePolinomioPesquisado = entrada.replace("reduza", "").trim();
				st = new StringTokenizer(nomePolinomioPesquisado, "(");
				Polinomio polinomioReduzir = colecPolin.retornaPolinomio(st.nextToken());
				polinomioReduzir.reduza();
				saida = "ok";
			} catch (Exception e) {
				System.err.println("Erro: Polinomio nao definido.");
			}
			
		}
		
		return saida;
	}
	
	
	/**
	 * Trata a entrada print do usuario
	 * @param entrada A entrada do usuario
	 * @return Retorna o resultado do print pedido
	 */
	public String print(String entrada){
		String saida = "";
		String nomePolinomioPesquisado = entrada.replace("print", "").trim();
		StringTokenizer st = new StringTokenizer(nomePolinomioPesquisado, "(");
		Polinomio polinomioPesquisado = colecPolin.retornaPolinomio(st.nextToken());
		try{
			String variavel = retornaVariavel(entrada);
			saida = polinomioPesquisado.calculaPolinomio(Integer.parseInt(variavel));
		} catch (Exception e) {
			//Situacao print p
			polinomioPesquisado = formataImpressao(polinomioPesquisado);
			saida = nomePolinomioPesquisado + "("+ polinomioPesquisado.getVariavel() + ") = " + polinomioPesquisado;
		}
		return saida;
	}
	
	/**
	 * Retorna o nome do polinomio
	 * @param entrada A entrada do usuario
	 * @return O nome do polinomio
	 */
	public static String nomePolinomio(String entrada){
		String [] s = entrada.split(" ");
		return s[1].substring(0, s[1].indexOf( "(" ));
	}
	
	/**
	 * Retorna a variavel incognita do polinomio
	 * @param entrada A entrada do usuario
	 * @return A variavel incognita
	 */
	private static String retornaVariavel(String entrada){
		return entrada.substring(entrada.indexOf('(') + 1, entrada.indexOf(')')).trim();
	}
	
	/**
	 * Cria um polinomio a partir da entrada e da variavel icognita
	 * @param entrada A entrada do usuario
	 * @param var A variavel icognita
	 * @return O polinomio
	 */
	public static Polinomio polinomio(String entrada, String var){
		String[] st = entrada.split("=");
		Polinomio polinomio = new Polinomio(st[1].trim().toString(),var);
		return polinomio;
	}

	/**
	 * Formata o polinomio para impressao, exemplo: - 3x +2  para -3x + 2
	 * @param polinomio Polinomio para ser formatado
	 * @return O polinomio formatado
	 */
	public static Polinomio formataImpressao(Polinomio polinomio){
		StringTokenizer st = new StringTokenizer(polinomio.toString(),"+,-", true);
		String polinomioFormatado = "";
		if (!polinomio.equals("")){
			String primeiroToken = st.nextToken();
			if (primeiroToken.equals("-")){
				polinomioFormatado = primeiroToken.trim();
			}else{
				polinomioFormatado = primeiroToken.trim() + " ";
			}
			while (st.hasMoreTokens()){
				polinomioFormatado += st.nextToken().trim() + " ";
			}
		}
		polinomio.setPolinomio(polinomioFormatado.trim());
		return polinomio;
	}
	
	/**
	 * Valida a definicao do usuario
	 * @return ok 
	 */
	public static String valida(){
		return "ok";
	}
	
	/**
	 * Finalisa o programa
	 * @return bye
	 */
	public static String finaliza(){
		return "bye";
	}

}
