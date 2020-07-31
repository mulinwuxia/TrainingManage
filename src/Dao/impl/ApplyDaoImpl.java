package Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.ApplyDao;
import Dao.BaseDao;
import Entity.Apply;

public class ApplyDaoImpl extends BaseDao implements ApplyDao {
	
	private Connection conn = null; // 保存数据库连接

	private PreparedStatement pstmt = null; // 用于执行SQL语句

	private ResultSet rs = null; // 用户保存查询到结果集

	@Override
	/**
	 ** 查询所有培训参与者申请信息
	 */
	public List<Apply> getAllApply() {
		List<Apply> applyList = new ArrayList<Apply>();
		try {
		String preparedSql = "select * from apply ";
		conn = getConn(); // 得到数据库连接
		pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
		rs = pstmt.executeQuery(); // 执行SQL语句
			while (rs.next()) {
				Apply apply = new Apply();
				apply.setParticipantId(rs.getInt(1));
				apply.setParticipantName(rs.getString(2));
				apply.setTrainingId(rs.getInt(3));
				applyList.add(apply);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return applyList;
	}

	@Override
	/**
	 ** 根据已知培训参与者的信息查询培训参与者申请信息
	 */
	public Apply selectApply(String sql, String[] param) {
		Apply apply = new Apply();
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
				apply.setParticipantId(rs.getInt(1));
				apply.setParticipantName(rs.getString(2));
				apply.setTrainingId(rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return apply;
	}

	@Override
	/**
	 ** 根据培训信息查询培训参与者申请信息
	 */
	public List<Apply> selectApplys(String sql, String[] param) {
	
        List<Apply> applyList = new ArrayList<Apply>();
		
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
				Apply apply = new Apply();
				apply.setParticipantId(rs.getInt(1));
				apply.setParticipantName(rs.getString(2));
				apply.setTrainingId(rs.getInt(3));
				applyList.add(apply);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return applyList;
	}

	@Override
	/**
	 ** 更新培训参与者申请信息
	 */
	public int updateApply(String sql, Object[] param) {
		int count = super.executeSQL(sql, param);
		return count;
	}

}
