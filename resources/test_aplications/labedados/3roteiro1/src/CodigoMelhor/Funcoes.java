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

/**
 * A classe Funcoes tem a responsabilidade
 * de verificar qual o comando dado pelo usuario no programa principal e, 
 * assim, designar quem executará esse comando.
 * Funcoes instancia um objeto da classe Polinomio e outro 
 * da classe ExecutaFuncoes, para assim poder executar o comando
 * dado pelo usuario. 
 * @author Magno Jefferson de Souza Queiroz (20421004)
 * @author Gustavo Pereira de Farias Lima (20421027)
 * @version 1.0 <br>
 *          Copyright (C) 2006 Universidade Federal de Campina Grande.
 */
public class Funcoes {
	Polinomio polinomio = new Polinomio();
	ExecutaFuncoes executa = new ExecutaFuncoes();
	Auxiliar aux = new Auxiliar();
	ManipulaPolinomios manipulador = new ManipulaPolinomios();
	

	/**
	 * Construtor Vazio para a Classe Funcoes
	 */	
	public Funcoes() {
		
	}
	
	/**
	 * Executa o comando dado pelo usuario e retorna uma String
	 * correspondente.
	 * @param comando
	 * @return Uma String que representa a execucao do comando dado
	 */
	public String executaComando(String comando){
		
		if (comando.equals(""))
			return "Você não digitou um comando.";

		String funcao = executa.retornaFuncao(comando);
		
		if(funcao.equals("def")){
			try {
				int result = verificaTipoDef(comando.substring(4));
				return verificaOperacaoDef(result, comando);
			} catch (Exception e) {
				return "erro: Esse comando não pode ser executado.";
			}
		}
		else if(funcao.equals("print")){
			String segToken = aux.getSegToken(comando);
			//Apenas imprime um polinomio
			if(!aux.verificaSeCalcula(segToken) ){				
				return executa.executaPrint(segToken);
			}
			//Calcula o valor de um polinomio e depois o imprime
			else {
				try {
					int valor =Integer.parseInt(aux.retornaVarDadoComando(comando));
					String nomePoli = polinomio.getNomePoli(comando);
					String var =executa.retornaVarDadoNome(nomePoli);		
					String oPolinomio = executa.retornaPolinomio(segToken);			
					return polinomio.calculaPoli(oPolinomio,valor, var) +"";
				} catch (Exception e) {
					return "erro: Esse comando não pode ser executado.";
				}
			}
		}
		else if(funcao.equals("reduza")){
			try {
				String nomePoli = aux.getSegToken(comando);
				String var = executa.retornaVarDadoNome(nomePoli);
				return executa.reduza(nomePoli, var);
			} catch (Exception e) {
				return "erro: Esse comando não pode ser executado.";
			}
		}
		else if (funcao.equals("exit")){
			return "bye";
		}
		
		return "COMANDO INVÁLIDO";
	}
	

