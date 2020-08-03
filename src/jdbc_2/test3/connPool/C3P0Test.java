package jdbc_2.test3.connPool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Test {

	/*
	 * �ֶ���ȡ���ӣ����Ƽ���
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
		
//		DataSources.destroy( cpds ); // �������ӳأ����á�
	}
	
	/*
	 * �����ļ����ֲ��ܸģ�ͨ�������ļ���ȡ���ӡ�
	 */
//	@Test
	public void testGetConnection2() throws SQLException {
		DataSource cpds = new ComboPooledDataSource("c3p0propxml");
		Connection conn = cpds.getConnection();
		System.out.println(conn);
	}
	
}
