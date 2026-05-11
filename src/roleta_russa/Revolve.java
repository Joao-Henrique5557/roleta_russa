package roleta_russa;

import java.util.Arrays;

public class Revolve {
	private int quantBalas = 0;
	private boolean[] balas;
	private int quantBalasVerdadeiras;
	private int tempPosBalaAtual = 0; 
	
	public boolean isVazio(String dificuldade) {
		if(this.balas == null) {
			System.out.println("Revolve vazio");
			rearmazenarBalas(dificuldade);
			this.mostrarInfoDoRevolve();
			return true;
		}
		if(this.tempPosBalaAtual > this.balas.length - 1) {
			System.out.println("Revolve vazio");
			rearmazenarBalas(dificuldade);
			this.mostrarInfoDoRevolve();
			return true;
		}
		return false;
	}
	
	public void atirar(Jogador alvo) {
		if(this.balas[this.tempPosBalaAtual] == true) {
			System.out.println("Jogador " + alvo.getNome() + " levou um tiro verdadeiro!");
			alvo.levarTiro();
			this.tempPosBalaAtual++;
		} else {
			System.out.println("Jogador " + alvo.getNome() + " levou um tiro falso!");
			this.tempPosBalaAtual++;
		}
	}
	
	public void rearmazenarBalas(String dificuldade) {
		System.out.println("Recarregando Revolve");
		this.tempPosBalaAtual = 0;
		this.quantBalas = (int) (Math.random()*5) + 2;
		
		switch(dificuldade){
			case "facil":
				this.quantBalasVerdadeiras = 1;
				if (this.quantBalas >= 3) this.quantBalas = 3;
				break;
			case "medio":
				this.quantBalasVerdadeiras = 2;
				if (this.quantBalas <= 4 && this.quantBalas < 6) this.quantBalas += 1;
				break;
			case "dificil":
				this.quantBalasVerdadeiras = 3;
				if (this.quantBalas <= 3 || this.quantBalas < 6) this.quantBalas += 1;
				break;
			default:
				this.quantBalasVerdadeiras = 2;
		}
		
		this.balas = new boolean[this.quantBalas];
		for(int i=0; i<this.quantBalasVerdadeiras; i++) {
			sortearBalaVerdadeira();
		}
	}
	
	private void sortearBalaVerdadeira() {
		int pos = (int) (Math.random() * this.quantBalas);
		if (this.balas[pos]) sortearBalaVerdadeira();
		else this.balas[pos] = true;
	}
	
	public void mostrarInfoDoRevolve() {
		System.out.println("Balas totais: " + this.quantBalas);
		System.out.println("Balas verdadeiras: " + this.quantBalasVerdadeiras);
		System.out.println("-----------------");
	}

	public boolean[] getBalas()              { return balas.clone(); }
	public int getTempPosBalaAtual()         { return tempPosBalaAtual; }
	public int getQuantBalas()               { return quantBalas; }
	public void setQuantBalas(int q)         { this.quantBalas = q; }
	public int getQuantBalasVerdadeiras()    { return quantBalasVerdadeiras; }
	public void setQuantBalasVerdadeiras(int q) { this.quantBalasVerdadeiras = q; }
	public void setBalas(boolean[] b)        { this.balas = b; }
	public void setTempPosBalaAtual(int t)   { this.tempPosBalaAtual = t; }
}
