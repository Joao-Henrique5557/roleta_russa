package roleta_russa;

import java.util.Scanner;

public class Jogador {
	private String nome = "guest";
	private int vidas = 2;
	private boolean isAlive = true;
	private int quantCigarros = 0;
	private int quantFacas = 0;
	private int quantTelefones = 0;
	private int quantLupas = 0;
	
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
	
	void fumar(Jogador atual) {
		if(atual.quantCigarros > 0) {
			atual.vidas++;
			atual.quantCigarros--;
		} else {
			System.out.println("Sem cigarros...");
		}
	}
	
	public void usarTelefone(Revolve rvv) {
		
	}

	public void usarLupa(Revolve rvv) {
		
	}

	public void cortarCano(Revolve rvv) {
		
	}
}
