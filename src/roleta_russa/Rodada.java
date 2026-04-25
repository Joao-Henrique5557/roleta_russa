package roleta_russa;

public class Rodada {
	
	public Rodada(String dificuldade) {
		Revolve rvv = new Revolve();
		rvv.rearmazenarBalas(dificuldade);
		rvv.mostrarInfoDoRevolve("lista");
		
		System.out.println("O primeiro a jogar é você, você atira em si mesmo ou no oponente?");
	}
}
