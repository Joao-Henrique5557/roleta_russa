package multiplayer;

/**
 * Protocolo de mensagens trocadas via socket.
 *
 * Formato de linha:  TIPO|dado1|dado2|...
 *
 * Exemplos:
 *   LOGIN|alice|abc123hash
 *   LOGIN_OK|alice
 *   LOGIN_FAIL|senha incorreta
 *   SALA_CRIAR
 *   SALA_ENTRAR
 *   PARTIDA_INICIAR|facil
 *   ACAO|1          (1=atirar em si, 2=atirar no oponente)
 *   RESULTADO|VIVO|2   (estado após tiro: VIVO ou MORTO | vidas restantes)
 *   CHAT|mensagem
 *   DESCONECTAR
 */
public class Protocolo {
    public static final String SEP          = "|";

    // Auth
    public static final String LOGIN        = "LOGIN";
    public static final String LOGIN_OK     = "LOGIN_OK";
    public static final String LOGIN_FAIL   = "LOGIN_FAIL";

    // Sala
    public static final String SALA_CRIAR   = "SALA_CRIAR";
    public static final String SALA_ENTRAR  = "SALA_ENTRAR";
    public static final String SALA_CHEIA   = "SALA_CHEIA";
    public static final String SALA_OK      = "SALA_OK";      // sala pronta com 2 jogadores
    public static final String SALA_AGUARDA = "SALA_AGUARDA"; // esperando 2º jogador

    // Partida
    public static final String PARTIDA_INICIAR = "PARTIDA_INICIAR";
    public static final String VEZ_DE          = "VEZ_DE";
    public static final String ACAO            = "ACAO";
    public static final String RESULTADO       = "RESULTADO";
    public static final String GAME_OVER       = "GAME_OVER";
    public static final String INFO_REVOLVE    = "INFO_REVOLVE";

    // Misc
    public static final String CHAT         = "CHAT";
    public static final String DESCONECTAR  = "DESCONECTAR";
    public static final String ERRO         = "ERRO";

    // ------------------------------------------------------------------ //
    //  Helpers de montagem/desmontagem
    // ------------------------------------------------------------------ //
    public static String montar(String tipo, String... dados) {
        if (dados.length == 0) return tipo;
        return tipo + SEP + String.join(SEP, dados);
    }

    public static String[] desmontar(String msg) {
        return msg.split("\\" + SEP, -1);
    }

    public static String tipo(String msg) {
        return desmontar(msg)[0];
    }
}
