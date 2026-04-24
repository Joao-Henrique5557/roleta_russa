package roleta_russa;

public class Rodada {
	private int quantBalas;
	
	public Rodada() {
		this.quantBalas = (int) (Math.random()*5) + 2;
		
		System.out.println("numero de balas: " + quantBalas);
	}
}
