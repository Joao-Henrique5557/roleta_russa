package multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

import roleta_russa.Jogador;
import roleta_russa.Partida;

public class ServerLocal {
	public int PORT = 5000;

	// Jogadores da partida
	private Jogador jogadorHost;
	private Jogador jogadorCliente;

	// Comunicação com o cliente remoto
	private PrintWriter saidaCliente;
	private BufferedReader entradaCliente;

	// Sinaliza quando o cliente conectou
	private final CountDownLatch clienteConectado = new CountDownLatch(1);

	public void iniciarLocal(Scanner tc) {
		System.out.println("=== SERVIDOR INICIADO ===");

		try {
			System.out.println("IP do servidor (compartilhe com o outro jogador): " + IP.getIP());
		} catch (SocketException e) {
			System.out.println("Não foi possível determinar o IP: " + e.getMessage());
		}

		System.out.print("Qual é o seu nome (host)? ");
		String nomeHost = tc.next();

		jogadorHost = new Jogador();
		jogadorHost.setNome(nomeHost);

		System.out.println("Aguardando outro jogador conectar na porta " + PORT + "...");
		System.out.println("--------------------------------------------");

		// Thread paralela: aceita conexão do cliente enquanto host espera
		Thread threadServidor = new Thread(() -> aguardarCliente());
		threadServidor.setDaemon(true);
		threadServidor.start();

		// Host aguarda o cliente conectar
		try {
			clienteConectado.await(); // bloqueia até o cliente entrar
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("Espera interrompida.");
			return;
		}

		System.out.println("✅ Jogador " + jogadorCliente.getNome() + " entrou na sala!");
		System.out.println("Iniciando partida...");
		System.out.println("--------------------------------------------");

		// Inicia a partida com Scanner que lê do console do host
		// e envia estado ao cliente via socket
		Partida ptd = new Partida();
		ptd.comecarPartida(tc, jogadorHost, jogadorCliente);
	}

	private void aguardarCliente() {
		try (ServerSocket servidor = new ServerSocket(this.PORT)) {
			Socket socketCliente = servidor.accept();
			System.out.println("\n✅ Cliente conectado: " + socketCliente.getInetAddress().getHostAddress());

			this.saidaCliente = new PrintWriter(socketCliente.getOutputStream(), true);
			this.entradaCliente = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

			// Recebe o nome do cliente
			String nomeCliente = entradaCliente.readLine();
			jogadorCliente = new Jogador();
			jogadorCliente.setNome(nomeCliente != null ? nomeCliente : "Convidado");

			// Avisa o cliente que entrou
			saidaCliente.println("BEM_VINDO:" + jogadorHost.getNome());

			// Libera o host para iniciar a partida
			clienteConectado.countDown();

		} catch (IOException e) {
			System.err.println("Erro ao aguardar cliente: " + e.getMessage());
		}
	}
}
