package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.Jogador;
import model.Usuario;
import util.Banco;

public class UsuarioDAO {
	public void inserirUsuario(Usuario jg) {
		try {
			Connection con = new Banco().conectar();
			String inserir = "insert into usuario(nome, email, senha, pontos) values (?,?,?,?);"; 
			PreparedStatement pst = con.prepareStatement(inserir);
			pst.setString(1, jg.getNome());
			pst.setString(2, jg.getEmail());
			pst.setString(3, jg.getSenha());
			pst.setInt(4, jg.getPontos());
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
