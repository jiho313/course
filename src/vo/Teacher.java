package vo;

import java.util.Date;

public class Teacher {

	private String id;
	private String password;
	private String name;
	private String phone;
	private String email;
	private int salary;
	private String retired;
	private Date createDate;
	private String type = "강사";

	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getRetired() {
		return retired;
	}

	public void setRetired(String retired) {
		this.retired = retired;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", password=" + password + ", name=" + name + ", phone=" + phone + ", email="
				+ email + ", salary=" + salary + ", retired=" + retired + ", createDate=" + createDate + ", type="
				+ type + "]";
	}
	
	

}
