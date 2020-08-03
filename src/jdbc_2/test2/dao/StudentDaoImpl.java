package jdbc_2.test2.dao;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;

import jdbc_2.test.util.JDBCUtils;
import jdbc_2.test2.bean.Student;

public class StudentDaoImpl extends BaseDAO<Student> implements StudentDao{

	@Override
	public boolean update(Connection conn, Student stu) {
		String sql = "update stuinfo set stuname=?,stuphone=? where id = ?";
		return updateTable(conn, sql, stu.getStuname(),stu.getStuphone(),stu.getId());
		
	}
	
//	@Test
	public void testupdate() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			Student stu = new Student(21, "Rose21New", "12312333344");
			Student stuGet = selectTableForOne(conn, stu);
			System.out.println(stuGet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Override
	public Student selectTableForOne(Connection conn, Student stu) {
		String sql = "select * from stuinfo where id = ?";
		return selectTableForOne(conn, sql, stu.getId());
		
	}
	
}
