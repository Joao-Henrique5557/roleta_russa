package roleta_russa;

import java.util.Arrays;

public class Revolve {
	private int quantBalas;
	private boolean[] balas;
	private int quantBalasVerdadeiras;
	private int tempPosBalaAtual; 
	
	public boolean isVazio(String dificuldade) {
		if(this.tempPosBalaAtual > this.balas.length - 1) {
			System.out.println("Revolve vazio");
			rearmazenarBalas(dificuldade);
			return true;
		} else {
			System.out.println("capsula do revolve: " + (this.tempPosBalaAtual + 1));
			return false;
		}
	}
	
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
	
	public void rearmazenarBalas(String dificuldade) {
		System.out.println("Recarregando Revolve");
		System.out.println("------------------");
		
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
		
		mostrarInfoDoRevolve();
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
	
	public void mostrarInfoDoRevolve() {
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
		System.out.println("-----------------");
	}
	
	public void mostrarInfoDoRevolve(String lista) {
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
		System.out.println("-----------------");
		
		if (lista.equals("lista")) {
			System.out.println("balas: " + Arrays.toString(balas));
		}
	}

	public boolean[] getBalas() {
		return balas.clone();
	}

	public int getTempPosBalaAtual() {
		return tempPosBalaAtual;
	}

	public int getQuantBalas() {
		return quantBalas;
	}

	public void setQuantBalas(int quantBalas) {
		this.quantBalas = quantBalas;
	}

	public int getQuantBalasVerdadeiras() {
		return quantBalasVerdadeiras;
	}

	public void setQuantBalasVerdadeiras(int quantBalasVerdadeiras) {
		this.quantBalasVerdadeiras = quantBalasVerdadeiras;
	}

	public void setBalas(boolean[] balas) {
		this.balas = balas;
	}

	public void setTempPosBalaAtual(int tempPosBalaAtual) {
		this.tempPosBalaAtual = tempPosBalaAtual;
	}
}
