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
import java.util.LinkedList;
import java.util.StringTokenizer;
/**
 * A classe Polinomio implementa as funcoes sobre os objetos
 * de sua classe.
 * @author Magno Jefferson de Souza Queiroz (20421004)
 * @author Gustavo Pereira de Farias Lima (20421027)
 * @version 1.0 <br>
 *          Copyright (C) 2006 Universidade Federal de Campina Grande.
 */
public class Polinomio {
	
	/**
	 * Construtor Vazio para a Classe Polinomios
	 */	
	public Polinomio() {
		
	}
	
	/**
	 * Retorna o nome do polinomio
	 * @param comando
	 * @return o nome do polinomio
	 */
	public String getNomePoli(String comando) {
		StringTokenizer str = new StringTokenizer(comando);
		str.nextToken();
		String segundo = str.nextToken();
		int indexAbreParenteses = segundo.indexOf("(");
		return segundo.substring(0,indexAbreParenteses);
	}

	/**
	 * Provem todos os termos de um polinomio separados por apenas
	 * um espaco em branco
	 * @param polinomio
	 * @return Uma String que representa o polinomio espacado
	 */
	public String espacaPolinomio(String polinomio) {
		String poliJuntinho = "";
		for (int i = 0; i < polinomio.length(); i++) {
			if(polinomio.charAt(i) != ' ')
				poliJuntinho += polinomio.charAt(i);					
		} 
			
		StringTokenizer str = new StringTokenizer(poliJuntinho, "= + -", true);
		int numeroTokens = str.countTokens();
		String poliSemEspacos = "";
		String token = "";
		int i = 1;
		while (str.hasMoreTokens()){
			token =  str.nextToken();
			if( i == 3){
				if(token.equals("-"))
					poliSemEspacos += token;
				else
					poliSemEspacos += token + " ";
			}
			else if(i == numeroTokens){
				poliSemEspacos += token;
			}
			else
				poliSemEspacos += token + " ";
			
			i++;
		}
		 poliSemEspacos = poliSemEspacos.trim();	 
		 return poliSemEspacos;
	}
	
	/**
	 * Dado um polinomio corretamente espacado, sera retornado
	 * um polinomio convenientemente organizado, iniciando com
	 * o termo mais significativo
	 * @param poliEspacado
	 * @param var
	 * @return Uma String que representa o polinomio organizado, minimizado
	 */
	 public String minimizaPolinomio(String poliEspacado, String var) {
		 int posAbreParenteses = poliEspacado.indexOf("(");
		 String nomePoli = poliEspacado.substring(0, posAbreParenteses);
		 int posDoIgual = poliEspacado.indexOf("=");
		 String polinomio = poliEspacado.substring(posDoIgual+2); // já vem organizado, tirou o espaço dpois do igual
		 	 
		 StringTokenizer str = new StringTokenizer(polinomio);
		 ArrayList<Integer> arrayCoeficientes = new ArrayList<Integer>();
		 ArrayList<Integer> arrayExpoentes = new ArrayList<Integer>();
		 int somaInteiros = 0;
		 int coeficiente = 1;
		 int expoente = 1;
		 boolean ehInteiro = false;
		 boolean temCoeficiente = false;
		 	 
		 while(str.hasMoreTokens()){
			 coeficiente = 1;
			 expoente = 1;
			 ehInteiro = false;
			 String token = str.nextToken();
		 
			 try { // verifica se token é um numero inteiro.
				somaInteiros += Integer.parseInt(token);
				ehInteiro = true;
			 } 
			 catch (Exception e) {
				 // faz nada.
			 }
			
			if(!ehInteiro){
				if(token.equals("-")){
					token = "-"+ str.nextToken();
					try { 
							somaInteiros += Integer.parseInt(token);
							ehInteiro = true;
					} catch (Exception e) {
							//faz nada
					}
				}
				else if(token.equals("+")){
					token = str.nextToken();
					 try { 
							somaInteiros += Integer.parseInt(token);
							ehInteiro = true;	
						} catch (Exception e) {
							
						}
				}
				String numCoef = "";
				String numExp = "";
				if(!ehInteiro){
					try {
						numCoef = token.substring(0, token.indexOf(var));
						if(numCoef.equals("-"))
							numCoef = -1+"";
						coeficiente = Integer.parseInt(numCoef);

					} catch (Exception e) {
						// coeficiente continua sendo 1
					}
					
					try {
						numExp = token.substring(token.indexOf(var) + var.length());
						if(numExp.equals("0")){
							somaInteiros += coeficiente*1;
							coeficiente = 0;
						}
						else
							expoente = Integer.parseInt(numExp);

					} catch (Exception e) {
						// expoente continua sendo 1
					}	
					if(coeficiente != 0){
						arrayCoeficientes.add(coeficiente);
						temCoeficiente = true;
						arrayExpoentes.add(expoente);
					}
				}
			}
		 }
		 String poliCompleto = "";
		 if(arrayCoeficientes.size() > 0)
		 	 poliCompleto = somaMonomios(arrayCoeficientes, arrayExpoentes, var, nomePoli);
		 
		 if(somaInteiros != 0 && temCoeficiente){
			if(somaInteiros > 0)
		 		poliCompleto += " + " + somaInteiros;
			else
		 		poliCompleto += " - " + Math.abs(somaInteiros);
		 	}
		 else if(somaInteiros != 0 && !temCoeficiente){
		 		if(somaInteiros > 0)
		 			poliCompleto += somaInteiros;
		 		else
		 			poliCompleto += "-" + Math.abs(somaInteiros);
		 }		 	 	 
		 
		 if(poliCompleto.equals(""))
			 return nomePoli+"("+var+") = 0";
		 
		 return poliCompleto;
	}
	
