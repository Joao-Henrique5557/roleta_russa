package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Beans.Novidade;
import util.Banco;

public class NovidadeDAO {

	public boolean cadastrar(Novidade novidade) {
		// Adicionadas as novas colunas correspondentes ao seu Model
		String sql = "INSERT INTO novidade (titulo, descricao, tipo, autor, versao, data_publicacao, ativo) "
				+ "VALUES (?, ?, ?::tipo_novidade, ?, ?, ?, ?)";
		Banco banco = new Banco();

		try (Connection conn = banco.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, novidade.getTitulo());
			stmt.setString(2, novidade.getDescricao());
			stmt.setString(3, novidade.getTipo());
			stmt.setString(4, novidade.getAutor());
			stmt.setString(5, novidade.getVersao());

			// Converte LocalDateTime para o formato que o banco de dados entende
			if (novidade.getDataPublicacao() != null) {
				stmt.setTimestamp(6, Timestamp.valueOf(novidade.getDataPublicacao()));
			} else {
				stmt.setNull(6, java.sql.Types.TIMESTAMP);
			}

			stmt.setBoolean(7, novidade.isAtivo());

			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;

		} catch (Exception e) {
			System.out.println("Erro ao cadastrar novidade no DAO: " + e);
			return false;
		}
	}
	
	// NOVO MÉTODO: Listar as novidades ativas ordenadas pela data mais recente
		public List<Novidade> listarTodas() {
			List<Novidade> lista = new ArrayList<>();
			// Traz apenas as novidades marcadas como ativas, mostrando as mais recentes primeiro
			String sql = "SELECT id, titulo, descricao, tipo, autor, versao, data_publicacao, ativo FROM novidade WHERE ativo = true ORDER BY data_publicacao DESC LIMIT 10";
			Banco banco = new Banco();

			try (Connection conn = banco.conectar();
					PreparedStatement stmt = conn.prepareStatement(sql);
					ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {
					Novidade n = new Novidade();
					n.setId(rs.getInt("id"));
					n.setTitulo(rs.getString("titulo"));
					n.setDescricao(rs.getString("descricao"));
					n.setTipo(rs.getString("tipo")); // O driver lê o ENUM como String perfeitamente
					n.setAutor(rs.getString("autor"));
					n.setVersao(rs.getString("versao"));
					
					// Converte o Timestamp do banco de volta para LocalDateTime do Java
					Timestamp ts = rs.getTimestamp("data_publicacao");
					if (ts != null) {
						n.setDataPublicacao(ts.toLocalDateTime());
					}
					
					n.setAtivo(rs.getBoolean("ativo"));

					lista.add(n);
				}

			} catch (Exception e) {
				System.out.println("Erro ao listar novidades no DAO: " + e);
			}
			return lista;
		}
}