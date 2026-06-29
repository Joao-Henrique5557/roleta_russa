package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Banco;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/AutenticarServlet")
public class AutenticarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AutenticarServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		// 1. Lê o corpo da requisição (JSON vindo do React)
		StringBuilder sb = new StringBuilder();
		String linha;
		try (java.io.BufferedReader reader = request.getReader()) {
			while ((linha = reader.readLine()) != null) {
				sb.append(linha);
			}
		}
		String json = sb.toString();

		if (json == null || json.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("{\"error\": \"O corpo da requisicao esta vazio.\"}");
			return;
		}

		// 2. Extração manual das chaves do JSON para autenticação
		String jsonLimpo = json.replace("\n", "").replace("\r", "").replace("{", "").replace("}", "").replace("\"", "");
		String[] partes = jsonLimpo.split(",");

		String loginInformado = null;
		String senhaInformada = null;

		for (String parte : partes) {
			String[] chaveValor = parte.split(":", 2);
			if (chaveValor.length == 2) {
				String chave = chaveValor[0].trim();
				String valor = chaveValor[1].trim();

				// Aceita "usuario" ou "login" enviado pelo frontend
				if (chave.equalsIgnoreCase("usuario") || chave.equalsIgnoreCase("login")
						|| chave.equalsIgnoreCase("email")) {
					loginInformado = valor;
				}
				if (chave.equalsIgnoreCase("senha")) {
					senhaInformada = valor;
				}
			}
		}

		if (loginInformado == null || senhaInformada == null || loginInformado.trim().isEmpty()
				|| senhaInformada.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("{\"error\": \"Usuário/E-mail e senha são obrigatórios\"}");
			return;
		}

		// 3. Consulta no banco se existe um registro correspondente
		String sql = "SELECT id, nome, email, pontos FROM usuarios WHERE (nome = ? OR email = ?) AND senha = ?";
		Banco banco = new Banco();

		try (Connection conn = banco.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, loginInformado);
			stmt.setString(2, loginInformado);
			stmt.setString(3, senhaInformada);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					response.setStatus(HttpServletResponse.SC_OK);
					out.write("{" + "\"id\":" + rs.getInt("id") + "," + "\"nome\":\"" + rs.getString("nome") + "\","
							+ "\"email\":\"" + rs.getString("email") + "\"," + "\"pontos\":" + rs.getInt("pontos")
							+ "}");
				} else {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					out.write("{\"error\": \"Usuário ou senha incorretos\"}");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write("{\"error\": \"Erro interno no servidor de banco de dados\"}");
		}
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_OK);
	}
}