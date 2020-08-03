package jdbc_2.test.transaction;

import java.sql.Connection;

import org.junit.Test;

import jdbc_2.test.util.JDBCUtils;

public class ConnectionTest {

	@Test
	public void testConnection() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		System.out.println(conn);
		System.out.println("test push");
	}
	
}
