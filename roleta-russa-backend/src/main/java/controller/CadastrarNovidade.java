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

		// 2. Extração manual dos dados do JSON
		String jsonLimpo = json.replace("\n", "").replace("\r", "").replace("{", "").replace("}", "").replace("\"", "");
		String[] partes = jsonLimpo.split(",");

		String titulo = null;
		String descricao = null;
		String tipo = null;
		String autor = null;
		String versao = null;
		String ativoParam = null;

		for (String parte : partes) {
			String[] chaveValor = parte.split(":", 2);
			if (chaveValor.length == 2) {
				String chave = chaveValor[0].trim();
				String valor = chaveValor[1].trim();

				if (chave.equalsIgnoreCase("titulo"))
					titulo = valor;
				if (chave.equalsIgnoreCase("descricao"))
					descricao = valor;
				if (chave.equalsIgnoreCase("tipo"))
					tipo = valor;
				if (chave.equalsIgnoreCase("autor"))
					autor = valor;
				if (chave.equalsIgnoreCase("versao"))
					versao = valor;
				if (chave.equalsIgnoreCase("ativo"))
					ativoParam = valor;
			}
		}

		// Validação básica dos campos principais
		if (titulo == null || descricao == null || tipo == null || titulo.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("{\"error\": \"Campos obrigatórios ausentes (titulo, descricao, tipo)\"}");
			return;
		}

		// 3. Popula o objeto Model com as propriedades da sua classe
		Novidade novidade = new Novidade();
		novidade.setTitulo(titulo);
		novidade.setDescricao(descricao);
		novidade.setTipo(tipo.toUpperCase());
		novidade.setAutor(autor != null ? autor : "Anônimo");
		novidade.setVersao(versao != null ? versao : "1.0.0");

		novidade.setDataPublicacao(LocalDateTime.now());

		boolean ativo = ativoParam == null || Boolean.parseBoolean(ativoParam);
		novidade.setAtivo(ativo);

		// 4. Envia para o DAO salvar
		NovidadeDAO dao = new NovidadeDAO();
		boolean sucesso = dao.cadastrar(novidade);

		if (sucesso) {
			response.setStatus(HttpServletResponse.SC_CREATED);
			out.write("{\"message\": \"Novidade criada com sucesso!\"}");
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.write("{\"error\": \"Erro ao salvar no banco de dados.\"}");
		}
	}
}