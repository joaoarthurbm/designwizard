

import java.util.*;
import java.util.regex.Pattern;
import static java.lang.Integer.*;
import static java.util.Collections.sort;

public class Polinomio<T> {
	
	private HashMap<Integer,Integer>  map = new HashMap<Integer,Integer>();
	private String variavel = "";
	
	
	public Polinomio(String polinomio){
		String polinomioMod = polinomio.replace(" ","");
		int[] indicesDosParenteses = new int[2];
	    indicesDosParenteses[0] = polinomioMod.indexOf('(') + 1;
	    indicesDosParenteses[1] = polinomioMod.indexOf(')');
		String variavel = polinomioMod.substring(indicesDosParenteses[0],indicesDosParenteses[1]);
		this.variavel = variavel;
		polinomioMod = Polinomio.getCoeficientesExpoentes(variavel,polinomioMod);
		String[] coeficientesExpoentes = polinomioMod.split(" ");
		
		int coeficiente = 0;
		int expoente = 0;
		for (int i = 0; i < coeficientesExpoentes.length; i+=2) {
			
			coeficiente = parseInt(coeficientesExpoentes[i]); 
			expoente = parseInt(coeficientesExpoentes[i + 1]);
			if (coeficiente != 0){
				
				if (this.map.containsKey(expoente)){
					
					int valor = this.map.get(expoente);
					this.map.put(expoente, coeficiente + valor);
				
				}else this.map.put(expoente,coeficiente);
			}
		}
	}
	
	public Polinomio(){
		
	}
		
	public int getValor(int chave){
		return map.get(chave);
	}
	
	public String getVariavel(){
		return this.variavel;
	}
	
	public void setValor(int chave,int valor){
		this.map.put(chave,valor);
	}
	
	private void trocaChaves(int chaveAntiga, int chaveNova){
		int valor = this.map.get(chaveAntiga);
		this.map.remove(chaveAntiga);
		this.map.put(chaveNova,valor);
	}
	
	public int[] getChaves(){
		
		Set<Integer> keys = this.map.keySet();
		ArrayList<Integer> chaves = new ArrayList<Integer>();
		chaves.addAll(keys);
		sort(chaves);
		
		int[] retorno = new int[chaves.size()];
		for (int i = 0; i < chaves.size(); i++) {
			retorno[i] = chaves.get(i);
		}
		return retorno;
	}

	public static boolean polinomioEhValido(String resto) {
//		retira todos os espaços.
		resto = resto.replace(" ","");
				
//		false se tiver algum sinal duplicado ex: ++
		if (!(Polinomio.sinaisCorretos(resto))){ 
			return false;                                 
		}
				
		List<String> lista1 = new ArrayList<String>();
		Scanner scan = new Scanner(resto);
		scan.useDelimiter(Pattern.compile("[()=]+"));

//		O polinomio deve ser dividido na forma: <identificador><variavel><equação>		
//		ex: p(x) = 5x+2x2 --> [p, x, 5x+2x2]
		while(scan.hasNext()){        
			lista1.add(scan.next());   
		}							 
		
//		false se o polinomio não for separado da maneira correta.
		if (lista1.size() != 3){     
			return false;
		}
		
//		separa as variaveis
		List<String> lista2 = new ArrayList<String>();
		Scanner in = new Scanner(lista1.get(2));
		in.useDelimiter(Pattern.compile("[0123456789+-]+"));
		while(in.hasNext()){        
			lista2.add(in.next());  
		}

//		testa se todas as variaveis são iguais as definidas ex: p(x) = 2y retorna false		
		int i = 0;
		while(i < lista2.size()){
			if (!(lista2.get(i).equals(lista1.get(1)))){
				return false;
			}
			i++;	
		}		
		
		return true;
	}
	
	
	private static boolean sinaisCorretos(String expressao){
//		teste para sinais duplicados
		if ((expressao.contains("==")) || (expressao.contains("((") 
				|| (expressao.contains("))")) || (expressao.contains("++")) ||
						(expressao.contains("--")) || (expressao.contains("+-"))
						|| (expressao.contains("-+")))){
			return false;
		}
		
//		false se não contiver sinal de igualdade
		if (!(expressao.contains("="))){ 
			return false;
		}
//		false se faltar algum parenteses
		if ( !(expressao.contains("(")) || !(expressao.contains(")"))){ 
			return false;
		}

//		false se um dos ultimos caracteres for um dos separadores
		int ultimoCaracter = expressao.length() - 1;
		char ultimo = expressao.charAt(ultimoCaracter);
		if ((ultimo == '+') || (ultimo == '-') || (ultimo == '=')){
			return false;
		}
		return true;
	}
	
