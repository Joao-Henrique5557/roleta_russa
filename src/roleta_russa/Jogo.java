package roleta_russa;

import java.util.Scanner;

import multiplayer.ServerLocal;

public class Jogo {

	public static void main(String[] args) {
		Scanner tc = new Scanner(System.in);
		
		System.out.println("Iniciando programa...");
		System.out.print("Deseja jogar em um servidor(1) ou jogar em duas pessoas(2)? ");
		int resp = tc.nextInt();
		if(resp == 1) {
			System.out.println("Verificando lista de sevidores locais...");
			System.out.println("Verificando lista de sevidores globais...");
			System.out.print("Deseja entrar em alguma sala? (s/n): ");
			String resp1 = tc.next();
			if(resp1.equals("s")) {
				System.out.print("Qual tipo de servidor você deseja entrar? ");
			} else if (resp1.equals("n")) {
				System.out.print("Deseja criar uma sala? (s/n): ");
				resp1 = tc.next();
				if(resp1.equals("s")) {
					System.out.print("Deseja iniciar um novo servidor local(1) ou global(2)? ");
					resp = tc.nextInt();
					if(resp == 1) {
						System.out.println("Iniciando servidor local...");
						System.out.println("--------------------------------------");
						ServerLocal svd = new ServerLocal();
						svd.iniciarLocal();
						Partida ptd = new Partida();
					} else if (resp == 2) {
						System.out.println("Iniciando servidor global...");
					}
				} else if(resp1.equals("n")) {
					System.out.println("Cancelando operação...");
				}
			}
		} else if (resp == 2) {
			Jogador eu = new Jogador(tc);
			Jogador voce = new Jogador(tc);
			
			System.out.println("------------------------");
			
			Partida ptd = new Partida();
			ptd.comecarPartida(tc, eu, voce);
		}
		System.out.println("Fechando programa...");
	}
}