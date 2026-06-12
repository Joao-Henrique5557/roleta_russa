package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.Jogador;
import util.Banco;

public class UsuarioDAO {
	public void inserirUsuario(String nome, String email, String senha) {
		Jogador jg = new Jogador();
		jg.setNome(nome);
		jg.setEmail(email);
		jg.setSenha(senha);
		
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