	     /**
		 * Soma todos os monomios de um polinomio e chama o metodo
		 * montaPolinomio, que tera a funcao de montar o polinomio,
		 * dai sera retornado o valor do calculo do polinomio
		 * @param arrayCoeficientes
		 * @param arrayExpoentes
		 * @param var
		 * @param nomePoli
		 * @return O polinomio montado
		 */
		public String somaMonomios(ArrayList<Integer> arrayCoeficientes, ArrayList<Integer> arrayExpoentes, String var, String nomePoli) {
			
			for (int i = 0; i < arrayExpoentes.size()-1; i++) {
				for (int j = i+1; j < arrayExpoentes.size(); j++) {
					if(arrayExpoentes.get(i) == arrayExpoentes.get(j)){
						arrayCoeficientes.set(i,arrayCoeficientes.get(i) + arrayCoeficientes.get(j));
						arrayCoeficientes.set(j,0);
						arrayExpoentes.set(j,0);
					}
				}
			}
			int index = 0;
			while(arrayCoeficientes.contains(0)){
				index = arrayCoeficientes.indexOf(0);
				arrayCoeficientes.remove(index);
				arrayExpoentes.remove(index);			
			}

			int aux = 0;
			for (int i = arrayExpoentes.size() - 1; i > 0; i--)
			{
			      for (int j = 0; j < i; j++)
			         if (arrayExpoentes.get(j) < arrayExpoentes.get(j + 1)) {
			            aux = arrayExpoentes.get(j);
			        	arrayExpoentes.set(j, arrayExpoentes.get(j+1));
			        	arrayExpoentes.set(j+1,aux);
			        	// agora troca os coeficientes
			            aux = arrayCoeficientes.get(j);
			        	arrayCoeficientes.set(j, arrayCoeficientes.get(j+1));
			        	arrayCoeficientes.set(j+1,aux);
			         }
			   }			
			
			if(arrayCoeficientes.size() == 0)
				return "0";
			
			String oPolinomio = montaPolinomio(arrayCoeficientes, arrayExpoentes, var, nomePoli);
			
			return oPolinomio;
		}
	 
		
		/**
		 * Recebe os valores do coeficientes e dos expoentes e monta o polinomio correspondente
		 * seguindo todas as determinacoes sobre os sinais matematicos de "+" e "-".
		 * @param arrayCoeficientes
		 * @param arrayExpoentes
		 * @param var
		 * @param nomePoli
		 * @return Monta o polinomio
		 */
	public String montaPolinomio(ArrayList<Integer> arrayCoeficientes, ArrayList<Integer> arrayExpoentes, String var, String nomePoli) {
		String oPolinomio = nomePoli+"("+var+") = ";
		
		// Tratando o primeiro elemento!
		if(arrayCoeficientes.size() != 1){
			if(arrayCoeficientes.get(0) < 0){
				if(arrayCoeficientes.get(0) != -1 && arrayExpoentes.get(0) != 1)
					oPolinomio += arrayCoeficientes.get(0)+var+arrayExpoentes.get(0)+" ";
				else if (arrayCoeficientes.get(0) == -1 && arrayExpoentes.get(0) != 1)
					oPolinomio += "-"+var+arrayExpoentes.get(0)+" ";
				else if(arrayCoeficientes.get(0) != -1 && arrayExpoentes.get(0) == 1)
					oPolinomio += arrayCoeficientes.get(0)+var+" ";
				else
					oPolinomio += "-" + var+" ";		
			}
			else{
				if (arrayCoeficientes.get(0) > 0) {
					if (arrayCoeficientes.get(0) == 1 && arrayExpoentes.get(0) == 1) //
						oPolinomio += var + " ";//
					else if (arrayCoeficientes.get(0) == 1)
						oPolinomio += var + arrayExpoentes.get(0) + " ";// expoente = 1
					else if (arrayCoeficientes.get(0) != 1 && arrayExpoentes.get(0) != 1)
						oPolinomio += arrayCoeficientes.get(0) + var + arrayExpoentes.get(0) + " ";
					else if (arrayCoeficientes.get(0) == 1 && arrayExpoentes.get(0) != 1)
						oPolinomio += var + arrayExpoentes.get(0) + " ";
					else if (arrayCoeficientes.get(0) != 1 && arrayExpoentes.get(0) == 1)
						oPolinomio += arrayCoeficientes.get(0) + var + " ";
					else
						oPolinomio += var + " ";
				}
				
			}
		}
		else if(arrayCoeficientes.size() == 1){
			if(arrayCoeficientes.get(0) < 0){
				
				if(arrayCoeficientes.get(0) != -1 && arrayExpoentes.get(0) != 1)
					oPolinomio += arrayCoeficientes.get(0)+var+arrayExpoentes.get(0);
				else if(arrayCoeficientes.get(0) == -1 && arrayExpoentes.get(0) != 1)
					oPolinomio += "-"+var+arrayExpoentes.get(0);
				else if(arrayCoeficientes.get(0) != -1 && arrayExpoentes.get(0) == 1)
					oPolinomio += arrayCoeficientes.get(0)+var;
				else
					oPolinomio += "-"+ var;		
			}
			else{
				if (arrayCoeficientes.get(0) == 1 && arrayExpoentes.get(0) == 1)//
					oPolinomio += var;//aqui
				else if(arrayCoeficientes.get(0) == 1)
					oPolinomio += var + arrayExpoentes.get(0);// expoente = 1
				else if(arrayCoeficientes.get(0) != 1 && arrayExpoentes.get(0) != 1)
						oPolinomio += arrayCoeficientes.get(0)+var+arrayExpoentes.get(0);
					else if(arrayCoeficientes.get(0) == 1 && arrayExpoentes.get(0) != 1)
						oPolinomio += var+arrayExpoentes.get(0);
					else if(arrayCoeficientes.get(0) != 1 && arrayExpoentes.get(0) == 1)
						oPolinomio += arrayCoeficientes.get(0)+var;
					else
						oPolinomio += var;		
			}
		}
		
		// Tira os 1's de todos, menos do primeiro termo. 
		for (int i = 1; i < arrayCoeficientes.size(); i++) {

			if(i == arrayCoeficientes.size()-1){				
				if(arrayCoeficientes.get(i) < 0){
					if(arrayCoeficientes.get(i) != 1 && arrayExpoentes.get(i) != 1)
						oPolinomio += "- " + Math.abs(arrayCoeficientes.get(i))+var+arrayExpoentes.get(i);
					else if(arrayCoeficientes.get(i) == 1 && arrayExpoentes.get(i) != 1)
						oPolinomio += "- "+var+arrayExpoentes.get(i);
					else if(arrayCoeficientes.get(i) != 1 && arrayExpoentes.get(i) == 1)
						oPolinomio += "- " + Math.abs(arrayCoeficientes.get(i))+var;
					else
						oPolinomio += "- " + var;		
				}
				else{
					 if (arrayCoeficientes.get(i) == 1 ) //
						oPolinomio += "+ "+var + arrayExpoentes.get(i);//		
						else if(arrayCoeficientes.get(i) == 1 && arrayExpoentes.get(i) == 1)
							oPolinomio += "+ " + var;
						else if(arrayCoeficientes.get(i) == 1 && arrayExpoentes.get(i) != 1)
							oPolinomio += "+ " +var+arrayExpoentes.get(i);
						else if(arrayCoeficientes.get(i) != 1 && arrayExpoentes.get(i) == 1)
							oPolinomio += "+ "+ arrayCoeficientes.get(i) +var;
						else
							oPolinomio += "+ "+ arrayCoeficientes.get(i) +var+ arrayExpoentes.get(i);
				}
			}
			
			else if(arrayCoeficientes.get(i) > 0){
				if(arrayCoeficientes.get(0) == 1 && arrayExpoentes.get(0) == 1)
					oPolinomio += "+ " +var + " ";
				else if (arrayCoeficientes.get(0) == 1) //
						oPolinomio += "+ " + var + arrayExpoentes.get(0) + " ";//
					else if (arrayCoeficientes.get(i) == 1
							&& arrayExpoentes.get(i) == 1)
						oPolinomio += "+ " + var + " ";
					else if (arrayCoeficientes.get(i) == 1
							&& arrayExpoentes.get(i) != 1)
						oPolinomio += "+ " + var + arrayExpoentes.get(i) + " ";
					else if (arrayCoeficientes.get(i) != 1
							&& arrayExpoentes.get(i) == 1)
						oPolinomio += "+ " + arrayCoeficientes.get(i) + var + " ";
					else
						oPolinomio += "+ " + arrayCoeficientes.get(i) + var
								+ arrayExpoentes.get(i) + " ";
			}
			else{
				if(arrayCoeficientes.get(i) != -1 && arrayExpoentes.get(i) != 1)
					oPolinomio += "- " + Math.abs(arrayCoeficientes.get(i))+var+arrayExpoentes.get(i)+" ";
				else if(arrayCoeficientes.get(i) == -1 && arrayExpoentes.get(i) != 1)
					oPolinomio += "- "+var+arrayExpoentes.get(i)+" ";
				else if(arrayCoeficientes.get(i) != -1 && arrayExpoentes.get(i) == 1)
					oPolinomio += "- " + Math.abs(arrayCoeficientes.get(i))+var+" ";
				else
					oPolinomio += "- " + var+ " ";			
			}
		}
		return oPolinomio;
			
		}

	
	/**
	 *Calcula o valor de um monomio de acordo com o valor dado
	 * @param monomio
	 * @param valor
	 * @param var
	 * @return O valor do calculo do dado monomio
	 */
	public int calculaMonomio(String monomio, int valor, String var) {
	
		try {
			return Integer.parseInt(monomio); // Ocorre caso nao tenha variavel no monomio
		} catch (Exception e) {
			// faz nada!!
		}
		
			monomio = "1" + monomio;
			monomio = monomio + "1";
			StringTokenizer str = new StringTokenizer(monomio, var);
			String termo1 = str.nextToken();
			String termo2 = str.nextToken();
			
			int posVar = monomio.indexOf(var);
			try {
				if(monomio.charAt(posVar-1) == '-'){
					if(valor < 0)
						return -(int)Math.pow(valor, Integer.parseInt(termo2)/10);
					else
						if(valor > 0)
							return (int)Math.pow(-valor, Integer.parseInt(termo2)/10);
				}
			} catch (Exception e) {
				
			}

			// Caso seja um monomio do tipo  "1x1"
			if(termo1.length() == 1 && termo2.length() == 1){
				return valor;
			}
			// Caso seja um monomio do tipo  "1xb1" , b é um numero qualquer
			else if(termo1.length() == 1 && termo2.length() != 1){
				int num = Integer.parseInt(termo2)/10;
				return (int)Math.pow(valor, num);
			}
			else if(termo1.length() != 1 && termo2.length() == 1){
				int num = Integer.parseInt(termo1.substring(1));
				return (int) num * valor;
			} 
			else{
				int numEsq = Integer.parseInt(termo1.substring(1));
				int numDir = Integer.parseInt(termo2)/10;
				return numEsq*(int)Math.pow(valor, numDir); 
			}	
	}

