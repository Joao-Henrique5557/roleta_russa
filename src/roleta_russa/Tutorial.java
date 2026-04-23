package roleta_russa;

public class Tutorial {
	private String tutorial = "O jogo de “roleta russa” em versão simulada funciona como um sistema de sorteio: o programa escolhe aleatoriamente um número dentro de um intervalo (geralmente de 1 a 6) usando classes como java.util.Random, e a cada rodada um novo número também é gerado para representar a tentativa do jogador. Se o número sorteado coincidir com o número previamente definido como “perigoso”, o jogo termina; caso contrário, o jogador continua na próxima rodada. Essa lógica é controlada por estruturas básicas como laços de repetição (while) e condicionais (if), podendo ser expandida com contagem de rodadas, níveis de dificuldade ou sistema de pontuação.";
	public void mostrarTutorial() {
		System.out.println(this.tutorial);
	}
}
