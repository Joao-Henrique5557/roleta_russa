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
		System.out.println("Rodada: " + this.numRodada);
		
		this.eu = eu;
		this.voce = voce;
		
		this.vezde = this.eu.getNome(); // define primeiro jogador da rodada
		this.dificuldade = dificuldade;
		this.rvv = rvv;
		this.tc = tc;
		
		rvv.rearmazenarBalas(this.dificuldade); // recarrega as palas da rodada
		
		while(eu.getVidas() >= 0 && voce.getVidas() >= 0) {
			iniciarTurno();
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
	
	private void iniciarTurno() {
		System.out.println("Vez de " + this.vezde);
		System.out.print(this.vezde + " atira em você mesmo ou no oponente? você(1) oponente(2): ");
		int resp = this.tc.nextInt();
		if (this.vezde == this.eu.getNome()) {
			if(resp == 1) {
				this.rvv.atirar(this.eu);
				System.out.println("-----------------");
			} else if (resp == 2) {
				this.rvv.atirar(this.voce);
				mudarVez(this.eu, this.voce);
				System.out.println("-----------------");
			}
		} else if (this.vezde == this.voce.getNome()) {
			if(resp == 1) {
				this.rvv.atirar(this.voce);
				System.out.println("-----------------");
			} else if (resp == 2) {
				this.rvv.atirar(this.eu);
				mudarVez(this.eu, this.voce);
				System.out.println("-----------------");
			}
		}
	}
	
	private void mudarVez(Jogador eu, Jogador voce) {
		if (this.vezde == eu.getNome()) {
			this.vezde = voce.getNome();
		} else if (this.vezde == voce.getNome()) {
			this.vezde = eu.getNome();
		}
	}
}
