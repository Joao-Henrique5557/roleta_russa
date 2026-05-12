package multiplayer;

import roleta_russa.Jogador;
import java.io.*;
import java.net.Socket;

public class ClienteHandler implements Runnable {
    private Socket socket;
    private ServerLocal servidor;
    private BufferedReader entrada;
    private PrintWriter saida;
    private Jogador jogador;
    private GerenciadorPartida partida;

    public ClienteHandler(Socket socket, ServerLocal servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter(socket.getOutputStream(), true);

            String dados = entrada.readLine(); 
            if (dados == null) return;
            String[] p = Protocolo.quebrar(dados);
            
            this.jogador = new Jogador();
            this.jogador.setNome(p[1]);
            enviar(Protocolo.montar(Protocolo.LOGIN_OK, jogador.getNome()));

            servidor.entrarNaFila(this);

            String linha;
            while ((linha = entrada.readLine()) != null) {
                String[] cmd = Protocolo.quebrar(linha);
                if (cmd[0].equals(Protocolo.ACAO) && partida != null) {
                    partida.processarAcao(this, Integer.parseInt(cmd[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Conexão encerrada com um cliente.");
        } finally {
            fechar();
        }
    }

    public void enviar(String msg) { if (saida != null) saida.println(msg); }
    public void setPartida(GerenciadorPartida gp) { this.partida = gp; }
    public Jogador getJogador() { return jogador; }
    private void fechar() {
        try { socket.close(); } catch (IOException e) {}
    }
}
