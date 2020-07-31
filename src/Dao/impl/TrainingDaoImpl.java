package Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.BaseDao;
import Dao.TrainingDao;
import Entity.Apply;
import Entity.Training;

public class TrainingDaoImpl extends BaseDao implements TrainingDao {
	
	private Connection conn = null; // 保存数据库连接

	private PreparedStatement pstmt = null; // 用于执行SQL语句

	private ResultSet rs = null; // 用户保存查询到结果集

	@Override
	/**
	 ** 查询所有培训课程信息
	 */
	public List<Training> getAllTraining() {
		List<Training> trainingList = new ArrayList<Training>();
		try {
		String preparedSql = "select * from training ";
		conn = getConn(); // 得到数据库连接
		pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
		rs = pstmt.executeQuery(); // 执行SQL语句
			while (rs.next()) {
				Training training = new Training();
				training.setId(rs.getInt(1));
				training.setName(rs.getString(2));
				training.setCompanyId(rs.getInt(3));
				training.setTime(rs.getDate(4));
				training.setPrice(rs.getDouble(5));
				training.setCapacity(rs.getInt(6));
				trainingList.add(training);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return trainingList;
	}

	@Override
	/**
	 ** 根据已知培训课程的信息查询培训课程信息
	 */
	public Training selectTraining(String sql, String[] param) {
		Training training = new Training();
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
				training.setId(rs.getInt(1));
				training.setName(rs.getString(2));
				training.setCompanyId(rs.getInt(3));
				training.setTime(rs.getDate(4));
				training.setPrice(rs.getDouble(5));
				training.setCapacity(rs.getInt(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return training;
	}

	@Override
	/**
	 ** 根据已知培训参与者的信息查询培训课程信息
	 */
	public List<Training> selectTrainings(String sql, String[] param) {
		List<Training> trainingList = new ArrayList<Training>();
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
					Training training = new Training();
					training.setId(rs.getInt(1));
					training.setName(rs.getString(2));
					training.setCompanyId(rs.getInt(3));
					training.setTime(rs.getDate(4));
					training.setPrice(rs.getDouble(5));
					training.setCapacity(rs.getInt(6));
					trainingList.add(training);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				super.closeAll(conn, pstmt, rs);
			}
			return trainingList;
		
	}

	@Override
	/**
	 ** 更新培训课程信息
	 */
	public int updateTraining(String sql, Object[] param) {
		int count = super.executeSQL(sql, param);
		return count;
	}

}
