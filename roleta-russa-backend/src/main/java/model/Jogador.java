package model;

public class Jogador {
	// valores a definir
	private String nome;
	private String senha;
	private String email;
	private int pontos = 0;
	
	// valores padrão
	private int vidas = 3;
	private boolean isAlive = true;

	public void levarTiro() {
		this.vidas--;
		if (this.vidas <= 0) {
			this.vidas = 0;
			this.isAlive = false;
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
}
