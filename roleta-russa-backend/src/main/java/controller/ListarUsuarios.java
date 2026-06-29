package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import dao.UsuarioDAO;
import model.Beans.Usuario;

@WebServlet("/ListarUsuarios")
public class ListarUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ListarUsuarios() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		UsuarioDAO dao = new UsuarioDAO();
		List<Usuario> usuarios = dao.listarTodos();

		StringBuilder json = new StringBuilder();
		json.append("[");
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario u = usuarios.get(i);
			json.append("{");
			json.append("\"id\":").append(u.getId()).append(",");
			// O .replace garante que nomes com aspas não quebrem a estrutura do JSON
			json.append("\"nome\":\"").append(u.getNome() != null ? u.getNome().replace("\"", "\\\"") : "")
					.append("\",");
			json.append("\"email\":\"").append(u.getEmail() != null ? u.getEmail().replace("\"", "\\\"") : "")
					.append("\",");
			json.append("\"pontos\":").append(u.getPontos());
			json.append("}");

			if (i < usuarios.size() - 1) {
				json.append(",");
			}
		}
		json.append("]");

		out.write(json.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}