package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import dao.NovidadeDAO;
import model.Beans.Novidade;

@WebServlet("/ListarNovidades")
public class ListarNovidades extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ListarNovidades() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. Configura o CORS (Fundamental para o React local conseguir ler a API)
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");

		// 2. Define que a resposta será um JSON em formato UTF-8
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		// 3. Busca a lista de novidades ativas do banco de dados
		NovidadeDAO dao = new NovidadeDAO();
		List<Novidade> novidades = dao.listarTodas();

		// 4. Monta a String JSON manualmente para evitar dependências externas
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (int i = 0; i < novidades.size(); i++) {
			Novidade n = novidades.get(i);
			json.append("{");
			json.append("\"id\":").append(n.getId()).append(",");
			json.append("\"titulo\":\"").append(n.getTitulo().replace("\"", "\\\"")).append("\",");
			json.append("\"descricao\":\"").append(n.getDescricao().replace("\"", "\\\"")).append("\",");
			json.append("\"tipo\":\"").append(n.getTipo()).append("\",");
			json.append("\"autor\":\"").append(n.getAutor()).append("\",");
			json.append("\"versao\":\"").append(n.getVersao()).append("\",");
			json.append("\"dataPublicacao\":\"")
					.append(n.getDataPublicacao() != null ? n.getDataPublicacao().toString() : "").append("\",");
			json.append("\"ativo\":").append(n.isAtivo());
			json.append("}");

			// Adiciona vírgula entre os objetos, exceto no último
			if (i < novidades.size() - 1) {
				json.append(",");
			}
		}
		json.append("]");

		// 5. Envia o JSON de volta para o cliente
		out.write(json.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}