	public static String getCoeficientesExpoentes(String variavel,String polinomioO){
		
		String polinomio = polinomioO.replace(" ","");
		int indiceDaIgualdade = polinomio.indexOf('=') + 1;
		polinomio = polinomio.substring(indiceDaIgualdade);
		polinomio = polinomio.replace(variavel," ");
		List<Character> polinomioFinal = new LinkedList<Character>();
		char[] polinomioChar = polinomio.toCharArray();
		
		for (int i = 0; i < polinomioChar.length; i++) {
			
			if ((i == 0) && (polinomioChar[i] == ' ')){
				polinomioFinal.add('1');
				polinomioFinal.add(polinomioChar[i]);
				if ((polinomioChar[i + 1] == '+')||(polinomioChar[i + 1] == '-')){
					polinomioFinal.add('1');
				}
			}else if ((i == polinomioChar.length -1) && (polinomioChar[i] == ' ')){
				if ((polinomioChar[i - 1] == '+')||(polinomioChar[i - 1] == '-')){
					polinomioFinal.add('1');
				}
				polinomioFinal.add(polinomioChar[i]);
				polinomioFinal.add('1');
			
			}else if (polinomioChar[i] == ' '){
				if ((polinomioChar[i - 1] == '+') || (polinomioChar[i - 1] == '-')) {
					polinomioFinal.add('1');
				}
				polinomioFinal.add(polinomioChar[i]);
				
				if ((polinomioChar[i + 1] == '+') || (polinomioChar[i + 1] == '-')) {
					polinomioFinal.add('1');
				}
			}else if ((polinomioChar[i] == '-') && (i!=0)){
				polinomioFinal.add('+');
				polinomioFinal.add(polinomioChar[i]);
			}else polinomioFinal.add(polinomioChar[i]);
		}
		int j =1;
		for (int i = 0; i < polinomioFinal.size(); i++) {
			if(polinomioFinal.get(i) == '+'){
				j++;
				if (j == 2){
					polinomioFinal.add(i,'0');
					polinomioFinal.add(i,' ');
					j-=2;
				}
			}else if (polinomioFinal.get(i) == ' '){
				j--;
			}
		}
		if (j==1){
			polinomioFinal.add(' ');
			polinomioFinal.add('0');
			
		}
		
		char[] poli = new char[polinomioFinal.size()];
		for (int i = 0; i < polinomioFinal.size(); i++) {
			poli[i] = polinomioFinal.get(i);		
		}
		String retorno = String.copyValueOf(poli);
		retorno = retorno.replace('+',' ');
		return retorno;
		
	}
	
	public String toString(){
		String constantes = "(" + this.variavel + ")" + " =";
		String retorno = "";
		int[] chaves = this.getChaves();
		
		for (int i = chaves.length - 1; i >= 0 ; i--) {
			
			if (this.map.get(chaves[i]) < 0){
				
				if (this.map.get(chaves[i]) == -1){
					retorno += " -";
					if(chaves[i] == 0){
						retorno += "1";
					}
				}else retorno += " " + this.map.get(chaves[i]);				
			
			}else if (i == chaves.length - 1){
				if ( this.map.get(chaves[i])== 1){
					retorno += " ";
					if (chaves[i] == 0){
						retorno += "1";
					}
				}else retorno+= " " + this.map.get(chaves[i]);
			
			}else if (this.map.get(chaves[i]) == 1){
				retorno += " + ";
			
			}else retorno += " + " + this.map.get(chaves[i]);
		
			if (chaves[i] != 0){
				if (chaves[i] == 1){
					retorno+= variavel;
				
				}else retorno += variavel + chaves[i]; 
			}
		
		}
		
		if (retorno.length() == 0){
			retorno += " " + 0;
		}
		return constantes + retorno;
		
	}
	
	public static String multiplique(Polinomio polinomio, int multiplicador){
		int[] chaves = polinomio.getChaves();
		
		for (int i = 0; i < chaves.length; i++) {
			int valor = polinomio.getValor(chaves[i]);
			polinomio.setValor(chaves[i], valor * multiplicador);
		}
		
		String retorno = polinomio.toString();
		String[] retorne = retorno.split("=");
		retorne[1]= retorne[1].replace(" ","");
		return retorne[1];
		
	}
	
	public static String multipliquePolinomios(Polinomio poli1, Polinomio poli2,String variavel){
		int[] chaves1 = poli1.getChaves();
		int[] chaves2 = poli2.getChaves();
		
		String novoPolinomio = "(" + variavel + ")" + "=";
		
		
		for (int i = 0; i < chaves1.length; i++) {
			for (int j = 0; j < chaves2.length; j++) {
				int valor = poli1.getValor(chaves1[i]) * poli2.getValor(chaves2[j]);
				int expoente = chaves1[i] * chaves2[j];
				
				if (valor < 0){
					novoPolinomio+= "-";
				}else if (i != 1){
					novoPolinomio+= "+";
				}
				novoPolinomio+= valor + variavel + expoente;
			}
		}
		Polinomio newPolinomio = new Polinomio(novoPolinomio);
		
		String retorno = newPolinomio.toString();
		String[] retorne = retorno.split("=");
		retorne[1]= retorne[1].replace(" ","");
		return retorne[1];
		
	}

	

	
}
