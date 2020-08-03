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

public abstract class BaseDAO<T> { // 此处添加范型，可在子类中获得到实体类对象。

	Class<T> clazz = null;
	{ // 使用构造函数或者代码块来初始化Class。风骚操作，不懂。
		Type genericSuperClass = this.getClass().getGenericSuperclass(); // this指向子类实例化对象。
        ParameterizedType paramType = (ParameterizedType) genericSuperClass;
        Type[] typeArguments = paramType.getActualTypeArguments(); // 获取父类的范型参数
        clazz = (Class<T>)typeArguments[0]; // 泛型的第一个参数
	}
	/*
	 * insert.update.delete.DDL更新表的操作.
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
	 * 查询一条数据
	 * 注意columnCount字段值和表的列数是否对的上
	 * clazz.getDeclaredFields().length;获取类字段数。
	 * 注意rs.next()运行之后才能指向下一个字段。
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
			int columnCount = rsmData.getColumnCount(); // 列数对不上，可以使用字段查询sql。
			int count = clazz.getDeclaredFields().length;
			
			if (rs.next()) { // 新加的一行，不加就不能获得数据。
				for (int i = 0; i < count; i++) {
					String columnLabel = rsmData.getColumnLabel(i+1);
					Field field = clazz.getDeclaredField(columnLabel);
					System.out.println("columnLabel:"+columnLabel+" field:"
							+field +" columnValue:"+rs.getObject(i+1)+" count:"+count);
					field.setAccessible(true);
					field.set(t, rs.getObject(i+1)); // 对象设置值
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
	 * 查询一组数据
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
				int columnCount = rsmData.getColumnCount(); // 同上问题。
				T t = clazz.getDeclaredConstructor().newInstance();				
				for (int i = 0; i < columnCount; i++) {
					String columnLabel = rsmData.getColumnLabel(i+1);
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, rs.getObject(i+1)); // 对象设置值
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
