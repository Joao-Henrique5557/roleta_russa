package roleta_russa;

import java.util.Scanner;

public class Jogo {

	public static void main(String[] args) {
		Scanner tc = new Scanner(System.in);
		
		// deseha jogar?
		System.out.print("Deseja jogar? (s/n): ");
		boolean doGame = tc.next().equals("s");
		
		while (doGame){
			// jogadores
			Jogador eu = new Jogador(tc);
			Jogador voce = new Jogador(tc);
			
			Partida ptd = new Partida();
			ptd.comecarPartida(tc, eu, voce);
			
			System.out.println("Fim de jogo");
			System.out.print("Deseja jogar? (s/n): ");
			doGame = tc.next().equals("s");
		}
		System.out.println("Fechando programa...");
		tc.close();
	}

}
