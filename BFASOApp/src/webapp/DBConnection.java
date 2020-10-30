package webapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

public static Connection con = null;
	
	public static Connection getDBConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bfaso?useSSL=false", "root", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

}
