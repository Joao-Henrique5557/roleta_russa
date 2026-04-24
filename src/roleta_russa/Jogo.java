package roleta_russa;

import java.util.Scanner;

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
			
			Jogador eu = new Jogador(tc);
			
			Partida ptd = new Partida(tc, eu);
			System.out.println("Fim de jogo");
			System.out.print("Deseja jogar? (s/n): ");
			doGame = tc.next().equals("s");
		}
		System.out.println("Fim de jogo");
		tc.close();
	}

}
