package Dao;

import java.util.List;

import Entity.Administrator;

/**
 ** 培训公司管理者dao接口
 *
 */

public interface AdministratorDao {
	/**
	 ** 查询出所有培训公司管理者
	 */
	public  List<Administrator> getAllAdministrator();

	/**
	 ** 根据查询条件查询出培训公司管理者
	 */
	public  Administrator getAdministrator(String sql, String[] param);

	/**
	 ** 更新培训公司信息管理者
	 */
	public  int updateAdministrator(String sql, Object[] param);
}
