package roleta_russa;

public class Partida {
	private int quantBalas;
	private String dificuldade;
	
	public Partida(String dificuldade) {
		this.dificuldade = dificuldade;
		this.quantBalas = (int) (Math.random()*5) + 2;
		
		System.out.println("-----------------");
		System.out.println("Nova partida iniciada");
		System.out.println("Dificuldade: " + dificuldade);
		System.out.println("numero de balas: " + quantBalas);
	}
}
