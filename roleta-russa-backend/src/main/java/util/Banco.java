package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Banco {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/roleta_russa?useTimezone=True";
	private String user = "root";
	private String password = "5557";

	public Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
