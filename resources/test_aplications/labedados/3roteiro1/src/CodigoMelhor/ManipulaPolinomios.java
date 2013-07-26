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

public class ManipulaPolinomios {
	
	Polinomio polinomio = new Polinomio();

    /**
	 * Construtor vazio
	 */
	public ManipulaPolinomios() {

	}
	
	/**
	 * Dados arrays que representam polinomios, com seus respectivos
	 * coeficientes e expoentes o metodo monta o polinomio resultante da
	 * multiplicacao dos dois poli8nomios passados por parametro
	 * @param arrayPoli1 o array correspondente ao polinomio 1
	 * @param arrayPoli2 o array correspondente ao polinomio 2
	 * @param varPoliEsq respresenta a variavel do polinomio resultante
	 * @param nomePoliEsq respresenta o nome do polinomio resultante
	 * @return Uma String que representa o polinomio resultante da multiplicacao 
	 */
	@SuppressWarnings("unchecked")
	public String montaPoliMultiplicado(ArrayList arrayPoli1, ArrayList arrayPoli2, String varPoliEsq, String nomePoliEsq) {
		ArrayList<Integer> coefPoli1 = new ArrayList<Integer>();  
		ArrayList<Integer> coefPoli2 = new ArrayList<Integer>();
		ArrayList<Integer> expPoli1 = new ArrayList<Integer>();
		ArrayList<Integer> expPoli2 = new ArrayList<Integer>();	
		ArrayList<Integer> coefPoliFinal = new ArrayList<Integer>();
		ArrayList<Integer> expPoliFinal = new ArrayList<Integer>();
		
		coefPoli1 = (ArrayList)arrayPoli1.get(0);
		expPoli1 = (ArrayList)arrayPoli1.get(1);		


		coefPoli2 = (ArrayList)arrayPoli2.get(0);
		expPoli2 = (ArrayList)arrayPoli2.get(1);

		
		// agora é só multiplicare montar! 		
		for (int i = 0; i < coefPoli1.size(); i++) {
			for (int j = 0; j < coefPoli2.size(); j++) {
				coefPoliFinal.add(coefPoli1.get(i)*coefPoli2.get(j));
				expPoliFinal.add(expPoli1.get(i)+expPoli2.get(j));
			}
		}
			
		String oPolinomio = polinomio.montaPolinomio(coefPoliFinal,expPoliFinal,varPoliEsq, nomePoliEsq);
		
		return oPolinomio;
	}
	
	


}
