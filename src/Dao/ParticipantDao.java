package Dao;

import java.util.*;

import Entity.Participant;

/**
 ** 培训参与者dao接口
 */

public interface ParticipantDao {
	/**
	 ** 查询所有培训参与者信息
	 */
	public  List<Participant> getAllParticipant();

	/**
	 ** 根据已知培训参与者的信息查询培训参与者信息
	 */
	public  Participant selectParticipant(String sql, String[] param);
	
	/**
	 ** 根据培训信息查询培训参与者信息
	 */
	public List<Participant> selectParticipants(String sql, String[] param);

	/**
	 ** 更新培训参与者信息
	 */
	public  int updateParticipant(String sql, Object[] param);
}
