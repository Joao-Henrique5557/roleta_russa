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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Configura o CORS (importante para o Frontend do Netlify conseguir acessar a API do Render)
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		
		// Define que a resposta será um JSON em formato UTF-8
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		// Busca a lista do banco de dados
		UsuarioDAO dao = new UsuarioDAO();
		List<Usuario> usuarios = dao.listarTodos();
		
		// Monta a String JSON manualmente
		StringBuilder json = new StringBuilder();
		json.append("[");
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario u = usuarios.get(i);
			json.append("{");
			json.append("\"id\":").append(u.getId()).append(",");
			json.append("\"nome\":\"").append(u.getNome()).append("\",");
			json.append("\"email\":\"").append(u.getEmail()).append("\",");
			json.append("\"pontos\":").append(u.getPontos());
			json.append("}");
			
			// Adiciona vírgula entre os objetos, exceto no último
			if (i < usuarios.size() - 1) {
				json.append(",");
			}
		}
		json.append("]");
		
		// Envia o JSON de volta para o cliente (Talend ou Browser)
		out.write(json.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Se alguém fizer um POST por engano aqui, ele redireciona para a listagem do GET
		doGet(request, response);
	}
}