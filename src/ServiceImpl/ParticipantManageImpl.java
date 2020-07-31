package ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Dao.GradeDao;
import Dao.ParticipantDao;
import Dao.TrainingDao;
import Dao.impl.ApplyDaoImpl;
import Dao.impl.GradeDaoImpl;
import Dao.impl.ParticipantDaoImpl;
import Dao.impl.RegistrateDaoImpl;
import Dao.impl.TrainingDaoImpl;
import Entity.Apply;
import Entity.Grade;
import Entity.Participant;
import Entity.Registrate;
import Entity.Training;
import Service.ParticipantManage;

public class ParticipantManageImpl implements ParticipantManage {

	@Override
	/**
	 ** 培训参与者登录
	 */
	public Participant login() {
		Scanner input = new Scanner(System.in);
		// 输入培训参与者姓名
		System.out.println("-------培训参与者登陆-------");
		System.out.println("请您输入姓名：");
		String participantName = input.nextLine().trim();
		System.out.println("请您输入密码：");
		String participantPassword = input.nextLine().trim();
		ParticipantDao participantDao = new ParticipantDaoImpl();
		String sql = "select * from participant where `name`=? and `password`=?";
		String[] param = { participantName, participantPassword };
		Participant participant = participantDao.selectParticipant(sql, param);
		if (null != participant.getName()) {
			System.out.println("\n登录成功！");
			System.out.println("您的基本信息：");
			System.out.println("****************************************************");
			System.out.println("编号：" + participant.getId());
			System.out.println("名字：" + participant.getName());
			System.out.println("资金：" + participant.getMoney());
			System.out.println("****************************************************");
		}
		return participant;
	}

	@Override
	/**
	 ** 查询所参与过的培训(根据参与者的编号查找)
	 */
	public List<Training> getTrainings(int participantId) {
		List<Training> trainingList = new ArrayList<Training>();
		String sql = "SELECT training.`id`, training.`name`,training.`companyId`,training.`time`,training.`price`,training.`capacity`" + 
				"FROM registrate JOIN training ON registrate.`trainingId`=training.`id`" + 
				"WHERE registrate.`participantId`=?";
		String[] param = { String.valueOf(participantId) };
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		return trainingList;
	}

	@Override
	/**
	 ** 查询培训成绩排名
	 */
	public List<Grade> getGradeRanking(int trainingId, int participantId) {
		
		String sql="SELECT grade.`participantId`,grade.`participantName`,grade.`trainingId`,grade.`score`" + 
				"FROM grade JOIN training on grade.`trainingId`=training.`id`" + 
				"WHERE training.`id`=? AND grade.`participantId`=?" + 
				"ORDER BY grade.`score` DEC ";
		
		String [] param= {String.valueOf(trainingId),String.valueOf(participantId)};
		
		GradeDao gradeDao= new GradeDaoImpl();
		List<Grade> gradeList = new ArrayList<Grade>();
		
		gradeList=gradeDao.selectGrades(sql, param);
		
		return gradeList;

	}

