package roleta_russa;

import java.util.Scanner;

public class Rodada {
	private String vezde;
	private int numRodada = 1; 
	private String dificuldade;
	
	private void mudarVez(Jogador eu, Jogador voce) {
		if (this.vezde == eu.getNome()) {
			this.vezde = voce.getNome();
		} else if (this.vezde == voce.getNome()) {
			this.vezde = eu.getNome();
		}
	}
	
	public void iniciarRodada(String dificuldade, Jogador eu, Jogador voce, Revolve rvv, Scanner tc) {
		System.out.println("Rodada: " + this.numRodada);
		
		this.vezde = eu.getNome(); // define primeiro jogador da rodada
		this.dificuldade = dificuldade;
		
		System.out.println("Vez de " + this.vezde);
		
		rvv.rearmazenarBalas(this.dificuldade); // recarrega as palas da rodada
		
		while(eu.getVidas() > 0 && voce.getVidas() > 0) {
			if(this.vezde == eu.getNome()) {
				System.out.print(this.vezde + " atira em você mesmo ou no oponente? você(1) oponente(2): ");
				int resp = tc.nextInt();
				if(resp == 1) {
					rvv.atirar(eu);
					mudarVez(eu, voce);
					System.out.println("Vez de " + this.vezde);
					System.out.println("-----------------");
				} else if (resp == 2) {
					rvv.atirar(voce);
					mudarVez(eu, voce);
					System.out.println("Vez de " + this.vezde);
					System.out.println("-----------------");
				}
			} else if (this.vezde == voce.getNome()) {
				System.out.print(this.vezde + " atira em você mesmo ou no oponente? você(1) oponente(2): ");
				int resp = tc.nextInt();
				if(resp == 1) {
					rvv.atirar(voce);
					mudarVez(eu, voce);
					System.out.println("Vez de " + this.vezde);
					System.out.println("-----------------");
				} else if (resp == 2) {
					rvv.atirar(eu);
					mudarVez(eu, voce);
					System.out.println("Vez de " + this.vezde);
					System.out.println("-----------------");
				}
			}
			verificarRevolve(rvv, this.dificuldade);
		}
	}

	private void verificarRevolve(Revolve rvv, String dificuldade) {
		if(rvv.getTempPosBalaAtual() > rvv.getBalas().length -1) {
			System.out.println("Revolve vazio");
			this.numRodada++;
			System.out.println("Rodada: " + this.numRodada);
			rvv.rearmazenarBalas(dificuldade);
		} else {
			System.out.println("capsula do revolve: " + (rvv.getTempPosBalaAtual() + 1));
		}
	}
}
