package Dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.BaseDao;
import Dao.ParticipantDao;
import Entity.Apply;
import Entity.Participant;

public class ParticipantDaoImpl extends BaseDao implements ParticipantDao {
	
	private Connection conn = null; // 保存数据库连接

	private PreparedStatement pstmt = null; // 用于执行SQL语句

	private ResultSet rs = null; // 用户保存查询到结果集

	@Override
	/**
	 ** 查询所有培训参与者信息
	 */
	public List<Participant> getAllParticipant() {
		List<Participant> participantList = new ArrayList<Participant>();
		try {
		String preparedSql = "select * from participant ";
		conn = getConn(); // 得到数据库连接
		pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
		rs = pstmt.executeQuery(); // 执行SQL语句
			while (rs.next()) {
				Participant participant = new Participant();
				participant.setId(rs.getInt(1));
				participant.setName(rs.getString(2));
				participant.setPassword(rs.getString(3));
				participant.setMoney(rs.getDouble(4));
				participantList.add(participant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return participantList;
	}

	@Override
	/**
	 ** 根据已知培训参与者的信息查询培训参与者信息
	 */
	public Participant selectParticipant(String sql, String[] param) {
		Participant participant = new Participant();
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
				participant.setId(rs.getInt(1));
				participant.setName(rs.getString(2));
				participant.setPassword(rs.getString(3));
				participant.setMoney(rs.getDouble(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			super.closeAll(conn, pstmt, rs);
		}
		return participant;
	}

	@Override
	/**
	 ** 根据培训信息查询培训参与者信息
	 */
	public List<Participant> selectParticipants(String sql, String[] param) {
		 List<Participant> participantList = new ArrayList<Participant>();
			
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
					Participant participant = new Participant();
					participant.setId(rs.getInt(1));
					participant.setName(rs.getString(2));
					participant.setPassword(rs.getString(3));
					participant.setMoney(rs.getDouble(4));
					participantList.add(participant);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				super.closeAll(conn, pstmt, rs);
			}
			return participantList;
	}

	@Override
	/**
	 ** 更新培训参与者信息
	 */
	public int updateParticipant(String sql, Object[] param) {
		int count = super.executeSQL(sql, param);
		return count;
	}

}
