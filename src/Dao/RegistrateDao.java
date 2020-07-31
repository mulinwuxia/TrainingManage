package Dao;

import java.util.List;

import Entity.Registrate;

public interface RegistrateDao {
	/**
	 ** 查询所有培训参与者签到信息
	 */
	public  List<Registrate> getAllRegistrate();

	/**
	 ** 根据已知培训参与者的信息查询培训参与者签到信息
	 */
	public  Registrate selectRegistrate(String sql, String[] param);
	
	/**
	 ** 根据培训信息查询培训参与者签到信息
	 */
	public List<Registrate> selectRegistrates(String sql, String[] param);

	/**
	 ** 更新培训参与者签到信息
	 */
	public  int updateRegistrate(String sql, Object[] param);
}
