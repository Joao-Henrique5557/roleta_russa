import dao.UsuarioDAO;

public class Main {

	public static void main(String[] args) {
		UsuarioDAO user = new UsuarioDAO();
		user.inserirUsuario("joao_devoficial", "7joaohenrique5557@gmail.com", "5557");
	}

}
