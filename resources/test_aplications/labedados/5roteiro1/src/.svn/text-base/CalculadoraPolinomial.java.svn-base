



public class CalculadoraPolinomial {
	
	public static int calcule(int constante, Polinomio polinomio){
		
		int[] chaves = polinomio.getChaves();
		int resultado = 0;
		
		for (int i = 0; i < chaves.length; i++) {
			resultado += (polinomio.getValor(chaves[i])) * (Math.pow(constante,chaves[i]));
		}
		return resultado;
	}
	
	
	
}		

