package model.Beans;

public class Usuario {
	// valores a definir
	private int id; 
	private String nome;
	private String senha;
	private String email;
	private int pontos = 0;

	// GETTER E SETTER DO ID (ADICIONADOS)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	// Construtor completo atualizado para incluir o id
	public Usuario(int id, String nome, String senha, String email, int pontos) {
		super();
		this.id = id;
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.pontos = pontos;
	}

	public Usuario() {
		super();
	}
}