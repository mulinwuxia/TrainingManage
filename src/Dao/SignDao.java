package Dao;

import java.util.List;

import Entity.Sign;

public interface SignDao {
	/**
	 ** 查询所有培训参与者签到信息
	 */
	public  List<Sign> getAllSign();

	/**
	 ** 根据已知培训参与者的信息查询培训参与者签到信息
	 */
	public  Sign selectSign(String sql, String[] param);
	
	/**
	 ** 根据培训信息查询培训参与者签到信息
	 */
	public List<Sign> selectSigns(String sql, String[] param);

	/**
	 ** 更新培训参与者签到信息
	 */
	public  int updateSign(String sql, Object[] param);
}
