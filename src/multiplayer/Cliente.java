package multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	private String server_ip;
	private int server_port;
	private Socket socket;
	private PrintWriter saida;
	private BufferedReader entrada;

	public boolean conectar(Scanner tc) {
		System.out.print("Qual é o IP do servidor? ");
		this.server_ip = tc.next();
		System.out.print("Qual é a porta do servidor? (padrão: 5000) ");
		this.server_port = tc.nextInt();

		System.out.print("Qual é o seu nome? ");
		String nomeJogador = tc.next();

		try {
			this.socket = new Socket(this.server_ip, this.server_port);
			this.saida = new PrintWriter(socket.getOutputStream(), true);
			this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Envia nome ao servidor
			saida.println(nomeJogador);

			// Aguarda confirmação
			String resposta = entrada.readLine();
			if (resposta != null && resposta.startsWith("BEM_VINDO:")) {
				String nomeHost = resposta.split(":")[1];
				System.out.println("✅ Conectado! Você vai jogar contra: " + nomeHost);
				return true;
			}
		} catch (IOException e) {
			System.err.println("Erro ao conectar: " + e.getMessage());
		}
		return false;
	}

	public void desconectar() {
		try {
			if (socket != null) socket.close();
		} catch (IOException e) {
			System.err.println("Erro ao desconectar: " + e.getMessage());
		}
	}
}
