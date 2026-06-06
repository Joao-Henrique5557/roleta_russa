import java.util.Scanner;

import multiplayer.ClienteJogo;
import multiplayer.ServerLocal;

public class Main {

	public static void main(String[] args) {
		System.out.println("Bem vindo ao jogo");

		System.out.println("Rodar servidor local? (1)");
		System.out.println("Jogar contra IA? (2)? - EM BREVE -");
		System.out.println("Jogar cotra colega presencialmente? (3)");

		Scanner tc = new Scanner(System.in);
		int resp = (int) tc.nextInt();

		switch (resp) {
		case 1:
			ServerLocal sl = new ServerLocal();
			sl.iniciar();
			break;
		case 2:
			break;
		case 3:
			ClienteJogo cj = new ClienteJogo();
			cj.iniciar();
			break;
		}
		
		tc.close();
	}

}
