package Entity;

/**
 * 管理者类
 */
public class Administrator {
    private int id;//培训公司管理者编号

    private String name;//培训公司 管理者名称
    
    private int companyId;//培训公司编号

    private String password;//培训公司管理者密码

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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
