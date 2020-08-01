package Test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Dao.CompanyDao;
import Dao.GradeDao;
import Dao.ParticipantDao;
import Dao.SignDao;
import Dao.TrainingDao;
import Dao.impl.ApplyDaoImpl;
import Dao.impl.CompanyDaoImpl;
import Dao.impl.GradeDaoImpl;
import Dao.impl.ParticipantDaoImpl;
import Dao.impl.SignDaoImpl;
import Dao.impl.TrainingDaoImpl;
import Entity.Administrator;
import Entity.Company;
import Entity.Grade;
import Entity.Participant;
import Entity.Sign;
import Entity.Training;
import ServiceImpl.AdministratorManageImpl;
import ServiceImpl.ParticipantManageImpl;

/**
 * 培训公司管理
*某培训公司提供培训服务。
*实现培训时间安排（每次3天），培训参与人的信息管理，培训现场签到管理，培训后成绩管理。
*能够实现统计某次培训的各个名次和成绩排名的情况。
*实现根据人名查找参加了哪些培训，某次培训共有哪些人参加。
 */
/**
 ** 系统入口类
 */
public class Main {

	public static void main(String[] args) {
		Main.startTrainingManage();

	}
	
	public static void clear() throws AWTException
    {
        Robot r = new Robot();
        r.mousePress(InputEvent.BUTTON3_MASK);       // 按下鼠标右键
        r.mouseRelease(InputEvent.BUTTON3_MASK);    // 释放鼠标右键
        r.keyPress(KeyEvent.VK_CONTROL);             // 按下Ctrl键
        r.keyPress(KeyEvent.VK_R);                    // 按下R键
        r.keyRelease(KeyEvent.VK_R);                  // 释放R键
        r.keyRelease(KeyEvent.VK_CONTROL);            // 释放Ctrl键
        r.delay(1000);       

    }

	private static void startTrainingManage() {
		System.out.println("----------------------培训公司管理系统启动-----------------");
		//输出数据库最初的数据信息
		System.out.println("培训课程信息");
		System.out.println("****************************************************");
		TrainingDao trainingDao = new TrainingDaoImpl();
		List<Training> trainingList = trainingDao.getAllTraining();
		System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t"+"培训公司编号");
		for (int i = 0; i < trainingList.size(); i++) {
			Training training = trainingList.get(i);
			System.out.println(training.getId()+"\t\t"+ training.getName()+"\t"+ training.getTime()+"\t"+training.getCompanyId());
		 }
		System.out.println("****************************************************");
        System.out.print("\n");
        System.out.println("培训参与者信息");
        ParticipantDao participantDao = new ParticipantDaoImpl();
		List<Participant> participantList = participantDao.getAllParticipant();
		System.out.println("****************************************************");
		System.out.println("培训参与者编号\t"+ "培训参与者姓名\t" );
		for (int i = 0; i < participantList.size(); i++) {
			Participant participant = participantList.get(i);
			System.out.println(participant.getId() +"\t\t"+ participant.getName()+"\t");
		}
		System.out.println("****************************************************");
		System.out.print("\n");
		System.out.println("培训公司信息");
        System.out.println("****************************************************");
        CompanyDao companyDao = new CompanyDaoImpl();
        List<Company> companyList = companyDao.getAllCompany();
        System.out.println("培训公司编号\t" + "培训公司名称\t");
        for (int i=0;i<companyList.size();i++) {
        	Company company = companyList.get(i);
	    System.out.println(company.getId() +"\t\t"+ company.getName()+"\t");
        }
        System.out.println("****************************************************");
        System.out.print("\n");
        Scanner input = new Scanner(System.in);
        System.out.println("请选择输入登录模式，输入1为培训参与者登录，输入2为培训公司管理者登录");
    	boolean type = true;
		String num;
		while (type) {
			num = input.next();
			if ("1".equals(num)) {
				Main.participantLogin();
				type = false;
			} else if ("2".equals(num)) {
				Main.administratorLogin();
				type = false;
			} else {
				System.out.println("输入有误，请按照指定规则输入");
				System.out.println("请选择登录模式，输入1为培训参与者登录，输入2为培训公司管理者登录");
				type = true;
			}
	    }
		
	}
	

	//培训参与者登录
	private static Participant participantLogin() {
		Scanner input = new Scanner(System.in);
		ParticipantManageImpl trainingParticipant = new ParticipantManageImpl();
		Participant participant = trainingParticipant.login();
		boolean reg = true;
		while (reg) {
			if (null == participant.getName()) {
				System.out.println("登录失败，请确认您的用户名和密码后重新输入");
				participant = trainingParticipant.login();
				reg = true;
			} else {
				reg = false;
				boolean type = true;
				}
			}
		System.out.println("您已登录成功，可以进行如下操作");
		ParticipantChoose(participant);
		return participant;
		
	}
	
