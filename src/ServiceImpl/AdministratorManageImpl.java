package ServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Dao.AdministratorDao;
import Dao.CompanyDao;
import Dao.GradeDao;
import Dao.ParticipantDao;
import Dao.TrainingDao;
import Dao.impl.AdministratorDaoImpl;
import Dao.impl.CompanyDaoImpl;
import Dao.impl.GradeDaoImpl;
import Dao.impl.ParticipantDaoImpl;
import Dao.impl.TrainingDaoImpl;
import Entity.Administrator;
import Entity.Company;
import Entity.Grade;
import Entity.Participant;
import Entity.Training;
import Service.AdministratorManage;

public class AdministratorManageImpl implements AdministratorManage {

	@Override
	/**
	 ** 培训公司管理者登陆
	 */
	public Administrator login() {
		Scanner input = new Scanner(System.in);
		Administrator administrator = null;
		System.out.println("-------培训公司管理者登陆-------");
		System.out.println("请输入您的名称：");
		String name = input.next();
		System.out.println("请输入您的密码：");
		String password = input.next();
		AdministratorDao administratorDao = new AdministratorDaoImpl();
		String sql = "select * from administrator where `name`=? and `password`=?";
		String[] param = { name, password };
		administrator = administratorDao.getAdministrator(sql, param);
		
		String sql1="select * from company where `id`=?";
		String [] param1= {String.valueOf(administrator.getCompanyId())};
		CompanyDao companyDao=new CompanyDaoImpl();
		Company company=companyDao.getCompany(sql1, param1);
		
		if (null != administrator.getName()) {
			System.out.println("\n登录成功！");
			System.out.println("您的基本信息");
			System.out.println("****************************************************");
			System.out.println("编号：" + administrator.getId());
			System.out.println("名字：" + administrator.getName());
			System.out.println("公司：" + company.getName());
			System.out.println("****************************************************");
		}
		return administrator;
	}

	@Override
	/**
	 ** 根据人名查询参与的培训
	 */
	public List<Training> getTrainings(String participantName, int companyId) {
		List<Training> trainingList = new ArrayList<Training>();
		String sql ="SELECT training.`id`,training.`name`,training.`companyId`,training.`time`,training.`price`,training.`capacity` "
		           +"FROM sign JOIN training ON sign.`trainingId`=training.`id` "
				   +"WHERE sign.`participantName`=? AND training.`companyId`=? AND sign.`signFlag`=1";
		String[] param = { participantName,String.valueOf(companyId)};
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		return trainingList;
	}

	@Override
	/**
	 ** 查询某次培训共有哪些人参加
	 */
	public List<Participant> getParticipants(int trainingId, int companyId) {
		List<Participant> participantList = new ArrayList<Participant>();
		String sql = "SELECT participant.`id`,participant.`name`,participant.`password`,participant.`money` "
		             +"FROM  sign JOIN participant ON sign.`participantId`=participant.`id` JOIN training ON sign.`trainingId`=training.`id` "
				     +"WHERE training.`Id`=? AND training.`companyId`=? AND sign.`signFlag`=1";
		String[] param = { String.valueOf(trainingId),String.valueOf(companyId)};
		ParticipantDao petDao = new ParticipantDaoImpl();
		participantList = petDao.selectParticipants(sql, param);
		return participantList;
	}

	@Override
	/**
	 ** 添加新培训
	 */
	public int addTraining(int companyId) throws ParseException {
		Scanner input = new Scanner(System.in);
	    System.out.println("请输入新培训的名称：");
		String trainingName = input.nextLine();
		System.out.println("请输入新培训的时间：");
		String trainingTime = input.nextLine();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=sdf.parse(trainingTime);
		
		Date date = new Date();
		String Time=sdf.format(date);
		Date date2=sdf.parse(Time);
		while(date1.before(date2))
		{
			System.out.println("时间不能在今日之前，请重新输入：");
			trainingTime = input.nextLine();
			date1=sdf.parse(trainingTime);
		}
		
		System.out.println("请输入新培训的课容量：");
		int trainingNumber = input.nextInt();
		while(trainingNumber<0)
		{
			System.out.println("课容量不能小于0，请重新输入：");
			trainingNumber = input.nextInt();
		}
		System.out.println("请输入新培训的价格：");
		double trainingPrice = input.nextDouble();
		while(trainingPrice<0)
		{
			System.out.println("价格不能小于0，请重新输入：");
			trainingPrice = input.nextDouble();
		}
		
	    String sql = "insert into training(`name`,`companyId`,`time`,`price`,`capacity`) values(?,?,?,?,?)";
	    String [] param = {trainingName,String.valueOf(companyId),trainingTime,String.valueOf(trainingPrice),String.valueOf(trainingNumber)};
	    TrainingDao trainingDao = new TrainingDaoImpl();
		int count = 0;
	    count = trainingDao.updateTraining(sql, param);
		if (count > 0) {
			System.out.println("成功添加"+trainingTime+"《" + trainingName + "》培训,课容量为"+trainingNumber+"人，价格为"+trainingPrice+"元");
		}
		else
			System.out.println("添加失败！");
		return count;
	}

	@Override
	/**
	 ** 删除培训
	 */
	public int deleteTraining(int trainingId, int companyId) {
		String sql = "SELECT * FROM training WHERE `id` = ? and `companyId`=?";
	    String[] param = {String.valueOf(trainingId),String.valueOf(companyId)};
	    TrainingDao trainingDao = new TrainingDaoImpl();
	    Training training=trainingDao.selectTraining(sql, param);
	    
		sql = "DELETE FROM training WHERE `id` = ? and `companyId`=?";
		int count = trainingDao.updateTraining(sql, param);
		if (count > 0) {
			System.out.println("成功删除《"+training.getName()+"》培训！");
		}
		return count;
	}

