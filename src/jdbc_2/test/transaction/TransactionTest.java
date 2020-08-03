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
			conn.setAutoCommit(false); /* ���ùر��Զ��ύ��*/
			String sql1 = "update stuinfo set stumoney=stumoney-100 where id = ?";
			String sql2 = "update stuinfo set stumoney=stumoney+100 where id = ?";
			
			updateCommonTest(conn, sql1, 1);
//			int x = 100/0; // �����쳣�Ჶ׽�����ˡ�
			updateCommonTest(conn, sql2, 3); // ѧ��1��2ת��100��
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("�ع��쳣");
			} /* �����쳣�Ļ��ͻع���*/
		} finally {			
			JDBCUtils.closeResource(conn, null);
		}
	}
	
	public boolean updateCommonTest(Connection conn, String sql, Object...ags) {
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < ags.length; i++) {
				ps.setObject( i+1 , ags[i]); // ע��˴���д���ˣ����ݿ��1��ʼ��
			}
			return 0 != ps.executeUpdate(); // ������Ӱ�������������0��ʾɶҲû����
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			JDBCUtils.closeResource(null, ps); /*���������ر�conn���������ڴ˴��رգ��������ӹر��Զ��ύ��*/
		}
		return false;
	}
	
}
