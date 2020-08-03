package jdbc_2.test2.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import jdbc_2.test.util.JDBCUtils;
import jdbc_2.test2.bean.Student;

public abstract class BaseDAO<T> { // �˴���ӷ��ͣ����������л�õ�ʵ�������

	Class<T> clazz = null;
	{ // ʹ�ù��캯�����ߴ��������ʼ��Class����ɧ������������
		Type genericSuperClass = this.getClass().getGenericSuperclass(); // thisָ������ʵ��������
        ParameterizedType paramType = (ParameterizedType) genericSuperClass;
        Type[] typeArguments = paramType.getActualTypeArguments(); // ��ȡ����ķ��Ͳ���
        clazz = (Class<T>)typeArguments[0]; // ���͵ĵ�һ������
	}
	/*
	 * insert.update.delete.DDL���±�Ĳ���.
	 */
	public boolean updateTable(Connection conn, String sqls, Object...objs) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sqls);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i+1, objs[i]);
			}
			return 1 == ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {			
			JDBCUtils.closeResource(null, ps);
		}
		return false;
	}
	
	/*
	 * ��ѯһ������
	 * ע��columnCount�ֶ�ֵ�ͱ�������Ƿ�Ե���
	 * clazz.getDeclaredFields().length;��ȡ���ֶ�����
	 * ע��rs.next()����֮�����ָ����һ���ֶΡ�
	 * columnLabel:id field:int jdbc_2.test2.bean.Student.id columnValue:21 columnCount:8
	   columnLabel:stuname field:java.lang.String jdbc_2.test2.bean.Student.stuname columnValue:Rose21New columnCount:8
	   columnLabel:stuphone field:java.lang.String jdbc_2.test2.bean.Student.stuphone columnValue:12312333344 columnCount:8
	   Student [id=21, stuname=Rose21New, stuphone=12312333344]
	 */
	public T selectTableForOne(Connection conn, String sql, Object...objs) {
		PreparedStatement ps = null;
		T t = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i+1, objs[i]);
			}
			t = clazz.getDeclaredConstructor().newInstance();
			rs = ps.executeQuery();
			
			ResultSetMetaData rsmData = rs.getMetaData();
			int columnCount = rsmData.getColumnCount(); // �����Բ��ϣ�����ʹ���ֶβ�ѯsql��
			int count = clazz.getDeclaredFields().length;
			
			if (rs.next()) { // �¼ӵ�һ�У����ӾͲ��ܻ�����ݡ�
				for (int i = 0; i < count; i++) {
					String columnLabel = rsmData.getColumnLabel(i+1);
					Field field = clazz.getDeclaredField(columnLabel);
					System.out.println("columnLabel:"+columnLabel+" field:"
							+field +" columnValue:"+rs.getObject(i+1)+" count:"+count);
					field.setAccessible(true);
					field.set(t, rs.getObject(i+1)); // ��������ֵ
				}
			}
			
			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(null, ps, rs);
		}
		return null;
		
	}
	
	/*
	 * ��ѯһ������
	 */
	public List<T> selectTableForList(Connection conn, String sql, Object...objs) {
		PreparedStatement ps = null;
		List<T> list = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++) {
				ps.setObject(i+1, objs[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ResultSetMetaData rsmData = rs.getMetaData();
				int columnCount = rsmData.getColumnCount(); // ͬ�����⡣
				T t = clazz.getDeclaredConstructor().newInstance();				
				for (int i = 0; i < columnCount; i++) {
					String columnLabel = rsmData.getColumnLabel(i+1);
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, rs.getObject(i+1)); // ��������ֵ
				}
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(null, ps, rs);
		}
		return null;
		
	}
	
}
