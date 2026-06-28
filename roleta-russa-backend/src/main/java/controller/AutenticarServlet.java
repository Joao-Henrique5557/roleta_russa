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
		// 1. Configurações de CORS e codificação
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		// 2. Coleta os dados enviados pelo formulário React
		String loginInformado = request.getParameter("usuario"); // Aceita tanto o nome quanto o email
		String senhaInformada = request.getParameter("senha");

		if (loginInformado == null || senhaInformada == null || loginInformado.isEmpty() || senhaInformada.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("{\"error\": \"Usuário/E-mail e senha são obrigatórios\"}");
			return;
		}

		// 3. Consulta no banco se existe um registro correspondente
		// Buscamos pelo nome OU pelo email digitado no mesmo campo de input
		String sql = "SELECT id, nome, email, pontos FROM usuarios WHERE (nome = ? OR email = ?) AND senha = ?";
		Banco banco = new Banco();

		try (Connection conn = banco.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, loginInformado);
			stmt.setString(2, loginInformado);
			stmt.setString(3, senhaInformada);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					// USUÁRIO ENCONTRADO -> Login com sucesso!
					response.setStatus(HttpServletResponse.SC_OK);

					// Retorna os dados do usuário autenticado (menos a senha)
					out.write("{" + "\"id\":" + rs.getInt("id") + "," + "\"nome\":\"" + rs.getString("nome") + "\","
							+ "\"email\":\"" + rs.getString("email") + "\"," + "\"pontos\":" + rs.getInt("pontos")
							+ "}");
				} else {
					// DADOS INCORRETOS -> Não autorizado
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
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setStatus(HttpServletResponse.SC_OK);
	}
}