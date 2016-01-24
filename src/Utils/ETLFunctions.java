package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ETLFunctions {

	
	public static Connection getConnection()
	{
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:ucanaccess://D:\\Projet\\Projet Smith Garage\\Base de données\\ProjetSmithGarageBD.mdb");
			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean validateUniqueClient(String parameter)
	{
		try {
		Connection con = getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from tblClients WHERE TELEPHONE= '"+ parameter+ "'");
		if (rs.next())
		{
			return false;
		}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	
	
}
