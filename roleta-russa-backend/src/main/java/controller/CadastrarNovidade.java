package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Beans.Novidade;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import dao.NovidadeDAO;

@WebServlet("/CadastrarNovidade")
public class CadastrarNovidade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CadastrarNovidade() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		// 1. Coleta TODOS os parâmetros do seu novo Model
		String titulo = request.getParameter("titulo");
		String descricao = request.getParameter("descricao");
		String tipo = request.getParameter("tipo");
		String autor = request.getParameter("autor");
		String versao = request.getParameter("versao");
		String ativoParam = request.getParameter("ativo");

		// Validação básica dos campos principais
		if (titulo == null || descricao == null || tipo == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("{\"error\": \"Campos obrigatórios ausentes (titulo, descricao, tipo)\"}");
			return;
		}

		// 2. Popula o objeto Model com as propriedades da sua classe
		Novidade novidade = new Novidade();
		novidade.setTitulo(titulo);
		novidade.setDescricao(descricao);
		novidade.setTipo(tipo.toUpperCase());
		novidade.setAutor(autor != null ? autor : "Anônimo");
		novidade.setVersao(versao != null ? versao : "1.0.0");

		// Define a data de publicação como o momento exato do cadastro
		novidade.setDataPublicacao(LocalDateTime.now());

		// Define ativo como true por padrão se não for enviado, ou avalia o parâmetro
		boolean ativo = ativoParam == null || Boolean.parseBoolean(ativoParam);
		novidade.setAtivo(ativo);

		// 3. Envia para o DAO salvar no Netlify
		NovidadeDAO dao = new NovidadeDAO();
		boolean sucesso = dao.cadastrar(novidade);

		// 4. Resposta JSON para o Frontend no Netlify
		if (sucesso) {
			response.setStatus(HttpServletResponse.SC_CREATED);
			out.write("{\"message\": \"Novidade criada com sucesso!\"}");
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write("{\"error\": \"Erro ao salvar no banco de dados.\"}");
		}
	}
}