

public enum Comando {
	DEF,REDUZA,PRINT,EXIT,ERROR;

	/**
	 * Metodo que seleciona o comando desejado
	 * @param comando
	 * @return um comando
	 */
	
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
}
	

	