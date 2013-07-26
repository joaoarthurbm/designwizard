

/**
 * 
 * @author adauto e ighor
 *
 */

public class CalculadoraPolinomial {
	
	/**
	 * Metodo que multiplica uma constante por um polinomio
	 * @param constante
	 * @param polinomio
	 * @return um polinomio
	 */
	public static int calcule(int constante, Polinomio polinomio){
		
		int[] chaves = polinomio.getChaves();
		int resultado = 0;
		
		for (int i = 0; i < chaves.length; i++) {
			resultado += (polinomio.getValor(chaves[i])) * (Math.pow(constante,chaves[i]));
		}
		return resultado;
	}
	
	/**
	 * Metodo para multiplicar um polinomio por uma constante quando o polinomio tem uma constante dentro dele.
	 * @param poli
	 * @param multiplicador
	 * @param variavel
	 * @return um polinomio
	 */	
	public static String multiplique(Polinomio poli, int multiplicador,String variavel){
		
		if (multiplicador == 0) return "(" + variavel + ")= 0"; 
		
		int[] chaves = poli.getChaves();
		String retorno = "(" + variavel + ")= ";
		
		for (int i = 0; i < chaves.length; i++) {
			
			int coeficiente = poli.getValor(chaves[i]) * multiplicador;
			if((coeficiente > 0)&&(i != 0)){
				retorno+= "+ ";
			}
			retorno += coeficiente + variavel + chaves[i] + " ";
			
		}
		return retorno.trim();
		
	}
	
	/**
	 * Metodo para multiplicar um polinomio por outro polinomio.
	 * @param p1
	 * @param p2
	 * @param variavel
	 * @return um polinomio
	 */
	 
	public static String multiplique(Polinomio p1, Polinomio p2, String variavel){
		
		int[] chaves1 = p1.getChaves();
		int[] chaves2 = p2.getChaves();
		String retorno = "(" + variavel + ")= ";
		
		
		if ((chaves1.length == 0) || (chaves2.length == 0)){
			return retorno + 0;
		}
		
		int controle = 0;
		for (int i = 0; i < chaves1.length; i++) {
			for (int j = 0; j < chaves2.length; j++) {
				int coeficiente = p1.getValor(chaves1[i]) * 
									p2.getValor(chaves2[j]);
				int expoente = chaves1[i] + chaves2[j];
				if((coeficiente > 0) && (controle != 0)){
					retorno+= "+ ";
				}
				retorno += coeficiente + variavel + expoente + " ";
				controle++;	
			}
				
		}
		return retorno.trim();	
	}
}		

