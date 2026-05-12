package multiplayer;

import roleta_russa.Jogador;
import roleta_russa.Revolve;
import java.util.concurrent.*;

public class GerenciadorPartida {
    private ClienteHandler p1, p2;
    private Revolve revolver;
    private ClienteHandler vez;
    private boolean ativa = true;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> timeoutTask;
    private final int TEMPO_LIMITE = 180;

    public GerenciadorPartida(ClienteHandler p1, ClienteHandler p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.revolver = new Revolve();
        this.revolver.rearmazenarBalas("medio");
        this.vez = p1;
        iniciar();
    }

    private void iniciar() {
        p1.enviar(Protocolo.montar(Protocolo.PARTIDA_INICIAR, p2.getJogador().getNome()));
        p2.enviar(Protocolo.montar(Protocolo.PARTIDA_INICIAR, p1.getJogador().getNome()));
        p1.getJogador().setVidas(3); p1.getJogador().setAlive(true);
        p2.getJogador().setVidas(3); p2.getJogador().setAlive(true);
        notificarTurno();
    }

    public synchronized void processarAcao(ClienteHandler autor, int escolha) {
        if (!ativa || autor != vez) return;
        if (timeoutTask != null) timeoutTask.cancel(false);

        Jogador alvo = (escolha == 1) ? autor.getJogador() : oponenteDe(autor).getJogador();
        boolean disparou = revolver.dispararNoAlvo(alvo);
        
        String msg = Protocolo.montar(Protocolo.RESULTADO, alvo.getNome(), 
                                     alvo.isAlive() ? (disparou ? "ATINGIDO" : "SALVO") : "MORTO", 
                                     String.valueOf(alvo.getVidas()));
        broadcast(msg);

        if (!p1.getJogador().isAlive() || !p2.getJogador().isAlive()) {
            finalizar();
        } else {
            // Se atirou em si mesmo e não morreu/não disparou, mantém a vez. Caso contrário troca.
            if (escolha == 2 || disparou) {
                vez = oponenteDe(autor);
            }
            notificarTurno();
        }
    }

    private void notificarTurno() {
        if (revolver.getQuantBalas() == 0) revolver.rearmazenarBalas("medio");
        
        broadcast(Protocolo.montar(Protocolo.INFO_REVOLVE, 
                  String.valueOf(revolver.getQuantBalasRestantes()), 
                  String.valueOf(revolver.getQuantBalasVerdadeiras())));
        broadcast(Protocolo.montar(Protocolo.VEZ_DE, vez.getJogador().getNome()));

        timeoutTask = scheduler.schedule(() -> executarAcaoAFK(), TEMPO_LIMITE, TimeUnit.SECONDS);
    }

    private synchronized void executarAcaoAFK() {
        if (ativa) processarAcao(vez, 1);
    }

    private void finalizar() {
        ativa = false;
        scheduler.shutdownNow();
        String vencedor = p1.getJogador().isAlive() ? p1.getJogador().getNome() : p2.getJogador().getNome();
        broadcast(Protocolo.montar(Protocolo.GAME_OVER, vencedor));
    }

    private void broadcast(String msg) {
        p1.enviar(msg);
        p2.enviar(msg);
    }

    private ClienteHandler oponenteDe(ClienteHandler c) { return (c == p1) ? p2 : p1; }
}
