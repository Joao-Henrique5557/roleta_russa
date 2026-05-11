package multiplayer;

import roleta_russa.Jogador;
import roleta_russa.Revolve;

import java.io.*;
import java.net.Socket;

/**
 * Roda em uma Thread separada para cada cliente conectado.
 * Faz login/registro e depois coordena a partida com o parceiro.
 */
public class ClienteHandler implements Runnable {

    private final Socket socket;
    private final ServerLocal servidor;

    private BufferedReader entrada;
    private PrintWriter saida;

    private Conta conta;
    private Jogador jogador;
    private ClienteHandler parceiro;   // o outro jogador na sala

    // Estado da partida (compartilhado via referência no servidor)
    private boolean minhavez = false;

    public ClienteHandler(Socket socket, ServerLocal servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    // ------------------------------------------------------------------ //
    //  Loop principal
    // ------------------------------------------------------------------ //
    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida   = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // 1. Autenticação
            if (!autenticar()) {
                fechar();
                return;
            }

            // 2. Entrar/criar sala
            entrarNaSala();

            // 3. Aguardar parceiro e jogar
            aguardarEJogar();

        } catch (IOException e) {
            System.out.println("Cliente desconectado: " +
                (conta != null ? conta.getUsername() : socket.getInetAddress()));
        } finally {
            fechar();
        }
    }

    // ------------------------------------------------------------------ //
    //  1. Autenticação
    // ------------------------------------------------------------------ //
    private boolean autenticar() throws IOException {
        enviar(Protocolo.montar(Protocolo.CHAT, "Bem-vindo! Digite LOGIN|usuario|senha ou REGISTRAR|usuario|senha"));

        String linha = entrada.readLine();
        if (linha == null) return false;

        String[] partes = Protocolo.desmontar(linha);
        String tipo = partes[0];

        if (tipo.equals("REGISTRAR") && partes.length == 3) {
            conta = Conta.registrar(partes[1], partes[2]);
            if (conta == null) {
                enviar(Protocolo.montar(Protocolo.LOGIN_FAIL, "Usuário já existe"));
                return false;
            }
        } else if (tipo.equals(Protocolo.LOGIN) && partes.length == 3) {
            conta = Conta.login(partes[1], partes[2]);
            if (conta == null) {
                enviar(Protocolo.montar(Protocolo.LOGIN_FAIL, "Usuário ou senha inválidos"));
                return false;
            }
        } else {
            enviar(Protocolo.montar(Protocolo.ERRO, "Comando inválido"));
            return false;
        }

        enviar(Protocolo.montar(Protocolo.LOGIN_OK, conta.getUsername()));
        jogador = new Jogador();
        jogador.setNome(conta.getUsername());
        return true;
    }

    // ------------------------------------------------------------------ //
    //  2. Sala
    // ------------------------------------------------------------------ //
    private void entrarNaSala() throws IOException {
        enviar(Protocolo.montar(Protocolo.CHAT, "Digite SALA_CRIAR para criar sala ou SALA_ENTRAR para entrar"));
        String linha = entrada.readLine();
        if (linha == null) return;

        // Por simplicidade: ambos os comandos colocam o jogador na fila
        servidor.entrarNaFila(this);
    }

    // ------------------------------------------------------------------ //
    //  3. Aguardar parceiro e jogar
    // ------------------------------------------------------------------ //
    private void aguardarEJogar() throws IOException {
        enviar(Protocolo.montar(Protocolo.SALA_AGUARDA, "Aguardando outro jogador..."));

        // Bloqueia até o servidor definir o parceiro
        synchronized (this) {
            while (parceiro == null) {
                try { wait(500); } catch (InterruptedException ignored) {}
            }
        }

        enviar(Protocolo.montar(Protocolo.SALA_OK, parceiro.conta.getUsername()));

        // Somente o "host" (primeiro a entrar) controla a lógica do revolve
        if (minhavez) {
            conduzirPartida();
        } else {
            aguardarPartida();
        }
    }

    // ------------------------------------------------------------------ //
    //  4a. Host: conduz a partida
    // ------------------------------------------------------------------ //
    private void conduzirPartida() throws IOException {
        // Pede dificuldade
        enviar(Protocolo.montar(Protocolo.CHAT, "Escolha a dificuldade: facil | medio | dificil"));
        String dificuldade = entrada.readLine();
        if (dificuldade == null) dificuldade = "medio";
        dificuldade = dificuldade.trim().toLowerCase();

        // Informa ambos
        enviar(Protocolo.montar(Protocolo.PARTIDA_INICIAR, dificuldade));
        parceiro.enviar(Protocolo.montar(Protocolo.PARTIDA_INICIAR, dificuldade));

        Revolve rvv = new Revolve();
        Jogador eu   = this.jogador;
        Jogador voce = parceiro.jogador;

        // Reseta vidas
        eu.setVidas(2);   eu.setAlive(true);
        voce.setVidas(2); voce.setAlive(true);

        ClienteHandler vez = this; // começa pelo host

        while (eu.isAlive() && voce.isAlive()) {
            rvv.isVazio(dificuldade);

            // Informa quem é a vez
            String infoRevolve = rvv.getQuantBalas() + "|" + rvv.getQuantBalasVerdadeiras();
            enviar(Protocolo.montar(Protocolo.INFO_REVOLVE, infoRevolve));
            parceiro.enviar(Protocolo.montar(Protocolo.INFO_REVOLVE, infoRevolve));

            enviar(Protocolo.montar(Protocolo.VEZ_DE, vez.conta.getUsername()));
            parceiro.enviar(Protocolo.montar(Protocolo.VEZ_DE, vez.conta.getUsername()));

            // Lê ação de quem é a vez
            String acaoLinha;
            int acao;
            if (vez == this) {
                acaoLinha = entrada.readLine();
            } else {
                acaoLinha = parceiro.entrada.readLine();
            }
            if (acaoLinha == null) break;
            String[] acaoPartes = Protocolo.desmontar(acaoLinha);
            try {
                acao = Integer.parseInt(acaoPartes[acaoPartes.length - 1]);
            } catch (NumberFormatException e) {
                acao = 1; // padrão: atirar em si
            }

            // Decide alvo
            Jogador alvo = (acao == 1)
                ? (vez == this ? eu : voce)     // atirar em si
                : (vez == this ? voce : eu);    // atirar no oponente

            boolean eraVida = alvo.getVidas() > 0;
            rvv.atirar(alvo);
            boolean morreu = !alvo.isAlive();

            String resultado = alvo.getNome() + "|" + (alvo.isAlive() ? "VIVO" : "MORTO") + "|" + alvo.getVidas();
            enviar(Protocolo.montar(Protocolo.RESULTADO, resultado));
            parceiro.enviar(Protocolo.montar(Protocolo.RESULTADO, resultado));

            // Só troca de vez se atirou no oponente
            if (acao == 2) {
                vez = (vez == this) ? parceiro : this;
            }
        }

        // Game over
        String vencedor = eu.isAlive() ? eu.getNome() : voce.getNome();
        String perdedor = eu.isAlive() ? voce.getNome() : eu.getNome();

        enviar(Protocolo.montar(Protocolo.GAME_OVER, vencedor));
        parceiro.enviar(Protocolo.montar(Protocolo.GAME_OVER, vencedor));

        // Salva resultado nas contas
        conta.registrarResultado(eu.isAlive());
        parceiro.conta.registrarResultado(voce.isAlive());

        System.out.println("Partida encerrada. Vencedor: " + vencedor);
    }

    // ------------------------------------------------------------------ //
    //  4b. Guest: apenas aguarda mensagens do servidor
    // ------------------------------------------------------------------ //
    private void aguardarPartida() throws IOException {
        // O guest só envia/recebe via socket; o host lê do socket do guest
        // Ficamos vivos enquanto a conexão estiver aberta
        String linha;
        while ((linha = entrada.readLine()) != null) {
            // Ações do guest são lidas diretamente pelo host em conduzirPartida()
            // Aqui tratamos apenas mensagens extras (ex: CHAT)
            if (Protocolo.tipo(linha).equals(Protocolo.CHAT)) {
                String[] p = Protocolo.desmontar(linha);
                parceiro.enviar(Protocolo.montar(Protocolo.CHAT,
                    conta.getUsername() + ": " + (p.length > 1 ? p[1] : "")));
            }
            // Quando o host encerrar a partida, ele fecha a conexão
            if (Protocolo.tipo(linha).equals(Protocolo.DESCONECTAR)) break;
        }
    }

    // ------------------------------------------------------------------ //
    //  Utilitários
    // ------------------------------------------------------------------ //
    public void enviar(String msg) {
        if (saida != null) saida.println(msg);
    }

    public synchronized void setParceiro(ClienteHandler parceiro, boolean souHost) {
        this.parceiro  = parceiro;
        this.minhavez  = souHost;
        notifyAll();
    }

    public Jogador getJogador() { return jogador; }
    public Conta   getConta()   { return conta; }

    private void fechar() {
        try { if (socket != null && !socket.isClosed()) socket.close(); }
        catch (IOException ignored) {}
        servidor.removerDaFila(this);
    }

    /** Lê próxima linha do socket (usado pelo host para ler do guest) */
    public String lerLinha() throws IOException {
        return entrada.readLine();
    }
}
