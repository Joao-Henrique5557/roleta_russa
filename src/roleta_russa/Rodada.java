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
		
		this.vezde = this.eu.getNome(); // define primeiro jogador da rodada
		this.dificuldade = dificuldade;
		this.rvv = rvv;
		this.tc = tc;
		
		rvv.rearmazenarBalas(this.dificuldade); // recarrega as palas da rodada
		
		while(eu.isAlive() && voce.isAlive()) {
			iniciarTurno();
			if(rvv.isVazio(this.dificuldade)) {
				this.numRodada++;
			}
		}
	}
	
	private void iniciarTurno() {
		System.out.println("Vez de " + this.vezde);
		System.out.println("Rodada: " + this.numRodada);
		System.out.println("----------------");
		
		System.out.print(this.vezde + " atira em você mesmo ou no oponente? você(1) oponente(2): ");
		int resp = this.tc.nextInt();
		System.out.println("----------------");
		
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
