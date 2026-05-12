package roleta_russa;

import java.util.Scanner;

import multiplayer.Cliente;
import multiplayer.ServerLocal;

public class Jogo {

	public static void main(String[] args) {
		Scanner tc = new Scanner(System.in);

		System.out.println("=== ROLETA RUSSA ===");
		System.out.print("Deseja jogar em rede(1) ou local com dois jogadores(2)? ");
		int resp = tc.nextInt();

		if (resp == 1) {
			System.out.println("----------------------------");
			System.out.print("Deseja criar uma sala(1) ou entrar em uma sala(2)? ");
			int opcaoRede = tc.nextInt();

			if (opcaoRede == 1) {
				// HOST: cria a sala E joga
				System.out.println("Iniciando servidor local...");
				System.out.println("--------------------------------------");
				ServerLocal svd = new ServerLocal();
				svd.iniciarLocal(tc); // host informa seu nome e aguarda oponente

			} else if (opcaoRede == 2) {
				// CLIENTE: entra em uma sala existente
				Cliente cliente = new Cliente();
				boolean conectou = cliente.conectar(tc);
				if (conectou) {
					System.out.println("Aguardando o host iniciar a partida...");
					// Futuramente: receber estado do jogo via socket e jogar remotamente
				} else {
					System.out.println("Não foi possível conectar ao servidor.");
				}
			}

		} else if (resp == 2) {
			// LOCAL: dois jogadores no mesmo teclado
			System.out.print("Qual o nome do primeiro jogador? ");
			String nomeJ1 = tc.next();
			System.out.print("Qual o nome do segundo jogador? ");
			String nomeJ2 = tc.next();

			Jogador eu = new Jogador();
			eu.setNome(nomeJ1);

			Jogador voce = new Jogador();
			voce.setNome(nomeJ2);

			System.out.println("------------------------");

			Partida ptd = new Partida();
			ptd.comecarPartida(tc, eu, voce);
		}

		System.out.println("Fechando programa...");
		tc.close();
	}
}
