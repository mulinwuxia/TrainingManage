package Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.AdministratorDao;
import Dao.BaseDao;
import Entity.Administrator;
import Entity.Company;

public class AdministratorDaoImpl extends BaseDao implements AdministratorDao {
	private Connection conn = null; // 保存数据库连接

	private PreparedStatement pstmt = null; // 用于执行SQL语句

	private ResultSet rs = null; // 用户保存查询到结果集
	
	
	@Override
	/**
	 ** 查询出所有培训公司管理者
	 */
	public List<Administrator> getAllAdministrator() {
		
		List<Administrator> administratorList = new ArrayList<Administrator>();
		try {
		String preparedSql = "select * from administrator ";
		conn = getConn(); // 得到数据库连接
		pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
		rs = pstmt.executeQuery(); // 执行SQL语句
			while (rs.next()) {
				Administrator administrator = new Administrator();
				administrator.setId(rs.getInt(1));
				administrator.setName(rs.getString(2));
				administrator.setCompanyId(rs.getInt(3));
				administrator.setPassword(rs.getString(4));
				administratorList.add(administrator);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return administratorList;
	}

	@Override
	/**
	 ** 根据查询条件查询出培训公司管理者
	 */
	public Administrator getAdministrator(String sql, String[] param) {
		Administrator administrator = new Administrator();
		try {
		conn = getConn(); // 得到数据库连接
		pstmt = conn.prepareStatement(sql); // 得到PreparedStatement对象
		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				pstmt.setString(i + 1, param[i]); // 为预编译sql设置参数
			}
		}
		rs = pstmt.executeQuery(); // 执行SQL语句
			while (rs.next()) {
				administrator.setId(rs.getInt(1));
				administrator.setName(rs.getString(2));
				administrator.setCompanyId(rs.getInt(3));
				administrator.setPassword(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return administrator;
	}

	@Override
	/**
	 ** 更新培训公司信息管理者
	 */
	public int updateAdministrator(String sql, Object[] param) {
		int count = super.executeSQL(sql, param);
		return count;
	}

}
