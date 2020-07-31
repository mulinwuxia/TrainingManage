package Dao;

import java.util.List;

import Entity.Grade;

public interface GradeDao {
	/**
	 ** 查询所有培训参与者成绩信息
	 */
	public  List<Grade> getAllGrade();

	/**
	 ** 根据已知培训参与者的信息查询培训参与者成绩信息
	 */
	public  Grade selectGrade(String sql, String[] param);
	
	/**
	 ** 根据培训信息查询培训参与者成绩信息
	 */
	public List<Grade> selectGrades(String sql, String[] param);

	/**
	 ** 更新培训参与者成绩信息
	 */
	public  int updateGrade(String sql, Object[] param);
}
