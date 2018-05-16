package farsnet.database;

import java.io.File;
import java.sql.*;

public class SqlLiteDbUtility {
	
	private static Connection connection = null;

	public static Connection getConnection() 
	{
		if (connection != null)
			return connection;
		else 
		{		
			try
			{
				File dbfile=new File(".");
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:"+dbfile.getAbsolutePath()+"/farsnet3.0.db3");
				System.out.println(dbfile.getAbsolutePath());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return connection;
		}
	}
}
