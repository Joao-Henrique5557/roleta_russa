package roleta_russa;

import java.util.Scanner;
import roleta_russa.Partida;
import roleta_russa.Tutorial;

public class Jogo {

	public static void main(String[] args) {
		Scanner tc = new Scanner(System.in);
		System.out.print("Deseja jogar? (s/n): ");
		
		boolean doGame = tc.next().equals("s");
		while (doGame){
			System.out.print("Deseja Tutorial? (s/n): ");
			if(tc.next().equals("s")) {
				Tutorial tt = new Tutorial();
				tt.mostrarTutorial();
			}
			
			System.out.print("Qual a dificuldade? (facil, medio, dificil: ");
			String dificuldade = tc.next();
			Partida ptd = new Partida(dificuldade);
			System.out.println("Fim de jogo");
			System.out.print("Deseja jogar? (s/n): ");
			doGame = tc.next().equals("s");
		}
		System.out.println("Fim de jogo");
		tc.close();
	}

}
