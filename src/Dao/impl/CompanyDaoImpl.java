package Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.BaseDao;
import Dao.CompanyDao;
import Entity.Company;

public class CompanyDaoImpl extends BaseDao implements CompanyDao {
	
	private Connection conn = null; // 保存数据库连接

	private PreparedStatement pstmt = null; // 用于执行SQL语句

	private ResultSet rs = null; // 用户保存查询到结果集

	@Override
	/**
	 ** 查询出所有培训公司
	 */
	public List<Company> getAllCompany() {
		List<Company> companyList = new ArrayList<Company>();
		try {
		String preparedSql = "select * from company ";
		conn = getConn(); // 得到数据库连接
		pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
		rs = pstmt.executeQuery(); // 执行SQL语句
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt(1));
				company.setName(rs.getString(2));
				company.setMoney(rs.getDouble(3));
				companyList.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return companyList;
	}

	@Override
	/**
	 ** 根据查询条件查询出培训公司
	 */
	public Company getCompany(String sql, String[] param) {
		Company company = new Company();
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
				company.setId(rs.getInt(1));
				company.setName(rs.getString(2));
				company.setMoney(rs.getDouble(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return company;
	}

	@Override
	/**
	 ** 更新培训公司信息
	 */
	public int updateCompany(String sql, Object[] param) {
		int count = super.executeSQL(sql, param);
		return count;
	}

}
