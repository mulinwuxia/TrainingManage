package Dao;

import java.util.List;

import Entity.Training;

/**
 ** 培训课程dao接口
 */
public interface TrainingDao {
	/**
	 ** 查询所有培训课程信息
	 */
	public  List<Training> getAllTraining();

	/**
	 ** 根据已知培训课程的信息查询培训课程信息
	 */
	public Training selectTraining(String sql, String[] param);
	
	/**
	 ** 根据已知培训参与者的信息查询培训课程信息
	 */
	public List<Training> selectTrainings(String sql, String[] param);

	/**
	 ** 更新培训课程信息
	 */
	public  int updateTraining(String sql, Object[] param);
}
