

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Logica {
	
	private Map<String,Polinomio> mapaReduzido = new HashMap<String,Polinomio>();
	private Map<String,String> mapaEntrada = new HashMap<String,String>();
	
	public String fassaOperacao(String entrada) {
		
		if (!(Logica.entradaValida(entrada))){
			return "Erro: Entrada Invalida";
		}
		entrada = entrada + " "; 
		Scanner scan = new Scanner(entrada);
		String sComando = scan.next();
		String resto = scan.nextLine();
		sComando = sComando.toUpperCase();
//		retorna o tipo enumerado do comando
		Comando comando = Logica.getComando(sComando);
		
		
		switch(comando){
			case DEF:{
				String chave = Logica.getChave(resto);
				String[] chaves = getChavesMapaEntrada();
				
				
				
				if (mapaReduzido.containsKey(chave)){
					mapaReduzido.remove(chave);
				}
				
				
				
				if(Polinomio.polinomioEhValido(resto)){
					mapaEntrada.put(chave,Logica.organizaPrint(resto));
					return "ok";
					
				}else if ((contemChave(resto,chaves)) || (resto.contains("*"))){
					
					String polinomioExpandido = getPolinomioExpandido(resto,chaves);
					System.out.println(polinomioExpandido);
					if (Polinomio.polinomioEhValido(polinomioExpandido)){
						mapaReduzido.put(chave,new Polinomio(Logica.organizaPrint(polinomioExpandido)));
						return "ok";
					}
				
				
				}else return "Erro: Polinomio invalido";
			}
			
			case PRINT:{
				String chave = Logica.getChave(resto);
				
				
				if ( (resto.contains("(")) && (resto.contains(")")) ){
					
					if ( !(Logica.printCorreto(resto)) ){
						return "Erro: Entrada Incorreta";
					}
					
					int constante = Logica.getInt(resto);
					if (mapaReduzido.containsKey(chave)){
						
						Polinomio p = mapaReduzido.get(chave);
						int resultado = CalculadoraPolinomial.calcule(constante,p);
						return chave + "("+ constante + ") = " + resultado + "";
					
					}else if (mapaEntrada.containsKey(chave)){
						
						String polinomio = mapaEntrada.get(chave);
						Polinomio p = new Polinomio(polinomio);
						int resultado = CalculadoraPolinomial.calcule(constante,p);
						return  chave + "("+ constante + ") = " + resultado + "";
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
				String chave = Logica.getChave(resto);
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
	
	

	public String[] getChavesMapaEntrada(){
		Set<String> keys = this.mapaEntrada.keySet();
		ArrayList<String> chaves = new ArrayList<String>();
		chaves.addAll(keys);
	
		String[] retorno = new String[chaves.size()];
		for (int i = 0; i < chaves.size(); i++) {
			retorno[i] = chaves.get(i);
		}
	
		return retorno;
	}
	
//	DEVE SER PRIVATE
	public static String getChave(String resto) {
		resto = resto.replace(" ","");
		Scanner scan = new Scanner(resto);
		String chave = "";
		if (resto.contains("(")){
			scan.useDelimiter("[(]");
			chave = scan.next();
			return chave;
		}
		chave = resto;
		return chave;
	}
	
	public static String organizaPrint(String resto){
		String retorno = resto.replace(" ","");
		String[] membros = retorno.split("=");
		char[] membroEsquerdo = membros[1].toCharArray();
		LinkedList<Character> membroE = new LinkedList<Character>();
				
		for (int i = 1; i < membroEsquerdo.length; i++) {
			if (((membroEsquerdo[i] == '-') || (membroEsquerdo[i] == '+'))){
				membroE.add(' ');
				membroE.add(membroEsquerdo[i]);
				membroE.add(' ');
				
			}else membroE.add(membroEsquerdo[i]);
		}
		membroE.addFirst(membroEsquerdo[0]);
		membroE.addFirst(' ');
		
		int tamanhoDaLista = membroE.size();
		membroEsquerdo = new char[tamanhoDaLista];
		for (int i = 0; i < tamanhoDaLista; i++) {
			membroEsquerdo[i] = membroE.poll();
		}
		
		retorno = membros[0] + " =" + String.valueOf(membroEsquerdo);
		return retorno;
	}


	// Prestar atenção se funciona e se o lugar dele eh mesmo aki//
	
	public static Comando getComando(String comando){
		
		comando = comando.replace(" ","");
		
		if (comando.equals("DEF")){
			return Comando.DEF;
		}else if (comando.equals("PRINT")){
			return Comando.PRINT;
		}else if (comando.equals("REDUZA")){
			return Comando.REDUZA;
		}else if (comando.equals("EXIT")){ 
			return Comando.EXIT;
		}else return Comando.ERROR;
	}
	
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
	
	public static boolean entradaValida(String entrada){
		String entradaSemEspaço = entrada.replace(" ","");
		if (entradaSemEspaço.length() == 0){
			return false;
		}else return true;
	}
	
	public static boolean printCorreto(String entrada){
		String entradaSemEspaço = entrada.replace(" ","");
		String chave = Logica.getChave(entradaSemEspaço);
		
		if (chave.length() ==  0){
			return false;
		}
		try{
			Logica.getInt(entradaSemEspaço);
		}catch(NumberFormatException retorno1){
			return false;
		}
		return true;
		
	}
	
	public static boolean contemChave(String polinomio,String[] chaves){
		
		boolean retorno = false; 
		String[] lados = polinomio.split("=");
		
		if (lados.length != 2){
			return retorno;
		}
		
		for (int i = 0; i < chaves.length; i++) {
			if (lados[1].contains(chaves[i])){
				retorno = true;
			}
			
		}
		return retorno;
		
	}
	
	
	public String getPolinomioExpandido(String resto,String[] chaves) {
		String[] lados = resto.split("=");
		int[] indicesDosParenteses = new int[2];
	    indicesDosParenteses[0] = lados[0].indexOf('(') + 1;
	    indicesDosParenteses[1] = lados[0].indexOf(')');
		String variavel = lados[0].substring(indicesDosParenteses[0],indicesDosParenteses[1]);
		
		
		
		lados[1] = lados[1].replace(" ","");
		
		Scanner scan = new Scanner(lados[1]);
		scan.useDelimiter(Pattern.compile("[*-+]+"));
		ArrayList<String> monomios = new ArrayList<String>();
		
		while (scan.hasNext()){
			monomios.add(scan.next());
		}
				
		char[] ladoDireito = lados[1].toCharArray();
		char[] sinais = new char[monomios.size()];
		
		int j = 0;
		for (int i = 1; i < ladoDireito.length; i++) {
			if ((ladoDireito[i] == '+') || (ladoDireito[i] == '-') ||
					(ladoDireito[i] == '*')){
				sinais[j] = ladoDireito[i];
				j++;
			}
		} 
		
		
		
		String mizera = String.copyValueOf(sinais);
		
		String retorno ="";
		int i = 0;	
		int sinal = 0;
		int multiplicador;
		
		
		while (i <= monomios.size()){
			
			Polinomio p1 = new Polinomio();
			Polinomio p2 = new Polinomio();
			String chave1;
			String chave2;
			
			if (Logica.contemChave(monomios.get(i), chaves)){
				System.out.println(contemChave(monomios.get(i),chaves));
				chave1 = Logica.getChave(monomios.get(i));
				p1 = new Polinomio(this.mapaEntrada.get(chave1));
				
			if (sinais[sinal] == '*'){	
				if (Logica.contemChave(monomios.get(i+1), chaves)){
					chave2 = Logica.getChave(monomios.get(i+1));
					p2 = new Polinomio(this.mapaEntrada.get(chave2));
				    retorno += Polinomio.multipliquePolinomios(p1,p2,variavel);
				    sinal++;
				    i+=2;
				    retorno+= sinais[sinal];
				
				}else { 
					multiplicador = Integer.parseInt(monomios.get(i+1));	
					String aux = Polinomio.multiplique(p1,multiplicador);
					aux = aux.replace(p1.getVariavel(),variavel);
					retorno += aux;
				    sinal++;
				    i+=2;
				    retorno+= sinais[sinal];
				
				
				} 
			}	
			
			}else if (Logica.contemChave(monomios.get(i), chaves)){		
				
				String aux = p1.toString();
				int indice = aux.indexOf("=") + 1;
				aux = aux.substring(indice);
				retorno+= sinais[sinal];
				sinal++;
				retorno+= aux;
				i++;
			
			}else if (i != 0){
				retorno+= sinais[sinal];
				sinal++;
				
				i++;
			
			}else retorno+= monomios.get(i); 	
			
		}
	
	return lados[0]+ "=" + retorno;	
	}
	
	
	
	private static boolean ehInteiro(String entrada){
		
		try{
			Integer.parseInt(entrada);
		}catch(NumberFormatException retorno1){
			return false;
		}
		
		return true;	
	}
}
	

