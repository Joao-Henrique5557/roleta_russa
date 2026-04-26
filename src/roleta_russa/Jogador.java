package roleta_russa;

import java.util.Scanner;

public class Jogador {
	private String nome;
	private int vidas = 3;
	
	public Jogador(Scanner tc) {
		System.out.print("(Primeiro login) Qual é o seu nome? ");
		this.nome = tc.next();
	}
	
	public void levarTiro() {
		if(this.vidas > 0) {
			this.vidas -= 1;
			System.out.println("Vidas do " + this.nome + ": " + this.vidas);
		} else {
			System.out.println("Jogador " + this.nome + " morreu!");
		}
	}

	public String getNome() {
		return nome;
	}

	public int getVidas() {
		return vidas;
	}
}
