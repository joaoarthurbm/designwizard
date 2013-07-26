
/* Universidade Federal de Campina Grande
	 * Departamento de Sistemas e Computacao
	 * Curso de Ciencia da Computacao
	 * Disciplina: Laboratorio de Estrutura de Dados
	 * Professor: Dalton Serey
	 * Monitores: Flavio
	 *            Jaindson
	 * 
	 * Periodo: 2005.2
	 * Criado em: 15/04/2006
	 * 
	 * Alunos: Magno Jefferson de Souza Queiroz - Matr: 20421004
	 *         Gustavo Pereira de Farias Lima - Matr: 20421027
	 *         
	 */
	
//package CodigoMelhor;

import java.util.Scanner;
import CodigoMelhor.Funcoes;

/**a
 * Classe que contem o MAIN do programa Calculadora Polinomial. 
 * Essa classe recebe um comando escolhido pelo usuario e 
 * instancia um objeto do tipo Funcoes que tera a responsabilidade
 * de executar o comando dado.
 * 
 * @author Magno Jefferson de Souza Queiroz (20421004)
 * @author Gustavo Pereira de Farias Lima (20421027)
 * @version 1.0 <br>
 *          Copyright (C) 2006 Universidade Federal de Campina Grande.
 */
public class CalculadoraMain {
	public static void main(String[] args) {

		Funcoes fun = new Funcoes();
		Scanner sc = new Scanner(System.in);

		String comando = "";
		while(!comando.equals("exit")){
			comando = sc.nextLine();
			System.out.println(fun.executaComando(comando));
		}		
	}
}
