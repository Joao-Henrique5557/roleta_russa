package model;

public class Revolve {
	private int quantBalasTotal = 0;
	private boolean[] balas;
	private int quantBalasVerdadeiras;
	private int posAtual = 0;

	public void rearmazenarBalas() {
		this.posAtual = 0;
		this.quantBalasTotal = (int) (Math.random() * 5) + 2; // dois até 6
		this.balas = new boolean[quantBalasTotal];
		this.quantBalasVerdadeiras = (int) (Math.random() * (quantBalasTotal - 1)) + 1; // 1 até 5 a depender a quantidade de balas

		int colocadas = 0;
		while (colocadas < quantBalasVerdadeiras) { // preencher as verdadeiras
			int idx = (int) (Math.random() * quantBalasTotal); // seleciona a posição da verdadeira a depender da quantBalasTotal
			if (!balas[idx]) { // se posição for vazia, colocar bala
				balas[idx] = true;
				colocadas++;
			}
		}
	}

	public boolean dispararNoAlvo(Jogador alvo) {
		boolean tiroReal = balas[posAtual];
		if (tiroReal) { // se o tiro for real
			alvo.levarTiro();
		}
		posAtual++;
		return tiroReal; // retorna tipo de bala
	}

	public int getQuantBalasRestantes() {
		return quantBalasTotal - posAtual;
	}

	public int getQuantBalas() {
		return getQuantBalasRestantes();
	}

	public int getQuantBalasVerdadeiras() {
		int cont = 0;
		for (int i = posAtual; i < balas.length; i++)
			if (balas[i])
				cont++;
		return cont;
	}
}
