package roleta_russa;

import java.io.PrintWriter;
import java.util.Scanner;

public class Jogador {
	private String nome;
	private int vidas = 2;
	private boolean isAlive = true;
	private int quantCigarros = 0;
	private int quantFacas = 0;
	private int quantTelefones = 0;
	private int quantLupas = 0;
	PrintWriter canalJogador = null; // ????
	
	public Jogador(Scanner tc) {
		if(this.nome == null) {
			System.out.print("Qual o nome do jogador? ");
			this.nome = tc.next();
		}
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

	public int getQuantCigarros() {
		return quantCigarros;
	}

	public void setQuantCigarros(int quantCigarros) {
		this.quantCigarros = quantCigarros;
	}

	public int getQuantFacas() {
		return quantFacas;
	}

	public void setQuantFacas(int quantFacas) {
		this.quantFacas = quantFacas;
	}

	public int getQuantTelefones() {
		return quantTelefones;
	}

	public void setQuantTelefones(int quantTelefones) {
		this.quantTelefones = quantTelefones;
	}

	public int getQuantLupas() {
		return quantLupas;
	}

	public void setQuantLupas(int quantLupas) {
		this.quantLupas = quantLupas;
	}

	public PrintWriter getCanalJogador() {
		return canalJogador;
	}

	public void setCanalJogador(PrintWriter canalJogador) {
		this.canalJogador = canalJogador;
	}
}
