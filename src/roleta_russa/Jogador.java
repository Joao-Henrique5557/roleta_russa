package roleta_russa;

import java.util.Scanner;

public class Jogador {
	private String nome;
	private int vidas = 3;
	
	public Jogador(String nome) {
		this.nome = nome;
	}
	
	public Jogador(Scanner tc) {
		System.out.print("(Primeiro login) Qual é o seu nome? ");
		this.nome = tc.next();
	}
	
	public void atirarEmSi() {
		System.out.println("O jogador " + nome + " atirou em si");
	}
	public void atirarNoOponente(Jogador oponente) {
		System.out.println("O jogador " + nome + " atirou no jogador " + oponente.nome);
	}
}
