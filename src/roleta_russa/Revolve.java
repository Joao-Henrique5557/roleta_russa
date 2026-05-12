package roleta_russa;

import java.util.Arrays;

public class Revolve {
	private int quantBalas = 0;
	private boolean[] balas;
	private int quantBalasVerdadeiras;
	private int tempPosBalaAtual = 0;

	public boolean isVazio(String dificuldade) {
		if (this.balas == null || this.tempPosBalaAtual > this.balas.length - 1) {
			System.out.println("Revolve vazio");
			rearmazenarBalas(dificuldade);
			this.mostrarInfoDoRevolve();
			return true;
		}
		return false;
	}

	public void atirar(Jogador alvo) {
		if (this.balas[this.tempPosBalaAtual]) {
			System.out.println("Jogador " + alvo.getNome() + " levou um tiro verdadeiro!");
			alvo.levarTiro();
		} else {
			System.out.println("Jogador " + alvo.getNome() + " levou um tiro falso! (clique)");
		}
		this.tempPosBalaAtual++;
	}

	public void rearmazenarBalas(String dificuldade) {
		System.out.println("Recarregando Revolve...");

		this.tempPosBalaAtual = 0;
		// numero entre 2 e 6
		this.quantBalas = (int) (Math.random() * 5) + 2;

		switch (dificuldade) {
			case "facil":
				// 1 bala verdadeira, máximo 3 slots
				this.quantBalasVerdadeiras = 1;
				if (this.quantBalas > 3) {
					this.quantBalas = 3;
				}
				break;
			case "medio":
				// 2 balas verdadeiras, mínimo 4 slots
				this.quantBalasVerdadeiras = 2;
				if (this.quantBalas < 4) {
					this.quantBalas = 4;
				}
				break;
			case "dificil":
				// 3 balas verdadeiras, mínimo 5 slots
				this.quantBalasVerdadeiras = 3;
				if (this.quantBalas < 5) {
					this.quantBalas = 5;
				}
				break;
			default:
				this.quantBalasVerdadeiras = 1;
				if (this.quantBalas > 3) {
					this.quantBalas = 3;
				}
				break;
		}

		this.balas = new boolean[this.quantBalas];

		for (int i = 0; i < this.quantBalasVerdadeiras; i++) {
			sortearBalaVerdadeira();
		}
	}

	private void sortearBalaVerdadeira() {
		int posicaoVerdadeira = (int) (Math.random() * this.quantBalas);
		if (this.balas[posicaoVerdadeira]) {
			sortearBalaVerdadeira();
		} else {
			this.balas[posicaoVerdadeira] = true;
		}
	}

	public void mostrarInfoDoRevolve() {
		System.out.println("Número de balas: " + this.quantBalas);
		System.out.println("Número de balas verdadeiras: " + this.quantBalasVerdadeiras);
		System.out.println("-----------------");
	}

	public void mostrarInfoDoRevolve(String lista) {
		if (this.quantBalas == 0 || this.quantBalasVerdadeiras == 0) {
			System.out.println("Erro: revólver não carregado.");
			return;
		}
		System.out.println("Número de balas: " + this.quantBalas);
		System.out.println("Número de balas verdadeiras: " + this.quantBalasVerdadeiras);
		System.out.println("-----------------");
		if (lista.equals("lista")) {
			System.out.println("Balas: " + Arrays.toString(balas));
		}
	}

	public boolean[] getBalas() { return balas.clone(); }
	public int getTempPosBalaAtual() { return tempPosBalaAtual; }
	public int getQuantBalas() { return quantBalas; }
	public void setQuantBalas(int quantBalas) { this.quantBalas = quantBalas; }
	public int getQuantBalasVerdadeiras() { return quantBalasVerdadeiras; }
	public void setQuantBalasVerdadeiras(int q) { this.quantBalasVerdadeiras = q; }
	public void setBalas(boolean[] balas) { this.balas = balas; }
	public void setTempPosBalaAtual(int pos) { this.tempPosBalaAtual = pos; }
}
