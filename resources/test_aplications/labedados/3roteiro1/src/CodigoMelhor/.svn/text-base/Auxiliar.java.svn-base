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
package CodigoMelhor;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Auxiliar {
	
	
	public Auxiliar() {

	}
	
	/**
	 * Método que recebe um String e retorna a mesma, porém sem espaço
	 * algum entre seus caracteres.
	 * @param depoisDoIgual
	 * @return uma String sem espaços entre seus caracteres.
	 */
	public String juntaTudo(String depoisDoIgual) {
		String semEspacos = "";
		for (int i = 0; i < depoisDoIgual.length(); i++) {
			if(depoisDoIgual.charAt(i) != ' ')
				semEspacos += depoisDoIgual.charAt(i);
		}
		return semEspacos;
	}

	/**
	 * Método que procura e retorna o escalar que multiplica um polinômio.
	 * @param depoisDoIgual
	 * @return O escalar que multiplica o polinômio.
	 */
	public int procuraEscalar(String depoisDoIgual) {
		String semEspacos = "";
		semEspacos = juntaTudo(depoisDoIgual);
		
		StringTokenizer str = new StringTokenizer(semEspacos, "+ - *", true);
		String token1 = "";
		String token2 = "";
		token1 = str.nextToken();
		token2 = str.nextToken();
		while(str.hasMoreTokens()){
			try {
				
				if(token2.equals("*")){
					try {
						return Integer.parseInt(token1);
					} catch (Exception e) {
						token2 = str.nextToken();
						return Integer.parseInt(token2);
					}
				}
				else{
					token1 = token2;
					token2 = str.nextToken();
				}
				
			} catch (Exception e) {
				//faz nada!
			}
			
		}
		
		return -1;
	}
	
	/**
	 * Retorna a variavel do polinomio dado um comando
	 * que o contem.
	 * @param comando
	 * @return a variavel procurada.
	 */	
	public String retornaVarDadoComando(String comando) {
		StringTokenizer str = new StringTokenizer(comando);
		str.nextToken();
		String segundoToken = str.nextToken();
		int abreparenteses = segundoToken.indexOf("(");
		int fechaParenteses = segundoToken.indexOf(")");
		return segundoToken.substring(abreparenteses+1, fechaParenteses);
	}
	
	/**
	 * Verifica se o escalar que multiplica o polinômio é negativo.
	 * @param escalar
	 * @param depoisDoIgual
	 * @return
	 */
	public String verificaEscalarEhNegativo(int escalar, String depoisDoIgual) {
		String semEspacos = "";
		semEspacos = juntaTudo(depoisDoIgual);
		int posNum = semEspacos.indexOf(escalar+"");
		try {
			if((semEspacos.charAt(posNum-1)+"").equals("-") )
				return "-";
		} catch (Exception e) {
			// faz nada!!
		}
		
		return "";
	}
	/**
	 * Verifica se o escalar que multiplica o polinomio vem antes ou após
	 * o sinal de vezes (*).
	 * @param depoisDoIgual
	 * @return
	 */
	public boolean verificaPosEscalar(String depoisDoIgual) {
		int posVezes = depoisDoIgual.indexOf("*");
		if(depoisDoIgual.substring(posVezes).contains("p"))
			return true;
		
		return false;
	}
	
	/**
	 * Recebe uma string que contém 2 polinômios como substring e retorna 
	 * os nomes destes polinômios.
	 * @param depoisDoIgual
	 * @return Os nomes dos dois polinômios.
	 */
	public ArrayList<String> retornaPoli1Poli2(String depoisDoIgual) {
		ArrayList<String> nomes = new ArrayList<String>();
		StringTokenizer str = new StringTokenizer(depoisDoIgual,"*",false);
		String nomePoli1 = str.nextToken();
		String nomePoli2 = str.nextToken();
		
		int posP = nomePoli1.indexOf("p");
		int posAbrePar = nomePoli1.indexOf("(");
		nomePoli1 = nomePoli1.substring(posP, posAbrePar);
		
		posP = nomePoli2.indexOf("p");
		posAbrePar = nomePoli2.indexOf("(");
		nomePoli2 = nomePoli2.substring(posP, posAbrePar);

		nomes.add(nomePoli1);
		nomes.add(nomePoli2);
		
		return nomes;
	}
	
	/**
	 * Dado um comando print, verifica se deve calcular o valor do polinomio
	 * para ser impresso.
	 * @param segToken
	 * @return true se o polinomio deve ser calculado e false caso contrario
	 */
	public boolean verificaSeCalcula(String segToken) {
		
		try {
			int abreparenteses = segToken.indexOf("(");
			int fechaParenteses = segToken.indexOf(")");	
			Integer.parseInt(segToken.substring(abreparenteses+1, fechaParenteses));
			return true;
		} catch (Exception e) {
			// faz nada!
		}
		return false;
	}
	
	/**
	 * Busca no mapa polinomial e retorna o nome do polinomio presente na String.
	 * @param depoisDoIgual
	 * @return nome do polinomio.
	 */
	public String retornaNomePoli(String depoisDoIgual) {
		int posP = depoisDoIgual.indexOf("p");
		int posAbreParenteses = depoisDoIgual.indexOf("(");
		return depoisDoIgual.substring(posP, posAbreParenteses);
	}
	
	/**
	 * 
	 * @param depoisDoIgual
	 * @return escalar.
	 */
	public int verificaAntesDepois(String depoisDoIgual) {
		
		String semEspacos = "";		
		for (int i = 0; i < depoisDoIgual.length(); i++) {
			if(depoisDoIgual.charAt(i) != ' ')
				semEspacos += depoisDoIgual.charAt(i);
		}
		
		StringTokenizer str = new StringTokenizer(semEspacos,"*");
		int num = 0;
		String token = "";
		try {
			token = str.nextToken();
			//token.trim();
			num = Integer.parseInt(token);
			return num;
		} catch (Exception e) {
			token = str.nextToken();
			//token.trim();
			num = Integer.parseInt(token);
			return num;
		}
		
	}

	/**
	 * Conta a quantidade de "(" presente em uma String.
	 * @param depoisDoIgual
	 * @return quantidade de "("
	 */
	public int contaParenteses(String depoisDoIgual) {
		int cont = 0;
		for (int i = 0; i < depoisDoIgual.length(); i++) {
			if(depoisDoIgual.charAt(i) == '(')
				cont ++;
		}
		return cont;
	}

	/**
	 * Dado um comando, retorna sua segunda parte
	 * @param comando
	 * @return Uma String que representa o segundo Token
	 */
	public String getSegToken(String comando) {
		StringTokenizer str = new StringTokenizer(comando);
		str.nextToken();
		return str.nextToken();
	}

	
}
