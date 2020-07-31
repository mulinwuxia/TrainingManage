package Service;

import java.text.ParseException;
import java.util.List;

import Entity.Administrator;
import Entity.Grade;
import Entity.Participant;
import Entity.Training;

public interface AdministratorManage {
	/**
	 ** 培训公司管理者登陆
	 */
	public Administrator login();
	
	/**
	 ** 根据人名查询参与的培训
	 */
	public List<Training> getTrainings(String participantName,int companyId);
	
	/**
	 ** 查询某次培训共有哪些人参加
	 */
	public List<Participant> getParticipants(int trainingId,int companyId);
	
	/**
	 ** 添加新培训
	 */
	public int addTraining(int companyId) throws ParseException;
	
	/**
	 ** 删除培训
	 */
	public int deleteTraining(int trainingId, int companyId);
	
	/**
	 ** 修改资金
	 */
	public int setMoney(double money,int companyId);
	
	/**
	 ** 培训价格设置
	 */
	public int setPrice(int trainingId, int trainingCompanyId);
	
	/**
	 ** 培训时间设置
	 */
	public int setTime(int trainingId,int companyId) throws ParseException;
	
	/**
	 ** 查询培训公司总资金
	 */
	
	public double getMoney(int companyId);
	
	/**
	 ** 查询培训成绩排名
	 */
	public List<Grade> getGradeRanking(int trainingId,int companyId);
	
	/**
	 ** 培训课程成绩录入
	 */
	public int setScore(int participantId,int trainingId,int companyId);

}
