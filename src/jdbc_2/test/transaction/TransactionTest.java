package jdbc_2.test.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import jdbc_2.test.util.JDBCUtils;


public class TransactionTest {

	@Test
	public void testTransaction() {
		
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false); /* 设置关闭自动提交。*/
			String sql1 = "update stuinfo set stumoney=stumoney-100 where id = ?";
			String sql2 = "update stuinfo set stumoney=stumoney+100 where id = ?";
			
			updateCommonTest(conn, sql1, 1);
//			int x = 100/0; // 产生异常会捕捉并回退。
			updateCommonTest(conn, sql2, 3); // 学生1给2转账100￥
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("回滚异常");
			} /* 出现异常的话就回滚。*/
		} finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}
	
	public boolean updateCommonTest(Connection conn, String sql, Object...ags) {
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < ags.length; i++) {
				ps.setObject( i+1 , ags[i]); // 注意此处别写错了，数据库从1开始。
			}
			return 0 != ps.executeUpdate(); // 返回受影响的行数，返回0表示啥也没做。
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			JDBCUtils.closeResource(null, ps); /*事务结束后关闭conn，而不能在此处关闭，否则连接关闭自动提交。*/
		}
		return false;
	}
	
}
