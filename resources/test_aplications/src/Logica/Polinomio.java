package Logica;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Classe para representar um polinômio. 
 * 
 * @author Allysson Makens
 * @author Carla Souza
 *
 */
public class Polinomio {
	
	private String polinomio;
	private String variavel;
	
	/**
	 * Construtor de um polinomio. Se o <tt>polinomio</tt> for vazio, o polinomio
	 * será igualado a 0.
	 * @param polinomio String com o polinomio.
	 * @param variavel Icognita do polinomio.
	 */
	public Polinomio(String polinomio, String variavel){
		if (polinomio.equals("")){
			this.polinomio = "0";
		} else {
			this.polinomio = polinomio;
		}
		this.variavel = variavel;
	}
	
	/**
	 * Retorna a icognita do polinômio.
	 * @return A variavel do polinômio.
	 */
	public String getVariavel(){
		return variavel;
	}
	
	/**
	 * Calcula o valor do polinomio quando a variavel tem valor <tt>variavel</tt>
	 * @param variavel Valor da variavel.
	 * @return Resultado do calculo.
	 */
	public String calculaPolinomio(int variavel){
		StringTokenizer st = new StringTokenizer(polinomio,"+,-", true);
		String expMonomioCalculados = "";
		while (st.hasMoreTokens()){
			String proximoToken = st.nextToken();
			if (proximoToken.equals("+") || proximoToken.equals("-")){
				expMonomioCalculados += proximoToken + " ";
			}
			else{
				Monomio monomio = new Monomio(proximoToken.trim() ,getVariavel());
				Double valor = monomio.calculaMonomio(variavel);
				expMonomioCalculados += (valor.intValue()) + " ";
			}	
		}
		
		return calculaExpressao(expMonomioCalculados.trim());
	}
	
	/**
	 * Calcula apenas os termos sem variável. Examplo:
	 * <p><blockquote><pre>
	 *     expressao = "2 + 4 + 9 + 7";
	 * </pre></blockquote><p>
	 * @param expressao Expressao a ser calculada.
	 * @return O resultado do calculo.
	 */
	private static String calculaExpressao(String expressao){
		StringTokenizer st = new StringTokenizer(expressao,"+,-", true);
		String proximoToken = st.nextToken().trim();
		if (proximoToken.equals("+") || proximoToken.equals("-")){
			proximoToken += st.nextToken().trim();
		}
		int soma = Integer.parseInt(proximoToken);
		String operador = "";
		while (st.hasMoreTokens()){
			proximoToken = st.nextToken().trim();
			if (proximoToken.equals("+") || proximoToken.equals("-")){
				operador = proximoToken;
			}
			else{
				if (operador.equals("+")){
					soma += Integer.parseInt(proximoToken);
				}else if (operador.equals("-")){
					soma -= Integer.parseInt(proximoToken);
				}
			}	
		}
		return soma + "";
	}
	
	/**
	 * Retorna o polinômio.
	 * @return Polinomio a ser retornado.
	 */
	public String getPolinomio() {
		return polinomio;
	}

	/**
	 * Modifica um polinômio.
	 * @param polinomio Novo polinomio.
	 */
	public void setPolinomio(String polinomio) {
		if (polinomio.equals("")){
			this.polinomio = "0";
		}else{
			this.polinomio = polinomio;
		}
	}

	/**
	 * Reduz um polinômio para a sua forma canônica. Examplo:
	 * <p><blockquote><pre>
	 *     p(x) = 5x + 2 + 2x3 -2 
	 * </pre></blockquote><p>
	 * Quando reduzido ele será:
	 * <p><blockquote><pre>
	 *     p(x) = 2x3 + 5x
	 * </pre></blockquote><p>
	 */
	public void reduza(){
		this.ordemDecrescente();
		StringTokenizer st = new StringTokenizer(polinomio,"+,-", true);
		Monomio monomio = new Monomio("",getVariavel());		
		String operador = "+";
		String polinomioReduzido = "";
		int soma = 0;
		String variavel = this.getVariavel();
		while (st.hasMoreTokens()){
			String proximoToken = st.nextToken().trim();
			if (proximoToken.equals("+") || proximoToken.equals("-")){
				operador = proximoToken;
			}
			else{
				Monomio monomioAuxiliar = new Monomio(proximoToken,getVariavel());
				if (monomio.mesmoGrau(monomioAuxiliar)){
					soma = calculaCoeficiente(operador, soma, monomioAuxiliar.getCoeficente());
				}else{
					if (soma != 0){
						polinomioReduzido += novoCoeficiente(soma) + variavel + monomio.getExpoente();
					}
					monomio = monomioAuxiliar;
					if (operador.equals("-")){
						soma = -monomio.getCoeficente();
					}else{
						soma = monomio.getCoeficente();
					}
				}
				
			}	
		}
		//tratamento para ultimo token
		if (soma != 0){
			polinomioReduzido += novoCoeficiente(soma) + variavel + monomio.getExpoente();
		}
		polinomioReduzido = polinomioReduzido.trim();
		if (polinomioReduzido.startsWith("+")){
			polinomioReduzido = polinomioReduzido.substring(1).trim();
		}
		
		setPolinomio(polinomioReduzido);
		this.minimizaPolinomio();
	}
	
