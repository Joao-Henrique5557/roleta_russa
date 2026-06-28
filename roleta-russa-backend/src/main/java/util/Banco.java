package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Banco {
	private String driver = "org.postgresql.Driver";

	private String url = "jdbc:postgresql://ep-tiny-star-aj8bth4t.c-3.us-east-2.db.netlify.com/netlifydb?sslmode=require";

	private String user = "netlifydb_owner";

	private String password = "npg_G8qrp6BfRUIe";

	public Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println("Erro ao conectar ao banco do Netlify: " + e);
			return null;
		}
	}
}