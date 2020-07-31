package Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.BaseDao;
import Dao.GradeDao;
import Entity.Grade;

public class GradeDaoImpl extends BaseDao implements GradeDao {
	
	private Connection conn = null; // 保存数据库连接

	private PreparedStatement pstmt = null; // 用于执行SQL语句

	private ResultSet rs = null; // 用户保存查询到结果集

	@Override
	/**
	 ** 查询所有培训参与者成绩信息
	 */
	public List<Grade> getAllGrade() {
		List<Grade> gradeList = new ArrayList<Grade>();
		try {
		String preparedSql = "select * from grade ";
		conn = getConn(); // 得到数据库连接
		pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
		rs = pstmt.executeQuery(); // 执行SQL语句
			while (rs.next()) {
				Grade grade = new Grade();
				grade.setParticipantId(rs.getInt(1));
				grade.setParticipantName(rs.getString(2));
				grade.setTrainingId(rs.getInt(3));
				grade.setScore(rs.getDouble(4));
				gradeList.add(grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return gradeList;
	}

	@Override
	/**
	 ** 根据已知培训参与者的信息查询培训参与者成绩信息
	 */
	public Grade selectGrade(String sql, String[] param) {
		Grade grade = new Grade();
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
				grade.setParticipantId(rs.getInt(1));
				grade.setParticipantName(rs.getString(2));
				grade.setTrainingId(rs.getInt(3));
				grade.setScore(rs.getDouble(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return grade;
	}

	@Override
	/**
	 ** 根据培训信息查询培训参与者成绩信息
	 */
	public List<Grade> selectGrades(String sql, String[] param) {
		
		List<Grade> gradeList = new ArrayList<Grade>();
		
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
				Grade grade = new Grade();
				grade.setParticipantId(rs.getInt(1));
				grade.setParticipantName(rs.getString(2));
				grade.setTrainingId(rs.getInt(3));
				grade.setScore(rs.getDouble(4));
				gradeList.add(grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return gradeList;
	}

	@Override

	/**
	 ** 更新培训参与者成绩信息
	 */
	public int updateGrade(String sql, Object[] param) {
		int count = super.executeSQL(sql, param);
		return count;
	}

}
