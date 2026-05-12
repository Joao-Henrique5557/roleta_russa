package multiplayer;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ServerLocal {
    private final int PORTA = 5000;
    private List<ClienteHandler> fila = new CopyOnWriteArrayList<>();

    public void iniciar() {
        try (ServerSocket server = new ServerSocket(PORTA)) {
            System.out.println("Servidor Autoritatívo iniciado em: " + IP.getIP());
            System.out.println("Aguardando conexões na porta " + PORTA + "...");
            while (true) {
                Socket socket = server.accept();
                System.out.println("Nova conexão: " + socket.getInetAddress());
                ClienteHandler handler = new ClienteHandler(socket, this);
                new Thread(handler).start();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public synchronized void entrarNaFila(ClienteHandler c) {
        fila.add(c);
        System.out.println(c.getJogador().getNome() + " entrou na fila.");
        if (fila.size() >= 2) {
            ClienteHandler p1 = fila.remove(0);
            ClienteHandler p2 = fila.remove(0);
            GerenciadorPartida gp = new GerenciadorPartida(p1, p2);
            p1.setPartida(gp);
            p2.setPartida(gp);
        }
    }
    
    public static void main(String[] args) {
        new ServerLocal().iniciar();
    }
}