	/**
	 * Soma ou subtrai os coeficientes de monomios de mesmo grau.
	 * @param operador Sinal: soma ou subtrai os monomios.
	 * @param soma Valor a ser somado com.
	 * @param valor Valor a ser adicionado em soma.
	 * @return A soma total.
	 */
	private static int calculaCoeficiente(String operador, int soma, int valor){
		if (operador.equals("+")){
			soma += valor;
		} else if (operador.equals("-")){
			soma -= valor;
		}
		return soma;
	}
	
	/**
	 * Retorna o novo coeficiente.
	 * @param soma
	 * @return O novo coeficiente.
	 */
	private static String novoCoeficiente(int soma){
		if (soma > 0)
			return " + " + soma;
		return String.valueOf(soma);
	}
	
	/**
	 * Organiza os termos do polinômio em ordem decrescente de grau.
	 */
	public void ordemDecrescente(){
		StringTokenizer st = new StringTokenizer(polinomio,"+,-", true);
		LinkedList<Monomio> listaMonomios = new LinkedList<Monomio>();
		LinkedList<String> listaSinais = new LinkedList<String>();
		
		String sinalInserir = "+";
		while (st.hasMoreTokens()){
			String proximoToken = st.nextToken().trim();
			if (proximoToken.equals("+") || proximoToken.equals("-")){
				sinalInserir = proximoToken;
			}
			else{
				boolean monomioInserido = false;
				Monomio monomioInserir = new Monomio(proximoToken,getVariavel());
				for (int i = 0; i < listaMonomios.size(); i++) {
					if (listaMonomios.get(i).compareTo(monomioInserir) == 1){
						listaMonomios.add(i, monomioInserir);
						listaSinais.add(i, sinalInserir);
						monomioInserido = true;
						break;
					}
					else if (listaMonomios.get(i).compareTo(monomioInserir) == 0){
						if (sinalInserir.equals("+")){
							listaSinais.add(i, sinalInserir);
							listaMonomios.add(i, monomioInserir);
							monomioInserido = true;
							break;
						}
					}
				}
				if (!monomioInserido){
					listaMonomios.addLast(monomioInserir);
					listaSinais.addLast(sinalInserir);
				}
			}
		}

		String polinomio = "";
		for (int i = 0; i < listaMonomios.size(); i++) {
			polinomio += listaSinais.get(i) + listaMonomios.get(i);
		}
		if (polinomio.startsWith("+")){
			polinomio = polinomio.substring(0, 0).replace("+","")
			                    + polinomio.substring(1); 
		}
		setPolinomio(polinomio);
	}
	
	/**
	 * Retira do polinomio dos termos com coeficiente igual a 0;	 
	 * Retira os coeficiente igual a 1;
	 * Retira os expoentes igual a 1, 0; 
	 */
	public void minimizaPolinomio(){
		StringTokenizer st = new StringTokenizer(polinomio,"+,-", true);
		String polinomioMinimizado = "";
		while (st.hasMoreTokens()){
			String proximoToken = st.nextToken().trim(); 
			Monomio monomio = new Monomio(proximoToken,getVariavel());
			if (proximoToken.equals("+") || proximoToken.equals("-")){
				polinomioMinimizado += proximoToken + " "; 
			}else if (monomio.getCoeficente() == 0){
				polinomioMinimizado += "0";
			}else if (monomio.getExpoente() == 0){
				polinomioMinimizado += monomio.getCoeficente() + " ";
			}else if (monomio.getCoeficente() == 1 && monomio.getExpoente() == 1){
				polinomioMinimizado += proximoToken.replace("1","") + " ";
			}else if (monomio.getCoeficente() == 1){
				polinomioMinimizado += proximoToken.replaceFirst("1","") + " ";
			}else if (monomio.getExpoente() == 1){
				int posicaoExpoente = proximoToken.length() - 1;
				polinomioMinimizado += proximoToken.substring(0,posicaoExpoente)
				                       + proximoToken.substring(posicaoExpoente).replace("1","") + " ";
			}else{
				polinomioMinimizado += proximoToken + " ";
			}
		}
		
		this.setPolinomio(polinomioMinimizado.trim());
	}
	
