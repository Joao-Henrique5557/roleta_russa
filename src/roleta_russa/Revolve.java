package roleta_russa;

import java.util.Arrays;

public class Revolve {
	private int quantBalas;
	private boolean[] balas;
	private int quantBalasVerdadeiras;
	private int tempPosBalaAtual; 
	
	public void atirar(Jogador alvo) {
		if(this.balas[this.tempPosBalaAtual] == true) {
			System.out.println("Jogador " + alvo.getNome() + " levou um tiro verdadeio!");
			alvo.levarTiro();
			this.tempPosBalaAtual++;
		} else {
			System.out.println("Jogador " + alvo.getNome() + " levou um tiro falso!");
			this.tempPosBalaAtual++;
		}
	}
	
	private void sortearBalaVerdadeira() {
		if (this.quantBalas == 0) {
			System.out.println("Erro");
		}
		
		if (this.balas == null) {
			System.out.println("Erro");
		}
		
		int posicaoVerdadeira = (int) (Math.random() * (this.quantBalas)) ; // numero aleatorio entre 0 e 6
		if (this.balas[posicaoVerdadeira] == true) {
			sortearBalaVerdadeira();
		} else {
			this.balas[posicaoVerdadeira] = true;
		}
	}
	
	public void rearmazenarBalas(String dificuldade) {
		this.tempPosBalaAtual = 0;
		this.quantBalas = (int) (Math.random()*5) + 2; // numero entre 2 e 6
		
		switch(dificuldade){
			case "facil":
				this.quantBalasVerdadeiras = 1;
				if (this.quantBalas >= 3) { // balas podem ser 2 ou 3 contra 1 bala V
					this.quantBalas = 3;
				}
				break;
			case "medio":
				this.quantBalasVerdadeiras = 2;
				if (this.quantBalas <= 4 && this.quantBalas < 6) {
					this.quantBalas += 1; 
				}
				break;
			case "dificil":
				this.quantBalasVerdadeiras = 3;
				if (this.quantBalas <= 3 || this.quantBalas < 6) {
					this.quantBalas += 1;
				}
				break;
		}
		
		this.balas = new boolean[this.quantBalas]; // exemplo 6
		
		for(int i=0; i<this.quantBalasVerdadeiras; i++) { // exemplo: balas verdadeiras: 2
			sortearBalaVerdadeira();
		}
		
		mostrarInfoDoRevolve("lista");
	}
	
//	private void mostrarInfoDoRevolve() {
//		if(this.quantBalas == 0) {
//			System.out.println("Erro");
//			return;
//		}
//		if(this.quantBalasVerdadeiras == 0) {
//			System.out.println("Erro");
//			return;
//		}
//		
//		System.out.println("numero de balas: " + this.quantBalas);
//		System.out.println("numero de balas verdadeiras: " + this.quantBalasVerdadeiras);
//	}
	
	private void mostrarInfoDoRevolve(String lista) {
		if(this.quantBalas == 0) {
			System.out.println("Erro");
			return;
		}
		if(this.quantBalasVerdadeiras == 0) {
			System.out.println("Erro");
			return;
		}
		
		System.out.println("numero de balas: " + this.quantBalas);
		System.out.println("numero de balas verdadeiras: " + this.quantBalasVerdadeiras);
		
		if (lista == "lista") {
			System.out.println("balas: " + Arrays.toString(balas));
		}
	}

	public boolean[] getBalas() {
		return balas;
	}

	public int getTempPosBalaAtual() {
		return tempPosBalaAtual;
	}
}
