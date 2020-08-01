package Service;

import java.util.List;

import Entity.Grade;
import Entity.Participant;
import Entity.Training;

public interface ParticipantManage {
	
	/**
	 ** 培训参与者登录
	 */
	public Participant login();
	/**
	 ** 查询所参与过的培训(根据参与者的编号查找)
	 */
	public List<Training> getTrainings(int participantId);
	
	/**
	 ** 查询培训成绩排名
	 */
	public List<Grade> getGradeRanking(int trainingId, int participantId);
	
	/**
	 ** 申请参与某培训 
	 */
	public int takeTraining(int trainingId, int participantId);
	
	/**
	 ** 取消参与某培训
	 */
	public int cancelTraining(int trainingId, int participantId);
	
	/**
	 ** 培训参与者资金设置
	 */
	public int setMoney(double money,int participantId);
	
	/**
	 ** 查询培训成绩排名
	 */
	public List<Grade> getGradeRanking(int trainingId,int companyId,int participantId);
	
	/**
	 ** 培训现场签到
	 */
	public int sign(int trainingId, int participantId );
	
	

}
