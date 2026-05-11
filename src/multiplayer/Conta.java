package multiplayer;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Gerencia contas de usuário: criação, login e persistência em arquivo.
 */
public class Conta {
    private static final String ARQUIVO_CONTAS = "contas.dat";

    private String username;
    private String senhaHash;
    private int vitorias;
    private int derrotas;

    public Conta(String username, String senhaHash) {
        this.username = username;
        this.senhaHash = senhaHash;
        this.vitorias = 0;
        this.derrotas = 0;
    }

    // ------------------------------------------------------------------ //
    //  Hash SHA-256
    // ------------------------------------------------------------------ //
    public static String hashSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    // ------------------------------------------------------------------ //
    //  Persistência simples em arquivo texto  (usuario:hash:v:d)
    // ------------------------------------------------------------------ //
    @SuppressWarnings("unchecked")
    private static Map<String, Conta> carregarContas() {
        Map<String, Conta> mapa = new HashMap<>();
        File f = new File(ARQUIVO_CONTAS);
        if (!f.exists()) return mapa;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(":");
                if (p.length == 4) {
                    Conta c = new Conta(p[0], p[1]);
                    c.vitorias = Integer.parseInt(p[2]);
                    c.derrotas = Integer.parseInt(p[3]);
                    mapa.put(p[0], c);
                }
            }
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível ler o arquivo de contas.");
        }
        return mapa;
    }

    private static void salvarContas(Map<String, Conta> mapa) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_CONTAS))) {
            for (Conta c : mapa.values()) {
                pw.println(c.username + ":" + c.senhaHash + ":" + c.vitorias + ":" + c.derrotas);
            }
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível salvar as contas.");
        }
    }

    // ------------------------------------------------------------------ //
    //  API pública
    // ------------------------------------------------------------------ //

    /**
     * Tenta registrar uma nova conta. Retorna a Conta criada ou null se o
     * usuário já existe.
     */
    public static Conta registrar(String username, String senha) {
        Map<String, Conta> mapa = carregarContas();
        if (mapa.containsKey(username)) {
            System.out.println("❌ Usuário '" + username + "' já existe.");
            return null;
        }
        Conta nova = new Conta(username, hashSenha(senha));
        mapa.put(username, nova);
        salvarContas(mapa);
        System.out.println("✅ Conta '" + username + "' criada com sucesso!");
        return nova;
    }

    /**
     * Tenta fazer login. Retorna a Conta ou null se credenciais inválidas.
     */
    public static Conta login(String username, String senha) {
        Map<String, Conta> mapa = carregarContas();
        Conta c = mapa.get(username);
        if (c == null || !c.senhaHash.equals(hashSenha(senha))) {
            System.out.println("❌ Usuário ou senha incorretos.");
            return null;
        }
        System.out.println("✅ Login realizado como '" + username + "'!");
        return c;
    }

    /** Registra vitória/derrota e salva. */
    public void registrarResultado(boolean venceu) {
        Map<String, Conta> mapa = carregarContas();
        if (venceu) this.vitorias++;
        else this.derrotas++;
        mapa.put(this.username, this);
        salvarContas(mapa);
    }

    public void exibirPerfil() {
        System.out.println("┌─────────────────────────────┐");
        System.out.println("│  Perfil: " + this.username);
        System.out.println("│  Vitórias : " + this.vitorias);
        System.out.println("│  Derrotas : " + this.derrotas);
        System.out.println("└─────────────────────────────┘");
    }

    // Getters
    public String getUsername() { return username; }
    public int getVitorias()    { return vitorias; }
    public int getDerrotas()    { return derrotas; }
}
