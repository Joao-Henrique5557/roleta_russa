package roleta_russa;

import java.util.Scanner;

public class Jogador {
	private String nome;
	private int vidas = 2;
	private boolean isAlive = true;
	
	public Jogador(Scanner tc) {
		System.out.print("(Primeiro login) Qual é o seu nome? ");
		this.nome = tc.next();
		System.out.println("-------- Olá " + this.nome + " --------------");
	}
	
	public void levarTiro() {
		if(this.vidas > 0) {
			setVidas(getVidas() - 1);
			System.out.println("Vidas do " + this.nome + ": " + this.vidas);
		} else if (this.vidas <= 0) {
			System.out.println("Jogador " + this.nome + " morreu!");
			this.isAlive = false;
		}
	}

	public String getNome() {
		return nome;
	}

	public int getVidas() {
		return vidas;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
