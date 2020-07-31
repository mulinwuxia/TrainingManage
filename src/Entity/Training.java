package Entity;

import java.util.Date;

/**
 * 培训课程类
 */
public class Training {
    private int id;           //培训课程编号
    private String name;      //培训课程名称
    private int companyId;    //培训公司编号
    private Date time;        //培训课程时间
    private double price;     //培训课程价格
    private int capacity;     //培训课程课容量

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
    
    
}
