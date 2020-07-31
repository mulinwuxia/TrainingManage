package Dao;

import java.util.List;

import Entity.Apply;
/**
 ** 培训申请dao接口
 */
public interface ApplyDao {
	/**
	 ** 查询所有培训参与者申请信息
	 */
	public  List<Apply> getAllApply();

	/**
	 ** 根据已知培训参与者的信息查询培训参与者申请信息
	 */
	public  Apply selectApply(String sql, String[] param);
	
	/**
	 ** 根据培训信息查询培训参与者申请信息
	 */
	public List<Apply> selectApplys(String sql, String[] param);

	/**
	 ** 更新培训参与者申请信息
	 */
	public  int updateApply(String sql, Object[] param);
}
