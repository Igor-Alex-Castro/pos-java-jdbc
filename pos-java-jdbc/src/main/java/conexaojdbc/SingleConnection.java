package conexaojdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
	private static String url = "jdbc:postgresql://localhost:5433/posjava";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection connection = null;
	
	static {
		
		connectar();
	}
	
	private SingleConnection() {
		connectar();
	}

	private static void connectar() {
		// TODO Auto-generated method stub
		try {
			
			if(connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
				System.out.println("Connectou com sucesso!!");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
