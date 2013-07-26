

import java.util.*;
import java.util.regex.Pattern;

import com.sun.java_cup.internal.reduce_action;

import static java.lang.Integer.*;
import static java.util.Collections.sort;

public class Polinomio<T> {
	
	private HashMap<Integer,Integer>  map = new HashMap<Integer,Integer>();
	public static String variavel = "";
	
	/**
	 * Cronstruto de um polinomio
	 * @param polinomio
	 */
	public Polinomio(String polinomio){
		String polinomioMod = polinomio.replace(" ","");
		this.variavel = TratamentosComuns.getVariavel(polinomioMod);
		polinomioMod = getCoeficientesExpoentes(variavel,polinomioMod);
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
		
		int[] chaves = this.getChaves();
		for (int i = 0; i < chaves.length; i++){
			if (getValor(chaves[i]) == 0) map.remove(chaves[i]);
			
		}
	}
		
	/**
	 * Metodo de recuperacao
	 * @param chave
	 * @return o valor da chave
	 */
	public int getValor(int chave){
		Logica.mapaEntrada.clear();
		return map.get(chave);
	}
	
	/**
	 * Metodo de recuperacao
	 * @return nome variavel
	 */
	public String getVariavel(){
		return this.variavel;
	}
	
	/**
	 * Metodo recuperador
	 * @return todas as chaves de um mapa
	 */
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

	/**
	 * Metodo que verifica se um polinomio e valido
	 * @param resto
	 * @return booleana
	 */
	public static boolean polinomioEhValido(String resto) {

		if (!resto.contains("=")) return false;
		String[] lados = resto.split("=");
		
		if (lados.length != 2) return false;
		
		String ladoEsq = lados[0].replace(" ","");
		String ladoDir = lados[1].replace(" ","");
		
		if ((ladoEsq.equals("")) || (ladoDir.equals("")))return false;
		
		if (!(ladoEsq.contains(")"))) return false;
		
		String delimitador = "[()]";
		ArrayList<String> lista = TratamentosComuns.separe(ladoEsq,delimitador);
		
		if ((lista.size()!= 2) || (lista.get(0).equals("")) ||
				lista.get(1).equals("")){
			return false; 
		}
		String variavel = lista.get(1);
		return ehCorretoLadoDir(ladoDir,variavel);
	}
	
	private static boolean ehCorretoLadoDir(String lado, String variavel){
		
		String variavelDupla = variavel + variavel;
		if (lado.contains(variavelDupla)) return false;
		
		if ((lado.contains("))")) || (lado.contains("(("))) return false;
		
		char ultimoCaracter = lado.charAt(lado.length() - 1);
		if ((ultimoCaracter == '+') || (ultimoCaracter == '-') 
				|| (ultimoCaracter == '*')){
			return false;
		}
		
		Scanner scan = new Scanner(lado);
		scan.useDelimiter("[-+*]");
		while(scan.hasNext()){
			String elemento = scan.next();
			
			if (elemento.equals("")) return false;
			
			if ((!(Inteiro.ehInteiro(elemento))) 
					&& (!(elemento.contains(variavel)))){
				return false;
			}
		}
		return true;
	}
		
	
	
	private static String getCoeficientesExpoentes(String variavel,String poli){
		
		String[] lados = poli.split("=");
		String ladoE = lados[1];
		ladoE = ladoE.replace(" ","");
		ladoE = ladoE.replace("-","+-");
		
		String delimitador = "[+]+";
		ArrayList<String> monomios = TratamentosComuns.separe(ladoE,delimitador);
		
		delimitador = "[^+^-]+";
		String entrada = ladoE.replace(variavel,"");
		ArrayList<String> sinais = TratamentosComuns.separe(entrada,delimitador);
				
		String retorno = reduzParaStringCompleta(monomios,variavel);
		retorno = retorno.replace(variavel," ");
		return retorno;
	}	
	
		
	private static String reduzParaStringCompleta(ArrayList<String> monomios,
			 String variavel) {
		
		String retorno = "";
		for (int i = 0; i < monomios.size(); i++) {
			String monomio = monomios.get(i).replace(" ","");
			
			if (!(Inteiro.ehInteiro(monomio))){
				int ultimoIndice = monomio.length() - 1;
				int indiceVariavel = monomio.indexOf(variavel);
				
				if (!Inteiro.ehInteiro(monomio.substring(0,indiceVariavel))){
					if (monomio.charAt(0) == '-') retorno+= "-";
					retorno+= "1";  
				
				}else retorno+= monomio.substring(0,indiceVariavel);
				
				if (!(Inteiro.ehInteiro(monomio.charAt(ultimoIndice)))){
					
					retorno+= variavel + 1 + " ";
				
				}else retorno+= variavel + monomio.substring(indiceVariavel + 1) + " ";
								
			}else retorno+= monomio + variavel + 0 + " ";
		}
	 	
		return retorno.trim();
	}
	
	/**
	 * Metodo que imprime um polinomio
	 */
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
		
			if ((chaves[i] != 0) && (map.get(chaves[i])!= 0)){
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
}	
	