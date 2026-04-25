package roleta_russa;

import java.util.List;

public class Revolve {
	private int quantBalas;
	private boolean[] balas;
	private int quantBalasVerdadeiras;
	
	public Revolve(String dificuldade) {
		this.quantBalas = (int) (Math.random()*5) + 2; // numero entre 2 e 6
		
		switch(dificuldade){
			case "facil":
				this.quantBalasVerdadeiras = 1;
				if (this.quantBalas >= 3) {
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
				this.quantBalas += 2;
				break;
		}
		System.out.println("numero de balas: " + this.quantBalas);
		System.out.println("numero de balas verdadeiras: " + this.quantBalasVerdadeiras);
		
	}

	public void atirar() {
		
	}
}