	/**
	 * Verifica qual o tipo correto de operacao a ser realizada de acordo
	 * com o tipo da definicao
	 * @param result informa o tipo de operacao
	 * @param comando representa todo o comando dado pelo usuario
	 */
	private String verificaOperacaoDef(int result, String comando) {
		String nomePoliEsq = polinomio.getNomePoli(comando);
		int indexIgual = comando.indexOf("=");
		String depoisDoIgual = comando.substring(indexIgual+1);
		depoisDoIgual = depoisDoIgual.trim();
		String var = "";
		String poliMultiplicado = "";
		int escalar = 0;
		switch (result) {
		case 0:
			String nomePoli = polinomio.getNomePoli(comando);
			var = aux.retornaVarDadoComando(comando);
			return executa.executaDef(comando, nomePoli, var);

		case 1:
			try {
				String varPoliEsq = aux.retornaVarDadoComando(comando);
				String nomePoliDir = polinomio.getNomePoli("token "+depoisDoIgual);
				String poliDepoisIgual = executa.retornaPolinomio(depoisDoIgual);
				String varPoliDir = executa.retornaVarDadoNome(nomePoliDir); 
				String polinomioMontado = nomePoliEsq+"("+varPoliEsq+") = "+poliDepoisIgual.replace(varPoliDir,varPoliEsq);
				executa.botaNoMapa(nomePoliEsq,polinomioMontado);
				executa.reduza(nomePoliEsq, varPoliEsq);
				String oComando = "def " + executa.pegaDoMapa(nomePoliEsq); 
				
				return executa.executaDef(oComando, nomePoliEsq, varPoliEsq);
				
			} catch (Exception e) {
				// faz nada!!
			}

			
		case 2:
			var = aux.retornaVarDadoComando(comando);
			
			boolean ehNegativo = sinalEhNegativo(depoisDoIgual);
			if(ehNegativo){
				escalar = -1;
				String nomePoliMultiplicador = aux.retornaNomePoli(depoisDoIgual);
				String trechoExpulso = "";
				trechoExpulso = "-"+nomePoliMultiplicador+"("+var+")";
				String parteMultip = multiplicaPoli(escalar, nomePoliMultiplicador, var);
				parteMultip = aux.juntaTudo(parteMultip);
				parteMultip = tiraExcessoDeSinais(parteMultip);
				depoisDoIgual = aux.juntaTudo(depoisDoIgual);
				depoisDoIgual = depoisDoIgual.replace(trechoExpulso, parteMultip);
				depoisDoIgual = tiraExcessoDeSinais(depoisDoIgual);
				String poliPronto = "def "+nomePoliEsq+"("+ var +") = "+polinomio.espacaPolinomio(depoisDoIgual);
				executa.botaNoMapa(nomePoliEsq, poliPronto.substring(4));
				executa.reduza(nomePoliEsq, var);
				String poliReduzido = (String)executa.pegaDoMapa(nomePoliEsq);
				return executa.executaDef("def "+poliReduzido, nomePoliEsq, var);
			}
			
			String poliPronto = "def "+nomePoliEsq+"("+ var +") = "+somaPoliComMonomios(depoisDoIgual);
			executa.botaNoMapa(nomePoliEsq, poliPronto.substring(4));
			executa.reduza(nomePoliEsq, var);
			String poliReduzido = (String)executa.pegaDoMapa(nomePoliEsq);
			return executa.executaDef("def "+poliReduzido, nomePoliEsq, var);
			
			
		case 3:
			boolean escalarVemAntes = aux.verificaPosEscalar(depoisDoIgual);
			escalar = aux.procuraEscalar(depoisDoIgual);	
			escalar = Integer.parseInt(aux.verificaEscalarEhNegativo(escalar, depoisDoIgual)+escalar);
			
			String nomePoliMultiplicador = aux.retornaNomePoli(depoisDoIgual);
			var = aux.retornaVarDadoComando(comando);
			String trechoExpulso = "";
			
			if(escalarVemAntes)
				trechoExpulso = escalar+"*"+nomePoliMultiplicador+"("+var+")";
			else
				trechoExpulso = nomePoliMultiplicador+"("+var+")*"+escalar;
			
			// ========================
			String parteMultip = multiplicaPoli(escalar, nomePoliMultiplicador, var);
			
			parteMultip = aux.juntaTudo(parteMultip);
			parteMultip = tiraExcessoDeSinais(parteMultip);
			
			depoisDoIgual = aux.juntaTudo(depoisDoIgual);
			depoisDoIgual = depoisDoIgual.replace(trechoExpulso, parteMultip);
			executa.botaNoMapa(nomePoliEsq,depoisDoIgual);
			String poliNaoEspacado = (String)executa.pegaDoMapa(nomePoliEsq);
			poliNaoEspacado = nomePoliEsq+"("+var+") = "+poliNaoEspacado;
			String poliEspacado = polinomio.espacaPolinomio(poliNaoEspacado);
			executa.botaNoMapa(nomePoliEsq, poliEspacado);
			executa.reduza(nomePoliEsq, var);
			String poliReduzido2 = (String)executa.pegaDoMapa(nomePoliEsq);
			return executa.executaDef("def "+poliReduzido2, nomePoliEsq, var);
			
			
		case 4:
			String varPoliEsq = aux.retornaVarDadoComando(comando);
			ArrayList<String> poli1Poli2 = new ArrayList<String>();
			
			poli1Poli2 = aux.retornaPoli1Poli2(depoisDoIgual);
			String nomepoli1 = poli1Poli2.get(0);
			String nomepoli2 = poli1Poli2.get(1);
			//  segunda parte...
			poliPronto = executa.multiplicaPolinomios(nomepoli1, nomepoli2, varPoliEsq, nomePoliEsq);
			executa.botaNoMapa(nomePoliEsq, poliPronto);
			executa.reduza(nomePoliEsq, varPoliEsq);
			String poliProntoReduzido = (String)executa.pegaDoMapa(nomePoliEsq);
			int z = 0;
			return executa.executaDef("def "+poliProntoReduzido, nomePoliEsq, varPoliEsq);
			
			
		default:
			
			break;
		}
		
		
		return "erro: ...";
	}

	/**
	 * 
	 * @param depoisDoIgual
	 * @return true se o sinal do escalar for negativo.
	 */
	public boolean sinalEhNegativo(String depoisDoIgual) {
		depoisDoIgual = aux.juntaTudo(depoisDoIgual);
		int posP = depoisDoIgual.indexOf("p");
		if(posP != 0 && depoisDoIgual.charAt(posP-1) == '-')
			return true;
	
		return false;
	}

