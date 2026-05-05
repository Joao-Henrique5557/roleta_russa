package multiplayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerLocal {
	public int PORT = 5000;
	
	// Lista thread-safe com todos os clientes conectados
    private final List<PrintWriter> clientes = new CopyOnWriteArrayList<>();
    
	public void iniciarLocal(){	
        System.out.println("=== SERVIDOR INICIADO ===");
        
        try {
        	InetAddress ip = InetAddress.getLocalHost();
        	System.out.println("IP do criador da sala: " + IP.getIP());
        } catch(UnknownHostException e){
        	System.out.println("Erro: " + e.getMessage());
        } catch (SocketException e) {
        	System.out.println("Erro");
			e.printStackTrace();
		}
        
        System.out.println("Aguardando conexões na porta " + PORT + "...");
        System.out.println("--------------------------------------------");
        
        try (ServerSocket servidor = new ServerSocket(this.PORT)){
        	while (true) {
                Socket clientSocket = servidor.accept();
                System.out.println("✅ Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());

                // Cada cliente roda em sua própria thread
                Thread t = new Thread(() -> tratarCliente(clientSocket));
                t.start();
            }
        } catch (IOException e){
        	System.err.println("Erro no servidor: " + e.getMessage());
        }
	}
	
	public void tratarCliente(Socket socket) {
		
	}
}
