package multiplayer;

public class Protocolo {
    public static final String SEP = ";";
    
    // Comandos Cliente -> Servidor
    public static final String LOGIN = "LOGIN";
    public static final String REGISTRAR = "REGISTRAR";
    public static final String SALA_ENTRAR = "SALA_ENTRAR";
    public static final String ACAO = "ACAO";

    // Comandos Servidor -> Cliente
    public static final String LOGIN_OK = "LOGIN_OK";
    public static final String LOGIN_FAIL = "LOGIN_FAIL";
    public static final String PARTIDA_INICIAR = "PARTIDA_INICIAR";
    public static final String INFO_REVOLVE = "INFO_REVOLVE";
    public static final String VEZ_DE = "VEZ_DE";
    public static final String RESULTADO = "RESULTADO";
    public static final String GAME_OVER = "GAME_OVER";
    public static final String ERRO = "ERRO";

    public static String montar(String... partes) {
        return String.join(SEP, partes);
    }

    public static String[] quebrar(String linha) {
        return linha.split(SEP);
    }
}