	/**
	 * Recebe uma string com excesso de sinais ( ex.: 2x2 + -3 ), faz o jogo de sinais
	 * e retorna a string correta ( ex.: 2x2 - 3 )
	 * @param parteMultip
	 * @return O string recebido, sem excesso de sinais.
	 */
	public String tiraExcessoDeSinais(String parteMultip) {
		String novaString = "";
		
		for (int i = 0; i < parteMultip.length(); i++) {
		
			if(parteMultip.charAt(i) == '+'){
				if(parteMultip.charAt(i+1) == '-')
					novaString += "";
				else 
					novaString += parteMultip.charAt(i); 
			}
			else if(parteMultip.charAt(i) == '-'){
				if(parteMultip.charAt(i+1) == '-'){
					novaString += "";
				}
				else if(i!=0 && parteMultip.charAt(i-1) == '-'){
					novaString += "+";
				}
				else 
					novaString += parteMultip.charAt(i); 
			}
			 else
				novaString += parteMultip.charAt(i);
			
		}

		
		
		return novaString;
		
	}

	/**
	 * Dada uma definicao que soma Polinomio a monomios, esse metodo faz a soma
	 * e retorna o polinomio resultante
	 * @param depoisDoIgual que possui o polinomio e os monomios a serem somados
	 * @return poliResult como sendo o polinomio resultante da soma
	 */
	private String somaPoliComMonomios(String depoisDoIgual) {
		int posP = depoisDoIgual.indexOf("p");
		int posAbreParenteses = depoisDoIgual.indexOf("(");
		int posFechaParenteses = depoisDoIgual.indexOf(")");
		String nomePoli = depoisDoIgual.substring(posP, posAbreParenteses);
		String poli = (String)executa.pegaDoMapa(nomePoli); // Poli a ser somado
		// achar poliDepoisDoIgual
		int indexIgual = poli.indexOf("=");
		String poliDepoisDoIgual = poli.substring(indexIgual+1);
		poliDepoisDoIgual = poliDepoisDoIgual.trim();
		// Achar p(x) p/ replace por poliDepoisDoIgual 
		String paraTrocar = depoisDoIgual.substring(posP, posFechaParenteses+1);
		String poliResult = depoisDoIgual.replace(paraTrocar, poliDepoisDoIgual);
		int z= 1;
		return poliResult;
	}
		
	/**
	 * Verifica qual tipo de Definicao deve ser realizada
	 * @param comando
	 * @return int que representa o tipo de definicao
	 */
	public int verificaTipoDef(String comando) {
		int indexIgual = comando.indexOf("=");
		String depoisDoIgual = comando.substring(indexIgual+1);
		int quantParenteses = aux.contaParenteses(depoisDoIgual);
		if(!depoisDoIgual.contains("p") && !depoisDoIgual.contains("*"))
			return 0; // def normal...
		else if(depoisDoIgual.contains("p") &&  !depoisDoIgual.contains("+")
				&&  !depoisDoIgual.contains("-")&&  !depoisDoIgual.contains("*")){
			return 1; // def do tipo p1(x) = p2(x)
		}
		else if(!depoisDoIgual.contains("*")){
			return 2; // soma monomios...
		}
		else if(depoisDoIgual.contains("*") && (quantParenteses >= 2)){
			return 4; // Multiplica dois Polinomios
		}
		
		else if(depoisDoIgual.contains("*") && ( quantParenteses == 1)){
			return 3; 
		}				
		
		return -1;
	}

	
	/**
	 * Multiplica um polinomio por um escalar
	 * @param escalar que multiplicara o polinomio
	 * @param nomePoliMult indica o nome do pilinomio a ser multiplicado
	 * @param var representa a variavel do pilinomio a ser multiplicado
	 * @return poliResultante que eh o resultado da multiplicacao do polinomio
	 * cujo nome foi passado por parametro e o escalar
	 */
	public String multiplicaPoli(int escalar, String nomePoliMult, String var) {
		String poliMult = (String)executa.pegaDoMapa(nomePoliMult);
		String varPoliMult = executa.retornaVarDadoNome(nomePoliMult);
		poliMult = poliMult.replace(varPoliMult,var);
		int posIgual = poliMult.indexOf("=");
		String poliDepoisIgual = poliMult.substring(posIgual+1);
		poliDepoisIgual.trim();
		StringTokenizer str = new StringTokenizer(poliDepoisIgual);
		String token = "";
		String coef = "";
		String poliResultante = "";
		int posVar = 0;
		while(str.hasMoreTokens()){
			coef = "";
			token = str.nextToken();
				
			if(token.equals("+"))
				poliResultante += " + ";
			else if(token.equals("-"))
				poliResultante += " - ";
			else{
				try {
					poliResultante += Integer.parseInt(token)*escalar+"";
					
				} catch (Exception e) {
					try {
						posVar = token.indexOf(var);
						coef = token.substring(0, posVar);
						String aux = Integer.parseInt(coef)*escalar+"";
						poliResultante += token.replaceFirst(coef, aux);
						
					} catch (Exception f) {
						poliResultante += escalar+token;
					}					
				}			
			}		
		}
			
		return poliResultante;
	}
}

	 