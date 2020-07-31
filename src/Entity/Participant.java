package Entity;
/**
 ** 培训参与者类
 */

public class Participant {
    private int id;//培训参与者编号

    private String name;//培训参与者姓名

    private String password;//培训参与者密码

    private double money;//培训参与者资金

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }



}