	//培训参与者菜单
	private static void ParticipantChoose(Participant participant) {
		Scanner input = new Scanner(System.in);
		int num = -1; //用户输入0返回时的输入数字，num=0时重复显示主菜单;num在用户每次订餐操作后进行提示重新赋值
	    boolean flag = false; //记录用户是否退出系统的状态：退出true   不退出false
	    do {
	    	System.out.println("****************************************************");
	    	System.out.println("1：培训课程签到");
			System.out.println("2：申请参加培训课程");
			System.out.println("3：取消参加培训课程");
			System.out.println("4：查询所参加过的培训课程");
			System.out.println("5：查询培训课程成绩排名");
			System.out.println("6：查询余额");
			System.out.println("7：充值");
			System.out.println("8：退出");
			System.out.println("****************************************************");
			System.out.println("请根据需要执行的操作，选择序号输入");
			boolean type = true;
	    	while (type) {
				int choose = input.nextInt();
				switch (choose) {
				case 1:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.trainingSign(participant);
					type = false;
					break;
				case 2:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.takeTraining(participant);
					type = false;
					break;
				case 3:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
				    Main.cancleTraining(participant);
					type = false;
					break;
				case 4:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.showTrainings(participant);
					type = false;
					break;
				case 5:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.showScoreRanking(participant);
					type = false;
					break;
				case 6:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.showMoney(participant);
					type = false;
					break;
				case 7:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.addMoney(participant);
					type = false;
					break;
				case 8:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					System.out.println("退出成功,谢谢使用!");
					type = false;
					flag = true;
					break;
				default:
					System.out.println("输入有误,请重新输入!");
					type = true;
					break;
				}
			}
	    	if(!flag)//!flag 等同于 flag ==false
			{ 
				System.out.print("请输入0，返回菜单：");
				num = input.nextInt();
			}
			else
			{
				break;
			}
	    }while(num==0);
		
		
	}
	
