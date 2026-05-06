package multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerLocal {
	public int PORT = 5000;
	
	// Lista thread-safe com todos os clientes conectados
    private final List<PrintWriter> clientes = new CopyOnWriteArrayList<>();
    
	public void iniciarLocal(){	
        System.out.println("=== SERVIDOR INICIADO ===");
        
        try {
        	System.out.println("IP do criador da sala: " + IP.getIP());
        } catch (SocketException e) {
        	System.out.println("Erro");
			e.printStackTrace();
		}
        
        System.out.println("Aguardando conexões na porta " + PORT + "...");
        System.out.println("--------------------------------------------");
        
        try (ServerSocket servidor = new ServerSocket(this.PORT)){
        	while (this.clientes.size() < 2) {
                Socket clientSocket = servidor.accept();
                System.out.println("✅ Jogador conectado: " + clientSocket.getInetAddress().getHostAddress());

                // Cada cliente roda em sua própria thread
                Thread t = new Thread(() -> tratarCliente(clientSocket));
                t.start();
            }
        } catch (IOException e){
        	System.err.println("Erro no servidor: " + e.getMessage());
        }
        
        if(this.clientes.size() == 2) {
    		System.out.println("Começando jogo...");
    	}
	}
	
	public void tratarCliente(Socket socket) {
		PrintWriter saida = null;
        try {
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            
            saida = new PrintWriter(socket.getOutputStream(), true);

            // Registra o cliente na lista global
            clientes.add(saida);
        } catch (IOException e) {
            System.out.println("Conexão encerrada: " + e.getMessage());
        } finally {
            // Remove o cliente da lista ao desconectar
            if (saida != null) clientes.remove(saida);
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("Cliente desconectado.");
        }
	}
}