	/**
	 *Calcula o valor final do polinomio
	 * @param oPolinomio
	 * @param valor
	 * @param var
	 * @return O valor do calculo do polinomio
	 */
	
	public int calculaPoli(String oPolinomio, int valor, String var) { 
		StringTokenizer str = new StringTokenizer(oPolinomio);
		LinkedList<String> numeros = new LinkedList<String>();
		String token ="";
		while(str.hasMoreTokens()){
			token = str.nextToken();
			if(token.equals("+") || token.equals("-"))
				numeros.addLast(token);
			else
				numeros.addLast(calculaMonomio(token, valor, var)+"");
		}
		
		int result = Integer.parseInt(numeros.get(0));
		String sinal = "";
		int num = 0;
		int i = 1;
		while(i < numeros.size()){
			
			sinal = numeros.get(i);
			i++;
			num = Integer.parseInt(numeros.get(i));
			
			if(sinal.equals("+"))
				result += num; 
			else 
				result -= num;
			i++;
		}
		
		return result;
	}

	/**
	 * Minimiza um polinomio espacado
	 * @param poli1Espacado
	 * @param var representa a variavel do polinomio resultante
	 * @return um array que representa o polinomio minimizado
	 */	
	public ArrayList minimizaPolinomio2(String poli1Espacado, String var) {
		 int posAbreParenteses = poli1Espacado.indexOf("(");
		 String nomePoli = poli1Espacado.substring(0, posAbreParenteses);
		 int posDoIgual = poli1Espacado.indexOf("=");
		 String polinomio = poli1Espacado.substring(posDoIgual+2); // já vem organizado, tirou o espaço dpois do igual
		 	 
		 StringTokenizer str = new StringTokenizer(polinomio);
		 ArrayList<Integer> arrayCoeficientes = new ArrayList<Integer>();
		 ArrayList<Integer> arrayExpoentes = new ArrayList<Integer>();
		 int somaInteiros = 0;
		 int coeficiente = 1;
		 int expoente = 1;
		 boolean ehInteiro = false;
		 boolean temCoeficiente = false;
		 ArrayList array = new ArrayList();
		 	 
		 while(str.hasMoreTokens()){
			 coeficiente = 1;
			 expoente = 1;
			 ehInteiro = false;
			 String token = str.nextToken();
		 
			 try { // verifica se token é um numero inteiro.
				somaInteiros += Integer.parseInt(token);
				ehInteiro = true;
			 } 
			 catch (Exception e) {
				 // faz nada.
			 }
			
			if(!ehInteiro){
				if(token.equals("-")){
					token = "-"+ str.nextToken();
					try { 
							somaInteiros += Integer.parseInt(token);
							ehInteiro = true;
					} catch (Exception e) {
							//faz nada
					}
				}
				else if(token.equals("+")){
					token = str.nextToken();
					 try { 
							somaInteiros += Integer.parseInt(token);
							ehInteiro = true;	
						} catch (Exception e) {
							
						}
				}
				String numCoef = "";
				String numExp = "";
				if(!ehInteiro){
					try {
						numCoef = token.substring(0, token.indexOf(var));
						if(numCoef.equals("-"))
							numCoef = -1+"";
						coeficiente = Integer.parseInt(numCoef);

					} catch (Exception e) {
						// coeficiente continua sendo 1
					}
					
					try {
						numExp = token.substring(token.indexOf(var) + var.length());
						if(numExp.equals("0")){
							somaInteiros += coeficiente*1;
							coeficiente = 0;
						}
						else
							expoente = Integer.parseInt(numExp);

					} catch (Exception e) {
						// expoente continua sendo 1
					}	
					if(coeficiente != 0){
						arrayCoeficientes.add(coeficiente);
						temCoeficiente = true;
						arrayExpoentes.add(expoente);
					}
				}
			}
		 }
		 arrayCoeficientes.add(somaInteiros);
		 arrayExpoentes.add(0);
		 array.add(arrayCoeficientes);
		 array.add(arrayExpoentes);
		return array;
		
	}
}
