package jdbc_2.test.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
	/**
	 * 获取连接
	 *
	 */
	public static Connection getConnection() throws Exception{
		InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
		
		Properties pro = new Properties();
		pro.load(in);
		
		String user = pro.getProperty("user");
		String password = pro.getProperty("password");
		String url = pro.getProperty("url");
		String driver = pro.getProperty("driver");
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url,user,password);
		return conn;
	}
	
	/**
	 * 关闭连接
	 */
	public static void closeResource(Connection conn, Statement statement) {
		
		try {
			if (statement!=null) {
				statement.close();
			}
			if (conn!=null) {
				conn.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * 关闭连接
	 */
	public static void closeResource(Connection conn, Statement statement, ResultSet rs) {
		
		try {
			if (statement!=null) {
				statement.close();
			}
			if (conn!=null) {
				conn.close();
			}
			if (rs!=null) {
				rs.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}
