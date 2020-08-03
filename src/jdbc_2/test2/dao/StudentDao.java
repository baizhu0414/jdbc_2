package jdbc_2.test2.dao;

import java.sql.Connection;
import java.util.List;

import jdbc_2.test2.bean.Student;

public interface StudentDao {

	/*
	 * 更新数据
	 */
	public boolean update(Connection conn, Student stu);
	
	/*
	 * 查询数据
	 */
	public Student selectTableForOne(Connection conn, Student stu);
	
}
