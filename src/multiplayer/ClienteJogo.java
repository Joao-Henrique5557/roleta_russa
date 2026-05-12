package multiplayer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteJogo {
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;
    private Scanner teclado;
    private String meuNome;
    private volatile boolean minhaVez = false;

    public void iniciar() {
        teclado = new Scanner(System.in);
        System.out.print("IP do Servidor: ");
        String ip = teclado.nextLine();
        
        try {
            socket = new Socket(ip, 5000);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Seu Nome: ");
            meuNome = teclado.nextLine();
            saida.println(Protocolo.montar(Protocolo.LOGIN, meuNome));

            new Thread(this::escutarServidor).start();
            loopTeclado();

        } catch (IOException e) { e.printStackTrace(); }
    }

    private void escutarServidor() {
        try {
            String linha;
            while ((linha = entrada.readLine()) != null) {
                tratarMensagem(linha);
            }
        } catch (IOException e) { System.out.println("Conexão perdida."); System.exit(0); }
    }

    private void tratarMensagem(String linha) {
        String[] p = Protocolo.quebrar(linha);
        switch(p[0]) {
            case Protocolo.VEZ_DE:
                minhaVez = p[1].equals(meuNome);
                System.out.println("\n>>> Vez de: " + p[1] + (minhaVez ? " (VOCÊ)" : ""));
                if (minhaVez) System.out.print("Escolha [1-Si mesmo, 2-Oponente]: ");
                break;
            case Protocolo.INFO_REVOLVE:
                System.out.println("\n[TAMBOR] Balas no tambor: " + p[1] + " | Reais: " + p[2]);
                break;
            case Protocolo.RESULTADO:
                System.out.println("[SALA] " + p[1] + " está " + p[2] + ". Vidas: " + p[3]);
                break;
            case Protocolo.GAME_OVER:
                System.out.println("\n=== FIM DE JOGO! VENCEDOR: " + p[1] + " ===");
                minhaVez = false;
                break;
        }
    }

    private void loopTeclado() {
        while (true) {
            if (minhaVez && teclado.hasNextLine()) {
                String acao = teclado.nextLine();
                saida.println(Protocolo.montar(Protocolo.ACAO, acao));
                minhaVez = false;
            }
        }
    }

    public static void main(String[] args) {
        new ClienteJogo().iniciar();
    }
}
