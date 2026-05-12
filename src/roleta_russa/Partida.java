package roleta_russa;

import java.util.Scanner;

public class Partida {
	private String dificuldade;

	public void comecarPartida(Scanner tc, Jogador eu, Jogador voce) {
		Revolve rvv = new Revolve();

		System.out.println("Nova partida iniciada");
		System.out.print("Qual a dificuldade? (facil, medio, dificil): ");
		this.dificuldade = tc.next();
		System.out.println("-----------------");

		Rodada rdd = new Rodada();
		rdd.iniciarRodada(dificuldade, eu, voce, rvv, tc);
	}
}
