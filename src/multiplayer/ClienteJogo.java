package multiplayer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Cliente de terminal para o jogo Roleta Russa multiplayer.
 *
 * Fluxo:
 *  1. Pede IP e porta do servidor
 *  2. Login ou registro
 *  3. Entra na fila / sala
 *  4. Joga de acordo com as mensagens recebidas do servidor
 */
public class ClienteJogo {

    private String ip;
    private int porta;
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;
    private Scanner teclado;

    private String meuNome;
    private boolean souHost = false;

    // ------------------------------------------------------------------ //
    //  Conexão
    // ------------------------------------------------------------------ //
    public void iniciar(Scanner tc) {
        this.teclado = tc;

        System.out.print("IP do servidor (ex: 192.168.1.10): ");
        this.ip = tc.next();
        System.out.print("Porta (padrão 5000): ");
        this.porta = tc.nextInt();
        tc.nextLine(); // limpar buffer

        try {
            socket  = new Socket(ip, porta);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida   = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            System.out.println("✅ Conectado ao servidor " + ip + ":" + porta);
            loop();

        } catch (IOException e) {
            System.out.println("❌ Não foi possível conectar: " + e.getMessage());
        } finally {
            fechar();
        }
    }

    // ------------------------------------------------------------------ //
    //  Loop principal de mensagens
    // ------------------------------------------------------------------ //
    private void loop() throws IOException {
        String linha;
        while ((linha = entrada.readLine()) != null) {
            String[] partes = Protocolo.desmontar(linha);
            String tipo = partes[0];

            switch (tipo) {

                // ---- Boas-vindas ----
                case Protocolo.CHAT:
                    System.out.println("[Servidor] " + (partes.length > 1 ? partes[1] : ""));
                    // Se o servidor pede login, lemos do teclado
                    if (partes.length > 1 && partes[1].contains("LOGIN")) {
                        pedirLogin();
                    } else if (partes.length > 1 && partes[1].contains("SALA")) {
                        pedirSala();
                    } else if (partes.length > 1 && partes[1].contains("dificuldade")) {
                        pedirDificuldade();
                    }
                    break;

                // ---- Auth ----
                case Protocolo.LOGIN_OK:
                    meuNome = partes.length > 1 ? partes[1] : "?";
                    System.out.println("✅ Logado como: " + meuNome);
                    break;

                case Protocolo.LOGIN_FAIL:
                    System.out.println("❌ Falha no login: " + (partes.length > 1 ? partes[1] : ""));
                    return;

                // ---- Sala ----
                case Protocolo.SALA_AGUARDA:
                    System.out.println("⏳ " + (partes.length > 1 ? partes[1] : "Aguardando..."));
                    break;

                case Protocolo.SALA_OK:
                    String adversario = partes.length > 1 ? partes[1] : "?";
                    System.out.println("🎮 Sala pronta! Adversário: " + adversario);
                    break;

                // ---- Partida ----
                case Protocolo.PARTIDA_INICIAR:
                    String dif = partes.length > 1 ? partes[1] : "medio";
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("🎯 PARTIDA INICIADA — Dificuldade: " + dif);
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    break;

                case Protocolo.INFO_REVOLVE:
                    if (partes.length > 1) {
                        String[] info = partes[1].split("\\|");
                        if (info.length == 2)
                            System.out.println("🔫 Revólver: " + info[0] + " balas totais, " + info[1] + " verdadeiras");
                    }
                    break;

                case Protocolo.VEZ_DE:
                    String vezDe = partes.length > 1 ? partes[1] : "?";
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    System.out.println("🎲 Vez de: " + vezDe);
                    if (vezDe.equals(meuNome)) {
                        pedirAcao();
                    } else {
                        System.out.println("⏳ Aguardando jogada do adversário...");
                    }
                    break;

                case Protocolo.RESULTADO:
                    if (partes.length >= 2) {
                        // formato: nome|VIVO/MORTO|vidas
                        String[] r = partes[1].split("\\|");
                        if (r.length >= 3) {
                            String icone = r[1].equals("MORTO") ? "💀" : "💥";
                            System.out.println(icone + " " + r[0] + " → " + r[1] + " | Vidas: " + r[2]);
                        } else {
                            System.out.println("Resultado: " + partes[1]);
                        }
                    }
                    break;

                case Protocolo.GAME_OVER:
                    String vencedor = partes.length > 1 ? partes[1] : "?";
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    if (vencedor.equals(meuNome)) {
                        System.out.println("🏆 VOCÊ VENCEU! Parabéns, " + meuNome + "!");
                    } else {
                        System.out.println("💀 Você perdeu. Vencedor: " + vencedor);
                    }
                    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    return;

                case Protocolo.ERRO:
                    System.out.println("⚠️  Erro do servidor: " + (partes.length > 1 ? partes[1] : ""));
                    break;

                default:
                    // Mensagem desconhecida — mostrar para debug
                    System.out.println("[DEBUG] " + linha);
            }
        }
    }

    // ------------------------------------------------------------------ //
    //  Interação com o jogador
    // ------------------------------------------------------------------ //
    private void pedirLogin() {
        System.out.println("1) Fazer login");
        System.out.println("2) Criar nova conta");
        System.out.print("Escolha: ");
        String op = teclado.nextLine().trim();

        System.out.print("Usuário: ");
        String user = teclado.nextLine().trim();
        System.out.print("Senha: ");
        String senha = teclado.nextLine().trim();

        if (op.equals("2")) {
            enviar("REGISTRAR" + Protocolo.SEP + user + Protocolo.SEP + senha);
        } else {
            enviar(Protocolo.montar(Protocolo.LOGIN, user, senha));
        }
    }

    private void pedirSala() {
        System.out.println("1) Criar sala");
        System.out.println("2) Entrar em sala existente");
        System.out.print("Escolha: ");
        String op = teclado.nextLine().trim();
        if (op.equals("1")) {
            enviar(Protocolo.SALA_CRIAR);
            souHost = true;
        } else {
            enviar(Protocolo.SALA_ENTRAR);
        }
    }

    private void pedirDificuldade() {
        System.out.print("Dificuldade (facil/medio/dificil): ");
        String d = teclado.nextLine().trim().toLowerCase();
        if (!d.equals("facil") && !d.equals("dificil")) d = "medio";
        enviar(d);
    }

    private void pedirAcao() {
        System.out.println("Sua vez!");
        System.out.print("Atirar em você mesmo (1) ou no adversário (2)? ");
        String op = teclado.nextLine().trim();
        enviar(Protocolo.montar(Protocolo.ACAO, op.isEmpty() ? "1" : op));
    }

    private void enviar(String msg) {
        if (saida != null) saida.println(msg);
    }

    private void fechar() {
        try { if (socket != null && !socket.isClosed()) socket.close(); }
        catch (IOException ignored) {}
    }
}