	@Override
	/**
	 ** 申请参与某培训 
	 */
	public int takeTraining(int trainingId, int participantId) {
		//在apply表中查找是否已经申请
		String sql2 = "select * from apply where  `participantId`=? and `trainingId`=?";
		String[] param2 = {String.valueOf(participantId),String.valueOf(trainingId)};
		ApplyDaoImpl applyDao = new ApplyDaoImpl();
		Apply apply=applyDao.selectApply(sql2, param2);
		
		//在participant表查找到这个培训参与者
		String sql3 = "select * from participant where `id`=?";
		String [] param3 = {String.valueOf(participantId)};
		ParticipantDaoImpl participantDao = new ParticipantDaoImpl();
		Participant participant = participantDao.selectParticipant(sql3, param3);
		
		if(apply.getParticipantName() == null)
		{
			//在training表中查找此培训课程
			String trainingSelectSql = "select * from training where `id`=?";
			String [] trainingSelectParam = {String.valueOf(trainingId)};
			TrainingDaoImpl trainingDao = new TrainingDaoImpl();
			Training training = trainingDao.selectTraining(trainingSelectSql, trainingSelectParam);
			
			//更新training表，修改课容量
			String trainingSql="update training  set `capacity`= ? where `id`=?";
			String [] trainingParam = {String.valueOf(training.getCapacity()-1),String.valueOf(trainingId)};
			trainingDao.updateTraining(trainingSql, trainingParam);
			
			//申请参与培训,添加到apply表
			String applysql = null;
			applysql = "insert into apply(`participantId`,`participantName`,`trainingId`) values(?,?,?)";
			String [] param = {String.valueOf(participantId),participant.getName(),String.valueOf(trainingId)};
			int updateApply = applyDao.executeSQL(applysql, param);
			
			if(updateApply>0)
				System.out.println("申请成功！");
			
			
			//将申请成功的培训添加到registrate表
			String registrateSql=null;
			registrateSql="insert into registrate(`participantId`,`participantName`,`trainingId`,`registrateFlag`) values(?,?,?,?)";
			String [] registrateParam = {String.valueOf(participantId),participant.getName(),String.valueOf(trainingId),String.valueOf(0)};
			RegistrateDaoImpl registrateDao = new  RegistrateDaoImpl();
			int updateRegistrate = registrateDao.executeSQL(registrateSql, registrateParam);
			
			//将申请成功的培训添加到grade表
			String gradeSql=null;
			gradeSql="insert into grade(`participantId`,`participantName`,`trainingId`,`score`) values(?,?,?,?)";
			String [] gradeParam = { String.valueOf(participantId),participant.getName(),String.valueOf(trainingId),String.valueOf(0)};
			GradeDaoImpl gradeDao = new GradeDaoImpl();
			int updateGrade = gradeDao.executeSQL(gradeSql, gradeParam);
		
			return updateApply;
		}
		else
		{
			System.out.println("已申请，不能重新申请！");
			return 0;
		}
	}

	@Override
	/**
	 ** 取消参与某培训
	 */
	public int cancelTraining(int trainingId, int participantId) {

		//在apply表中查找是否已经申请
		String sql2 = "select * from apply where  `participantId`=? and `trainingId`=?";
		String[] param2 = {String.valueOf(participantId),String.valueOf(trainingId)};
		ApplyDaoImpl applyDao = new ApplyDaoImpl();
		Apply apply=applyDao.selectApply(sql2, param2);
		
		//在participant表查找到这个培训参与者
		String sql3 = "select * from participant where `id`=?";
		String [] param3 = {String.valueOf(participantId)};
		ParticipantDaoImpl participantDao = new ParticipantDaoImpl();
		Participant participant = participantDao.selectParticipant(sql3, param3);
		
		if(apply.getParticipantName()!=null)
		{
			//在training表中查找此培训课程
			String trainingSelectSql = "select * from training where `id`=?";
			String [] trainingSelectParam = {String.valueOf(trainingId)};
			TrainingDaoImpl trainingDao = new TrainingDaoImpl();
			Training training = trainingDao.selectTraining(trainingSelectSql, trainingSelectParam);
			
			//更新training表，修改课容量
			String trainingSql="update training  set `capacity`= ? where `id`=?";
			String [] trainingParam = {String.valueOf(training.getCapacity()+1),String.valueOf(trainingId)};
			trainingDao.updateTraining(trainingSql, trainingParam);
			
			//取消参与培训,在apply表中删除
			String applysql = null;
			applysql = "delete from apply where `participantId`=? and `trainingId`=?";
			String [] param = {String.valueOf(participantId),String.valueOf(trainingId)};
			int updateApply = applyDao.executeSQL(applysql, param);// 更新申请培训信息
			
			if(updateApply>0)
				System.out.println("取消成功！");
			
			
			//在registrate表中删除
			String registrateSql=null;
			registrateSql="delete from registrate where `participantId`=? and `trainingId`=?";
			String [] registrateParam = {String.valueOf(participantId),String.valueOf(trainingId)};
			RegistrateDaoImpl registrateDao = new  RegistrateDaoImpl();
			int updateRegistrate = registrateDao.executeSQL(registrateSql, registrateParam);
			
			//在grade表中删除
			String gradeSql=null;
			gradeSql="delete from grade where `participantId`=? and `trainingId`=?";
			String [] gradeParam = { String.valueOf(participantId),String.valueOf(trainingId)};
			GradeDaoImpl gradeDao = new GradeDaoImpl();
			int updateGrade = gradeDao.executeSQL(gradeSql, gradeParam);
			
			return updateApply;		
		}
		else
		{
			System.out.println("没有申请，不能取消申请！");
			return 0;
		}
	}

