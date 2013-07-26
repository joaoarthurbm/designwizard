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
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * A classe ExecutaFuncoes cria um objeto do tipo mapaPolinomial, onde
 * os polinomios poderao ser acessados, e possui métodos que tratam
 * os objetos, ou seja, os polinomios desse mapa.
 * que permite a ela ter metodos de acesso a polinomios
 * @author Magno Jefferson de Souza Queiroz (20421004)
 * @author Gustavo Pereira de Farias Lima (20421027)
 * @version 1.0 <br>
 *          Copyright (C) 2006 Universidade Federal de Campina Grande.
 */
public class ExecutaFuncoes {

	public HashMap<String,Object> mapaPolinomial = new HashMap<String,Object>();
	Polinomio polinomio = new Polinomio();
	Auxiliar aux = new Auxiliar();
	ManipulaPolinomios manipulador = new ManipulaPolinomios();
	
	/**
	 * Construtor Vazio para a Classe ExecutaFuncoes
	 */	
	public ExecutaFuncoes() {

	}
	
	/**
	 * Coloca um polinomio no mapa dado seu nome e o proprio polinomio 
	 * @param key o nome do polinomio
	 * @param obj o polinomio a ser adicionado no mapa
	 */	
	public void botaNoMapa(String key, Object obj){
		mapaPolinomial.put(key, obj);
	}
	
	
	/**
	 * Pega um polinomio do mapa dado seu nome 
	 * @param key o nome do polinomio
	 */	
	public Object pegaDoMapa(String key){
		return mapaPolinomial.get(key);
	}

	/**
	 * Retorna qual a funcao que o usuario deseja realizar
	 * @param comando
	 * @return o especifico comando
	 */	
	public String retornaFuncao(String comando) {
		StringTokenizer str = new StringTokenizer(comando);
		return str.nextToken();
	}
	
	/**
	 * Retorna um polinomio apos achar seu nome
	 * e procura-lo no mapaPolinomial
	 * @param segToken
	 * @return o polinomio
	 */	
	public String retornaPolinomio(String segToken) {
		int indexAbreParenteses = segToken.indexOf("(");
		String nomePoli = segToken.substring(0,indexAbreParenteses);
		
		String polinomio = (String)mapaPolinomial.get(nomePoli);
		int indexIgual = polinomio.indexOf("=");
		polinomio = polinomio.substring(indexIgual+2);
		return polinomio;
	}
	
	
	/**
	 * Multiplica um polinomio por outro
	 * @param nomePoli1 o nome de um polinomio a ser multiplicado 
	 * @param nomePoli2 o nome de um polinomio a ser multiplicado 
	 * @param varPoliEsq representa a variavel a ser utilixada no polinomio final
	 * @param nomePoliEsq eh o nome do polinomio resultante
	 * @return poliTerminado, o polinomio resultante 
	 */	
	public String multiplicaPolinomios(String nomepoli1, String nomepoli2, String varPoliEsq, String nomePoliEsq) {
		String poli1 = (String)pegaDoMapa(nomepoli1);
		String poli2 = (String)pegaDoMapa(nomepoli2);
		String varPoli1 = retornaVarDadoNome(nomepoli1);
		String varPoli2 = retornaVarDadoNome(nomepoli2);
		poli1 = poli1.replace(varPoli1,varPoliEsq);
		poli2 = poli2.replace(varPoli2, varPoliEsq);
		// agora tenho os 2 polinomios com a variavel que quero!

		// agora coloca-se os coeficientes e expoentes de cada um 
		// dos polinômios em arrays, dpois é só invocar o método somaMonomios.
		ArrayList arrayPoli1 = new ArrayList();
		ArrayList arrayPoli2 = new ArrayList();
		
		arrayPoli1 = polinomio.minimizaPolinomio2(poli1, varPoliEsq);	
		arrayPoli2 = polinomio.minimizaPolinomio2(poli2 , varPoliEsq);

		// agora tenho poli1 e poli2 em arrays...
		
		String poliTerminado = manipulador.montaPoliMultiplicado(arrayPoli1, arrayPoli2, varPoliEsq, nomePoliEsq);
			
		return poliTerminado;
	}

	
	/**
	 * Retorna a variavel do polinomio dado seu nome.
	 * Uma busca é feita no mapaPolinomial, atraves de seu nome
	 * e em seguida retorna-se a variavel do mesmo.
	 * @param nomePoli
	 * @return a variavel procurada.
	 */	
	public String retornaVarDadoNome(String nomePoli) {
		String polinomio = (String)mapaPolinomial.get(nomePoli);
		int abreparenteses = polinomio.indexOf("(");
		int fechaParenteses = polinomio.indexOf(")");	
		return polinomio.substring(abreparenteses+1, fechaParenteses);
	}

	
	/**
	 * Aceita ou nao a definicao de um polinomio,
	 * em caso positivo insere-o em um Map.
	 * @param comando
	 * @param nomePoli
	 * @param var
	 * @return A String "OK" se a operacao foi realizada com sucesso
	 */
	public String executaDef(String comando, String nomePoli, String var) {
		String novoPolinomio = comando.substring(4);
		String poliOrganizado = polinomio.espacaPolinomio(novoPolinomio); 
		try {
			mapaPolinomial.put(nomePoli, poliOrganizado);
			return "ok";
		} catch (Exception e) {
			return "Polinômio incorreto";
		}
	}

	
	/**
	 * Imprime um polinomio definido pelo usuario
	 * @param nomePoli
	 * @return O polinomio a ser impresso
	 */
	public String executaPrint(String nomePoli) {
		if (mapaPolinomial.containsKey(nomePoli))
			return (String) mapaPolinomial.get(nomePoli);
		return "Esse polinômio não existe!";
	}

	/**
	 * Reduz, ou minimiza um polinomio, ou seja, reorganiza seus termos
	 * de forma conveniente utilizando para isso um metodo da classe Polinomio
	 * @param nomePoli
	 * @param var
	 * @return Uma msensagem de OK apos reduzir o polinomio
	 */
	public String reduza(String nomePoli, String var) {

		String poli = "";
		String poliMinimizado = "";

		poli = (String) mapaPolinomial.get(nomePoli);
		poli = polinomio.espacaPolinomio(poli);
		poliMinimizado = polinomio.minimizaPolinomio(poli, var); 
		mapaPolinomial.put(nomePoli, poliMinimizado);
		return "ok";
				
	}
	

}
