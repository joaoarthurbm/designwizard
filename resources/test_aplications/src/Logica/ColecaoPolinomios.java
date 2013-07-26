
package Logica;

import java.util.*;

/**
 * Classe cria uma colecao de polinomios 
 * @author Allysson Makens
 * @author Carla Souza
 */ 
public class ColecaoPolinomios {
	
	Map <String,Polinomio> colecaoPolinomios;
	
	/**
	 * Construtor de uma colecao de polinomios
	 */
	public ColecaoPolinomios(){
		colecaoPolinomios = new HashMap <String,Polinomio>();
	}
	
	/**
	 * Adiciona polinomio na colecao
	 * @param nomePolinomio Nome do polinomio
	 * @param polinomio Polinomio a ser adicionado
	 */
	public void adicionaPolinomio(String nomePolinomio, Polinomio polinomio){
		colecaoPolinomios.put(nomePolinomio, polinomio);
	}
	
	/**
	 * Pesquisa polinomio
	 * @param nome Nome do polinomio a ser pesquisado
	 * @return O polinomio pesquisado
	 */
	public Polinomio retornaPolinomio(String nome){
		return colecaoPolinomios.get(nome);
	}

}
