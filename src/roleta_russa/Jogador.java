package roleta_russa;

import java.util.Scanner;

public class Jogador {
	private String nome;
	
	public Jogador(String nome) {
		this.nome = nome;
	}
	
	public Jogador(Scanner tc) {
		System.out.print("(Primeiro login) Qual é o seu nome? ");
		this.nome = tc.next();
	}
}
