package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Usuario;
import util.Banco;

public class UsuarioDAO {

	public void inserirUsuario(Usuario u) {

		String sql = "INSERT INTO usuario(nome, email, senha, pontos) VALUES (?,?,?,?)";

		try (Connection con = new Banco().conectar(); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setString(1, u.getNome());
			pst.setString(2, u.getEmail());
			pst.setString(3, u.getSenha());
			pst.setInt(4, u.getPontos());

			pst.executeUpdate();

			System.out.println("Usuário inserido com sucesso");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
