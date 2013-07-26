

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Logica {
	
	private Map<String,Polinomio> mapaReduzido = new HashMap<String,Polinomio>();
	public static Map<String,String> mapaEntrada = new HashMap<String,String>();
	
	/**
	 * Metodo qua captura de define o que cada comando passado faz
	 * @param entrada
	 * @return uma string de aceitacao de polinomio
	 */
	public String fassaOperacao(String entrada) {
		
		if (!(Logica.entradaValida(entrada))){
			return "Entrada Invalida";
		}
		entrada = entrada + " "; 
		Scanner scan = new Scanner(entrada);
		String sComando = scan.next();
		String resto = scan.nextLine();
		sComando = sComando.toUpperCase();
//		retorna o tipo enumerado do comando
		Comando comando = Comando.getComando(sComando);
		
		
		switch(comando){
			case DEF:{
				String chave = TratamentosComuns.getChave(resto);
				
				if (!(Polinomio.polinomioEhValido(resto))){ 
					return "Erro: Polinomio invalido";
				}
				
				if (mapaReduzido.containsKey(chave)){
					mapaReduzido.remove(chave);
				}
				
				if (resto.contains("*")){
					String polinom = desenvolvaPolinomio(resto);
					mapaReduzido.put(chave,new Polinomio(polinom));
					String saida = organizaPrint(chave + mapaReduzido.get(chave).toString());
					mapaEntrada.put(chave,saida);
					return "ok";
				}
				String[] lados = resto.split("=");
				String key = TratamentosComuns.getChave(lados[1]);
				String chaves = getChavesDoMapaEntrada();
				
				if (chaves.contains(key)){
					String polinomio = substituicao(resto);
					mapaReduzido.put(chave,new Polinomio(polinomio));
					mapaEntrada.put(chave,chave + mapaReduzido.get(chave).toString());
					return "ok";
				}
				
				mapaEntrada.put(chave,Logica.organizaPrint(resto));
				return "ok";
			}
			
			case PRINT:{
				String chave = TratamentosComuns.getChave(resto);
				
				
				if ( (resto.contains("(")) && (resto.contains(")")) ){
					
					if ( !(Logica.printCorreto(resto)) ){
						return "Erro: Entrada Incorreta";
					}
					
					int constante = Logica.getInt(resto);
					if (mapaReduzido.containsKey(chave)){
						
						Polinomio p = mapaReduzido.get(chave);
						int resultado = CalculadoraPolinomial.calcule(constante,p);
						return resultado + "";
					
					}else if (mapaEntrada.containsKey(chave)){
						
						String polinomio = mapaEntrada.get(chave);
						Polinomio p = new Polinomio(polinomio);
						int resultado = CalculadoraPolinomial.calcule(constante,p);
						return  resultado + "";
					}
				}else if (mapaReduzido.containsKey(chave)){
					
					String retorno = Logica.organizaPrint(mapaReduzido.get(chave).toString());
					return chave + retorno;
				
				}else if (mapaEntrada.containsKey(chave)){
					
					return Logica.organizaPrint(mapaEntrada.get(chave));
				
				}else return "Erro: Chave Invalida";
				
				break;
			}
			
			case REDUZA:{
				String chave = TratamentosComuns.getChave(resto);
				mapaReduzido.put(chave,new Polinomio(mapaEntrada.get(chave)));
				return "ok";
			}
			case EXIT:{
				return "bye";
			}
			case ERROR:{
				return "Erro: Comando Invalido";
				
			}
			default: 
				break;
			
		}
		return null;
	}
	
	
	/**
	 * Metodo que procura as de um mapa onde cada uma referencia a um polinomio nao reduzido
	 * @return chaves do mapa
	 */
	public String getChavesDoMapaEntrada(){
		
		Set<String> chaves = this.mapaEntrada.keySet();
		String retorno = "";
		for (String chave : chaves) {
			retorno += chave + " ";
			
		}
		return retorno.trim();
	}
	
	/**
	 * Metodo que procura um polinomio referenciado por uma chave
	 * @param chave
	 * @return polinomio
	 */
	public String getPolinomioMapaEntrada(String chave){
		
		return this.mapaEntrada.get(chave);
	}
	
	/**
	 * Metodo que procura as chaves de um mapa onde cada uma referencia a um polinomio reduzido
	 * @return chaves do mapa
	 */
	public String getChavesDoMapaReduzido(){
		
		Set<String> chaves = this.mapaReduzido.keySet();
		String retorno = "";
		for (String chave : chaves) {
			retorno += chave + " ";
			
		}
		return retorno.trim();
	}
		
	private static String organizaPrint(String resto){
		String retorno = resto.replace(" ","");
		String[] lados = TratamentosComuns.getLados(retorno,"=");
		char[] membroDir = lados[1].toCharArray();
		LinkedList<Character> membroE = new LinkedList<Character>();
				
		for (int i = 1; i < membroDir.length; i++) {
			if (((membroDir[i] == '-') || (membroDir[i] == '+'))){
				membroE.add(' ');
				membroE.add(membroDir[i]);
				membroE.add(' ');
				
			}else membroE.add(membroDir[i]);
		}
		membroE.addFirst(membroDir[0]);
		membroE.addFirst(' ');
		
		int tamanhoDaLista = membroE.size();
		membroDir = new char[tamanhoDaLista];
		for (int i = 0; i < tamanhoDaLista; i++) {
			membroDir[i] = membroE.poll();
		}
		
		retorno = lados[0] + " =" + String.valueOf(membroDir);
		return retorno;
	}

	/**
	 * Metodo que procura o inteiro dentro da string passada como parametro
	 * @param resto
	 * @return um inteiro dentro da string
	 */
	public static int getInt(String resto){
		Scanner scan = new Scanner(resto);
		scan.useDelimiter(Pattern.compile("[()]"));
		String[] a = new String[3];
		int i = 0;
		while(scan.hasNext()){
			a[i] = scan.next();
			i++;  
		}
		return Integer.parseInt(a[1]);
	}
	
	/**
	 * Metodo que avalia se um entrada e valida
	 * @param entrada
	 * @return booleana
	 */
	public static boolean entradaValida(String entrada){
		String entradaSemEspaco = entrada.replace(" ","");
		if (entradaSemEspaco.length() == 0){
			return false;
		}else return true;
	}
	
	private static boolean printCorreto(String entrada){
		String entradaSemEspaco = entrada.replace(" ","");
		String chave = TratamentosComuns.getChave(entradaSemEspaco);
		
		if (chave.length() ==  0){
			return false;
		}
		try{
			Logica.getInt(entradaSemEspaco);
		}catch(NumberFormatException retorno1){
			return false;
		}
		return true;
	}
	
	/**
	 * Metodo que desenvolve e opera sobre um polinomio passado como parametro
	 * @param poli
	 * @return o polinomio desenvolvido e calculado
	 */
	public String desenvolvaPolinomio(String poli){
		poli = poli.replace(" ","");
		String[] lados = poli.split("=");
		String ladoEsq = lados[0];
		String ladoDir = lados[1];
		
		String delimitador = "[-+*]";
		ArrayList<String> monomios = TratamentosComuns.separe(ladoDir,delimitador);
		
		delimitador = "[0-9a-zA-Z()]+";
		ArrayList<String> sinais = TratamentosComuns.separe(ladoDir,delimitador);
		if (ladoDir.charAt(0) != '-') sinais.add(0,"");
		
		
	    int constante = 0;
	    if (sinais.get(0) == "-") constante = 1;
	    
	    String key = TratamentosComuns.getChave(ladoEsq);
	    String vari = TratamentosComuns.getVariavel(ladoEsq);
	    String retorno = key; 
	    for (int i = 0; i < monomios.size(); i++) {
			String sinal;
	    	
			if (i + 1 > sinais.size() - 1){
				sinal = "";
			}else sinal = sinais.get(i + 1);
			
	    	int multiplicador = 1;
			if ((i == 0) && (constante == 1)) multiplicador = -1;
			retorno += sinais.get(i);
			if (sinal.equals("*")){
				if (Inteiro.ehInteiro(monomios.get(i))){
					multiplicador *= Integer.parseInt(monomios.get(i));
					String variavel = TratamentosComuns.getVariavel(monomios.get(i+1));
					String chave = TratamentosComuns.getChave(monomios.get(i + 1));
					String polinomio = getPolinomioMapaEntrada(chave);
					retorno += CalculadoraPolinomial.multiplique(new Polinomio(polinomio)
							  ,multiplicador,variavel);
					i++;
					if (i > sinais.size() - 1) retorno += sinais.get(i);
					
				
				}else if (Inteiro.ehInteiro(monomios.get(i + 1))){
					multiplicador *= Integer.parseInt(monomios.get(i + 1));
					String variavel = TratamentosComuns.getVariavel(monomios.get(i));
					String chave = TratamentosComuns.getChave(monomios.get(i));
					String polinomio = getPolinomioMapaEntrada(chave);
					Polinomio p = new Polinomio(polinomio);
					retorno += CalculadoraPolinomial.multiplique(p,multiplicador,variavel);
					i++;
					if (i > sinais.size() - 1) retorno += sinais.get(i);
				
				}else{
					String chave = TratamentosComuns.getChave(monomios.get(i));
					String variavel = TratamentosComuns.getVariavel(monomios.get(i));
					String chave1 = TratamentosComuns.getChave(monomios.get(i+1));
					String p1 = getPolinomioMapaEntrada(chave);
					String p2 = getPolinomioMapaEntrada(chave1);
					Polinomio poli1 = new Polinomio(p1);
					Polinomio poli2 = new Polinomio(p2);
					retorno += CalculadoraPolinomial.multiplique(poli1,poli2,variavel);
					i++;
					if (i > sinais.size() - 1) retorno += sinais.get(i);
				}
			
			}else retorno += monomios.get(i);
		
	    }
	   
	    return retorno;
	}
	
	/**
	 * Metodo que desenvolve um polinomio passado como parametro
	 * @param poli
	 * @return o polinomio desenvolvido
	 */
	public String substituicao(String poli){
		
		String[] lados = poli.split("=");
		String ladoEsq = lados[0];
		String ladoDir = lados[1];
		String chave = TratamentosComuns.getChave(ladoEsq);
		
		String variavel = TratamentosComuns.getVariavel(ladoEsq);
		
		String delimitador = "[0-9a-zA-Z()]+";
		String ladoD = ladoDir.replace(" ","");
		ArrayList<String> sinais = TratamentosComuns.separe(ladoD,delimitador);
		
		if (ladoDir.charAt(0) != '-') sinais.add(0,"");
			
		String retorno = ladoEsq + "=";
		Scanner scan = new Scanner(ladoDir);
		scan.useDelimiter("[-+*]");
		int i = 0;
		while (scan.hasNext()){
			String monomio = scan.next();
			
			retorno += sinais.get(i);
						
			if (monomio.contains("(")){
				
				String key = TratamentosComuns.getChave(monomio);
				String polinomio = getPolinomioMapaEntrada(key);
				String[] lado = polinomio.split("=");
				String vari = TratamentosComuns.getVariavel(lado[0]);
				polinomio = lado[1].replace(" ","");
				polinomio = polinomio.replace(vari,variavel);
				retorno += polinomio;
				retorno = retorno.replace(vari,variavel);
				
			
			}else{
				retorno += monomio.replace(" ","");
			}
			i++;
		}
		
		retorno = retorno.replace("+-","-");
		return retorno;
	}
	
}
