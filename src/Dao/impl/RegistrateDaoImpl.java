package Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.BaseDao;
import Dao.RegistrateDao;
import Entity.Apply;
import Entity.Registrate;

public class RegistrateDaoImpl extends BaseDao implements RegistrateDao {
	
	private Connection conn = null; // 保存数据库连接

	private PreparedStatement pstmt = null; // 用于执行SQL语句

	private ResultSet rs = null; // 用户保存查询到结果集

	@Override
	/**
	 ** 查询所有培训参与者签到信息
	 */
	public List<Registrate> getAllRegistrate() {
		List<Registrate> registrateList = new ArrayList<Registrate>();
		try {
		String preparedSql = "select * from apply ";
		conn = getConn(); // 得到数据库连接
		pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
		rs = pstmt.executeQuery(); // 执行SQL语句
			while (rs.next()) {
				Registrate registrate = new Registrate();
				registrate.setParticipantId(rs.getInt(1));
				registrate.setParticipantName(rs.getString(2));
				registrate.setTrainingId(rs.getInt(3));
				registrate.setRegistrateFlag(rs.getInt(4));
				registrateList.add(registrate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return registrateList;
	}

	@Override
	/**
	 ** 根据已知培训参与者的信息查询培训参与者签到信息
	 */
	public Registrate selectRegistrate(String sql, String[] param) {
		Registrate registrate = new Registrate();
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
				registrate.setParticipantId(rs.getInt(1));
				registrate.setParticipantName(rs.getString(2));
				registrate.setTrainingId(rs.getInt(3));
				registrate.setRegistrateFlag(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return registrate;
	}

	@Override
	/**
	 ** 根据培训信息查询培训参与者签到信息
	 */
	public List<Registrate> selectRegistrates(String sql, String[] param) {
		
		List<Registrate> registrateList = new ArrayList<Registrate>();
		
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
				Registrate registrate = new Registrate();
				registrate.setParticipantId(rs.getInt(1));
				registrate.setParticipantName(rs.getString(2));
				registrate.setTrainingId(rs.getInt(3));
				registrate.setRegistrateFlag(rs.getInt(4));
				registrateList.add(registrate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return registrateList;
	}

	@Override
	/**
	 ** 更新培训参与者签到信息
	 */
	public int updateRegistrate(String sql, Object[] param) {
		int count = super.executeSQL(sql, param);
		return count;
	}

}
