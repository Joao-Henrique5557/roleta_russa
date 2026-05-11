package roleta_russa;

import java.util.Scanner;

import multiplayer.ClienteJogo;
import multiplayer.Conta;
import multiplayer.ServerLocal;

public class Jogo {

    public static void main(String[] args) {
        Scanner tc = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      ROLETA RUSSA  v2.0          ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("1) Jogar localmente (2 jogadores no mesmo PC)");
        System.out.println("2) Hospedar servidor (multiplayer local)");
        System.out.println("3) Conectar a um servidor");
        System.out.println("4) Gerenciar conta");
        System.out.print("Escolha: ");
        int resp = tc.nextInt();
        tc.nextLine();

        switch (resp) {

            // ── Modo local ───────────────────────────────────────────────
            case 1 -> {
                System.out.print("Nome do jogador 1: ");
                String nomeJ1 = tc.nextLine().trim();
                System.out.print("Nome do jogador 2: ");
                String nomeJ2 = tc.nextLine().trim();

                Jogador eu   = new Jogador();
                eu.setNome(nomeJ1);
                Jogador voce = new Jogador();
                voce.setNome(nomeJ2);

                System.out.println("────────────────────────────────────");

                Partida ptd = new Partida();
                ptd.comecarPartida(tc, eu, voce);
            }

            // ── Servidor ─────────────────────────────────────────────────
            case 2 -> {
                System.out.println("Iniciando servidor local...");
                System.out.println("────────────────────────────────────");
                ServerLocal svd = new ServerLocal();
                
                Thread t = new Thread(() -> {
                    ClienteJogo cliente = new ClienteJogo();
                    cliente.iniciar(tc);
                });

                svd.iniciarLocal(); // bloqueante
            }

            // ── Cliente ──────────────────────────────────────────────────
            case 3 -> {
                ClienteJogo cliente = new ClienteJogo();
                cliente.iniciar(tc);
            }

            // ── Gerenciar conta ──────────────────────────────────────────
            case 4 -> {
                System.out.println("1) Criar nova conta");
                System.out.println("2) Ver perfil");
                System.out.print("Escolha: ");
                int sub = tc.nextInt();
                tc.nextLine();

                if (sub == 1) {
                    System.out.print("Usuário: ");
                    String u = tc.nextLine().trim();
                    System.out.print("Senha: ");
                    String s = tc.nextLine().trim();
                    Conta.registrar(u, s);

                } else if (sub == 2) {
                    System.out.print("Usuário: ");
                    String u = tc.nextLine().trim();
                    System.out.print("Senha: ");
                    String s = tc.nextLine().trim();
                    Conta c = Conta.login(u, s);
                    if (c != null) c.exibirPerfil();
                }
            }

            default -> System.out.println("Opção inválida.");
        }

        System.out.println("Encerrando...");
        tc.close();
    }
}
