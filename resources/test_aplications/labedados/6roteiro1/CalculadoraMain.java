/*
 * @(#)CalculadoraPolinomial.java 1.0 06/04/06
 *
 * You can modify the template of this file in the
 * directory ..\JCreator\Templates\Template_1\Project_Name.java
 *
 * You can also create your own project template by making a new
 * folder in the directory ..\JCreator\Template\. Use the other
 * templates as examples.
 *
 */


//import java.awt.*;
//import java.awt.event.*;
import java.util.*;
//import java.util.regex.Pattern;
import java.io.*;
class CalculadoraMain {
	
	
	public static final String banner  = new String("*******************************************************************************\n"+
													"******************            Calculadora Polinomial                ***********\n"+
						                            "******************   Ufcg - Universidade Federal de Campina Grande  ***********\n"+
						                            "******************         Strutura de Dados e Algoritmos           ***********\n"+
						                            "******************            Alunos: Helio e Ronaldo               ***********\n"+
	                                                "*******************************************************************************");
	                                                
	public static final String help = new String("*****************************************************************************\n"+
												 "******** COMANDOS            |             FUNÇÃO          ******************\n"+
												 "********  def                |        define uma equação   ******************\n"+
												 "********  print              |        imprime uma equação  ******************\n"+
												 "*****************************************************************************");
	
	public static void iniciaCalculadora(){
		Map equacoes = new HashMap();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try{
			boolean flag =true;
			while(flag == true){
				String entrada = in.readLine();
								
				//define uma equação polinomial
				if(entrada.contains("def")){
					Scanner  comandos = new Scanner(entrada).useDelimiter("[def()=]+");
					String nome =  comandos.next();
					//System.out.println(nome);
					comandos.next();
					String equacao = "";
					while(comandos.hasNext()){
						equacao += comandos.next();
					}
					
					EquacaoPolinomial equa = new EquacaoPolinomial(nome.trim(),equacao.trim());
					equacoes.put(equa.getNome(),equa.getEquacao());
					System.out.println("ok");
				}
				
				if(entrada.equals("exit")){
					flag = false;
					System.out.println("bye");
				}
				
				if(entrada.contains("print")){
					
					try{
						Scanner  comandos = new Scanner(entrada);
						comandos.next();
						String nome = comandos.next();
						
						if(nome.contains("(")){
							int ind = nome.indexOf("(");
							String poli = nome.substring(0,ind);
							int num = Integer.parseInt(nome.substring(ind+1 , nome.length()-1));
							
							EquacaoPolinomial p = new EquacaoPolinomial((String)equacoes.get(poli));
							System.out.println(p.calculaEquacao(num));
						}
						else{
							String poli =(String) equacoes.get(nome);
							System.out.println(nome + "(x) = "+ poli);
						}
					}catch(Exception e){}
				}
				
			}
		}catch(Exception e){}
	}
	 
	public static void main(String args[]) {
		CalculadoraMain.iniciaCalculadora();
	}
}
