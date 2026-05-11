package roleta_russa;

import java.util.Scanner;

public class Rodada {
    private String vezde;
    private int numRodada = 1;
    private String dificuldade;
    private Jogador eu;
    private Jogador voce;
    private Revolve rvv;
    private Scanner tc;

    public void iniciarRodada(String dificuldade, Jogador eu, Jogador voce, Revolve rvv, Scanner tc) {
        this.eu = eu;
        this.voce = voce;
        this.vezde = this.eu.getNome();
        this.dificuldade = dificuldade;
        this.rvv = rvv;
        this.tc = tc;

        while (eu.isAlive() && voce.isAlive()) {
            iniciarTurno();
        }

        // Anunciar vencedor
        String vencedor = eu.isAlive() ? eu.getNome() : voce.getNome();
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🏆 Partida encerrada! Vencedor: " + vencedor);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    private void iniciarTurno() {
        rvv.isVazio(this.dificuldade);

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("Vez de: " + this.vezde + "   |   Rodada: " + this.numRodada);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print(this.vezde + " → atirar em si mesmo (1) ou no oponente (2)? ");
        int resp = this.tc.nextInt();
        System.out.println("─────────────────────────────────────");

        if (this.vezde.equals(this.eu.getNome())) {
            if (resp == 1) {
                this.rvv.atirar(this.eu);
            } else if (resp == 2) {
                this.rvv.atirar(this.voce);
                mudarVez(this.eu, this.voce);
            }
        } else if (this.vezde.equals(this.voce.getNome())) {
            if (resp == 1) {
                this.rvv.atirar(this.voce);
            } else if (resp == 2) {
                this.rvv.atirar(this.eu);
                mudarVez(this.eu, this.voce);
            }
        }

        if (rvv.isVazio(this.dificuldade) && (this.eu.getVidas() < 2 || this.voce.getVidas() < 2)) {
            this.numRodada++;
        }
    }

    private void mudarVez(Jogador eu, Jogador voce) {
        if (this.vezde.equals(eu.getNome())) {
            this.vezde = voce.getNome();
        } else if (this.vezde.equals(voce.getNome())) {
            this.vezde = eu.getNome();
        }
    }
}