	/**
	 * Representa um polinomio como String.
	 */
	public String toString(){
		return getPolinomio();
	}
	
	
	/**
	 * Classe de um monomio.
	 * @author Allysson Makens
	 * @author Carla Souza
	 */
	private static class Monomio{
		
		private int coeficiente;
		private int expoente;
		private String variavel;
		
		/**
		 * Construtor de um monimio.
		 * @param monomio Monimio (coeficiente e expoente)
		 * @param variavel Variavel do monimio.
		 */
		public Monomio(String monomio, String variavel){
			coeficiente = retornaCoeficiente(monomio);
			expoente = retornaExpoente(monomio);
			this.variavel = variavel; 
		}
		
		/**
		 * Calcula o valor de um monimio a partir do valor da variavel.
		 * @param variavel Valor da variavel.
		 * @return Resultado do calculo.
		 */
		public Double calculaMonomio(int variavel) {
			return (coeficiente * (Math.pow(variavel, expoente)));
		}

		/**
		 * Retorna o expoente do monomio.
		 * @param monomio Monomio a pegar o expoente.
		 * @return O expoente do monomio.
		 */
		private static int retornaExpoente(String monomio){
			int i = monomio.length() - 1;
			String expoente = "";
			while (i >= 0 && Character.isDigit(monomio.charAt(i))){
				expoente = monomio.charAt(i) + "" + expoente;
				i--;
			}
			if (i < 0){
				expoente = "0";
			}
			if (expoente.equals("")){
				expoente = "1";
			}
			return Integer.parseInt(expoente);
		}
		
		/**
		 * Retorna o coeficiente de um monomio.
		 * @param monomio Monimio a saber o coeficiente.
		 * @return O valor do coeficiente.
		 */
		private static int retornaCoeficiente(String monomio){
			int i = 0;
			String coeficiente = "";
			while (i < monomio.length() && Character.isDigit(monomio.charAt(i))){
				coeficiente += monomio.charAt(i) + "";
				i++;
			}
			if (coeficiente.equals("")){
				coeficiente = "1";
			}
			
			return Integer.parseInt(coeficiente);
		}
		
		/**
		 * Compara dois monimios.
		 * @param monomio Monomio a ser comparado
		 * @return 1 se <tt>monimio</tt> for maior; 0 se iguals; -1 caso contraio.
		 */
		public int compareTo(Monomio monomio){
			if (monomio.getExpoente() > getExpoente()){
				return 1;
			}
			else if (mesmoGrau(monomio)){
				if (monomio.getCoeficente() > getCoeficente()){
					return 1;
				}
				else if (monomio.getCoeficente() == getCoeficente()){
					return 0;
				}
			}
			return -1;
		}
		
		/**
		 * Compara os expoentes de dois monimios
		 * @param monomio Monomio a ser comparado
		 * @return True se iguais, False caso contrario.
		 */
		public boolean mesmoGrau(Monomio monomio){
			return monomio.getExpoente() == getExpoente();
		}
		
		/**
		 * Retorna o coeficiente do monomio.
		 * @return Valor do coeficiente.
		 */
		public int getCoeficente(){
			return coeficiente;
		}
		
		/**
		 * Retorna o expoente do monomio.
		 * @return Valor do expoente.
		 */
		public int getExpoente(){
			return expoente;
		}
		
		/**
		 * Retorna a variavel do monomio.
		 * @return A variavel do monomio.
		 */
		public String getVariavel(){
			return variavel;
		}
		
		/**
		 * Representa o monomio como string.
		 */
		public String toString(){			
			return getCoeficente() + getVariavel() + getExpoente();
		}
	}
}
