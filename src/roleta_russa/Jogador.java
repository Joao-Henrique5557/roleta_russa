package roleta_russa;

public class Jogador {
	private String nome = "guest";
	private int vidas = 2;
	private boolean isAlive = true;
	private int quantCigarros = 0;
	private int quantFacas = 0;
	private int quantTelefones = 0;
	private int quantLupas = 0;

	public void levarTiro() {
		this.vidas--;
		if (this.vidas <= 0) {
			this.vidas = 0;
			System.out.println("Jogador " + this.nome + " morreu!");
			this.isAlive = false;
		} else {
			System.out.println("Vidas do " + this.nome + ": " + this.vidas);
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

	public void fumar() {
		if (this.quantCigarros > 0) {
			this.vidas++;
			this.quantCigarros--;
			System.out.println(this.nome + " fumou um cigarro e recuperou 1 vida. Vidas: " + this.vidas);
		} else {
			System.out.println("Sem cigarros...");
		}
	}

	public void usarTelefone(Revolve rvv) {
		// Futuramente: revelar uma bala aleatória
	}

	public void usarLupa(Revolve rvv) {
		// Futuramente: ver a bala atual
	}

	public void cortarCano(Revolve rvv) {
		// Futuramente: aumentar dano do próximo tiro
	}

	public int getQuantCigarros() { return quantCigarros; }
	public void setQuantCigarros(int q) { this.quantCigarros = q; }
	public int getQuantFacas() { return quantFacas; }
	public void setQuantFacas(int q) { this.quantFacas = q; }
	public int getQuantTelefones() { return quantTelefones; }
	public void setQuantTelefones(int q) { this.quantTelefones = q; }
	public int getQuantLupas() { return quantLupas; }
	public void setQuantLupas(int q) { this.quantLupas = q; }
}
