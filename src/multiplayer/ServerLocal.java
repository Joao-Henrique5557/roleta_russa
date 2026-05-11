package multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Servidor local de roleta russa.
 *
 * - Aceita conexões em loop.
 * - Mantém uma fila de matchmaking: quando há 2 jogadores esperando,
 *   forma uma sala e inicia a partida.
 */
public class ServerLocal {
    public int PORT = 5000;

    // Fila de jogadores aguardando partida
    private final ConcurrentLinkedQueue<ClienteHandler> fila = new ConcurrentLinkedQueue<>();

    // ------------------------------------------------------------------ //
    //  Iniciar servidor
    // ------------------------------------------------------------------ //
    public void iniciarLocal() {
        System.out.println("=== SERVIDOR ROLETA RUSSA ===");

        try {
            System.out.println("IP local: " + IP.getIP());
        } catch (Exception e) {
            System.out.println("(não foi possível obter o IP local)");
        }

        System.out.println("Aguardando conexões na porta " + PORT + "...");
        System.out.println("--------------------------------------------");

        try (ServerSocket servidor = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = servidor.accept();
                System.out.println("✅ Novo cliente: " + clientSocket.getInetAddress().getHostAddress());

                ClienteHandler handler = new ClienteHandler(clientSocket, this);
                Thread t = new Thread(handler);
                t.setDaemon(true);
                t.start();
            }
        } catch (IOException e) {
            System.err.println("Erro fatal no servidor: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------ //
    //  Matchmaking
    // ------------------------------------------------------------------ //
    public synchronized void entrarNaFila(ClienteHandler novo) {
        fila.add(novo);
        System.out.println("Fila de espera: " + fila.size() + " jogador(es)");

        if (fila.size() >= 2) {
            ClienteHandler host  = fila.poll();
            ClienteHandler guest = fila.poll();

            System.out.println("Sala formada: " + host.getConta().getUsername()
                + " vs " + guest.getConta().getUsername());

            // Define parceiros e informa quem é host (conduz a lógica)
            host.setParceiro(guest, true);
            guest.setParceiro(host, false);
        }
    }

    public synchronized void removerDaFila(ClienteHandler handler) {
        fila.remove(handler);
    }
}
