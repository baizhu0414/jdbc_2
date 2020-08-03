package jdbc_2.test3.connPool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Test {

	/*
	 * 手动获取连接，不推荐。
	 */
//	@Test
	public void testGetConnection1() throws Exception {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/stu");
		cpds.setUser("root");
		cpds.setPassword("root");
		cpds.setInitialPoolSize(10);
		
		Connection conn = cpds.getConnection();
		System.out.println(conn);
		
//		DataSources.destroy( cpds ); // 销毁连接池，不用。
	}
	
	/*
	 * 配置文件名字不能改，通过配置文件获取链接。
	 */
//	@Test
	public void testGetConnection2() throws SQLException {
		DataSource cpds = new ComboPooledDataSource("c3p0propxml");
		Connection conn = cpds.getConnection();
		System.out.println(conn);
	}
	
}
