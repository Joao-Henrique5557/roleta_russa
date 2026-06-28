package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Beans.Usuario;
import util.Banco;

public class UsuarioDAO {

	public boolean inserirUsuario(Usuario usuario) {
		String sql = "INSERT INTO usuarios (nome, email, senha, pontos) VALUES (?, ?, ?, ?)";
		util.Banco banco = new util.Banco();

		try (java.sql.Connection conn = banco.conectar();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getSenha());
			stmt.setInt(4, usuario.getPontos());

			int linhas = stmt.executeUpdate();
			return linhas > 0; // Retorna true se inseriu no banco

		} catch (Exception e) {
			e.printStackTrace(); // Mostra o erro real no console do Eclipse se falhar
			return false;
		}
	}

	public List<Usuario> listarTodos() {
		List<Usuario> lista = new ArrayList<>();
		String sql = "SELECT id, nome, email, pontos FROM usuarios ORDER BY pontos DESC LIMIT 10";
		Banco banco = new util.Banco();

		try (Connection conn = banco.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Usuario u = new Usuario();
				u.setId(rs.getInt("id"));
				u.setNome(rs.getString("nome"));
				u.setEmail(rs.getString("email"));
				u.setPontos(rs.getInt("pontos"));

				// Omitimos a senha por segurança na listagem pública
				lista.add(u);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
