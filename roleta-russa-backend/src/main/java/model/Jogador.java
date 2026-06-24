package model;

public class Jogador {
	// valores padrão
	private int vidas = 3;
	private boolean isAlive = true;
	private String nome;

	public void levarTiro() {
		this.vidas--;
		if (this.vidas <= 0) {
			this.vidas = 0;
			this.isAlive = false;
		}
	}

	public int getVidas() {
		return vidas;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Jogador(int vidas, boolean isAlive, String nome) {
		super();
		this.vidas = vidas;
		this.isAlive = isAlive;
		this.nome = nome;
	}

	public Jogador() {
		super();
		// TODO Auto-generated constructor stub
	}
}
