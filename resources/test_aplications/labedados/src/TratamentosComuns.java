import java.util.ArrayList;
import java.util.Scanner;

class TratamentosComuns {
	
	
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
	
	public static String[] getLados(String entrada,String separador){
		String[] retorno = entrada.split(separador);
		return retorno;
	}
	
	public static String getVariavel(String poli){
		
		int[] indicesDosParenteses = new int[2];
	    indicesDosParenteses[0] = poli.indexOf('(') + 1;
	    indicesDosParenteses[1] = poli.indexOf(')');
		String variavel = poli.substring(indicesDosParenteses[0],indicesDosParenteses[1]);
		return variavel;
	}
	
	public static ArrayList<String> separe(String entrada,String separador){
		Scanner scan = new Scanner(entrada);
		scan.useDelimiter(separador);
		ArrayList<String> retorno = new ArrayList<String>();
		
		while (scan.hasNext()){
			retorno.add(scan.next());
		}
		
		return retorno;
	}
	
}