	private static void trainingSign(Participant participant) {

		System.out.println("-------培训课程签到--------------");
		
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		String Time=dateFormat.format(date);
		
		
		List<Sign> registrateList = new ArrayList<Sign>();
		String sql="SELECT sign.`participantId`,sign.`participantName`,sign.`trainingId`,sign.`signFlag`" + 
				"FROM sign JOIN training ON sign.`trainingId`=training.`id`" + 
				"WHERE sign.`participantId`=? AND sign.`signFlag`=0 AND training.`time`=? ";
		String[] param = {String.valueOf(participant.getId()),Time};
		SignDao petDao = new SignDaoImpl();
		registrateList = petDao.selectSigns(sql, param);
		
		if(registrateList.isEmpty())
		{
			System.out.println("未申请培训课程，或者已经签到，或者时间未到，不能进行培训课程的签到!");
		}
		else
		{
			System.out.println("已经申请，且可以进行签到的培训课程：");
			String sql1="SELECT DISTINCT training.`id`,training.`name`,training.`companyId`,training.`time`,training.`price`,training.`capacity`" + 
					"FROM sign JOIN training ON sign.`trainingId`=training.`id`" + 
					"WHERE sign.`participantId`=? AND sign.`signFlag`=0 AND training.`time`=?";
			String [] param1= {String.valueOf(participant.getId()),Time};
			List<Training> trainingList = new ArrayList<Training>();
			TrainingDao trainingDao = new TrainingDaoImpl();
			trainingList = trainingDao.selectTrainings(sql1, param1);
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t"+"培训公司编号\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t"+training.getCompanyId());
			}
			System.out.println("****************************************************");
			
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = trainingDao.selectTraining(sql2, param2);
			
			boolean reg = true;
			while (reg) {
				if (training.getName() != null ) {
					reg = false;
					boolean type = true;
					
				} 
				else {
					
					System.out.println("您未参与该培训课程，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = trainingDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			ParticipantManageImpl participantSign=new ParticipantManageImpl();
			participantSign.sign(trainingId, participant.getId());
		}
		
	}

	private static void takeTraining(Participant participant) {

		System.out.println("-------申请参加培训课程--------------");
		
		//现在的时间
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		String Time=dateFormat.format(date);
		
		//找到sign表中的time，根据每次培训三天的原则，计算出不能进行申请的时间，进而找出符合条件的培训
		
		List<Training> trainingList = new ArrayList<Training>();
		String sql="SELECT *\r\n" + 
				"FROM training\r\n" + 
				"WHERE training.`time` NOT IN (SELECT training.`time`\r\n" + 
				"FROM SIGN JOIN training ON sign.`trainingId`=training.`id`\r\n" + 
				"UNION\r\n" + 
				"SELECT DATE_ADD( training.`time`, INTERVAL 1 DAY)\r\n" + 
				"FROM SIGN JOIN training ON sign.`trainingId`=training.`id`\r\n" + 
				"UNION\r\n" + 
				"SELECT DATE_ADD( training.`time`, INTERVAL 2 DAY)\r\n" + 
				"FROM SIGN JOIN training ON sign.`trainingId`=training.`id`)\r\n" + 
				"AND DATE_ADD( training.`time`, INTERVAL 1 DAY) NOT IN (SELECT training.`time`\r\n" + 
				"FROM SIGN JOIN training ON sign.`trainingId`=training.`id`\r\n" + 
				"UNION\r\n" + 
				"SELECT DATE_ADD( training.`time`, INTERVAL 1 DAY)\r\n" + 
				"FROM SIGN JOIN training ON sign.`trainingId`=training.`id`\r\n" + 
				"UNION\r\n" + 
				"SELECT DATE_ADD( training.`time`, INTERVAL 2 DAY)\r\n" + 
				"FROM SIGN JOIN training ON sign.`trainingId`=training.`id`)\r\n" + 
				"AND  DATE_ADD( training.`time`, INTERVAL 2 DAY) NOT IN (SELECT training.`time`\r\n" + 
				"FROM SIGN JOIN training ON sign.`trainingId`=training.`id`\r\n" + 
				"UNION\r\n" + 
				"SELECT DATE_ADD( training.`time`, INTERVAL 1 DAY)\r\n" + 
				"FROM SIGN JOIN training ON sign.`trainingId`=training.`id`\r\n" + 
				"UNION\r\n" + 
				"SELECT DATE_ADD( training.`time`, INTERVAL 2 DAY)\r\n" + 
				"FROM SIGN JOIN training ON sign.`trainingId`=training.`id`)\r\n" + 
				"AND training.`time` > ? AND training.`capacity`>0\r\n";
		String[] param = {Time };
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		
		if(trainingList.isEmpty())
		{
			System.out.println("所有培训课程的课容量都满了，或培训课程时间已经过了，或者已经申请，不能申请参加此培训课程！");
		}
		else
		{
			System.out.println("可以申请的培训课程：");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t"+"培训课程价格\t"+"培训公司编号\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t"+training.getPrice()+"\t\t"+training.getCompanyId());
			}
			System.out.println("****************************************************");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = petDao.selectTraining(sql2, param2);
			
			boolean reg = true;
			while (reg) {
				if (training.getName() !=null) {
					reg = false;
					
				} 
				else {
					
					System.out.println("该培训课程编号不符合条件，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = petDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			
			
			ParticipantManageImpl participantTake=new ParticipantManageImpl();
			
			//申请培训
			
			int count = participantTake.takeTraining(trainingId, participant.getId());
			
			//申请成功，再修改资金
			if(count==1)
			{
				//修改资金
				double participantMoney=participant.getMoney();
				double trainingPrice=training.getPrice();
				double temp=participantMoney-trainingPrice;
				while(temp<0)
				{
					System.out.println("您的资金余额为："+participantMoney+"，余额不足，请充值！");
					System.out.println("请输入充值金额：");
					double congzhi=input.nextDouble();
					temp+=congzhi;
					participantMoney+=congzhi;
					System.out.println("充值成功！");
				}
				participantMoney-=trainingPrice;
				participant.setMoney(participantMoney);
				//培训参与者资金设置
				participantTake.setMoney(participantMoney,participant.getId());
				
				//培训公司资金设置
				
				String sql3 = "select * from company where `id`=?";
				String[] param3 = {String.valueOf(training.getCompanyId())};
				CompanyDaoImpl takeCompany =new CompanyDaoImpl();
				Company company = takeCompany.getCompany(sql3, param3);
				double money = company.getMoney();
				AdministratorManageImpl changeCompanyMoney = new AdministratorManageImpl();
				changeCompanyMoney.setMoney(money+training.getPrice(),training.getCompanyId());
				System.out.println("\n申请"+training.getTime()+"《"+training.getName()+"》成功！");
			}
			
			
			
		}
		
	}

	private static void cancleTraining(Participant participant) {

		System.out.println("-------取消参加培训课程--------------");
		
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		String Time=dateFormat.format(date);
		
		List<Training> trainingList = new ArrayList<Training>();
		String sql="SELECT DISTINCT training.`id`, training.`name`,training.`companyId`,training.`time`,training.`price`,training.`capacity`" + 
				"FROM training JOIN sign ON training.`id`=sign.`trainingId`" + 
				"WHERE training.`time`>? AND sign.`signFlag`=0";
		String[] param = {Time };
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		
		if(trainingList.isEmpty())
		{
			System.out.println("未申请培训，或者培训时间已过，不能取消参与培训！");
		}
		else
		{
			System.out.println("已经申请，时间未过，且未参与的培训课程：");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t"+"培训课程价格\t"+"培训公司编号\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t"+training.getPrice()+"\t\t"+training.getCompanyId());
			}
			System.out.println("****************************************************");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = petDao.selectTraining(sql2, param2);
			
			
			boolean reg = true;
			while (reg) {
				if (training!=null) {
					reg = false;
				} 
				else {
					
					System.out.println("该培训课程编号不符合条件，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = petDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			
			
		
			//取消培训
			ParticipantManageImpl participantCancle=new ParticipantManageImpl();
			int count = participantCancle.cancelTraining(trainingId, participant.getId());
			
			//取消成功再退钱
			if(count==1)
			{
				double participantMoney=participant.getMoney();
				double trainingPrice=training.getPrice();
				participantMoney+=trainingPrice;
				participant.setMoney(participantMoney);
				
				//培训参与者资金设置
				participantCancle.setMoney(participantMoney,participant.getId());
				
				//培训公司资金设置	
				String sql3 = "select * from company where `id`=?";
				String[] param3 = {String.valueOf(training.getCompanyId())};
				CompanyDaoImpl cancleCompany =new CompanyDaoImpl();
				Company company = cancleCompany.getCompany(sql3, param3);
				double money = company.getMoney();
				AdministratorManageImpl changeCompanyMoney = new AdministratorManageImpl();
				changeCompanyMoney.setMoney(money-training.getPrice(),training.getCompanyId());
			}
			
		}
		
	}

	private static void showTrainings(Participant participant) {
		System.out.println("-------查询参与过的培训课程--------------");
		
		System.out.println("****************************************************");
		System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t"+"培训公司编号\t");
		List<Training> trainingList = new ArrayList<Training>();
		String sql="SELECT DISTINCT training.`id`,training.`name`,training.`companyId`,training.`time`,training.`price`,training.`capacity`" + 
				"FROM training JOIN sign ON training.`id`=sign.`trainingId`" + 
				"WHERE sign.`participantId`=? AND sign.`signFlag`=1 ";
		String[] param = {String.valueOf(participant.getId())};
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		for(int i=0;i<trainingList.size();i++)
		{
			Training training = trainingList.get(i);
			System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t"+training.getCompanyId());
		}
		System.out.println("****************************************************");
		
	}

	private static void showScoreRanking(Participant participant) {

		System.out.println("-------培训课程成绩排行榜--------------");
		
		List<Training> trainingList = new ArrayList<Training>();
		String sql="SELECT DISTINCT training.`id`,training.`name`,training.`companyId`,training.`time`,training.`price`,training.`capacity`" + 
				"FROM training JOIN sign ON training.`id`=sign.`trainingId`" + 
				"WHERE sign.`participantId`=? AND sign.`signFlag`=1 ";
		String[] param = {String.valueOf(participant.getId())};
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		
		if(trainingList.isEmpty())
		{
			System.out.println("未参与培训，不能进行培训成绩查询！");
		}
		else
		{
			System.out.println("已经参与的培训：");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t"+"培训公司编号\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t"+training.getCompanyId());
			}
			System.out.println("****************************************************");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = petDao.selectTraining(sql2, param2);
			
			boolean reg = true;
			while (reg) {
				if (training.getName()!=null) {
					reg = false;
					boolean type = true;
				} 
				else {
					
					System.out.println("该培训课程编号不符合条件，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = petDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			ParticipantManageImpl participantShowScroeRanking=new ParticipantManageImpl();
			List<Grade> gradeList = new ArrayList<Grade>();
			gradeList = participantShowScroeRanking.getGradeRanking(trainingId,training.getCompanyId(), participant.getId());
			System.out.println("\n"+training.getTime()+"《"+training.getName()+"》培训的成绩排名：");
			System.out.println("注意：自己的排名为红色部分");
			System.out.println("****************************************************");
			System.out.println("培训课程成绩排名\t"+"培训参与者编号\t"+"培训参与者名字\t"+"培训课程成绩\t");
			for(int i=0;i<gradeList.size();i++)
			{
				Grade grade = gradeList.get(i);
				if(grade.getParticipantId()==participant.getId())
					System.err.println((i+1)+"\t\t"+grade.getParticipantId()+"\t\t"+grade.getParticipantName()+"\t\t"+grade.getScore());
				else
					System.out.println((i+1)+"\t\t"+grade.getParticipantId()+"\t\t"+grade.getParticipantName()+"\t\t"+grade.getScore());
					
			}
			System.out.println("****************************************************");
		}
		
	}

	private static void showMoney(Participant participant) {
		System.out.println("-------余额查询--------------");
		System.out.println("您的余额为："+participant.getMoney());
		
	}

	private static void addMoney(Participant participant) {
		System.out.println("-------充值--------------");
		System.out.println("您的余额为："+participant.getMoney());
		System.out.println("请输入充值金额：");
		Scanner input = new Scanner(System.in);
		double chongzhi=input.nextDouble();
		while(chongzhi<=0)
		{
			if(chongzhi<=0)
			{
				System.out.println("充值金额不能小于等于0，请重新输入！");
				System.out.println("请输入充值金额：");
				chongzhi=input.nextDouble();
			}
		}
		double participantMoney=participant.getMoney();
		participantMoney+=chongzhi;
		participant.setMoney(participantMoney);
		ParticipantManageImpl participantChange=new ParticipantManageImpl();
		participantChange.setMoney(participantMoney, participant.getId());
		System.out.println("充值成功！");
		System.out.println("您的余额为："+participant.getMoney());
		
	}

	//培训公司管理者登录
	private static Administrator administratorLogin() {
		Scanner input = new Scanner(System.in);
		AdministratorManageImpl adminstrator = new AdministratorManageImpl();
		Administrator administrator = adminstrator.login();
		boolean reg = true;
		while (reg) {
			if (null == administrator.getName()
					) {
				System.out.println("登录失败，请确认您的用户名和密码后重新输入");
				administrator = adminstrator.login();
				reg = true;
			} else {
				reg = false;
				}
			}
			System.out.println("您已登录成功，可以进行如下操作");
			administratorChoose(administrator);
		return administrator;
		
	}

	//培训公司管理者菜单
	private static void administratorChoose(Administrator administrator) {
		Scanner input = new Scanner(System.in);
		int num = -1; //用户输入0返回时的输入数字，num=0时重复显示主菜单;num在用户每次订餐操作后进行提示重新赋值
	    boolean flag = false; //记录用户是否退出系统的状态：退出true   不退出false
		do {
			System.out.println("****************************************************");
			System.out.println("1：培训课程时间设置");
			System.out.println("2：培训课程价格设置");
			System.out.println("3：培训课程成绩录入");
			System.out.println("4：添加培训课程");
			System.out.println("5：删除培训课程");
			System.out.println("6：查询培训课程成绩排名");
			System.out.println("7：查询培训课程的所有参与者");
			System.out.println("8：查询参与者参加过的培训课程");
			System.out.println("9：查询公司总资金");
			System.out.println("10：退出");
			System.out.println("****************************************************");
			System.out.println("请根据需要执行的操作，选择序号输入");
			boolean type = true;
			while (type) {
				int choose = input.nextInt();
				switch (choose) {
				case 1:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.setTrainingTime(administrator);
					type = false;
					break;
				case 2:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.setTrainingPrice(administrator);
					type = false;
					break;
				case 3:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.setTrainingScore(administrator);
				    
					type = false;
					break;
				case 4:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.addTraining(administrator);
					type = false;
					break;
				case 5:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.deleteTraining(administrator);
					
					type = false;
					break;
				case 6:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.showScoreRanking(administrator);
					
					type = false;
					break;
				case 7:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.showParticipants(administrator);
					
					type = false;
					break;
				case 8:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.showTrainings(administrator);
					
					type = false;
					break;
				case 9:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					Main.showCompanyMoney(administrator);
					type = false;
					break;
				case 10:
					try {
						Main.clear();
					} catch (AWTException e) {
						e.printStackTrace();
					}
					System.out.println("退出成功，谢谢使用!");
					type = false;
					flag=true;
					break;
				default:
					System.out.println("输入有误,请重新输入!");
					type = true;
					break;
				}
			}
			if(!flag)//!flag 等同于 flag ==false
			{ 
				System.out.print("请输入0，返回菜单：");
				num = input.nextInt();
			}
			else
			{
				break;
			}
		}while(num==0);
		
	}

	private static void setTrainingTime(Administrator administrator) {

		System.out.println("-------培训课程时间设置-------");
		
		//在company表中找出所属的公司
		String sql1="select * from company where `id` = ?";
		String [] param1 = {String.valueOf(administrator.getCompanyId())};
		CompanyDao companyDao = new CompanyDaoImpl();
		Company company = companyDao.getCompany(sql1, param1);
		
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		String Time=dateFormat.format(date);
		
		//在training表中选出可以修改时间的培训课程
		List<Training> trainingList = new ArrayList<Training>();
		String sql="SELECT * " + 
				"FROM training " + 
				"WHERE `time`>? AND `companyId`=? ";
		String[] param = {Time,String.valueOf(administrator.getCompanyId())};
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		
		
		if(trainingList.isEmpty())
		{
			System.out.println("公司没有时间未到的培训课程，不能进行培训课程时间设置！");
		}
		else
		{
			System.out.println(company.getName()+"的培训课程");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t");
			}
			System.out.println("****************************************************");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = petDao.selectTraining(sql2, param2);
			
			boolean reg = true;
			while (reg) {
				if (training.getName() !=null) {
					reg = false;
					boolean type = true;
					
				} 
				else {
					
					System.out.println("该培训课程编号不符合条件，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = petDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			AdministratorManageImpl trainingSetTime=new AdministratorManageImpl();
			try {
				trainingSetTime.setTime(trainingId, administrator.getCompanyId());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	private static void setTrainingPrice(Administrator administrator) {

		System.out.println("-------培训课程价格设置-------");
		
		//在company表中找出所属的公司
		String sql1="select * from company where `id` = ?";
		String [] param1 = {String.valueOf(administrator.getCompanyId())};
		CompanyDao companyDao = new CompanyDaoImpl();
		Company company = companyDao.getCompany(sql1, param1);
		
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		String Time=dateFormat.format(date);
		
		//在training表中选出可以时间未到的培训课程
		List<Training> trainingList = new ArrayList<Training>();
		String sql="SELECT * " + 
				"FROM training " + 
				"WHERE `time`>? AND `companyId`=? ";
		String[] param = {Time,String.valueOf(administrator.getCompanyId())};
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		
		if(trainingList.isEmpty())
		{
			System.out.println("公司没有时间未到的培训课程，不能进行培训课程价格设置！");
		}
		else
		{
			System.out.println(company.getName()+"的培训课程");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t"+"培训课程价格\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t"+training.getPrice());
			}
			System.out.println("****************************************************");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = petDao.selectTraining(sql2, param2);
			
			boolean reg = true;
			while (reg) {
				if (training.getName() !=null) {
					reg = false;
					boolean type = true;
					
				} 
				else {
					
					System.out.println("该培训课程编号不符合条件，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = petDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			AdministratorManageImpl SetTrainingPrice=new AdministratorManageImpl();
			SetTrainingPrice.setPrice(trainingId, administrator.getCompanyId());
			
		}
		
	}

	private static void setTrainingScore(Administrator administrator) {

		System.out.println("-------培训课程成绩录入-------");
		
		//在company表中找出所属的公司
		String sql1="select * from company where `id` = ?";
		String [] param1 = {String.valueOf(administrator.getCompanyId())};
		CompanyDao companyDao = new CompanyDaoImpl();
		Company company = companyDao.getCompany(sql1, param1);		
		
		//将training表，grade表，sign表结合，查询成绩为0，已签到的培训课程
		List<Training> trainingList = new ArrayList<Training>();
		String sql="SELECT DISTINCT training.`id`,training.`name`,training.`companyId`,training.`time`,training.`price`,training.`capacity` " + 
				"FROM training JOIN grade ON training.`id`=grade.`trainingId` JOIN sign ON sign.`trainingId`=training.`id`" + 
				"WHERE grade.`score` = 0 AND sign.`signFlag` = 1 AND training.`companyId`=?";
		String[] param = {String.valueOf(administrator.getCompanyId())};
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		
		if(trainingList.isEmpty())
		{
			System.out.println("没有需要录入成绩的培训课程！");
		}
		else
		{
			System.out.println(company.getName()+"的需要录入成绩的培训课程名单");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t");
			}
			System.out.println("****************************************************");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = petDao.selectTraining(sql2, param2);
			
			boolean reg = true;
			while (reg) {
				if (training.getName() !=null) {
					reg = false;
					boolean type = true;
					
				} 
				else {
					
					System.out.println("该培训课程编号不符合条件，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = petDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			AdministratorManageImpl trainingSetScore=new AdministratorManageImpl();
			GradeDao gradeDao = new GradeDaoImpl();
			List<Grade> gradeList = new ArrayList<Grade>();
			
			String sql3="select * from grade where `trainingId`=?  and  `score`=0";
			String[] param3= {String.valueOf(trainingId)};
			
			gradeList=gradeDao.selectGrades(sql3, param3);
			System.out.println("需要录入成绩的名单：");
			System.out.println("****************************************************");
			System.out.println("培训参与者编号\t"+"培训参与者姓名");
			for(int i=0;i<gradeList.size();i++)
			{
				Grade grade=gradeList.get(i);
				System.out.println(grade.getParticipantId()+"\t\t"+grade.getParticipantName());
			}
			System.out.println("****************************************************");
			
			for(int i=0;i<gradeList.size();i++)
			{
				Grade grade=gradeList.get(i);
				trainingSetScore.setScore(grade.getParticipantId(), trainingId, company.getId());
			}
		}
		
	}
		

	private static void addTraining(Administrator administrator) {

		System.out.println("-------添加培训课程-------");
		
		AdministratorManageImpl trainingAdd=new AdministratorManageImpl();
		try {
			trainingAdd.addTraining(administrator.getCompanyId());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

	private static void deleteTraining(Administrator administrator) {

		System.out.println("-------删除培训课程-------");
		
		//在company表中找出所属的公司
		String sql1="select * from company where `id` = ?";
		String [] param1 = {String.valueOf(administrator.getCompanyId())};
		CompanyDao companyDao = new CompanyDaoImpl();
		Company company = companyDao.getCompany(sql1, param1);
		
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		String Time=dateFormat.format(date);
		Date date1 = null;
		try {
			date1 = dateFormat.parse(Time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Training> trainingList = new ArrayList<Training>();
		String sql="select * from training where `companyId`=? ";
		String[] param = {String.valueOf(administrator.getCompanyId())};
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		
		if(trainingList.isEmpty())
		{
			System.out.println("公司未开设培训课程，不能删除培训课程！");
		}
		else
		{
			System.out.println(company.getName()+"的培训课程");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t");
			}
			System.out.println("****************************************************");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = petDao.selectTraining(sql2, param2);
			
			boolean reg = true;
			while (reg) {
				if (training.getName()!=null ) {
					reg = false;
					
				} 
				else {
					
					System.out.println("该培训课程编号不符合条件，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = petDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			
			Date date2=training.getTime();
			//培训项目未开始要退钱，也就是说有申请的，同时也要将申请的记录删除
			if(date1.before(date2))
			{
				double trainingPrice=training.getPrice();
				
				//找出需要退钱的培训参与者
				String selectsql="SELECT DISTINCT participant.`id`,participant.`name`,participant.`password`,participant.`money` " + 
						"FROM apply JOIN participant ON apply.`participantId`=participant.`id` JOIN training ON training.`id`=apply.`trainingId`" + 
						"WHERE training.`companyId`=?";
				String[] selectparam = {String.valueOf(company.getId())};
				List<Participant> pList=new ArrayList<Participant>();
				ParticipantDaoImpl pdao=new ParticipantDaoImpl();
				pList=pdao.selectParticipants(selectsql, selectparam);
				
				//退钱给培训参与者
				ParticipantManageImpl pinfodao=new ParticipantManageImpl();
				for(int i=0;i<pList.size();i++)
				{
					Participant p=pList.get(i);
					pinfodao.setMoney(trainingPrice+p.getMoney(), p.getId());
					
				}
				//培训公司资金管理
				
				AdministratorManageImpl companyMoney=new AdministratorManageImpl();
				double cm=company.getMoney();
				cm-=training.getPrice()*pList.size();
				company.setMoney(cm);
				companyMoney.setMoney(cm, company.getId());
				
				//删除培训申请记录
				String deleteSql = "delete from apply where `trainingId`=?";
				String [] deleteParam = {String.valueOf(trainingId)};
				ApplyDaoImpl applyDao = new ApplyDaoImpl();
				applyDao.updateApply(deleteSql, deleteParam);
				
			}
			
			//删除培训签到记录
			String signSql=null;
			signSql="delete from sign where `trainingId`=?";
			String [] signParam = {String.valueOf(trainingId)};
			SignDaoImpl signDao = new  SignDaoImpl();
			signDao.executeSQL(signSql, signParam);
			
			//删除培训课程成绩记录
			String gradeSql=null;
			gradeSql="delete from grade where `trainingId`=?";
			String [] gradeParam = { String.valueOf(trainingId)};
			GradeDaoImpl gradeDao = new GradeDaoImpl();
			gradeDao.executeSQL(gradeSql, gradeParam);
			
			//培训公司删除培训
			AdministratorManageImpl trainingDelete=new AdministratorManageImpl();
			trainingDelete.deleteTraining(trainingId,company.getId());
		}
		
	}

	private static void showScoreRanking(Administrator administrator) {

		System.out.println("-------查询培训课程的成绩排行榜-------");
		
		//在company表中找出所属的公司
		String sql1="select * from company where `id` = ?";
		String [] param1 = {String.valueOf(administrator.getCompanyId())};
		CompanyDao companyDao = new CompanyDaoImpl();
		Company company = companyDao.getCompany(sql1, param1);		
		
		//公司已经录入成绩的培训课程
		List<Training> trainingList = new ArrayList<Training>();
		String sql="SELECT DISTINCT training.`id`,training.`name`,training.`companyId`,training.`time`,training.`price`,training.`capacity`" + 
				"FROM training JOIN grade ON training.`id`=grade.`trainingId`" + 
				"WHERE training.`companyId`=? AND grade.`score` !=0 ";
		String[] param = {String.valueOf(administrator.getCompanyId())};
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		
		if(trainingList.isEmpty())
		{
			System.out.println("公司未有已经录入成绩的培训课程，不能查询培训课程的成绩排行榜！");
		}
		else
		{
			System.out.println(company.getName()+"的已经录入成绩的培训课程");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t");
			}
			System.out.println("****************************************************");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = petDao.selectTraining(sql2, param2);
			
			boolean reg = true;
			while (reg) {
				if (training.getName() !=null ) {
					reg = false;
					boolean type = true;
					
				} 
				else {
					
					System.out.println("该培训课程编号不符合条件，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = petDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			
			AdministratorManageImpl showScore=new AdministratorManageImpl();
			List<Grade> gradeList = new ArrayList<Grade>();
			gradeList = showScore.getGradeRanking(trainingId, company.getId());
			System.out.println("\n"+training.getTime()+"《"+training.getName()+"》培训的成绩排名：");
			System.out.println("****************************************************");
			System.out.println("培训课程成绩排名\t"+"培训参与者编号\t"+"培训参与者名字\t"+"培训课程成绩\t");
			for(int i=0;i<gradeList.size();i++)
			{
				Grade grade = gradeList.get(i);
				System.out.println((i+1)+"\t\t"+grade.getParticipantId()+"\t\t"+grade.getParticipantName()+"\t"+grade.getScore());	
					
			}
			System.out.println("****************************************************");
		}
		
	}

	private static void showParticipants(Administrator administrator) {

		System.out.println("-------查询培训课程的参与者-------");
		
		//在company表中找出所属的公司
		String sql1="select * from company where `id` = ?";
		String [] param1 = {String.valueOf(administrator.getCompanyId())};
		CompanyDao companyDao = new CompanyDaoImpl();
		Company company = companyDao.getCompany(sql1, param1);				
		
		List<Training> trainingList = new ArrayList<Training>();
		String sql="select * from training where `companyId`=? ";
		String[] param = {String.valueOf(administrator.getCompanyId())};
		TrainingDao petDao = new TrainingDaoImpl();
		trainingList = petDao.selectTrainings(sql, param);
		
		
		if(trainingList.isEmpty())
		{
			System.out.println("公司未开设培训课程，不能查询培训课程的参与者！");
		}
		else
		{
			System.out.println(company.getName()+"的培训课程");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t");
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime()+"\t");
			}
			System.out.println("****************************************************");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训课程编号：");
			int trainingId=input.nextInt();
			String sql2 = "select * from training where `id` = "+ trainingId;
			String [] param2 = {};
			Training training = petDao.selectTraining(sql2, param2);
			
			
			boolean reg = true;
			while (reg) {
				if (training.getName() != null ) {
					reg = false;
					boolean type = true;
					
				} 
				else {
					
					System.out.println("该培训课程编号不符合条件，请确认后重新输入!");
					System.out.println("请输入培训课程编号：");
					trainingId=input.nextInt();
					sql2 = "select * from training where `id` = "+ trainingId;
					training = petDao.selectTraining(sql2, param2);
					reg = true;
					
					}
				}
			System.out.println(training.getTime()+"《"+training.getName()+"》"+"培训参与者名单");
			System.out.println("****************************************************");
			System.out.println("培训参与者编号\t"+"培训参与者姓名");
			AdministratorManageImpl showParticipant=new AdministratorManageImpl();
			List<Participant> participantList =showParticipant.getParticipants(trainingId, company.getId());
			for(int i=0;i<participantList.size();i++)
			{
				Participant p=participantList.get(i);
				System.out.println(p.getId()+"\t\t"+p.getName());
			}
			System.out.println("****************************************************");
		}
		
	}

	private static void showTrainings(Administrator administrator) {

		System.out.println("-------查询某人参与过的培训--------------");
		
		//在company表中找出所属的公司
		String sql1="select * from company where `id` = ?";
		String [] param1 = {String.valueOf(administrator.getCompanyId())};
		CompanyDao companyDao = new CompanyDaoImpl();
		Company company = companyDao.getCompany(sql1, param1);	
		
		List<Participant> participantList=new ArrayList<Participant>();
		String sql="SELECT DISTINCT participant.`id`,participant.`name`, participant.`password`,participant.`money`" + 
				"FROM participant JOIN sign ON participant.`id` = sign.`participantId` JOIN training ON training.`id`=sign.`trainingId`" + 
				"WHERE training.`companyId`=? AND sign.`signFlag` = 1";
		String[] param = {String.valueOf(company.getId())};
		ParticipantDao petDao = new ParticipantDaoImpl();
		participantList = petDao.selectParticipants(sql, param);
		
		if(participantList.isEmpty())
		{
			System.out.println("没有人参与公司的培训，不能根据人名进行查询！");
		}
		else
		{
			System.out.println("参加"+company.getName()+"的培训的名单");
			System.out.println("****************************************************");
			System.out.println("培训参与者id\t"+"培训参与者名字");
			for(int i=0;i<participantList.size();i++)
			{
				Participant p = participantList.get(i);
				System.out.println(p.getId()+"\t\t"+p.getName());
			}
			System.out.println("****************************************************\n\n");
			
			Scanner input = new Scanner(System.in);
			System.out.println("请输入培训参与者的名字：");
			String participantName=input.nextLine();
			int flag = 0;
			for(int i=0;i<participantList.size();i++)
			{
				Participant p = participantList.get(i);
				if(p.getName().equals(participantName))
					flag=1;
			}
			boolean reg = true;
			while (reg) {
				if (flag==1 ) {
					reg = false;
					
				} 
				else {
					
					System.out.println("该培训参与者不符合条件，请确认后重新输入!");
					System.out.println("请输入培训参与者的名字：");
					participantName=input.next();
					for(int i=0;i<participantList.size();i++)
					{
						Participant p = participantList.get(i);
						if(p.getName().equals(participantName))
							flag=1;
					}
					reg = true;
					
					}
				}
			System.out.println(participantName+"在公司所参与过的培训课程：");
			System.out.println("****************************************************");
			System.out.println("培训课程编号\t"+"培训课程名称\t"+"培训课程时间\t");
			List<Training> trainingList = new ArrayList<Training>();
			AdministratorManageImpl petDao1=new AdministratorManageImpl();
			trainingList = petDao1.getTrainings(participantName, company.getId());
			for(int i=0;i<trainingList.size();i++)
			{
				Training training = trainingList.get(i);
				System.out.println(training.getId()+"\t\t"+training.getName()+"\t"+training.getTime());
			}
			System.out.println("****************************************************");
		}
		
	}

	private static void showCompanyMoney(Administrator administrator) {
		System.out.println("-------查询公司总资金-------------");
		
		//在company表中找出所属的公司
		String sql1="select * from company where `id` = ?";
		String [] param1 = {String.valueOf(administrator.getCompanyId())};
		CompanyDao companyDao = new CompanyDaoImpl();
		Company company = companyDao.getCompany(sql1, param1);
		
		System.out.println(company.getName()+"的总资金为:"+company.getMoney());
		
	}

	

}
