package roleta_russa;

import java.util.Scanner;

public class Rodada {
	private String vezde;
	private int numRodada = 1;
	private String dificuldade;
	private Jogador eu;
	private Jogador voce;
	private Revolve rvv;
	private Scanner tc;

	public void iniciarRodada(String dificuldade, Jogador eu, Jogador voce, Revolve rvv, Scanner tc) {
		this.eu = eu;
		this.voce = voce;
		this.vezde = this.eu.getNome(); // primeiro jogador da rodada
		this.dificuldade = dificuldade;
		this.rvv = rvv;
		this.tc = tc;

		while (eu.isAlive() && voce.isAlive()) {
			iniciarTurno();
		}

		// Anunciar vencedor
		if (eu.isAlive()) {
			System.out.println("🏆 " + eu.getNome() + " venceu a partida!");
		} else {
			System.out.println("🏆 " + voce.getNome() + " venceu a partida!");
		}
	}

	private void iniciarTurno() {
		boolean eraVazio = rvv.isVazio(this.dificuldade); // recarrega se vazio

		if (eraVazio) {
			this.numRodada++;
		}

		System.out.println("Vez de: " + this.vezde);
		System.out.println("Rodada: " + this.numRodada);
		System.out.println("----------------");
		System.out.print(this.vezde + " atira em você mesmo(1) ou no oponente(2)? ");
		int resp = this.tc.nextInt();
		System.out.println("----------------");

		// CORRIGIDO: usar .equals() para comparar Strings
		if (this.vezde.equals(this.eu.getNome())) {
			if (resp == 1) {
				this.rvv.atirar(this.eu);
				// Atirar em si mesmo e errar: mantém a vez
			} else if (resp == 2) {
				this.rvv.atirar(this.voce);
				mudarVez(); // Atirar no oponente sempre passa a vez
			}
		} else if (this.vezde.equals(this.voce.getNome())) {
			if (resp == 1) {
				this.rvv.atirar(this.voce);
			} else if (resp == 2) {
				this.rvv.atirar(this.eu);
				mudarVez();
			}
		}

		System.out.println("-----------------");
	}

	private void mudarVez() {
		if (this.vezde.equals(eu.getNome())) {
			this.vezde = voce.getNome();
		} else {
			this.vezde = eu.getNome();
		}
	}
}
