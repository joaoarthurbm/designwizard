
import Controladora.Calculadora;
import java.util.*;

/**
 * Classe que executa a calculadora
 * @author Allysson Makens
 * @author Carla Souza
 *
 */
public class CalculadoraMain {

	public static String calculadora(String entrada, Calculadora calculadora){
		String saida = calculadora.trataEntrada(entrada);
		return saida;
	}

	public static void main(String[] args) {
		
		Calculadora calculadora = new Calculadora();
		Scanner sc = new Scanner(System.in);
		String entrada = "";
		while (!entrada.equals("exit")){
			entrada = sc.nextLine();
			System.out.println(calculadora(entrada, calculadora));
		}
	}

}
