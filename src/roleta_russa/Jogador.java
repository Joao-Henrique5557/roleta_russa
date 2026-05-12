package roleta_russa;

public class Jogador {
    private String nome;
    private int vidas = 3;
    private boolean isAlive = true;

    public void levarTiro() {
        this.vidas--;
        if (this.vidas <= 0) {
            this.vidas = 0;
            this.isAlive = false;
        }
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getVidas() { return vidas; }
    public void setVidas(int vidas) { this.vidas = vidas; }
    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean isAlive) { this.isAlive = isAlive; }
}