	@Override
	/**
	 ** 培训参与者资金设置
	 */
	public int setMoney(double money, int participantId) {
		String updatesql = null;
		updatesql = "update participant set `money`=? where `id`=?";
		String[] param = {String.valueOf(money),String.valueOf(participantId)};
		ParticipantDaoImpl participantDao = new ParticipantDaoImpl();
		int updateParticipant = participantDao.updateParticipant(updatesql, param);// 更新培训参与者信息
		return updateParticipant;
	}

	@Override
	/**
	 ** 查询培训成绩排名
	 */
	public List<Grade> getGradeRanking(int trainingId, int companyId, int participantId) {
		String sql="SELECT grade.`participantId`,grade.`participantName`,grade.`trainingId`,grade.`score`" + 
				"FROM grade JOIN training on grade.`trainingId`=training.`id`" + 
				"WHERE training.`id`=? AND training.`companyId`=?" + 
				"ORDER BY grade.`score` DESC ";
		String [] param= {String.valueOf(trainingId),String.valueOf(companyId)};
		
		GradeDao gradeDao= new GradeDaoImpl();
		List<Grade> gradeList = new ArrayList<Grade>();
		
		gradeList=gradeDao.selectGrades(sql, param);
		
		return gradeList;
	}

	@Override
	/**
	 ** 培训现场签到
	 */
	public int registrate(int trainingId, int participantId) {
		
		//在registrate表中查找是否存在，已申请但未签到的培训课程
		String sql2 = "select * from registrate where  `participantId`=? and `trainingId`=? and `registrateFlag`=0";
		String[] param2 = {String.valueOf(participantId),String.valueOf(trainingId)};
		RegistrateDaoImpl registrateDao = new RegistrateDaoImpl();
		Registrate registrate=registrateDao.selectRegistrate(sql2, param2);
		
		if(registrate.getParticipantName()!=null)
		{
			//更新registrate表的信息
			String updatesql = null;
			updatesql = "update registrate set `registrateFlag`=? where `participantId`=? and `trainingId`=?";
			Scanner input = new Scanner(System.in);
			System.out.println("请输入1进行签到：");
			int registrateFlag = input.nextInt();
			do {
				if(registrateFlag==1)
					break;
				else
				{
					System.out.println("输入错误，请输入1重新签到！");
					registrateFlag = input.nextInt();
			
				}
				
			}while(registrateFlag==1);
			String[] param = {String.valueOf(registrateFlag),String.valueOf(participantId), String.valueOf(trainingId)};
			int updateRegistrate = registrateDao.executeSQL(updatesql, param);
			if(updateRegistrate>0)
			{
				System.out.println("签到成功！");
			}
			
			//删除apply表中已经签到的信息
			String deleteSql = "delete from apply where `participantId`=? and `trainingId`=?";
			String [] deleteParam = {String.valueOf(participantId),String.valueOf(trainingId)};
			ApplyDaoImpl applyDao = new ApplyDaoImpl();
			applyDao.updateApply(deleteSql, deleteParam);
			
			return updateRegistrate;
		}
		else
		{
			System.out.println("不存在此种已申请但未签到的培训课程！");
			return 0;
		}
		
	}

}
