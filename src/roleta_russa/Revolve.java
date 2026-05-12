package roleta_russa;

import java.util.Arrays;

public class Revolve {
    private int quantBalasTotal = 0;
    private boolean[] balas;
    private int quantBalasVerdadeiras;
    private int posAtual = 0;

    public void rearmazenarBalas(String dificuldade) {
        this.posAtual = 0;
        this.quantBalasTotal = (int) (Math.random() * 5) + 2;
        this.balas = new boolean[quantBalasTotal];
        this.quantBalasVerdadeiras = (int) (Math.random() * (quantBalasTotal - 1)) + 1;
        
        int colocadas = 0;
        while (colocadas < quantBalasVerdadeiras) {
            int idx = (int) (Math.random() * quantBalasTotal);
            if (!balas[idx]) {
                balas[idx] = true;
                colocadas++;
            }
        }
    }

    public boolean dispararNoAlvo(Jogador alvo) {
        boolean tiroReal = balas[posAtual];
        if (tiroReal) {
            alvo.levarTiro();
        }
        posAtual++;
        return tiroReal;
    }

    public int getQuantBalasRestantes() { return quantBalasTotal - posAtual; }
    public int getQuantBalas() { return getQuantBalasRestantes(); }
    public int getQuantBalasVerdadeiras() {
        int cont = 0;
        for (int i = posAtual; i < balas.length; i++) if (balas[i]) cont++;
        return cont;
    }
}
