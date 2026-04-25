package roleta_russa;

import java.util.Scanner;

public class Partida {
	private String dificuldade;
	private Jogador voce;
	
	public Partida(Scanner tc, Jogador eu) {
		voce = new Jogador("Desafiante");
		
		System.out.println("-----------------");
		System.out.println("Nova partida iniciada");
		System.out.print("Qual a dificuldade? (facil, medio, dificil): ");
		this.dificuldade = tc.next();
		System.out.println("Dificuldade: " + dificuldade);
		System.out.println("-----------------");
		
		Rodada rdd = new Rodada(dificuldade);
		
	}
}
