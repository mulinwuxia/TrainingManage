package Dao;

import java.util.*;

import Entity.Company;

/**
 ** 培训公司dao接口
 *
 */

public interface CompanyDao {
	/**
	 ** 查询出所有培训公司
	 */
	public  List<Company> getAllCompany();

	/**
	 ** 根据查询条件查询出培训公司
	 */
	public  Company getCompany(String sql, String[] param);

	/**
	 ** 更新培训公司信息
	 */
	public  int updateCompany(String sql, Object[] param);

}
