package jdbc_2.test2.dao;

import java.sql.Connection;
import java.util.List;

import jdbc_2.test2.bean.Student;

public interface StudentDao {

	/*
	 * ��������
	 */
	public boolean update(Connection conn, Student stu);
	
	/*
	 * ��ѯ����
	 */
	public Student selectTableForOne(Connection conn, Student stu);
	
}