	@Override
	/**
	 ** 修改公司总资金
	 */
	public int setMoney(double money, int companyId) {
		String updatesql = null;
		updatesql = "update company set `money`=? where `id`=?";
		String[] param = {String.valueOf(money),String.valueOf(companyId)};
		CompanyDaoImpl companyDao = new CompanyDaoImpl();
		int updateCompany = companyDao.updateCompany(updatesql, param);// 更新公司信息
		return updateCompany;
	}

	@Override
	/**
	 ** 培训价格设置
	 */
	public int setPrice(int trainingId, int trainingCompanyId) {
		
		//更新语句
		String updatesql = null;
		updatesql = "UPDATE training SET `price`=? WHERE `id`=? and `companyId`=?";
		
		Scanner input = new Scanner(System.in);
		System.out.println("请输入培训的价格：");
		double trainingPrice = input.nextDouble();
		while(trainingPrice<0)
		{
			if(trainingPrice<0)
			{
				System.out.println("培训课程的价格不能小于0，请重新输入！");
				System.out.println("请输入培训的价格：");
				trainingPrice = input.nextDouble();
			}
		}
		
		String[] param = {String.valueOf(trainingPrice),String.valueOf(trainingId),String.valueOf(trainingCompanyId)};
		
		//找到所更新的培训
		TrainingDaoImpl trainingDao = new TrainingDaoImpl();
		String sql="select * from training where `id`=? and `companyId`=?";
		String[] param1= {String.valueOf(trainingId),String.valueOf(trainingCompanyId)};
		Training training = trainingDao.selectTraining(sql, param1);
		
		// 更新培训信息
		int updateTraining = trainingDao.executeSQL(updatesql, param);
		if(updateTraining>0)
		{
			System.out.println("培训时间设置成功！\n");
			System.out.println(training.getTime()+"《"+training.getName()+"》培训的价格为："+trainingPrice);
			
		}
		return updateTraining;
	}

	@Override
	/**
	 ** 查询培训公司总资金
	 */
	public double getMoney(int companyId) {
		String sql="select * from company where `id`=?";
		String[] param = { String.valueOf(companyId)};
		CompanyDao petDao=new CompanyDaoImpl();
		Company company=petDao.getCompany(sql, param);
		return company.getMoney();
	}

	@Override
	/**
	 ** 培训时间设置
	 */
	public int setTime(int trainingId, int companyId) throws ParseException {
		String updatesql = null;
		updatesql = "UPDATE training SET `time`=? WHERE `id`=? and `companyId`=?";
		Scanner input = new Scanner(System.in);
		System.out.println("请输入培训的时间：");
		String trainingtime = input.nextLine();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=sdf.parse(trainingtime);
		
		Date date = new Date();
		String Time=sdf.format(date);
		Date date2=sdf.parse(Time);
		while(date1.before(date2))
		{
			System.out.println("时间不能在今日之前，请重新输入：");
			trainingtime = input.nextLine();
			date1=sdf.parse(trainingtime);
		}
		
		String[] param = {trainingtime ,String.valueOf(trainingId),String.valueOf(companyId)};
		
		TrainingDaoImpl trainingDao = new TrainingDaoImpl();
		String sql="select * from training where `id`=? and `companyId`=?";
		String[] param1= {String.valueOf(trainingId),String.valueOf(companyId)};
		Training training=trainingDao.selectTraining(sql, param1);
		
		int updateTraining = trainingDao.executeSQL(updatesql, param);// 更新培训信息
		if(updateTraining>0)
		{
			System.out.println("培训时间设置成功！\n");
			System.out.println("《"+training.getName()+"》培训的时间为："+trainingtime);
			
		}
		return updateTraining;
	}

	@Override
	/**
	 ** 查询培训成绩排名
	 */
	public List<Grade> getGradeRanking(int trainingId,int companyId) {
		
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
	 ** 培训成绩录入
	 */
	public int setScore(int participantId,int trainingId,int companyId) {
		String updatesql = null;
		updatesql = "update grade set `score`=? where `participantId`=? and `trainingId`=?";
		Scanner input = new Scanner(System.in);
		System.out.println("请输入编号为"+participantId+"培训参与者的成绩：");
		double trainingScore = input.nextDouble();
		while(trainingScore<0)
		{
			if(trainingScore<0)
			{
				System.out.println("成绩不能小于0，请重新输入：");
				trainingScore = input.nextDouble();
			}
		}
		
		String[] param = {String.valueOf(trainingScore),String.valueOf(participantId),String.valueOf(trainingId)};
		GradeDaoImpl gradeDao = new GradeDaoImpl();
		int updateGrade = gradeDao.executeSQL(updatesql, param);// 更新培训信息
		
		String sql1 = "select * from grade where `participantId`=? and `trainingId`=?";
		String[] param1 = { String.valueOf(participantId),String.valueOf(trainingId) };
		Grade grade = gradeDao.selectGrade(sql1, param1);
		if(updateGrade>0)
		{
			System.out.println("成功录入成绩！\n");
			//输出该培训参与者的成绩
		
			System.out.println("id\t"+"名字\t"+"成绩");
			System.out.println(grade.getParticipantId()+"\t"+grade.getParticipantName()+"\t"+trainingScore);
			
		}
		return updateGrade;
	}
	
	

}
