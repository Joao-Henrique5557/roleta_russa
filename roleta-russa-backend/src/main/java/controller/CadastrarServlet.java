package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;

import dao.UsuarioDAO;

/**
 * Servlet implementation class EntrarServlet
 */
@WebServlet("/CadastrarServlet")
public class CadastrarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CadastrarServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String nomeDado = request.getParameter("nome");
		String senhaDada = request.getParameter("senha");
		String emailDado = request.getParameter("email");

		Usuario user = new Usuario();
		user.setNome(nomeDado);
		user.setSenha(senhaDada);
		user.setEmail(emailDado);

		UsuarioDAO dao = new UsuarioDAO();
		dao.inserirUsuario(user);

		response.sendRedirect("sucesso.html");
	}

}
