package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Beans.Usuario;
import java.io.IOException;
import dao.UsuarioDAO;

@WebServlet("/CadastrarServlet")
public class CadastrarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CadastrarServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");

		Usuario u = new Usuario();
		u.setNome(nome);
		u.setEmail(email);
		u.setSenha(senha);
		u.setPontos(0);

		new UsuarioDAO().inserirUsuario(u);

		response.getWriter().write("Cadastro via URL realizado");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		java.io.PrintWriter out = response.getWriter();

		// ETAPA A: Ler o texto do JSON enviado pelo React
		StringBuilder sb = new StringBuilder();
		String linha;
		try (java.io.BufferedReader reader = request.getReader()) {
			while ((linha = reader.readLine()) != null) {
				sb.append(linha);
			}
		}
		String json = sb.toString();

		// ETAPA B: Validar se o texto veio vazio
		if (json == null || json.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("{\"error\": \"O corpo da requisicao esta vazio.\"}");
			return;
		}

		// ETAPA C: Correção Nativa Segura contra quebras de linha e aspas
		String jsonLimpo = json.replace("\n", "").replace("\r", "").replace("{", "").replace("}", "").replace("\"", "");
		String[] partes = jsonLimpo.split(",");

		String nomeDado = null;
		String emailDado = null;
		String senhaDada = null;

		for (String parte : partes) {
			String[] chaveValor = parte.split(":", 2); // O '2' protege senhas que contêm ':'
			if (chaveValor.length == 2) {
				String chave = chaveValor[0].trim();
				String valor = chaveValor[1].trim();

				if (chave.equalsIgnoreCase("nome"))
					nomeDado = valor;
				if (chave.equalsIgnoreCase("email"))
					emailDado = valor;
				if (chave.equalsIgnoreCase("senha"))
					senhaDada = valor;
			}
		}

		// ETAPA D: Validação de segurança sem riscos de NullPointerException
		if (nomeDado == null || senhaDada == null || emailDado == null || nomeDado.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("{\"error\": \"Campos obrigatorios ausentes no formulario do React.\"}");
			return;
		}

		// ETAPA E: Criar o usuário e mandar para o seu DAO
		Usuario user = new Usuario();
		user.setNome(nomeDado);
		user.setSenha(senhaDada);
		user.setEmail(emailDado);

		UsuarioDAO dao = new UsuarioDAO();
		boolean sucesso = dao.inserirUsuario(user);

		if (sucesso) {
			response.setStatus(HttpServletResponse.SC_CREATED);
			out.write("{\"message\": \"Usuario cadastrado com sucesso!\"}");
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write("{\"error\": \"O DAO falhou ao inserir no banco de dados.\"}");
		}
	}
}